package GameEngine;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;

//Define a custom JPanel with rendering logic
public class RenderPanel extends JPanel {
 @Override
 protected void paintComponent(Graphics g) {
     super.paintComponent(g); // Clear the panel
     Graphics2D g2d = (Graphics2D) g;

     // Example rendering logic
     g2d.setColor(Color.RED);
     g2d.fillRect(50, 50, 100, 100); // Draw a red rectangle

     g2d.setColor(Color.BLUE);
     g2d.drawString("Rendering to GameImagesAndCombatPanel", 60, 200); // Draw text
 }
}
