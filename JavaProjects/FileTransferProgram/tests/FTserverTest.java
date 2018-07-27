package tests;

import static org.junit.jupiter.api.Assertions.*;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ConnectException;
import java.net.Socket;
import java.net.UnknownHostException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import fileTransferProgram.FTserver;

class FTserverTest {
	private static FTserver server;

	@BeforeEach
	void before() {
		server = new FTserver("dir");
	}

	@Test
	void testStart() throws UnknownHostException, IOException, InterruptedException {
		server.start(80);
		Socket client = new Socket("127.0.0.1", 80);
		DataOutputStream out = new DataOutputStream(client.getOutputStream());
		out.writeUTF("SHOW /");
		out.flush();
		Thread.sleep(100);
		assertTrue(client.getInputStream().available() > 0);
		client.close();
		server.shutDown();
	}

	@Test
	void testStartInvalidPortNum() {
		server.start(-1);
		assertThrows(ConnectException.class, () -> new Socket("127.0.0.1", 80));
		server.start(65536);
		assertThrows(ConnectException.class, () -> new Socket("127.0.0.1", 80));
	}

	@Test
	void testShutDown() throws InterruptedException {
		server.start(80);
		Thread.sleep(100);
		server.shutDown();
		assertThrows(ConnectException.class, () -> new Socket("127.0.0.1", 80));
	}

}
