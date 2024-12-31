package com.choongang;  // 패키지 선언, 해당 클래스를 com.choongang 패키지에 속하게 합니다.

import java.util.Arrays;
import java.util.Scanner;  // Scanner 클래스를 사용하기 위해 java.util 패키지에서 가져옵니다.
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static java.awt.SystemColor.menu;

public class MyStorage {
    /**
     * 프로그램의 메인 메서드로, 프로그램의 실행 시작점입니다.
     */
    public static void main(String[] args){
        //MyStorageMethod myStorageMethod= new MyStorageMethod();
        System.out.println("[Item_Storage]");
        System.out.println("-".repeat(30));
        System.out.printf("[System] 점장님 어서오세요.\n[System] 해당 프로그램의 기능입니다.\n");

        int menuNum = 0;
        Scanner s = new Scanner(System.in);
        do {
            MyStorageMethod.showMenu();
            menuNum = MyStorageMethod.selectMenu(s);  // 메뉴 선택을 받습니다.
            if(menuNum == 6){  // '프로그램 종료' 선택 시
                System.out.println("[System] 프로그램을 종료합니다.");
                break;  // 반복문을 종료하고 프로그램을 종료합니다.
            }
            // 선택된 메뉴에 따라 해당 기능을 실행합니다.
            switch (menuNum) {
                case 1:
                    MyStorageMethod.prod_input(s);
                    break;
                case 2:
                    MyStorageMethod.prod_remove(s);
                    break;
                case 3:
                    MyStorageMethod.prod_amount_add(s);
                    break;
                case 4:
                    MyStorageMethod.prod_amount_decrease(s);
                    break;
                case 5:
                    MyStorageMethod.prod_search();
                    break;
                default:
                    System.out.println("[System] 시스템 번호를 다시 확인하여 주세요.");
            }
        }while (menuNum != 6);
        s.close();  // Scanner 객체를 닫습니다.
    }
}
