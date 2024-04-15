package shop;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class SignUp {
	protected String name;
	protected String id;
	protected String pw; // " PW
	protected int phone; // 폰번호
	protected int birth; // 생년월일 앞 6자리만

	public SignUp() {}
	// 관리자 가입용 생성자
	public SignUp(String id, String pw, String name, int phone, int birth) {
		this.id = id;
		this.pw = pw;
		this.name = name;
		this.phone = phone;
		this.birth = birth;
		System.out.println(name + "님 회원 가입이 완료되었습니다.");
		// 데이터를 바로 DB로 넘겨줄까
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Scanner sc = new Scanner(System.in);

		int choice = 0;
		try {
			// JDBC 드라이버 로드
			Class.forName("oracle.jdbc.driver.OracleDriver");

			// 데이터베이스 연결
			conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "SHOPDB", "1234");
			conn.setAutoCommit(false);
			// System.out.println("DB 접속 완료\n");

			// 쿼리 준비
			String sql = "INSERT INTO SHOPUSER (ID, PW, NAME, PHONE, BIRTH) VALUES (?, ?, ?, ?, ?)";
			pstmt = conn.prepareStatement(sql);

			// 매개 변수 설정
			pstmt.setString(1, id); // 첫 번째 매개 변수 설정
			pstmt.setString(2, pw); // 두 번째 매개 변수 설정
			pstmt.setString(3, name); // 세 번째 매개 변수 설정
			pstmt.setInt(4, phone); // 네 번째 매개 변수 설정
			pstmt.setInt(5, birth); // 다섯 번째 매개 변수 설정

			// SQL 실행
			int rowsInserted = pstmt.executeUpdate();

			System.out.println("executeUpdate 실행");
			conn.commit();
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("예외 발생");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			// 리소스 해제
			try {
				if (conn != null) conn.close();
				if (pstmt != null) pstmt.close();
				if (rs != null) rs.close();
				System.out.println("시스템 안전 종료");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	// 사용자 가입용 생성자
	//	public SignUp(String id, String pw, String name, String phone, int birth) {
	//		this.id = id;
	//		this.pw = pw;
	//		this.name = name;
	//		this.phone = phone;
	//		this.birth = birth;
	//		System.out.println(name + "님의 신규 회원가입이 완료되었습니다.");
	//	}
	public String getName() {
		return name;
	}
	public String getId() {
		return id;
	}
	public String getPw() {
		return pw;
	}
	public int getPhone() {
		return phone;
	}
	public int getBirth() {
		return birth;
	}
	//	public int getAdminNum() {
	//		return adminNum;
	//	}
	//	

}

//class AdminSignUp extends SignUp { // 관리자 회원가입
//
//	//	public AdminSignUp(String id, String pw, String name, String phone, int birth) {
//	//		// 이게 맞나?
//	//		super(id, pw, name, phone, birth); // 부모 생성자를 호출해서 생성된 객체의 필드값을 넣어주자
//	//	}
//	public AdminSignUp(int adminNum, String id, String pw, String name, String phone, int birth) {
//		// 이게 맞나?
//		super(id, pw, name, phone, birth); // 부모 생성자를 호출해서 생성된 객체의 필드값을 넣어주자
//	}
//}
//
class UserSignUp extends SignUp { // 사용자(고객) 회원가입
	public UserSignUp(String id, String pw, String name, int phone, int birth) {
		super(id, pw, name, phone, birth); // 부모 생성자를 호출해서 생성된 객체의 필드값을 넣어주자
	}
}

class ShowUser{
	public static void showUser() {
		Connection conn = null;
		try {
			// JDBC 로드
			Class.forName("oracle.jdbc.driver.OracleDriver");
			//			System.out.println("JDBC Driver 로드 성공 !"); // 확인용 콘솔출력
			String url = "jdbc:oracle:thin:@localhost:1521:xe";	// thin = tcp : 데이터 전송 방식
			//				localhost = 사용자 PC의 DB에 접속함 / 1521 : 포트 번호
			String user = "SHOPDB";	// Oracle 사용자 계정
			String pw = "1234";		// Oracle 사용자 비밀번호

			String sql = "SELECT * FROM SHOPUSER"; // 가입일자 컬럼을 추가해서 가입일자 순으로 정렬하기

			// DB 연결
			conn = DriverManager.getConnection(url, user, pw);
			//			System.out.println("DB 연결 성공 !");
			/* DB 상태를 위한 변수, 객체 생성 */
			Statement st = conn.createStatement();
			// 위에서 입력받은 sql 변수의 값 그대로 쿼리를 실행한다. *오타 주의
			ResultSet rs = st.executeQuery(sql);
			System.out.println("    ID     |      PW     |    이름      |     연락처     | 생년월일 |");
			/*데이터 읽어오기*/
			while(rs.next()) {
				//				if (rs.next()) { // rs.next() 값이 true일 때 아래 조건문 실행
				String userId = rs.getString("ID");
				String userPw = rs.getString("PW");	// PW는 최초 확인 이후 조회항목에서 제외할것
				String userName = rs.getString("NAME");
				int userPhone = rs.getInt("PHONE");
				int userBirth = rs.getInt("BIRTH");

				System.out.printf("%-10s |  %-10s |%-11s | %-12d | %-6d |\n", userId, userPw, userName, userPhone, userBirth);
				//				}

			} // while 종료
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				conn.close();
				
				//				System.out.println("DB 연결 해제");
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
}
