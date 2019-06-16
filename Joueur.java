import java.util.*;

public class Joueur{
  private String nom;
  private int couleur;

  public Joueur(String name, int couleur){
    this.nom = name;
    this.couleur = couleur;
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
}
