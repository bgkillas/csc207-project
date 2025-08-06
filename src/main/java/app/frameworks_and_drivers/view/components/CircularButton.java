package app.frameworks_and_drivers.view.components;

import java.awt.*;
import javax.swing.*;

/**
 * A custom circular button with centered text.
 * This button overrides the default Swing `JButton`.
 */
public class CircularButton extends JButton {

    /**
     * Constructs a new {CircularButton} with the specified label text.
     *
     * @param label the text to display on the button
     */
    public CircularButton(String label) {
        super(label);
        setContentAreaFilled(false);
        setFocusPainted(false);
    }

    /**
     * Paints the circular button with centered text.
     *
     * @param g the {Graphics} context in which to paint
     */
    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();

        // Enable anti-aliasing for smooth edges
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Fill circle with background color
        g2.setColor(getBackground());
        g2.fillOval(0, 0, getWidth(), getHeight());

        // Draw the button text
        FontMetrics fm = g2.getFontMetrics();
        int textWidth = fm.stringWidth(getText());
        int textHeight = fm.getAscent();

        int x = (getWidth() - textWidth) / 2;
        int y = (getHeight() + textHeight) / 2 - 3;

        g2.setColor(getForeground());
        g2.drawString(getText(), x, y);

        g2.dispose();
    }

    /**
     * Overrides the default hitbox detection to match the circular shape.
     *
     * @param x the x-coordinate of the point
     * @param y the y-coordinate of the point
     * @return true if the point is inside the circular area, false otherwise
     */
    @Override
    public boolean contains(int x, int y) {
        int radius = getWidth() / 2;
        int centerX = radius;
        int centerY = getHeight() / 2;
        return (Math.pow(x - centerX, 2) + Math.pow(y - centerY, 2)) <= Math.pow(radius, 2);
    }
}
