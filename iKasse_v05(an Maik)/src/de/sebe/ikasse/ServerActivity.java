package de.sebe.ikasse;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;
import java.util.concurrent.ExecutionException;

import adapters.GridAdapterSpeisen;
import adapters.ListAdapterBedienungen;
import adapters.ListAdapterServerLog;
import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import constants.Constants;
import dialogs.ColorPickerDialog;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;
import network.AsyncTaskStartServer;
import network.Server;
import objects.Bedienung;
import objects.Speise;

public class ServerActivity extends Activity implements dialogs.ColorPickerDialog.OnColorChangedListener {
	@SuppressWarnings("unused")
	private Server server;

	private ArrayList<Bedienung> bedienungen = new ArrayList<Bedienung>();
	private ArrayList<Speise> speisen = new ArrayList<Speise>();
	private ArrayList<String> log_nachrichten = new ArrayList<String>();
	private ArrayList<String> log_ids = new ArrayList<String>();
	private ArrayList<String> log_ips = new ArrayList<String>();
	private ArrayList<String> log_zeiten = new ArrayList<String>();

	private RelativeLayout relServerAddBedienung, relServerAddSpeise, relServerLog, relServerBedienungenAnzeigen, relServerAddSpeiseFarbe;
	private ListView lvServerBedienungen, lvServerLog, lvServerBedienungenAnzeigen;
	private GridView gvServerSpeisen;
	private EditText etServerAddBedienungID, etServerAddBedienungName, etServerAddBedienungPasswort, etServerAddBedienungUmsatz, etServerAddSpeiseID, etServerAddSpeiseBezeichnung, etServerAddSpeisePreis, etServerAddSpeiseIp;
	private Button btServerAddBedienungFertig, btServerAddBedienungAbbrechen, btServerAddSpeiseFertig, btServerAddSpeiseAbbrechen;

	private SharedPreferences sharedPreferences;
	private NotificationManager notificationManager;
	public Vibrator vibrator;

