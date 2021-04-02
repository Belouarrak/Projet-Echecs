package Main;

import javax.swing.JFrame;

public class ChessMaster extends JFrame{
  /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
private IHMechec gui;

  public ChessMaster(){
    super("Echec-o-tron 3019 - Final Fantasy Edition");
    this.gui = new IHMechec();
    this.add(gui.getEchecPanel());
    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    this.setLocationByPlatform(true);
    this.pack();
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
