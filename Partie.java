import java.util.*;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class Partie{
  private Echiquier echiquier;
  private Joueur blanc;
  private Joueur noir;
  private Joueur joueurCourant;
  private Joueur joueurGagnant = null;
  private Scanner input;
  private String[] abc = {"a","b","c","d","e","f","g","h"};
  private List<String> listabc;
  private String[] nums = {"1","2","3","4","5","6","7","8"};
  private List<String> listnums;


  //initialiser la partie, le scanner et le joueur courant
  public Partie(){
    this.input = new Scanner(System.in);
    this.listabc = Arrays.asList(this.abc);
    this.listnums = Arrays.asList(this.nums);
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
  /*entrerCoords doit demander le mouvement de format "h4 b2" (par exemple), et va retourner les deux cases en question,
  continue de demander un bon format si les deux cases ne sont pas sur l'échiquier ou si le joueur met nimp*/
  public Case[] entrerCoords() {
    String coords="";
    boolean bonformat=false;
    while (bonformat==false){
      //le texte est mis en minuscule et replaceAll va enlever tous les espaces (\\s+ = tous les espaces et charactères non-visibles)
      coords = this.input.nextLine().toLowerCase().replaceAll("\\s+","");
      if (coords.length()==4){
        //true si on a quatre charactères de format "lettre/chiffre/lettre/chiffre"
        if (this.listabc.contains(coords.charAt(0)+"") && this.listnums.contains(coords.charAt(1)+"") && this.listabc.contains(coords.charAt(2)+"") && this.listnums.contains(coords.charAt(3)+"") ){
          try { // SAUVEGARDER FICHIER
           FileWriter fichierWrite = new FileWriter("Fichiertxt.txt", true); // Ecrire le fichier, le true permet d'append le fichier au lieu de créer un nouvel objet
           BufferedWriter ecrire = new BufferedWriter(fichierWrite); // Stream chaining, de convention
           ecrire.write(coords); // On écrit le contenu de la variable dans le fichier
           ecrire.newLine(); // On passe à la ligne suivante
           ecrire.close(); // On ferme le fichier pour éviter les problèmes de mémoire
         }
          catch (IOException Ex) {System.out.println(Ex.getMessage());}
          this.echiquier.setNumTour(this.echiquier.getNumTour()+1); // On rajoute 1 pour garder le fil
          bonformat=true;
        }
      }
      //Sinon on va print:
      else {
        System.out.println("Veuillez entrer des cases valides. ");
      }
    }
    return this.entrerCoords(coords);
  }
  public Case[] entrerCoords(String coords){
    //les lettres sontaprès cela convertis en coordonnées y1 et y2
    String y1string = ""+coords.charAt(0);
    String y2string = ""+coords.charAt(2);
    int y1=0;
    int y2=0;
    for (int i = 0; i<this.abc.length; i++){
      if (y1string.equals(this.abc[i])){
        y1 = i;
      }
      if (y2string.equals(this.abc[i])){
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
  public void chargerMoves(String file) throws Exception{
    try{
      BufferedReader ReadFileBuffer = new BufferedReader(new FileReader(file)); // Streaming chain, convention
      String ligne = ReadFileBuffer.readLine().toLowerCase().replaceAll("\\s+","");
      while (ligne!= null){
        // read next line
        Case[] cases = this.entrerCoords(ligne);
        this.echiquier.bougerPiece(cases[0], cases[1]);
        this.changerJoueurCourant();
        ligne = ReadFileBuffer.readLine().toLowerCase().replaceAll("\\s+","");
      }
      ReadFileBuffer.close();
    }
    catch(Exception e){System.out.println(e);}
   }
  //legalMove a un nom un peu redondant à mouvementPossible, mais cette méthode ci est plus élargie, et connait le contexte de la partie. Elle va elle même,
  //après avoir verifié que plusieurs configurations de base sont respectées, utiliser mouvementPossible
  public boolean legalMove(Joueur currentPlayer, Case caseDep, Case caseAr, Echiquier board){
    //doit retourner false si la case de départ est vide
    if (!caseDep.estOccupee()){
      return false;
    }
    //doit retoruner false si la case de départ n'est pas celle du joueur courrant
    if (currentPlayer.getPlayerColor()!=caseDep.getPiece().getColor()){
      return false;
    }
    //return true si la pièce peut effectuer le mouvement selon les règles du jeu
    //(la case fait partie du chemin et il n'y a pas d'autre pièce dessus qui la sépare de la case de départ)
    if (caseDep.getPiece().mouvementPossible(board,caseDep.getX(), caseDep.getY(),caseAr.getX(), caseAr.getY())){
      //doit retoruner false si la case d'arrivée est occupée par une pièce du même joueur
      if(caseAr.estOccupee()){
        if(caseDep.getPiece().getColor()==caseAr.getPiece().getColor()){
          return false;
        }
      }
      return true;
    }
    return false;
  }
  public boolean estEnEchec(Joueur joueur, Echiquier board){
    for (int i=0; i<8;i++){
      for (int j=0;j<8;j++){
        //si une pièce du joueur opposé peut capturer le roi, on est en échec
        if (this.legalMove(this.joueurOppose(joueur), board.getCase(i,j), board.getCaseRoi(joueur), board)){
          return true;
        }
      }
    }
    return false;
  }
  public Joueur joueurOppose(Joueur joueur){
    if(joueur==this.blanc){
      return this.noir;
    }
    else{
      return this.blanc;
    }
  }
  //moveMetEchec regarde si le mouvement effectué ne met pas en situation d'échec
  public boolean moveMetEchec(Joueur joueur,Case caseDep, Case caseAr){
    //vérifie au préalable si le mouvement est dans les règles
    if (this.legalMove(joueur, caseDep, caseAr, this.echiquier)==false){
      return true;
    }
    Echiquier newBoard = new Echiquier(this.echiquier);
    newBoard.bougerPiece(caseDep, caseAr);
    if (this.estEnEchec(joueur, newBoard)){
      return true;
    }
    return false;
  }
  public boolean noLegalMovePossible(Joueur joueur, Echiquier board){
    for(int i=0;i<8;i++){
      for(int j=0;j<8;j++){
        //si la case contient une pièce du joueur on regarde si elle peut effectuer un mouvement qui ne met pas en échec
        if (board.getCase(i,j).getPiece()!=null){
          if (board.getCase(i,j).getPiece().getColor()==joueur.getPlayerColor()){
            for(int x=0;x<8;x++){
              for(int y=0;y<8;y++){
                if (this.moveMetEchec(joueur, board.getCase(i,j), board.getCase(x,y))==false){
                  return false;
                }
              }
            }
          }
        }
      }
    }
    return true;
  }
  //move, tant que le mouvement n'est pas bon pour legalMove, va appeller entrerCoords, puis effectuer le mouvement avec la méthode bougerPiece
  public void move() {
    System.out.println("Effectuer un mouvement: ");
    Case[] cases = this.entrerCoords();
    while(this.legalMove(this.joueurCourant, cases[0], cases[1], this.echiquier)==false && this.moveMetEchec(this.joueurCourant, cases[0], cases[1])==true){
      System.out.println("Veuillez entrer un mouvement valide.");
      cases = this.entrerCoords();
    }
    this.echiquier.bougerPiece(cases[0], cases[1]);
    System.out.println(this.getEchiquier().toString());
    if (this.promouvoirPion(this.echiquier.getCase(cases[1].getX(), cases[1].getY()))){
      System.out.println(this.getEchiquier().toString());
    }
  }
  public boolean promouvoirPion(Case box){
    if (box.estOccupee() && box.getPiece().getColor()==this.joueurCourant.getPlayerColor() && box.getPiece() instanceof Pion){
      if(this.joueurCourant==this.blanc && box.getX()==7){
        System.out.println("Votre pion a atteint "+box.getStringCase()+". Veuillez le promouvoir.");
        this.choisirPromotion(0, box);
        return true;
      }
      else if (this.joueurCourant==this.noir && box.getX()==0){
        System.out.println("Votre pion a atteint "+box.getStringCase()+". Veuillez le promouvoir.");
        this.choisirPromotion(1, box);
        return true;
      }
    }
    return false;
  }
  public void choisirPromotion(int color, Case box){
    System.out.println("Dame: 'D', Fou: 'F', Tour: 'T', Cavalier: 'C'.\nEntrer la nature de la nouvelle pièce.");
    String nature = "";
    boolean prom = false;
    while(!prom){
      nature=this.input.nextLine().trim();
      if (nature.equals("D")){
        Dame piece = new Dame(color);
        box.enleverPiece();
        box.occuperCase(piece);
        prom = true;
      }
      else if (nature.equals("F")){
        Fou piece = new Fou(color);
        box.enleverPiece();
        box.occuperCase(piece);
        prom = true;
      }
      else if (nature.equals("T")){
        Tour piece = new Tour(color);
        box.enleverPiece();
        box.occuperCase(piece);
        prom = true;
      }
      else if (nature.equals("C")){
        Cavalier piece = new Cavalier(color);
        box.enleverPiece();
        box.occuperCase(piece);
        prom = true;
      }
    }
  }
  public boolean lancerPartie(){
    boolean finpartie = false;
    System.out.println("C'est au tour de "+this.joueurCourant.getNom()+" de jouer.");
    //vérifier si le joueur est en échec
    if (this.estEnEchec(this.joueurCourant, this.echiquier)==true){
      //vérifier si le joueur est en échecs et mat
      System.out.println("Verficiation noLegalMovePossible: "+ this.noLegalMovePossible(this.joueurCourant, this.echiquier));
      if (this.noLegalMovePossible(this.joueurCourant, this.echiquier)==true){
        this.joueurGagnant = this.joueurOppose(this.joueurCourant);
        System.out.println("Partie terminee. Le gagnant de la partie est "+this.joueurGagnant.getNom()+"!!!");
        return true;
      }
      else{
        this.move();
      }
    }
    else{
      //vérifier si Pat
      if (this.noLegalMovePossible(this.joueurCourant, this.echiquier)==true){
        System.out.println("Vous avez atteint un Pat, personne ne gagne.");
        return true;
      }
      else{
        this.move();
      }
    }
    this.changerJoueurCourant();
    return false;
  }
}
