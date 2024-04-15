package Test01;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Test02 {
    Scanner sc = new Scanner(System.in);
    Statement stmt = null;
    Statement stmt1 = null;
    PreparedStatement pstmt = null;
    Connection con = null;
    ResultSet rs = null;
    ResultSet rs1 = null;

    public void connect() { // 데이터베이스 연결

        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
            System.out.println("JDBC Driver 로드 성공!!");
            String url = "jdbc:oracle:thin:@localhost:1521:xe";
            String user = "ATOM";
            String pw = "1234";
            con = DriverManager.getConnection(url, user, pw);
            stmt = con.createStatement();
            stmt1 = con.createStatement();

            System.out.println("연결 성공");
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    public void per(String i, String p, String n) { // 회원가입시 아이디 중복여부 체크
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

    public void databaseClose() { // 데이터 베이스 닫는 메소드
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
        if (stmt1 != null) {
            try {
                stmt1.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

    }

    public void road(String a, String b) throws SQLException { // 로그인시 등장하는 메인화면 관리자모드 / 사용자모드로 나눔 관리자모드 ID: sim PW:
                                                               // 1234
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
            if (a.equals("sim") && b.equals("1234")) {
                System.out.println("관리자로 접속합니다.");
                ad(ID);
            } else if (a.equals(ID) && b.equals(PW)) {
                System.out.println("로그인성공");
                while (true) {
                    System.out.println("1. 금액충전 , 2. 장바구니 , 3. 상품구입 , 4. 잔액확인 , 5. 로그아웃 ");
                    String var1 = sc.nextLine();
                    if (var1.equals("1")) {

                        try {
                            System.out.println("충전할 금액을 입력하세요");
                            int var2 = sc.nextInt();
                            sc.nextLine();
                            money(var2, ID);
                            System.out.println(var2 + "원이 충전되었습니다.");
                        } catch (SQLException e) {

                            e.printStackTrace();
                        }
                    } else if (var1.equals("2")) {
                        System.out.println("1. 장바구니에 상품 넣기   2. 장바구니 목록   3. 장바구니 결제     4. 장바구니 비우기");
                        String bt1 = sc.nextLine();
                        if (bt1.equals("1")) {
                            System.out.println("상품을 골라주세요");
                            lst1();
                            System.out.println("장바구니에 넣을 상품의 번호를 입력해주세요.");
                            try {
                                int var2 = sc.nextInt();
                                sc.nextLine();
                                System.out.println("장바구니에 넣을 수량을 입력해주세요.");
                                int var3 = sc.nextInt();
                                sc.nextLine();
                                bk(ID, var3, var2);
                            } catch (Exception e) {
                                sc.nextLine();
                                System.out.println("입력값을 확인해 주세요.");
                            }
                        } else if (bt1.equals("2")) {
                            bklst(ID);
                        } else if (bt1.equals("3")) {
                            sell1(ID);
                        } else if (bt1.equals("4")) {
                            bklst(ID);
                            System.out.println("삭제하실 목록의 이름을 선택해주세요");
                            String var3 = sc.nextLine();
                            del1(ID, var3);
                        }
                    } else if (var1.equals("3")) {
                        System.out.println("상품을 구매합니다.");
                        lst1();
                        System.out.println("구매할 상품의 번호를 입력하세요.");
                        int var2 = sc.nextInt();
                        sc.nextLine();
                        System.out.println("구매할 상품의 수량을 입력하세요.");
                        int var3 = sc.nextInt();
                        sc.nextLine();
                        sell(var2, ID, var3);

                    } else if (var1.equals("4")) {
                        System.out.println("잔액조회");
                        views(ID);
                    } else if (var1.equals("5")) {
                        break;
                    }
                }

            } else {
                System.out.println("로그인실패");

            }

        } else {
            System.out.println("로그인실패");

        }
    }

    public void money(int a, String b) throws SQLException { // 손님용 금액 충전 메소드 데이터 베이스 값 변경
        int money = a;
        String ID = b;
        String updateStr = "UPDATE PR SET MONEY =" + money + "+ NVL((SELECT MONEY FROM PR WHERE ID = '" + ID
                + "'),0) WHERE ID = '" + ID + "'";
        pstmt = con.prepareStatement(updateStr);
        pstmt.executeUpdate();
    }

    public void money1(int a, String b, int d) throws SQLException { // 손님들이 물건 구입시 사용하는 메소드 데이터베이스에 값을 변경시킨다
        int money = a;
        String ID = b;
        int c = 0;
        String sql = "SELECT MONEY FROM PR WHERE ID = '" + ID + "'";
        rs = stmt.executeQuery(sql);
        while (rs.next()) {
            c = rs.getInt("MONEY");
        }
        if (c >= a * d) {
            System.out.println(a + "//" + c);
            String updateStr = "UPDATE PR SET MONEY = NVL((SELECT MONEY FROM PR WHERE ID = '" + ID
                    + "'),0) -" + money * d + " WHERE ID = '" + ID + "'";
            pstmt = con.prepareStatement(updateStr);
            pstmt.executeUpdate();
            System.out.println("구매가 정상 처리 되었습니다.");
            int dd = c - money * d;
            System.out.println("잔액은 " + dd + "원 입니다.");
        } else {
            System.out.println("잔액이 부족합니다.");
        }
    }

    public void ad(String a) throws SQLException { // 관리자모드 메인화면
        String sql = "SELECT * FROM PR";
        while (true) {
            System.out.println("1. 회원목록  2. 상품등록  3. 상품삭제  4. 상품목록  5. 로그아웃  99. 금액충전");
            stmt = con.createStatement();
            rs = stmt.executeQuery(sql);
            String var1 = sc.nextLine();
            if (var1.equals("1")) {

                System.out.println("회원 정보 출력");
                while (rs.next()) {
                    String ID = rs.getString("ID");
                    String PW = rs.getString("PW");
                    String NAME = rs.getString("NAME");
                    int MONEY = rs.getInt("MONEY");

                    System.out.println("ID:" + ID + "  PW:" + PW + "  이름:" + NAME + "  잔액:" + MONEY);
                }
            } else if (var1.equals("2")) {

                System.out.println("상품을 등록합니다.");
                System.out.println("상품 번호를 입력해주세요");

                try {
                    int var2 = sc.nextInt();
                    sc.nextLine();
                    System.out.println("상품 이름을 입력해주세요");
                    String var3 = sc.nextLine();
                    System.out.println("상품 가격을 입력해주세요");
                    int var4 = sc.nextInt();
                    sc.nextLine();
                    md(var2, var3, var4);
                } catch (Exception e) {

                    System.out.println("입력값을 확인하세요");
                    sc.nextLine();
                }

            } else if (var1.equals("3")) {
                System.out.println("등록된 상품을 삭제합니다.");
                System.out.println("삭제할 상품의 번호, 이름을 입력하세요");
                System.out.println("삭제할 상품의 번호");
                try {
                    int var2 = sc.nextInt();
                    sc.nextLine();
                    System.out.println("삭제할 상품의 이름");
                    String var3 = sc.nextLine();
                    del(var2, var3);
                } catch (InputMismatchException e) {
                    sc.nextLine();
                    System.out.println("입력값을 확인해주세요");
                }

            } else if (var1.equals("4")) {
                lst1();
            } else if (var1.equals("99")) {
                try {
                    System.out.println("충전할 금액을 입력하세요");
                    int var2 = sc.nextInt();
                    sc.nextLine();
                    money(var2, a);
                    System.out.println(var2 + "원이 충전되었습니다.");
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            } else if (var1.equals("5")) {
                break;
            }
        }
    }

    public void md(int a, String b, int c) { // 관리자모드에서 상품등록
        try {
            String updateStr = "INSERT INTO DATA VALUES ('" + a + "','" + b + "','" + c + "')";
            pstmt = con.prepareStatement(updateStr);
            pstmt.executeUpdate();
            System.out.println("상품등록 성공!");
        } catch (SQLException e) {
            System.out.println("상품등록 실패! \n번호 중복을 확인해주세요");
        }
    }

    public void del(int a, String b) { // 관리자모드에서 상품삭제

        try {
            String updateStr = "DELETE FROM DATA WHERE NO = '" + a + "' AND NAME = '" + b + "'";
            pstmt = con.prepareStatement(updateStr);
            pstmt.executeUpdate();
            System.out.println("상품삭제 성공!");
        } catch (SQLException e) {
            System.out.println("DELETE FROM DATA WHERE NO = '" + a + "' AND NAME = '" + b + "'");
            System.out.println("상품삭제 실패! \n 입력을 다시 확인해주세요");
        }

    }

    public void lst1() throws SQLException { // 상품의 고유번호, 이름, 가격을 출력
        String sql1 = "SELECT * FROM DATA";
        stmt = con.createStatement();
        rs = stmt.executeQuery(sql1);
        while (rs.next()) {
            int NO = rs.getInt("NO");
            String NAME = rs.getString("NAME");
            int PRICE = rs.getInt("PRICE");
            System.out.println("NO:" + NO + "  상품명:" + NAME + "  가격:" + PRICE);
        }
    }

    public void sell(int a, String b, int c) throws SQLException { // 상품의 번호를 받아 상품의 가격을 출력해주고 money1이라는 메소드를 호출하여 상품을
                                                                   // 구매
        String sql = "SELECT * FROM DATA WHERE NO =" + a;
        rs = stmt.executeQuery(sql);
        while (rs.next()) {
            int PRICE = rs.getInt("PRICE");

            money1(PRICE, b, c);
        }

    }

    public void bk(String a, int b, int c) throws SQLException { // 고객의 장바구니 상품을 넣는다
        String v1 = ""; // 고객의 아이디와 상품의 이름이 같다면 같은 행에 수량과 총가격이 업데이트된다
        String v2 = "";
        String NAME = "";
        int PRICE = 0;
        String sql1 = "SELECT * FROM DATA WHERE NO = " + c;
        rs = stmt.executeQuery(sql1);
        while (rs.next()) {
            NAME = rs.getString("NAME");
            PRICE = rs.getInt("PRICE");
        }
        String sql2 = "SELECT US,NAME FROM BK WHERE US = '" + a + "' AND NAME = '" + NAME + "'";
        rs1 = stmt.executeQuery(sql2);
        while (rs1.next()) {
            v1 = rs1.getString("US");
            v2 = rs1.getString("NAME");
        }
        if (a.equals(v1) && NAME.equals(v2)) {
            String updateStr = "UPDATE BK SET US = '" + a + "', NAME = '" + NAME + "',PRICE = " + PRICE
                    + ", ONE = NVL((SELECT ONE FROM BK WHERE US = '" + a + "' AND ROWNUM = 1),0)+" + b
                    + ", TWO = NVL((SELECT TWO FROM BK WHERE US = '" + a + "' AND ROWNUM = 1),0)+" + PRICE * b
                    + " WHERE US = '" + a + "' AND NAME = '" + NAME + "'";
            pstmt = con.prepareStatement(updateStr);
            pstmt.executeUpdate();

        } else {
            String sql = "INSERT INTO BK VALUES ('" + a + "','" + NAME + "','" + PRICE +
                    "','" + b + "', '"
                    + PRICE * b
                    + "')";
            stmt.executeQuery(sql);
            System.out.println("장바구니에 저장되었습니다.");
        }

    }

    public void bklst(String a) throws SQLException { // 고객의 장바구니에 들어가있는 목록
        String sql1 = "SELECT * FROM BK WHERE US = '" + a + "'";
        stmt = con.createStatement();
        rs = stmt.executeQuery(sql1);
        while (rs.next()) {
            String NAME = rs.getString("NAME");
            int PRICE = rs.getInt("PRICE");
            int ONE = rs.getInt("ONE");
            int TWO = rs.getInt("TWO");
            System.out.println("상품명: " + NAME + "  상품가격:" + PRICE + "  선택 수량: " + ONE + "  총 가격: " + TWO);
        }
    }

    public void sell1(String a) throws SQLException { // 고객의 장바구니에 물품 일괄결제
        int b = 0;
        int PRICE = 0;
        int NUM = 0; // 추후에 수량변경을 위한 변수
        String sql = "SELECT * FROM BK WHERE US = '" + a + "'";
        rs = stmt.executeQuery(sql);

        String sql3 = "SELECT MONEY FROM PR WHERE ID = '" + a + "'";
        rs1 = stmt1.executeQuery(sql3);
        while (rs1.next()) {
            b = rs1.getInt("MONEY");
        }

        while (rs.next()) {
            NUM = rs.getInt("ONE");
            PRICE = rs.getInt("TWO");
        }

        if (b > PRICE) {
            String sql1 = "UPDATE BK SET ONE= 0, TWO = 0";
            String sql2 = "UPDATE PR SET MONEY = (SELECT MONEY FROM PR WHERE ID = '" + a + "') -" + PRICE
                    + "WHERE ID = '" + a + "'";
            pstmt = con.prepareStatement(sql1);
            pstmt.executeUpdate();
            pstmt = con.prepareStatement(sql2);
            pstmt.executeUpdate();
            System.out.println("상품 결제 완료!");
        } else {
            System.out.println("잔액이 부족합니다.");
        }

    }

    public void del1(String a, String b) { // 고객의 장바구니 물품 삭제
                                           // 물품을 일괄삭제 시키기때문에 추후 수정을통해 수량을 빼게 만들어야한다
        try {
            String updateStr = "DELETE FROM BK WHERE US = '" + a + "' AND NAME = '" + b + "'";
            pstmt = con.prepareStatement(updateStr);
            pstmt.executeUpdate();
            System.out.println("상품삭제 성공!");
        } catch (SQLException e) {
            System.out.println("DELETE FROM DATA WHERE NO = '" + a + "' AND NAME = '" + b + "'");
            System.out.println("상품삭제 실패! \n 입력을 다시 확인해주세요");
        }

    }

    public void views(String a) throws SQLException { // 고객의 잔액확인
        String sql = "SELECT MONEY FROM PR WHERE ID = '" + a + "'";
        stmt = con.createStatement();
        rs = stmt.executeQuery(sql);
        rs.next();
        int b = rs.getInt("MONEY");
        System.out.println(a + " 고객님의 잔액은 " + b + "원 입니다.");

    }

}