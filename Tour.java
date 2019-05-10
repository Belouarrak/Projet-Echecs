import java.util.*;
import java.lang.*;

public class Tour extends Piece{

  public Tour(int color){
    super(color);
  }
  public boolean mouvementPossible(Case[][] board, int departX, int departY, int arriveeX, int arriveeY){
    //soit même colonne, soit même ligne
    if(departX==arriveeX || departY==arriveeY){
      return true;
    }
    return false;
  }
  public String toString(){
    if(this.couleur==0){
      return "U+2656";
    }
    else {
      return "U+265C";
    }
  }
}
