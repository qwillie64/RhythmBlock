package UI;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JPanel;


public class Pause extends JPanel{
    private JButton back_btn;

    public Pause(int width, int height) {
        setBackground(new Color(0, 0, 0, 0));
        setOpaque(false);
        setSize(width, height);
        setLayout(null);

        back_btn = new JButton("Go Back");
        back_btn.setLocation(50, 50);
        back_btn.setSize(200, 150);
        back_btn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("go back");
            }
        });
        add(back_btn);
    }


    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        System.out.println("pause page repaint");
    }
}
