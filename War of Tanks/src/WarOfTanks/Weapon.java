package WarOfTanks;

import java.awt.Image;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import javax.imageio.ImageIO;

public class Weapon {
	private int id;
	private Image imgWeapon, imgWeapon_selected;
	private String name, description;
	
	public Weapon () {
		
	}
	
	public Weapon(URL codeBase, int id) throws MalformedURLException, IOException {
		this.id = id;
		switch(id) {
		case 0:
			name = "Bazooka";
			imgWeapon = ImageIO.read(new URL(codeBase, "weapon_bazooka.png"));
			imgWeapon_selected = ImageIO.read(new URL(codeBase, "weapon_bazooka_selected.png"));
			description = "Dieses Flugobjekt fliegt in Parabelform und explodiert bei Bodenkontakt";
			break;
		case 1:
			name = "Luftangriff";
			imgWeapon = ImageIO.read(new URL(codeBase, "weapon_air_strike.png"));
			imgWeapon_selected = ImageIO.read(new URL(codeBase, "weapon_air_strike_selected.png"));
			description = "Aliens bewerfen das Ziel mit Atommüll";
			break;
		case 2:
			name = "Erdbeben";
			imgWeapon = ImageIO.read(new URL(codeBase, "lives.png"));
			imgWeapon_selected = ImageIO.read(new URL(codeBase, "fuel.png"));
			description = "Nicht einmal der stärkste Panzer kann dies unbeschadet überstehen";
			break;
		}
	}
	
	public int getID() {
		return id;
	}
	
	public String getName() {
		return name;
	}
	
	public Image getImage(boolean selected) {
		return selected == true ? imgWeapon_selected : imgWeapon;
	}
	
	public String getDescription() {
		return description;
	}
}