package tests;

import static org.junit.jupiter.api.Assertions.*;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import fileTransferProgram.FTclient;
import fileTransferProgram.FTserver;

class FTclientTest {

	private static String ip = "127.0.0.1";
	private static int port = 80;
	private static File TempDir = Paths.get("TestData", "TempDir").toFile();
	private static FTclient client = new FTclient(TempDir.getAbsolutePath());
	private static FTserver server = new FTserver("TestData");
	private static ByteArrayOutputStream outContent;

	@BeforeAll
	static void beforeAll() {
		TempDir.mkdir();
		server.start(port);
	}

	@BeforeEach
	void beforeEach() {
		outContent = new ByteArrayOutputStream();
		System.setOut(new PrintStream(outContent));
	}

	@AfterAll
	static void afterAll() {
		removeFile(TempDir);
		server.shutDown();
		System.setIn(System.in);
		System.setOut(System.out);
	}

	@Test
	void testFTclientInvalidInputs() {
		assertThrows(IllegalArgumentException.class, () -> new FTclient(""));
		assertThrows(IllegalArgumentException.class, () -> new FTclient("  "));
		assertThrows(IllegalArgumentException.class, () -> new FTclient(null));
	}

	@Test
	void testStartStop() {
		setUserInput("STOP");
		client.start(ip, port);
		assertEquals(getExpectedResponse(), outContent.toString());
	}

	@ParameterizedTest
	@ValueSource(strings = { "TestData/file1.txt", "TestData/file2.gif" })
	void testStartGetRequest(String fPath) throws IOException {
		Path expFilePath = Paths.get(fPath);
		Path resultFilePath = Paths.get(TempDir.getPath()).resolve(expFilePath.getFileName());
		setUserInput("GET " + expFilePath.getFileName().toString(), "STOP");
		client.start(ip, port);
		assertEquals(getExpectedResponse("OK"), outContent.toString());
		assertArrayEquals(Files.readAllBytes(expFilePath), Files.readAllBytes(resultFilePath));
	}

	@ParameterizedTest
	@ValueSource(strings = { "TestData/file1.txt", "TestData/file2.gif" })
	void testStartPutRequest(String fPath) throws IOException {
		Path expFilePath = Paths.get(fPath);
		Path pathInServ = Paths.get(TempDir.getName(), "serverOut");
		setUserInput("PUT " + expFilePath + " " + pathInServ, "STOP");
		client.start(ip, port);
		assertEquals(getExpectedResponse("OK"), outContent.toString());
		assertArrayEquals(Files.readAllBytes(expFilePath), Files.readAllBytes(
				Paths.get("TestData").resolve(pathInServ.resolve(expFilePath.getFileName()))));
	}

	@Test
	void testStartPutRequestBadFile() {
		setUserInput("PUT /FakeDir", "STOP");
		client.start(ip, port);
		assertEquals(getExpectedResponse("Cannot find file"), outContent.toString());
	}

	@Test
	void testStartShowRequest() {
		setUserInput("SHOW /FakeDir", "STOP");
		client.start(ip, port);
		assertEquals(getExpectedResponse("Could not find directory"), outContent.toString());
	}

	@Test
	void testStartRemoveRequest() {
		setUserInput("REMOVE /FakeFile", "STOP");
		client.start(ip, port);
		assertEquals(getExpectedResponse("File Not Found"), outContent.toString());
	}

	@Test
	void testStartInvalidMethod() {
		setUserInput("POST /", "STOP");
		client.start(ip, port);
		assertEquals(getExpectedResponse("Invalid Method"), outContent.toString());
	}

	@ParameterizedTest
	@ValueSource(strings = { "GET", "   ", "" })
	void testStartMalformedRequest(String req) {
		setUserInput(req, "STOP");
		client.start(ip, port);
		assertEquals(getExpectedResponse("Malformed request"), outContent.toString());
	}

	@Test
	void testStartFailedConnection() {
		setUserInput("SHOW /", "STOP");
		client.start("FakeIP", port);
		assertEquals(getExpectedResponse("Could not reach server."), outContent.toString());
	}

	@Test
	void testStartInvalidInputs() {
		assertThrows(IllegalArgumentException.class, () -> client.start(null, 80));
		assertThrows(IllegalArgumentException.class, () -> client.start(" ", 80));
		assertThrows(IllegalArgumentException.class, () -> client.start("127.0.0.1", -1));
		assertThrows(IllegalArgumentException.class, () -> client.start("127.0.0.1", 65536));
	}

	private String getExpectedResponse(String... s) {
		String expectedPrompt = "Enter request line or STOP to end" + System.lineSeparator();
		StringBuilder response = new StringBuilder(expectedPrompt);
		for (String string : s) {
			response.append(string + System.lineSeparator());
			response.append(expectedPrompt);
		}
		return response.toString();
	}

	private void setUserInput(String... s) {
		StringBuilder command = new StringBuilder();
		for (String string : s) {
			command.append(string + System.lineSeparator());
		}
		ByteArrayInputStream userInput = new ByteArrayInputStream(command.toString().getBytes());
		System.setIn(userInput);
	}

	private static void removeFile(File file) {
		if (file.isDirectory()) {
			File[] list = file.listFiles();
			for (File f : list) {
				removeFile(f);
			}
			file.delete();
		} else {
			file.delete();
		}
	}
}
