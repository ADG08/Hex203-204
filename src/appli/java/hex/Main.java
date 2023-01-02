package appli.java.hex;

import ihm.java.hex.Ihm;
import ihm.java.hex.Iihm;
import jeu.java.hex.Plateau;

public class Main {

	public static void main(String[] args) {

		Iihm i = new Ihm();
		Plateau p;
		
		int x = i.demanderModeDeJeu();
		int taille = i.demanderTaille();
		
<<<<<<< Updated upstream
		while(true) {
			p.jouer(i.jouer(p));
=======
		if(x == 1) {
			p = new Plateau(taille, i);
			
			while(true) {
				p.jouer(i.jouer(p));
			}
			
		}else if(x == 2) {
			p = new Plateau(taille, i);
			
			while(true) {
				p.jouer(i.jouer(p));
				p.jouerIA();
			}
>>>>>>> Stashed changes
		}
		else if(x == 3) {
			p = new Plateau(taille, i);
			while(true) {
				p.jouerIA();
			}
		}
		
		
		
		

	}

}
