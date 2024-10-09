package com.chess.gui;

import java.awt.Color;
import java.awt.Dimension;
import javax.swing.JButton;
import javax.swing.JPanel;

import com.chess.utils.Utils;

import javax.swing.*;

public class BoardRenderer {
	private JPanel boardPanel; // The panel that represents the chess board
	private JButton[][] squares; // Array to hold references to each square on the board

	public BoardRenderer(JPanel boardPanel) {
		System.out.println("Initializing BoardRenderer with the provided board panel");
		this.boardPanel = boardPanel;
		this.squares = new JButton[8][8];
		renderBoard(); // Render the initial chess board
	}

	// Method to render the chess board
	public void renderBoard() {
		System.out.println("Rendering the chess board");
		boardPanel.removeAll(); // Clear the board before rendering

		// Loop through each square of the board and add buttons to the panel
		for (int row = 0; row < 8; row++) {
			for (int col = 0; col < 8; col++) {
				JButton square = new JButton();
				square.setPreferredSize(new Dimension(75, 75)); // Set the size of each square

				// Set the color of the square based on its position
				if ((row + col) % 2 == 0) {
					square.setBackground(Color.WHITE); // White for even sum of row and column
					System.out.println("Setting square at (" + row + ", " + col + ") to WHITE");
				} else {
					square.setBackground(Color.GRAY); // Gray for odd sum of row and column
					System.out.println("Setting square at (" + row + ", " + col + ") to GRAY");
				}

				squares[row][col] = square; // Store reference to the square
				boardPanel.add(square); // Add the square to the board panel
				System.out.println("Added square at (" + row + ", " + col + ") to the board panel");
			}
		}

		// Refresh the layout and redraw the board panel
		System.out.println("Revalidating and repainting the board panel");
		boardPanel.revalidate();
		boardPanel.repaint();
	}

	// Method to set a piece on a specific square
	public void setPiece(int row, int col, ImageIcon pieceImage) {
		System.out.println("Attempting to set piece at square (" + row + ", " + col + ")");
		if (Utils.isWithinBounds(row, col)) { // Check if the coordinates are within bounds
			System.out.println("Setting piece at square (" + row + ", " + col + ")");
			if (squares[row][col] != null) {
				squares[row][col].setIcon(pieceImage); // Set the image of the piece on the square
				System.out.println("Piece set at square (" + row + ", " + col + ")");
			} else {
				System.out.println("Square at (" + row + ", " + col + ") is null, cannot set piece");
			}
		} else {
			System.out.println("Attempted to set piece out of bounds at (" + row + ", " + col + ")");
		}
	}

	// Method to move a piece from one square to another
	public void movePiece(int fromRow, int fromCol, int toRow, int toCol) {
		System.out.println(
				"Attempting to move piece from (" + fromRow + ", " + fromCol + ") to (" + toRow + ", " + toCol + ")");
		if (Utils.isWithinBounds(fromRow, fromCol) && Utils.isWithinBounds(toRow, toCol)) { // Check if both coordinates
																							// are within bounds
			System.out
					.println("Moving piece from (" + fromRow + ", " + fromCol + ") to (" + toRow + ", " + toCol + ")");
			if (squares[fromRow][fromCol] != null) {
				ImageIcon pieceImage = (ImageIcon) squares[fromRow][fromCol].getIcon(); // Get the piece image from the
																						// original square
				System.out.println("Piece found at source: " + pieceImage);
				squares[fromRow][fromCol].setIcon(null); // Remove the piece from the original square
				System.out.println("Removed piece from square (" + fromRow + ", " + fromCol + ")");
				squares[toRow][toCol].setIcon(pieceImage); // Set the piece on the new square
				System.out.println("Set piece at square (" + toRow + ", " + toCol + ")");
			} else {
				System.out.println("No piece at the source square, cannot move piece");
			}
		} else {
			System.out.println("Attempted to move piece out of bounds");
		}
	}

	// Method to check if a square has a piece
	public boolean hasPiece(int row, int col) {
		System.out.println("Checking if square (" + row + ", " + col + ") has a piece");
		if (Utils.isWithinBounds(row, col)) { // Check if the coordinates are within bounds
			boolean hasPiece = squares[row][col] != null && squares[row][col].getIcon() != null; // Check if the square
																									// is not null and
																									// contains an icon
			System.out.println("Square (" + row + ", " + col + ") has piece: " + hasPiece);
			return hasPiece;
		} else {
			System.out.println("Attempted to check piece out of bounds at (" + row + ", " + col + ")");
			return false;
		}
	}
}
