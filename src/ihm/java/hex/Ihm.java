package ihm.java.hex;

import java.util.Iterator;
import java.util.Scanner;

import main.java.hex.Plateau;

public class Ihm implements Iihm {

	@Override
	public void debut(Plateau p) {
		
		
		
		for (int i = 0; i < 2; i++) {
			System.out.println("Joueur " + i + " : Saisie de votre nom");
			Scanner sc = new Scanner(System.in);
			if(sc.hasNext())
				p.getJ()[i].setName(sc.next());
				System.out.println(p.getJ()[i]);
			
		}
		
		
	}

}
