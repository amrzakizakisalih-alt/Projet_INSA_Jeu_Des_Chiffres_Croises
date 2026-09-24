package src;

import java.util.Scanner;
import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;

import materiel.Sacjetons;
import materiel.Carte;
import materiel.Paquet_cartes;
import materiel.Jeton;
import materiel.Plateau;
import utilisateur.Joueur;
import utilisateur.Chevalet;

/**
 * Classe principale qui gère l'ensemble du système de jeu.
 * Elle coordonne le plateau, les joueurs, les cartes et les jetons.
 */

public class Systeme {

	  /** Plateau de jeu partagé entre tous les joueurs */
    public static Plateau plateau;

    /** Sac contenant tous les jetons du jeu */
    public static Sacjetons sac_jetons = new Sacjetons();

    /** Paquet contenant toutes les cartes à tirer */
    public static Paquet_cartes paquet_cartes = new Paquet_cartes();

    /** Liste des joueurs participant à la partie */
    private List<Joueur> listeJoueur = new ArrayList<>();

    /** Présence de connexions : gauche, droite, haut, bas */
    private boolean[] presance;

	  
	  /**
     * Constructeur du système de jeu.
     *
     * @param nbjoueur Le nombre de joueurs participant.
     * @param plateau Le plateau de jeu utilisé.
     */
    public Systeme(int nbjoueur,Plateau plateau) {
        Scanner scanner = new Scanner(System.in); 
        presance = new boolean[4];
        Chevalet.sac_jetons=sac_jetons;
        sac_jetons.remplissage_sac();
        this.plateau=plateau;
    
    
        for (int i = 0; i < nbjoueur; i++) {
            System.out.printf("\nSaisir le nom du joueur %d : ", i+1);
            String nom = scanner.nextLine(); // Utilisation correcte de nextLine()

            Chevalet chevalet = new Chevalet();
            Joueur joueur = new Joueur(nom, 0, chevalet,this.plateau);
            joueur.getChevalet().piocher();
            this.listeJoueur.add(joueur);
        }
        paquet_cartes.tirage();
        
    }
    
    /**
     * Récupère la liste des joueurs.
     *
     * @return Une liste d'objets Joueur.
     */
    public List<Joueur> getListeJoueur() {
        return this.listeJoueur;
    }
    
    /**
     * Gère les tours de jeu successifs pour chaque joueur
     * jusqu'à la fin de la partie.
     */
  
    public void gestionDesToursJoueurs(){
        boolean partieEnCours = true;
		    boolean couppossible=true;
		    Scanner scanner=new Scanner(System.in);
			
		    while(couppossible && partieEnCours ){
		        
		        //paquet_cartes.afficher_carte_tire();				
			      for (int i=0;i<this.listeJoueur.size();i++){
				        plateau.affichage(this.listeJoueur,this.listeJoueur.get(i));
				        System.out.println("\nAu tour du joueur "+"  "+this.listeJoueur.get(i).getNom());
				        //this.listeJoueur.get(i).getChevalet().afficherChev();
				        System.out.println("\nQue voulez-vous faire ? \n 1. Echanger des jetons \n 2. Jouer :");
				        while (!scanner.hasNextInt()) {
			     		      System.out.println("Erreur : veuillez entrer un entier valide !");
			     		      scanner.next(); // On ignore l'entrée incorrecte
			     		      System.out.println("Entrez un entier : ");
				        }
				      
				      int choix1= scanner.nextInt();
				      if (choix1==1){
					        this.listeJoueur.get(i).getChevalet().echangerJetons();}
				      else{
					        this.listeJoueur.get(i).getChevalet().utiliser_jetons();
					        plateau.effacerMemoire();
					        this.listeJoueur.get(i).jouer();
						      for (int w = 0; w < plateau.TAILLE; w++) {
        			  			int c = plateau.getMemoireVive(w, 0);
        			  			int v = plateau.getMemoireVive(w, 1);
        			  			//System.out.println("x: "+w+" k: "+c+" z: "+v);
        			  	}
        			  							
					        if (this.verification()){
					            this.miseAJourScore(this.listeJoueur.get(i));
						          plateau.effacerMemoire();
						      }
						  
					        if (this.listeJoueur.get(i).getChevalet().getNombreJetons()==0){
					            System.out.println("Joueur   "+ this.listeJoueur.get(i).getNom()+ "  éliminé");
						          this.listeJoueur.remove(i);
						      }
  
						  
					        if (!this.verification()){
						          System.out.println("\nMauvais coup, jouez autrement\n");
						          
						          for (int x = 0; x < plateau.TAILLE; x++) {
        			  			    int k = plateau.getMemoireVive(x, 0);
        			  			    int z = plateau.getMemoireVive(x, 1);
        			  			
        			  			    if (k != -1 && z != -1) {
        			  				      Jeton jeton = new Jeton(plateau.getCase(k,z));
        			  				      this.listeJoueur.get(i).getChevalet().listeJetons.add(jeton);
        			  				      plateau.modifierCase(k,z,0);
        			  			
        			  			    }
						          }
					            plateau.effacerMemoire();
					       }   
							  
						      if (sac_jetons.getTaille()==0){
							        System.out.println("Pouvez-vous encore jouer? (1=Oui/0=Non) :");
							        while (!scanner.hasNextInt()) {
			     		  			    System.out.println("Erreur : veuillez entrer un entier valide !");
			     		  			    scanner.next(); // On ignore l'entrée incorrecte
			     		  			    System.out.print("Entrez un entier : ");
				  		  	    }
							        int choix=scanner.nextInt();
							        if (choix==0){
							  	        couppossible=false;
							        }
							  	
						      }
					    }
					}
					partieEnCours = verifierConditionsContinuerPartie();
	  	}
	  	
	  	gererFinDePartie();
					
			
    }
    
