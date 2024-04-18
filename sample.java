import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class sample {
    public static void main(String[] args) {
        // JDBC ���� ����
        String url = "jdbc:oracle:thin:@localhost:1521:xe?useUnicode=true&characterEncoding=UTF-8";
        String user = "ATOM";
        String password = "1234";
        Connection con = null;
        PreparedStatement pstmt = null;
        // �����ͺ��̽� ����
        try {
            con = DriverManager.getConnection(url, user, password);
            System.out.println("�����ͺ��̽� ���� ����!");
            String sql = "INSERT INTO SAMPLE (KOREAN_D, ENG_D) VALUES (?, ?)";
            pstmt = con.prepareStatement(sql);
            pstmt.setString(1, "��");
            pstmt.setString(2, "ABCD");
            // SQL ����
            int rowsInserted = pstmt.executeUpdate();
            if (rowsInserted > 0) {
                System.out.println("�����Ͱ� ���������� ���ԵǾ����ϴ�.");
            }
        } catch (SQLException e) {
            System.err.println("�����ͺ��̽� ���� ���� �Ǵ� SQL ���� ����!");
            e.printStackTrace();
        } finally {
            // ���ҽ� ����
            try {
                if (pstmt != null) {
                    pstmt.close();
                }
                if (con != null) {
                    con.close();
                }
            } catch (SQLException e) {
                System.err.println("���ҽ� ���� �� ���� �߻�!");
                e.printStackTrace();
            }
        }
    }
}