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
		JButton browseButton = new JButton("Select File");		
		Container mainCont = main.getContentPane();
		JTextField debugTextField = new JTextField();
		final String[] TOURNAMENT_TYPES = {"In Order", "Closest ELO", "Best with Worst", "Random"}; 
		JComboBox<String> tournamentSelectionBox = new JComboBox<String>(TOURNAMENT_TYPES);
		JButton createTournamentButton = new JButton("Create Tournament");
		
		debugTextField.setPreferredSize(new Dimension(300,20));
		main.setSize(new Dimension(550, 300));
		
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
		
		createTournamentButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e)
			{
				JOptionPane.showMessageDialog(null, "Load Tournament");
				
				//TODO: Load tournament based on parsed file
			}
						
		});
		
		
		main.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//main.pack();
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
