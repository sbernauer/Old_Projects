package network;

import java.util.ArrayList;

import android.os.AsyncTask;
import constants.Constants;
import de.sebe.ikasse.ServerActivity;
import objects.Bestellung;
import objects.Speise;

public class AsyncTaskServerSammelbestellung extends AsyncTask<Void, Void, Void> {
	private ServerActivity serverActivity;
	private Server server;

	private ArrayList<Bestellung> bestellungen;
	private ArrayList<Speise> speisen;

	private int ID_client, bedienungs_ID;
	private String bedienungs_Name, tisch;
	private volatile boolean wartenAufAntwortKasse = false;

	public void init(ServerActivity serverActivity, Server server, int ID_client, String[] args_von_client) {
		this.serverActivity = serverActivity;
		this.server = server;

		speisen = serverActivity.getSpeisen();
		bestellungen = new ArrayList<Bestellung>();

		this.ID_client = ID_client;
		bedienungs_ID = Integer.parseInt(args_von_client[1]);
		bedienungs_Name = args_von_client[2];
		tisch = args_von_client[3];

		int a = 4; // 0 => REQUEST_BESTELLUNG_SERVER; 1 => ID Bedienung; 2 => Name Bedienung; 3=> Tisch; => Ab 4 gehts los mit den Speisen
		while (a < args_von_client.length - 1) {
			int anzahl = Integer.parseInt(args_von_client[a]);
			int speise_ID = Integer.parseInt(args_von_client[a + 1]);
			double einzelPreis = 0;
			String bestellen_bei_IP = "";
			for (Speise speise : speisen) {
				if (speise.getID() == speise_ID) {
					einzelPreis = speise.getPreis();
					bestellen_bei_IP = speise.getBestellung_bei_Ip();
					break;
				}
			}
			double gesamtPreis = anzahl * einzelPreis;
			if (!bestellen_bei_IP.equals("")) {
				bestellungen.add(new Bestellung(bedienungs_ID, bedienungs_Name, tisch, anzahl, speise_ID, bestellen_bei_IP, gesamtPreis));
			}
			a += 2;
		}
	}

	protected void onPostExecute() {

	}

	@Override
	protected Void doInBackground(Void... params) {

		serverActivity.log_add(0, "", "Sammelbestellung wird angefangen abzuarbeiten...");
		boolean fail = false;
		boolean sendenErfolgreich = false;
		for (int a = 0; a < bestellungen.size(); a++) {
			wartenAufAntwortKasse = true;
			try {
				sendenErfolgreich = server.sende_an_IP(bestellungen.get(a).getBestellen_bei_IP(), Constants.REQUEST_BESTELLUNG_KASSE + Constants.trennZeichenkette + bedienungs_ID + Constants.trennZeichenkette + bedienungs_Name + Constants.trennZeichenkette + tisch + Constants.trennZeichenkette + bestellungen.get(a).getAnzahl() + Constants.trennZeichenkette + bestellungen.get(a).getSpeise_ID());
			} catch (Exception e) {
				 serverActivity.log_add(0, "", "Sammelbestellung bei SpeiseID = " + bestellungen.get(a).getSpeise_ID() + " fehlgeschlagen im catch\n" + e.getMessage());
				server.sammelbestellung_abarbeiten_nicht_moeglich(ID_client);
				fail = true;
				break;
			}
			if (!sendenErfolgreich) {
				// serverActivity.log_add(0, "", "Senden nicht erfolgreich");
				server.sammelbestellung_abarbeiten_nicht_moeglich(ID_client);
				fail = true;
				break;
			}
			long starttime = System.currentTimeMillis();
			while (((System.currentTimeMillis() - starttime) < 5000) & wartenAufAntwortKasse) {
			}
			if (!wartenAufAntwortKasse) { // Bestellung erfolgreich
				serverActivity.bedienungsID_erhoehe_Umsatz(bedienungs_ID, bestellungen.get(a).getGesamtPreis());
			} else { // Zeit abgelaufen
				serverActivity.log_add(0, "", "Sammelbestellung bei SpeiseID = " + bestellungen.get(a).getSpeise_ID() + " fehlgeschlagen aufgrund abgelaufener Zeit");
				server.sammelbestellung_abarbeiten_nicht_moeglich(ID_client);
				fail = true;
				break;
			}
		}
		if (!fail) {
//			serverActivity.log_add(0, "", "Sammelbestellung fertig abgearbeitet");
			server.sammelbestellung_abgearbeitet(ID_client);
		}
		return null;
	}

	public void setWartenAufAntwortKasse(boolean wartenAufAntwortKasse) {
		this.wartenAufAntwortKasse = wartenAufAntwortKasse;
	}
}