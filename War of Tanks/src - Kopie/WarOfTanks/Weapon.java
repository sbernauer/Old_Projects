package WarOfTanks;

public class Weapon {
	private int id;
	private String name, description;
	
	public Weapon () {
		
	}
	
	public Weapon(int id) {
		this.id = id;
		switch(id) {
		case 0:
			name = "Einzelschuss";
			description = "Einzelner Schuss";
			break;
		case 1:
			name = "Doppelschuss";
			description = "Teilt sich nach Abschuss";
			break;
		case 2:
			name = "Granate";
			description = "Explodiert bei Aufprall";
			break;
		}
	}
	
	public int getID() {
		return id;
	}
	
	public String getName() {
		return name;
	}
	
	public String getDescription() {
		return description;
	}
}