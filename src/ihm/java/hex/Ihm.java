package ihm.java.hex;

import java.util.Scanner;

import jeu.java.hex.Plateau;

public class Ihm implements Iihm {
	private final static int TAILLE_MAX = 26;
	
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
		System.out.println("A " + p.getJ()[ p.getJoueurActuel()] + " de jouer");
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
	public int demanderTaille() {
		Scanner sc = new Scanner(System.in);
		System.out.println("Veuillez indiquer la taille du plateau :");
		if(sc.hasNextInt()) {
			int x = sc.nextInt();
			while(x < 0 || x > TAILLE_MAX) {
				System.out.println("Reesayer");
				x = sc.nextInt();
			}
			return x;
		}
		return 0;
	}

	@Override
	public int demanderModeDeJeu() {
		Scanner sc = new Scanner(System.in);
		System.out.println("Veuillez choisir le mode de jeu :");
		System.out.println("1 - Joueur contre Joueur");
		System.out.println("2 - Joueur contre IA");
		System.out.println("3 - IA contre IA");
		if(sc.hasNextInt() ) {
			int x = sc.nextInt();
			while(x < 0 || x > 3) {
				System.out.println("Saissisez un mode de jeu existant !");
				x = sc.nextInt();
			}
			return x;
		}
		return -1;
	}

	@Override
	public void coupIA(Plateau p, int x, int y) {
		System.out.println("L'ia " + p.getJ()[p.getJoueurActuel()] + " reflechi !!");
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println(p.getJ()[p.getJoueurActuel()] + " viens de jouer a la case : " + getChar(x) + y);
		
		
	}
	
	public static char getChar(int i) {
	    return i<0 || i>25 ? '?' : (char)('A' + i);
	}



}
