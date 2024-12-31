package com.choongang;

import java.util.Arrays;
import java.util.Scanner;
import java.util.stream.IntStream;

public class MyStorageMethod {
    // 'EMPTY'는 빈 제품 위치를 나타내는 상수로, '등록 가능' 문자열을 저장하고 있습니다.
    public final static String EMPTY = "등록 가능";
    // product는 현재 등록된 제품을 저장하는 배열입니다.
    static String[] products = new String[]{EMPTY,EMPTY,EMPTY,EMPTY,EMPTY};
    // counts는 제품의 수량을 저장하는 배열입니다.
    static int[] counts = new int[]{0, 0, 0, 0, 0};

    /**
     * 메뉴 옵션을 화면에 표시하는 메서드입니다.
     * 반환 타입은 void로, 별도의 반환 값이 없습니다.
     */
    public static void showMenu(){
        System.out.println("1. 물건 정보(제품명) 등록하기");
        System.out.println("2. 물건 정보(제품명) 등록 취소하기");
        System.out.println("3. 물건 넣기 (제품 입고)");
        System.out.println("4. 물건 빼기 (제품 출고)");
        System.out.println("5. 재고 조회");
        System.out.println("6. 프로그램 종료");
        System.out.println("-".repeat(30));  // '-' 문자를 30번 반복하여 줄을 그어 메뉴 구분을 명확하게 합니다.
    }

    /**
     * 사용자로부터 선택된 메뉴 번호를 입력 받아 반환하는 메서드입니다.
     * @param s Scanner 객체, 사용자로부터 입력을 받기 위해 사용됩니다.
     * @return int 메뉴 번호를 정수 형태로 반환합니다.
     */
    public static int selectMenu(Scanner s){
        System.out.print("[System] 원하는 기능의 번호를 입력하세요 : ");
        try{
            int select = Integer.parseInt(s.nextLine());  // 사용자로부터 정수 입력을 받아 변수 select에 저장합니다.
            return select;  // 입력 받은 정수를 반환합니다.
        }catch (NumberFormatException e){
            System.out.println("[System] 잘못된 값이 입력되었습니다.");
            System.out.println();
            return 0;
        }
    }

    /**
     * 제품을 등록하는 메서드입니다.
     * @param s Scanner 객체, 사용자로부터 제품명 입력을 받기 위해 사용됩니다.
     * 반환 타입은 void로, 별도의 반환 값이 없습니다.
     * 제품의 이름을 입력받아, products 배열에 할당합니다.
     */
    public static void prod_input(Scanner s){
        // 제품 등록 로직을 구현합니다. 제품명을 입력받아 빈 배열 위치에 저장합니다.
        // TODO:
        int emptyIdx = findEmptySlot();
        if(emptyIdx == -1) {
            System.out.println("[System] 등록된 물품이 가득 찼습니다.");
            return;
        }
        System.out.print("[System] 제품 등록을 원하는 제품명을 입력하세요 :");
        String inputName = s.nextLine().trim();

        for(int i = 0; i < products.length; i++){
            if(products[i].equals(inputName)){
                System.out.println("[System] 중복된 이름의 물품이 있습니다.");
                System.out.println();
                return;
            }
        }
        products[emptyIdx] = inputName;
        counts[emptyIdx] = 0;
        System.out.println("[System] 등록이 완료됬습니다.");
        prod_search();
    }

    /**
     * 제품 등록을 취소하는 메서드입니다.
     * @param s Scanner 객체, 사용자로부터 취소할 제품명 입력을 받기 위해 사용됩니다.
     * 반환 타입은 void로, 별도의 반환 값이 없습니다.
     * 제품의 이름을 입력받아, products의 배열에서 요소를 초기화합니다 ("등록 가능"으로 변경)
     * 해당 제품의 수량도 0으로 변경해야 합니다. (counts 배열의 같은 index의 값을 0으로 변경합니다)
     */
    public static void prod_remove(Scanner s){
        // 제품 등록 취소 로직을 구현합니다. 입력받은 제품명에 해당하는 배열 요소를 EMPTY로 설정합니다.
        System.out.println("[System] 제품 등록 취소를 원하는 제품명을 입력하세요 :");
        String input = s.nextLine();  // 제품명 입력 받기

        int foundIdx = findProductIndex(input);
        if(notFindProduct(foundIdx)){
            return;
        }

        products[foundIdx] = EMPTY;
        counts[foundIdx] = 0;
        rearrangeArrays();
        System.out.println("[System] 제품 취소가 완료됬습니다.");
        prod_search();
    }

