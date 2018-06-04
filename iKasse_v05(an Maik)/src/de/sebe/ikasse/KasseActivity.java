package de.sebe.ikasse;

import java.util.ArrayList;

import adapters.ExpandedGridView;
import adapters.GridAdapterBestellungKasse;
import android.R.color;
import android.app.Activity;
import android.content.Context;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Vibrator;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;
import constants.Constants;
import network.AsyncTaskStartClientKasse;
import network.ClientKasse;
import objects.BestellungAnzeigeKasse;
import objects.Speise;

public class KasseActivity extends Activity {
	public ClientKasse client;

	private ArrayList<Speise> speisen = new ArrayList<Speise>();
	private ArrayList<BestellungAnzeigeKasse> bestellungen_alt = new ArrayList<BestellungAnzeigeKasse>();
	private ArrayList<BestellungAnzeigeKasse> bestellungen_neu = new ArrayList<BestellungAnzeigeKasse>();

	public RelativeLayout relKasseFehler;
	public TextView tvKasseFehler;
	public Button btKasseFehlerNochmal;
	private ExpandedGridView egvKasseNeueBestellungen, egvKasseAlteBestellungen;
	private ToggleButton tbKasseSound, tbKasseVibrieren;

	private MediaPlayer mpTon_neue_Bestellung;

	public Vibrator vibrator;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		try {
			requestWindowFeature(Window.FEATURE_NO_TITLE);
			getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		} catch (Exception e) {

		}
		setContentView(R.layout.layout_kasse_main);
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

		relKasseFehler = (RelativeLayout) findViewById(R.id.relKasseFehler);
		tvKasseFehler = (TextView) findViewById(R.id.tvKasseFehler);
		btKasseFehlerNochmal = (Button) findViewById(R.id.btKasseFehlerNochmal);
		egvKasseNeueBestellungen = (ExpandedGridView) findViewById(R.id.egvKasseNeueBestellungen);
		egvKasseAlteBestellungen = (ExpandedGridView) findViewById(R.id.egvKasseAlteBestellungen);
		tbKasseSound = (ToggleButton) findViewById(R.id.tbKasseSound);
		tbKasseVibrieren = (ToggleButton) findViewById(R.id.tbKasseVibrieren);

		vibrator = (Vibrator) getApplicationContext().getSystemService(Context.VIBRATOR_SERVICE);
		mpTon_neue_Bestellung = MediaPlayer.create(getApplicationContext(), R.raw.ton_neue_bestellung);

