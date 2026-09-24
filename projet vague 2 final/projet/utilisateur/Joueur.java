package utilisateur;


import java.util.Scanner;
import java.util.List;
import java.util.ArrayList;
import materiel.Sacjetons;
import materiel.Jeton;
import materiel.Plateau;

/**
 * Classe représentant un joueur.
 */


public class Joueur{
    private String nom;
	  private int score;
	  private Chevalet chevalet;
	  private static Plateau plateau;
	  private static Sacjetons sac_jetons;
	  private int nb_jetons_poses=0;
	  
	  /**
     * Constructeur de joueur.
     *
     * @param nom Le nom du joueur.
     * @param score Le score du joueur.
     * @param plateau Le plateau de jeu .
     * @param chevalet Le chevalet.
     */
	
	  public Joueur(String nom, int score, Chevalet chevalet,Plateau plateau){
		    this.nom=nom;
		    this.score=score;
		    this.chevalet=chevalet;
		    this.plateau=plateau;
		  
	  }

    /**
     * Renvoie le chevalet
     * @return Le chevalet (support de jetons) du joueur
     */

    public Chevalet getChevalet() {
        return this.chevalet;
    }

    /**
     * @return Le nombre de jetons posés par le joueur
     */

	  public int getNombre_jet_pose(){
	      return this.nb_jetons_poses;
	  } 

    /**
     * Réinitialise le nombre de jetons posés par le joueur
     */
	  public void effacerNombre_jet_pose(){
	      this.nb_jetons_poses=0;
	  }

    /**
     * Renvoie le nom
     * @return Le nom du joueur
     */
	
	  public String getNom(){
	    	return this.nom;
	  }
	  /**
     * Modifie le nom du joueur
     */
	
	  public void setNom(String nom){
	    	this.nom=nom;
	  }

    /**
     * Renvoie le score
     * @return Le score du joueur
     */
	
	  public int getScore(){
	    	return this.score;
	  }

	  /**
     * Modifie le score du joueur
     */
	
	  public void setScore(int score){
	    	this.score=score;
	  }
	  
	  
    /**
     * Démarre le processus de jeu pour le joueur
     * Gère les interactions pour placer les jetons
     */
		
	  public void jouer(){
	    	Scanner scanner = new Scanner(System.in);
	    	int indice=0;
	    	nb_jetons_poses=0;
	    	while (indice!=-1 && !(chevalet.listeJetonspiochespourjouer.isEmpty())){
	      		chevalet.afficherjetonspioches();
	      		System.out.print("Quelle pièce voulez vous choisir ? (de 1 à "+chevalet.listeJetonspiochespourjouer.size()+", 0 pour sortir) : ");
			      while (!scanner.hasNextInt()) {
			     	  	System.out.println("Erreur : veuillez entrer un entier valide !");
			     	  	scanner.next(); // On ignore l'entrée incorrecte
			     	  	System.out.print("Entrez un entier : ");
				    } 
			      indice= scanner.nextInt()-1;
			      if (indice == -1) {  // L'utilisateur a entre 0 pour sortir
       			    break;
    			  } else if (indice < 0 || indice >= chevalet.listeJetonspiochespourjouer.size()) {  
       			    System.out.println("Erreur, choisissez dans l'intervalle !");
       			    continue;  // Re-demander une saisie sans exÃ©cuter le reste du bloc
   			    }
				
				    int jeton_choisi=chevalet.listeJetonspiochespourjouer.get(indice).getValeur();
				    System.out.print("Ecrivez la ligne et colonne : ");
				    while (!scanner.hasNextInt()) {
			     	  	System.out.println("Erreur : veuillez entrer un entier valide !");
			     	  	scanner.next(); // On ignore l'entrée incorrecte
			     	  	System.out.print("Entrez des entiers : ");
				    } 
				    int scanneri= scanner.nextInt();
				    while (!scanner.hasNextInt()) {
			     	  	System.out.println("Erreur : veuillez entrer des entiers valide !");
			     	  	scanner.next(); // On ignore l'entrée incorrecte
			     		  System.out.print("Entrez des entiers : ");
				    }
				    int scannerj = scanner.nextInt();
				    plateau.modifierCase(scanneri,scannerj,jeton_choisi);
				    System.out.println(plateau.coupOK);
				    if (plateau.coupOK==true){
					      chevalet.listeJetonspiochespourjouer.remove(indice);
					      nb_jetons_poses+=1;
					  }
				
				    plateau.affichage_bof();				
			  }
		
		    if (!(chevalet.listeJetonspiochespourjouer.isEmpty())){
			      chevalet.listeJetons.addAll(chevalet.listeJetonspiochespourjouer);
			      chevalet.listeJetonspiochespourjouer.clear();
		    }
		
	  }
	

}

	
	

