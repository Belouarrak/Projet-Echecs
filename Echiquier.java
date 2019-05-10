import java.util.*;

public class Echiquier{
  private Case[][] plateau;

  public Echiquier(){
    this.plateau = new Case[8][8];
    for(int i=0; i<plateau.length; i++){
            for(int j=0; j<plateau.length; j++){
                this.plateau[i][j] = new Case(i, j);
            }
        }
  }
  public Case getCase(int x, int y){
    return this.plateau[x][y];
  }
  public boolean legalMove(Case caseDep, Case caseAr){
    //doit retourner false si la case de départ est vide
    if (!caseDep.estOccupee()){
      return false;
    }
    //return true si le move de base est possible pour le type de pièce et si il n'y a pas de pièce(s) entre les deux pièces sur le chemin
    if (caseDep.getPiece().mouvementPossible(caseDep.getX(), caseDep.getY(),caseAr.getX(), caseAr.getY()) && this.rienEntreLesDeux(caseDep, caseAr)){
      return true;
    }
    return false;
  }
  public boolean rienEntreLesDeux(Case caseDep, Case caseAr){
    
  }
  public String toString(){
    String str = new String("");
    str+="  +--X---+------+------+------+------+------+------+---X--+\n";
    for(int x=0; x<8; x++){
      str+=x+1+" |";
      for(int y=0; y<8; y++){
        str+=this.plateau[x][y]+"|";
      }
      if(x<7)
        str+="\n  +------+------+------+------+------+------+------+------+\n";
    }
    str+="\n  +--X---+------+------+------+------+------+------+---X--+";
    str+="\n     a      b      c      d      e      f      g       h   ";
    return str;
  }
}
