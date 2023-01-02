package main.java.hex;

import ihm.java.hex.Ihm;
import ihm.java.hex.Iihm;

public class Main {

	public static void main(String[] args) {

		final int taille = 4;
		Iihm i = new Ihm();
		
		Plateau p = new Plateau(taille, i);
		
		while(p.hasWon()) {
			p.jouer(i.jouer(p));
		}

	}

}
