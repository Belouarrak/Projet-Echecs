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
  //getStringCase va retourner les coordonnées de la case au format normal de position aux échecs (ex:"b2"), va servir pour les prints de certains messages d'erreur
  public String getStringCase(){
    String[] abc = {"a","b","c","d","e","f","g","h"};
    String y = new String(abc[this.positiony]);
    String x = new String(Integer.toString(positionx+1));
    String str = y+x;
    return str;
  }
  //occuperCase prend une piece en paramètre et va poser celle-ci sur la case, si la case était initialement occupée, la case initiale sera retournée
  public Piece occuperCase(Piece unepiece){
    Piece pieceEnlevee = this.piece;
    this.piece = unepiece;
    return pieceEnlevee;
  }
  //estOccupee retourne true si la case est occupee
  public boolean estOccupee() {
    if(piece != null){
      return true;}
    return false;
      }
  //retourne la pièce occupee est attache null à la case
  public Piece enleverPiece(){
    Piece enlevee = this.piece;
    this.piece = null;
    return enlevee;
  }
  //la case vide, en ce moment, est marquée par "xxxxxx"
  public String toString(){
    if(this.piece==null){
      return("XXXXXX");
    }
    return this.piece.toString();
  }
}
