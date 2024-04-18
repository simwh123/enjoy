import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class sample {
    public static void main(String[] args) {
        // JDBC 연결 정보
        String url = "jdbc:oracle:thin:@localhost:1521:xe?useUnicode=true&characterEncoding=UTF-8";
        String user = "ATOM";
        String password = "1234";
        Connection con = null;
        PreparedStatement pstmt = null;
        // 데이터베이스 연결
        try {
            con = DriverManager.getConnection(url, user, password);
            System.out.println("데이터베이스 연결 성공!");
            String sql = "INSERT INTO SAMPLE (KOREAN_D, ENG_D) VALUES (?, ?)";
            pstmt = con.prepareStatement(sql);
            pstmt.setString(1, "가");
            pstmt.setString(2, "ABCD");
            // SQL 실행
            int rowsInserted = pstmt.executeUpdate();
            if (rowsInserted > 0) {
                System.out.println("데이터가 성공적으로 삽입되었습니다.");
            }
        } catch (SQLException e) {
            System.err.println("데이터베이스 연결 실패 또는 SQL 실행 오류!");
            e.printStackTrace();
        } finally {
            // 리소스 정리
            try {
                if (pstmt != null) {
                    pstmt.close();
                }
                if (con != null) {
                    con.close();
                }
            } catch (SQLException e) {
                System.err.println("리소스 정리 중 오류 발생!");
                e.printStackTrace();
            }
        }
    }
}