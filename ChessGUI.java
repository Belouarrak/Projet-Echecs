import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.print.attribute.standard.Media;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JToolBar;
import javax.swing.SwingConstants;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;


	public class ChessGUI extends JPanel {

	private final JPanel gui = new JPanel(new BorderLayout(3, 3)); // GUI
	private JButton[][] chessBoardSquares = new JButton[8][8]; // 8X8 Jbutton
	private Image[][] chessPieceImages = new Image[2][6]; // IMAGE DE CHESSPIECE
	private JPanel echiquier, boardConstrain; // Echiquier
	private JLabel message = new JLabel("Appuyez sur Nouvelle Partie !"); // JLABEL POUR ECHIQUIER
	private static final String COLS = "ABCDEFGH"; // REPRESENTATION DE L'INTERFACE
	public static final int QUEEN = 0, KING = 1, ROOK = 2, KNIGHT = 3, BISHOP = 4, PAWN = 5; // PIECES VARIABLES
	public static final int[] STARTING_ROW = { ROOK, KNIGHT, BISHOP, KING, QUEEN, BISHOP, KNIGHT, ROOK };// TABLEAU DE PIECES
	public static final int BLACK = 0, WHITE = 1; // VALEURS DES PIECES
	private JButton sauvegarder = new JButton("SAUVEGARDER");
	private JButton precedent = new JButton("PREC");
	private JButton abandon = new JButton("ABANDON");
	private JButton stop = new JButton("STOP");
	private JButton charger = new JButton("CHARGER");
	private JButton nouvellePartie = new JButton("NOUVELLE PARTIE");
	private JButton play = new JButton("PLAY");
	private Partie partie;
	private boolean playing = false;
	private Case[] caseMove = new Case[2];
	private ArrayList<String> historiqueMoves;
	private JTextArea panelHistorique;
	private boolean finpartie = false;
	Clip clip;

	ChessGUI() {
		initializeGui();
		this.partie = null;
		//this.setupBoard(partie.getEchiquier());
	}

	public final void initializeGui() {
		// create the images for the chess pieces
		createImages();
		// set up the main GUI
		gui.setBorder(new EmptyBorder(5, 5, 5, 5)); // ?????????
		JToolBar tools = new JToolBar(); // Toolbar en haut
		tools.setFloatable(true); // Pour pouvoir déplacer le JToolbar en haut
		gui.add(tools, BorderLayout.NORTH); // TOOLBAR AU NORD
		//JPanel info = new JPanel(new GridLayout(10, 1));
		String i = "FALSE";
		JLabel echec = new JLabel("ECHEC =   " + i);
		JLabel echecmat = new JLabel("ECHEC ET MAT =  " + i);
		this.panelHistorique = new JTextArea();
		this.panelHistorique.setEditable(false);
		Border blackline = BorderFactory.createLineBorder(Color.black);
		this.panelHistorique.setMaximumSize(new Dimension(170,700));
		this.panelHistorique.setPreferredSize(new Dimension(170,700));
		this.panelHistorique.setBorder(blackline);
		this.panelHistorique.setLineWrap(true);
		this.panelHistorique.setBackground(new Color(213, 205, 193));
		Font font = new Font("Arial", Font.BOLD, 14);
    this.panelHistorique.setFont(font);
		JScrollPane scrollPane = new JScrollPane(this.panelHistorique);
		echec.setVerticalAlignment(JLabel.TOP);
		echecmat.setVerticalAlignment(JLabel.TOP);
		//info.add(echec, BorderLayout.EAST);
		//info.add(echecmat, BorderLayout.EAST);
		//gui.add(info, BorderLayout.EAST);
		gui.add(scrollPane, BorderLayout.WEST);
		this.nouvellePartie.addActionListener(new NouvellePartieListener());
		tools.add(nouvellePartie);
		tools.add(charger);
		tools.add(sauvegarder);// TODO - add functionality! POUR SAUVEGARDER
		tools.add(precedent); // TODO - add functionality!$
		tools.add(new JButton("SUIV")); // TODO - add functionality!
		tools.addSeparator(); // SEPARER LES BOUTONS
		tools.add(abandon); // TODO - add functionality!
		tools.add(play);
		tools.add(stop);
		tools.addSeparator(); // AUTRE SEPARATION
		tools.add(message); // MESSAGE EN STRING VERS LA DROITE

		creerPanelEchiquier();
		addChessListener();
		addUndoListener();
		try {
			SimpleAudioPlayer();
		} catch (UnsupportedAudioFileException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (LineUnavailableException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public final JComponent getGui() {
		return gui;
	}
 	private void creerPanelEchiquier(){
			this.echiquier = new JPanel(new GridLayout(0, 9)) {
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
		this.boardConstrain = new JPanel(new GridBagLayout());
		this.boardConstrain.setBackground(ochre);
		this.boardConstrain.add(echiquier);
		gui.add(this.boardConstrain);

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
	public String toStringHistorique(){
		String historique = "";
		for (int i=0; i<this.historiqueMoves.size(); i++){
			historique+=this.historiqueMoves.get(i)+"\n";
		}
		return historique;
	}
	public void newGame(String blanc, String noir){
		this.partie = new Partie();
		this.partie.setJoueurBlanc(new Joueur(blanc,0));
		this.partie.setJoueurNoir(new Joueur(noir,1));
		this.partie.setJoueurCourant();
		this.finpartie=false;
		this.historiqueMoves = new ArrayList<String>();
		this.panelHistorique.setText(toStringHistorique());
		setupBoard(this.partie.getEchiquier());
		this.message.setText("C'est au tour de "+this.partie.getJoueurCourant().getNom()+" de jouer.");
	}
	//public void playMove()

	public void checkPartie(){
		this.message.setText("C'est au tour de "+this.partie.getJoueurCourant().getNom()+" de jouer.");
	  //vérifier si le joueur est en échec
		JOptionPane jop = new JOptionPane();
	  if (this.partie.estEnEchec(this.partie.getJoueurCourant(), this.partie.getEchiquier())==true){
			this.message.setText(this.partie.getJoueurCourant().getNom()+" est en echec.");
	    //vérifier si le joueur est en échecs et mat
	    if (this.partie.noLegalMovePossible(this.partie.getJoueurCourant(), this.partie.getEchiquier())==true){
				this.finpartie = true;
	      this.partie.setJoueurGagnant(this.partie.joueurOppose(this.partie.getJoueurCourant()));
				jop.showMessageDialog(this, "Partie terminee. Le gagnant de la partie est "+this.partie.getJoueurGagnant().getNom()+"!!!");
	    }
	  }
	  else{
	    //vérifier si Pat
	    if (this.partie.noLegalMovePossible(this.partie.getJoueurCourant(), this.partie.getEchiquier())==true){
				this.finpartie = true;
	      jop.showMessageDialog(this, "Vous avez atteint un Pat, personne ne gagne.");
	    }
	  }
	}


			public void SimpleAudioPlayer()
			        throws UnsupportedAudioFileException,
			        IOException, LineUnavailableException
			    {
				    // current status of clip
				    AudioInputStream audioInputStream;
				    String filePath = "./sound/TT.wav";
			        // create AudioInputStream object
			        audioInputStream =  AudioSystem.getAudioInputStream(new File(filePath).getAbsoluteFile());
			        // create clip reference
			        clip = AudioSystem.getClip();
			        // open audioInputStream to the clip
			        clip.open(audioInputStream);

			        clip.loop(Clip.LOOP_CONTINUOUSLY);
			        clip.start();
			    }



	public void setupBoard(Echiquier echiquier) {
		if (this.partie!=null){
			// set up les pieces
			for (int i = 0; i<8;i++) {
				for(int j = 0; j<8; j++){
					Case box = echiquier.getCase(Math.abs(j-7), i);
					//set up les deux dames
					if (box.estOccupee() && box.getPiece() instanceof Dame){
						if (box.getPiece().getColor()==1){
							chessBoardSquares[i][j].setIcon(new ImageIcon(chessPieceImages[BLACK][KING]));
						}
						else{
							chessBoardSquares[i][j].setIcon(new ImageIcon(chessPieceImages[WHITE][KING]));
						}
					}
					//set up les deux rois
					else if (box.estOccupee() && box.getPiece() instanceof Roi){
						if (box.getPiece().getColor()==1){
							chessBoardSquares[i][j].setIcon(new ImageIcon(chessPieceImages[BLACK][QUEEN]));
						}
						else{
							chessBoardSquares[i][j].setIcon(new ImageIcon(chessPieceImages[WHITE][QUEEN]));
						}
					}
					//set up les deux Cavaliers
					else if (box.estOccupee() && box.getPiece() instanceof Cavalier){
						if (box.getPiece().getColor()==1){
							chessBoardSquares[i][j].setIcon(new ImageIcon(chessPieceImages[BLACK][KNIGHT]));
						}
						else{
							chessBoardSquares[i][j].setIcon(new ImageIcon(chessPieceImages[WHITE][KNIGHT]));
						}
					}
					//set up les deux Fous
					else if (box.estOccupee() && box.getPiece() instanceof Fou){
						if (box.getPiece().getColor()==1){
							chessBoardSquares[i][j].setIcon(new ImageIcon(chessPieceImages[BLACK][BISHOP]));
						}
						else{
							chessBoardSquares[i][j].setIcon(new ImageIcon(chessPieceImages[WHITE][BISHOP]));
						}
					}
					//set up les deux Tours
					else if (box.estOccupee() && box.getPiece() instanceof Tour){
						if (box.getPiece().getColor()==1){
							chessBoardSquares[i][j].setIcon(new ImageIcon(chessPieceImages[BLACK][ROOK]));
						}
						else{
							chessBoardSquares[i][j].setIcon(new ImageIcon(chessPieceImages[WHITE][ROOK]));
						}
					}
					//set up les Pions
					else if (box.estOccupee() && box.getPiece() instanceof Pion){
						if (box.getPiece().getColor()==1){
							chessBoardSquares[i][j].setIcon(new ImageIcon(chessPieceImages[BLACK][PAWN]));
						}
						else{
							chessBoardSquares[i][j].setIcon(new ImageIcon(chessPieceImages[WHITE][PAWN]));
						}
					}
					else {
						chessBoardSquares[i][j].setIcon(null);
					}
				}
			}
		}
	}
	public void choisirPromotion(int color, Case box){
		Object[] possibilities = {"Dame", "Fou", "Tour", "Cavalier"};
		boolean prom = false;
		while(!prom ){
		String s = (String)JOptionPane.showInputDialog(this,"Veuillez choisir la nature de la piece nouvellement promue.", "Promotion du pion de "+this.partie.getJoueurCourant().getNom(),JOptionPane.PLAIN_MESSAGE,null,possibilities,"Dam");
		if ((s != null) && (s.length() > 0)) {
			if (s.equals("Dame")){
				Dame piece = new Dame(color);
				box.enleverPiece();
				box.occuperCase(piece);
				prom = true;
			}
			else if (s.equals("Fou")){
				Fou piece = new Fou(color);
				box.enleverPiece();
				box.occuperCase(piece);
				prom = true;
			}
			else if (s.equals("Tour")){
				Tour piece = new Tour(color);
				box.enleverPiece();
				box.occuperCase(piece);
				prom = true;
			}
			else if (s.equals("Cavalier")){
				Cavalier piece = new Cavalier(color);
				box.enleverPiece();
				box.occuperCase(piece);
				prom = true;
			}
		}
			else {
				s = "";
				prom = false;
			}
		}
  }
	public void addChessListener() {
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				chessBoardSquares[i][j].setActionCommand(Math.abs(j-7)+" "+ i);
				chessBoardSquares[i][j].addActionListener(new ButtonCaseListener());
			}
		}
	}
	public void retablicCouleurCases(){
		Color ochre = new Color(240, 240, 240);
		this.echiquier.setBackground(ochre);
		this.boardConstrain.setBackground(ochre);
		for (int ii = 0; ii < chessBoardSquares.length; ii++) {
			for (int jj = 0; jj < chessBoardSquares[ii].length; jj++) {
				JButton b = chessBoardSquares[jj][ii];
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
			}
		}
		setupBoard(partie.getEchiquier());
	}
	public void addUndoListener() {
		sauvegarder.addActionListener(new UndoListener());
		precedent.addActionListener(new UndoListener());
		stop.addActionListener(new UndoListener());
		play.addActionListener(new UndoListener());
	}
//inner-class avec les Listener
	class ButtonCaseListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			if (finpartie==false){
				//si aucune case n'est séléctionné on va séléctionner celle là et surligner la case sur le gui
				if (caseMove[0]==null){
					String coords = e.getActionCommand().replaceAll("\\s+","");
					int x = Integer.parseInt(Character.toString(coords.charAt(0)));
					int y = Integer.parseInt(Character.toString(coords.charAt(1)));
					if (partie.getEchiquier().getCase(x,y).estOccupee() && partie.getEchiquier().getCase(x,y).getPiece().getColor()==partie.getJoueurCourant().getPlayerColor()){
						caseMove = new Case[2];
						caseMove[0] = partie.getEchiquier().getCase(x,y);
						chessBoardSquares[y][Math.abs(x-7)].setBackground(Color.BLUE);
						for(int i=0;i<8;i++){
							for(int j=0;j<8;j++){
								if (partie.legalMove(partie.getJoueurCourant(), caseMove[0], partie.getEchiquier().getCase(i,j), partie.getEchiquier())){
									if(partie.moveMetEchec(partie.getJoueurCourant(), caseMove[0], partie.getEchiquier().getCase(i,j))){
										chessBoardSquares[j][Math.abs(i-7)].setBackground(Color.RED);
									}
									else{
										if(partie.getEchiquier().getCase(i,j).estOccupee()){
											chessBoardSquares[j][Math.abs(i-7)].setBackground(Color.GREEN);
										}
										else{
											chessBoardSquares[j][Math.abs(i-7)].setBackground(Color.BLUE);
										}
									}
								}
							}
						}
					}
				}
				//si une case est déja sélectionnée on va sélectionner celle là aussi si ce n'est pas la même que la première, la surligner puis essayer le mouvement
				else if (caseMove[1]==null && caseMove[0]!=null){
					String coords = e.getActionCommand().replaceAll("\\s+","");
					int x = Integer.parseInt(Character.toString(coords.charAt(0)));
					int y = Integer.parseInt(Character.toString(coords.charAt(1)));
					if (caseMove[0]!=partie.getEchiquier().getCase(x,y)){
						chessBoardSquares[y][Math.abs(x-7)].setBackground(Color.BLUE);
						caseMove[1] = partie.getEchiquier().getCase(x,y);
						if (partie.move(caseMove[0], caseMove[1])){
							if (partie.promouvoirPion(partie.getEchiquier().getCase(caseMove[1].getX(), caseMove[1].getY()))){
								choisirPromotion(partie.getJoueurCourant().getPlayerColor(), partie.getEchiquier().getCase(caseMove[1].getX(), caseMove[1].getY()));
								setupBoard(partie.getEchiquier());
							}
							partie.changerJoueurCourant();
							retablicCouleurCases();
							historiqueMoves.add(new String(caseMove[0].getStringCase()+caseMove[1].getStringCase()));
							caseMove[0]=null;
							caseMove[1]=null;
							checkPartie();
						}
						else if (partie.getEchiquier().getCase(x,y).getPiece()!=null && partie.getEchiquier().getCase(x,y).getPiece().getColor()==partie.getJoueurCourant().getPlayerColor()){
							caseMove[0]=partie.getEchiquier().getCase(x,y);
							caseMove[1]=null;
							retablicCouleurCases();
							chessBoardSquares[y][Math.abs(x-7)].setBackground(Color.BLUE);
							for(int i=0;i<8;i++){
								for(int j=0;j<8;j++){
									if (partie.legalMove(partie.getJoueurCourant(), caseMove[0], partie.getEchiquier().getCase(i,j), partie.getEchiquier())){
										if(partie.moveMetEchec(partie.getJoueurCourant(), caseMove[0], partie.getEchiquier().getCase(i,j))){
											chessBoardSquares[j][Math.abs(i-7)].setBackground(Color.RED);
										}
										else{
											if(partie.getEchiquier().getCase(i,j).estOccupee()){
												chessBoardSquares[j][Math.abs(i-7)].setBackground(Color.GREEN);
											}
											else{
												chessBoardSquares[j][Math.abs(i-7)].setBackground(Color.BLUE);
											}
										}
									}
								}
							}
						}
						else{
							caseMove=new Case[2];
							retablicCouleurCases();
						}
						setupBoard(partie.getEchiquier());
						panelHistorique.setText(toStringHistorique());
					}
					else{
						caseMove=new Case[2];
						retablicCouleurCases();
					}
				}
				else {
					caseMove=new Case[2];
					retablicCouleurCases();
				}
			}
		}
	}
	class NouvellePartieListener implements ActionListener{ // NOUVEL ACTION LISTENER

		@Override
		public void actionPerformed(ActionEvent e) {
			Object[] options = {"Oui, allons-y", "Je me suis trompe mdr"};
			int reponse = JOptionPane.showOptionDialog(null, "Voulez-vous creer une nouvelle partie?\nVous perdrez votre progression si vous jouiez une partie non sauvegardee. ", "Lancer une nouvelle partie",JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
			//boolean finpartie = false;
			if (reponse==JOptionPane.YES_OPTION){
				boolean bon = false;
				JOptionPane jOp = new JOptionPane();
				while (!bon){
					String nomblanc = jOp.showInputDialog("A qui sont les pieces blanches?");
					String nomnoir = jOp.showInputDialog("A qui sont les pieces noires?");
					if (((nomblanc != null) && (nomblanc.length() > 0)) && ((nomnoir != null) && (nomnoir.length() > 0)))  {
						newGame(nomblanc, nomnoir);
						bon = true;
					}
					else{
						jOp.showMessageDialog(gui,"Veuillez entre les noms des deux joueurs!");
						bon = false;
					}
				}
			}
		}
	}
	class UndoListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			if (e.getActionCommand() == "STOP") {
					clip.stop();
			}
			if (e.getActionCommand() == "PLAY") {
				System.out.println(e.getActionCommand());
				clip.start();
			}
			//System.out.println(e.getActionCommand());
			//partie.undoGame();
			//gui.undoGame();
		}
	}
}
