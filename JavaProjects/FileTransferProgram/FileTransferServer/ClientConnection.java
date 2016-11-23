package FileTransferProgram.FileTransferServer;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.Socket;

/**
 * ClientConnection represents a connection to the client.
 */
final class ClientConnection implements Runnable {

	private final Socket CLIENT_SOCKET;
	private final File WORKING_DIRECTORY;
	private String Dir_Path;
	private DataInputStream INPUT;
	private DataOutputStream OUTPUT;

	public ClientConnection(Socket client, String directory) {
		this.CLIENT_SOCKET = client;
		this.WORKING_DIRECTORY = new File(directory);
		this.Dir_Path = WORKING_DIRECTORY.getAbsolutePath() + File.separator;
		try {
			this.INPUT = new DataInputStream(CLIENT_SOCKET.getInputStream());
			this.OUTPUT = new DataOutputStream(CLIENT_SOCKET.getOutputStream());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Starts listening over the client socket for commands from the client.
	 */
	@Override
	public void run() {
		System.out.println("Connection made to " + CLIENT_SOCKET.getInetAddress().toString());
		try {
			while (true) {
				String incoming;
				while ((incoming = INPUT.readUTF()) != null) {
					handleRequest(incoming.trim());
				}
			}
		} catch (IOException ex) {
			System.out.println(CLIENT_SOCKET.getInetAddress().toString() + " has discconected");
		}
	}

	/**
	 * calls the appropriate method for the given request.
	 *
	 * @param request
	 *            - String representing request from client
	 * 
	 */
	private void handleRequest(String request) {
		if (request.equalsIgnoreCase("!showFiles")) {
			displayFiles(WORKING_DIRECTORY.listFiles());
		} else if (request.startsWith("!openFolder") && isValid(request)) {
			openFolder(request.split(" ")[1]);
		} else if (request.startsWith("!delete") && isValid(request)) {
			prepareDeletion(request.split(" ")[1]);
		} else if (request.startsWith("!download") && isValid(request)) {
			prepareFileUpload(request.split(" ")[1]);
		} else if (request.startsWith("!uploading") && isValid(request)) {
			recieveFile(request.split(" ")[1]);
		}
	}

	/**
	 * Sends client the name of each file in a given array of files.
	 *
	 * @param listOfFiles
	 *            - array of files.
	 */
	private void displayFiles(File[] listOfFiles) {
		sendMessage("----------------------------------------");
		sendMessage("Files:");
		for (File file : listOfFiles) {
			if (file.isDirectory()) {
				sendMessage(file.getName() + "(Folder)");
			} else {
				sendMessage(file.getName());
			}
		}
		sendMessage("@END@");
	}

	/**
	 * Prepares the list of files requested from client, then calls the display
	 * files method for the list.
	 *
	 * @param fileName
	 *            - request from client of which folder to view
	 */
	private void openFolder(String fileName) {
		File folder = new File(Dir_Path + fileName);
		if (folder.isDirectory() && isSecure(fileName)) {
			displayFiles(folder.listFiles());
		} else {
			sendMessage("That folder does not exsist, check folder name");
			sendMessage("@END@");
		}
	}

	/**
	 * Prepares the files to delete requested from the user
	 *
	 * @param fileName
	 *            - String of file name.
	 */
	private void prepareDeletion(String fileName) {
		File file = new File(Dir_Path + fileName);
		if (file.exists() && isSecure(fileName)) {
			removeFile(file);
		}
	}

	/**
	 * recursively removes files and folders from working directory
	 *
	 * @param file
	 *            - file or folder to delete
	 */
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

	/**
	 * Prepares the file requested from the client to send over network
	 *
	 * @param fileName
	 *            - the request of which file the client wants
	 */
	private void prepareFileUpload(String fileName) {
		File file = new File(Dir_Path + fileName);
		if (file.isFile() && isSecure(fileName)) {
			sendFile(file);
		} else {
			try {
				OUTPUT.writeLong(0);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * Reads bytes from given file and sends those bytes over data output
	 * stream.
	 * 
	 * @param file
	 *            - file to send over stream
	 */
	private void sendFile(File file) {
		try (FileInputStream fis = new FileInputStream(file)) {
			OUTPUT.writeLong(file.length());
			int read;
			byte[] buff = new byte[1024];
			while ((read = fis.read(buff)) > 0) {
				OUTPUT.write(buff, 0, read);
			}
			OUTPUT.flush();
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}

	/**
	 * Receives bytes from data input stream and writes bytes to file.
	 * 
	 * @param fileName
	 *            - String representing name of the file to write bytes to.
	 */
	private void recieveFile(String fileName) {
		File file = new File(Dir_Path + fileName);
		try (FileOutputStream fos = new FileOutputStream(file)) {
			file.createNewFile();
			int read;
			byte[] buff = new byte[1024];
			long numOfBytes = INPUT.readLong();
			while (numOfBytes > 0) {
				read = INPUT.read(buff);
				numOfBytes -= read;
				fos.write(buff, 0, read);
			}
			System.out.println("File Recieved");
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}

	/**
	 * Sends String over data output stream.
	 * 
	 * @param m
	 *            - string to send
	 */
	private void sendMessage(String m) {
		try {
			OUTPUT.writeUTF(m);
			OUTPUT.flush();
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}

	/*
	 * returns True if the command is at least 2 words in length.
	 */
	private boolean isValid(String message) {
		return message.split(" ").length > 1;
	}

	/**
	 * checks to make sure the request does not escape working directory.
	 *
	 * @param r
	 *            request as a string
	 * @return - returns true if the request is valid and stays in working
	 *         directory
	 */
	private boolean isSecure(String r) {
		return !(r.contains("/..") || r.contains("\\.."));
	}

}
