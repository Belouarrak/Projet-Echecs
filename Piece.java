import java.util.*;

public abstract class Piece{
  private int couleur;

  public Piece(int color){
   this.couleur = color;
  }
  public abstract boolean mouvementPossible(int departX, int departY, int arriveeX, int arriveeY);
  public abstract String toString();
}