		kasse_anmelden();
	}

	private void kasse_anmelden() {
		relKasseFehler.setVisibility(View.VISIBLE);
		tvKasseFehler.setText(getResources().getString(R.string.anmeldung_erfolgt));
		btKasseFehlerNochmal.setVisibility(View.GONE);
		AsyncTaskStartClientKasse asynctask = new AsyncTaskStartClientKasse();
		asynctask.param_uebergeben(this);
		try {
			client = asynctask.execute("").get();
		} catch (Exception e) {
			relKasseFehler.setVisibility(View.VISIBLE);
			tvKasseFehler.setText("FEHLER Error:\n" + e.getMessage() + "\n" + e.toString() + "\n" + e.getLocalizedMessage());
			btKasseFehlerNochmal.setVisibility(View.VISIBLE);
			vibrator.vibrate(Constants.VIBRIERZEIT_KASSE_ANMELDUNG_FEHLGESCHLAGEN);
		}
		if (client != null) {
			send(String.valueOf(Constants.REQUEST_KASSE_ANMELDEN));
		}
	}

	public void handle(String msg) {
		try {
			final String[] args = msg.split(Constants.trennZeichenkette);
			int typ = Integer.parseInt(args[0]);
			switch (typ) {
			case Constants.RESPONSE_KASSE_ANGEMELDET:
				runOnUiThread(new Runnable() {
					@Override
					public void run() {
						anmeldung_erfolgreich();
					}
				});
				break;
			case Constants.RESPONSE_SPEISEKARTE:
				runOnUiThread(new Runnable() {
					@Override
					public void run() {
						speisekarte_abrufen_erfolgreich(args);
					}
				});
				break;
			case Constants.REQUEST_BESTELLUNG_KASSE:
//				final int bedienungs_ID = Integer.parseInt(args[1]);
				final String bedienungs_Name = args[2];
				final String tisch = args[3];
				final int anzahl = Integer.parseInt(args[4]);
				final int speiseID = Integer.parseInt(args[5]);
				String tempSpeise_Bezeichnung = "";
				int tempSpeise_Farbe = color.white;
				for (Speise speise : speisen) {
					if (speise.getID() == speiseID) {
						tempSpeise_Bezeichnung = speise.getBezeichnung();
						tempSpeise_Farbe = speise.getFarbe();
						break;
					}
				}
				final String speise_Bezeichnung = tempSpeise_Bezeichnung;
				final int speise_Farbe = tempSpeise_Farbe;
				runOnUiThread(new Runnable() {
					@Override
					public void run() {
						// Toast.makeText(getApplicationContext(), "BESTELLUNG ANGEKOMMEN: \n" + bedienungs_Name + "\n" + anzahl + "x " + speiseID, Toast.LENGTH_SHORT).show();
						bestellungen_neu.add(new BestellungAnzeigeKasse(bedienungs_Name, tisch, anzahl, speise_Bezeichnung, speise_Farbe));
						refresh_gvBestellungenNeu();
						if (tbKasseVibrieren.isChecked()) {
							vibrator.vibrate(Constants.VIBRIERZEIT_KASSE_BESTELLUNG_ANGEKOMMEN);
						}
						if (tbKasseSound.isChecked()) {
							if (!mpTon_neue_Bestellung.isPlaying()) {
								mpTon_neue_Bestellung.start();
							}
						}
					}
				});
				try {
					send(String.valueOf(Constants.RESPONSE_BESTELLUNG_KASSE_OK));
				} catch (Exception e) {
					// TODO An der Kasse eine Fehlermeldung ausgeben
				}
				break;
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void anmeldung_erfolgreich() {
		send(String.valueOf(Constants.REQUEST_SPEISEKARTE));
	}

	private void speisekarte_abrufen_erfolgreich(String[] args) {
		boolean ok = true;
		for (int a = 1; a < args.length; a++) {
			try {
				speisen.add(new Speise(args[a]));
			} catch (Exception e) {
				ok = false;
			}
		}
		if (ok) {
			relKasseFehler.setVisibility(View.GONE);
			vibrator.vibrate(Constants.VIBRIERZEIT_KASSE_ANMELDUNG_OK);
		} else {
			Toast.makeText(getApplicationContext(), "Speisekarte konnte nicht abgerufen werden!", Toast.LENGTH_LONG).show();
			vibrator.vibrate(Constants.VIBRIERZEIT_KASSE_ANMELDUNG_FEHLGESCHLAGEN);
			finish();
		}

	}

	private void send(String msg) {
		client.send(msg);
	}

	public void kasse_anmelden(View v) {
		kasse_anmelden();
	}

	private void refresh_gvBestellungenNeu() {
		GridAdapterBestellungKasse adapter = new GridAdapterBestellungKasse(getApplicationContext(), bestellungen_neu);
		egvKasseNeueBestellungen.setAdapter(adapter);
		egvKasseNeueBestellungen.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				// TODO Aubessern, dass zusammengruppiert wird
				// TODO Oder doch nicht???
				bestellungen_alt.add(bestellungen_neu.get(position));
				bestellungen_neu.remove(position);
				refresh_gvBestellungenNeu();
				refresh_gvBestellungenAlt();
			}
		});
	}

	private void refresh_gvBestellungenAlt() {
		GridAdapterBestellungKasse adapter = new GridAdapterBestellungKasse(getApplicationContext(), bestellungen_alt);
		egvKasseAlteBestellungen.setAdapter(adapter);
		egvKasseAlteBestellungen.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				// TODO Aubessern, dass zusammengruppiert wird
				// TODO Oder doch nicht???
				bestellungen_alt.remove(position);
				refresh_gvBestellungenAlt();
			}
		});
	}
	
	@Override
	protected void onDestroy() {
		mpTon_neue_Bestellung.release();
		super.onDestroy();
	}
	
	@Override
	public void onBackPressed() {
		
	}

	// @Override
	// public boolean onCreateOptionsMenu(Menu menu) {
	// getMenuInflater().inflate(R.menu.menu_main, menu);
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
