package Test01;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class Test02 {
    Scanner sc = new Scanner(System.in);
    Statement stmt = null;
    PreparedStatement pstmt = null;
    Connection con = null;
    ResultSet rs = null;

    public void connect() {

        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
            System.out.println("JDBC Driver 로드 성공!!");
            String url = "jdbc:oracle:thin:@localhost:1521:xe";
            String user = "ATOM";
            String pw = "1234";
            con = DriverManager.getConnection(url, user, pw);
            stmt = con.createStatement();

            System.out.println("연결 성공");
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    public void model(String n, int p) throws SQLException {
        String name = n;
        int price = p;
        String updateStr = "INSERT INTO DATA VALUES (" + "'" + name + "'" + "," + "'" + price + "'" + ")";
        pstmt = con.prepareStatement(updateStr);
        pstmt.executeUpdate();
    }

    public void per(String i, String p, String n) {
        String sql = "SELECT * FROM PR";
        try {
            stmt = con.createStatement();
            rs = stmt.executeQuery(sql);
        } catch (SQLException e) {

            e.printStackTrace();
        }
        String id = i;
        String pw = p;
        String name = n;
        try {
            String updateStr = "INSERT INTO PR VALUES ('" + id + "','" + pw + "','" + name + "'" + ",''" + ")";
            pstmt = con.prepareStatement(updateStr);
            pstmt.executeUpdate();
            System.out.println("회원가입 성공!");
        } catch (SQLException e) {
            System.out.println("아이디가 존재합니다. 다른 아이디를 입력해주세요");
        }

    }

    public void databaseClose() {
        if (stmt != null) {
            try {
                stmt.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if (pstmt != null) {
            try {
                pstmt.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if (rs != null) {
            try {
                rs.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if (con != null) {
            try {
                con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

    }

    public void road(String a, String b) throws SQLException {
        String ID;
        String PW;
        String sql = "SELECT * FROM PR WHERE" + " ID =" + "'" + a + "'";
        try {
            stmt = con.createStatement();
            rs = stmt.executeQuery(sql);
        } catch (SQLException e) {

            e.printStackTrace();
        }

        if (rs.next()) {
            ID = rs.getString("ID");
            PW = rs.getString("PW");
            if (a.equals(ID) && b.equals(PW)) {
                System.out.println("로그인성공");
                while (true) {
                    System.out.println("1. 금액충전 , 2. 장바구니 , 3. 상품구입 , 4. 로그아웃 ");
                    String var1 = sc.nextLine();
                    if (var1.equals("1")) {
                        try {
                            System.out.println("충전할 금액을 입력하세요");
                            int var2 = sc.nextInt();
                            sc.nextLine();
                            money(var2, ID);
                        } catch (SQLException e) {

                            e.printStackTrace();
                        }
                    } else if (var1.equals("2")) {

                    } else if (var1.equals("3")) {

                    } else if (var1.equals("4")) {
                        break;
                    }
                }

            } else {
                System.out.println("로그인실패");

            }

        }
    }

    public void money(int a, String b) throws SQLException {
        int money = a;
        String ID = b;
        String updateStr = "UPDATE PR SET MONEY =" + money + "+ NVL((SELECT MONEY FROM PR WHERE ID = '" + ID
                + "'),0) WHERE ID = '" + ID + "'";
        pstmt = con.prepareStatement(updateStr);
        pstmt.executeUpdate();
    }

    public void money1(int a, String b) throws SQLException {
        int money = a;
        String ID = b;
        String updateStr = "UPDATE PR SET MONEY =" + money + "- NVL((SELECT MONEY FROM PR WHERE ID = '" + ID
                + "'),0) WHERE ID = '" + ID + "'";
        pstmt = con.prepareStatement(updateStr);
        pstmt.executeUpdate();
    }
}
