package adapters;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;
import de.sebe.ikasse.R;
import objects.Speise;

public class GridAdapterSpeisen extends BaseAdapter {
	private LayoutInflater inflater;
	private RelativeLayout relGridItemSpeise;
	private TextView tvGridItemSpeiseAnzahl, tvGridItemSpeiseBezeichnung, tvGridItemSpeisePreis;
	private boolean id_statt_anzahl_anzeigen;

	private ArrayList<Speise> speisen = new ArrayList<Speise>();

	public GridAdapterSpeisen(Context context, ArrayList<Speise> speisen, boolean id_statt_anzahl_anzeigen) {
		this.inflater = LayoutInflater.from(context);
		this.speisen = speisen;
		this.id_statt_anzahl_anzeigen = id_statt_anzahl_anzeigen;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return speisen.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return speisen.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		if (position < getCount() && position >= 0) {
			return position;
		}
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if (convertView == null) { // If the View is not cached
			// Inflates the Common View from XML file
			convertView = this.inflater.inflate(R.layout.layout_grid_item_speise, null);
		}

		Speise speise = speisen.get(position);

		relGridItemSpeise = (RelativeLayout) convertView.findViewById(R.id.relGridItemSpeise);
		tvGridItemSpeiseBezeichnung = (TextView) convertView.findViewById(R.id.tvGridItemSpeiseBezeichnung);
		tvGridItemSpeisePreis = (TextView) convertView.findViewById(R.id.tvGridItemSpeisePreis);
		tvGridItemSpeiseAnzahl = (TextView) convertView.findViewById(R.id.tvGridItemSpeiseAnzahl);

		relGridItemSpeise.setBackgroundColor(speise.getFarbe());
		tvGridItemSpeiseBezeichnung.setText(speise.getBezeichnung());
		tvGridItemSpeisePreis.setText("" + speise.getPreisString());
		if (id_statt_anzahl_anzeigen) {
			tvGridItemSpeiseAnzahl.setText(String.valueOf(speise.getID()));
		} else {
			tvGridItemSpeiseAnzahl.setText(String.valueOf(speise.getAnzahl()));
		}

		return convertView;
	}

}
