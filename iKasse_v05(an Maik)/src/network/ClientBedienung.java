package network;

import java.io.DataOutputStream;
import java.net.Socket;

import de.sebe.ikasse.BedienungActivity;

public class ClientBedienung {
	private BedienungActivity activity;

	private Socket socket;
	// private DataInputStream console;
	private DataOutputStream streamOut;
	private ClientBedienungThread client;
	private String serverName;
	private int serverPort;

	public ClientBedienung(String serverName, int serverPort, BedienungActivity activity) throws Exception {
		this.serverName = serverName;
		this.serverPort = serverPort;
		this.activity = activity;

		connect();
	}

	public void connect() throws Exception {
		socket = new Socket(serverName, serverPort);
		open();
	}

	public void send(String msg) {
		try {
			streamOut.writeUTF(msg);
			streamOut.flush();
		} catch (Exception e) {
			if (streamOut != null) {
				try {
					close();
				} catch (Exception e1) {
					// TODO Auto-generated catch block
				}
			}

		}
	}

	public void handle(String msg) {
		activity.handle(msg);
	}

	public void open() throws Exception {
		streamOut = new DataOutputStream(socket.getOutputStream());
		client = new ClientBedienungThread(this, socket);

	}

	public void close() throws Exception {

		if (streamOut != null)
			streamOut.close();
		if (socket != null)
			socket.close();
		client.close();
		client.stop();
	}
}