package Test01;

import java.util.ArrayList;
import java.util.Scanner;

import javax.swing.plaf.synth.SynthStyle;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Test01 {
    public static void main(String[] args) throws SQLException {
        Join jo = new Join();
        money mo = new money();
        Scanner sc = new Scanner(System.in);
        Test02 cone = new Test02();
        cone.connect();
        while (true) {
            System.out.println("회원가입:1 로그인:2 종료:3");
            String var1 = sc.nextLine();

            if (var1.equals("1")) {
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
                        cone.per(var2, var3, var4);
                        break;
                    }
                }

            } else if (var1.equals("2")) {
                System.out.println("로그인");
                System.out.println("아이디를 입력하세요");
                String var2 = sc.nextLine();
                System.out.println("비밀번호를 입력하세요");
                String var3 = sc.nextLine();
                cone.road(var2, var3);

            } else if (var1.equals("3")) {
                System.out.println("종료");
                cone.databaseClose();
                break;
            }

            else if (var1.equals("3")) {
                System.out.println("금액충전");
                int mo1 = sc.nextInt();
                sc.nextLine();
                if (mo1 > 0) {
                    mo.money(mo1);
                } else {
                    System.out.println("입력을 확인해주세요");
                }

            } else if (var1.equals("4")) {
                System.out.println("상품구입");

                // mo.var1 -= 2000;
            } else if (var1.equals("5")) {
                System.out.println("장바구니");
                System.out.println("리스트확인");
                for (int i = 0; i < jo.lst.size(); i++) {
                    System.out.println(jo.lst.get(i));
                }
            } else if (var1.equals("8")) {
                System.out.println("금액확인");
                System.out.println(mo.var1);

            } else {
                System.out.println("입력값을 확인해 주세요");

            }

        }
    }
}

class Join {
    String ID;
    String PW;
    String name;
    ArrayList<String> lst = new ArrayList<String>();

    public void Join(String ID, String PW, String name) {
        this.ID = ID;
        this.PW = PW;
        this.name = name;
        boolean var1 = lst.contains(ID + "/" + PW + "/" + name);
        if (var1 == false) {
            lst.add(ID + "/" + PW + "/" + name);
        }

    }
}

class Login {

}

class money {
    int var1 = 0;

    public void money(int var1) {
        this.var1 += var1;
    }
}

class model {
    ArrayList<String> lst = new ArrayList<String>();
    String a;
    int b;

    public void model(String a, int b) {
        this.a = a;
        this.b = b;
        for (int i = 0; i < lst.size(); i++) {
            lst.add(a + "/" + b);
        }
    }
}
