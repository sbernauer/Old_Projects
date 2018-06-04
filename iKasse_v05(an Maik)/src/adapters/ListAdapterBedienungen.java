package adapters;

import java.util.ArrayList;

import objects.Bedienung;
import de.sebe.ikasse.R;
import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class ListAdapterBedienungen extends BaseAdapter {
	private LayoutInflater inflater;

	private ArrayList<Bedienung> bedienungen;

	private TextView tvListItemBedienungName, tvListItemBedienungPasswort, tvListItemBedienungUmsatz;

	public ListAdapterBedienungen(Context context, ArrayList<Bedienung> bedienungen) {
		// Caches the LayoutInflater for quicker use
		this.inflater = LayoutInflater.from(context);
		// Sets the events data
		this.bedienungen = bedienungen;
	}

	public int getCount() {
		return this.bedienungen.size();
	}

	public String getItem(int position) throws IndexOutOfBoundsException {
		return this.bedienungen.get(position).getName();
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
		if (convertView == null) { // If the View is not cached
			// Inflates the Common View from XML file
			convertView = this.inflater.inflate(R.layout.layout_list_item_bedienung, null);
		}
		Bedienung bed = bedienungen.get(position);

		tvListItemBedienungName = (TextView) convertView.findViewById(R.id.tvListItemBedienungName);
		tvListItemBedienungPasswort = (TextView) convertView.findViewById(R.id.tvListItemBedienungPasswort);
		tvListItemBedienungUmsatz = (TextView) convertView.findViewById(R.id.tvListItemBedienungUmsatz);
		
		tvListItemBedienungName.setText(bed.getID() + ": " + bed.getName());
		tvListItemBedienungPasswort.setText("PW: " + bed.getPasswort());
		tvListItemBedienungUmsatz.setText("Umsatz: " + bed.getUmsatz());
		return convertView;
	}
}