import java.util.*;

public class Echiquier{
  private Case[][] plateau;
  private static int numTour;

  public Echiquier(){
    this.plateau = new Case[8][8];
    for(int i=0; i<plateau.length; i++){
      for(int j=0; j<plateau.length; j++){
        this.plateau[i][j] = new Case(i, j);
      }
    }
    this.numTour = 0;
  }
  public Case getCase(int x, int y){
    return this.plateau[x][y];
  }
/*  public boolean caseExiste(int x, int y){
    if ((0<=x && x<=7) && (0<=y && y<=7)){
      return true;
    }
    return false;
  }*/
  public int getNumTour(){
    return this.numTour;
  }
  public boolean legalMove(Joueur currentPlayer, Case caseDep, Case caseAr){
    //doit retourner false si la case de départ est vide
    if (!caseDep.estOccupee()){
      System.out.println("La case "+caseDep.getStringCase()+" est vide.");
      System.out.println("Veuillez effectuer un mouvement valide. ");
      return false;
    }
    //doit retoruner false si la case de départ n'est pas celle du joueur courrant
    if (!currentPlayer.getPiecesJoueur().contains(caseDep.getPiece())){
      System.out.println("La case "+caseDep.getStringCase()+" n'est pas la votre. ");
      System.out.println("Veuillez effectuer un mouvement valide. ");
      return false;
    }
    //return true si la pièce peut effectuer le mouvement selon les règles du jeu
    //(la case fait partie du chemin et il n'y a pas d'autre pièce dessus qui la sépare de la case de départ)
    if (caseDep.getPiece().mouvementPossible(this,caseDep.getX(), caseDep.getY(),caseAr.getX(), caseAr.getY())){
      //doit retoruner false si la case d'arrivée est occupée par une pièce du même joueur
      if(caseAr.estOccupee()){
        if(caseDep.getPiece().getColor()==caseAr.getPiece().getColor()){
          System.out.println("La case "+caseAr.getStringCase()+" est de la même couleur.");
          System.out.println("Veuillez effectuer un mouvement valide. ");
          return false;
        }
      }
      return true;
    }
    return false;
  }
  public String toString(){
    String str = new String("");
    str+="  +--X---+------+------+------+------+------+------+---X--+\n";
    for(int x=7; x>=0; x--){
      str+=x+1+" |";
      for(int y=0; y<8; y++){
        str+=this.plateau[x][y]+"|";
      }
      if(x>0)
        str+="\n  +------+------+------+------+------+------+------+------+\n";
    }
    str+="\n  +--X---+------+------+------+------+------+------+---X--+";
    str+="\n     a      b      c      d      e      f      g       h   ";
    return str;
  }
}
