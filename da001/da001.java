import java.awt.*;
import javax.swing.*;
import javax.swing.plaf.TreeUI;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class Da001 {
    public static void main(String[] args) throws InterruptedException {
        Thread tr = new Da003();
        JFrame f = new JFrame();
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setSize(300, 300);

        f.getContentPane().setLayout(null);
        JButton btn1 = new JButton("종료");
        btn1.setBounds(220, 20, 60, 30);

        Button btn2 = new Button("시작");
        btn2.setBounds(160, 20, 60, 30);

        f.getContentPane().add(btn1);
        f.getContentPane().add(btn2);

        f.setVisible(true);

        btn2.addActionListener(event -> {
            f.setVisible(false);
            tr.start();

        });

    }
}

class Da002 extends Thread {

    JLabel JL1 = new JLabel();
    JLabel JL = new JLabel();
    JFrame f1 = new JFrame();
    Random rand = new Random();
    ImageIcon icon = new ImageIcon(
            Da002.class.getResource("./icon01.png"));

    public void run() {
        int var1 = rand.nextInt(200);
        f1.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f1.setSize(300, 300);
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
        f1.setVisible(true);
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
        while (true) {

            if (JL1.getY() >= JL.getY() - 10 && JL1.getX() + 10 >= JL.getX() && JL1.getX() - 10 <= JL.getX()) {
                System.out.println("게임종료");

            } else if (JL1.getY() >= 200) {
                JL1.setLocation(rand.nextInt(200), -40);
            } else if (JL1.getY() < 200) {
                try {
                    TimeUnit.MILLISECONDS.sleep(100);
                } catch (InterruptedException e1) {
                    e1.printStackTrace();
                }
                JL1.setLocation(JL1.getX(), JL1.getY() + 10);

            } else {
                break;
            }

        }

    }
}

class Da003 extends Thread {
    JLabel JL1 = new JLabel();
    JLabel JL = new JLabel();
    JFrame f1 = new JFrame();
    Random rand = new Random();
    ImageIcon icon = new ImageIcon(
            Da002.class.getResource("./icon01.png"));

    JLabel[] var2;

    public void run() {
        var2 = new JLabel[10];
        for (int i = 0; i < var2.length; i++) {

            var2[i] = new JLabel();
            var2[i].setIcon(icon);
            var2[i].setLocation(rand.nextInt(260), -40);
            var2[i].setSize(100, 100);
            f1.add(var2[i]);

        }

        f1.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f1.setSize(300, 300);
        f1.setLayout(null);
        f1.setResizable(false);
        JL.setIcon(icon);
        JL.setLocation(130, 200);
        JL.setSize(100, 100);

        f1.add(JL);
        f1.setVisible(true);
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
        boolean ttt = true;
        while (ttt) {
            for (int i = 0; i < var2.length; i++) {

                if (var2[i].getY() >= JL.getY() - 10 && var2[i].getX() + 10 >= JL.getX()
                        && var2[i].getX() - 10 <= JL.getX()) {
                    System.out.println("게임종료");
                    ttt = false;

                } else if (var2[i].getY() >= 200) {
                    var2[i].setLocation(rand.nextInt(200), -40);
                } else if (var2[i].getY() < 200) {
                    try {
                        TimeUnit.MILLISECONDS.sleep(10);
                    } catch (InterruptedException e1) {
                        e1.printStackTrace();
                    }
                    var2[i].setLocation(var2[i].getX(), var2[i].getY() + rand.nextInt(30));

                }

            }

        }

    }
}