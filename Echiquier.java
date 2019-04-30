import java.util.*;

public class Echiquier{
  private Case[][] plateau;

  public Echiquier(){
    this.plateau = new Case[8][8];
    for(int i=0; i<plateau.length; i++){
            for(int j=0; j<plateau.length; j++){
                this.plateau[i][j] = new Case(i, j);
            }
        }
  }
  public Case getCase(int x, int y){
    return this.case[x][y];
  }
}
