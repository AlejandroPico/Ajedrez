package com.chess.player;

public class Player {
	private String name; // The name of the player
	private boolean isWhite; // Boolean indicating whether the player is playing white pieces

       // Constructor to initialize the player with a name and their color (white or black)
	public Player(String name, boolean isWhite) {
		if (name == null || name.isEmpty()) {
			throw new IllegalArgumentException("Player name cannot be null or empty.");
		}
                this.name = name;
                this.isWhite = isWhite;
	}

	// Method to get the player's name
	public String getName() {
                return name;
	}

	// Method to check if the player is playing with white pieces
	public boolean isWhite() {
                return isWhite;
	}

	// Method to set the player's name
	public void setName(String name) {
		if (name == null || name.isEmpty()) {
			throw new IllegalArgumentException("Player name cannot be null or empty.");
		}
                this.name = name;
	}
}