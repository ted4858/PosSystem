package project03;

import java.util.Scanner;

public class PosMain {
	
	private static Scanner scanner = new Scanner(System.in);
    private static PosSystem posSystem = new PosSystem();
    private static boolean run = true;
    private static boolean login_state = false;
    private static long startTime;
 
    //24시간 편의점 POS 시스템 시뮬레이션 메인 코드
    public static void main(String[] args) {
    	
        int selectNo = 0;
        String userName = null;
    	String passWord = null;
        
        System.out.println("\n<<<<<<<<<<<<< [ 24시간 편의점 POS 시스템입니다. ] >>>>>>>>>>>>>\n");
        System.out.println("<<<<<<<<<<< ( 사용자 이름과 패스워드를 입력하십시오. ) >>>>>>>>>>>");
        
        //POS 로그인 및 메뉴 선택 무한 반복문
        while(run) {
        	try {
        		if(login_state == false) {
        			System.out.println("\n---------------------------------------------------------");
        			System.out.println("\t\t로그인(이름, PassWord 입력)");
        			System.out.println("---------------------------------------------------------\n");
        			System.out.print("\t이름 : ");
        			userName = scanner.next();
        			System.out.print("\tPassWord : ");
        			passWord = scanner.next();
        			System.out.println("\n---------------------------------------------------------");
        			login_state = posSystem.checkLogin(userName, passWord);
        			// 로그인 후에 근무 시작 시간(MilliSecond) 저장
        			startTime = System.currentTimeMillis();
        		} else if (login_state == true) {
        			try {
        				System.out.println("\n--------------------- < 메뉴 선택 > -----------------------");
                        System.out.println("---------------------------------------------------------");
                        System.out.println("1.입고(물품 입력) | 2.출고(물품 판매) | 3.물품 검색 | 4.물품 삭제 |");
                        System.out.println("5.재고 파악 | 6.현재 예상 급료 | 7.암호 변경 | 8.로그아웃 | 9.종료");
                        System.out.println("---------------------------------------------------------");
                        System.out.print("\n# 번호 입력 : ");
             
                        selectNo = scanner.nextInt();
                        
                        System.out.println();
             
                        if(selectNo == 1) {                        	
                        	posSystem.incoming();
                        } else if(selectNo == 2) {                        	
                        	posSystem.outgoing();
                        } else if(selectNo == 3) {                       	
                        	posSystem.itemSearch();
                        } else if(selectNo == 4) {                        	
                        	posSystem.deleteItem();
                        } else if(selectNo == 5) {                        	
                        	posSystem.checkStock();
                        } else if(selectNo == 6) {
                        	salaryCalculation();
                        } else if(selectNo == 7) {
                        	posSystem.selectChangePasswordOrUserName();
                        } else if(selectNo == 8) {
                        	logOut();
                        } else if(selectNo == 9) {
                        	exit();
                        } else {
                        	System.out.println("잘못 입력하셨습니다. 다시 입력하세요! (입력값 : 1 ~ 9)\n");
                        }
					} catch (Exception e) {
						scanner = new Scanner(System.in);
						System.out.println("\n잘못 입력하셨습니다. (정수만 입력 가능)");
					}
        		} else {
        			System.out.println("\n사용자 이름과 패스워드를 입력하십시오.\n");
        		}
			} catch (Exception e) {
				scanner = new Scanner(System.in);
				System.out.println("\n잘못된 입력입니다. 다시 로그인 해주십시오.");
				//e.printStackTrace();
			}
        }
        
        System.out.println("---------------------------------------------------------");
        System.out.println("\nㅇ 급료 : "  + posSystem.salaryCalculation(startTime) + " 원");
        System.out.println("\nㅇ 오늘 하루도 수고하셨습니다.");
        System.out.println("---------------------------------------------------------");
    }
    
    //현재 나의 예상 급료 계산 메뉴 메소드
    private static void salaryCalculation() {
    	System.out.println(">> 6.현재 예상 급료\n");
    	System.out.println("---------------------------------------------------------");
    	System.out.println("\nㅇ 현재 " + posSystem.getUserName() + "님의 예상 급료는 " + posSystem.salaryCalculation(startTime) + " 원입니다.");
    	System.out.println("---------------------------------------------------------");
    }
    
    //로그아웃 메소드
    private static void logOut() {
    	System.out.println(">> 8.로그아웃\n");
    	login_state = false;
		System.out.println("ㅇ 로그아웃 되었습니다!");
    }
    
    //프로그램 종료 메소드
    private static void exit() {
    	System.out.println(">> 9.종료\n");
        run = false;
    }
}
