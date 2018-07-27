package fileTransferProgram;

import java.io.File;
import java.io.IOException;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

public class RequestHandler implements Runnable {

	private final Socket connection;
	private final String directory;

	public RequestHandler(Socket connection, String directory) {
		this.connection = connection;
		this.directory = directory;
	}

	@Override
	public void run() {
		String request = FtpIO.getLineFromSock(connection);
		if (request == null) return;
		String[] requestData = FtpIO.parseRequest(request);
		if (requestData == null || requestData.length != 2) return;
		File resource = resolvePath(directory, requestData[1]).toFile();
		if (requestData[0].equalsIgnoreCase("GET")) {
			doGet(resource);
		} else if (requestData[0].equalsIgnoreCase("PUT")) {
			doPut(resource);
		} else if (requestData[0].equalsIgnoreCase("SHOW")) {
			doShow(resource);
		} else if (requestData[0].equalsIgnoreCase("REMOVE")) {
			doRemove(resource);
		} else {
			FtpIO.sendLineOverSock("Invalid Method", connection);
		}
		FtpIO.closeConnection(connection);
	}

	private Path resolvePath(String dir, String requestedFile) {
		Path requestedPath = Paths.get(dir, requestedFile).toAbsolutePath().normalize();
		Path workingDirPath = Paths.get(dir).toAbsolutePath();
		if (requestedPath.startsWith(workingDirPath)) {
			return requestedPath;
		} else {
			return workingDirPath;
		}
	}

	private void doRemove(File resource) {
		if (resource != null && resource.exists()) {
			removeFile(resource);
			FtpIO.sendLineOverSock("OK", connection);
		} else {
			FtpIO.sendLineOverSock("File Not Found", connection);
		}
	}

	private void doShow(File resource) {
		if (resource != null && resource.isDirectory()) {
			FtpIO.sendLineOverSock("OK", connection);
			FtpIO.sendLineOverSock(String.join(System.lineSeparator(), resource.list()),
					connection);
		} else {
			FtpIO.sendLineOverSock("Could not find directory", connection);
		}
	}

	private void doPut(File resource) {
		if (resource != null) {
			try {
				resource.mkdirs();
				Files.copy(connection.getInputStream(), resource.toPath(),
						StandardCopyOption.REPLACE_EXISTING);
				FtpIO.sendLineOverSock("OK", connection);
			} catch (IOException e) {
				e.printStackTrace();
				FtpIO.sendLineOverSock("Errors putting file", connection);
				FtpIO.closeConnection(connection);
			}
		}
	}

	private void doGet(File resource) {
		if (resource != null && resource.isFile()) {
			FtpIO.sendLineOverSock("OK", connection);
			try {
				Files.copy(resource.toPath(), connection.getOutputStream());
			} catch (IOException e) {
				e.printStackTrace();
				FtpIO.sendLineOverSock("Error getting file", connection);
				FtpIO.closeConnection(connection);
			}
		} else {
			FtpIO.sendLineOverSock("File Not Found", connection);
		}
	}

	private void removeFile(File file) {
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
