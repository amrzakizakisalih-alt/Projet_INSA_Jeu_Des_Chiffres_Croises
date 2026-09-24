package materiel;

import java.util.ArrayList;
import java.util.List;

/**
 * Gestion d'un paquet de cartes numérotées.
 * Contient les opérations de tirage et de manipulation des cartes.
 */

public class Paquet_cartes{
    private List<Carte> paquet_carte = new ArrayList<>();
	  public List<Carte> carte_tire = new ArrayList<>();
	  public static int taille = 36;
	  /**
     * Constructeur du paquet de cartes.
     * Crée le paquet avec 36 cartes
     */
	  public Paquet_cartes(){
	      for (int i=15;i<51;i++){
	        	Carte carte = new Carte(i);
	        	paquet_carte.add(carte);
	      }
	  }
	  
	  /**
     * renvoie la taille du paquet de carte
     */ 
	
	  public int getNombreCartes() {
		    return this.paquet_carte.size();
	  }
	  /**
     * Modifie la valeur d'une carte tire (utiliser lors des tests)
     */ 
    public void setCartAdmin(int val){
        this.carte_tire.get(1).setNumero(val);
    }
    
    /**
     * Récupère une carte spécifique
     * @param indice Position dans le paquet
     * @return La carte demandée ou null si hors limites
     */ 
     
    public Carte getCarte(int indice) {
        if (indice >= 0 && indice < taille) {
            return this.paquet_carte.get(indice);
        } else {
            System.out.println("Erreur : Indice invalide !");
            return null;
        }
    }
	  
	  /**
     * Tire 3 cartes aléatoires du paquet
     */
     
    public void tirage(){
        for(int i=0;i<3;i++){
            int randomInt = (int) (Math.random() * paquet_carte.size());
            this.carte_tire.add(paquet_carte.get(randomInt));
        }
    }
    
    /**
     * affiche le paquet
     */
	
	  public void afficher_paquet(){
	      for (int i=0;i<this.taille;i++){
		  	    this.paquet_carte.get(i).afficheVal();  
	      }
  	}
  	
    /**
     * affiche les cartes tires
     */
  	
    public void afficher_carte_tire(){
        if (carte_tire.isEmpty()) {
            System.out.println("Aucune carte tirée !");
        } else {
            for (Carte carte : carte_tire) {
                carte.afficheVal();
            }
        }
    }

}
	


