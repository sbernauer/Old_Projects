package network;

import java.util.ArrayList;

import constants.Constants;
import de.sebe.ikasse.ServerActivity;
import objects.Bedienung;
import objects.Speise;
import android.os.AsyncTask;

public class AsyncTaskStartServer extends AsyncTask<String, Void, Server> {
	private Server server;
	private ServerActivity serverActivity;

    public void param_uebergeben(ServerActivity serverActivity) {
    	this.serverActivity = serverActivity;
    }

    protected void onPostExecute() {
    	
    }

	@Override
	protected Server doInBackground(String... params) {
		server = new Server(Constants.SERVER_PORT, serverActivity);
		return server;
	}
}