package fileTransferProgram;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.SocketException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class FTserver {
	private String workingDir;
	private ExecutorService exec;
	private ServerSocket server;

	public FTserver(String workingDir) {
		this.workingDir = workingDir;
		this.exec = Executors.newCachedThreadPool();
	}

	public void start(int port) {
		if (port < 0 || port > 65535) return;
		exec.execute(() -> {
			try {
				server = new ServerSocket(port);
				while (true) {
					exec.execute(new RequestHandler(server.accept(), workingDir));
				}
			} catch (SocketException e) {
			} catch (IOException e) {
				e.printStackTrace();
			}
		});
	}

	public void shutDown() {
		try {
			if (server != null) server.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		exec.shutdownNow();
	}
}
