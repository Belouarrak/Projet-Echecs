import java.util.*;
import java.lang.*;

public class Fou extends Piece{

  public Fou(int color){
    super(color);
  }
  public boolean mouvementPossible(Case[][] board, int departX, int departY, int arriveeX, int arriveeY){
    //diagonale
    if((departX-arriveeX)==(departY-arriveeY)){
      return true;
    }
    return false;
  }
  public String toString(){
    if(this.couleur==0){
      return "U+2657";
    }
    else{
      return "U+265D";
    }
  }
}
