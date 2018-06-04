package network;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import android.widget.Toast;
import constants.Constants;
import de.sebe.ikasse.ServerActivity;
import objects.Bedienung;
import objects.Speise;

public class Server implements Runnable {
	private ServerActivity serverActivity;

	private ServerThread clients[] = new ServerThread[200];
	private ServerSocket server = null;
	private Thread thread = null;
	private int clientCount = 0;

	private AsyncTaskServerSammelbestellung sammelbestellung;
	
	public Server(int port, ServerActivity serverActivity) {
		this.serverActivity = serverActivity;

		try {
			// System.out.println("Binding to port " + port + ", please wait ...");
			server = new ServerSocket(port);
			// System.out.println("Server started: " + server);
			start();
		} catch (IOException ioe) {
			// System.out.println("Can not bind to port " + port + ": " + ioe.getMessage());
		}
	}

	public void run() {
		while (thread != null) {
			try {
				// System.out.println("Waiting for a client ...");
				addThread(server.accept());
			} catch (IOException ioe) {
				// System.out.println("Server accept error: " + ioe);
				stop();
			}
		}
	}

	public void start() {
		if (thread == null) {
			thread = new Thread(this);
			thread.start();
		}
	}

	@SuppressWarnings("deprecation")
	public void stop() {
		if (thread != null) {
			thread.stop();
			thread = null;
		}
	}

	private int findClient(int ID) {
		for (int i = 0; i < clientCount; i++)
			if (clients[i].getID() == ID)
				return i;
		return -1;
	}

	public synchronized void handle(int ID, String IP, String input) {
		try {
			log_add(ID, IP, input);
		} catch (Exception e) {

		}

		try {
			String[] args = input.split(Constants.trennZeichenkette);
			int typ = Integer.parseInt(args[0]);
			switch (typ) {
			case Constants.REQUEST_ANMELDEDATEN_BEDIENUNG:
				boolean ok = false;
				int bedienungsID = 0;
				String bedienungsName = "";
				for (Bedienung bed : serverActivity.getBedienungen()) {
					if (bed.getName().equals(args[1]) && bed.getPasswort().equals(args[2])) {
						ok = true;
						bedienungsID = bed.getID();
						bedienungsName = bed.getName();
					}
				}
				if (ok) { // Anmedlung erfolgreich
					clients[findClient(ID)].send(Constants.RESPONSE_ANMELDEDATEN_BEDIENUNG_OK + Constants.trennZeichenkette + bedienungsID + Constants.trennZeichenkette + bedienungsName);
				} else { // Anmeldung fehlgeschlagen
					clients[findClient(ID)].send(String.valueOf(Constants.RESPONSE_ANMELDEDATEN_BEDIENUNG_FEHLGESCHLAGEN));
				}
				break;
			case Constants.REQUEST_KASSE_ANMELDEN:
				clients[findClient(ID)].send(String.valueOf(Constants.RESPONSE_KASSE_ANGEMELDET));
				break;
			case Constants.REQUEST_SPEISEKARTE:
				String str = String.valueOf(Constants.RESPONSE_SPEISEKARTE);
				for (Speise speise : serverActivity.getSpeisen()) {
					str += Constants.trennZeichenkette + speise.getSpeicherString();
				}
				clients[findClient(ID)].send(str);
				break;
			case Constants.REQUEST_BESTELLUNG_SERVER:
				// -------------------------------------------------
				sammelbestellung = new AsyncTaskServerSammelbestellung();
				sammelbestellung.init(serverActivity, this, ID, args);
				sammelbestellung.execute();

				// --------------------------------------------------
				// clients[findClient(ID)].send(String.valueOf(Constants.RESPONSE_BESTELLUNG_SERVER_OK));
				break;
			case Constants.RESPONSE_BESTELLUNG_KASSE_OK:
				if(sammelbestellung != null) {
					sammelbestellung.setWartenAufAntwortKasse(false);	
				}
				break;
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void sammelbestellung_abgearbeitet(int ID_client) {
		clients[findClient(ID_client)].send(String.valueOf(Constants.RESPONSE_BESTELLUNG_SERVER_OK));
	}

	public void sammelbestellung_abarbeiten_nicht_moeglich(int ID_client) {
		clients[findClient(ID_client)].send(String.valueOf(Constants.RESPONSE_BESTELLUNG_SERVER_FEHLGESCHLAGEN));
	}

	@SuppressWarnings("deprecation")
	public synchronized void remove(int ID) {
		int pos = findClient(ID);
		if (pos >= 0) {
			ServerThread toTerminate = clients[pos];
			// System.out.println("Removing client thread " + ID + " at " + pos);
			if (pos < clientCount - 1)
				for (int i = pos + 1; i < clientCount; i++)
					clients[i - 1] = clients[i];
			clientCount--;
			try {
				toTerminate.close();
			} catch (IOException ioe) {
				// System.out.println("Error closing thread: " + ioe);
			}
			try {
				toTerminate.stop();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	private void addThread(Socket socket) {
		if (clientCount < clients.length) {
			// System.out.println("Client accepted: " + socket);
			clients[clientCount] = new ServerThread(this, socket);
			try {
				clients[clientCount].open();
				clients[clientCount].start();
				clientCount++;
			} catch (IOException ioe) {
				// System.out.println("Error opening thread: " + ioe);
			}
		} else {
			// System.out.println("Client refused: maximum " + clients.length + " reached.");
		}
	}

	public void log_add(int ID, String IP, String msg) {
		serverActivity.log_add(ID, IP, msg);
	}

	public boolean sende_an_IP(String ip, String msg) {
		for (int a = 0; a < clients.length; a++) {
			if (clients[a] != null && clients[a].getIP().equals(ip)) {
				try {
					clients[a].send(msg);
				} catch (Exception e) {
					return false;
				}
				return true;
			}
		}
		serverActivity.log_add(0, "", "Es wurde kein client gefunden, der nicht null war und dessen IP gepasset hat");
		return false;
	}
}