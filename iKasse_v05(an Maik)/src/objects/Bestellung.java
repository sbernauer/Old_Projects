package objects;

public class Bestellung {
	private int bedienungs_ID;
	private String bedienungs_Name;
	private String tisch;
	private int anzahl;
	private int speise_ID;
	private String bestellen_bei_IP;
	private double gesamtPreis;

	public Bestellung(int bedienungs_ID, String bedienungs_Name, String tisch, int anzahl, int speise_ID, String bestellen_bei_IP, double gesamtPreis) {
		this.bedienungs_ID = bedienungs_ID;
		this.bedienungs_Name = bedienungs_Name;
		this.anzahl = anzahl;
		this.speise_ID = speise_ID;
		this.tisch = tisch;
		this.bestellen_bei_IP = bestellen_bei_IP;
		this.gesamtPreis = gesamtPreis;
	}

	public int getBedienungs_ID() {
		return bedienungs_ID;
	}

	public void setBedienungs_ID(int bedienungs_ID) {
		this.bedienungs_ID = bedienungs_ID;
	}
	
	public String getbedienungs_Name() {
		return bedienungs_Name;
	}
	
	public void setBedienungs_Name(String bedienungs_Name) {
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

	public int getSpeise_ID() {
		return speise_ID;
	}

	public void setSpeise_ID(int speise_ID) {
		this.speise_ID = speise_ID;
	}

	public String getBestellen_bei_IP() {
		return bestellen_bei_IP;
	}

	public void setBestellen_bei_IP(String bestellen_bei_IP) {
		this.bestellen_bei_IP = bestellen_bei_IP;
	}
	
	public double getGesamtPreis() {
		return gesamtPreis;
	}
	
	public void setGesamtPreis(double gesamtPreis) {
		this.gesamtPreis = gesamtPreis;
	}
}
