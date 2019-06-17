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
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
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

public class IHMechec extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L; //SERIAL NECESSAIRE
	private final JPanel echecPanel = new JPanel(new BorderLayout(3, 3)); // GUI
	private JButton[][] carresEchiquier = new JButton[8][8]; // TABLEAU DE BOUTONS REPRESENTANT L'ECHIQUIER GRAPHIQUE QU'ON ASSOCIERA AVEC DES LISTENERS = PLUS EFFICACE QUE 64 BOUTONS
	
	private JLabel message = new JLabel("Appuyez sur Nouvelle Partie !"); // JLABEL POUR ECHIQUIER
	private static final String COLS = "ABCDEFGH"; // REPRESENTATION DE L'INTERFACE
	public static final int REINE = 0, ROI = 1, TOUR = 2, CHEVALIER = 3, FOU = 4, PION = 5; // PIECES VARIABLES POUR INSERER BOUTONS
																										
	private Image[][] imageEchiquier = new Image[2][6]; // IMAGE DE CHESSPIECE A INSERER DE SUBDIVISION SUR UNE IMAGE COMPOSE DE 64x64 SOUS-IMAGES REPRESENTANT LES ICONES DE PIECES
	private JButton sauvegarder = new JButton("SAUVEGARDER"); // BOUTON SAUVEGARDER
	private JButton precedent = new JButton("PREC"); // BOUTON PRECEDENT
	private JButton abandon = new JButton("ABANDON"); // BOUTON ABANDON
	private JButton stop = new JButton("STOP"); // BOUTON STOP
	private JButton charger = new JButton("CHARGER"); // BOUTON CHARGER
	private JButton nouvellePartie = new JButton("NOUVELLE PARTIE"); // BOUTON NOUVELLE PARTIE
	private JButton play = new JButton("PLAY"); // BOUTON PLAY
	private Partie partie; // CLASSE PARTIE DANS CLASSE IHMECHEC POUR RECUPERER METHODES ET FAIRE LE LIEN ENTRE LES DEUX
	private Case[] caseMove = new Case[2]; // DEPLACEMENT DE BOUTONS
	public static final int BLACK = 0, WHITE = 1; // VALEURS DES PIECES
	private ArrayList<String> historiqueMoves; // HISTORIQUE DE MOUVEMENTS QUI SERONT AFFICHE DANS UN JSCROLLPANE
	private JPanel echiquier, limiteEchiquier; // ECHIQUIER AVEC LIMITES VISUELLES ENTRE BOUTONS
	private JTextArea panelHistorique; // PANEL OU SERA AFFICHE LES HISTORIQUES
	private boolean finpartie = false; // VARIABLE POUR INDIQUER LA FIN DE LA PARTIE, SET SUR FALSE
	public static final int[] LIGNE_DEPART = { TOUR, CHEVALIER, FOU, ROI, REINE, FOU, CHEVALIER, TOUR }; // TABLEAU DE VARIABLES A INT PRECIS POUR PLACER LES PIONS DANS L'ECHIQUIER GRAPHIQUE
	Clip clip; // POUR JOUER DES FICHIERS AUDIOS

	IHMechec() {
		initializeGui(); // INIT POUR METTRE EN PLACE L'INTERFACE
		this.partie = null;
		// this.setupBoard(partie.getEchiquier());
	}

	public final void initializeGui() {
		// create the images for the chess pieces
		creationImages();
		// set up the main GUI
		echecPanel.setBorder(new EmptyBorder(5, 5, 5, 5)); // BORDURE ENTRE BOUTONS
		JToolBar menuFlottant = new JToolBar(); // TOOLBAR FLOTTANT
		menuFlottant.setFloatable(true); // POUR POUVOIR DEPLACER LE MENU A N'IMPORTE QUEL ENDROIT
		echecPanel.add(menuFlottant, BorderLayout.NORTH); // TOOLBAR AU NORD DE BASE, MAIS SA POSITION SERA MODIFIABLE
		// JPanel info = new JPanel(new GridLayout(10, 1));
		this.panelHistorique = new JTextArea(); // JTEXT AREA POUR LE MENU A DROITE DE MOUVEMENTS
		this.panelHistorique.setEditable(false);
		Border bordure = BorderFactory.createLineBorder(Color.black); // COULEUR DE BORDURE
		this.panelHistorique.setMaximumSize(new Dimension(170, 700)); // TAILLE MAXIMUM DE DIMENSION POUR CONSERVER LES DIMENSIONS DE LA FENETRE
		this.panelHistorique.setPreferredSize(new Dimension(170, 700)); // MEME CHOSE MAIS LA TAILLE PREFERREE QUE L'APPLICATION VA ESSAYER DE METTRE EN PLACE SI POSSIBLE
		this.panelHistorique.setBorder(bordure); // ON MET LA BORDURE
		this.panelHistorique.setLineWrap(true);
		this.panelHistorique.setBackground(new Color(213, 205, 193));
		Font font = new Font("Arial", Font.BOLD, 14); // POLICE POUR PANEL HISTORIQUE
		this.panelHistorique.setFont(font);
		JScrollPane scrollPane = new JScrollPane(this.panelHistorique); // JSCROLLPANE POUR LE PANEL HISTORIQUE
		echecPanel.add(scrollPane, BorderLayout.WEST);
		this.nouvellePartie.addActionListener(new NouvellePartieListener()); //LISTENER POUR LA CREATION D'UNE NOUVELLE PARTIE
		menuFlottant.add(nouvellePartie);
		menuFlottant.addSeparator(); //SEPARATION DES BOUTONS POUR PLUS DE CLARTE
		menuFlottant.add(charger);
		menuFlottant.add(sauvegarder);
		menuFlottant.addSeparator();
		menuFlottant.add(precedent);
		menuFlottant.add(new JButton("SUIV"));
		menuFlottant.addSeparator();
		menuFlottant.add(abandon);
		menuFlottant.addSeparator(); // SEPARER LES BOUTONS
		menuFlottant.add(play);
		menuFlottant.add(stop);
		menuFlottant.addSeparator(); // AUTRE SEPARATION
		menuFlottant.add(message); // MESSAGE EN STRING VERS LA DROITE

		creerPanelEchiquier();
		addChessListener(); // LISTENER
		addUndoListener(); // LISTENER

	}

	public final JComponent getEchecPanel() {
		return echecPanel; // POUR RECUPERER L'ECHIQUIER GRAPHIQUE
	}

	private void creerPanelEchiquier() {
		this.echiquier = new JPanel(new GridLayout(0, 9)) {

			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;};
		echiquier.setBorder(new CompoundBorder(new EmptyBorder(50, 50, 50, 50), new LineBorder(Color.BLACK))); //
		Color white = new Color(240, 240, 240);
		echiquier.setBackground(white);
		this.limiteEchiquier = new JPanel(new GridBagLayout());
		this.limiteEchiquier.setBackground(white);
		this.limiteEchiquier.add(echiquier);
		echecPanel.add(this.limiteEchiquier);

		// create the chess board squares
		Insets margeBoutons = new Insets(0, 0, 0, 0);
		for (int ii = 0; ii < carresEchiquier.length; ii++) {
			for (int jj = 0; jj < carresEchiquier[ii].length; jj++) {
				JButton b = new JButton();
				b.setMargin(margeBoutons);
				ImageIcon icon = new ImageIcon(new BufferedImage(64, 64, BufferedImage.TYPE_INT_ARGB));
				b.setIcon(icon);
				if ((jj % 2 == 1 && ii % 2 == 1)
						// ) {
						|| (jj % 2 == 0 && ii % 2 == 0)) {
					b.setBackground(Color.WHITE);
				} else {
					b.setBackground(Color.BLACK);
				}
				carresEchiquier[jj][ii] = b;
			}
		}

	
		echiquier.add(new JLabel(""));

		for (int ii = 0; ii < 8; ii++) {
			echiquier.add(new JLabel(COLS.substring(ii, ii + 1), SwingConstants.CENTER));}
		
		for (int ii = 0; ii < 8; ii++) {
			for (int jj = 0; jj < 8; jj++) {
				switch (jj) {
				case 0:
					echiquier.add(new JLabel("" + (9 - (ii + 1)), SwingConstants.CENTER));
				default:
					echiquier.add(carresEchiquier[jj][ii]);
				}
			}
		}
	}

	private final void creationImages() {
		try {
			BufferedImage bi = ImageIO.read(getClass().getResource("./img/pieces.png"));
			for (int ii = 0; ii < 2; ii++) {
				for (int jj = 0; jj < 6; jj++) {
					imageEchiquier[ii][jj] = bi.getSubimage(jj * 64, ii * 64, 64, 64);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public String toStringHistorique() { // On insere l'historique de mouvements dans le Jscroll avec ça
		String historique = "";
		for (int i = 0; i < this.historiqueMoves.size(); i++) {
			historique += this.historiqueMoves.get(i) + "\n";
		}
		return historique;
	}

	public void newGame(String blanc, String noir) { // On met en place une nouvelle partie après avoir appuyé le bouton nouvelle partie en insérant la partie Partie, les joueurs, le joueur courant, l'historique et le rafriachissement visuel
		this.partie = new Partie();
		this.partie.setJoueurBlanc(new Joueur(blanc, 0));
		this.partie.setJoueurNoir(new Joueur(noir, 1));
		this.partie.setJoueurCourant();
		this.finpartie = false;
		this.historiqueMoves = new ArrayList<String>();
		this.panelHistorique.setText(toStringHistorique());
		setupBoard(this.partie.getEchiquier()); // RAFRAICHIT L'ECHIQUIER
		this.message.setText("C'est au tour de " + this.partie.getJoueurCourant().getNom() + " de jouer.");
		
		try {
			SimpleAudioPlayer("./sound/TT.wav");
		} catch (UnsupportedAudioFileException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (LineUnavailableException e) {
			e.printStackTrace();
		}
	}

	public void checkPartie() {
		this.message.setText("C'est au tour de " + this.partie.getJoueurCourant().getNom() + " de jouer.");
		if (this.partie.estEnEchec(this.partie.getJoueurCourant(), this.partie.getEchiquier()) == true) {
			this.message.setText(this.partie.getJoueurCourant().getNom() + " est en echec.");
			// vérifier si le joueur est en échecs et mat
			if (this.partie.noLegalMovePossible(this.partie.getJoueurCourant(), this.partie.getEchiquier()) == true) {
				this.finpartie = true;
				this.partie.setJoueurGagnant(this.partie.joueurOppose(this.partie.getJoueurCourant()));
				try {
					if(clip.isRunning()) {
						clip.close();
					}
					SimpleAudioPlayer("./sound/fanfare.wav");
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
				JOptionPane.showMessageDialog(this, "Partie terminee. Le gagnant de la partie est "
						+ this.partie.getJoueurGagnant().getNom() + "!!!");
				
			}
		} else {
			// vérifier si Pat
			if (this.partie.noLegalMovePossible(this.partie.getJoueurCourant(), this.partie.getEchiquier()) == true) {
				this.finpartie = true;
				JOptionPane.showMessageDialog(this, "Vous avez atteint un Pat, personne ne gagne.");
			}
		}
	}

	public void SimpleAudioPlayer(String filePath) throws UnsupportedAudioFileException, IOException, LineUnavailableException {
		AudioInputStream audioInputStream;
		audioInputStream = AudioSystem.getAudioInputStream(new File(filePath).getAbsoluteFile());
		clip = AudioSystem.getClip();
		clip.open(audioInputStream);
		clip.loop(Clip.LOOP_CONTINUOUSLY);
		clip.start();
	}

	public void setupBoard(Echiquier echiquier) {
		if (this.partie != null) {
			// set up les pieces
			for (int i = 0; i < 8; i++) {
				for (int j = 0; j < 8; j++) {
					Case box = echiquier.getCase(Math.abs(j - 7), i);
					// set up les deux dames
					if (box.estOccupee() && box.getPiece() instanceof Dame) {
						if (box.getPiece().getColor() == 1) {
							carresEchiquier[i][j].setIcon(new ImageIcon(imageEchiquier[BLACK][ROI]));
						} else {
							carresEchiquier[i][j].setIcon(new ImageIcon(imageEchiquier[WHITE][ROI]));
						}
					}
					// set up les deux rois
					else if (box.estOccupee() && box.getPiece() instanceof Roi) {
						if (box.getPiece().getColor() == 1) {
							carresEchiquier[i][j].setIcon(new ImageIcon(imageEchiquier[BLACK][REINE]));
						} else {
							carresEchiquier[i][j].setIcon(new ImageIcon(imageEchiquier[WHITE][REINE]));
						}
					}
					// set up les deux Cavaliers
					else if (box.estOccupee() && box.getPiece() instanceof Cavalier) {
						if (box.getPiece().getColor() == 1) {
							carresEchiquier[i][j].setIcon(new ImageIcon(imageEchiquier[BLACK][CHEVALIER]));
						} else {
							carresEchiquier[i][j].setIcon(new ImageIcon(imageEchiquier[WHITE][CHEVALIER]));
						}
					}
					// set up les deux Fous
					else if (box.estOccupee() && box.getPiece() instanceof Fou) {
						if (box.getPiece().getColor() == 1) {
							carresEchiquier[i][j].setIcon(new ImageIcon(imageEchiquier[BLACK][FOU]));
						} else {
							carresEchiquier[i][j].setIcon(new ImageIcon(imageEchiquier[WHITE][FOU]));
						}
					}
					// set up les deux Tours
					else if (box.estOccupee() && box.getPiece() instanceof Tour) {
						if (box.getPiece().getColor() == 1) {
							carresEchiquier[i][j].setIcon(new ImageIcon(imageEchiquier[BLACK][TOUR]));
						} else {
							carresEchiquier[i][j].setIcon(new ImageIcon(imageEchiquier[WHITE][TOUR]));
						}
					}
					// set up les Pions
					else if (box.estOccupee() && box.getPiece() instanceof Pion) {
						if (box.getPiece().getColor() == 1) {
							carresEchiquier[i][j].setIcon(new ImageIcon(imageEchiquier[BLACK][PION]));
						} else {
							carresEchiquier[i][j].setIcon(new ImageIcon(imageEchiquier[WHITE][PION]));
						}
					} else {
						carresEchiquier[i][j].setIcon(null);
					}
				}
			}
		}
	}

	public void choisirPromotion(int color, Case box) {
		Object[] possibilities = { "Dame", "Fou", "Tour", "Cavalier" };
		boolean prom = false;
		while (!prom) {
			String s = (String) JOptionPane.showInputDialog(this,
					"Veuillez choisir la nature de la piece nouvellement promue.",
					"Promotion du pion de " + this.partie.getJoueurCourant().getNom(), JOptionPane.PLAIN_MESSAGE, null,
					possibilities, "Dam");
			if ((s != null) && (s.length() > 0)) {
				if (s.equals("Dame")) {
					Dame piece = new Dame(color);
					box.enleverPiece();
					box.occuperCase(piece);
					prom = true;
				} else if (s.equals("Fou")) {
					Fou piece = new Fou(color);
					box.enleverPiece();
					box.occuperCase(piece);
					prom = true;
				} else if (s.equals("Tour")) {
					Tour piece = new Tour(color);
					box.enleverPiece();
					box.occuperCase(piece);
					prom = true;
				} else if (s.equals("Cavalier")) {
					Cavalier piece = new Cavalier(color);
					box.enleverPiece();
					box.occuperCase(piece);
					prom = true;
				}
			} else {
				s = "";
				prom = false;
			}
		}
	}

	public void addChessListener() {
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				carresEchiquier[i][j].setActionCommand(Math.abs(j - 7) + " " + i);
				carresEchiquier[i][j].addActionListener(new ButtonCaseListener());
			}
		}
	}

	public void retablicCouleurCases() {
		Color gris = new Color(210, 210, 210);
		this.echiquier.setBackground(gris);
		this.limiteEchiquier.setBackground(gris);
		for (int ii = 0; ii < carresEchiquier.length; ii++) {
			for (int jj = 0; jj < carresEchiquier[ii].length; jj++) {
				JButton b = carresEchiquier[jj][ii];
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
		charger.addActionListener(new UndoListener());
		sauvegarder.addActionListener(new UndoListener());
		precedent.addActionListener(new UndoListener());
		stop.addActionListener(new UndoListener());
		play.addActionListener(new UndoListener());
		abandon.addActionListener(new UndoListener());
	}

//inner-class avec les Listener
	class ButtonCaseListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			if (finpartie == false) {
				// si aucune case n'est séléctionné on va séléctionner celle là et surligner la
				// case sur le gui
				if (caseMove[0] == null) {
					String coords = e.getActionCommand().replaceAll("\\s+", "");
					int x = Integer.parseInt(Character.toString(coords.charAt(0)));
					int y = Integer.parseInt(Character.toString(coords.charAt(1)));
					if (partie.getEchiquier().getCase(x, y).estOccupee() && partie.getEchiquier().getCase(x, y)
							.getPiece().getColor() == partie.getJoueurCourant().getPlayerColor()) {
						caseMove = new Case[2];
						caseMove[0] = partie.getEchiquier().getCase(x, y);
						carresEchiquier[y][Math.abs(x - 7)].setBackground(Color.BLUE);
						for (int i = 0; i < 8; i++) {
							for (int j = 0; j < 8; j++) {
								if (partie.legalMove(partie.getJoueurCourant(), caseMove[0],
										partie.getEchiquier().getCase(i, j), partie.getEchiquier())) {
									if (partie.moveMetEchec(partie.getJoueurCourant(), caseMove[0],
											partie.getEchiquier().getCase(i, j))) {
										carresEchiquier[j][Math.abs(i - 7)].setBackground(Color.RED);
									} else {
										if (partie.getEchiquier().getCase(i, j).estOccupee()) {
											carresEchiquier[j][Math.abs(i - 7)].setBackground(Color.GREEN);
										} else {
											carresEchiquier[j][Math.abs(i - 7)].setBackground(Color.BLUE);
										}
									}
								}
							}
						}
					}
				}
				// si une case est déja sélectionnée on va sélectionner celle là aussi si ce
				// n'est pas la même que la première, la surligner puis essayer le mouvement
				else if (caseMove[1] == null && caseMove[0] != null) {
					String coords = e.getActionCommand().replaceAll("\\s+", "");
					int x = Integer.parseInt(Character.toString(coords.charAt(0)));
					int y = Integer.parseInt(Character.toString(coords.charAt(1)));
					if (caseMove[0] != partie.getEchiquier().getCase(x, y)) {
						carresEchiquier[y][Math.abs(x - 7)].setBackground(Color.BLUE);
						caseMove[1] = partie.getEchiquier().getCase(x, y);
						if (partie.move(caseMove[0], caseMove[1])) {
							if (partie.promouvoirPion(
									partie.getEchiquier().getCase(caseMove[1].getX(), caseMove[1].getY()))) {
								choisirPromotion(partie.getJoueurCourant().getPlayerColor(),
										partie.getEchiquier().getCase(caseMove[1].getX(), caseMove[1].getY()));
								setupBoard(partie.getEchiquier());
							}
							partie.changerJoueurCourant();
							retablicCouleurCases();
							historiqueMoves.add(new String(
									caseMove[0].getStringCase().trim() + caseMove[1].getStringCase().trim()));
							caseMove[0] = null;
							caseMove[1] = null;
							checkPartie();
						} else if (partie.getEchiquier().getCase(x, y).getPiece() != null && partie.getEchiquier()
								.getCase(x, y).getPiece().getColor() == partie.getJoueurCourant().getPlayerColor()) {
							caseMove[0] = partie.getEchiquier().getCase(x, y);
							caseMove[1] = null;
							retablicCouleurCases();
							carresEchiquier[y][Math.abs(x - 7)].setBackground(Color.BLUE);
							for (int i = 0; i < 8; i++) {
								for (int j = 0; j < 8; j++) {
									if (partie.legalMove(partie.getJoueurCourant(), caseMove[0],
											partie.getEchiquier().getCase(i, j), partie.getEchiquier())) {
										if (partie.moveMetEchec(partie.getJoueurCourant(), caseMove[0],
												partie.getEchiquier().getCase(i, j))) {
											carresEchiquier[j][Math.abs(i - 7)].setBackground(Color.RED);
										} else {
											if (partie.getEchiquier().getCase(i, j).estOccupee()) {
												carresEchiquier[j][Math.abs(i - 7)].setBackground(Color.GREEN);
											} else {
												carresEchiquier[j][Math.abs(i - 7)].setBackground(Color.BLUE);
											}
										}
									}
								}
							}
						} else {
							caseMove = new Case[2];
							retablicCouleurCases();
						}
						setupBoard(partie.getEchiquier());
						panelHistorique.setText(toStringHistorique());
					} else {
						caseMove = new Case[2];
						retablicCouleurCases();
					}
				} else {
					caseMove = new Case[2];
					retablicCouleurCases();
				}
			}
		}
	}

	class NouvellePartieListener implements ActionListener { // NOUVEL ACTION LISTENER

		@Override
		public void actionPerformed(ActionEvent e) {
			Object[] options = { "Oui, allons-y", "Je me suis trompé" };
			int reponse = JOptionPane.showOptionDialog(null,
					"Voulez-vous creer une nouvelle partie?\nVous perdrez votre progression si vous jouiez une partie non sauvegardee. ",
					"Lancer une nouvelle partie", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null,
					options, options[0]);
			// boolean finpartie = false;
			if (reponse == JOptionPane.YES_OPTION) {
				boolean bon = false;
				while (!bon) {
					String nomblanc = JOptionPane.showInputDialog("A qui sont les pieces blanches?");
					String nomnoir = JOptionPane.showInputDialog("A qui sont les pieces noires?");
					if (((nomblanc != null) && (nomblanc.length() > 0))
							&& ((nomnoir != null) && (nomnoir.length() > 0))) {
						newGame(nomblanc, nomnoir);
						bon = true;
					} else {
						JOptionPane.showMessageDialog(echecPanel, "Veuillez entre les noms des deux joueurs!");
						bon = false;
					}
				}
			}
		}
	}

	class UndoListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			if (e.getActionCommand() == "STOP") {
				clip.stop();
			}
			if (e.getActionCommand() == "PLAY") {
				System.out.println(e.getActionCommand());
				clip.start();
			}
			if (e.getActionCommand() == "CHARGER") {
				try {
					String fichier = JOptionPane.showInputDialog("Veuillez entrer le nom du fichier a charger.");
					partie.chargerMoves(fichier);
					setupBoard(partie.getEchiquier());
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
			if (e.getActionCommand() == "SAUVEGARDER") {
				if (partie != null) {
					try { // SAUVEGARDER FICHIER
						String fichier = JOptionPane.showInputDialog(
								"Veuillez entrer le nom du fichier dans lequel sauvegarder (avec '.txt').");
						FileWriter fichierWrite = new FileWriter(fichier, true); // Ecrire le fichier, le true permet
																					// d'append le fichier au lieu de
																					// créer un nouvel objet
						BufferedWriter ecrire = new BufferedWriter(fichierWrite); // Stream chaining, de convention*
						ecrire.write(historiqueMoves.get(0));
						for (int i = 1; i < historiqueMoves.size(); i++) {
							ecrire.newLine(); // On passe à la ligne suivante
							ecrire.write(historiqueMoves.get(i)); // On écrit le contenu de la variable dans le fichier
						}
						ecrire.close(); // On ferme le fichier pour éviter les problèmes de mémoire
					} catch (IOException Ex) {
						System.out.println(Ex.getMessage());
					}
				}
			}

			if (e.getActionCommand() == "ABANDON") {
				JOptionPane bop = new JOptionPane();
				finpartie = true;
				partie.setJoueurGagnant(partie.joueurOppose(partie.getJoueurCourant()));
				try {
					if(clip.isRunning()) {
						clip.close();
					}
					SimpleAudioPlayer("./sound/fanfare.wav");
				} catch (UnsupportedAudioFileException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (LineUnavailableException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				JOptionPane.showMessageDialog(bop,
						"Partie terminee. Le gagnant de la partie est " + partie.getJoueurGagnant().getNom() + "!!!");


			}
		}
	}
}
