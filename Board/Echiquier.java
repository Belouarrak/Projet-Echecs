package Board;

import Pieces.*;
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
    for(int i=0; i<8; i++){
      for(int j=0; j<8; j++){
        this.plateau[i][j] = new Case(firstBoard.getCase(i,j));
      }
    }
    this.numTour = firstBoard.getNumTour();
  }
  //enlève la pièce de la case de départ et la pose sur la case d'arrivée
  public void bougerPiece(Case caseDep, Case caseAr) {
    this.getCase(caseAr.getX(), caseAr.getY()).occuperCase(this.getCase(caseDep.getX(), caseDep.getY()).enleverPiece());
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
  public void setNumTour(int num){
    this.numTour=num;
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
