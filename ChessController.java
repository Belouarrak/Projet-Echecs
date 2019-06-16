import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ChessController {
	private Partie partie;
	private ChessGUI gui;

/*@param partie
@param gui*/

	public ChessController() {
}

	/*public ChessController(Partie partie, ChessGUI gui) {
		this.partie = partie;
		this.gui = gui;
		this.gui.addChessListener(new ButtonListener());
		this.gui.addUndoListener(new UndoListener());
		this.gui.setupBoard(partie.getEchiquier());
	}

	class ButtonListener implements ActionListener
	{

		@Override
		public void actionPerformed(ActionEvent e) {
			System.out.println(e.getActionCommand());
		}
	}
	class NouvellePartieListener implements ActionListener{ // NOUVEL ACTION LISTENER

		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent e) {
			partie.move();
			gui.setupBoard(partie.getEchiquier()); // A VOIR
		}

	};
	class UndoListener implements ActionListener
	{

		@Override
		public void actionPerformed(ActionEvent e) {
			System.out.println(e.getActionCommand());
			//partie.undoGame();
			//gui.undoGame();

		}



	}*/
	}
