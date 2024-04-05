import java.awt.*;
import javax.swing.*;
import javax.swing.plaf.TreeUI;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class da001 {
    public static void main(String[] args) throws InterruptedException {

        JFrame f = new JFrame();
        JFrame f1 = new JFrame();
        JLabel JL = new JLabel();
        JLabel JL1 = new JLabel();
        Random rand = new Random();
        ImageIcon icon = new ImageIcon(
                da001.class.getResource("./icon01.png"));
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f1.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setSize(300, 300);
        f1.setSize(300, 300);

        f.getContentPane().setLayout(null);
        JButton btn1 = new JButton("종료");
        btn1.setBounds(220, 20, 60, 30);

        Button btn2 = new Button("시작");
        btn2.setBounds(160, 20, 60, 30);

        f.getContentPane().add(btn1);
        f.getContentPane().add(btn2);

        f.setVisible(true);

        btn1.addActionListener(event -> {
            System.exit(0);
        });
        int var1 = rand.nextInt(200);
        f1.setLayout(null);
        f1.setResizable(false);
        JL.setIcon(icon);
        JL.setLocation(130, 200);
        JL.setSize(100, 100);
        JL1.setIcon(icon);
        JL1.setLocation(var1, -40);
        JL1.setSize(100, 100);

        f1.add(JL);
        f1.add(JL1);

        btn2.addActionListener(event -> {
            f.setVisible(false);
            f1.setVisible(true);
            for (int j = 0; j < 20; j++) {
                if (JL1.getY() < 200) {
                    try {
                        TimeUnit.MILLISECONDS.sleep(300);
                    } catch (InterruptedException e1) {
                        e1.printStackTrace();
                    }
                    JL1.setLocation(var1, JL1.getY() + 1);
                    System.out.println(JL1.getY());
                } else {
                    break;
                }
            }

        });

        f1.addKeyListener(new KeyAdapter() {

            public void keyPressed(KeyEvent e) {
                switch (e.getKeyCode()) {
                    case KeyEvent.VK_UP:
                        JL.setLocation(JL.getX(), JL.getY());
                        break;
                    case KeyEvent.VK_DOWN:
                        JL.setLocation(JL.getX(), JL.getY());
                        break;
                    case KeyEvent.VK_LEFT:
                        if (JL.getX() > 0) {
                            JL.setLocation(JL.getX() - 10, JL.getY());
                        }
                        JL.setLocation(JL.getX(), JL.getY());

                        break;
                    case KeyEvent.VK_RIGHT:
                        if (JL.getX() < 260) {
                            JL.setLocation(JL.getX() + 10, JL.getY());
                        }
                        JL.setLocation(JL.getX(), JL.getY());

                        break;

                }
            }

        });

    }
}
