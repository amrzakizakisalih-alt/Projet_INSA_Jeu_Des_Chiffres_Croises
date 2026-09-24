import java.util.Scanner;
import src.Systeme;
import materiel.Plateau;

public class main {

    
    public static void main(String[] args) { 
        System.out.println("Saisir le nombre de joueur : ");
        Scanner scanner=new Scanner(System.in);
        while (!scanner.hasNextInt()) {
			      System.out.println("Erreur : veuillez entrer un entier valide !");
			      scanner.next(); // On ignore l'entrée incorrecte
			      System.out.print("Entrez un entier : ");
				}
        int choix = scanner.nextInt();
        Plateau plateau= new Plateau();
        Systeme systeme = new Systeme(choix,plateau);
        // Affichage de l'état du plateau
	      systeme.gestionDesToursJoueurs();
        
        
    }

		

}
