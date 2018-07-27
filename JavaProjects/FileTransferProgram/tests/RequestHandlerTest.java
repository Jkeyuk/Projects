package tests;

import static org.junit.jupiter.api.Assertions.*;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import fileTransferProgram.RequestHandler;

class RequestHandlerTest {
	private ServerSocket server;
	private Socket clientToServer;
	private RequestHandler handler;
	private String workingDir;

	@BeforeEach
	void setup() throws IOException {
		workingDir = "TestData";
		server = new ServerSocket(80);
		clientToServer = new Socket("127.0.0.1", 80);
		Socket connection = server.accept();
		handler = new RequestHandler(connection, workingDir);
	}

	@AfterEach
	void tearDown() throws IOException {
		clientToServer.close();
		server.close();
	}

	@ParameterizedTest
	@ValueSource(strings = { "file1.txt", "file2.gif", "index.html", "test.css", "test.html" })
	void testRunGetRequest(String filePath) throws IOException {
		sendRequest("GET " + filePath, clientToServer);
		handler.run();
		ByteArrayOutputStream expected = new ByteArrayOutputStream();
		expected.write("OK".getBytes());
		Files.copy(Paths.get(workingDir, filePath), expected);
		assertStreamHasBytes(expected.toByteArray(), clientToServer.getInputStream());
	}

	@Test
	void testRunGetRequestInvalidFile() throws IOException {
		sendRequest("GET fakeFile", clientToServer);
		handler.run();
		assertStreamHasBytes("File Not Found".getBytes(), clientToServer.getInputStream());
	}

	@Test
	void testRunShowRequest() throws IOException {
		sendRequest("SHOW /", clientToServer);
		handler.run();
		ByteArrayOutputStream expected = getExpectedShowDirResponse();
		assertStreamHasBytes(expected.toByteArray(), clientToServer.getInputStream());
	}

	@Test
	void testRunShowRequestInvalidDir() throws IOException {
		sendRequest("SHOW /FakeDir", clientToServer);
		handler.run();
		String expected = "Could not find directory";
		assertStreamHasBytes(expected.getBytes(), clientToServer.getInputStream());
	}

	@Test
	void testRunRemoveRequest() throws IOException {
		File tempDir = Paths.get(workingDir, "tempDir").toFile();
		File tempFile = tempDir.toPath().resolve("tempFile.txt").toFile();
		tempDir.mkdir();
		tempFile.createNewFile();
		assertTrue(tempDir.exists());
		assertTrue(tempFile.exists());
		sendRequest("REMOVE " + tempDir.getName(), clientToServer);
		handler.run();
		assertStreamHasBytes("OK".getBytes(), clientToServer.getInputStream());
		assertTrue(!tempFile.exists());
		assertTrue(!tempDir.exists());
		tempFile.delete();
		tempDir.delete();
	}

	@Test
	void testRunRemoveRequestInvalidFile() throws IOException {
		sendRequest("REMOVE fakeFile", clientToServer);
		handler.run();
		assertStreamHasBytes("File Not Found".getBytes(), clientToServer.getInputStream());
	}

	@ParameterizedTest
	@ValueSource(strings = { "file1.txt", "file2.gif", "index.html", "test.css", "test.html" })
	void testRunPutRequest(String fileName) throws IOException {
		File expected = Paths.get(workingDir, fileName).toFile();
		Path pathToPlace = Paths.get("output", fileName);
		sendRequest("PUT " + pathToPlace, clientToServer);
		Files.copy(expected.toPath(), clientToServer.getOutputStream());
		clientToServer.shutdownOutput();
		handler.run();
		File result = Paths.get(workingDir).resolve(pathToPlace).toFile();
		assertStreamHasBytes("OK".getBytes(), clientToServer.getInputStream());
		assertTrue(result.exists());
		assertArrayEquals(Files.readAllBytes(result.toPath()),
				Files.readAllBytes(expected.toPath()));
		result.delete();
		result.getParentFile().delete();
	}

	@Test
	void testRunInvalidMethod() throws IOException {
		sendRequest("InvalidMethod fakeFile", clientToServer);
		handler.run();
		assertStreamHasBytes("Invalid Method".getBytes(), clientToServer.getInputStream());
	}

	@ParameterizedTest
	@ValueSource(strings = { "", "''", "fdgsgfgd", "fgsf fgsf fsgf" })
	void testRunMalformedRequests(String request) throws IOException {
		sendRequest(request, clientToServer);
		handler.run();
		assertEquals(0, getServerResponse(clientToServer.getInputStream()).size());
	}

	@ParameterizedTest
	@ValueSource(strings = { "../src", "../../", "./../../", "TestData/../", "TestData/../../" })
	void testRunDirTraversalAttack(String resource) throws IOException {
		sendRequest("SHOW " + resource, clientToServer);
		handler.run();
		ByteArrayOutputStream expected = getExpectedShowDirResponse();
		assertStreamHasBytes(expected.toByteArray(), clientToServer.getInputStream());
	}

	private ByteArrayOutputStream getExpectedShowDirResponse() throws IOException {
		ByteArrayOutputStream expected = new ByteArrayOutputStream();
		expected.write("OK".getBytes());
		String dir = String.join(System.lineSeparator(), new File(workingDir).list());
		ByteBuffer buff = ByteBuffer.allocate(2);
		buff.putShort((short) dir.getBytes().length);
		expected.write(buff.array());
		expected.write(dir.getBytes());
		return expected;
	}

	private void sendRequest(String string, Socket sock) {
		try {
			DataOutputStream out = new DataOutputStream(sock.getOutputStream());
			out.writeUTF(string);
			out.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void assertStreamHasBytes(byte[] bytes, InputStream stream) {
		ByteArrayOutputStream result = getServerResponse(stream);
		assertArrayEquals(bytes, result.toByteArray());
	}

	private ByteArrayOutputStream getServerResponse(InputStream inStream) {
		DataInputStream in = new DataInputStream(new BufferedInputStream(inStream));
		ByteArrayOutputStream result = new ByteArrayOutputStream();
		try {
			if (in.available() > 0) result.write(in.readUTF().getBytes());
			while (in.available() > 0) {
				result.write(in.read());
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return result;
	}
}
