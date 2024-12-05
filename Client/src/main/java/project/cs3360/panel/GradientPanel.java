package project.cs3360.panel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GradientPanel extends JPanel {
    private final int WIDE;
    private final int HIGH;
    private final float HUE_MIN = 0;
    private final float HUE_MAX = 1;
    private final Timer timer;
    private float hue = HUE_MIN;
    private Color color1;
    private Color color2;
    private float delta = 0.01f;

    public GradientPanel(int wide, int high, Color color1, Color color2) {
        WIDE = wide;
        HIGH = high;
        this.color1 = color1;
        this.color2 = color2;
        ActionListener action = new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent evt) {
                hue += delta;
                if (hue > HUE_MAX) {
                    hue = HUE_MIN;
                }
                GradientPanel.this.color1 = Color.getHSBColor(hue, 1, 1);
                GradientPanel.this.color2 = Color.getHSBColor(hue + 16 * delta, 1, 1);
                repaint();
            }
        };
        timer = new Timer(10, action);
        timer.start();
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        GradientPaint p = new GradientPaint(
                0, 0, color1, getWidth(), 0, color2);
        g2d.setPaint(p);
        g2d.fillRect(0, 0, getWidth(), getHeight());
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(WIDE, HIGH);
    }
}
