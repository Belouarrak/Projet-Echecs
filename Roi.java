import java.util.*;
import java.lang.*;

public class Roi extends Piece{

  public Roi(int color){
    super(color);
  }
  public int getColor(){
    return this.couleur;
  }
  public boolean mouvementPossible(Case[][] board, int departX, int departY, int arriveeX, int arriveeY){
    //distance entre les cases toujours égale à 1
    if(Math.abs(arriveeX-departX)<=1 && Math.abs(arriveeY-departY)<=1){
      return true;
    }
    return false;
  }
  public String toString(){
    if(this.couleur==0){
      return "U+2654";
    }
    else{
      return "U+265A";
    }
  }
}
