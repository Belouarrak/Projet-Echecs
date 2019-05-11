import java.util.*;
import java.lang.*;

public class Tour extends Piece{

  public Tour(int color){
    super(color);
  }
  public int getColor(){
    return this.couleur;
  }
  public boolean mouvementPossible(Case[][] board, int departX, int departY, int arriveeX, int arriveeY){
    //departX=arriveeX: même ligne; departY=arriveeY: même colonne
    if(departX==arriveeX || departY==arriveeY){
      //ligne de droite
      if(departX==arriveeX && arriveeY-departY>0)){
        //regarde si il a une pièce sur le chemin
        for (int i=1; i<arriveeY-departY; i++){
          if (board.getCase(departX, departY+i).estOccupee()){
            return false;
          }
        }
        return true;
      }
      //ligne de gauche
      if(departX==arriveeX && arriveeY-departY<0)){
        //regarde si il a une pièce sur le chemin
        for (int i=1; i<departY-arriveeY; i++){
          if (board.getCase(departX, departY-i).estOccupee()){
            return false;
          }
        }
        return true;
      }
      //colonne du haut
      if(departY==arriveeY && arriveeX-departX>0)){
        //regarde si il a une pièce sur le chemin
        for (int i=1; i<arriveeX-departX; i++){
          if (board.getCase(departX+i, departY).estOccupee()){
            return false;
          }
        }
        return true;
      }
      //colonne du bas
      if(departY==arriveeY && arriveeX-departX<0)){
        //regarde si il a une pièce sur le chemin
        for (int i=1; i<departX-arriveeX; i++){
          if (board.getCase(departX-i, departY).estOccupee()){
            return false;
          }
        }
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
