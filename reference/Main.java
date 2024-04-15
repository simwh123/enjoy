// DB 연동해서 쿼리 실행해보자.
package shop;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;
// 상품 등록과 조회를 하나의 클래스 내부 메서드로 전환하는게 낫지 않을까?

class RegistItems { // 상품 등록 클래스

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String table = "";
		int choice=0; // if문 분기
		try {
			// JDBC 드라이버 로드
			Class.forName("oracle.jdbc.driver.OracleDriver");

			// 데이터베이스 연결
			conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "SHOPDB", "1234");
			conn.setAutoCommit(false);
			//			System.out.println("DB 접속 완료\n");

			while(choice != 3) {
				// SQL 쿼리 생성 (매개 변수를 사용하여 쿼리를 생성)
				// ITEM_NUMBER 컬럼은 DB측에서 시퀀스를 이용하여 순차적으로 증가시키기.
				System.out.println("등록 할 상품 입력");
				System.out.println("1. 상의 | 2. 하의 | 3. 상품 등록 종료");
				choice = sc.nextInt();
				sc.nextLine(); // 버퍼 비우기
				if(choice == 1) { // 상의 선택됨
					System.out.println("|1. 반소매| 2. 긴소매 |");
					int choice2 = sc.nextInt();
					sc.nextLine();
					if(choice2 == 1) {
						System.out.println("반소매 상품 등록을 시작합니다.");
						table = "S_SLEEVE";
					} else if (choice2 == 2) {
						System.out.println("긴소매 상품 등록을 시작합니다.");
						table = "L_SLEEVE"; 
					} else {
						System.out.println("잘못된 입력입니다.");
						continue;
					}
				} else if(choice == 2) { // 하의 선택 
					System.out.println("|1. 와이드 핏| 2. 스탠다드 핏 | 3. 슬림 핏 |");
					int choice2 = sc.nextInt();
					sc.nextLine();
					if(choice2 == 1) {
						System.out.println("새로운 와이드 팬츠 상품을 등록합니다.");
						table = "WIDE_P";
					} else if (choice2 == 2) {
						System.out.println("새로운 스탠다드 팬츠 상품을 등록합니다.");
						table = "STANDARD_P"; 
					} else if (choice2 == 3) {
						System.out.println("새로운 슬림 팬츠 상품을 등록합니다.");
						table = "SLIM_P"; 
					} else {
						System.out.println("잘못된 입력입니다.");
						continue; // 맨 처음 while 분기로 돌아갈듯
					}

				}
				else if(choice == 3) {
					System.out.println("상품 등록을 종료합니다.");
					break;
				}
				else {
					System.out.println("잘못된 입력");
					continue;
				}
				// 상 하의 입력값을 맞춰줘야 할 이유 ??

				String sequence = table + "_SEQ";;
				String sql = "INSERT INTO " + table + " (ITEM_NUMBER, ITEM_BRAND, ITEM_NAME, COST, STOCK, ITEM_INFO) VALUES (" + sequence +".nextval, ?, ?, ?, ?, ?)";

				//				System.out.println("sql 변수값 입력완료");
				// PreparedStatement 객체 생성
				pstmt = conn.prepareStatement(sql);
				//				System.out.println("prepareStatement메서드 실행");
				//			rs = pstmt.executeQuery();
				// 매개 변수 설정
				//				System.out.println("ITEM NUMBER 입력 : ");
				String iNumber = sequence;
				System.out.println("브랜드 입력 : ");
				String iBrand = sc.nextLine();
				System.out.println("상품명 입력 : ");
				String iName = sc.nextLine();
				System.out.println("가격 입력 : ");
				int iCost = sc.nextInt();
				sc.nextLine(); // 버퍼 클리어

				// 재고는 입력받는것이 아닌 자동 수량 변경으로 변경할것
				System.out.println("입고 수량 입력 : ");
				int stock = sc.nextInt();
				sc.nextLine(); // 버퍼 클리어
				System.out.println("상품설명 : ");
				String iInfo = sc. nextLine();

				//				pstmt.setString(1, iNumber); // 첫 번째 매개 변수 설정
				pstmt.setString(1, iBrand); // 두 번째 매개 변수 설정
				pstmt.setString(2, iName); // 두 번째 매개 변수 설정
				pstmt.setInt(3, iCost); // 세 번째 매개 변수 설정
				pstmt.setInt(4, stock); // 네 번째 매개 변수 설정
				pstmt.setString(5, iInfo); // 다섯 번째 매개 변수 설정

				// SQL 실행
				int rowsInserted = pstmt.executeUpdate();

				System.out.println("executeUpdate 실행");
				conn.commit();
				//			if (rowsInserted > 0) {
				//			    System.out.println("데이터가 성공적으로 삽입되었습니다.");
				//			} else {
				//			    System.out.println("데이터 삽입 실패!");
				//			}
			}
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
				if (conn != null)conn.close();
				if (pstmt != null)pstmt.close();
				if (rs != null) rs.close();
				System.out.println("시스템 안전 종료");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

	}
}

