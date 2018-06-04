package objects;

import constants.Constants;

public class Bedienung {
	private int id;
	private String name;
	private String passwort;
	private double umsatz;
	
	public Bedienung(int id, String name, String passwort, double umsatz) {
		this.id = id;
		this.name = name;
		this.passwort = passwort;
		this.umsatz = umsatz;
	}
	
	public Bedienung(String speicherString) throws Exception {
		String[] data = speicherString.split(Constants.subTrennZeichenkette);
		this.id = Integer.parseInt(data[0]);
		this.name = data[1];
		this.passwort = data[2];
		this.umsatz = Double.parseDouble(data[3]);
	}
	
	public int getID() {
		return id;
	}
	
	public String getName(){
		return name;
	}
	
	public String getPasswort() {
		return passwort;
	}
	
	public double getUmsatz() {
		return umsatz;
	}
	
	public void setUmsatz(double umsatz) {
		this.umsatz = umsatz;
	}
	
	public void erhoeheUmsatz(double menge) {
		umsatz += menge;
	}
	
	public String getSpeicherString() {
		return id + Constants.subTrennZeichenkette + name + Constants.subTrennZeichenkette + passwort + Constants.subTrennZeichenkette + umsatz;
	}
}