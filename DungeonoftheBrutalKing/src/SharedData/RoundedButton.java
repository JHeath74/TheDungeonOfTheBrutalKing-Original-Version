
package SharedData;

import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.BasicStroke;
import javax.swing.JButton;
import javax.swing.border.Border;

public class RoundedButton extends JButton {
    private int radius;

    public RoundedButton(String text, int radius) {
        super(text);
        this.radius = radius;
        setContentAreaFilled(false); // Disable default background painting
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Draw rounded background
        g2.setColor(getBackground());
        g2.fillRoundRect(0, 0, getWidth(), getHeight(), radius, radius);

        // Draw gold border
        g2.setColor(new Color(255, 215, 0)); // Gold color
        g2.setStroke(new BasicStroke(3)); // Border thickness
        g2.drawRoundRect(1, 1, getWidth() - 2, getHeight() - 2, radius, radius);

        // Draw button text
        g2.setColor(getForeground());
        FontMetrics fm = g2.getFontMetrics();
        int textWidth = fm.stringWidth(getText());
        int textHeight = fm.getHeight();
        g2.drawString(getText(), (getWidth() - textWidth) / 2, (getHeight() + textHeight / 2) / 2 - fm.getDescent());

        g2.dispose();
    }

    @Override
    public void setBorder(Border border) {
        // Prevent setting borders to maintain rounded corners
    }
}
