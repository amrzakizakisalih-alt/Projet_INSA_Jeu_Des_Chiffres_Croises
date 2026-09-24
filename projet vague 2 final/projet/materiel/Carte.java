package materiel;

/**
 * Représente une carte avec un numéro.
 */

public class Carte{
    private int numero;
	      
	  /**
	    * Constructeur 
      * Crée une carte avec un numéro spécifique
      * @param val Numéro de la carte
      */
    public Carte( int val){
		    this.numero=val;
	  }
	  
	  /**
     * @return Le numéro de la carte
     */
	  public int getNumero(){
		    return this.numero;
	  }
	  /**
     * Modifie le numero d'une carte
     */
	  public void setNumero(int val){
		    this.numero=val;
	  }

	  /**
     * Affiche le numero d'une carte
     */
	  public void afficheVal(){
	      System.out.printf(" [%2d] ",this.numero);
	  }
	
	  @Override
    public String toString() {
        return "[" + this.getNumero() + "]";
    }


			
}

