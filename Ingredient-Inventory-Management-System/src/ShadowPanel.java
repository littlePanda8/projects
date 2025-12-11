import javax.swing.*;
import java.awt.*;

public class ShadowPanel extends JPanel {

    private Color shadowColor = new Color(0, 0, 0, 30); // semi-transparent gray
    private int shadowHeight = 10; // height of the shadow

    public ShadowPanel() {
        super();
        setOpaque(true);
        setBackground(Color.WHITE);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // Draw bottom shadow
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        int w = getWidth();
        int h = getHeight();

        // gradient from semi-transparent gray to fully transparent
        GradientPaint gradient = new GradientPaint(
                0, h - shadowHeight, shadowColor,
                0, h, new Color(0, 0, 0, 0)
        );

        g2.setPaint(gradient);
        g2.fillRect(0, h - shadowHeight, w, shadowHeight);

        g2.dispose();
    }
}
