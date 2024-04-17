package Test01;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

import javax.swing.*;
import javax.swing.plaf.basic.BasicOptionPaneUI.ButtonActionListener;

public class Test03 {
    public static void main(String[] args) {
        Test02 ts = new Test02();
        JFrame jf = new JFrame();
        JFrame jf1 = new JFrame();
        Scanner sc = new Scanner(System.in);
        JLabel jl = new JLabel();
        boolean c = false;
        JButton btn1 = new JButton("입력");
        JButton btn2 = new JButton("닫기");
        JTextField msg = new JTextField(10); // 문자열을 입력받는 UI, 숫자로 크기 설정
        JPanel panel = new JPanel();
        jf.setLayout(null);
        jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jf.setSize(500, 500);

        jf1.setLayout(null);
        jf1.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jf1.setSize(500, 500);
        jf1.setLayout(new FlowLayout());

        jl.setText("검색");
        panel.add(jl);
        panel.add(msg);
        panel.add(btn1);
        panel.add(btn2);
        jf1.add(panel);

        JButton btn3 = new JButton("로그인");
        JLabel j2 = new JLabel("ID");
        JLabel j3 = new JLabel("PW");
        JTextField msg1 = new JTextField(10);
        JTextField msg2 = new JTextField(10);
        JPanel panel1 = new JPanel();
        JPanel panel2 = new JPanel();
        panel1.add(j2);
        panel1.add(msg1);
        panel2.add(j3);
        panel2.add(msg2);
        panel2.add(btn3);
        panel1.setBounds(67, 50, 300, 30);
        panel2.setBounds(100, 90, 300, 50);
        jf.add(panel1);
        jf.add(panel2);

        btn1.addActionListener(event -> {
            String Msg = msg.getText();
            try {
                ts.views(Msg);

            } catch (SQLException e) {

                e.printStackTrace();
            }
        });
        btn2.addActionListener(event -> {
            System.out.println("닫기");
            ts.databaseClose();
        });
        btn3.addActionListener(event -> {
            String Msg1 = msg1.getText();
            String Msg2 = msg2.getText();

            try {
                boolean d = ts.ck(Msg1, Msg2, c);
                if (d == true) {
                    jf.dispose();
                    jf1.setVisible(true);
                }

            } catch (SQLException e) {
                e.printStackTrace();
            }
        });

        jf.setVisible(true);

    }
}
