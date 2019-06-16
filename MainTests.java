import java.io.*;
import java.util.*;

public class MainTests{
  public static void main(String[] args){
    try{
      Partie newGame = new Partie();
      //newGame.chargerMoves("Test checkmate.txt");
      newGame.lancerPartie();
      System.out.println(newGame.getEchiquier().toString());
    }
    catch(Exception e){System.out.println(e);}
  }
}
