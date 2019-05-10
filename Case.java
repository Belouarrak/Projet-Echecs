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
  public int getX(){
    return this.positionx;
  }
  public int getY(){
    return this.positiony;
  }
  public Piece getPiece(){
    return this.piece;
  }
  public void occuperCase(Piece unepiece){
    if(this.piece==null){
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
    if(this.piece==null){
      return("XXXXXX");
    }
    return this.piece.toString();
  }
}
