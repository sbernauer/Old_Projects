package network;

import android.os.AsyncTask;
import android.view.View;
import constants.Constants;
import de.sebe.ikasse.BedienungActivity;

public class AsyncTaskStartClientBedienung extends AsyncTask<String, Void, ClientBedienung> {
	private ClientBedienung client;
	private BedienungActivity activity;

    public void param_uebergeben(BedienungActivity activity) {
    	this.activity = activity;
    }

    protected void onPostExecute() {
    	
    }

	@Override
	protected ClientBedienung doInBackground(String... params) {
		try {
			client = new ClientBedienung(Constants.SERVER_IP, Constants.SERVER_PORT, activity);
			return client;
		} catch (final Exception e) {
			activity.runOnUiThread(new Runnable() {
				@Override
				public void run() {
					activity.tvBedienung_anmelden_nio.setVisibility(View.VISIBLE);
					activity.tvBedienung_anmelden_nio.setText("FEHLER Error:\n" + e.getMessage() + "\n" + e.toString() + "\n" + e.getLocalizedMessage());
					activity.relBedienung_anmelden_warten.setVisibility(View.GONE);
					activity.vibrator.vibrate(250);
				}
			});
		}
		return null;
	}
}