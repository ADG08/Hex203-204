package ihm.java.hex;

import main.java.hex.Plateau;

public interface Iihm {
	
	public void debutJeu(Plateau p);

	public void debutTour(Plateau p);
	
	public void afficherPlateau(Plateau p);
	
	public String jouer(Plateau p);
}
