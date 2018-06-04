package objects;

public class BestellungAnzeigeKasse {
	private String bedienungs_Name;
	private String tisch;
	private int anzahl;
	private String speise_Bezeichnung;
	private int speise_Farbe;
	
	public BestellungAnzeigeKasse(String bedienungs_Name, String tisch, int anzahl, String speise_Bezeichnung, int speise_Farbe) {
		this.bedienungs_Name = bedienungs_Name;
		this.tisch = tisch;
		this.anzahl = anzahl;
		this.speise_Bezeichnung = speise_Bezeichnung;
		this.speise_Farbe = speise_Farbe;
	}
	
	public String getBedienungsName() {
		return bedienungs_Name;
	}
	
	public void setBedienungsName(String bedienungs_Name) {
		this.bedienungs_Name = bedienungs_Name;
	}
	
	public String getTisch() {
		return tisch;
	}
	
	public void setTisch(String tisch) {
		this.tisch = tisch;
	}
	
	public int getAnzahl() {
		return anzahl;
	}
	
	public void setAnzahl(int anzahl) {
		this.anzahl = anzahl;
	}
	
	public String getSpeiseBezeichnung() {
		return speise_Bezeichnung;
	}
	
	public void setSpeiseBezeichnung(String speise_Bezeichnung) {
		this.speise_Bezeichnung = speise_Bezeichnung;
	}
	
	public int getSpeise_Farbe(){
		return speise_Farbe;
	}
	
	public void setSpeise_Farbe(int speise_Farbe) {
		this.speise_Farbe = speise_Farbe;
	}
}