	private boolean server_2_bedienung_wird_bearbeitet = false, server_3_speise_wird_bearbeitet = false;
	private int server_2_bedienung_wird_bearbeitet_list_position = 0, server_3_speise_wird_bearbeitet_list_position = 0;
	private int zwischenablage_farbe;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_server_1);
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

		notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
		notificationManager.cancelAll();
		vibrator = (Vibrator) getApplicationContext().getSystemService(Context.VIBRATOR_SERVICE);
	}

	private void server_start() {
		AsyncTaskStartServer asynctask = new AsyncTaskStartServer();
		asynctask.param_uebergeben(this);
		try {
			server = asynctask.execute("").get();
		} catch (InterruptedException e) {
			// Auto-generated catch block
			e.printStackTrace();
		} catch (ExecutionException e) {
			// Auto-generated catch block
			e.printStackTrace();
		}

		Toast.makeText(getApplicationContext(), "SERVER läuft...", Toast.LENGTH_SHORT).show();
		vibrator.vibrate(Constants.VIBRIERZEIT_SERVER_SERVER_GESTARTET);
		// notifyServerLaeuft(); TODO
	}

	public void server_1_weiter(View v) {
		setContentView(R.layout.layout_server_2);
		setTitle(getResources().getString(R.string.bedienungen_einrichten));

		lvServerBedienungen = (ListView) findViewById(R.id.lvServerBedienungen);
		relServerAddBedienung = (RelativeLayout) findViewById(R.id.relServerAddBedienung);
		etServerAddBedienungID = (EditText) findViewById(R.id.etServerAddBedienungID);
		etServerAddBedienungName = (EditText) findViewById(R.id.etServerAddBedienungName);
		etServerAddBedienungPasswort = (EditText) findViewById(R.id.etServerAddBedienungPasswort);
		etServerAddBedienungUmsatz = (EditText) findViewById(R.id.etServerAddBedienungUmsatz);
		btServerAddBedienungFertig = (Button) findViewById(R.id.btServerAddBedienungFertig);
		btServerAddBedienungAbbrechen = (Button) findViewById(R.id.btServerAddBedienungAbbrechen);

		bedienungen_einlesen();
		refresh_lvBedienungen();
	}

	public void server_2_weiter(View v) {
		bedienungen_speichern();

		setContentView(R.layout.layout_server_3);
		setTitle(getResources().getString(R.string.speisekarte_einrichten));

		gvServerSpeisen = (GridView) findViewById(R.id.gvServerSpeisekarte);
		relServerAddSpeise = (RelativeLayout) findViewById(R.id.relServerAddSpeise);
		etServerAddSpeiseID = (EditText) findViewById(R.id.etServerAddSpeiseID);
		etServerAddSpeiseBezeichnung = (EditText) findViewById(R.id.etServerAddSpeiseBezeichnung);
		etServerAddSpeisePreis = (EditText) findViewById(R.id.etServerAddSpeisePreis);
		etServerAddSpeiseIp = (EditText) findViewById(R.id.etServerAddSpeiseIp);
		relServerAddSpeiseFarbe = (RelativeLayout) findViewById(R.id.relServerAddSpeiseFarbe);
		btServerAddSpeiseFertig = (Button) findViewById(R.id.btServerAddSpeiseFertig);
		btServerAddSpeiseAbbrechen = (Button) findViewById(R.id.btServerAddSpeiseAbbrechen);

		speisen_einlesen();
		refresh_gvServerSpeisen();
	}

	public void server_3_weiter(View v) {
		speisen_speichern();

		setContentView(R.layout.layout_server_main);
		setTitle(getResources().getString(R.string.server_laeuft));
		relServerLog = (RelativeLayout) findViewById(R.id.relServerLog);
		lvServerLog = (ListView) findViewById(R.id.lvServerLog);
		relServerBedienungenAnzeigen = (RelativeLayout) findViewById(R.id.relServerBedienungenAnzeigen);
		lvServerBedienungenAnzeigen = (ListView) findViewById(R.id.lvServerBedienungenAnzeigen);
		server_start();
	}

	private void bedienungen_einlesen() {
		sharedPreferences = getSharedPreferences("Bedienungen", 0);
		int a = 0;
		String str;
		while (!(str = sharedPreferences.getString("speicherStringBedienung" + a, "")).equals("")) {
			try {
				bedienungen.add(new Bedienung(str));
			} catch (Exception e) {
			}
			a++;
		}
	}

	private void bedienungen_speichern() {
		sharedPreferences = getSharedPreferences("Bedienungen", 0);
		SharedPreferences.Editor editor = sharedPreferences.edit();
		editor.clear();
		for (int a = 0; a < bedienungen.size(); a++) {
			editor.putString("speicherStringBedienung" + a, bedienungen.get(a).getSpeicherString());
		}
		editor.commit();
	}

	private void speisen_einlesen() {
		sharedPreferences = getSharedPreferences("Speisen", 0);
		int a = 0;
		String str;
		while (!(str = sharedPreferences.getString("speicherStringSpeise" + a, "")).equals("")) {
			try {
				speisen.add(new Speise(str));
			} catch (Exception e) {
			}
			a++;
		}
	}

	private void speisen_speichern() {
		sharedPreferences = getSharedPreferences("Speisen", 0);
		SharedPreferences.Editor editor = sharedPreferences.edit();
		editor.clear();
		for (int a = 0; a < speisen.size(); a++) {
			editor.putString("speicherStringSpeise" + a, speisen.get(a).getSpeicherString());
		}
		editor.commit();
	}

	private void refresh_lvBedienungen() {
		ListAdapterBedienungen adapter = new ListAdapterBedienungen(getApplicationContext(), bedienungen);
		lvServerBedienungen.setAdapter(adapter);
		lvServerBedienungen.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				server_2_bedienung_wird_bearbeitet = true;
				server_2_bedienung_wird_bearbeitet_list_position = position;
				btServerAddBedienungFertig.setText(getResources().getString(R.string.fertig));
				btServerAddBedienungAbbrechen.setText(getResources().getString(R.string.loeschen));
				btServerAddBedienungAbbrechen.setTextColor(Color.rgb(155, 0, 0));
				etServerAddBedienungID.setText(String.valueOf(bedienungen.get(position).getID()));
				etServerAddBedienungName.setText(bedienungen.get(position).getName());
				etServerAddBedienungPasswort.setText(bedienungen.get(position).getPasswort());
				etServerAddBedienungUmsatz.setText(String.valueOf(bedienungen.get(position).getUmsatz()));
				relServerAddBedienung.setVisibility(View.VISIBLE);
			}
		});
	}

	private void refresh_gvServerSpeisen() {
		GridAdapterSpeisen adapter = new GridAdapterSpeisen(getApplicationContext(), speisen, true);
		gvServerSpeisen.setAdapter(adapter);
		gvServerSpeisen.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				server_3_speise_wird_bearbeitet = true;
				server_3_speise_wird_bearbeitet_list_position = position;
				btServerAddSpeiseFertig.setText(getResources().getString(R.string.fertig));
				btServerAddSpeiseAbbrechen.setText(getResources().getString(R.string.loeschen));
				btServerAddSpeiseAbbrechen.setTextColor(Color.rgb(155, 0, 0));
				etServerAddSpeiseID.setText(String.valueOf(speisen.get(position).getID()));
				etServerAddSpeiseBezeichnung.setText(speisen.get(position).getBezeichnung());
				etServerAddSpeisePreis.setText(String.valueOf(speisen.get(position).getPreis()));
				etServerAddSpeiseIp.setText(speisen.get(position).getBestellung_bei_Ip());
				relServerAddSpeiseFarbe.setBackgroundColor(speisen.get(position).getFarbe());
				relServerAddSpeise.setVisibility(View.VISIBLE);
			}
		});
	}

	public void btServerAddBedienungHinzufuegen(View v) {
		etServerAddBedienungID.setText("TODO"); // TODO Automatisch mit IDs füllen
		etServerAddBedienungName.setText("");
		etServerAddBedienungPasswort.setText("");
		etServerAddBedienungUmsatz.setText("0");
		btServerAddBedienungFertig.setText(getResources().getString(R.string.hinzufuegen));
		btServerAddBedienungAbbrechen.setText(getResources().getString(R.string.abbrechen));
		btServerAddBedienungAbbrechen.setTextColor(Color.rgb(0, 0, 0));

		relServerAddBedienung.setVisibility(View.VISIBLE);
	}

	public void btServerAddBedienungFertig(View v) {
		// TODO Überprüfen, ob alle EditText ausgefüllt sind

		if (server_2_bedienung_wird_bearbeitet) {
			try {
				int id = Integer.parseInt(etServerAddBedienungID.getText().toString());
				double umsatz = Double.parseDouble(etServerAddBedienungUmsatz.getText().toString());
				bedienungen.set(server_2_bedienung_wird_bearbeitet_list_position, new Bedienung(id, etServerAddBedienungName.getText().toString(), etServerAddBedienungPasswort.getText().toString(), umsatz));
				server_2_bedienung_wird_bearbeitet = false;
			} catch (Exception e) {
				// TODO Konvert-Fehler anzeigen
			}
		} else {
			try {
				int id = Integer.parseInt(etServerAddBedienungID.getText().toString());
				double umsatz = Double.parseDouble(etServerAddBedienungUmsatz.getText().toString());
				bedienungen.add(new Bedienung(id, etServerAddBedienungName.getText().toString(), etServerAddBedienungPasswort.getText().toString(), umsatz));
			} catch (Exception e) {
				// TODO Konvert-Fehler anzeigen
			}
		}

		refresh_lvBedienungen();
		relServerAddBedienung.setVisibility(View.GONE);
	}

	public void btServerAddBedienungAbbrechen(View v) {
		if (server_2_bedienung_wird_bearbeitet) {
			bedienungen.remove(server_2_bedienung_wird_bearbeitet_list_position);
			server_2_bedienung_wird_bearbeitet = false;
			refresh_lvBedienungen();
		}
		relServerAddBedienung.setVisibility(View.GONE);
	}

	public void btServerAddSpeiseHinzufuegen(View v) {
		etServerAddSpeiseID.setText(""); // TODO Automatisch mit IDs füllen
		etServerAddSpeiseBezeichnung.setText("");
		etServerAddSpeisePreis.setText("");
		btServerAddSpeiseFertig.setText(getResources().getString(R.string.hinzufuegen));
		btServerAddSpeiseAbbrechen.setText(getResources().getString(R.string.abbrechen));
		btServerAddSpeiseAbbrechen.setTextColor(Color.rgb(0, 0, 0));

		relServerAddSpeise.setVisibility(View.VISIBLE);
	}

	public void btServerAddSpeiseFertig(View v) {
		// TODO Überprüfen, ob alle EditText ausgefüllt sind

		if (server_3_speise_wird_bearbeitet) {
			try {
				int id = Integer.parseInt(etServerAddSpeiseID.getText().toString());
				double preis = Double.parseDouble(etServerAddSpeisePreis.getText().toString());
				String bestellung_bei_Ip = etServerAddSpeiseIp.getText().toString();
				int color = ((ColorDrawable) relServerAddSpeiseFarbe.getBackground()).getColor();
				speisen.set(server_3_speise_wird_bearbeitet_list_position, new Speise(id, etServerAddSpeiseBezeichnung.getText().toString(), preis, bestellung_bei_Ip, color, 0));
				server_3_speise_wird_bearbeitet = false;
				// TODO ..................... Farbe noch richtg machen
			} catch (Exception e) {
				// TODO Konvert-Fehler anzeigen
			}
		} else {
			try {
				int id = Integer.parseInt(etServerAddSpeiseID.getText().toString());
				double preis = Double.parseDouble(etServerAddSpeisePreis.getText().toString());
				String bestellung_bei_Ip = etServerAddSpeiseIp.getText().toString();
				int color = ((ColorDrawable) relServerAddSpeiseFarbe.getBackground()).getColor();
				speisen.add(new Speise(id, etServerAddSpeiseBezeichnung.getText().toString(), preis, bestellung_bei_Ip, color, 0));
				// TODO ..................... Farbe noch richtg machen
			} catch (Exception e) {
				// TODO Konvert-Fehler anzeigen
			}
		}

		refresh_gvServerSpeisen();
		relServerAddSpeise.setVisibility(View.GONE);
	}

	public void btServerAddSpeiseAbbrechen(View v) {
		if (server_3_speise_wird_bearbeitet) {
			speisen.remove(server_3_speise_wird_bearbeitet_list_position);
			server_3_speise_wird_bearbeitet = false;
			refresh_gvServerSpeisen();
		}
		relServerAddSpeise.setVisibility(View.GONE);
	}

	public void log_add(int ID, String IP, String msg) {
		log_nachrichten.add(msg);
		log_ids.add(String.valueOf(ID));
		log_ips.add(IP);
		Calendar cal = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss", Locale.GERMAN);
		log_zeiten.add(sdf.format(cal.getTime()));
		if (relServerLog.getVisibility() == View.VISIBLE) {
			refresh_lvLog();
		}
	}

	public void linServerAddSpeiseFarbe(View v) {
		new ColorPickerDialog(this, this, ((ColorDrawable) relServerAddSpeiseFarbe.getBackground()).getColor()).show();
	}

	private void notifyServerLaeuft() {
		// NotificationCompat.InboxStyle style = new NotificationCompat.InboxStyle();
		// style.setBigContentTitle("iKasse");
		// style.addLine("Server läuft!");
		// style.addLine("Server läuft!");
		// style.addLine("Server läuft!");

		NotificationCompat.Builder builder = new NotificationCompat.Builder(this);
		builder.setAutoCancel(true);
		builder.setOngoing(true); // sperren
		builder.setContentTitle("iKasse");
		builder.setContentText("Server läuft...");
		builder.setSmallIcon(R.drawable.ic_launcher);
		// if (getSharedPreferences("EINSTELLUNGEN", 0).getBoolean("Erweiterte_Benachrichtigungen", true)) {
		// builder.setStyle(style);
		// }

		Intent resultIntent = new Intent(this, MainActivity.class);
		TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
		stackBuilder.addParentStack(MainActivity.class);
		stackBuilder.addNextIntent(resultIntent);
		PendingIntent resultPendingIntent = stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);
		builder.setContentIntent(resultPendingIntent);

		Notification notification = builder.build();
		NotificationManager manager = (NotificationManager) this.getSystemService(NOTIFICATION_SERVICE);

		manager.notify(0, notification);
	}

	@Override
	public void colorChanged(int color) {
		relServerAddSpeiseFarbe.setBackgroundColor(color);
	}

	public ArrayList<Bedienung> getBedienungen() {
		return bedienungen;
	}

	public ArrayList<Speise> getSpeisen() {
		return speisen;
	}

	public void bedienungsID_erhoehe_Umsatz(int bedienungsId, double menge) {
		for (Bedienung bed : bedienungen) {
			if (bed.getID() == bedienungsId) {
				bed.erhoeheUmsatz(menge);
				break;
			}
		}
		if (relServerBedienungenAnzeigen.getVisibility() == View.VISIBLE) {
			refresh_lvServerBedienungenAnzeigen();
		}
	}

	public void btServerLogAufrufen(View v) {
		refresh_lvLog();
		relServerLog.setVisibility(View.VISIBLE);
		setTitle(getResources().getString(R.string.log));
	}

	public void btServerLogZurueck(View v) {
		relServerLog.setVisibility(View.GONE);
		setTitle(getResources().getString(R.string.server_laeuft));
	}

	public void btServerBedienungenAnzeigen(View v) {
		refresh_lvServerBedienungenAnzeigen();
		relServerBedienungenAnzeigen.setVisibility(View.VISIBLE);
		setTitle("Bedienungen");
	}

	public void btServerBedienungenAnzeigenZurueck(View v) {
		relServerBedienungenAnzeigen.setVisibility(View.GONE);
		setTitle(getResources().getString(R.string.server_laeuft));
	}

	public void btServerAusschalten(View v) {
		// TODO
	}

	private void refresh_lvLog() {
		final ListAdapterServerLog adapter = new ListAdapterServerLog(getApplicationContext(), log_nachrichten, log_ids, log_ips, log_zeiten);
		runOnUiThread(new Runnable() {
			public void run() {
				lvServerLog.setAdapter(adapter);
			}
		});

		lvServerLog.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

			}
		});

		lvServerLog.setOnItemLongClickListener(new OnItemLongClickListener() {
			@Override
			public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {

				return true;
			}
		});
	}

	private void refresh_lvServerBedienungenAnzeigen() {
		final ListAdapterBedienungen adapter = new ListAdapterBedienungen(getApplicationContext(), bedienungen);
		runOnUiThread(new Runnable() {
			public void run() {
				lvServerBedienungenAnzeigen.setAdapter(adapter);
			}
		});
	}

	public void btServerAddSpeiseCopy(View v) {
		zwischenablage_farbe = ((ColorDrawable) relServerAddSpeiseFarbe.getBackground()).getColor();
	}

	public void btServerAddSpeisePaste(View v) {
		relServerAddSpeiseFarbe.setBackgroundColor(zwischenablage_farbe);
	}

	@Override
	public void onBackPressed() {

	}

	// @Override
	// public boolean onCreateOptionsMenu(Menu menu) {
	// getMenuInflater().inflate(R.menu.menu_server, menu);
	// return true;
	// }
	//
	// @Override
	// public boolean onOptionsItemSelected(MenuItem item) {
	// int id = item.getItemId();
	// if (id == R.id.action_einstellungen) {
	// return true;
	// }
	// return super.onOptionsItemSelected(item);
	// }
}
