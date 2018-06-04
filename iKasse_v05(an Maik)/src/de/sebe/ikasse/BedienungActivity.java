package de.sebe.ikasse;

import java.util.ArrayList;

import adapters.GridAdapterSpeisen;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.os.Vibrator;
import android.text.InputType;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import constants.Constants;
import network.AsyncTaskStartClientBedienung;
import network.ClientBedienung;
import objects.Speise;

public class BedienungActivity extends Activity {
	private ClientBedienung client;

	private int bedienungsID = 0;
	private String bedienungs_Name = "";
	private String tisch = "---";

	private ArrayList<Speise> speisen = new ArrayList<Speise>();

	private EditText etBedienung_anmelden_Name, etBedienung_anmelden_Passwort, etBedienung_tisch_waehlen;
	private Button btBedienung_anmelden, btBedienungTisch;
	public RelativeLayout relBedienung_anmelden_warten;
	private RelativeLayout relBedienung_bestellung_warten, relBedienung_tisch_waehlen;
	private TextView tvBedienung_anmelden_warten, tvBedienungGesamtpreis;
	public TextView tvBedienung_anmelden_nio;
	private GridView gvBedienungSpeisen;

	public Vibrator vibrator;
	private InputMethodManager imm;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		try {
			requestWindowFeature(Window.FEATURE_NO_TITLE);
			getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		} catch (Exception e) {

		}
		setContentView(R.layout.layout_bedienung_anmelden);
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

		etBedienung_anmelden_Name = (EditText) findViewById(R.id.etBedienung_anmelden_Name);
		etBedienung_anmelden_Passwort = (EditText) findViewById(R.id.etBedienung_anmelden_Passwort);
		btBedienung_anmelden = (Button) findViewById(R.id.btBedienung_anmelden);
		relBedienung_anmelden_warten = (RelativeLayout) findViewById(R.id.relBedienung_anmelden_warten);
		tvBedienung_anmelden_warten = (TextView) findViewById(R.id.tvBedienung_anmelden_warten);
		tvBedienung_anmelden_nio = (TextView) findViewById(R.id.tvBedienung_anmelden_nio);
		
		getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
		imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
		etBedienung_anmelden_Name.requestFocus();
		imm.showSoftInput(etBedienung_anmelden_Name, InputMethodManager.SHOW_IMPLICIT);
		
//
//		getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
//		etBedienung_anmelden_Name.requestFocus();
//		imm.showSoftInput(etBedienung_anmelden_Name, InputMethodManager.SHOW_IMPLICIT);

		// Handler handler = new Handler();
		// handler.postDelayed(new Runnable() {
		// public void run() {
		// etBedienung_anmelden_Name.dispatchTouchEvent(MotionEvent.obtain(SystemClock.uptimeMillis(), SystemClock.uptimeMillis(), MotionEvent.ACTION_DOWN, 0, 0, 0));
		//// etBedienung_anmelden_Name.dispatchTouchEvent(MotionEvent.obtain(SystemClock.uptimeMillis(), SystemClock.uptimeMillis(), MotionEvent.ACTION_DOWN, 0, 0, 0));
		// }
		// }, 100);
		//
		// handler.postDelayed(new Runnable() {
		// public void run() {
		//// etBedienung_anmelden_Name.dispatchTouchEvent(MotionEvent.obtain(SystemClock.uptimeMillis(), SystemClock.uptimeMillis(), MotionEvent.ACTION_DOWN, 0, 0, 0));
		// etBedienung_anmelden_Name.dispatchTouchEvent(MotionEvent.obtain(SystemClock.uptimeMillis(), SystemClock.uptimeMillis(), MotionEvent.ACTION_DOWN, 0, 0, 0));
		// }
		// }, 200);

