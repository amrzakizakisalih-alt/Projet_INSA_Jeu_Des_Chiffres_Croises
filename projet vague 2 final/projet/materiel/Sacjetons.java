package materiel;

import java.util.ArrayList;
import java.util.List;

/**
 * Classe représentant un sac de jetons.
 * Gère la création, l'ajout, la suppression et le tirage des jetons.
 */
 

public class Sacjetons {
    private List<Jeton> listeJetons = new ArrayList<>();
    
    /**
     * @return La taille actuelle du sac
     */
    public int getTaille() {
        return this.listeJetons.size(); 
    }
    
    /**
     * Récupère un jeton à une position donnée
     * @param indice Position du jeton dans le sac
     * @return Le jeton demandé ou null si hors limites
     */
    
    public Jeton getJeton(int indice) {
        if (indice >= 0 && indice < listeJetons.size()) {
            return this.listeJetons.get(indice);
        } else {
            System.out.println("Erreur : Indice invalide !");
            return null;
        }
    }
    
    /**
     * Supprime un jeton à une position donnée
     * @param indice Position du jeton dans le sac
     */
    public void supprimerJeton(int indice) {
        if (indice >= 0 && indice < listeJetons.size()) {
            listeJetons.remove(indice);
        } else {
            System.out.println("Erreur : Indice hors limites !");
        }
    }
    
    /**
     * Ajoute une liste de jetons au sac
     * @param listeJetonsSelect Liste de jetons à ajouter
     */
    public void ajoutJetons(List<Jeton> listeJetonsSelect) {
        this.listeJetons.addAll(listeJetonsSelect);
    }
    
    /**
     * Remplit le sac avec 90 jetons.
     */
    public void remplissage_sac() {
        for (int i = 1; i < 91; i++) { // Taille fixe
            if (i < 21) {
                Jeton j = new Jeton(i);
                this.listeJetons.add(j);
            } else {
                int alea = (int) ((Math.random() * 20)+1);
                Jeton j = new Jeton(alea);
                this.listeJetons.add(j);
            }
        }
    }
    
    /**
     *  Affiche le sac a jetons
     */
     
    public void afficherSac() {
        for (Jeton j : listeJetons) {
            j.afficheVal();
        } 
    }


}

