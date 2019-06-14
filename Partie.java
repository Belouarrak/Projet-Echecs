import java.util.*;

public class Partie{
  private Echiquier echiquier;
  private Joueur blanc;
  private Joueur noir;
  private Joueur joueurCourant;
  private Scanner input;

  //initialiser la partie, le scanner et le joueur courant
  public Partie(){
    this.input = new Scanner(System.in);
    this.initialiserPartie();
    this.joueurCourant=this.blanc;
    System.out.println(this.getEchiquier().toString());
  };
  //Initialise les joueurs et l'échiquier
  public void initialiserPartie() {
    System.out.println("Veuillez entrer le nom des joueurs: \nJoueur blanc: ");
    String nameblanc = this.input.nextLine();
    System.out.println("Joueur noir: ");
    String namenoir = this.input.nextLine();
    this.setEchiquier(new Echiquier());
    this.setJoueurBlanc(new Joueur(nameblanc,0));
    this.setJoueurNoir(new Joueur(namenoir,1));
    //Création des Pions
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
  public void setJoueurBlanc(Joueur joueur) {
        this.blanc = joueur;
      }
  public Joueur getNoir() {
        return this.noir;
      }
  public void setJoueurNoir(Joueur joueur) {
        this.noir = joueur;
      }
  public void changerJoueurCourant() {
    if(this.joueurCourant==this.blanc){
      this.joueurCourant=this.noir;
    }
    else{
      this.joueurCourant=this.blanc;
    }
  }
  //enlève la pièce de la case de départ et la pose sur la case d'arrivée
  public void bougerPion(Case caseDep, Case caseAr) {
    caseAr.occuperCase(caseDep.enleverPiece());
  }
  /*entrerCoords doit demander le mouvement de format "h4 b2" (par exemple), et va retourner les deux cases en question,
  continue de demander un bon format si les deux cases ne sont pas sur l'échiquier ou si le joueur met nimp*/
  public Case[] entrerCoords() {
    String coords="";
    String[] abc = {"a","b","c","d","e","f","g","h"};
    List<String> listabc = Arrays.asList(abc);
    String[] nums = {"1","2","3","4","5","6","7","8"};
    List<String> listnums = Arrays.asList(nums);
    boolean bonformat=false;
    while (bonformat==false){
      //le texte est mis en minuscule et replaceAll va enlever tous les espaces (\\s+ = tous les espaces et charactères non-visibles)
      coords = this.input.nextLine().toLowerCase().replaceAll("\\s+","");
      if (coords.length()==4){
        //true si on a quatre charactères de format "lettre/chiffre/lettre/chiffre"
        if (listabc.contains(coords.charAt(0)+"") && listnums.contains(coords.charAt(1)+"") && listabc.contains(coords.charAt(2)+"") && listnums.contains(coords.charAt(3)+"") ){
          bonformat=true;
        }
      }
      //Sinon on va print:
      else {
        System.out.println("Veuillez entrer des cases valides. ");
      }
    }
    //les lettres sontaprès cela convertis en coordonnées y1 et y2
    String y1string = ""+coords.charAt(0);
    String y2string = ""+coords.charAt(2);
    int y1=0;
    int y2=0;
    for (int i = 0; i<abc.length; i++){
      if (y1string.equals(abc[i])){
        y1 = i;
      }
      if (y2string.equals(abc[i])){
        y2 = i;
      }
    }
    //les chiffres sont converties en coordonnées x1 et x2
    int x1 = Character.getNumericValue(coords.charAt(1))-1;
    int x2 = Character.getNumericValue(coords.charAt(3))-1;
    Case[] cases = new Case[2];
    //va return un tableau de deux cases, la première étant celle de départ, la deuxième sera celle d'arrivée
    cases[0] = this.echiquier.getCase(x1,y1);
    cases[1] = this.echiquier.getCase(x2,y2);
    return cases;
  }
  //move, tant que le mouvement n'est pas bon pour legalMove, va appeller entrerCoords, puis effectuer le mouvement avec la méthode bougerPion
  public void move() {
    System.out.println("Effectuer un mouvement: ");
    Case[] cases = this.entrerCoords();
    while(!this.echiquier.legalMove(this.joueurCourant, cases[0], cases[1])){
      System.out.println("Veuillez entrer un mouvement valide.");
      cases = this.entrerCoords();
    }
    this.bougerPion(cases[0], cases[1]);
    System.out.println(this.getEchiquier().toString());
  }
  public boolean estEnEchec(Joueur player) {

  }
}
