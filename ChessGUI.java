import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.swing.*;
import javax.swing.border.*;
import java.net.URL;
import javax.imageio.ImageIO;

	public class ChessGUI extends JPanel {

	private final JPanel gui = new JPanel(new BorderLayout(3, 3)); // GUI
	private JButton[][] chessBoardSquares = new JButton[8][8]; // 8X8 Jbutton
	private Image[][] chessPieceImages = new Image[2][6]; // IMAGE DE CHESSPIECE
	private JPanel echiquier; // Echiquier
	private final JLabel message = new JLabel("Chess Champ is ready to play!"); // JLABEL POUR ECHIQUIER
	private static final String COLS = "ABCDEFGH"; // REPRESENTATION DE L'INTERFACE
	public static final int QUEEN = 0, KING = 1, ROOK = 2, KNIGHT = 3, BISHOP = 4, PAWN = 5; // PIECES VARIABLES
	public static final int[] STARTING_ROW = { ROOK, KNIGHT, BISHOP, KING, QUEEN, BISHOP, KNIGHT, ROOK };// TABLEAU DE PIECES
	public static final int BLACK = 0, WHITE = 1; // VALEURS DES PIECES
	private JButton sauvegarder = new JButton("SAUVEGARDER");
	private JButton precedent = new JButton("PREC");
	private JButton abandon = new JButton("ABANDON");

	ChessGUI() {
		initializeGui();
	}

	public final void initializeGui() {
		// create the images for the chess pieces
		createImages();

		// set up the main GUI
		gui.setBorder(new EmptyBorder(5, 5, 5, 5)); // ?????????
		JToolBar tools = new JToolBar(); // Toolbar en haut
		tools.setFloatable(true); // Pour pouvoir déplacer le JToolbar en haut
		gui.add(tools, BorderLayout.NORTH); // TOOLBAR AU NORD
		JPanel info = new JPanel(new GridLayout(10, 1));
		String i = "FALSE";
		JLabel echec = new JLabel("ECHEC =   " + i);
		JLabel echecmat = new JLabel("ECHEC ET MAT =  " + i);
		echec.setVerticalAlignment(JLabel.TOP);
		echecmat.setVerticalAlignment(JLabel.TOP);
		info.add(echec, BorderLayout.EAST);
		info.add(echecmat, BorderLayout.EAST);
		gui.add(info, BorderLayout.EAST);

		Action newGameAction = new AbstractAction("NOUVELLE PARTIE") { // NOUVEL ACTION LISTENER

			/**
			 *
			 */
			private static final long serialVersionUID = 1L;

			@Override
			public void actionPerformed(ActionEvent e) {
				setupNewGame(); // A VOIR
			}

		};
		tools.add(newGameAction);

		tools.add(sauvegarder); // TODO - add functionality! POUR SAUVEGARDER
		tools.add(precedent); // TODO - add functionality!$
		tools.add(new JButton("SUIV")); // TODO - add functionality!
		tools.addSeparator(); // SEPARER LES BOUTONS
		tools.add(new JButton("ABANDON")); // TODO - add functionality!
		tools.addSeparator(); // AUTRE SEPARATION
		tools.add(message); // MESSAGE EN STRING VERS LA DROITE

		echiquier = new JPanel(new GridLayout(0, 9)) {

			/**
			 *
			 */
			private static final long serialVersionUID = 1L;

			/**
			 * Override the preferred size to return the largest it can, in a square shape.
			 * Must (must, must) be added to a GridBagLayout as the only component (it uses
			 * the parent as a guide to size) with no GridBagConstaint (so it is centered).
			 */
			@Override
			public final Dimension getPreferredSize() {
				Dimension d = super.getPreferredSize(); // Encapsulation de la taille de l'échiquier
				Dimension prefSize = null;
				Component c = getParent(); // ON RECUPERE LA TAILLE DU PARENT
				if (c == null) {
					prefSize = new Dimension((int) d.getWidth(), (int) d.getHeight());
				} else if (c != null && c.getWidth() > d.getWidth() && c.getHeight() > d.getHeight()) {
					prefSize = c.getSize();
				} else {
					prefSize = d;
				}
				int w = (int) prefSize.getWidth();
				int h = (int) prefSize.getHeight();
				// the smaller of the two sizes
				int s = (w > h ? h : w);
				return new Dimension(s, s);
			}
		};
		echiquier.setBorder(new CompoundBorder(new EmptyBorder(50, 50, 50, 50), new LineBorder(Color.BLACK)));
		// Set the BG to be ochre
		Color ochre = new Color(240, 240, 240);
		echiquier.setBackground(ochre);
		JPanel boardConstrain = new JPanel(new GridBagLayout());
		boardConstrain.setBackground(ochre);
		boardConstrain.add(echiquier);
		gui.add(boardConstrain);

		// create the chess board squares
		Insets buttonMargin = new Insets(0, 0, 0, 0);
		for (int ii = 0; ii < chessBoardSquares.length; ii++) {
			for (int jj = 0; jj < chessBoardSquares[ii].length; jj++) {
				JButton b = new JButton();
				b.setMargin(buttonMargin);
				// our chess pieces are 64x64 px in size, so we'll
				// 'fill this in' using a transparent icon..
				ImageIcon icon = new ImageIcon(new BufferedImage(64, 64, BufferedImage.TYPE_INT_ARGB));
				b.setIcon(icon);
				if ((jj % 2 == 1 && ii % 2 == 1)
						// ) {
						|| (jj % 2 == 0 && ii % 2 == 0)) {
					b.setBackground(Color.WHITE);
				} else {
					b.setBackground(Color.BLACK);
				}
				chessBoardSquares[jj][ii] = b;
			}
		}

		/*
		 * fill the chess board
		 */
		echiquier.add(new JLabel(""));
		// fill the top row
		for (int ii = 0; ii < 8; ii++) {
			echiquier.add(new JLabel(COLS.substring(ii, ii + 1), SwingConstants.CENTER));
		}
		// fill the black non-pawn piece row
		for (int ii = 0; ii < 8; ii++) {
			for (int jj = 0; jj < 8; jj++) {
				switch (jj) {
				case 0:
					echiquier.add(new JLabel("" + (9 - (ii + 1)), SwingConstants.CENTER));
				default:
					echiquier.add(chessBoardSquares[jj][ii]);
				}
			}
		}
	}

	public final JComponent getGui() {
		return gui;
	}

	private final void createImages() {
		try {
			BufferedImage bi = ImageIO.read(getClass().getResource("./img/pieces.png"));
			for (int ii = 0; ii < 2; ii++) {
				for (int jj = 0; jj < 6; jj++) {
					chessPieceImages[ii][jj] = bi.getSubimage(jj * 64, ii * 64, 64, 64);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(1);
		}
	}

	/**
	 * Initializes the icons of the initial chess board piece places
	 */
	private final void setupNewGame() {
		message.setText("Make your move!");
		// set up the black pieces
		for (int i2 = 0; i2 < STARTING_ROW.length; i2++) {
			chessBoardSquares[i2][0].setIcon(new ImageIcon(chessPieceImages[BLACK][STARTING_ROW[i2]]));
		}
		for (int ii = 0; ii < STARTING_ROW.length; ii++) {
			chessBoardSquares[ii][1].setIcon(new ImageIcon(chessPieceImages[BLACK][PAWN]));
		}
		// set up the white pieces
		for (int ii = 0; ii < STARTING_ROW.length; ii++) {
			chessBoardSquares[ii][6].setIcon(new ImageIcon(chessPieceImages[WHITE][PAWN]));
		}
		for (int ii = 0; ii < STARTING_ROW.length; ii++) {
			chessBoardSquares[ii][7].setIcon(new ImageIcon(chessPieceImages[WHITE][STARTING_ROW[ii]]));
		}
	}

	public void addChessListener(ActionListener listener) {
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				chessBoardSquares[i][j].setActionCommand(Math.abs(j-7)+ " " + i);
				chessBoardSquares[i][j].addActionListener(listener);
			}

		}
	}


	public void addUndoListener(ActionListener undoListener) {
		sauvegarder.addActionListener(undoListener);
		//precedent.addActionListener(undoListener);
	}


}
