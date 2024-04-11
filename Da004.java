import java.util.Scanner;
import java.awt.*;
import javax.swing.*;
import javax.swing.plaf.TreeUI;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class Da004 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        JFrame f = new JFrame();
        File file = new File("./금액.txt");
        f.setVisible(true);
        f.setSize(300, 300);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.getContentPane().setLayout(null);
        JButton btn1 = new JButton("500");
        btn1.setBounds(20, 10, 70, 20);
        f.getContentPane().add(btn1);
        JButton btn2 = new JButton("충전");
        btn2.setBounds(20, 50, 70, 20);
        f.getContentPane().add(btn2);

        btn2.addActionListener(event -> {
            int var1 = 0;

            System.out.println("숫자를 입력하세요");
            int var2 = sc.nextInt();
            var1 += var2;
            sc.nextLine();
            try (OutputStream output = new FileOutputStream("./금액.txt")) {
                String s = Integer.toString(var1);
                byte[] by = s.getBytes();
                output.write(by);
            } catch (IOException e) {
                e.printStackTrace();
            }

            System.out.println(var1);

        });

    }
}
