package WarOfTanks;

public class Tank {
	private String playerName;
	private int health, color, frameNumber, frameNumberCounter, fuel, maxHealth, numberWeapons, numberWeaponNames, selectedWeapon, aimX, aimY;
	private double x, y, rotation, shootingDirection;
	private boolean isMoving;
	private Weapon[] Weapons = new Weapon[20];
	private String[][] WeaponNames = new String[3][20];

	public Tank() {

	}

	public Tank(String playerName, int health, int fuel, int color, double x, double y, double shootingDiretion, int maxHealth) {
		this.playerName = playerName;
		this.health = health;
		this.fuel = fuel;
		this.color = color;
		this.x = x;
		this.y = y;
		this.shootingDirection = shootingDiretion;
		this.maxHealth = maxHealth;

		this.frameNumber = 0;
		this.isMoving = false;
		this.numberWeapons = 0;
		this.selectedWeapon = 0;

		newWeapons(10);
	}

	public String getPlayerName() {
		return playerName;
	}

	public int getHealth() {
		return health;
	}

	public int getColor() {
		return color;
	}

	public double getX() {
		return x;
	}

	public double getY() {
		return y;
	}

	public void setShootingDirection(double shootingDirection) {
		this.shootingDirection = shootingDirection;
	}

	public double getShootingDirection() {
		return shootingDirection;
	}

	public void doDamage(int damage) {
		health -= damage;
	}

	public int getAndIncFrameNumber() {
		if (isMoving) {
			frameNumberCounter++;
			if (frameNumberCounter == 5) {
				frameNumber = (frameNumber == 1) ? 0 : 1;
				frameNumberCounter = 0;
			}
			isMoving = false;
		}
		return frameNumber;
	}

	public boolean move(double x, double y, int fuelNeeded) {
		if (fuel >= fuelNeeded) {
			this.x = x;
			this.y = y;
			this.fuel -= fuelNeeded;
			this.isMoving = true;
			return true;
		} else {
			fuel = 0;
		}
		return false;
	}

	public int getMaxHealth() {
		return maxHealth;
	}

	public void setFuel(int fuel) {
		this.fuel = fuel;
	}

	public int getFuel() {
		return fuel;
	}

	public double getRotation() {
		return rotation;
	}

	public void setRotation(double rotation) {
		this.rotation = rotation;
	}

	public Weapon[] getWeapons() {
		return Weapons;
	}

	public void removeWeapon(int id) {
		// ...
		updateWeaponNames();
	}

	public void newWeapons(int number) {
		for (int i = 0; i <= number - 1; i++) {
			Weapons[numberWeapons + i] = new Weapon((int) (Math.random() * 3));
		}
		numberWeapons += number;
		updateWeaponNames();
	}

	private void updateWeaponNames() {
		String name;
		int counter = 0;
		boolean exist;
		for (int a = 0; a <= numberWeapons - 1; a++) {
			exist = false;
			name = Weapons[a].getName();
			for (int b = 0; b <= counter - 1; b++) {
				if (name == WeaponNames[0][b]) {
					WeaponNames[1][b] = String.valueOf(Integer.parseInt(WeaponNames[1][b]) + 1);
					exist = true;
					break;
				}
			}
			if (!exist) {
				WeaponNames[0][counter] = name;
				WeaponNames[1][counter] = "1";
				WeaponNames[2][counter] = Weapons[a].getDescription();
				counter++;
			}
		}
		numberWeaponNames = counter;
	}

	public String[][] getWeaponNames() {
		return WeaponNames;
	}

	public int getNumberWeaponNames() {
		return numberWeaponNames;
	}
	
	public int getSelectedWeapon() {
		return selectedWeapon;
	}
	
	public void setSelectedWeapon(int selectedWeapon) {
		this.selectedWeapon = selectedWeapon;
	}
	
	public int getAimX() {
		return aimX;
	}
	
	public int getAimY() {
		return aimY;
	}
	
	public void setAimX(int aimX) {
		this.aimX = aimX;
	}
	
	public void setAimY(int aimY) {
		this.aimY = aimY;
	}
}