package jeu.java.hex;

import org.junit.jupiter.engine.descriptor.TestMethodTestDescriptor;

import javafx.util.Pair;
import java.util.*;

import ihm.java.hex.Iihm;

public class Plateau {
	private final static int TAILLE_MAX = 26;
	private final static int NB_JOUEURS = 2;
	private final static int PREMIERE_COLONNE = 'A';
	private final static int PREMIERE_LIGNE = '1';
	

	// le premier joueur relie la premiere et la derniere ligne
	// le second joueur relie la premiere et la derniere colonne
	
	private Pion[][] t;
	private Joueur[] j;
	private int joueurActuel;
	private Iihm ihm;
	private int dernierPionx;
	private int dernierPiony;

	private void suivant() {
		joueurActuel = (joueurActuel +1) % NB_JOUEURS;
		
	}
	
	public void jouer(String coord) {
		assert estValide(coord);
		assert getCase(coord) == Pion.Vide;
		Pion pion = Pion.values()[joueurActuel];
		int col = getColonne (coord);
		int lig = getLigne(coord);
		t[col][lig] = pion;
		ihm.afficherPlateau(this);
		dernierPionx = getLigne(coord);
		dernierPiony = getColonne(coord);
		suivant();
	}
	
	public static int getTaille(String pos) {
		int taille = (int) Math.sqrt(pos.length());
		assert taille * taille == pos.length();
		return taille;
	}

	public boolean estValide(String coord) {
		if ( coord.length() !=2)
			return false;
		int col = getColonne (coord);
		int lig = getLigne(coord);
		if (col <0 || col >= taille())
			return false;
		if (lig <0 || lig >= taille())
			return false;
		return true;
	}
	
	public Pion getCase(String coord) {
		assert estValide(coord);
		int col = getColonne (coord);
		int lig = getLigne(coord);
		return t[col][lig];
	}

	private int getColonne(String coord) {
		return coord.charAt(0) - PREMIERE_COLONNE; // ex 'B' -'A' == 1
	}
	
	private int getLigne(String coord) {
		return coord.charAt(1) - PREMIERE_LIGNE; // ex '2' - '1' == 1
	}

	public Plateau(int taille, Iihm ihm) {
		assert taille > 0 && taille <= TAILLE_MAX;
		t = new Pion [taille][taille];
		
		for (int lig = 0; lig < taille(); ++lig)
			for (int col = 0; col < taille(); ++col)
				t[col][lig] = Pion.Vide;
		
		this.ihm = ihm;
		j = new Joueur[2];
		
		for (int i = 0; i < j.length; i++) {
			j[i] = new Joueur();
		}
		
		ihm.debutJeu(this);
	}
	
	public Joueur[] getJ() {
		return j;
	}

	public Plateau(int taille, String pos, Iihm ihm) {
		assert taille > 0 && taille <= TAILLE_MAX;
		t = new Pion [taille][taille];
		
		String[] lignes = decouper(pos);
		
		for (int lig = 0; lig < taille(); ++lig)
			for (int col = 0; col < taille(); ++col)
				t[col][lig] = 
				  Pion.get(lignes[lig].charAt(col));
		
		if (getNb(Pion.Croix) != getNb(Pion.Rond) &&
			getNb(Pion.Croix) != (getNb(Pion.Rond)+1) &&
					getNb(Pion.Croix) != (getNb(Pion.Rond)-1))
			throw new IllegalArgumentException(
					"position non valide");
		
		this.ihm = ihm;
		j = new Joueur[2];
		
		for (int i = 0; i < j.length; i++) {
			j[i] = new Joueur();
		}
		
		ihm.debutJeu(this);
	}

	public int getNb(Pion pion) {
		int nb = 0;
		for (Pion [] ligne : t)
			for (Pion p : ligne)
				if (p == pion)
					++nb;
		return nb;
	}

	public int taille() {
		return t.length;
	}
	
	private String espaces(int n) {
		String s = "";
		for(int i = 0; i < n; ++i)
			s+= " ";
		return s;
	}
	
	@Override
	public String toString() {
		String s = "";
		for (int i = 0; i < taille(); ++i)
			s+=" "+(char)( 'A' + i);
		s+='\n';
		for (int lig = 0; lig < taille(); ++lig) {
			s+= lig+1 + espaces (lig);
			for (int col = 0; col < taille(); ++col)
				s+= " "+ t[col][lig];
			s+='\n';
		}
		return s;
	}

	public static String[] decouper(String pos) {
		int taille = getTaille(pos);
		String[] lignes = new String[taille];
		for (int i = 0; i <taille; ++i)
			lignes[i] = pos.substring(i*taille,
					(i+1)*taille);
		return lignes;
	}
	

	public int getJoueurActuel() {
		return joueurActuel;
	}

	public boolean hasWon() {
		String typePionJoueur = Pion.values()[joueurActuel].toString();
		int[][] pionsJoueur = new int[taille()][taille()];
		for (int lig = 0; lig < taille(); ++lig)
            for (int col = 0; col < taille(); ++col)
                pionsJoueur[col][lig] = 0;
		pionsJoueur = pionsPlacesParLeJoueur(dernierPionx, dernierPiony, typePionJoueur);

		
		return false ;
	} 
    
    private boolean VerifCaseAdj(int[][] tab, int x, int y) {
		if (tab[x][y] == 1){
			 return true;
		}

		return false;
    }

	private int[][] pionsPlacesParLeJoueur(int x, int y, String pion){
		int[][] tab = new int[taille()][taille()];
		tab[x][y] = 1;

		int basY = y + 1;
		int basGaucheX = x +1;
		int basGaucheY = y +1;
		int gaucheX = x + 1;
		int hautY = y - 1;
		int hautDroitX = x -1;
		int hautDroitY = y - 1;
		int droit = x - 1;

		if (basY < taille() && t[x][basY].toString() == pion)
			tab[x][basY] = 1;

		if (basGaucheX < taille() && basGaucheY < taille() && t[basGaucheX][basGaucheY].toString() == pion)
			tab[basGaucheX][basGaucheY] = 1;

		if (gaucheX < taille() && t[gaucheX][y].toString() == pion)
			tab[gaucheX][y] = 1;
		
		if (hautY >= 0 && t[x][hautY].toString() == pion)
			tab[x][hautY] = 1;

		if (hautDroitX >= 0 && hautDroitY >= 0 && t[hautDroitX][hautDroitY].toString() == pion)
			tab[hautDroitX][hautDroitY] = 1;
		
		if (droit >= 0 && t[droit][y].toString() == pion)
			tab[basGaucheX][basGaucheY] = 1;
			
		return tab;
	}
}

