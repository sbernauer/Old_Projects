package adapters;

import java.util.ArrayList;

import de.sebe.ikasse.R;
import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class ListAdapterServerLog extends BaseAdapter {
	private LayoutInflater inflater;

	private ArrayList<String> log_nachrichten;
	private ArrayList<String> log_ids;
	private ArrayList<String> log_ips;
	private ArrayList<String> log_zeiten;
	
	private TextView tvListItemServerLogNachricht, tvListItemServerLogIP, tvListItemServerLogZeit;

	public ListAdapterServerLog(Context context, ArrayList<String> log_nachrichten, ArrayList<String> log_ids, ArrayList<String> log_ips, ArrayList<String> log_zeiten) {
		// Caches the LayoutInflater for quicker use
		this.inflater = LayoutInflater.from(context);
		// Sets the events data
		this.log_nachrichten = log_nachrichten;
		this.log_ids = log_ids;
		this.log_ips = log_ips;
		this.log_zeiten = log_zeiten;
	}

	public int getCount() {
		return this.log_nachrichten.size();
	}

	public String getItem(int position) throws IndexOutOfBoundsException {
		return this.log_nachrichten.get(position);
	}

	public long getItemId(int position) throws IndexOutOfBoundsException {
		if (position < getCount() && position >= 0) {
			return position;
		}
		return 0;
	}

	public int getViewTypeCount() {
		return 1;
	}

	public View getView(int position, View convertView, ViewGroup parent) {		
		//TODO Log farblich und inhaltlich gestalten...
		
		if (convertView == null) { // If the View is not cached
			// Inflates the Common View from XML file
			convertView = this.inflater.inflate(R.layout.layout_list_item_server_log, null);
		}
		
		tvListItemServerLogNachricht = (TextView)convertView.findViewById(R.id.tvListItemServerLogNachricht);
		tvListItemServerLogIP = (TextView)convertView.findViewById(R.id.tvListItemServerLogIP);
		tvListItemServerLogZeit = (TextView)convertView.findViewById(R.id.tvListItemServerLogZeit);

		tvListItemServerLogNachricht.setText(log_nachrichten.get(position));
		tvListItemServerLogIP.setText(log_ips.get(position) + "; " + log_ids.get(position));
		tvListItemServerLogZeit.setText(log_zeiten.get(position));

		return convertView;
	}
}