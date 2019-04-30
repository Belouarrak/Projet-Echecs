import java.util.*;

public class Joueur{
  private ArrayList<Piece> pieces;
  private String nom;
  private boolean estBlanc;

  public Joueur(String name, boolean couleurBlanche){
    this.nom = name;
    this.estBlanc = couleurBlanche;
  }
  public void setNom(String name){
    this.nom = name;
  }
  public String getNom(){
    return this.nom;
  }
  public void setEstBlanche(boolean couleurBlanche){
    this.estBlanc = couleurBlanche;
  }
  public boolean getEstBlanche(){
    return this.estBlanc;
  }
  public void initialiserPiece(){
    //je dois créer les classes pour chaque type de pièce
  }
}
