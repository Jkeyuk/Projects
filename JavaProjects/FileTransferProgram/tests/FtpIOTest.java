package tests;

import static org.junit.jupiter.api.Assertions.*;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import fileTransferProgram.FtpIO;

class FtpIOTest {

	private static ServerSocket server;
	private static Socket client;
	private static Socket serverToClient;

	@BeforeAll
	static void before() throws UnknownHostException, IOException {
		server = new ServerSocket(80);
		client = new Socket("127.0.0.1", 80);
		serverToClient = server.accept();
	}

	@AfterAll
	static void after() throws IOException {
		client.close();
		serverToClient.close();
		server.close();
	}

	@Test
	void testSendLineOverSock() throws IOException {
		FtpIO.sendLineOverSock("Test Line", serverToClient);
		String result = DataInputStream.readUTF(new DataInputStream(client.getInputStream()));
		assertEquals("Test Line", result);
	}

	@Test
	void testSendLineOverSockNullInputs() throws IOException {
		FtpIO.sendLineOverSock(null, serverToClient);
		assertEquals(0, client.getInputStream().available());
		FtpIO.sendLineOverSock("Test Line", null);
		assertEquals(0, client.getInputStream().available());
	}

	@Test
	void testGetLineFromSock() throws IOException {
		DataOutputStream out = new DataOutputStream(client.getOutputStream());
		out.writeUTF("Test Line");
		out.flush();
		String result = FtpIO.getLineFromSock(serverToClient);
		assertEquals("Test Line", result);
	}

	@Test
	void testGetLineFromNullSock() throws IOException {
		String result = FtpIO.getLineFromSock(null);
		assertNull(result);
	}

	@ParameterizedTest
	@CsvSource({ "'GET file1.txt', GET, file1.txt", "'  PUT   file1.txt  ', PUT, file1.txt" })
	void testParseRequest(String request, String expMeth, String expRes) {
		String[] result = FtpIO.parseRequest(request);
		assertArrayEquals(new String[] { expMeth, expRes }, result);
	}

	@Test
	void testParseRequestNullInput() {
		String[] result = FtpIO.parseRequest(null);
		assertNull(result);
	}

	@ParameterizedTest
	@ValueSource(strings = { "", "GET", "GET file now" })
	void testParseRequestMalformed(String request) {
		String[] result = FtpIO.parseRequest(request);
		assertNull(result);
	}

}
