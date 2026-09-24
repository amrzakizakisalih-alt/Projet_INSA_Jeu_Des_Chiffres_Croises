package materiel;

import utilisateur.Joueur;
import src.Systeme;

import javax.swing.*;
import java.awt.*;
import java.util.List;

/**
 * Classe représentant le plateau de jeu graphique.
 * Gère l'affichage de la grille, des joueurs et des cartes.
 */


public class Plateau extends JPanel {
    public static final int TAILLE = 17;
    public int[][] grille;
    private JPanel[][] cases;
    private JLabel[][] labels;
    private JFrame fenetrePrincipale;
    private int nbrJoueur = 5;
    private int[][] memoireVive;
    private int nbr_jetonsPose;
    public boolean coupOK;
    
    /**
     * Constructeur du plateau de jeu.
     * Crée la grille du plateau ainsi que les fenêtres principales.
     */

    public Plateau() {
    
        this.grille = new int[TAILLE][TAILLE];
        this.memoireVive = new int[this.TAILLE][2];
        this.labels = new JLabel[TAILLE][TAILLE];
        this.nbr_jetonsPose=0;
    
        for (int i = 0; i < this.TAILLE; i++) {
            for (int j = 0; j < TAILLE; j++) {
                labels[i][j] = new JLabel("", SwingConstants.CENTER);
            }
        }
        fenetrePrincipale = new JFrame("Plateau");
        fenetrePrincipale.setLayout(new BorderLayout());
        fenetrePrincipale.setSize(800, 800);

    }

    /**
     * Récupère la valeur de la grille à la position donnée
     */

    
    public int getCase(int i, int j) {
        if (i >= 0 && i < TAILLE && j >= 0 && j < TAILLE) {
            return this.grille[i][j];
        }
        System.out.println("Coordonnées invalides dans getCase !");
        return -1; // Valeur d'erreur
    }

    /**
     * Récupère la valeur de la memoire vive a la position donnée
     */

    
    public int getMemoireVive(int i, int j) {
        if (i < 0 || i >= TAILLE || j < 0 || j >= 2) {  // Vérification des limites
            System.out.println("Coordonnées invalides dans getMemoireVive !");
            return -1; // Valeur par défaut en cas d'erreur
        }
        return this.memoireVive[i][j];
    }

    /**
     * Récupère le nombre de jetons poses.
     */

    
    public int getNbrJetonsPose(){
      return this.nbr_jetonsPose;
    }

    /**
     * Efface la memoire vive et réinitialise le compteur nombre de jetons pose.
     */

    public void effacerMemoire(){
    
      this.nbr_jetonsPose=0;
      for(int i=0;i<this.TAILLE;i++){
        for(int j=0;j<2;j++){
          memoireVive[i][j]=-1;
        }
      }
      
    }

    /**
     * Récupère la taille de la memoire vive.
     */


    public int getTailleMemoire() {
        return this.memoireVive.length; 
    }
    
    
    /**
     * Affiche l'interface complète du jeu
     * @param listeJoueur Liste des joueurs de la partie
     * @param joueurActif Joueur dont c'est le tour
     */
  
    
    public void affichage(List<Joueur> listeJoueur,Joueur joueurActif) {
        fenetrePrincipale.getContentPane().removeAll();
        fenetrePrincipale.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);  // permet la fermeture sans arret du programme principal

        // Création du panneau d'informations des joueurs
        JPanel infoJoueurs = new JPanel();
	      infoJoueurs.setLayout(new BoxLayout(infoJoueurs, BoxLayout.Y_AXIS));
	      infoJoueurs.setPreferredSize(new Dimension(300, 300));
	      infoJoueurs.setBorder(BorderFactory.createTitledBorder("Infos Partie"));
        