    /**
     * 제품 수량을 증가시키는 메서드입니다.
     * @param s Scanner 객체를 통해 사용자로부터 제품명과 수량 입력을 받습니다.
     * products 배열에서 입력받은 요소를 찾아내어. counts 배열에서 해당 제품의 수량을 입력받은 수 만큼 증가시킵니다.
     * 반환 타입은 void로, 별도의 반환 값이 없습니다.
     */
    public static void prod_amount_add(Scanner s) {
        productAddMassagePrint();
        String input = s.nextLine();  // 제품명 입력 받기

        int foundIdx = findProductIndex(input);
        if(notFindProduct(foundIdx)){
            return;
        }

        int cnt = getValidIntegerInput(s, "[System] 추가할 수량을 입력해주세요 : ");
        counts[foundIdx] += cnt;  // 제품의 수량을 증가

        System.out.println("[Clear] 정상적으로 제품의 수량 추가가 완료되었습니다.");
        prod_search();
        System.out.println("-".repeat(25));
    }

    // 제품의 수량을 추가하기 전에 출력하는 메세지
    private static void productAddMassagePrint(){
        System.out.println("[System] 물건의 수량을 추가합니다.(입고)");
        prod_search();
        System.out.print("[System] 수량을 추가할 제품명을 입력하세요 : ");
    }

    /**
     * 제품 수량을 감소시키는 메서드입니다.
     * @param s Scanner 객체를 통해 사용자로부터 제품명과 수량 입력을 받습니다.
     * products 배열에서 입력받은 요소를 찾아내어.
     * counts 배열에서 해당 제품의 수량을 입력받은 수 만큼 감소시킵니다.
     * 반환 타입은 void로, 별도의 반환 값이 없습니다.
     */
    public static void prod_amount_decrease(Scanner s) {
        // 제품 수량 감축 로직을 구현합니다. 입력받은 수량만큼 해당 제품의 수량을 감소시킵니다.
        System.out.println("[System] 제품의 출고를 진행합니다. : ");
        prod_search();

        System.out.println("[System] 출고를 진행할 제품명을 입력하세요 : ");
        String deleteName = s.nextLine();  // 제품명 입력 받기
        int foundIdx = findProductIndex(deleteName);

        if(notFindProduct(foundIdx)){
            return;
        }

        int deleteQuantity = getValidIntegerInput(s, "[System] 원하는 출고량을 입력하세요 :");
        if(counts[foundIdx] < deleteQuantity){
            System.out.println("[System] 남은 수량보다 더 많이 출고할 수 없습니다.");
            return;
        }
        counts[foundIdx] -= deleteQuantity;
        System.out.println("[System] 출고가 완료되었습니다.");
        prod_search();
    }

    //등록된 모든 제품의 목록과 수량을 출력하는 메서드
    public static void prod_search(){
        System.out.println("[System] 현재 등록된 물건 목록 ▼");
        for (int i = 0; i < products.length; ++i){
            System.out.println("> " + products[i] + " : " + counts[i] + "개");
        }
        System.out.println("-".repeat(25));
    }

    //제품을 찾지 못한 경우 리턴하는 메서드
    private static boolean notFindProduct(int foundIdx){
        if (foundIdx < 0) {  // 제품을 찾지 못한 경우
            System.out.println("[Warning] 입력한 제품명이 없습니다. 다시 확인하여 주세요.");
            return true;
        }
        return false;
    }

    //빈 배열을 정리하는 메서드
    private static void rearrangeArrays(){
        String[] newProducts = Arrays.stream(products)
                .filter(products -> !products.equals(EMPTY))
                .toArray(String[]::new);

        int[] newCounts = IntStream.range(0, products.length)
                .filter(i -> !products[i].equals(EMPTY))
                .map(i->counts[i])
                .toArray();

        Arrays.fill(products, EMPTY);
        Arrays.fill(counts, 0);

        System.arraycopy(newProducts, 0, products, 0, newProducts.length);
        System.arraycopy(newCounts, 0, counts, 0, newCounts.length);
    }

    //배열의 특정 값을 가진 배열의 인덱스를 동적으로 탐색하는 메서드
    private static int findProductIndex(String productsName){
        for(int i = 0; i < products.length; i++){
            if(products[i].equals(productsName)){
                return i;
            }
        }
        return -1;
    }

    //배열의 빈 인덱스를 동적으로 탐색하는 메서드
    private static int findEmptySlot() {
        for (int i = 0; i < products.length; i++) {
            if (products[i].equals(EMPTY)) {
                return i;
            }
        }
        return -1;
    }

    //숫자가 아닌 경우, 에러 메시지를 출력하는 유효성 검사 메서드 입니다.
    private static int getValidIntegerInput(Scanner s, String message){
        //유효한 입력이 들어왔는지 체크하는 변수
        boolean validInput = false;
        int input = 0;

        while (!validInput){
            try{
                System.out.print(message);
                input = Integer.parseInt(s.nextLine());  // 수량 입력 받기
                validInput = true;
            }catch (NumberFormatException e){
                System.out.println("[Error] 숫자만 입력하세요");
            }
        }
        return input;
    }
}
