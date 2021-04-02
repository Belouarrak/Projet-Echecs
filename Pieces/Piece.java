package Pieces;

import Board.Echiquier;

import java.util.*;
import java.lang.*;

public abstract class Piece{
  protected int couleur;

  public Piece(int color){
   this.couleur = color;
  }
  public abstract int getColor();
  //mouvementPossible, méthode abstraite dont les paramètres changent pour chaque pièce,
  //elle connait les positions de l'échiquier et va regarder aussi si il y a une pièce sur le chemin entre la pièce de départ et la pièce d'arrivée
  public abstract boolean mouvementPossible(Echiquier board, int departX, int departY, int arriveeX, int arriveeY);
  public abstract String toString();
}
