package DungeonoftheBrutalKing;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Toolkit;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextPane;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;
import javax.swing.text.BadLocationException;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;


public class GameStartMenu extends JFrame implements Runnable{

	private static final long serialVersionUID = 1L;

	public static void main(String[] args) throws IOException, FontFormatException, BadLocationException, InterruptedException {

		GameSettings myGameSettings = new GameSettings();
        LoadSaveGame myLoadSaveGame = new LoadSaveGame();
        CharacterCreation myCharacterCreation = new CharacterCreation();

		
		new Thread(new GameStartMenu()).start();
		
		


		//***********************************************
		//**** Creating JFrame, JPanel, and JButtons ****
		//***********************************************

		JFrame StartMenuFrame = null;
		JPanel GameTitlePanel, StartButtonPanel = null;
		JButton ContinueGameButton, StartNewGameButton, LoadExistingGameButton, ExitGameButton = null;
		
		JTextPane GameTitlePane = null;

		
		int width, height = 0;

		//***************************************************
		//**** Initializing JFrame, JPanel, and JButtons ****
		//***************************************************

		StartMenuFrame = new JFrame("Dungeon of the Brutal King");
		GameTitlePanel = new JPanel();
		StartButtonPanel = new JPanel();
		StartNewGameButton = new JButton("Start New Game");
		
		ContinueGameButton = new JButton("Continue Current Game");
		LoadExistingGameButton = new JButton("Load Exisiting Game");
		ExitGameButton = new JButton("Exit Game");
		GameTitlePane = new JTextPane();

		//***************************************************
		//******** Getting Screen Width and Height **********
		//***************************************************

		// getScreenSize() returns the size of the screen in pixels
        Dimension size = Toolkit.getDefaultToolkit().getScreenSize();

        // width will store the width of the screen
        width = (int)size.getWidth();

        // height will store the height of the screen
        height = (int)size.getHeight();

      //***************************************************
      //***************** Setting Up JFrame ***************
      //***************************************************

        StartMenuFrame.setLayout(new BorderLayout());
        StartMenuFrame.setSize(width, height);
        StartMenuFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        StartMenuFrame.setUndecorated(true);
        StartMenuFrame.getContentPane().setBackground(myGameSettings.colorLightBrown);
        StartMenuFrame.getContentPane().setForeground(myGameSettings.colorLightBrown);


      //***************************************************
      //**** Setting Up and adding JPanel to JFrame********
      //***************************************************

        GameTitlePanel.setLayout(new BorderLayout());
        StartButtonPanel.setLayout(new FlowLayout());

        GameTitlePanel.setMaximumSize(new Dimension(450, 200));
        StartButtonPanel.setMaximumSize(new Dimension(250, 350));

        StartMenuFrame.add(GameTitlePanel, BorderLayout.CENTER);
        StartMenuFrame.add(StartButtonPanel, BorderLayout.SOUTH);

        StartButtonPanel.setBackground(myGameSettings.colorLightBrown);
        StartButtonPanel.setForeground(myGameSettings.colorLightBrown);

        GameTitlePanel.setBackground(myGameSettings.colorLightBrown);
        GameTitlePanel.setForeground(myGameSettings.colorLightBrown);


      //***************************************************
      //********** Adding Fields to JPanel ****************
      //***************************************************

        StartButtonPanel.add(ContinueGameButton, FlowLayout.LEFT);
        StartButtonPanel.add(StartNewGameButton, FlowLayout.LEFT);
       
        StartButtonPanel.add(LoadExistingGameButton, FlowLayout.CENTER);
        StartButtonPanel.add(ExitGameButton, FlowLayout.RIGHT);


      //***************************************************
      //********** Setting settings for JTextPane *********
      //***************************************************

        //StartImageLabel = new JLabel(new ImageIcon(myGameSettings.myStartMenuPicture));
        GameTitlePanel.add(GameTitlePane);
        GameTitlePane.setEditable(false);
     
        Font avatarFont = new Font("src\\DungeonoftheBrutalKing\\Fonts\\fontAvatar.ttf", Font.BOLD, 100);

        StyledDocument doc = GameTitlePane.getStyledDocument();
        SimpleAttributeSet attributeSet = new SimpleAttributeSet();
        StyleConstants.setAlignment(attributeSet, StyleConstants.ALIGN_CENTER);
        doc.setParagraphAttributes(0, doc.getLength(), attributeSet, false);
             
        
        GameTitlePane.setForeground(myGameSettings.colorLightYellow);
        GameTitlePane.setBackground(myGameSettings.colorLightBrown);
        GameTitlePane.setFont(avatarFont);
       
        GameTitlePane.setText("\nDungeon\n of the\nBrutal King");
        
        GameTitlePane.setCharacterAttributes(attributeSet, true);


      //***************************************************
      //************ Setting Up Buttons *******************
      //***************************************************

        ContinueGameButton.setPreferredSize(new Dimension(200,50));
        StartNewGameButton.setPreferredSize(new Dimension(200, 50));
        LoadExistingGameButton.setPreferredSize(new Dimension(200, 50));
        ExitGameButton.setPreferredSize(new Dimension(200, 50));

        ContinueGameButton.setBackground(myGameSettings.colorGrey);
        ContinueGameButton.setForeground(myGameSettings.colorWhite);
        
        StartNewGameButton.setBackground(myGameSettings.colorGrey);
        StartNewGameButton.setForeground(myGameSettings.colorWhite);
        
        LoadExistingGameButton.setBackground(myGameSettings.colorGrey);
        LoadExistingGameButton.setForeground(myGameSettings.colorWhite);

        ExitGameButton.setBackground(myGameSettings.colorGrey);
        ExitGameButton.setForeground(myGameSettings.colorWhite);

    if (GameSettings.SavedGameDirectory != null) {
        File directory = new File(myGameSettings.SavedGameDirectory);
        if (directory.isDirectory()) {
           String[] files = directory.list();
           if (files.length > 0) {
              LoadExistingGameButton.setVisible(true);
              ContinueGameButton.setVisible(true);
           } else {
        	  LoadExistingGameButton.setVisible(false);
        	  ContinueGameButton.setVisible(false);
           }
        }
    }

        StartMenuFrame.setVisible(true);

        StartNewGameButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                File saveFile = new File(GameSettings.SavedGameDirectory + "/InitlaCharecterSave.txt");

                if (saveFile.exists()) {
                    int response = JOptionPane.showConfirmDialog(null,
                            "InitlaCharecterSave.txt exists. Do you want to delete it and start a new game?",
                            "File Exists",
                            JOptionPane.YES_NO_OPTION,
                            JOptionPane.QUESTION_MESSAGE);

                    if (response == JOptionPane.YES_OPTION) {
                        if (saveFile.delete()) {
                            try {
								proceedToCreateCharacter(e);
							} catch (IOException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
                        } else {
                            JOptionPane.showMessageDialog(null,
                                    "Failed to delete the file. Please try again.",
                                    "Error",
                                    JOptionPane.ERROR_MESSAGE);
                        }
                    } else {
                        returnToPreviousScreen();
                    }
                } else {
                    try {
						proceedToCreateCharacter(e);
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
                }
            }

            private void proceedToCreateCharacter(ActionEvent e) throws IOException {
                Window window = SwingUtilities.getWindowAncestor((Component) e.getSource());
                MusicPlayer.stopMidi();
                window.dispose();
                myCharacterCreation.createCharector();
            }

            private void returnToPreviousScreen() {
                // Code to return to the previous screen
            }
        });

        ContinueGameButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					Window window = SwingUtilities.getWindowAncestor((Component) e.getSource());
					
					myLoadSaveGame.ContinueCurrentGame();
					window.dispose();
					
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
				
				
				
			}
        	
        });

        LoadExistingGameButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				MusicPlayer.stopMidi();


				Window window = SwingUtilities.getWindowAncestor((Component) e.getSource());
				myLoadSaveGame.LoadGame();
				window.dispose();
			}

        });


        ExitGameButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				System.exit(0);

			}});



	}

	@Override
	public void run() {
		MusicPlayer soundplayer = new MusicPlayer();
		try {
			soundplayer.midiPlayer("Stones.mid");
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

	



}