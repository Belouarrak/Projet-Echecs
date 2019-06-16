import java.io.*;
import java.util.*;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

public class MainTests{
	
  public static void main(String[] args){

	  ChessGUI vue = new ChessGUI();

			JFrame f = new JFrame("ChessChamp");
			f.add(vue.getGui());
			// Ensures JVM closes after frame(s) closed and
			// all non-daemon threads are finished
			f.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
			// See https://stackoverflow.com/a/7143398/418556 for demo.
			f.setLocationByPlatform(true);

			// ensures the frame is the minimum size it needs to be
			// in order display the components within it
			f.pack();
			// ensures the minimum size is enforced.
			f.setMinimumSize(f.getSize());
			f.setVisible(true);
	  
			
			
	  Partie partie = new Partie();
	  
	  ChessController controller = new ChessController(partie, vue);
	  vue.setVisible(true);
    /*try{
      Partie newGame = new Partie();
      newGame.lancerPartie();
    }
    catch(Exception e){System.out.println(e);}*/
  }
}

