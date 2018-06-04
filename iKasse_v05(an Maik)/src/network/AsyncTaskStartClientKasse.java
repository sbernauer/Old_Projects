package network;

import android.os.AsyncTask;
import android.view.View;
import constants.Constants;
import de.sebe.ikasse.KasseActivity;

public class AsyncTaskStartClientKasse extends AsyncTask<String, Void, ClientKasse> {
	private ClientKasse client;
	private KasseActivity activity;

    public void param_uebergeben(KasseActivity activity) {
    	this.activity = activity;
    }

    protected void onPostExecute() {
    	
    }

	@Override
	protected ClientKasse doInBackground(String... params) {
		try {
			client = new ClientKasse(Constants.SERVER_IP, Constants.SERVER_PORT, activity);
			return client;
		} catch (final Exception e) {
			activity.runOnUiThread(new Runnable() {
				@Override
				public void run() {
					activity.relKasseFehler.setVisibility(View.VISIBLE);
					activity.tvKasseFehler.setText("FEHLER Error:\n" + e.getMessage() + "\n" + e.toString() + "\n" + e.getLocalizedMessage());
					activity.btKasseFehlerNochmal.setVisibility(View.VISIBLE);
					activity.vibrator.vibrate(250);
//					activity.tvBedienung_anmelden_nio.setVisibility(View.VISIBLE);
//					activity.tvBedienung_anmelden_nio.setText("FEHLER Error:\n" + e.getMessage() + "\n" + e.toString() + "\n" + e.getLocalizedMessage());
//					activity.relBedienung_anmelden_warten.setVisibility(View.GONE);
					activity.vibrator.vibrate(250);
				}
			});
		}
		return null;
	}
}