package Pieces;

import java.util.*;
import java.lang.*;
import Board.Echiquier;

public class Fou extends Piece{

  public Fou(int color){
    super(color);
  }
  public int getColor(){
    return this.couleur;
  }
  //passe par toutes les diagonales
  public boolean mouvementPossible(Echiquier board, int departX, int departY, int arriveeX, int arriveeY){
    if(Math.abs(departX-arriveeX)==Math.abs(departY-arriveeY)){
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
