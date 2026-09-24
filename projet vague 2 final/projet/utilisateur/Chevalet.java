package utilisateur;

import materiel.Sacjetons;
import materiel.Jeton;
import java.util.Scanner;
import java.util.List;
import java.util.ArrayList;
import java.util.Collections;

/**
 * Gestion du chevalet (support de jetons) d'un joueur.
 * Contient les opérations d'échange et de sélection des jetons.
 */
 
public class Chevalet{
    public static Sacjetons sac_jetons;
	  public List<Jeton> listeJetons = new ArrayList<>();
	  private static int capacite=9;
	  public List<Jeton> listeJetonspiochespourjouer = new ArrayList<>();

    /**
     * Renvoie le nombre de jetons présents dans le chevalet
     */
	  public int getNombreJetons(){
		    return this.listeJetons.size();
	  }
	  
    /**
     * Remplit le chevalet avec 6 jetons piochés aléatoirement
     */
	  public void piocher(){
		    for (int i=0;i<6;i++){
		      	int indice_aleatoire = (int) (Math.random() * sac_jetons.getTaille());
		      	this.listeJetons.add(sac_jetons.getJeton(indice_aleatoire));
			  }
	  }
	  
    /**
     * Affiche les jetons présents dans le chevalet avec leur indice.
     */
	  public void afficherChev() {
	      int c=1;
        for (Jeton j : listeJetons) {
            System.out.print(c+" : ");
            c++;
            j.afficheVal();
        }
    }

    /**
     * Renvoie le chevalet sous forme de string
     */
    public String getStringChevalet() {
        String result = "";
        for (int i = 0; i < this.listeJetons.size(); i++) {
            result += "   [ " + this.listeJetons.get(i).getValeur()+ " ]   ";
        }
        return result;
    }

    /**
     * Affiche les jetons pioche du sac à jetons
     */
    
    public void afficherjetonspioches() {
        for (Jeton j : listeJetonspiochespourjouer) {
            j.afficheVal();
        }
    }

    /**
     * Permet d'échanger des jetons entre le chevalet et le sac, (3-2-1) contre 2 jetons du sac.
     */
     
	  public void echangerJetons(){
		    List<Jeton> listeJetonspioches = new ArrayList<>();
        List<Jeton> listeJetonsSelect = new ArrayList<>();
		    Scanner scanner = new Scanner(System.in); //important classe Scanner du package util
		    int verif;
		    
		    for (int i=0;i<2;i++){
		        int indice_aleatoire = (int) (Math.random() * sac_jetons.getTaille());
			      listeJetonspioches.add(sac_jetons.getJeton(indice_aleatoire));
			      sac_jetons.supprimerJeton(indice_aleatoire);
		    }
		    System.out.println("La pioche : ");
		    listeJetonspioches.get(0).afficheVal();
		    listeJetonspioches.get(1).afficheVal();
		    System.out.println("Selectionner vos jetons");
		
		
		    do{
		
		        System.out.println("Saisir le nombre de jetons que vous voulez echanger : ");
		        afficherChev();
		        while (!scanner.hasNextInt()) {
                System.out.println("Erreur : veuillez entrer un entier valide !");
                scanner.next(); // On ignore l'entrée incorrecte
                System.out.println("Entrez un entier : ");
            }
       	  
			      int nbr_jetons = scanner.nextInt();
			      verif= 2-nbr_jetons+getNombreJetons();
			      if(verif>this.capacite || nbr_jetons>3 || nbr_jetons<=0){
			          System.out.println("Erreur : Depassement capacite(9)");
			          verif=10;
			      }else{
			          for(int i=0;i<nbr_jetons;i++){
				            System.out.println("Saisir l'indice du jeton : ");
				            afficherChev();
				            while (!scanner.hasNextInt()) {
			     		          System.out.println("Erreur : veuillez entrer un entier valide !");
			     		          scanner.next(); // On ignore l'entrée incorrecte
			     		          System.out.println("Entrez un entier : ");
				            }
				            int indice_jetons = scanner.nextInt()-1;			
				            if (indice_jetons<0 || indice_jetons>nbr_jetons){
				  	            System.out.print("Erreur choisissez dans l'intervalle");
				  	            i-=1;
				  	        }else{
				  	            listeJetonsSelect.add(this.listeJetons.get(indice_jetons));
				                this.listeJetons.remove(this.listeJetons.get(indice_jetons));}
			              }
			              listeJetons.addAll(listeJetonspioches);
			              sac_jetons.ajoutJetons(listeJetonsSelect);
			              break;
			        }  

		      }while(verif>this.capacite);
	  
    }
	
    /**
     * Sélectionne des jetons à utiliser pendant le tour
     */
     
    public void utiliser_jetons(){
		    List<Integer> stockage_indice=new ArrayList<>();
		    System.out.println("Quels jetons voulez-vous utiliser, donner l'indice entre 1 et "+this.listeJetons.size() +", 0 pour sortir : ");
		    Scanner scanner= new Scanner(System.in);
		    int choix_indice=0;
		    while (true){
		        while (!scanner.hasNextInt()) {
			     	    	System.out.println("Erreur : veuillez entrer un entier valide !");
			     	    	scanner.next(); // On ignore l'entrée incorrecte
			     	    	System.out.println("Entrez un entier : ");
			      }   
			      choix_indice = scanner.nextInt() - 1; // Décalage pour correspondre aux indices de la liste

			      if (choix_indice == -1) {
			          System.out.println("Sortie du choix des jetons.");
			          break;
			      }

			      if (choix_indice < 0 || choix_indice >= listeJetons.size()) {
			          System.out.println("Erreur : indice invalide.");
			          continue;
			      }
			
			      if (!(stockage_indice.contains(choix_indice))){
				        stockage_indice.add(choix_indice);
				        this.listeJetonspiochespourjouer.add(this.listeJetons.get(choix_indice));
				    }
			      else{
				        System.out.println("Erreur choisissez un jeton qui n'est pas déjà sélectionné");
				    }
			  }
		    stockage_indice.sort(Collections.reverseOrder());
        for (int index : stockage_indice) {
            this.listeJetons.remove(index);
        }
		}
		
		
}
				
			
	
