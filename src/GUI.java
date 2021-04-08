import javax.swing.*;
import javax.swing.filechooser.*;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.nio.file.*;


public class GUI extends JFrame {
	
	static Path filePath;

	public static void main(String[] args) {
		
		try 
		{
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} 
		catch (Exception e) {}

		JFrame main = new JFrame("Tournament Tools");
		JFrame bracket = new JFrame("Bracket");
		JTextArea bracketText = new JTextArea();
		Container bracketCont = bracket.getContentPane();
		JButton browseButton = new JButton("Select File");		
		Container mainCont = main.getContentPane();
		JTextField debugTextField = new JTextField();
		final String[] TOURNAMENT_TYPES = {"In Order", "Closest ELO", "Best with Worst", "Random"}; 
		JComboBox<String> tournamentSelectionBox = new JComboBox<String>(TOURNAMENT_TYPES);
		JButton createTournamentButton = new JButton("Create Tournament");
		
		bracketText.setPreferredSize(new Dimension(500, 500));
		debugTextField.setPreferredSize(new Dimension(300,20));
		main.setSize(new Dimension(550, 300));
		bracket.setSize(new Dimension(550, 500));
		
		mainCont.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 15));
		
		mainCont.add(browseButton);
		
		mainCont.add(debugTextField);

		mainCont.add(tournamentSelectionBox);
		
		mainCont.add(createTournamentButton);
			
		browseButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e)
			{
				filePath = getFilePath();
				if (filePath != null)
				{
					debugTextField.setText(filePath.toString());
				}
				
				//TODO: Parse this loaded file
								
			}
						
		});
		
		bracketCont.add(bracketText);
		
		createTournamentButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e)
			{
				if (filePath == null)
				{
					JOptionPane.showMessageDialog(null, "Please Select a File to Load");
				}
				
				else
				{
					Tournament t = new Tournament(1);
					try 
					{
						t.importFromFile(filePath);
					} 
					
					catch (DataLoadingException e1) 
					{
						JOptionPane.showMessageDialog(null, "Not a Valid Tournament File");
					}
					
					bracketText.setText(t.generateBrackets());
					
					bracket.setVisible(true);		
					
				}
			}
						
		});
		
		
		main.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		main.setVisible(true);
	}
	
	private static Path getFilePath()
	{
		JFileChooser jfc = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
		
		int chosenFile = jfc.showOpenDialog(null);
		
		if (chosenFile == JFileChooser.APPROVE_OPTION) 
		{
            File selectedFile = jfc.getSelectedFile();
            return Path.of((selectedFile.getAbsolutePath()));
        }
	
		return null;	
	}

}
