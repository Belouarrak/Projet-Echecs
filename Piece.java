import java.util.*;

public abstract class Piece{
  private int couleur;

  public Piece(int color){
   this.couleur = color;
  }
  public abstract void mouvementPossible(int caseDepartx, int caseDeparty, int caseArriveex, int caseArriveey);
  public abstract String toString();
}
