import java.util.*;
import java.lang.*;

public class Dame extends Piece{

  public Dame(int color){
    super(color);
  }
  public boolean mouvementPossible(Case[][] board, int departX, int departY, int arriveeX, int arriveeY){
    //
    if((Math.abs(departX-arriveeX)==Math.abs(departY-arriveeY))||(departX==arriveeX || departY==arriveeY)){
      return true;
    }
    return false;
  }
  public String toString(){
    if(this.couleur==0){
      return "U+2655";
    }
    else{
      return "U+265B";
    }
  }
}
