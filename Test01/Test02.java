package Test01;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Test02 {
    public Test02() {
        Connection con = null;
        Statement st = null;
        ResultSet rs = null;
        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
            String url = "jdbc:oracle:thin:@localhost:1521:xe";
            String user = "ATOM";
            String pw = "1234";
            String sql = "SELECT * FROM EMP";
            con = DriverManager.getConnection(url, user, pw);
            System.out.println("연결 성공");

            st = con.createStatement();
            rs = st.executeQuery(sql);
            /* data 읽어오기 */
            while (rs.next()) {
                String en = rs.getString("EMPNO");
                String name = rs.getString("ENAME");
                String job = rs.getString("JOB");
                String mgr = rs.getString("MGR");
                String hiredate = rs.getString("HIREDATE");
                String sal = rs.getString("SAL");
                String comm = rs.getString("COMM");
                String deptno = rs.getString("DEPTNO");
                System.out.println(en + " " + name + " " + job + " " + mgr + " " + hiredate + " " + sal + " " + comm
                        + " " + deptno);
            }
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                st.close();
                rs.close();
                con.close();
                System.out.println("연결 해제");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

}
