import java.util.*;

public class Partie{
  private Echiquier echiquier;
  private Joueur blanc;
  private Joueur noir;

  public Partie(){};
  public Echiquier getEchiquier() {
        return this.echiquier;
      }
  public void setEchiquier(Echiquier echiquier) {
        this.echiquier = echiquier;
      }
  public Joueur getBlanc() {
        return this.blanc;
      }
  public void setJoueurBlanc(Joueur joueur) {
        this.blanc = joueur;
      }
  public Joueur getNoir() {
        return this.noir;
      }
  public void setJoueurNoir(Joueur joueur) {
        this.noir = joueur;
      }
  public void initialiserPartie(){
    Scanner input = new Scanner(System.in);
    System.out.println("Veuillez entrer le nom des joueurs: \nJoueur blanc: ");
    String nameblanc = input.nextLine();
    System.out.println("Joueur noir: ");
    String namenoir = input.nextLine();
    this.setEchiquier(new Echiquier());
    this.setJoueurBlanc(new Joueur(nameblanc,true));
    this.setJoueurNoir(new Joueur(namenoir,false));
    for(int j=0; j<8; j++){
      Pion pawnwhite = new Pion(0);
      Pion pawnblack = new Pion(1);
      this.blanc.addPiece(pawnwhite);
      this.echiquier.getCase(1,j).occuperCase(pawnwhite);
      this.noir.addPiece(pawnblack);
      this.echiquier.getCase(6,j).occuperCase(pawnblack);
    }
    //autres pièces blanches
      //les deux Tours blanches
    for(int i=0;i<2;i++){
      Tour tourwhite = new Tour(0);
      this.blanc.addPiece(tourwhite);
      this.echiquier.getCase(0,7*i).occuperCase(tourwhite);
    }
      //les deux Cavaliers blancs
    Cavalier knightwhite1 = new Cavalier(0);
    Cavalier knightwhite2 = new Cavalier(0);
    this.blanc.addPiece(knightwhite1);
    this.echiquier.getCase(0,1).occuperCase(knightwhite1);
    this.blanc.addPiece(knightwhite2);
    this.echiquier.getCase(0,6).occuperCase(knightwhite2);
      //les deux Fous blancs
    Fou fouwhite1 = new Fou(0);
    Fou fouwhite2 = new Fou(0);
    this.blanc.addPiece(fouwhite1);
    this.echiquier.getCase(0,2).occuperCase(fouwhite1);
    this.blanc.addPiece(fouwhite2);
    this.echiquier.getCase(0,5).occuperCase(fouwhite2);
      //La Dame blanche
    Dame queenwhite = new Dame(0);
    this.blanc.addPiece(queenwhite);
    this.echiquier.getCase(0,3).occuperCase(queenwhite);
      //Le Roi blanc
    Roi kingwhite = new Roi(0);
    this.blanc.addPiece(kingwhite);
    this.echiquier.getCase(0,4).occuperCase(kingwhite);
    //autres pièces noires
      //les deux Tours blanches
    for(int i=0;i<2;i++){
      Tour tourblack = new Tour(1);
      this.noir.addPiece(tourblack);
      this.echiquier.getCase(7,7*i).occuperCase(tourblack);
    }
      //les deux Cavaliers noirs
    Cavalier knightblack1 = new Cavalier(1);
    Cavalier knightblack2 = new Cavalier(1);
    this.noir.addPiece(knightblack1);
    this.echiquier.getCase(7,1).occuperCase(knightblack1);
    this.noir.addPiece(knightblack2);
    this.echiquier.getCase(7,6).occuperCase(knightblack2);
      //les deux Fous noirs
    Fou foublack1 = new Fou(1);
    Fou foublack2 = new Fou(1);
    this.noir.addPiece(foublack1);
    this.echiquier.getCase(7,2).occuperCase(foublack1);
    this.noir.addPiece(foublack2);
    this.echiquier.getCase(7,5).occuperCase(foublack2);
      //La Dame noire
    Dame queenblack = new Dame(1);
    this.noir.addPiece(queenblack);
    this.echiquier.getCase(7,3).occuperCase(queenblack);
      //Le Roi noir
    Roi kingblack = new Roi(1);
    this.noir.addPiece(kingblack);
    this.echiquier.getCase(7,4).occuperCase(kingblack);
    System.out.println("Reste des pieces fait. ");
  }
}
