import java.util.*;

public class Case {
  private int positionx;
  private int positiony;
  private Piece piece;

  public Case(int x, int y){
    this.positionx = x;
    this.positiony = y;
    this.piece = null;
  }
  public void occuperCase(Piece unepiece){
    if(this.piece!=null){
      this.piece = unepiece;
    }
  }
  public boolean estOccupee() {
    if(piece != null){
      return true;}
    return false;
      }
  public Piece enleverPiece(){
    Piece enlevee = this.piece;
    this.piece = null;
    return enlevee;
  }
  public String toString(){
    return this.piece.toString());
  }
}
