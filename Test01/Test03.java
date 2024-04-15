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
        Scanner sc = new Scanner(System.in);
        JLabel jl = new JLabel();
        ts.connect();

        JButton btn1 = new JButton("입력");
        JButton btn2 = new JButton("닫기");
        JTextField msg = new JTextField(10); // 문자열을 입력받는 UI, 숫자로 크기 설정
        JPanel panel = new JPanel();
        jf.setLayout(null);
        jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jf.setSize(500, 500);
        jf.setLayout(new FlowLayout());
        jl.setText("잔액조회");

        panel.add(jl);
        panel.add(msg);
        panel.add(btn1);
        panel.add(btn2);
        jf.add(panel);

        btn1 = new JButton("확인");
        JLabel j2 = new JLabel("아이디");
        JTextField msg1 = new JTextField(10);
        panel.add(j2);
        panel.add(msg1);
        panel.add(btn1);
        jf.add(panel);

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

        jf.setVisible(true);

    }
}