    /**
     * Gère l'affichage de fin de partie.
     */
     
    public void gererFinDePartie() {


        // Calculer les scores
        Joueur vainqueur = null;
        int meilleurScore = -1;

        for (Joueur joueur : listeJoueur) {
            if (joueur.getScore() > meilleurScore) {
                meilleurScore = joueur.getScore();
                vainqueur = joueur;
            }
        }

        //   Afficher le résultat
        System.out.println("\n--- FIN DE PARTIE ---");
        System.out.println("Raison de l'arrêt : " + getRaisonArret());
    
        if (vainqueur != null) {
            System.out.println("Vainqueur : " + vainqueur.getNom() + " avec " + meilleurScore + " points !");
        } else {
            System.out.println("Aucun vainqueur !");
        }

        // Afficher tous les scores
        System.out.println("\nScores finaux :");
        for (Joueur joueur : listeJoueur) {
            System.out.println("- " + joueur.getNom() + " : " + joueur.getScore());
        }
    }

    /**
     * Donne la raison de l'arrêt du jeu
     */
    
    private String getRaisonArret() {
        if(listeJoueur.isEmpty()) return "Tous les joueurs éliminés";
        if(!plateau.estComplet()) return "Plateau rempli";
        if(sac_jetons.getTaille()==0 && !peutEncoreJouer()) return "Plus de jetons disponibles";
        return "Arrêt volontaire des joueurs";
    }

    /**
     * Vérifie si une condition d'arrêt est atteinte
     *
     * @return false si l'une des conditions est atteinte, sinon false.
     */
    
    private boolean verifierConditionsContinuerPartie() {
        // Vérifier si tous les joueurs sont éliminés
        if(listeJoueur.isEmpty()) {
            return false;
        }

        //  Vérifier si le plateau est complet
        if(!plateau.estComplet()) return false;

        // Vérifier s'il reste des coups possibles
        if(sac_jetons.getTaille()==0 && !peutEncoreJouer()) {
            return false;
        }

        // 4. Demander aux joueurs s'ils veulent continuer
        if(demanderArretPartie()) {
            return false;
        }

        return true;
    }

    /**
     * Vérifie si les joueurs peuvent encore jouer
     *
     * @return true si oui, sinon false.
     */

    private boolean peutEncoreJouer() {
        for(Joueur joueur : listeJoueur) {
            if(joueur.getChevalet().getNombreJetons() > 0) {
                return true;
            }
        }
        return false;
    }

    /**
     * Demande aux joueurs s'ils veulent encore jouer
     *
     * @return true si oui, sinon false.
     */

    private boolean demanderArretPartie() {
        System.out.println("Voulez-vous arrêter la partie ? (1=Oui/0=Non) : ");
        Scanner scanner=new Scanner(System.in);
        while (!scanner.hasNextInt()) {
			     	System.out.println("Erreur : veuillez entrer un entier valide !");
			     	scanner.next(); // On ignore l'entrée incorrecte
			     	System.out.print("Entrez un entier : ");
				}
        int choix = scanner.nextInt();
        return choix == 1;
    }
    
  
  
	  /**
     * Vérifie si les jetons placés par un joueur forment une combinaison valide.
     *
     * @return true si les combinaisons sont valides, sinon false.
     */
  
