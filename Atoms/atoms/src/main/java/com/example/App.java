package com.example;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Hello world!
 *
 */
public class App {
    public static void main(String[] args) {
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;
        try {
            // 오라클 드라이버를 사용하겠다는 의미
            Class.forName("oracle.jdbc.driver.OracleDriver");
            // 자신의 주소값에 scott 라는 아이디와 tiger 의 비밀번호로 접속함
            conn = DriverManager.getConnection(
                    "jdbc:oracle:thin:@localhost:1521:xe", "atom", "1234");

            stmt = conn.createStatement();
            // EMP 테이블 전체조회하기위한 쿼리문
            String query = "SELECT * FROM PR";

            // select문이기때문에 executeQuery으로사용
            rs = stmt.executeQuery(query);

            while (rs.next()) {
                String ID = rs.getString("ID");
                String pw = rs.getString("PW");
                // 출력
                System.out.println(ID + ", " + pw);
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            // 항상 사용후 무조건 닫아주자!
            try {
                rs.close();
                stmt.close();
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