class ShowItems { // 전체 상품 조회 클래스
	// 클래스 지정 없이 모든 상품을 조회할 방법은 없을까
	public static void main(String[] args) {
		Connection conn = null;
		Scanner sc = new Scanner(System.in);
		String table = "";
		int choice=0;
		try {
			// JDBC 로드
			Class.forName("oracle.jdbc.driver.OracleDriver");
			//			System.out.println("JDBC Driver 로드 성공 !"); // 확인용 콘솔출력
			String url = "jdbc:oracle:thin:@localhost:1521:xe";	// thin = tcp : 데이터 전송 방식
			//				localhost = 사용자 PC의 DB에 접속함 / 1521 : 포트 번호
			String user = "SHOPDB";	// Oracle 사용자 계정
			String pw = "1234";		// Oracle 사용자 비밀번호
			while(choice != 3) {
				System.out.println("조회 할 테이블을 입력하시오");
				System.out.print("1. 상의 | 2. 하의 | 3. 상품조회 종료");
				choice = sc.nextInt();
				if(choice == 1) {
					System.out.println("|1. 반소매| 2. 긴소매");
					int choice2 = sc.nextInt();
					sc.nextLine();
					if(choice2 == 1) {
						System.out.println("반소매 상품을 조회합니다.");
						table = "S_SLEEVE";
					}
					else if(choice2 == 2) {
						System.out.println("긴소매 상품을 조회합니다.");
						table = "L_SLEEVE"; 
					}
				} else if(choice == 2) { // 하의 상품 조회
					System.out.println("|1. 와이드핏| 2. 스탠다드 | 3. 슬림핏 | ");
					int choice2 = sc.nextInt();
					sc.nextLine();
					if(choice2 == 1) {
						System.out.println("와이드 팬츠 상품을 조회합니다.");
						table = "WIDE_P";
					}
					else if(choice2 == 2) {
						System.out.println("스탠타드 팬츠 상품을 조회합니다.");
						table = "STANDARD_P"; 
					}else if(choice2 == 3) {
						System.out.println("슬림팬츠 상품을 조회합니다.");
						table = "SLIM_P"; 
					}
				}
				else if(choice == 3) {
					System.out.println("상품조회를 종료합니다.");
					break;
				}
				else {
					System.out.println("잘못된 입력");
					continue;
				}

				String sql = "SELECT * FROM " + table + " ORDER BY ITEM_NUMBER";

				// DB 연결
				conn = DriverManager.getConnection(url, user, pw);
				//			System.out.println("DB 연결 성공 !");
				/* DB 상태를 위한 변수, 객체 생성 */
				Statement st = conn.createStatement();
				// 위에서 입력받은 sql 변수의 값 그대로 쿼리를 실행한다. *오타 주의
				ResultSet rs = st.executeQuery(sql);
				System.out.println("상품번호     |    브랜드    | 종류       | 가격    | 입고수량     | 상품설명");
				/*데이터 읽어오기*/
				while(rs.next()) {
					//				if (rs.next()) { // rs.next() 값이 true일 때 아래 조건문 실행
					int iNumber = rs.getInt("ITEM_NUMBER");
					String iBrand = rs.getString("ITEM_BRAND");
					String iName = rs.getString("ITEM_NAME");
					int iCost = rs.getInt("COST");
					int iStock = rs.getInt("STOCK");
					String iInfo = rs.getString("ITEM_INFO");

					System.out.printf("%-9d |    %-5s |%-11s | %-6d | %-9d | %-15s\n", iNumber, iBrand, iName, iCost, iStock, iInfo);
					//				}

				} // while 종료
			}
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				//					rs.close();
				//					st.close();
				conn.close();
				//				System.out.println("DB 연결 해제");
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
}

public class Main {
	// 메인 클래스의 메인 메서드는 메뉴출력 및 메서드 호출 기능만 가질것.
	public static void main(String[] args) {

		Scanner sc = new Scanner(System.in);
		int choice = 0;
		while(choice != 3) {
			// 회원가입은 어떻게 넣을건데
			System.out.println("MENU");
			System.out.println("|1. 상품 등록 |2. 상품 조회 |3. 종료 |4. 회원가입 | 5. 회원 조회"); // 마지막에 순서 바꿔주기
			choice = sc.nextInt();
			sc.nextLine();
			if(choice == 1) {
				RegistItems.main(args); // 상품 등록 메서드 호출
			} else if (choice == 2) {
				ShowItems.main(args); // 상품 조회 메서드 호출
			} else if (choice == 3) {
				System.out.println("프로그램을 종료합니다.");
				break;
			} else if (choice == 4) { // 회원가입 분기로 돌리기
				System.out.println("회원가입을 진행합니다.");
				System.out.print("사용할 ID 입력 : "); // 중복 ID 없는지 확인하는 기능이 필요할듯
				String id = sc.nextLine();
				System.out.print("PW 입력 : ");
				String pw = sc.nextLine();
				System.out.print("이름 입력 : ");
				String name = sc.nextLine();
				System.out.print("연락처 입력 : ");
				int phoneNum = sc.nextInt();
				sc.nextLine();
				System.out.print("생년월일 6자리 입력 : ");
				int birth = sc.nextInt();
				sc.nextLine();
				UserSignUp user1 = new UserSignUp(id, pw, name, phoneNum, birth);
//				int ch = sc.nextInt();
//				if(ch == 1) { // (admin + i)로 계속 증가시켜줄수있나?
//			

//				} else if (ch == 2) {
//					// 사용자 가입
//				} else System.out.println("잘못된 입력입니다.");
			} else if (choice == 5) {
				System.out.println("전체 회원 조회");
				ShowUser.showUser();
			}

			else { 
				System.out.println("잘못된 입력입니다.");
				continue;
			}
		}
	} // main() 종료
}



