package ihm.java.hex;

import jeu.java.hex.Plateau;

public interface Iihm {
	
	public void debutJeu(Plateau p);

	public void debutTour(Plateau p);
	
	public void afficherPlateau(Plateau p);
	
	public String jouer(Plateau p);

	public void fin(Plateau p, int gagnant);
}
