import java.util.*;
import java.lang.*;

public class Fou extends Piece{

  public Fou(int color){
    super(color);
  }
  public boolean mouvementPossible(Case[][] board, int departX, int departY, int arriveeX, int arriveeY){
    if(Math.abs(departX-arriveeX)==Math.abs(departY-arriveeY)){
      //diagonale haute-droite
      if((arriveeX-departX)==(arriveeY-departY)){
        //regarde si il a une pièce sur le chemin
        for (int i=1; i<arriveeX-departX; i++){
          if (board.getCase(departX+i, departY+i).estOccupee()){
            return false;
          }
        }
        return true;
      }
      //diagonale haute-gauche
      if((arriveeX-departX)==(departY-arriveeY)){
        //regarde si il a une pièce sur le chemin
        for (int i=1; i<arriveeX-departX; i++){
          if (board.getCase(departX+i, departY-i).estOccupee()){
            return false;
          }
        }
        return true;
      }
      //diagonale basse-droite
      if((departX-arriveeX)==(arriveeY-departY)){
        //regarde si il a une pièce sur le chemin
        for (int i=1; i<departX-arriveeX; i++){
          if (board.getCase(departX-i, departY+i).estOccupee()){
            return false;
          }
        }
        return true;
      }
      //diagonale basse-gauche
      if((departX-arriveeX)==(departY-arriveeY)){
        //regarde si il a une pièce sur le chemin
        for (int i=1; i<departX-arriveeX; i++){
          if (board.getCase(departX-i, departY-i).estOccupee()){
            return false;
          }
        }
        return true;
      }
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
