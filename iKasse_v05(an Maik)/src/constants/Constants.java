package constants;

public class Constants {
	public static final String SERVER_IP = "10.10.10.2";
	public static final int SERVER_PORT = 4321;
	
	public static final String trennZeichenkette = "#&#&%&#&#";
	public static final String subTrennZeichenkette = "#%&%#";
	
	public static final String PASSWORT_SERVER = "";
	public static final String PASSWORT_KASSE = "";

	public static final int REQUEST_ONLINE = 1;
	public static final int REQUEST_ANMELDEDATEN_BEDIENUNG = 2;
	public static final int REQUEST_SPEISEKARTE = 4;
	public static final int REQUEST_KASSE_ANMELDEN = 5;
	public static final int REQUEST_BESTELLUNG_SERVER = 6;
	public static final int REQUEST_BESTELLUNG_KASSE = 8;

	public static final int RESPONSE_ONLINE = 101;
	public static final int RESPONSE_ANMELDEDATEN_BEDIENUNG_OK = 102;
	public static final int RESPONSE_ANMELDEDATEN_BEDIENUNG_FEHLGESCHLAGEN = 103;
	public static final int RESPONSE_SPEISEKARTE = 104;
	public static final int RESPONSE_KASSE_ANGEMELDET = 105;
	public static final int RESPONSE_BESTELLUNG_SERVER_OK = 106;
	public static final int RESPONSE_BESTELLUNG_SERVER_FEHLGESCHLAGEN = 107;
	public static final int RESPONSE_BESTELLUNG_KASSE_OK = 108;
	
	// Zeiten in Millisekunden
	public static final int VIBRIERZEIT_SERVER_SERVER_GESTARTET = 250;	
	public static final int VIBRIERZEIT_BEDIENUNG_ANMELDUNG_OK = 150;	
	public static final int VIBRIERZEIT_BEDIENUNG_ANMELDUNG_FEHLGESCHLAGEN = 250;	
	public static final int VIBRIERZEIT_BEDIENUNG_BESTELLUNG_OK = 150;
	public static final int VIBRIERZEIT_BEDIENUNG_BESTELLUNG_FEHLGESCHLAGEN = 2000;
	public static final int VIBRIERZEIT_KASSE_ANMELDUNG_OK = 150;
	public static final int VIBRIERZEIT_KASSE_ANMELDUNG_FEHLGESCHLAGEN = 250;
	public static final int VIBRIERZEIT_KASSE_BESTELLUNG_ANGEKOMMEN = 200;
}