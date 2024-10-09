package com.chess.gui;

import java.util.HashMap;
import java.util.Map;

import javax.swing.ImageIcon;

public class PieceImageLoader {
	private Map<String, ImageIcon> pieceImages; // Map to store images of each piece

	public PieceImageLoader() {
		System.out.println("Initializing PieceImageLoader");
		pieceImages = new HashMap<>();
		loadImages(); // Load the piece images during initialization
	}

	private void loadImages() {
		System.out.println("Loading piece images");
		// Load images for each piece and store them in the map
		try {
			pieceImages.put("white_pawn", new ImageIcon(getClass().getResource("/images/white_pawn.png")));
			System.out.println("Loaded image for white_pawn");
			pieceImages.put("black_pawn", new ImageIcon(getClass().getResource("/images/black_pawn.png")));
			System.out.println("Loaded image for black_pawn");
			pieceImages.put("white_rook", new ImageIcon(getClass().getResource("/images/white_rook.png")));
			System.out.println("Loaded image for white_rook");
			pieceImages.put("black_rook", new ImageIcon(getClass().getResource("/images/black_rook.png")));
			System.out.println("Loaded image for black_rook");
			// Load other pieces similarly...
		} catch (NullPointerException e) {
			System.err.println("Error loading piece images: Resource not found - " + e.getMessage()); // Handle null
																										// pointer
																										// exceptions
																										// for missing
																										// resources
		} catch (Exception e) {
			System.err.println("Error loading piece images: " + e.getMessage()); // Log any other errors during image
																					// loading
		}
	}

	public ImageIcon getPieceImage(String pieceKey) {
		System.out.println("Getting image for piece: " + pieceKey);
		ImageIcon image = pieceImages.get(pieceKey);
		if (image == null) {
			System.err.println("Image for piece " + pieceKey + " not found."); // Log if the image is not found
		}
		return image; // Return the image for the given piece key
	}
}