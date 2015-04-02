import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;


public class UserInterface extends JFrame {  
		
		private JTextArea messageInput_JTextArea;
		private JTextField fileEncodeLocation_JTextField;
		
		private JTextField fileDecodeLocation_JTextField;
		private JTextField keyImageDirectory_JTextField;
		
		private JButton selectDirectory_JButton;
		private JButton selectKeyImageDirectory_JButton;
		private JButton submit_JButton;
		
		private JFileChooser fileChooser;
    
		public UserInterface() {  
    
			//Create tabbed panel and add tabs for "Encode" and "Decode"
			JTabbedPane tabbedPane = new JTabbedPane();
			
			tabbedPane.addTab("Encode", null, buildEncodeFrame(),
			                "Encode messages here");
			tabbedPane.addTab("Decode", null, buildDecodeFrame(),
	                  		"Decode messages here");
			
			add(tabbedPane);
		}
 
		private JPanel buildEncodeFrame() {

			//Initialize "Encode" panel
			JPanel encode_JPanel = new JPanel(new BorderLayout());
			
			//Create panel for directory selection options
			JPanel directorySelect_JPanel = new JPanel(new GridLayout(1, 2));
			
			//Create "selectDirectory" button and add it to the directory panel after attaching an action listener to it
			selectDirectory_JButton = new JButton("Select File Directory");
			selectDirectory_JButton.addActionListener(new ActionListener() {
                
                @Override
                public void actionPerformed(ActionEvent arg0){
                	fileChooser = new JFileChooser();
                	fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
                	if (fileChooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
            			String filePath = fileChooser.getSelectedFile().getAbsolutePath();
            			fileEncodeLocation_JTextField.setText(filePath);
                	}
                }
            });
			directorySelect_JPanel.add(selectDirectory_JButton);

			//Add text field for selected directory string
			fileEncodeLocation_JTextField = new JTextField();
			fileEncodeLocation_JTextField.setEditable(false);
			fileEncodeLocation_JTextField.setBackground(Color.WHITE);
			directorySelect_JPanel.add(fileEncodeLocation_JTextField);
			
			
			//Create and add "encode" button
			submit_JButton = new JButton("Encode");
			submit_JButton.addActionListener(new ActionListener() {
                
                @Override
                public void actionPerformed(ActionEvent arg0){
                
                	String message = messageInput_JTextArea.getText();
                	String fileDirectory = fileEncodeLocation_JTextField.getText();
                	
                	if(message.length() > 0 && fileDirectory.length() > 0) {
                		String result = ProgramController.encodeMessage(message, fileDirectory);
                		JOptionPane.showMessageDialog(null, result);
                	}
                	else
                		JOptionPane.showMessageDialog(null, "Please check that a message is entered and/or that a file directory has been selected");
                }
            });
			encode_JPanel.add(submit_JButton, BorderLayout.SOUTH);
			
			//Create panel with titled border
			JPanel messageInput_JPanel = new JPanel();
			messageInput_JPanel.setBorder(new TitledBorder("Enter message to encode below"));
			
			//Add message input field to panel
			messageInput_JTextArea = new JTextArea();
			messageInput_JTextArea.setWrapStyleWord(true);
			messageInput_JTextArea.setLineWrap(true);
			
			JScrollPane jScrollPane1 = new JScrollPane(messageInput_JTextArea);
			jScrollPane1.setVerticalScrollBarPolicy(
	                JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
			jScrollPane1.setPreferredSize(new Dimension(500, 350));
			messageInput_JPanel.add(jScrollPane1);
			
			//Add panels to the main encode panel
			encode_JPanel.add(directorySelect_JPanel, BorderLayout.NORTH);
			encode_JPanel.add(messageInput_JPanel, BorderLayout.CENTER);
			
			return encode_JPanel;
		}   
  
		private JPanel buildDecodeFrame() {  
 
			//Initialize "Decode" panel
			JPanel decode_JPanel = new JPanel();
			
			//Create panel for directory selection options
			JPanel directorySelect_JPanel = new JPanel(new GridLayout(2, 2));
			
			//Create "selectImageFileDirectory" button and add it to the directory panel after attaching an action listener to it
			selectDirectory_JButton = new JButton("Select Image File");
			selectDirectory_JButton.addActionListener(new ActionListener() {
                
                @Override
                public void actionPerformed(ActionEvent arg0){
                	fileChooser = new JFileChooser();
                	if (fileChooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
            			String filePath = fileChooser.getSelectedFile().getAbsolutePath();
            			fileDecodeLocation_JTextField.setText(filePath);
                	}
                }
            });
			directorySelect_JPanel.add(selectDirectory_JButton);

			//Add text field for selected image file string
			fileDecodeLocation_JTextField = new JTextField();
			fileDecodeLocation_JTextField.setEditable(false);
			fileDecodeLocation_JTextField.setBackground(Color.WHITE);
			directorySelect_JPanel.add(fileDecodeLocation_JTextField);
			
			
			//Create "selectKeyImageFileDirectory" button and add it to the directory panel after attaching an action listener to it
			selectKeyImageDirectory_JButton = new JButton("Select Key Image File");
			selectKeyImageDirectory_JButton.addActionListener(new ActionListener() {
                
                @Override
                public void actionPerformed(ActionEvent arg0){
                	fileChooser = new JFileChooser();
                	if (fileChooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
            			String filePath = fileChooser.getSelectedFile().getAbsolutePath();
            			keyImageDirectory_JTextField.setText(filePath);
                	}
                }
            });
			directorySelect_JPanel.add(selectKeyImageDirectory_JButton);

			//Add text field for selected image file string
			keyImageDirectory_JTextField = new JTextField();
			keyImageDirectory_JTextField.setEditable(false);
			keyImageDirectory_JTextField.setBackground(Color.WHITE);
			directorySelect_JPanel.add(keyImageDirectory_JTextField);
			
			
			//Create and add "decode" button
			submit_JButton = new JButton("Decode");
			submit_JButton.addActionListener(new ActionListener() {
                
                @Override
                public void actionPerformed(ActionEvent arg0){
                
                	String imageFileDirectory = fileDecodeLocation_JTextField.getText();
                	String keyImageFileDirectory = keyImageDirectory_JTextField.getText();
                	
                	if(imageFileDirectory.length() > 0 && keyImageFileDirectory.length() > 0) {
                		String result = ProgramController.decodeMessage(imageFileDirectory, keyImageFileDirectory);
                		JOptionPane.showMessageDialog(null, result);
                	}
                	else
                		JOptionPane.showMessageDialog(null, "Please check that the image file is selected and/or that a key image file has been selected");
                }
            });
			
			
			//Add directory control panel and submit button to the decode panel
			decode_JPanel.add(directorySelect_JPanel);
			decode_JPanel.add(submit_JButton);
			
			return decode_JPanel;
		}
}
  