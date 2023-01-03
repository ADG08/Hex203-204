package jeu.java.hex;

import java.util.Random;
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
	private int x;
	private int y;

	private void suivant() {
		joueurActuel = (joueurActuel +1) % NB_JOUEURS;
		
	}
	
	public void jouer(String coord) {
		assert estValide(coord);
		if (getCase(coord) != Pion.Vide) {
			System.out.println("Vous ne pouvez pas posez votre pion en : " + coord);
			return;
			
		}
		
		
		Pion pion = Pion.values()[joueurActuel];
		int col = getColonne (coord);
		int lig = getLigne(coord);
		t[col][lig] = pion;
		ihm.afficherPlateau(this);
		x = getColonne(coord);
		y = getLigne(coord);
		hasWon();
		suivant();
	}
	
	public void jouerIA() {
		ihm.debutTour(this);
		
		
		int min = 0;
		int max = taille();

		Random random = new Random();		
		
		int x = random.nextInt(max + min) + min;
		int y = random.nextInt(max + min) + min;
		Pion pion = Pion.values()[joueurActuel];
		t[x][y] = pion;
		
		ihm.coupIA(this, x, y);
		
		
		suivant();
		ihm.afficherPlateau(this);
		
		
		
		
		
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
		int[][] Casevisitee = new int[taille()][taille()];
		
		//crée un tableau de case visitee
		for (int lig = 0; lig < taille(); ++lig)
			for (int col = 0; col < taille(); ++col)
				Casevisitee[col][lig] = 0;

		//permet de ne pas entrer dans une analyse poussée alors qu'il n'y a aucun pion dans la rangée de debut et/ou de fin
		int nbPionsPremiereRange = 0;
		int nbPionsDernierRange = 0;

		//compte le nb de pion pour le joueur actuel (ici le 0) dans les deux rangées du dessus
		if (typePionJoueur.equals("X")){
			for (int i = 0; i < taille(); i++){
				if (typePionJoueur.equals(t[i][0].toString())){
					nbPionsPremiereRange++;
				}
				if (typePionJoueur.equals(t[i][taille() - 1].toString())){
					nbPionsDernierRange++;
				}
			}
			
			//si il y a au moins 1 pion X par rangée...
			if (nbPionsPremiereRange > 0 && nbPionsDernierRange > 0){
				for (int i = 0; i < taille(); i++){
					//...on cherche les pions X de la premiere rangée
					if (typePionJoueur.equals(t[i][0].toString())){
						Casevisitee[i][0] = 1; //on a trouvé une case donc on signal qu'on l'a visité
						asAdjacent(i, 0, typePionJoueur, Casevisitee); // on va regardé si un chemin de victoire existe a partir de ce pion
					}
				}
			}else { // si il y a 0 pion dans la premiere et/ou derniere rangee
				return false;
			}

		}else if(typePionJoueur.equals("O")){ //compte le nb de pion pour le joueur actuel (ici le 1) dans les deux rangées du dessus
			for (int i = 0; i < taille(); i++){
				if (typePionJoueur.equals(t[0][i].toString())){
					nbPionsPremiereRange++;
				}
				if (typePionJoueur.equals(t[taille() - 1][i].toString())){
					nbPionsDernierRange++;
				}
			}
			
			//si il y a au moins 1 pion O par rangée...
			if (nbPionsPremiereRange > 0 && nbPionsDernierRange > 0){
				for (int i = 0; i < taille(); i++){
					//...on cherche les pions O de la premiere colonne
					if (typePionJoueur.equals(t[0][i].toString())){
						Casevisitee[0][i] = 1; //on a trouvé une case donc on signal qu'on l'a visité
						asAdjacent(0, i, typePionJoueur, Casevisitee); // on va regardé si un chemin de victoire existe a partir de ce pion
					}
				}
			}else { //si il n'y a pas de O, on quitte has won
				return false;
			}
	
		}return false; //on quitte car les conditions de recherche ne sont pas remplies
	} 


	// on regarde les cases adjacentes a la case donnée
	private void asAdjacent(int x, int y, String pion, int[][] dejaFait){
		int basY = y + 1;
		int basGaucheX = x - 1;
		int basGaucheY = y + 1;
		int gaucheX =  x - 1;
		int hautY = y - 1;
		int hautDroitX = x + 1;
		int hautDroitY = y - 1;
		int droit = x + 1;

		if (pion.equals("X") && y == taille() - 1){ //si on arrive a la derniere ligne et que le pion est X, le joueur 0 a gagné
			fin(0);
		}else if (pion.equals("O") && x == taille() - 1){ //si on arrive a la derniere colonne et que le pion est O, le joueur 1 a gagné
			fin(1);
		}else{
			if (basY < taille() && pion.equals(t[x][basY].toString()) && dejaFait[x][basY] == 0){ //verifie que la case du bas a le signe voulu et qu'elle a pas deja été visitée
				dejaFait[x][basY] = 1;
				asAdjacent(x, basY, pion, dejaFait);
			}
				
			if (basGaucheX >= 0 && basGaucheY < taille() && pion.equals(t[basGaucheX][basGaucheY].toString()) && dejaFait[basGaucheX][basGaucheY] == 0){ //verifie que la case en bas a gauche a le signe voulu et qu'elle a pas deja été visitée
				dejaFait[basGaucheX][basGaucheY] = 1;
				asAdjacent(basGaucheX, basGaucheY, pion, dejaFait);
			}

			if (gaucheX >= 0 && pion.equals(t[gaucheX][y].toString()) && dejaFait[gaucheX][y] == 0){ //verifie que la case a gauche a le signe voulu et qu'elle a pas deja été visitée
				dejaFait[gaucheX][y] = 1;
				asAdjacent(gaucheX, y, pion, dejaFait);
			}

			if (hautY >= 0 && pion.equals(t[x][hautY].toString()) && dejaFait[x][hautY] == 0){ //verifie que la case du haut a le signe voulu et qu'elle a pas deja été visitée
				dejaFait[x][hautY] = 1;
				asAdjacent(x, hautY, pion, dejaFait);
			}

			if (hautDroitX < taille() && hautDroitY >= 0 && pion.equals(t[hautDroitX][hautDroitY].toString()) && dejaFait[hautDroitX][hautDroitY] == 0){ //verifie que la case en haut a droite a le signe voulu et qu'elle a pas deja été visitée
				dejaFait[hautDroitX][hautDroitY] = 1;
				asAdjacent(hautDroitX, hautDroitY, pion, dejaFait);
			}

			if (droit < taille() && pion.equals(t[droit][y].toString()) && dejaFait[droit][y] == 0){ //verifie que la case a droite a le signe voulu et qu'elle a pas deja été visitée
				dejaFait[droit][y] = 1;
				asAdjacent(droit, y, pion, dejaFait);
			}
		}
	}

	private void fin(int i) {
		ihm.fin(this, i);
		System.exit(0);
	}
}
