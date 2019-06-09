import java.io.*;
import java.util.*;

public class MainTests{
  public static void main(String[] args){
    try{
      Partie newGame = new Partie();
      newGame.ChargerPartie();
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
  
  /*public void ChargerPartie() {
	  String reponse ="O";
	  System.out.println("Voulez-vous charger une partie? (O)");
	  Scanner input = new Scanner(System.in);
	  reponse = input.nextLine();}*/
	  

}
/*System.out.println("Voulez-vous chargez une partie?");
ChargerPartie();*/