        for (int i = 0; i < listeJoueur.size(); i++) { // Utilisation de la taille réelle de la liste
            Joueur joueur = listeJoueur.get(i);
    
            // Création d'un panel par joueur
            JPanel joueurPanel = new JPanel(new GridLayout(5, 1, 0, 2));
            joueurPanel.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY),BorderFactory.createEmptyBorder(0, 1, 0, 0)));
 
    
            // Infos de base
            joueurPanel.add(new JLabel("Joueur " + (i + 1) + " : " + joueur.getNom()));
            joueurPanel.add(new JLabel("Score : " + joueur.getScore()));

            // Vérification joueur actif avec equals()
            if(joueur.getNom().equals(joueurActif.getNom())) {
                joueurPanel.add(new JLabel("Jetons disponibles : " +joueurActif.getChevalet().getNombreJetons()));
        
                // Création d'un sous-panel pour le chevalet
                JPanel chevaletPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0)); 
		            chevaletPanel.add(new JLabel("Chevalet : "));
		            for (Jeton jeton : joueurActif.getChevalet().listeJetons) {
			              JLabel jetonLabel = new JLabel(String.valueOf(jeton.getValeur()));
			              chevaletPanel.add(Box.createHorizontalStrut(5));
			              jetonLabel.setBorder(BorderFactory.createLineBorder(Color.GRAY));
			              chevaletPanel.add(jetonLabel);
		            }
        
                joueurPanel.add(chevaletPanel);
            }
    
            infoJoueurs.add(joueurPanel);
        }   
          
        JPanel cartesPanel = new JPanel(new GridLayout(1, 3, 10, 10));
        cartesPanel.setBorder(BorderFactory.createTitledBorder("Cartes Tirées"));
        int c=0;
        for (Carte carte : Systeme.paquet_cartes.carte_tire) {
            c++;
            JLabel carteLabel = new JLabel("Carte "+c+" : " + carte.getNumero(), SwingConstants.CENTER);
            carteLabel.setFont(new Font("Arial", Font.BOLD, 16));
            cartesPanel.add(carteLabel);
        }

        // Panneau contenant les infos joueurs et le menu principal
        JPanel fenetreJoueurs = new JPanel(new BorderLayout());
        fenetreJoueurs.add(infoJoueurs, BorderLayout.WEST);
        
        //fenetreJoueurs.setPreferredSize(new Dimension(500, 600));

        fenetrePrincipale.add(fenetreJoueurs, BorderLayout.EAST);
        fenetrePrincipale.add(cartesPanel, BorderLayout.NORTH); 

        // Création du plateau de jeu
        JPanel plat = new JPanel(new GridLayout(TAILLE, TAILLE));
        cases = new JPanel[TAILLE][TAILLE];
        
        for (int i = 0; i < TAILLE; i++) {
            for (int j = 0; j < TAILLE; j++) {
                cases[i][j] = new JPanel();
                cases[i][j].setBorder(BorderFactory.createLineBorder(Color.BLACK));
                cases[i][j].setBackground(Color.WHITE);
                
                

                labels[i][j].setFont(new Font("Arial", Font.BOLD, 19));
                cases[i][j].setLayout(new BorderLayout());
                cases[i][j].add(labels[i][j], BorderLayout.CENTER);

                plat.add(cases[i][j]);
            }
        }
        

        // Ajout du plateau à la fenêtre
        fenetrePrincipale.add(plat, BorderLayout.CENTER);

        // Affichage de la fenêtre
        fenetrePrincipale.validate();
        fenetrePrincipale.repaint();
        fenetrePrincipale.setVisible(true);
    }

    /**
     * Affichage basique sans l'utilisation d'interface graphique.
     */

    public void affichage_bof() {
        System.out.println("Plateau de jeu :");
        for (int i = 0; i < TAILLE; i++) {
            for (int j = 0; j < TAILLE; j++) {
                // Affichage formaté avec un espace réservé de 2 caractères
                if (grille[i][j] == 0) {
                    System.out.print("[  ]"); // Deux espaces pour aligner
                } else {
                    System.out.printf("[%2d]", grille[i][j]); // %2d pour garder l'alignement
                }
            }
            System.out.println();
        }
    }
    
    /**
     * Modifie une case du plateau
     * @param i Coordonnée ligne
     * @param j Coordonnée colonne
     * @param valeur Valeur à insérer (0 pour effacer)
     */
    public void modifierCase(int i, int j, int valeur) {
        coupOK=false;
        if ((i >= 0 && i < this.TAILLE && j >= 0 && j < this.TAILLE && this.getCase(i,j)==0) || valeur==0) {
            coupOK=true;
            this.grille[i][j] = valeur;
            if (valeur == 0) {
                labels[i][j].setText(""); // Case vide
            } else {
                labels[i][j].setText(String.valueOf(valeur));
                this.nbr_jetonsPose += 1; // Incrémenter avant d'utiliser la valeur
                this.manipulationmMemoire(i, j);
            }
            //System.out.println("Case modifiée : i=" + i + ", j=" + j + ", valeur=" + valeur);
        } else {
            System.out.println("Coordonnées invalides !");

        
        }
    }

    /**
     * Ajout de la position du dernier coup dans la memoire vive.
     *@param i Coordonnée ligne du coup
     *@param j Coordonnée colonne du coup
     */

    
    public void manipulationmMemoire(int i, int j){
        if (nbr_jetonsPose > 0 && nbr_jetonsPose <= this.TAILLE) { // Vérification pour éviter les erreurs d'indice
            memoireVive[nbr_jetonsPose - 1][0] = i;
            memoireVive[nbr_jetonsPose - 1][1] = j;
        }
    }
    
    /**
     * Verifie si le plateau de jeu est complet
     * @return false si le plateau est complet, sinon true.
     */
    
    public boolean estComplet() {
        int compteur=0;
			  for(int j=0;j<this.TAILLE;j++){
			    	for(int k=0;k<this.TAILLE;k++){
			    		if (!(this.getCase(j,k)==0)){
			      	    compteur+=1;
			      			if (compteur==this.TAILLE*this.TAILLE){
				  		      	return false;
				  	     	}
				  	   }
			      }
			  }
			  return true;
    }



}

