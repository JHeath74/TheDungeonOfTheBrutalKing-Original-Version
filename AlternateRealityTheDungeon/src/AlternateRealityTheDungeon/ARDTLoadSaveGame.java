package AlternateRealityTheDungeon;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.Date;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class ARDTLoadSaveGame
{

	ARTDCharecter myChar = new ARTDCharecter();
	
	public void SaveGame() throws IOException, ParseException
	{
		Date date = Calendar.getInstance().getTime();
		DateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd-hh:mm:ss");  
 
		
		String datetime = dateFormat.format(date);  
		
		datetime = datetime.replaceAll(":", "."); 
		
		String GameSaveDateTime = "src/AlternateRealityTheDungeon/TextFiles/SaveGame/SavedGame"+ datetime +".txt";
		String SavedGameName = "SavedGame"+ datetime;
		
		
		File GameSave = new File(GameSaveDateTime);
		boolean isFileCreated = GameSave.createNewFile(); 
		
		FileWriter writer = new FileWriter(GameSaveDateTime);
		
		
		for(String Charinfo: myChar.CharInfo()) {
		  writer.write(Charinfo + System.lineSeparator());
		}
		writer.close();
		
		JOptionPane.showMessageDialog(null, "Game Save: " + SavedGameName);
	}
	
	public void LoadGame()
	{
		JFrame loadGame = new JFrame("Load Game");
		JPanel lg = new JPanel(new BorderLayout());
		JButton load = new JButton("Load Game");
		JComboBox loadGameSelection = new JComboBox();
		
		File loadgamefiles = new File("src\\AlternateRealityTheDungeon\\TextFiles\\SaveGame\\");
		
		File[] listOfFiles = loadgamefiles.listFiles();
		
		for(int i = 0; i <listOfFiles.length; i++)
		{
			loadGameSelection.addItem(listOfFiles[i].getName());
		}
		
		loadGameSelection.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Copy information from the select game file to the arraylist
				
			}
			
		});
		
		
		lg.add(loadGameSelection);
		loadGame.add(lg, BorderLayout.CENTER);
		loadGame.add(load, BorderLayout.SOUTH);
			
		loadGame.setLocationRelativeTo(null);
		loadGame.setSize(640, 480);
		
		loadGame.setVisible(true);
		
	}
	
	public void StartGameLoadCharecter()
	{
		
	}
	
	
}
