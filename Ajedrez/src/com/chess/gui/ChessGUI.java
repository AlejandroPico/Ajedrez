package com.chess.gui;

import java.awt.BorderLayout;
import java.awt.GridLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class ChessGUI {
	private JFrame frame; // Main frame for the chess game
	private JPanel boardPanel; // Panel to hold the chess board

	public ChessGUI() {
		System.out.println("Initializing the main frame for the chess game GUI");
		frame = new JFrame("Chess Game"); // Create the main frame with title "Chess Game"
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Set the default close operation to exit the application
		frame.setSize(600, 600); // Set the size of the frame
		frame.setLayout(new BorderLayout()); // Set the layout manager for the frame to BorderLayout

		System.out.println("Initializing the chess board panel");
		boardPanel = new JPanel(new GridLayout(8, 8)); // Create an 8x8 grid layout for the chess board
		frame.add(boardPanel, BorderLayout.CENTER); // Add the board panel to the center of the frame

		System.out.println("Setting the frame to be visible");
		frame.setVisible(true); // Make the frame visible
	}

	// Method to get the board panel
	public JPanel getBoardPanel() {
		System.out.println("Getting the board panel");
		return boardPanel; // Return the board panel to be used by other components
	}
}
