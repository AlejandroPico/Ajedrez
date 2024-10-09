package com.chess.moves;

import java.util.ArrayList;
import java.util.List;

public class MoveHistory {

	private List<String> moveList; // List to store the moves in algebraic notation

	// Constructor to initialize the move history list
	public MoveHistory() {
		System.out.println("Initializing MoveHistory");
		moveList = new ArrayList<>(); // Initialize the list to store moves
	}

	// Method to add a move to the history
	public void addMove(String move) {
		// Validate that the move is not null or empty
		if (move == null || move.isEmpty()) {
			System.out.println("Invalid move. Cannot add null or empty move to history.");
			return;
		}
		// Add the move to the list and print debug information
		System.out.println("Adding move to history: " + move);
		moveList.add(move);
		System.out.println("Current move list: " + moveList);
	}

	// Method to get the last move made
	public String getLastMove() {
		// Check if the move list is empty
		if (moveList.isEmpty()) {
			System.out.println("No moves in history");
			return null; // Return null if no moves are available
		}
		// Get the last move from the list
		String lastMove = moveList.get(moveList.size() - 1);
		System.out.println("Getting last move: " + lastMove);
		return lastMove;
	}

	// Method to clear the move history
	public void clearHistory() {
		// Clear the move list and print debug information
		System.out.println("Clearing move history");
		moveList.clear();
		System.out.println("Move history cleared. Current move list: " + moveList);
	}

	// Method to get all moves in history
	public List<String> getMoveList() {
		// Return a copy of the move list to avoid external modification
		System.out.println("Getting full move history");
		System.out.println("Full move list: " + moveList);
		return new ArrayList<>(moveList);
	}

	// Method to print the move history
	public void printHistory() {
		// Print each move in the list with its index
		System.out.println("Printing move history:");
		for (int i = 0; i < moveList.size(); i++) {
			System.out.println((i + 1) + ". " + moveList.get(i));
		}
	}
}