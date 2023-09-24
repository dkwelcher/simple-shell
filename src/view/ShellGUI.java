package view;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class ShellGUI {
	
	private JFrame frame;
	private JTextArea textArea;
	private static final double SCREEN_WIDTH_FACTOR = 0.7;
	private static final double SCREEN_HEIGHT_FACTOR = 0.7;
	private static final Dimension MIN_DIMENSION = new Dimension(1000, 800);
	private static final Color BACKGROUND_COLOR = new Color(0, 0, 0);
	private static final Color FOREGROUND_COLOR = new Color(255, 255, 255);
	private static final Font TEXT_FONT = new Font("Verdana", Font.PLAIN, 18);
	
	public ShellGUI() {
		initializeGUI();
	}
	
	public static void main(String[] args) {
		new ShellGUI();
	}
	
	private void initializeGUI() {
		Dimension frameSize = getFrameDimension();
		
		frame = new JFrame("Simple Shell");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(frameSize);
		frame.setMinimumSize(MIN_DIMENSION);
		
		JPanel panel = getPanel();
		
		frame.add(panel);
		
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	}
	
	private Dimension getFrameDimension() {
		Dimension screenDimension = Toolkit.getDefaultToolkit().getScreenSize();
		int frameWidth = (int) (screenDimension.width * SCREEN_WIDTH_FACTOR);
		int frameHeight = (int) (screenDimension.height * SCREEN_HEIGHT_FACTOR);
		return new Dimension(frameWidth, frameHeight);
	}
	
	private JPanel getPanel() {
		JPanel panel = new JPanel(new BorderLayout());
		textArea = new JTextArea();
		textArea.setLineWrap(true);
		textArea.setWrapStyleWord(true);
		textArea.setEditable(true);
		textArea.setBackground(BACKGROUND_COLOR);
		textArea.setForeground(FOREGROUND_COLOR);
		textArea.setFont(TEXT_FONT);
		textArea.append(">");
		textArea.setCaretPosition(textArea.getDocument().getLength());
		
		textArea.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent event) {
				int caretPosition = textArea.getCaretPosition();
				int promptIndex = textArea.getText().lastIndexOf(">") + 1;
				
				if(caretPosition <= promptIndex) {
					textArea.setCaretPosition(textArea.getDocument().getLength());
				}
				
				if(event.getKeyCode() == KeyEvent.VK_ENTER) {
					event.consume();
					
					String command = textArea.getText().substring(promptIndex).trim();
					String output = executeCommand(command);
					
					textArea.append("\n>" + output + "\n>");
					
					textArea.setCaretPosition(textArea.getDocument().getLength());
				}
			}
		});
		
		JScrollPane scrollPane = new JScrollPane(textArea, ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		panel.add(scrollPane);
		return panel;
	}
	
	private String executeCommand(String command) {
		return command + " was executed";
	}
}