		vibrator = (Vibrator) getApplicationContext().getSystemService(Context.VIBRATOR_SERVICE);
	}

	public void bedienung_anmelden(View v) {
		imm.hideSoftInputFromWindow(etBedienung_anmelden_Name.getWindowToken(), 0);
		tvBedienung_anmelden_nio.setVisibility(View.GONE);
		relBedienung_anmelden_warten.setVisibility(View.VISIBLE);
		AsyncTaskStartClientBedienung asynctask = new AsyncTaskStartClientBedienung();
		asynctask.param_uebergeben(this);
		try {
			client = asynctask.execute("").get();
		} catch (Exception e) {
			tvBedienung_anmelden_nio.setVisibility(View.VISIBLE);
			tvBedienung_anmelden_nio.setText("FEHLER Error:\n" + e.getMessage() + "\n" + e.toString() + "\n" + e.getLocalizedMessage());
			relBedienung_anmelden_warten.setVisibility(View.GONE);
			vibrator.vibrate(Constants.VIBRIERZEIT_BEDIENUNG_ANMELDUNG_FEHLGESCHLAGEN);
		}
		if (client != null) {
			send(Constants.REQUEST_ANMELDEDATEN_BEDIENUNG + Constants.trennZeichenkette + etBedienung_anmelden_Name.getText().toString() + Constants.trennZeichenkette + etBedienung_anmelden_Passwort.getText().toString());
		}
	}

	private void anmeldung_erfolgreich() {
		tvBedienung_anmelden_warten.setText(getResources().getString(R.string.speisekarte_abrufen));
		send(String.valueOf(Constants.REQUEST_SPEISEKARTE));
	}

	private void anmeldung_nicht_erfolgreich() {
		relBedienung_anmelden_warten.setVisibility(View.GONE);
		tvBedienung_anmelden_nio.setVisibility(View.VISIBLE);
		vibrator.vibrate(Constants.VIBRIERZEIT_BEDIENUNG_ANMELDUNG_FEHLGESCHLAGEN);
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
			speisen_auf_0_setzen();
			setContentView(R.layout.layout_bedienung_main);
			getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
			gvBedienungSpeisen = (GridView) findViewById(R.id.gvBedienungSpeisen);
			tvBedienungGesamtpreis = (TextView) findViewById(R.id.tvBedienungGesamtpreis);
			relBedienung_bestellung_warten = (RelativeLayout) findViewById(R.id.relBedienung_bestellung_warten);
			btBedienungTisch = (Button) findViewById(R.id.btBedienungTisch);
			relBedienung_tisch_waehlen = (RelativeLayout) findViewById(R.id.relBedienung_tisch_waehlen);
			etBedienung_tisch_waehlen = (EditText) findViewById(R.id.etBedienung_tisch_waehlen);
			refresh_gvBedienungSpeisen();
			vibrator.vibrate(Constants.VIBRIERZEIT_BEDIENUNG_ANMELDUNG_OK);
		} else {
			Toast.makeText(getApplicationContext(), "Speisekarte konnte nicht abgerufen werden!", Toast.LENGTH_LONG).show();
			vibrator.vibrate(Constants.VIBRIERZEIT_BEDIENUNG_ANMELDUNG_FEHLGESCHLAGEN);
			finish();
		}
	}

	private void speisen_auf_0_setzen() {
		for (Speise speise : speisen) {
			speise.setAnzahl(0);
		}
	}

	private void send(String msg) {
		client.send(msg);
	}

	public void handle(String msg) {
		try {
			final String[] args = msg.split(Constants.trennZeichenkette);
			int typ = Integer.parseInt(args[0]);
			switch (typ) {
			case Constants.RESPONSE_ANMELDEDATEN_BEDIENUNG_OK:
				runOnUiThread(new Runnable() {
					@Override
					public void run() {
						bedienungsID = Integer.parseInt(args[1]);
						bedienungs_Name = args[2];

						anmeldung_erfolgreich();
					}
				});
				break;
			case Constants.RESPONSE_ANMELDEDATEN_BEDIENUNG_FEHLGESCHLAGEN:
				runOnUiThread(new Runnable() {
					@Override
					public void run() {
						anmeldung_nicht_erfolgreich();
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
			case Constants.RESPONSE_BESTELLUNG_SERVER_OK:
				for (Speise speise : speisen) {
					speise.setAnzahl(0);
				}
				try { // Tischnummer um 1 erhöhen
					int tischNummer = Integer.parseInt(tisch);
					tisch = String.valueOf(tischNummer + 1);
					btBedienungTisch.setText("Tisch " + tisch);
				} catch (Exception e) {
				}
				runOnUiThread(new Runnable() {
					@Override
					public void run() {
						refresh_gvBedienungSpeisen();
						relBedienung_bestellung_warten.setVisibility(View.GONE);
						Toast.makeText(getApplicationContext(), "Bestellung erfolgreich", Toast.LENGTH_SHORT).show();
						vibrator.vibrate(Constants.VIBRIERZEIT_BEDIENUNG_BESTELLUNG_OK);
					}
				});
				break;
			case Constants.RESPONSE_BESTELLUNG_SERVER_FEHLGESCHLAGEN:
				runOnUiThread(new Runnable() {
					@Override
					public void run() {
						relBedienung_bestellung_warten.setVisibility(View.GONE);
						Toast.makeText(getApplicationContext(), "Bestellung FEHLGESCHLAGEN!!!!!", Toast.LENGTH_SHORT).show();
						vibrator.vibrate(Constants.VIBRIERZEIT_BEDIENUNG_BESTELLUNG_FEHLGESCHLAGEN);
					}
				});
				break;
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void refresh_gvBedienungSpeisen() {
		GridAdapterSpeisen adapter = new GridAdapterSpeisen(getApplicationContext(), speisen, false);
		gvBedienungSpeisen.setAdapter(adapter);
		refresh_Gesamtpreis();
		btBedienungTisch.setText("Tisch " + tisch);
		gvBedienungSpeisen.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				speisen.get(position).addiereAnzahl(1);
				refresh_gvBedienungSpeisen();
			}
		});
		gvBedienungSpeisen.setOnItemLongClickListener(new OnItemLongClickListener() {
			@Override
			public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
				if (speisen.get(position).subtrahiereAnzahl(1)) {
					refresh_gvBedienungSpeisen();
				}
				return true;
			}
		});
	}

	public void bedienung_bestellen(View v) {
		String msg = Constants.REQUEST_BESTELLUNG_SERVER + Constants.trennZeichenkette + bedienungsID + Constants.trennZeichenkette + bedienungs_Name + Constants.trennZeichenkette + tisch;
		for (Speise speise : speisen) {
			if (speise.getAnzahl() > 0) {
				msg += Constants.trennZeichenkette + speise.getAnzahl() + Constants.trennZeichenkette + speise.getID();
			}
		}
		relBedienung_bestellung_warten.setVisibility(View.VISIBLE);
		send(msg);
	}

	private void refresh_Gesamtpreis() {
		double gesamtpreis = 0;
		for (Speise speise : speisen) {
			gesamtpreis += speise.getAnzahl() * speise.getPreis();
		}
		String str;
		if (gesamtpreis % 1 == 0) {
			str = String.valueOf(gesamtpreis);
			str = str.substring(0, str.length() - 2) + " €";
		} else {
			str = String.valueOf((double) Math.round(gesamtpreis * 100) / 100);
			if (!str.substring(str.length() - 3, str.length() - 2).equals(".")) {
				str += "0";
			}
			str += " €";
		}
		tvBedienungGesamtpreis.setText(str);
	}

	public void bedienung_tisch_waehlen(View v) {
		etBedienung_tisch_waehlen.setText("");
		etBedienung_tisch_waehlen.setInputType(InputType.TYPE_CLASS_NUMBER |InputType.TYPE_NUMBER_FLAG_DECIMAL);
		relBedienung_tisch_waehlen.setVisibility(View.VISIBLE);
		etBedienung_tisch_waehlen.requestFocus();
		imm.showSoftInput(etBedienung_tisch_waehlen, InputMethodManager.SHOW_IMPLICIT);

		
		
		// FIXME Hier soll nur die Ziffer-tastatur angezeigt werden
//		imm.showSoftInputFromInputMethod(etBedienung_tisch_waehlen.getWindowToken(), InputMethodManager.SHOW_IMPLICIT);
//		getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
//		etBedienung_tisch_waehlen.requestFocus();
		// imm.showSoftInput(etBedienung_tisch_waehlen, InputMethodManager.SHOW_IMPLICIT);
		// etBedienung_tisch_waehlen.setInputType(InputType.TYPE_NUMBER_VARIATION_NORMAL);

//		etBedienung_tisch_waehlen.dispatchTouchEvent(MotionEvent.obtain(SystemClock.uptimeMillis(), SystemClock.uptimeMillis(), MotionEvent.ACTION_DOWN, 0, 0, 0));
//		etBedienung_tisch_waehlen.dispatchTouchEvent(MotionEvent.obtain(SystemClock.uptimeMillis(), SystemClock.uptimeMillis(), MotionEvent.ACTION_DOWN, 0, 0, 0));
	}

	public void bedienung_tisch_waehlen_fertig(View v) {
		tisch = etBedienung_tisch_waehlen.getText().toString();
		if (tisch.equals("")) {
			tisch = "---";
		}
		etBedienung_tisch_waehlen.clearFocus();
		imm.hideSoftInputFromWindow(etBedienung_tisch_waehlen.getWindowToken(), 0);
		relBedienung_tisch_waehlen.setVisibility(View.GONE);
		btBedienungTisch.setText("Tisch " + tisch);
	}

	public void bedienung_tisch_waehlen_text_eingeben(View v) {
		etBedienung_tisch_waehlen.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_FLAG_NO_SUGGESTIONS);
		getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
		etBedienung_tisch_waehlen.requestFocus();
		imm.showSoftInput(etBedienung_tisch_waehlen, InputMethodManager.SHOW_IMPLICIT);
	}
	
	@Override
	public void onBackPressed() {
		
	}
}
