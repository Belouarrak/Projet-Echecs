import java.util.*;

public class Partie{
  private Echiquier echiquier;
  private Joueur blanc;
  private Joueur noir;

  public void setCouleurBlanche(Joueur joueur) {
        this.blanc = joueur;
    }

    public void setCouleurNoire(Joueur joueur) {
        this.noir = joueur;
    }

    public Echiquier getEchiquier() {
        return this.echiquier;
    }

    public void setEchiquier(Echiquier echiquier) {
        this.echiquier = echiquier;
    }

    public Joueur getBlanc() {
        return this.blanc;
    }

    public void setBlanc(Joueur joueur) {
        this.blanc = joueur;
    }

    public Joueur getNoir() {
        return this.noir;
    }

    public void setJoueurNoir(Joueur joueur) {
        this.noir = joueur;
    }
}
