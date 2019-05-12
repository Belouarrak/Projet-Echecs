import java.io.*;
import java.util.*;

public class MainTests{
  public static void main(String[] args){
    try{
      Partie newGame = new Partie();
      String reponse ="O";
      Scanner input = new Scanner(System.in);
      while(!reponse.equals("N")){
        newGame.move();
        System.out.println("Continuer? (N)");
        reponse = input.nextLine();
      }
    }
    catch(Exception e){System.out.println(e);}
  }
}
