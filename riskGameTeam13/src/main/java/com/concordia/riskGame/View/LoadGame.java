package com.concordia.riskGame.View;

import java.io.File;
import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;



import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

import com.concordia.riskGame.control.GameDriver;
import com.concordia.riskGame.model.Map.MapContents;
import com.concordia.riskGame.model.Map.MapParseProcessor;

public class LoadGame {

	private JFileChooser fileChooser;
	private String filePath;

	public LoadGame()
	{
		System.out.println("##### Select a file to load the game #######");
		System.out.println("#### Okay Button is Clicked ####");
		
		
		fileChooser = new JFileChooser();
		fileChooser.setDialogTitle("Select the saved game file");
		fileChooser.setCurrentDirectory(new File(System.getProperty("user.home")));
		

		int result = fileChooser.showOpenDialog(fileChooser);
		fileChooser.setLocation(500, 200);
		fileChooser.setSize(500, 500);
		fileChooser.setVisible(true);

		
		if (result == JFileChooser.APPROVE_OPTION) {
			File selectedFile = fileChooser.getSelectedFile();
			System.out.println("Selected file: " + selectedFile.getAbsolutePath().toString());
			filePath = selectedFile.getAbsolutePath().toString();
			System.out.println("###### The selected file path is ####### : "+filePath );
			readSavedMapContent();
		}

		
	}
	
	public void readSavedMapContent()
	{
		try {
			System.out.println("##### Calling readSavedMapContent() #########");
			System.out.println("##### file path is  ######### : "+ filePath );
			FileInputStream saveFile = new FileInputStream(filePath);
			ObjectInputStream restore = new ObjectInputStream(saveFile);
			Object obj = restore.readObject();
			System.out.println("####### Saved file object is ####### : "+obj.toString());
			
			MapContents mapContentObject;
			mapContentObject = (MapContents)obj;
			System.out.println("####### mapContentObject file object is ####### : "+mapContentObject.toString());
			
			System.out.println("##########  Loaded Saved game File #######");
			System.out.println("##########  Map Content Number of Player       ####### : "+mapContentObject.getPlayerList().size());
			System.out.println("##########  Map Content Number of Countries  ####### : "+mapContentObject.getCountryList().size());
	
			int rotateValue = mapContentObject.getRotateCount();
			System.out.println("######### The roate count value is ######## : "+rotateValue);
			List<com.concordia.riskGame.model.Player.Player> playerListLoadGame = new ArrayList();
			playerListLoadGame = mapContentObject.getPlayerList();
			Collections.rotate(playerListLoadGame, playerListLoadGame.size() - rotateValue);
			mapContentObject.setPlayerList(playerListLoadGame);
			MapParseProcessor mp = new MapParseProcessor();
			mp.callGamePhase(mapContentObject);
		
			
		}catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	
}