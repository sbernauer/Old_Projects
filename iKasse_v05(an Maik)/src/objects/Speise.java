package objects;

import constants.Constants;

public class Speise {
	private int id;
	private String bezeichnung;
	private double preis;
	private String bestellung_bei_ip;
	private int farbe;
	private int anzahl;

	public Speise(int id, String bezeichnung, double preis, String bestellung_bei_ip, int farbe, int anzahl) {
		this.id = id;
		this.bezeichnung = bezeichnung;
		this.preis = preis;
		this.bestellung_bei_ip = bestellung_bei_ip;
		this.farbe = farbe;
		this.anzahl = anzahl;
	}
	
	public Speise(String speicherString) throws Exception {
		String[] data = speicherString.split(Constants.subTrennZeichenkette);
		this.id = Integer.parseInt(data[0]);
		this.bezeichnung = data[1];
		this.preis = Double.parseDouble(data[2]);
		this.bestellung_bei_ip = data[3];
		this.farbe = Integer.parseInt(data[4]);
		this.anzahl = Integer.parseInt(data[5]);
	}

	public int getID() {
		return id;
	}

	public String getBezeichnung() {
		return bezeichnung;
	}

	public double getPreis() {
		return preis;
	}

	public String getPreisString() {
		String str;
		if (preis % 1 == 0) {
			str = String.valueOf(preis);
			str = str.substring(0, str.length() - 2) + " €";
		} else {
			str = String.valueOf((double) Math.round(preis * 100) / 100);
			if (!str.substring(str.length() - 3, str.length() - 2).equals(".")) {
				str += "0";
			}
			str += " €";
		}
		return str;
	}

	public String getBestellung_bei_Ip() {
		return bestellung_bei_ip;
	}
	
	public int getFarbe() {
		return farbe;
	}
	
	public int getAnzahl() {
		return anzahl;
	}
	
	public void setAnzahl(int anzahl) {
		this.anzahl = anzahl;
	}
	
	public void addiereAnzahl(int add) {
		this.anzahl += add;
	}
	
	// True, wenn von der Anzahl 1 abgezogen wird und die Anzahl nicht negativ wird
	public boolean subtrahiereAnzahl(int sub) {
		if (anzahl > 0) {
			this.anzahl -= sub;
			return true;
		}
		return false;
	}
	
	public String getSpeicherString() {
		return id + Constants.subTrennZeichenkette + bezeichnung + Constants.subTrennZeichenkette + preis + Constants.subTrennZeichenkette + bestellung_bei_ip + Constants.subTrennZeichenkette + farbe + Constants.subTrennZeichenkette + anzahl;
	}
}