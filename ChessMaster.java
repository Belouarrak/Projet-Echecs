import java.io.*;
import java.util.*;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

public class ChessMaster extends JFrame{
  private ChessGUI gui;

  public ChessMaster(){
    super("ChessMater");
    this.gui = new ChessGUI();
    this.add(gui.getGui());
    // Ensures JVM closes after frame(s) closed and
    // all non-daemon threads are finished
    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    this.setLocationByPlatform(true);
    // ensures the frame is the minimum size it needs to be
    // in order display the components within it
    this.pack();
    // ensures the minimum size is enforced.
    this.setMinimumSize(this.getSize());
    this.setVisible(true);
    this.gui.setVisible(true);
  }

  public static void main(String[] args){
    ChessMaster chessMaster = new ChessMaster();
    /*try{
    Partie newGame = new Partie();
    newGame.lancerPartie();
  }
  catch(Exception e){System.out.println(e);}*/
  }
}
