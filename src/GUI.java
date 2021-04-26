import javax.swing.*;
import javax.swing.filechooser.*;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.nio.file.*;


public class GUI extends JFrame {
	
	static Path filePath;
	static final int LOAD_FILE = 0;
	static final int SAVE_FILE = 1;
	private static Tournament generatedTournament;

	public static void main(String[] args) {
		
		try 
		{
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} 
		catch (Exception e) {}

		JFrame main = new JFrame("Tournament Tools");
		JFrame bracket = new JFrame("Bracket");
		Image icon = Toolkit.getDefaultToolkit().getImage("data/Tournament_Bracket_Logo.png"); 
		main.setIconImage(icon);
		bracket.setIconImage(icon);
		
		JTextArea bracketText = new JTextArea();
		Container bracketCont = bracket.getContentPane();
		JButton browseButton = new JButton("Select File");		
		Container mainCont = main.getContentPane();
		JTextField loadFileTextField = new JTextField();
		JTextField saveFileTextField = new JTextField();
		final String[] TOURNAMENT_TYPES = {"IN_ORDER", "CLOSEST_ELO", "BEST_WITH_WORST", "RANDOM"}; 
		JComboBox<String> tournamentSelectionBox = new JComboBox<String>(TOURNAMENT_TYPES);
		JButton createTournamentButton = new JButton("Create Tournament");
		JButton pdfSaveButton = new JButton("Save as PDF");
		JButton exportForWeb = new JButton("Export for Web");
		JButton browseSaveButton = new JButton("Browse");
		
		bracketText.setPreferredSize(new Dimension(550, 500));
		loadFileTextField.setPreferredSize(new Dimension(300,20));
		saveFileTextField.setPreferredSize(new Dimension(250,20));
		main.setSize(new Dimension(570, 300));
		bracket.setSize(new Dimension(600, 600));
		
		mainCont.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 15));		
		mainCont.add(browseButton);
		mainCont.add(loadFileTextField);
		mainCont.add(tournamentSelectionBox);
		mainCont.add(createTournamentButton);
		
		bracketCont.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 15));;
		bracketCont.add(bracketText);
		bracketCont.add(browseSaveButton);
		bracketCont.add(saveFileTextField);
		bracketCont.add(pdfSaveButton);
		bracketCont.add(exportForWeb);
		
		browseButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e)
			{
				filePath = getFilePath(LOAD_FILE);
				if (filePath != null)
				{
					loadFileTextField.setText(filePath.toString());
				}				
			}
						
		});
		
		createTournamentButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e)
			{
				if (filePath == null)
				{
					JOptionPane.showMessageDialog(null, "Please Select a File to Load");
				}
				
				else
				{
					String selected = (String) tournamentSelectionBox.getSelectedItem();
					SchedulingMethods schedMethod = SchedulingMethods.valueOf(selected);					
					Tournament t = new Tournament(1, schedMethod);
					try 
					{
						t.importFromFile(filePath);
					} 
					
					catch (DataLoadingException e1) 
					{
						JOptionPane.showMessageDialog(null, "Not a Valid Tournament File");
					}
					
					bracketText.setText(t.generateBrackets());
					
					generatedTournament = t;
					filePath = null;
					
					bracket.setVisible(true);		
					
				}
			}
						
		});
		
		browseSaveButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e)
			{
				filePath = getFilePath(SAVE_FILE);
				if (filePath != null)
				{
					saveFileTextField.setText(filePath.toString());
				}				
			}
						
		});
		
		pdfSaveButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e)
			{
				if (filePath == null)
				{
					JOptionPane.showMessageDialog(null, "Please Select a Location to Save");
				}
				else
				{
					generatedTournament.generatePDF(saveFileTextField.getText()+"\\"+generatedTournament.getTournamentName()+".pdf");
				}				
			}
						
		});
		
		exportForWeb.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e)
			{
				if (filePath == null)
				{
					JOptionPane.showMessageDialog(null, "Please Select a Location to Save");
				}
				else
				{
					generatedTournament.exportJSON(saveFileTextField.getText()+"\\"+generatedTournament.getTournamentName()+".json");
				}				
			}
						
		});
		
		main.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		bracket.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		main.setVisible(true);
	}
	
	private static Path getFilePath(int fileMethod)
	{
		JFileChooser jfc = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
		if (fileMethod == SAVE_FILE)
		{
			jfc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		}
		
		int chosenFile = jfc.showOpenDialog(null);
		
		if (chosenFile == JFileChooser.APPROVE_OPTION) 
		{
            File selectedFile = jfc.getSelectedFile();
            return Path.of((selectedFile.getAbsolutePath()));
        }
	
		return null;	
	}

}
