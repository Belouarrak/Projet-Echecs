package Pieces;

import java.util.*;
import java.lang.*;
import Board.Echiquier;

public class Pion extends Piece{

  public Pion(int color){
    super(color);
  }
  public int getColor(){
    return this.couleur;
  }
  //en fonction de la couleur du pion les positions à prendre en compte sont inversées
  public boolean mouvementPossible(Echiquier board, int departX, int departY, int arriveeX, int arriveeY){
    //PION BLANC
    if (this.couleur==0) {
      //true si la case est de distance 2 devant la case de départ (x+2) et le pion est à sa positon initiale et la case entre les deux est vide
      if (departX==1){
        if(arriveeX==departX+2 && departY==arriveeY && !board.getCase(departX+1, departY).estOccupee() && !board.getCase(departX+2, departY).estOccupee()){
          return true;
        }
      }
      //true si la case est celle devant la case de départ (x+1) (la case d'arrivée ne doit pas être occupée)
      if(arriveeX==departX+1 && departY==arriveeY && !board.getCase(arriveeX, arriveeY).estOccupee()){
        return true;
      }
      //true si la case est celle sur la diagonale supérieure (droite ou gauche) de la case de départ et celle-ci est occupée par un ennemi
      if(arriveeX==departX+1 && Math.abs(arriveeY-departY)==1 && board.getCase(arriveeX, arriveeY).estOccupee() && board.getCase(arriveeX, arriveeY).getPiece().getColor()!=this.couleur ){
        return true;
      }
    }
    //PION NOIR
    else {
      //true si la case est de distance 2 devant la case de départ (x-2) et le pion est à sa positon initiale et la case entre les deux est vide
      if (departX==6){
        if(arriveeX==departX-2 && departY==arriveeY && !board.getCase(departX-1, departY).estOccupee() && !board.getCase(departX-2, departY).estOccupee()){
          return true;
        }
      }
      //true si la case est celle devant la case de départ (x-1)
      if(arriveeX==departX-1 && departY==arriveeY && !board.getCase(arriveeX, arriveeY).estOccupee()){
        return true;
      }
      //true si la case est celle sur la diagonale supérieure (droite ou gauche) de la case de départ et celle-ci est occupée par un ennemi
      if(arriveeX==departX-1 && Math.abs(arriveeY-departY)==1 && board.getCase(arriveeX, arriveeY).estOccupee() && board.getCase(arriveeX, arriveeY).getPiece().getColor()!=this.couleur){
        return true;
      }
    }
    return false;
  }
  public String toString(){
    if(this.couleur==0){
      return "U+2659";
    }
    else{
      return "U+265F";
    }
  }
}
