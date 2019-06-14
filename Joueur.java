import java.util.*;

public class Joueur{
  private ArrayList<Piece> pieces; //chaque joueur a une collection constituée de ses pièces
  private ArrayList<Case[]> movesEchecs;
  private String nom;
  private int couleur;

  public Joueur(String name, int couleur){
    this.nom = name;
    this.couleur = couleur;
    this.pieces = new ArrayList<Piece>();
    this.movesEchecs = new ArrayList<Case[]>();
  }
  public void setNom(String name){
    this.nom = name;
  }
  public String getNom(){
    return this.nom;
  }
  public void setPlayerColor(int couleur){
    this.couleur = couleur;
  }
  public int getPlayerColor(){
    return this.couleur;
  }
  public void addPiece(Piece pawn){
    this.pieces.add(pawn);
  }
  public ArrayList<Piece> getPiecesJoueur(){
    return this.pieces;
  }
}