    public boolean verification() {
        int verif = 0;
        boolean verifLigne;
        boolean verifCol;
        int i_prec = -1;  // Initialisation
        int j_prec = -1;  // Initialisation
        boolean verifLigne_prec = false;  // Initialisation
        boolean verifCol_prec = false;    // Initialisation
        boolean connexionHorizontale;
        boolean connexionVerticale;
    
        // Parcours des positions en mémoire vive
        for (int x = 0; x < plateau.TAILLE; x++) {
            int i = plateau.getMemoireVive(x, 0);
            int j = plateau.getMemoireVive(x, 1);
        
        
        
            if (i != -1 && j != -1) {
                // Vérifier si on peut réutiliser les résultats précédents
                if (x != 0 && i_prec == i) {
                    verifLigne = verifLigne_prec;
                    verif_ligne(i, j);
                } else {
                    verifLigne = verif_ligne(i, j);
                }
            
                if (x != 0 && j_prec == j) {
                    verifCol = verifCol_prec;
                    verif_col(i, j); // Met à jour presance même si on ne change pas verifCol
                } else {
                    verifCol = verif_col(i, j);
                }

                connexionHorizontale = this.presance[0] || this.presance[1];
                connexionVerticale = this.presance[2] || this.presance[3];
            
                boolean ligneValide = verifLigne || connexionVerticale;
                boolean colonneValide = verifCol || connexionHorizontale;
            
                if (!ligneValide || !colonneValide) {
                    verif++;
                }
                // Sauvegarder les valeurs pour la prochaine itération
                i_prec = i;
                j_prec = j;
                verifLigne_prec = verifLigne;
                verifCol_prec = verifCol;
            }
        }
    
        if(verif==0) return true;
        return false;
    }
  
    /**
     * Vérifie la validité d'une colonne contenant un jeton récemment placé.
     *
     * @param l Ligne du jeton placé.
     * @param c Colonne du jeton placé.
     * @return true si la somme de la colonne correspond à une carte tirée.
     */
  
    public boolean verif_col(int l, int c) {
        if (l < 0 || l >= plateau.TAILLE || c < 0 || c >= plateau.TAILLE) {
            return false;
        }

        int debut = l, fin = l, verif = 0;

        // Chercher la limite supérieure
        for (int i = l - 1; i >= 0; i--) {
            if (plateau.getCase(i, c) != 0){
                debut = i;
                
            }else {
                break;
            }
        }

        // Chercher la limite inférieure
        for (int i = l + 1; i < plateau.TAILLE; i++) {
            if (plateau.getCase(i, c) != 0){
                fin = i;
            }else {
                break;
            }
        }



        for (int i = debut; i <= fin; i++) {
            verif += plateau.getCase(i, c);
        }

        this.presance[2] = false;
        this.presance[3] = false;
        if (debut != l) this.presance[2] = true;  // Il y a un élément au-dessus
        if (fin != l) this.presance[3] = true;    // Il y a un élément en dessous
    
        for (int i = 0; i < 3; i++) {
            if (verif == paquet_cartes.carte_tire.get(i).getNumero()) {
                return true;
            }
        }
    return false;
    }

    /**
     * Vérifie la validité d'une ligne contenant un jeton récemment placé.
     *
     * @param l Ligne du jeton placé.
     * @param c Colonne du jeton placé.
     * @return true si la somme de la ligne correspond à une carte tirée.
     */
	
    public boolean verif_ligne(int l, int c) {
        if (l < 0 || l >= plateau.TAILLE|| c < 0 || c >= plateau.TAILLE) {
            return false;
        }
      
        // Trouver les limites de la séquence non-vide
        int debut = c;
        int fin = c;
        int verif = 0;
     
       // Chercher la limite à gauche
        for (int j = c - 1; j >= 0; j--) {
           if (plateau.getCase(l,j) != 0)
               debut = j;
           else
               break;
       }
      
       // Chercher la limite à droite
       for (int j = c + 1; j < plateau.TAILLE; j++) {
           if (plateau.getCase(l,j) != 0){
               fin = j;
           }else{
               break;
           }
       }
      
       // Additionner toutes les valeurs de la séquence en un seul parcours
       for (int j = debut; j <= fin; j++) {
           verif += plateau.getCase(l,j);
       }
      
       // verification 
       this.presance[0] = false;
       this.presance[1] = false;
       if (debut != c) this.presance[0] = true;  // Il y a une connexion vers le haut
       if (fin != c) this.presance[1] = true;    // Il y a une connexion vers le bas
       for (int i = 0; i < 3; i++) {
           if (verif == paquet_cartes.carte_tire.get(i).getNumero()){
             return true;
           }
        }
        return false;
    } 
    
