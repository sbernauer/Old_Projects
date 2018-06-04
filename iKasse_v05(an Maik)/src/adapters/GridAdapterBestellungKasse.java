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
import objects.BestellungAnzeigeKasse;
import objects.Speise;

public class GridAdapterBestellungKasse extends BaseAdapter {
	private LayoutInflater inflater;
	
	private RelativeLayout relGridItemBestellungKasse;
	private TextView tvGridItemBestellungKasseAnzahl, tvGridItemBestellungKasseBezeichnung, tvGridItemBestellungKasseBedienungsName, tvGridItemBestellungKasseTisch;

	private ArrayList<BestellungAnzeigeKasse> bestellungen_zum_anzeigen;

	public GridAdapterBestellungKasse(Context context, ArrayList<BestellungAnzeigeKasse> bestellungen_zum_anzeigen) {
		this.inflater = LayoutInflater.from(context);
		this.bestellungen_zum_anzeigen = bestellungen_zum_anzeigen;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return bestellungen_zum_anzeigen.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return bestellungen_zum_anzeigen.get(position);
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
			convertView = this.inflater.inflate(R.layout.layout_grid_item_bestellung_kasse, null);
		}
		
		relGridItemBestellungKasse = (RelativeLayout)convertView.findViewById(R.id.relGridItemBestellungKasse);
		tvGridItemBestellungKasseAnzahl = (TextView)convertView.findViewById(R.id.tvGridItemBestellungKasseAnzahl);
		tvGridItemBestellungKasseBezeichnung = (TextView)convertView.findViewById(R.id.tvGridItemBestellungKasseBezeichnung);
		tvGridItemBestellungKasseBedienungsName= (TextView)convertView.findViewById(R.id.tvGridItemBestellungKasseBedienungsName);
		tvGridItemBestellungKasseTisch = (TextView)convertView.findViewById(R.id.tvGridItemBestellungKasseTisch);
		
		tvGridItemBestellungKasseAnzahl.setText(String.valueOf(bestellungen_zum_anzeigen.get(position).getAnzahl()));
		tvGridItemBestellungKasseBezeichnung.setText(bestellungen_zum_anzeigen.get(position).getSpeiseBezeichnung());
		tvGridItemBestellungKasseBedienungsName.setText(bestellungen_zum_anzeigen.get(position).getBedienungsName());
		tvGridItemBestellungKasseTisch.setText(bestellungen_zum_anzeigen.get(position).getTisch());
		relGridItemBestellungKasse.setBackgroundColor(bestellungen_zum_anzeigen.get(position).getSpeise_Farbe());
		
		return convertView;
	}

}
