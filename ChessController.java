import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ChessController {
	private Partie partie;
	private ChessGUI gui;
	
/*@param partie
@param gui*/
	
	public ChessController() {
}
	
	public ChessController(Partie partie, ChessGUI gui) {
		this.partie = partie;
		this.gui = gui;
		gui.addChessListener(new ButtonListener());
		gui.addUndoListener(new UndoListener());
	}
	
	class ButtonListener implements ActionListener
	{

		@Override
		public void actionPerformed(ActionEvent e) {
			System.out.println(e.getActionCommand());
		}
	}
	
	class UndoListener implements ActionListener
	{

		@Override
		public void actionPerformed(ActionEvent e) {
			System.out.println(e.getActionCommand());
			//partie.undoGame();
			//gui.undoGame();
			
		}


			
		}
	}

