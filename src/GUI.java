import javax.swing.*;
import javax.swing.filechooser.*;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
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
		
		debugTextField.setPreferredSize(new Dimension(300,20));
		main.setSize(new Dimension(700,300));
		
		mainCont.setLayout(new GridBagLayout());
		GridBagConstraints constraints = new GridBagConstraints();
		
		constraints.gridx = 1;
		constraints.gridy = 1;
		mainCont.add(browseButton, constraints);
		
		constraints.gridx = 1;
		constraints.gridy = 0;
		mainCont.add(debugTextField, constraints);
		
		browseButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e)
			{
				filePath = getFilePath();
				debugTextField.setText(filePath.toString());
				//TODO: Parse this loaded file
								
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
