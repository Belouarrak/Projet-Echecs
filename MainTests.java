import java.io.*;

public class MainTests{
  public static void main(String[] args){
    try{
      Partie newGame = new Partie();
      newGame.initialiserPartie();
      System.out.println(newGame.getEchiquier().toString());
    }
    catch(Exception e){System.out.println(e);}
  }
}
