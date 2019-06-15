import java.util.*;
import java.lang.*;

public class Dame extends Piece{

  public Dame(int color){
    super(color);
  }
  public int getColor(){
    return this.couleur;
  }
  //la dame possède une combinaison des pattern du fou et de la tour
  public boolean mouvementPossible(Echiquier board, int departX, int departY, int arriveeX, int arriveeY){
    //pièce sur les diagoales ou même ligne ou même colonne
    if(Math.abs(departX-arriveeX)==Math.abs(departY-arriveeY) || (departX==arriveeX || departY==arriveeY)){
      //diagonale haute-droite
      if(arriveeX-departX>0 && arriveeY-departY>0){
        //regarde si il a une pièce sur le chemin
        for (int i=1; i<arriveeX-departX; i++){
          if (board.getCase(departX+i, departY+i).estOccupee()){
            return false;
          }
        }
        return true;
      }
      //diagonale haute-gauche
      if(arriveeX-departX>0 && arriveeY-departY<0){
        //regarde si il a une pièce sur le chemin
        for (int i=1; i<arriveeX-departX; i++){
          if (board.getCase(departX+i, departY-i).estOccupee()){
            return false;
          }
        }
        return true;
      }
      //diagonale basse-droite
      if(arriveeX-departX<0 && arriveeY-departY>0){
        //regarde si il a une pièce sur le chemin
        for (int i=1; i<departX-arriveeX; i++){
          if (board.getCase(departX-i, departY+i).estOccupee()){
            return false;
          }
        }
        return true;
      }
      //diagonale basse-gauche
      if(arriveeX-departX<0 && arriveeY-departY<0){
        //regarde si il a une pièce sur le chemin
        for (int i=1; i<departX-arriveeX; i++){
          if (board.getCase(departX-i, departY-i).estOccupee()){
            return false;
          }
        }
        return true;
      }
      //ligne de droite
      if(departX==arriveeX && arriveeY-departY>0){
        //regarde si il a une pièce sur le chemin
        for (int i=1; i<arriveeY-departY; i++){
          if (board.getCase(departX, departY+i).estOccupee()){
            return false;
          }
        }
        return true;
      }
      //ligne de gauche
      if(departX==arriveeX && arriveeY-departY<0){
        //regarde si il a une pièce sur le chemin
        for (int i=1; i<departY-arriveeY; i++){
          if (board.getCase(departX, departY-i).estOccupee()){
            return false;
          }
        }
        return true;
      }
      //colonne du haut
      if(departY==arriveeY && arriveeX-departX>0){
        //regarde si il a une pièce sur le chemin
        for (int i=1; i<arriveeX-departX; i++){
          if (board.getCase(departX+i, departY).estOccupee()){
            return false;
          }
        }
        return true;
      }
      //colonne du bas
      if(departY==arriveeY && arriveeX-departX<0){
        //regarde si il a une pièce sur le chemin
        for (int i=1; i<departX-arriveeX; i++){
          if (board.getCase(departX-i, departY).estOccupee()){
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
      return "U+2655";
    }
    else{
      return "U+265B";
    }
  }
}
