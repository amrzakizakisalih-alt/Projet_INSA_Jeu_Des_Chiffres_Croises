package materiel;

/**
 * Représente un jeton avec une valeur numérique.
 */

public class Jeton {
	
    private int valeur ;

    /**
     * Constructeur
     * Crée un jeton avec une valeur donnée
     * @param val Valeur numérique du jeton
     */
     
	  public Jeton(int val){
	      this.valeur=val;
	  }
	  
	  /**
     * @return La valeur numérique du jeton
     */
	  
	  public int getValeur(){
		    return this.valeur ;
	  }
	  /**
     * @return La valeur numérique du jeton sous forme de String
     */
    public String getLettre() {
        return String.valueOf(this.valeur); 
    }
    /**
     * modifie la valeur numérique du jeton
     */
	
	  public void setValeur(int val){
		    this.valeur=val;
	  }
	  
	  /**
     * affiche la valeur numérique du jeton entre crochet
     */
	
	  public void afficheVal(){
	      System.out.printf(" [%2d] \n",this.valeur);
	  }

}