    /**
     * Met à jour le score d'un joueur après une action validée.
     * Les points sont calculés à partir des lignes et colonnes valides.
     *
     * @param joueurActif Le joueur dont le score doit être mis à jour.
     */
    
    public void miseAJourScore(Joueur joueurActif) {
         if (verification()) {
            List<int[]> positions = new ArrayList<>();

            // Récupérer les positions mémorisées sur le plateau
            for (int x = 0; x < plateau.TAILLE; x++) {
                int ligne = plateau.getMemoireVive(x, 0);
                int colonne = plateau.getMemoireVive(x, 1);
                if (ligne != -1 && colonne != -1) {
                    positions.add(new int[]{ligne, colonne});
                }
            }

            // Créer des tableaux booléens pour suivre les lignes/colonnes déjà traitées
            boolean[] lignesAjoutees = new boolean[plateau.TAILLE]; // plateau.TAILLE fixe basée sur le plateau
            boolean[] colonnesAjoutees = new boolean[plateau.TAILLE];

            // Réinitialiser les listes
            List<Integer> lignes = new ArrayList<>();
            List<Integer> colonnes = new ArrayList<>();

            // Parcourir les positions et marquer les lignes/colonnes
            for (int[] pos : positions) {
                int ligne = pos[0];
                int colonne = pos[1];

                // Gestion des getListeJoueurlignes
                if (!lignesAjoutees[ligne]) {
                    lignes.add(ligne);
                    lignesAjoutees[ligne] = true; // Marquer comme ajoutée
                }

                // Gestion des colonnes
                if (!colonnesAjoutees[colonne]) {
                    colonnes.add(colonne);
                    colonnesAjoutees[colonne] = true;
                }
            }

            int points = 0;

            // Vérifier les lignes
            for (int ligne : lignes) {
                for (int[] pos : positions) {
                    if (pos[0] == ligne) {
                        int[] resultat = sommeEtLongueurLigne(ligne, pos[1]);
                        int somme = resultat[0];
                        int longueur = resultat[1];

                        for (Carte carte : paquet_cartes.carte_tire) {
                            if (carte.getNumero() == somme) {
                                points += somme + longueur;
                                break;
                            }
                        }
                        break;
                    }
                }
            }

            // Vérifier les colonnes
            for (int colonne : colonnes) {
                for (int[] pos : positions) {
                    if (pos[1] == colonne) {
                        int[] resultat = sommeEtLongueurColonne(pos[0], colonne);
                        int somme = resultat[0];
                        int longueur = resultat[1];

                        for (Carte carte : paquet_cartes.carte_tire) {
                            if (carte.getNumero() == somme) {
                                points += somme + longueur;
                                break;
                            }
                        }
                        break;
                    }
                }
            }

            // Ajouter les points au joueur actif
            if (joueurActif != null) {
                joueurActif.setScore(joueurActif.getScore() + points);
            }
        }
    }
    
    /**
     * Calcule la somme et la longueur d'une ligne contenant un jeton donné.
     *
     * @param ligne Ligne du jeton.
     * @param colonne Colonne du jeton.
     * @return Tableau contenant [somme, longueur].
     */

    private int[] sommeEtLongueurLigne(int ligne, int colonne) {
        int debut = colonne, fin = colonne, somme = plateau.getCase(ligne, colonne);

        for (int j = colonne - 1; j >= 0 && plateau.getCase(ligne, j) != 0; j--) {
            debut = j;
            somme += plateau.getCase(ligne, j);
        }
        for (int j = colonne + 1; j < plateau.TAILLE && plateau.getCase(ligne, j) != 0; j++) {
            fin = j;
            somme += plateau.getCase(ligne, j);
        }

        return new int[]{somme, fin - debut + 1};
    }
    
    /**
     * Calcule la somme et la longueur d'une colonne contenant un jeton donné.
     *
     * @param ligne Ligne du jeton.
     * @param colonne Colonne du jeton.
     * @return Tableau contenant [somme, longueur].
     */

    private int[] sommeEtLongueurColonne(int ligne, int colonne) {
        int debut = ligne, fin = ligne, somme = plateau.getCase(ligne, colonne);

        for (int i = ligne - 1; i >= 0 && plateau.getCase(i, colonne) != 0; i--) {
            debut = i;
            somme += plateau.getCase(i, colonne);
        }
        for (int i = ligne + 1; i < plateau.TAILLE && plateau.getCase(i, colonne) != 0; i++) {
            fin = i;
            somme += plateau.getCase(i, colonne);
        }

        return new int[]{somme, fin - debut + 1};
    }
    
    


    
}
