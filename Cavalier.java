import java.util.*;
import java.lang.*;

public class Cavalier extends Piece{

  public Cavalier(int color){
    super(color);
  }
  public int getColor(){
    return this.couleur;
  }
  public boolean mouvementPossible(Case[][] board, int departX, int departY, int arriveeX, int arriveeY){
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
