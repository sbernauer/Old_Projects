package network;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.Socket;

public class ClientKasseThread extends Thread {
	private Socket socket = null;
	private ClientKasse client = null;
	private DataInputStream streamIn = null;

	public ClientKasseThread(ClientKasse client, Socket socket) {
		this.client = client;
		this.socket = socket;
		open();
		start();
	}

	public void open() {
		try {
			streamIn = new DataInputStream(socket.getInputStream());
		} catch (IOException ioe) {
			System.out.println("Error getting input stream: " + ioe);
//			client.stop();
		}
	}

	public void close() {
		try {
			if (streamIn != null)
				streamIn.close();
		} catch (IOException ioe) {
			System.out.println("Error closing input stream: " + ioe);
		}
	}

	public void run() {
		while (true) {
			try {
				client.handle(streamIn.readUTF());
			} catch (IOException ioe) {
				System.out.println("Listening error: " + ioe.getMessage());
//				client.stop();
			}
		}
	}
}
