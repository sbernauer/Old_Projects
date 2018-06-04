package de.sebe.ikasse;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;
import constants.Constants;

public class MainActivity extends Activity {
	private RelativeLayout relMainPasswortServer, relMainPasswortKasse;
	private EditText etMainPasswortServer, etMainPasswortKasse;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_main);
		relMainPasswortServer = (RelativeLayout) findViewById(R.id.relMainPasswortServer);
		etMainPasswortServer = (EditText) findViewById(R.id.etMainPasswortServer);
		relMainPasswortKasse = (RelativeLayout) findViewById(R.id.relMainPasswortKasse);
		etMainPasswortKasse = (EditText) findViewById(R.id.etMainPasswortKasse);
	}
	
	public void main_Bedienung(View v){
		Intent intentBedienungActivity = new Intent(this, BedienungActivity.class);
		startActivity(intentBedienungActivity);
		this.finish();
	}
	
	public void main_Server(View v){
		etMainPasswortServer.setText("");
		relMainPasswortServer.setVisibility(View.VISIBLE);
	}
	
	public void main_Kasse(View v){
		etMainPasswortKasse.setText("");
		relMainPasswortKasse.setVisibility(View.VISIBLE);
	}
	
	public void btMainPasswortServerAbbrechen(View v) {
		relMainPasswortServer.setVisibility(View.GONE);
	}
	
	public void btMainPasswortKasseAbbrechen(View v) {
		relMainPasswortKasse.setVisibility(View.GONE);
	}
	
	public void btMainPasswortServerFortfahren(View v) {
		if(etMainPasswortServer.getText().toString().equals(Constants.PASSWORT_SERVER)) {
			Intent intentServerActivity = new Intent(this, ServerActivity.class);
			startActivity(intentServerActivity);
			this.finish();
		} else {
			Toast.makeText(getApplicationContext(), "Falches Passwort", Toast.LENGTH_SHORT).show();
			etMainPasswortServer.setText("");
		}
	}
	
	public void btMainPasswortKasseFortfahren(View v) {
		if(etMainPasswortKasse.getText().toString().equals(Constants.PASSWORT_KASSE)) {
			Intent intentKasseActivity = new Intent(this, KasseActivity.class);
			startActivity(intentKasseActivity);
			this.finish();
		} else {
			Toast.makeText(getApplicationContext(), "Falches Passwort", Toast.LENGTH_SHORT).show();
			etMainPasswortKasse.setText("");
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.menu_main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();
		if (id == R.id.action_einstellungen) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
