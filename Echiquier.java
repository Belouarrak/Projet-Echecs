import java.util.*;

public class Echiquier{
  private Case[][] plateau;
  private static int numTour;

  //crée un plateau de 64 cases, initialise le nombre de tour à zéro
  public Echiquier(){
    this.plateau = new Case[8][8];
    for(int i=0; i<this.plateau.length; i++){
      for(int j=0; j<this.plateau.length; j++){
        this.plateau[i][j] = new Case(i, j);
      }
    }
    this.numTour = 0;
  }
  public Echiquier(Echiquier firstBoard){
    this.plateau = new Case[8][8];
    for(int i=0; i<this.plateau.length; i++){
      for(int j=0; j<this.plateau.length; j++){
        this.plateau[i][j] = new Case(firstBoard.getCase(i,j));
      }
    }
    this.numTour = firstBoard.getNumTour();
  }
  //retourne la case correspondant aux coordonnées x,y
  public Case getCase(int x, int y){
    return this.plateau[x][y];
  }
/*  public boolean caseExiste(int x, int y){
    if ((0<=x && x<=7) && (0<=y && y<=7)){
      return true;
    }
    return false;
  }*/
  public Case getCaseRoi(Joueur player){
    for(int i=0; i<this.plateau.length; i++){
      for(int j=0; j<this.plateau.length; j++){
        if (this.plateau[i][j].getPiece()!=null){
          if (this.plateau[i][j].getPiece().getColor()==player.getPlayerColor() && this.plateau[i][j].getPiece() instanceof Roi){
            return this.plateau[i][j];
          }
        }
      }
    }
    return null;
  }
  public int getNumTour(){
    return this.numTour;
  }
  //legalMove a un nom un peu redondant à mouvementPossible, mais cette méthode ci est plus élargie, et connait le contexte de la partie. Elle va elle même,
  //après avoir verifié que plusieurs configurations de base sont respectées, utiliser mouvementPossible
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
          System.out.println("La case "+caseAr.getStringCase()+" contient une pièce de la même couleur.");
          return false;
        }
      }
      return true;
    }
    return false;
  }
  //toString de l'échiquier simple pour le moment
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
