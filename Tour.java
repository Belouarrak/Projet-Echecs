import java.util.*;
import java.lang.*;

public class Tour extends Piece{

  public Tour(int color){
    super(color);
  }
  public boolean mouvementPossible(int departX, int departY, int arriveeX, int arriveeY){
    //exception de base (va falloir créer une classe exception comme ça c'est plus facile)
    if(departX == arriveeX && departY == arriveeY)
      return false; //cannot move nothing
    if(arriveeX < 0 || arriveeX > 7 || departX < 0 || departX > 7 || arriveeY < 0 || arriveeY > 7 || departY <0 || departY > 7)
      return false;//bordures
    //
    if(departX==arriveeX || departY==arriveeY){
      return true;
    }
    return false;
  }
  public String toString(){
    if(this.color==0){
      return "U+2656";
    }
    else{
      return "U+265C";
    }
  }
}
