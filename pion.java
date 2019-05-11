import java.util.*;
import java.lang.*;

public class Pion extends Piece{

  public Pion(int color){
    super(color);
  }
  public boolean mouvementPossible(Case[][] board, int departX, int departY, int arriveeX, int arriveeY){
    //PION BLANC
    if (this.color==0) {
      //true si la case est de distance 2 devant la case de départ (x+2) et le pion est à sa positon initiale et la case entre les deux est vide
      if (departX==1){
        if((arriveeX==departX+2 && departY==arriveeY) && !board.getCase(departX+1, departY).estOccupee(){
          return true;
        }
      }
      //true si la case est celle devant la case de départ (x+1)
      if(arriveeX==departX+1 && departY==arriveeY){
        return true;
      }
      //true si la case est celle sur la diagonale supérieure (droite ou gauche) de la case de départ et celle-ci est occupée
      if((arriveeX==departX+1 && Math.abs(arriveeY-departY)==1) && board.getCase(arriveeX, arriveeY).estOccupee()){
        return true;
      }
    }
    //PION NOIR
    if (this.color==1) {
      //true si la case est de distance 2 devant la case de départ (x-2) et le pion est à sa positon initiale et la case entre les deux est vide
      if (departX==6){
        if((arriveeX==departX-2 && departY==arriveeY) && !board.getCase(departX-1, departY).estOccupee(){
          return true;
        }
      }
      //true si la case est celle devant la case de départ (x-1)
      if(arriveeX==departX-1 && departY==arriveeY){
        return true;
      }
      //true si la case est celle sur la diagonale supérieure (droite ou gauche) de la case de départ et celle-ci est occupée
      if((arriveeX==departX-1 && Math.abs(arriveeY-departY)==1) && board.getCase(arriveeX, arriveeY).estOccupee()){
        return true;
      }
    }
    return false;
  }
  public String toString(){
    if(this.couleur==0){
      return "U+2659";
    }
    else{
      return "U+265F";
    }
  }
}
