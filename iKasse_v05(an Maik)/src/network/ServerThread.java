package network;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class ServerThread extends Thread {
	private Server server = null;
	private Socket socket = null;
	private int ID = -1;
	private DataInputStream streamIn = null;
	private DataOutputStream streamOut = null;

	public ServerThread(Server server, Socket socket) {
		super();
		this.server = server;
		this.socket = socket;
		ID = socket.getPort();
	}

	@SuppressWarnings("deprecation")
	public void send(String msg) {
		try {
			streamOut.writeUTF(msg);
			streamOut.flush();
			server.log_add(ID, socket.getLocalAddress().toString(), msg);
		} catch (IOException ioe) {
			// System.out.println(ID + " ERROR sending: " + ioe.getMessage());
			server.remove(ID);
			stop();
		}
	}

	public int getID() {
		return ID;
	}

	public String getIP() {
		String ret = socket.getInetAddress().toString();
		if (ret.substring(0, 1).equals("/")) {
			ret = ret.substring(1, ret.length());
		}
		return ret;
	}

	@SuppressWarnings("deprecation")
	public void run() {
		// System.out.println("Server Thread " + ID + " running.");
		while (true) {
			try {
				if (socket != null && socket.getInetAddress() != null) {
					server.handle(ID, socket.getInetAddress().toString(), streamIn.readUTF());
				}
			} catch (IOException ioe) {
				// System.out.println(ID + " ERROR reading: " + ioe.getMessage());
				server.remove(ID);
				try {
					stop();
				} catch (Exception e) {

				}
			}
		}
	}

	public void open() throws IOException {
		streamIn = new DataInputStream(new BufferedInputStream(socket.getInputStream()));
		streamOut = new DataOutputStream(new BufferedOutputStream(socket.getOutputStream()));
	}

	public void close() throws IOException {
		if (socket != null)
			socket.close();
		if (streamIn != null)
			streamIn.close();
		if (streamOut != null)
			streamOut.close();
	}
}