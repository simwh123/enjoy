package Test01;

import java.util.Scanner;
import java.sql.SQLException;

public class Test01 {
    public static void main(String[] args) throws SQLException {
        Scanner sc = new Scanner(System.in);
        Test02 cone = new Test02();

        while (true) {
            System.out.println("회원가입:1 로그인:2 종료:3");
            String var1 = sc.nextLine();

            if (var1.equals("1")) {
                System.out.print("\033\143");
                System.out.println("회원가입");

                System.out.println("아이디를 입력하세요");
                String var2 = sc.nextLine();

                while (true) {
                    System.out.println("비밀번호를 입력하세요 (4자 이상 8자 이하)");
                    String var3 = sc.nextLine();
                    if (var3.length() < 4) {
                        System.out.println("비밀번호는 4자 이상으로 입력하세요");
                        continue;
                    } else if (var3.length() > 8) {
                        System.out.println("비밀번호는 8자 이하로 입력하세요");
                        continue;
                    } else {
                        System.out.println("이름을 입력하세요");
                        String var4 = sc.nextLine();
                        System.out.print("\033\143");
                        cone.per(var2, var3, var4);
                        break;
                    }
                }

            } else if (var1.equals("2")) {
                System.out.print("\033\143");
                System.out.println("로그인");
                System.out.println("아이디를 입력하세요");
                String var2 = sc.nextLine();
                System.out.println("비밀번호를 입력하세요");
                String var3 = sc.nextLine();
                System.out.print("\033\143");
                cone.road(var2, var3);

            } else if (var1.equals("3")) {
                System.out.print("\033\143");
                System.out.println("종료");
                cone.databaseClose();
                break;
            }

        }
    }
}
