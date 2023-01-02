package appli.java.hex;

import ihm.java.hex.Ihm;
import ihm.java.hex.Iihm;
import main.java.hex.Plateau;

public class Main {

	public static void main(String[] args) {

		Iihm i = new Ihm();
    
		Plateau p = null;

		int x = i.demanderModeDeJeu();
		int taille = i.demanderTaille();

			if (x == 1) {
				p = new Plateau(taille, i);

				while (true) {
					p.jouer(i.jouer(p));
				}

			} else if (x == 2) {
				p = new Plateau(taille, i);

				while (true) {
					p.jouer(i.jouer(p));
					p.jouerIA();
				}
			} else if (x == 3) {
				p = new Plateau(taille, i);
				while (true) {
					p.jouerIA();
				}
			}

		}

	}