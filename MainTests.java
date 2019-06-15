import java.io.*;
import java.util.*;

public class MainTests{
  public static void main(String[] args){
    try{
      Partie newGame = new Partie();
      newGame.lancerPartie();
    }
    catch(Exception e){System.out.println(e);}
  }
}
