package ihm.java.hex;

import java.util.Scanner;

import jeu.java.hex.Plateau;

public class Ihm implements Iihm {

	@Override
	public void debutJeu(Plateau p) {
		
		for (int i = 0; i < p.getJ().length; i++) {
			System.out.println("Joueur " + i + " : Saisie de votre nom");
			Scanner sc = new Scanner(System.in);
			if(sc.hasNext())
				p.getJ()[i].setName(sc.next());
				System.out.println(p.getJ()[i]);
			
		}
		
		
	}

	@Override
	public void debutTour(Plateau p) {
		System.out.println("� " + p.getJ()[p.getJoueurActuel()] + " de jouer");
	}

	@Override
	public void afficherPlateau(Plateau p) {
		System.out.println(p);
		
	}

	@Override
	public String jouer(Plateau p) {
		debutTour(p);
		Scanner sc = new Scanner(System.in);
		if(sc.hasNext()) {
			return sc.next();
		}
		return null;
	}

	@Override
	public void fin(Plateau p) {
		System.out.println(p.getJ()[p.getJoueurActuel()] + " a gagne");
	}

}
