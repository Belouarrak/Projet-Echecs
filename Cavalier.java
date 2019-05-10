import java.util.*;
import java.lang.*;

public class Cavalier extends Piece{

  public Cavalier(int color){
    super(color);
  }
  public boolean mouvementPossible(Case[][] board, int departX, int departY, int arriveeX, int arriveeY){
    //exception de base (va falloir créer une classe exception comme ça c'est plus facile)
    if(departX == arriveeX && departY == arriveeY)
      return false; //ne vas pas bouger
    if(arriveeX < 0 || arriveeX > 7 || departX < 0 || departX > 7 || arriveeY < 0 || arriveeY > 7 || departY <0 || departY > 7)
      return false;//bordures
    //
    if((Math.abs(departX-arriveeX)==2 && Math.abs(departY-arriveeY) == 1) || (Math.abs(departX-arriveeX)==1 && Math.abs(departY-arriveeY)==2)){
      return true;
    }
    return false;
  }
  public String toString(){
   if(this.couleur==0){
     return "U+2658";
   }
   else{
     return "U+265E";
   }
 }
}
