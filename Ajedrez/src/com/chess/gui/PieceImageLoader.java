package com.chess.gui;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Map;

import javax.swing.ImageIcon;

/**
 * Generates simple piece icons programmatically so the game does not rely on
 * external image resources. Each piece is represented by a circle with its
 * initial inside. White pieces have a black border and black pieces have a
 * white border.
 */
public class PieceImageLoader {
    private Map<String, ImageIcon> pieceImages;

    public PieceImageLoader() {
        pieceImages = new HashMap<>();
        createImages();
    }

    private void createImages() {
        String[] colors = { "white", "black" };
        String[] types = { "pawn", "rook", "knight", "bishop", "queen", "king" };

        for (String color : colors) {
            boolean isWhite = color.equals("white");
            for (String type : types) {
                pieceImages.put(color + "_" + type, generateIcon(isWhite, type));
            }
        }
    }

    private ImageIcon generateIcon(boolean isWhite, String type) {
        int size = 60;
        BufferedImage img = new BufferedImage(size, size, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = img.createGraphics();
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        Color fill = isWhite ? Color.WHITE : Color.BLACK;
        Color border = isWhite ? Color.BLACK : Color.WHITE;

        g.setColor(fill);
        g.fillOval(5, 5, size - 10, size - 10);
        g.setColor(border);
        g.setStroke(new BasicStroke(2));
        g.drawOval(5, 5, size - 10, size - 10);

        g.setFont(new Font("SansSerif", Font.BOLD, 24));
        String letter = pieceLetter(type);
        FontMetrics fm = g.getFontMetrics();
        int textX = (size - fm.stringWidth(letter)) / 2;
        int textY = (size - fm.getHeight()) / 2 + fm.getAscent();
        g.drawString(letter, textX, textY);

        g.dispose();
        return new ImageIcon(img);
    }

    private String pieceLetter(String type) {
        return switch (type) {
        case "pawn" -> "P";
        case "rook" -> "R";
        case "knight" -> "N";
        case "bishop" -> "B";
        case "queen" -> "Q";
        case "king" -> "K";
        default -> "";
        };
    }

    public ImageIcon getPieceImage(String key) {
        return pieceImages.get(key);
    }
}