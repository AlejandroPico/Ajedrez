package com.chess.utils;

import java.util.Random;

public class Utils {
	// Method to convert board coordinates to algebraic notation (e.g., (0, 0) -> "a8")
	public static String toAlgebraicNotation(int row, int col) {
		System.out.println("Converting coordinates (" + row + ", " + col + ") to algebraic notation");
		// Validate that the coordinates are within the board's boundaries (0-7)
		if (row < 0 || row >= 8 || col < 0 || col >= 8) {
			throw new IllegalArgumentException("Coordinates are out of bounds: (" + row + ", " + col + ")");
		}
		// Convert column number to file (letter from 'a' to 'h')
		char file = (char) ('a' + col);
		// Convert row number to rank (8 to 1)
		int rank = 8 - row;
		String notation = "" + file + rank;
		System.out.println("Converted to: " + notation);
		return notation;
	}

	// Method to convert algebraic notation to board coordinates (e.g., "a8" -> (0, 0))
	public static int[] fromAlgebraicNotation(String notation) {
		System.out.println("Converting algebraic notation " + notation + " to coordinates");
		// Validate that the notation is not null and has the correct format
		if (notation == null || notation.length() != 2 || notation.charAt(0) < 'a' || notation.charAt(0) > 'h'
				|| notation.charAt(1) < '1' || notation.charAt(1) > '8') {
			throw new IllegalArgumentException("Invalid algebraic notation: " + notation);
		}
		// Extract file (letter) and rank (number) from the notation
		char file = notation.charAt(0);
		int rank = Character.getNumericValue(notation.charAt(1));
		// Convert file to column index (0-7)
		int col = file - 'a';
		// Convert rank to row index (0-7)
		int row = 8 - rank;
		int[] coordinates = { row, col };
		System.out.println("Converted to coordinates: (" + row + ", " + col + ")");
		return coordinates;
	}

	// Method to generate a random number within a range
	public static int getRandomNumber(int min, int max) {
		System.out.println("Generating random number between " + min + " and " + max);
		// Validate that the minimum is not greater than the maximum
		if (min > max) {
			throw new IllegalArgumentException("Max must be greater than or equal to min");
		}
		// Generate a random number between min and max (inclusive)
		Random random = new Random();
		int result = random.nextInt((max - min) + 1) + min;
		System.out.println("Generated random number: " + result);
		return result;
	}

	// Method to check if coordinates are within the board boundaries
	public static boolean isWithinBounds(int row, int col) {
		System.out.println("Checking if coordinates (" + row + ", " + col + ") are within bounds");
		// Check if the row and column are within the valid range (0-7)
		boolean withinBounds = row >= 0 && row < 8 && col >= 0 && col < 8;
		System.out.println("Coordinates (" + row + ", " + col + ") are within bounds: " + withinBounds);
		return withinBounds;
	}
}
