package appli.java.hex;

import ihm.java.hex.Ihm;
import ihm.java.hex.Iihm;
import main.java.hex.Plateau;

public class Main {

	public static void main(String[] args) {

		final int taille = 4;
		Iihm i = new Ihm();
		
		Plateau p = new Plateau(taille, i);
		
		while(true) {
			p.jouer(i.jouer(p));
		}

	}

}
