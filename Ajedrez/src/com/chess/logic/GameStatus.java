package com.chess.logic;

public class GameStatus {
	// Enum to represent different possible statuses of the game
	public enum Status {
		ONGOING, CHECK, CHECKMATE, STALEMATE, DRAW
	}

	private Status currentStatus; // Variable to store the current status of the game

	// Constructor to initialize the game status to ONGOING
	public GameStatus() {
		currentStatus = Status.ONGOING;
		System.out.println("Game status initialized to ONGOING.");
	}

	// Method to get the current game status
	public Status getCurrentStatus() {
		System.out.println("Getting current game status: " + currentStatus);
		return currentStatus;
	}

	// Method to set the current game status
	public void setCurrentStatus(Status status) {
		System.out.println("Setting current game status to: " + status);
		this.currentStatus = status;
	}
}