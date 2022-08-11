package project03;

import java.text.DecimalFormat;
import java.util.Calendar;
import java.util.Scanner;
import java.util.regex.Pattern;

public class PosSystem implements PointOfSales {
	private static Scanner scanner = new Scanner(System.in);
	private static boolean run = true;
	private String userName;
	private String passWord;
	private String salary;
	private String changePassword1;
	private String changePassword2;
	private int selectNo;
	private StockDB[] stockDB = new StockDB[100];
	private int totalItemType;
	private long totalItemPrice;
	private String totalItemPriceComma;
	private int checkError;
	private String disposalDatePattern;
	private String residentRegistrationNumber;
	private String residentRegistrationNumberPattern;
	private String selectItemName;
	private int selectBarcode;
	private int selectnumberOfItem;
	private boolean ItemNameOrBarcode;
	private long customerCash;
	private long payingCash;
	private String selectYesOrNo;
	
	
	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassWord() {
		return passWord;
	}

	public void setPassWord(String passWord) {
		this.passWord = passWord;
	}

	//생성자
	public PosSystem() {
		this.userName = "소대웅";
		this.passWord = "1234";
		this.salary = null;
		this.changePassword1 = null;
		this.changePassword2 = null;
		this.selectNo = 0;
		this.totalItemType = 0;
		this.totalItemPrice = 0;
		this.totalItemPriceComma = null;
		this.checkError = 1;
		this.disposalDatePattern = "\\d{4}-\\d{2}-\\d{2}";
		this.residentRegistrationNumber = null;
		this.residentRegistrationNumberPattern = "\\d{4}-\\d{2}-\\d{2}";
		this.selectItemName = null;
		this.selectBarcode = 0;
		this.selectnumberOfItem = 0;
		this.ItemNameOrBarcode = false;
		this.customerCash = 0;
		this.payingCash = 0;
		this.selectYesOrNo = new String();
		for(int i = 0; i < 100; ++i) {
			stockDB[i] = new StockDB();
		}
	}
	
	//로그인 되었는지 확인하기 위한 메소드
	@Override
	public boolean checkLogin(String userName, String passWord) {
		if(this.userName.equals(userName) && this.passWord.equals(passWord)) {
			System.out.println("\nㅇ 로그인 되었습니다!");
			return true;
		} else if(this.userName.equals(userName)) {
			System.out.println("\nㅇ 패스워드가 잘못 입력되었습니다. 다시 입력하십시오.");
			return false;
		} else if(this.passWord.equals(passWord)) {
			System.out.println("\nㅇ 이름이 잘못 입력되었습니다. 다시 입력하십시오.");
			return false;
		} else {
			System.out.println("\nㅇ 이름과 패스워드가 잘못 입력되었습니다.\nㅇ 다시 입력하십시오.");
			return false;
		}
	}
	
	//물품을 입고(입력)하기 위한 메소드
	@Override
	public void incoming() {
		System.out.println(">> 1.입고(물품 입력)\n");
		
		while(run) {
			try {
				System.out.println("ㅇ 현재 총 물품 종류는 " + this.totalItemType + "가지입니다.");
				System.out.print("ㅇ 입고하시겠습니까?(Y/N) ");
				this.changePassword1 = scanner.next();
				if(this.changePassword1.equals("Y")) {
					break;
				} else if(this.changePassword1.equals("N")) {
					System.out.println("\nㅇ 물품 입력를 종료합니다.");
					System.out.println("\nㅇ 메인 메뉴로 돌아갑니다.");
					run = false;
				} else {
					System.out.println("\n잘못된 입력입니다. (입력값 : Y / N)\n");
				}
			} catch (Exception e) {
				scanner = new Scanner(System.in);
				System.out.println("\n잘못된 입력입니다. 다시 입력해 주십시오.\n");
			}
		}
		
		while(run) {
			try {
				switch(checkError) {
				case 1:
					System.out.println("\n---------------------------------------------------------");
					System.out.println("ㅇ " + (this.totalItemType + 1) + "번 물품 추가");
					System.out.println("---------------------------------------------------------");
					System.out.print(">> 이름 : ");
					this.stockDB[totalItemType].name = scanner.next();
					checkError = 2;
				case 2:
					//물품 바코드는 자동 저장(번호로 저장) -> HashMap 연결
					this.stockDB[totalItemType].barcode = (this.totalItemType + 1);
					checkError = 3;
				case 3:
					System.out.print(">> 개당 가격 : ");
					this.stockDB[totalItemType].price = scanner.nextInt();
					checkError = 4;
				case 4:
					System.out.print(">> 개수 : ");
					this.stockDB[totalItemType].numberOfItem = scanner.nextInt();
					checkError = 5;
				case 5:
					System.out.print(">> 생산지 : ");
					this.stockDB[totalItemType].placeOfProduction = scanner.next();
					checkError = 6;
				case 6:
					System.out.print(">> 폐기 날짜(형식 : 2000-01-01) : ");
					this.stockDB[totalItemType].disposalDate = scanner.next();
					if(Pattern.matches(this.disposalDatePattern, this.stockDB[totalItemType].disposalDate)) {
						checkError = 7;
					} else {
						System.out.println("\n잘못된 입력입니다. (형식 : 2000-01-01)\n");
						break;
					}
				case 7:
					System.out.print(">> 성인용 유무(Y/N) : ");
					this.stockDB[totalItemType].forAdults = scanner.next();
					if(this.stockDB[totalItemType].forAdults.equals("Y") || 
							this.stockDB[totalItemType].forAdults.equals("N")) {
						checkError = 8;
					} else {
						System.out.println("\n잘못된 입력입니다. (입력값 : Y / N)\n");
						break;
					}
					System.out.println("---------------------------------------------------------");
					System.out.println("\n" + this.stockDB[totalItemType].name + " 입고 완료되었습니다!");
					this.stockDB[totalItemType].sumOfPrice = this.stockDB[totalItemType].numberOfItem * this.stockDB[totalItemType].price;
					totalItemType++;
				case 8:
					System.out.println("\nㅇ 현재 총 물품 종류는 " + this.totalItemType + "가지입니다.");
					System.out.print("ㅇ 더 입고하시겠습니까?(Y/N) ");
					this.selectYesOrNo = scanner.next();
					if(this.selectYesOrNo.equals("Y")) {
						checkError = 1;
						break;
					} else if(this.selectYesOrNo.equals("N")) {
						System.out.println("\nㅇ 입고를 종료합니다.");
						System.out.println("ㅇ 메인 메뉴로 돌아갑니다.");
					} else {
						System.out.println("\n잘못된 입력입니다. (입력값 : Y / N)\n");
						break;
					}
				default:
					checkError = 1;
					run = false;
				}
			} catch (Exception e) {
				scanner = new Scanner(System.in);
				System.out.println("\n잘못된 입력입니다. 다시 입력해 주십시오.\n");
				e.printStackTrace();
			}
		}
		run = true;
	}
	
	//물품을 출고(판매)하기 위한 메소드
	@Override
	public void outgoing() {
		System.out.println(">> 2.출고(물품 판매)\n");
		
		if(totalItemType == 0) {
			System.out.println("\t물품 재고가 없습니다.");
			run = false;
		}
		
		while(run) {
			try {
				System.out.println("ㅇ 현재 총 물품 종류는 " + this.totalItemType + "가지입니다.");
				System.out.print("ㅇ 출고하시겠습니까?(Y/N) ");
				this.changePassword1 = scanner.next();
				if(this.changePassword1.equals("Y")) {
					break;
				} else if(this.changePassword1.equals("N")) {
					System.out.println("\nㅇ 물품 판매를 종료합니다.");
					System.out.println("\nㅇ 메인 메뉴로 돌아갑니다.");
					run = false;
				} else {
					System.out.println("\n잘못된 입력입니다. (입력값 : Y / N)\n");
				}
			} catch (Exception e) {
				scanner = new Scanner(System.in);
				System.out.println("\n잘못된 입력입니다. 다시 입력해 주십시오.\n");
			}
		}
		
		while(run) {
			try {
				System.out.println("\n---------------------------------------------------------");
				System.out.println("\t1.이름으로 찾기 | 2.바코드 번호로 찾기");
				System.out.println("---------------------------------------------------------");
				System.out.print("\n# 번호 입력 : ");
	 
	            selectNo = scanner.nextInt();
	            
	            System.out.println();
	 
	            if(selectNo == 1) {
	            	System.out.println(">> 1.이름으로 찾기\n");
	            	ItemNameOrBarcode = true;
	            	outgoingItemNameOrBarcode(ItemNameOrBarcode);
		        } else if(selectNo == 2) {
		        	System.out.println(">> 2.바코드 번호로 찾기\n");
		        	ItemNameOrBarcode = false;
		        	outgoingItemNameOrBarcode(ItemNameOrBarcode);
		        } else {
		        	System.out.println("잘못 입력하셨습니다. 다시 입력하세요! (입력값 : 1, 2)\n");
		        }
			} catch (Exception e) {
				scanner = new Scanner(System.in);
				System.out.println("\n잘못 입력하셨습니다. (정수만 입력 가능)\n");
			}
		}
		
		checkError = 1;
		run = true;
	}
	
	//물품을 출고할 때 이름으로 검색할 것인지 바코드 숫자로 검색할 것인지 선택하는 메소드
	@Override
	public void outgoingItemNameOrBarcode(boolean ItemNameOrBarcode) {
		int checkItem = 0;
		while(run) {
			try {
				switch(checkError) {
				case 1:
					if(ItemNameOrBarcode == true) {
						System.out.print(">> 이름 : ");
						this.selectItemName = scanner.next();
						for(int i = 0; i < totalItemType; i++) {
							if(this.stockDB[i].name.equals(this.selectItemName)) {
								checkItem = i;
								checkError = 2;
								break;
							}
						}
					} else {
						System.out.print(">> 바코드 번호 : ");
						this.selectBarcode = scanner.nextInt();
						for(int i = 0; i < totalItemType; i++) {
							if(this.stockDB[i].barcode == this.selectBarcode) {
								checkItem = i;
								checkError = 2;
								break;
							}
						}
					}
				case 2:
					if(this.stockDB[checkItem].forAdults.equals("Y")) {
						System.out.println("\nㅇ 만 19세 미만 판매 금지 품목입니다.");
						System.out.println("ㅇ 생년월일을 입력해 주십시오. (형식 : 2000-01-01)");
						System.out.print(">> 생년월일 : ");
						this.residentRegistrationNumber = scanner.next();
						if(Pattern.matches(this.residentRegistrationNumberPattern, this.residentRegistrationNumber)) {
							String birthYear = residentRegistrationNumber.substring(0, 4);
							String birthMonth = residentRegistrationNumber.substring(5, 7);
							String birthDay = residentRegistrationNumber.substring(8, 10);
							int age = getAge(Integer.parseInt(birthYear), Integer.parseInt(birthMonth), Integer.parseInt(birthDay));
							
							if(age >= 19) {
								System.out.println("\nㅇ 성인 인증이 완료되었습니다.\n");
								checkError = 3;
							} else {
								System.out.println("\nㅇ 미성년자로 확인 되었습니다.");
								System.out.println("ㅇ 메인 메뉴로 돌아갑니다.");
								run = false;
							}

						} else {
							System.out.println("\n!! 잘못된 입력입니다. (형식 : 2000-01-01)");
						}
						break;
					} else if (this.stockDB[checkItem].forAdults.equals("N")) {
						checkError = 3;
					} else if (checkItem == 0) {
						System.out.print("\n재고 목록에 없는 이름입니다. 다시 입력해주 십시오.\n");
						break;
					} else {
						System.out.println("\noutgoingItemName -> switch(checkError) -> case 1: Error\n");
						break;
					}
				case 3:
					System.out.println("---------------------------------------------------------");
					System.out.println("  [ " + (checkItem + 1) + "번 물품 ]");
					System.out.println("---------------------------------------------------------");
					System.out.println("  ㅇ 이름 : " + this.stockDB[checkItem].name);
					System.out.println("  ㅇ 바코드 : " + this.stockDB[checkItem].barcode);
					System.out.println("  ㅇ 가격 : " + this.stockDB[checkItem].price);
					System.out.println("  ㅇ 개수 : " + this.stockDB[checkItem].numberOfItem);
					System.out.println("  ㅇ 총 가격 : " + this.stockDB[checkItem].sumOfPrice);
					System.out.println("  ㅇ 생산지 : " + this.stockDB[checkItem].placeOfProduction);
					System.out.println("  ㅇ 폐기 날짜 : " + this.stockDB[checkItem].disposalDate);
					System.out.println("  ㅇ 성인용 유무 : " + this.stockDB[checkItem].forAdults);
					System.out.println("---------------------------------------------------------");
					System.out.println();
					checkError = 4;
				case 4:
					System.out.print(">> 구매하실 개수 : ");
					this.selectnumberOfItem = scanner.nextInt();
					if(this.selectnumberOfItem <= this.stockDB[checkItem].numberOfItem) {
						System.out.println("\nㅇ 입력하신 개수는 " + this.selectnumberOfItem + "개 입니다.\n");
					} else if(this.selectnumberOfItem > this.stockDB[checkItem].numberOfItem) {
						System.out.println("\nㅇ 입력하신 개수가 현재 재고를 초과합니다.");
						System.out.println("ㅇ 다시 입력해 주십시오.\n");
						break;
					} else if(this.selectnumberOfItem == 0) {
						System.out.println("\nㅇ 입력하신 개수가 0개입니다.");
						System.out.println("ㅇ 다시 입력해 주십시오.\n");
						break;
					} else {
						System.out.println("\nㅇ 입력 오류입니다.");
						System.out.println("ㅇ 다시 입력해 주십시오.\n");
						break;
					}
					payingCash = this.selectnumberOfItem * this.stockDB[checkItem].price;
					checkError = 5;
				case 5:
					System.out.println(">> 가격은  : " + payingCash + " 원입니다.");
					System.out.println(">> 결제 수단을 선택해주십시오.\n");
					cardOrCash();
					this.stockDB[checkItem].numberOfItem -= this.selectnumberOfItem;
					this.stockDB[checkItem].sumOfPrice -= (this.selectnumberOfItem * this.stockDB[checkItem].price);
					System.out.println("\nㅇ " + this.stockDB[checkItem].name + " " + this.selectnumberOfItem + "개 출고 완료되었습니다!");
					
					// 품목 재고가 모두 팔리면 품목 자동 삭제
					if(this.stockDB[checkItem].numberOfItem == 0) {
						if(this.stockDB[checkItem + 1].barcode != 0) {
							for(int i = checkItem; checkItem < this.stockDB.length - 1; i++) {
								this.stockDB[i].name = this.stockDB[i + 1].name;
//								this.stockDB[i].barcode = this.stockDB[i + 1].barcode;
								this.stockDB[i].price =  this.stockDB[i + 1].price;
								this.stockDB[i].numberOfItem = this.stockDB[i + 1].numberOfItem;
								this.stockDB[i].sumOfPrice = this.stockDB[i + 1].sumOfPrice;
								this.stockDB[i].placeOfProduction = this.stockDB[i + 1].placeOfProduction;
								this.stockDB[i].disposalDate = this.stockDB[i + 1].disposalDate;
								this.stockDB[i].forAdults = this.stockDB[i + 1].forAdults;
							}
						} else {
							this.stockDB[checkItem].name = null;
							this.stockDB[checkItem].barcode = 0;
							this.stockDB[checkItem].price =  0;
							this.stockDB[checkItem].numberOfItem = 0;
							this.stockDB[checkItem].sumOfPrice = 0;
							this.stockDB[checkItem].placeOfProduction = null;
							this.stockDB[checkItem].disposalDate = null;
							this.stockDB[checkItem].forAdults = null;
						}
						this.totalItemType--;
						System.out.println("\n\t물품 재고가 없습니다.");
					}
					checkError = 6;
					break;
				case 6:
					System.out.println("\nㅇ 현재 총 물품 종류는 " + this.totalItemType + "가지입니다.");
					System.out.print("ㅇ 물품을 더 출고하시겠습니까?(Y/N) ");
					this.selectYesOrNo = scanner.next();
					if(this.selectYesOrNo.equals("Y")) {
						checkError = 1;
						break;
					} else if(this.selectYesOrNo.equals("N")) {
						System.out.println("\nㅇ 물품 판매를 종료합니다.");
						System.out.println("\nㅇ 메인 메뉴로 돌아갑니다.");
						run = false;
					} else {
						System.out.println("\n잘못된 입력입니다. (입력값 : Y / N)\n");
						break;
					}
					checkError = 7;
				default:
					checkError = 1;
					run = false;
				}
			} catch (Exception e) {
				scanner = new Scanner(System.in);
				System.out.println("\n잘못된 입력입니다. 다시 입력해 주십시오.\n");
				e.printStackTrace();
			}
		}
	}

	//재고를 파악하기 위한 메소드
	@Override
	public void checkStock() {
		System.out.println(">> 5.재고 파악\n");
		
		this.totalItemPrice = 0;
		
		if(totalItemType == 0) {
			System.out.println("\t물품 재고가 없습니다.\n");
		} else {
			for(int i = 0; i < totalItemType; ++i) {
				System.out.println("---------------------------------------------------------");
				System.out.println("  [ " + (i + 1) + "번 물품 ]");
				System.out.println("---------------------------------------------------------");
				System.out.println("  ㅇ 이름 : " + this.stockDB[i].name);
				System.out.println("  ㅇ 바코드 : " + this.stockDB[i].barcode);
				System.out.println("  ㅇ 가격 : " + this.stockDB[i].price);
				System.out.println("  ㅇ 개수 : " + this.stockDB[i].numberOfItem);
				System.out.println("  ㅇ 총 가격 : " + this.stockDB[i].sumOfPrice);
				System.out.println("  ㅇ 생산지 : " + this.stockDB[i].placeOfProduction);
				System.out.println("  ㅇ 폐기 날짜 : " + this.stockDB[i].disposalDate);
				System.out.println("  ㅇ 성인용 유무 : " + this.stockDB[i].forAdults);
				System.out.println("---------------------------------------------------------");
				System.out.println();
				
				this.totalItemPrice += this.stockDB[i].sumOfPrice;
			}
		}

		DecimalFormat mkComma = new DecimalFormat("#,###");
        this.totalItemPriceComma = (String)mkComma.format(this.totalItemPrice);
		
        System.out.println("---------------------------------------------------------");
		System.out.println("\t< 총 물품 종류 : " + this.totalItemType + "가지 >");
		System.out.println("---------------------------------------------------------");
		System.out.println("\t< 총 물품 가격 : " + this.totalItemPriceComma + " 원 >");
		System.out.println("---------------------------------------------------------");
	}

	//현재 급료를 계산하기 위한 메소드
	@Override
	public String salaryCalculation(long startTime) {
		long endTime = System.currentTimeMillis();
        
        //근무 시간(MilliSecond)을 시, 분, 초로 저장
        int second = (int) ((endTime - startTime) / 1000) % 60;
        int minute = (int) ((endTime - startTime) / 1000 / 60) % 60;
        int hour = (int) ((endTime - startTime) / 1000 / 60 / 60) + 8;
        
        DecimalFormat mkComma = new DecimalFormat("#,###");
        salary = (String)mkComma.format((hour * 9800));
        
        System.out.println("ㅇ 근무 시간 : " + hour + "시간 " + minute + "분 " + second + "초");
        System.out.println("\nㅇ 최저 시급 : 9,800원");
        
		return salary;
	}

	//PassWord를 변경하기 위한 메소드
	@Override
	public void changePassword() {
		while(run) {
			try {
				System.out.println("ㅇ 기존의 패스워드를 입력해 주십시오.");
				System.out.print("PassWord : ");
				this.changePassword1 = scanner.next();
				if(this.passWord.equals(this.changePassword1)) {
					break;
				} else {
					System.out.println("\n잘못된 패스워드입니다. 다시 입력해 주십시오.\n");
				}
			} catch (Exception e) {
				scanner = new Scanner(System.in);
				System.out.println("\n잘못된 입력입니다. 다시 입력해 주십시오.\n");
			}
		}
		while(run) {
			try {
				System.out.println("\nㅇ 변경하실 패스워드를 입력해 주십시오.");
				System.out.print("PassWord1 : ");
				this.changePassword1 = scanner.next();
				System.out.println("\nㅇ 한번 더 입력해 주십시오.");
				System.out.print("PassWord2 : ");
				this.changePassword2 = scanner.next();
				if(this.changePassword1.equals(this.changePassword2)) {
					this.passWord = this.changePassword1;
					this.changePassword1 = null;
					this.changePassword2 = null;
					System.out.println("\n암호 변경이 완료되었습니다!\n");
					break;
				} else {
					System.out.println("\n입력하신 패스워드가 동일하지 않습니다.");
					System.out.println("다시 입력해주십시오.\n");
				}
			} catch (Exception e) {
				scanner = new Scanner(System.in);
				System.out.println("\n잘못된 입력입니다. 다시 입력해 주십시오.\n");
			}
		}
	}
	
	//사용자를 변경하기 위한 메소드
	@Override
	public void changeUserName() {
		while(run) {
			try {
				System.out.println("ㅇ 현재 사용자는 " + this.userName + "님 입니다.");
				System.out.print("ㅇ 사용자를 변경하시겠습니까?(Y/N) ");
				this.changePassword1 = scanner.next();
				if(this.changePassword1.equals("Y")) {
					break;
				} else if(this.changePassword1.equals("N")) {
					System.out.println();
					run = false;
				} else {
					System.out.println("\n잘못된 입력입니다. (입력값 : Y / N)\n");
				}
			} catch (Exception e) {
				scanner = new Scanner(System.in);
				System.out.println("\n잘못된 입력입니다. 다시 입력해 주십시오.\n");
			}
		}
		while(run) {
			try {
				System.out.println("\nㅇ 변경하실 사용자를 입력해 주십시오.");
				System.out.print("사용자 이름 : ");
				this.changePassword1 = scanner.next();
				System.out.println("\nㅇ 한번 더 입력해 주십시오.");
				System.out.print("사용자 이름 : ");
				this.changePassword2 = scanner.next();
				if(this.changePassword1.equals(this.changePassword2)) {
					this.userName = this.changePassword1;
					this.changePassword1 = null;
					this.changePassword2 = null;
					System.out.println("\n사용자 변경이 완료되었습니다!\n");
					run = false;
				} else {
					System.out.println("\n입력하신 사용자가 동일하지 않습니다.");
					System.out.println("다시 입력해주십시오.\n");
				}
			} catch (Exception e) {
				scanner = new Scanner(System.in);
				System.out.println("\n잘못된 입력입니다. 다시 입력해 주십시오.\n");
			}
		}
		run = true;
	}

	//5번 암호 변경 메뉴에서 사용자와 PassWord 중 변경할 것을 선택하기 위한 메소드
	@Override
	public void selectChangePasswordOrUserName() {
		System.out.println(">> 7.암호 변경\n");
		while(run) {
			try {
				System.out.println("---------------------------------------------------------");
				System.out.println("\t1.사용자 변경 | 2.암호 변경 | 3.메인 메뉴");
				System.out.println("---------------------------------------------------------");
				System.out.print("\n# 번호 입력 : ");
	 
	            selectNo = scanner.nextInt();
	            
	            System.out.println();
	 
	            if(selectNo == 1) {
	            	System.out.println(">> 5-(1).사용자 변경\n");
	            	changeUserName();
		        } else if(selectNo == 2) {
		        	System.out.println(">> 5-(2).암호 변경\n");
		        	changePassword();
		        } else if(selectNo == 3) {
		        	System.out.println(">> 5-(3).메인 메뉴");
		        	break;
		        } else {
		        	System.out.println("잘못 입력하셨습니다. 다시 입력하세요! (입력값 : 1, 2, 3)\n");
		        }
			} catch (Exception e) {
				scanner = new Scanner(System.in);
				System.out.println("\n잘못 입력하셨습니다. (정수만 입력 가능)\n");
			}
		}
	}
	
	//물품을 이름이나 바코드 숫자로 종류별 검색하기 위한 메소드
	@Override
	public void itemSearch() {
		System.out.println(">> 3.물품 검색\n");
		
		if(totalItemType == 0) {
			System.out.println("\t물품 재고가 없습니다.");
			run = false;
		}
		
		while(run) {
			try {
				System.out.println("ㅇ 현재 총 물품 종류는 " + this.totalItemType + "가지입니다.");
				System.out.print("ㅇ 검색하시겠습니까?(Y/N) ");
				this.changePassword1 = scanner.next();
				if(this.changePassword1.equals("Y")) {
					break;
				} else if(this.changePassword1.equals("N")) {
					run = false;
				} else {
					System.out.println("\n잘못된 입력입니다. (입력값 : Y / N)\n");
				}
			} catch (Exception e) {
				scanner = new Scanner(System.in);
				System.out.println("\n잘못된 입력입니다. 다시 입력해 주십시오.\n");
			}
		}
		
		while(run) {
			try {
				System.out.println("\n---------------------------------------------------------");
				System.out.println("\t1.이름으로 찾기 | 2.바코드 번호로 찾기");
				System.out.println("---------------------------------------------------------");
				System.out.print("\n# 번호 입력 : ");
	 
	            selectNo = scanner.nextInt();
	            
	            System.out.println();
	 
	            if(selectNo == 1) {
	            	System.out.println(">> 1.이름으로 찾기\n");
	            	ItemNameOrBarcode = true;
	            	searchItemNameOrBarcode(ItemNameOrBarcode);
		        } else if(selectNo == 2) {
		        	System.out.println(">> 2.바코드 번호로 찾기\n");
		        	ItemNameOrBarcode = false;
		        	searchItemNameOrBarcode(ItemNameOrBarcode);
		        } else {
		        	System.out.println("잘못 입력하셨습니다. 다시 입력하세요! (입력값 : 1, 2)\n");
		        }
			} catch (Exception e) {
				scanner = new Scanner(System.in);
				System.out.println("\n잘못 입력하셨습니다. (정수만 입력 가능)\n");
			}
		}
		
		run = true;
	}

	//입력하는 이름이나 바코드 숫자에 해당하는 상품 정보를 삭제하기 위한 메소드
	@Override
	public void deleteItem() {
		System.out.println(">> 4.물품 삭제\n");
		
		if(totalItemType == 0) {
			System.out.println("\t물품 재고가 없습니다.");
			run = false;
		}
		
		while(run) {
			try {
				System.out.println("ㅇ 현재 총 물품 종류는 " + this.totalItemType + "가지입니다.");
				System.out.print("ㅇ 삭제하시겠습니까?(Y/N) ");
				this.changePassword1 = scanner.next();
				if(this.changePassword1.equals("Y")) {
					break;
				} else if(this.changePassword1.equals("N")) {
					run = false;
				} else {
					System.out.println("\n잘못된 입력입니다. (입력값 : Y / N)\n");
				}
			} catch (Exception e) {
				scanner = new Scanner(System.in);
				System.out.println("\n잘못된 입력입니다. 다시 입력해 주십시오.\n");
			}
		}
		
		while(run) {
			try {
				System.out.println("\n---------------------------------------------------------");
				System.out.println("\t1.이름으로 삭제하기 | 2.바코드 번호로 삭제하기");
				System.out.println("---------------------------------------------------------");
				System.out.print("\n# 번호 입력 : ");
	 
	            selectNo = scanner.nextInt();
	            
	            System.out.println();
	 
	            if(selectNo == 1) {
	            	System.out.println(">> 1.이름으로 삭제하기\n");
	            	ItemNameOrBarcode = true;
	            	deleteItemNameOrBarcode(ItemNameOrBarcode);
		        } else if(selectNo == 2) {
		        	System.out.println(">> 2.바코드 번호로 삭제하기\n");
		        	ItemNameOrBarcode = false;
		        	deleteItemNameOrBarcode(ItemNameOrBarcode);
		        } else {
		        	System.out.println("잘못 입력하셨습니다. 다시 입력하세요! (입력값 : 1, 2)\n");
		        }
			} catch (Exception e) {
				scanner = new Scanner(System.in);
				System.out.println("\n잘못 입력하셨습니다. (정수만 입력 가능)\n");
			}
		}
		
		run = true;
	}
	
	//2번 출고에서 성인인증을 위해 만 나이를 구하는 메소드
	public int getAge(int birthYear, int birthMonth, int birthDay) {
		Calendar current = Calendar.getInstance();
		
		int currentYear = current.get(Calendar.YEAR);
		int currentMonth = current.get(Calendar.MONTH) + 1;
		int currentDay = current.get(Calendar.DAY_OF_MONTH);
		
		int age = currentYear - birthYear;
		
		if(birthMonth * 100 + birthDay > currentMonth * 100 + currentDay) {
			age--;
		}
		
		return age;
	}
	
	//2번 출고에서 결제 방법을 선택하기 위한 메소드
	public void cardOrCash() {
		while(run) {
			try {
				System.out.println("---------------------------------------------------------");
				System.out.println("\t1.카드 | 2.현금 | 3.결제 취소");
				System.out.println("---------------------------------------------------------");
				System.out.print("\n# 번호 입력 : ");
	 
	            selectNo = scanner.nextInt();
	            
	            System.out.println();
	 
	            if(selectNo == 1) {
	            	System.out.println(">> 2-(1).카드\n");
	            	payingCreditCard();
		        } else if(selectNo == 2) {
		        	System.out.println(">> 2-(2).현금\n");
		        	cashPayment();
		        } else if(selectNo == 3) {
		        	System.out.println(">> 2-(3).결제 취소");
		        	System.out.println("\nㅇ 결제가 취소되었습니다.");
		        	System.out.println("ㅇ 메인 메뉴로 돌아갑니다.\n");
		        	run = false;
		        } else {
		        	System.out.println("잘못 입력하셨습니다. 다시 입력하세요! (입력값 : 1, 2, 3)\n");
		        }
			} catch (Exception e) {
				scanner = new Scanner(System.in);
				System.out.println("\n잘못 입력하셨습니다. (정수만 입력 가능)\n");
			}
		}
	}
	
	//카드 결제 메소드
	public void payingCreditCard() {
		System.out.println("ㅇ 카드 결제가 완료되었습니다!");
		System.out.println("ㅇ 이용해 주셔서 감사합니다.");
		run = false;
	}
	
	//현금 결제 메소드
	public void cashPayment() {
		while(run) {
			try {
				System.out.print("ㅇ 지불하실 금액을 입력해 주십시오 : ");
				this.customerCash = scanner.nextInt();
				if(customerCash < payingCash) {
					System.out.println("\nㅇ 지불하신 금액이 " + (payingCash - customerCash) + "원 부족합니다.");
					System.out.println("ㅇ 다시 입력해 주십시오.\n");
				} else if(customerCash >= payingCash) {
					System.out.println("\nㅇ 현금 결제가 완료되었습니다!");
					System.out.println("ㅇ 거스름돈은 " + (customerCash- payingCash) + "원 입니다.");
					System.out.println("ㅇ 이용해 주셔서 감사합니다.");
					run = false;
				} else {
					System.out.print("\nㅇ 입력 오류입니다.");
					System.out.print("ㅇ 다시 입력해 주십시오.\n");
				}
			} catch (Exception e) {
				scanner = new Scanner(System.in);
				System.out.println("\n잘못 입력하셨습니다. 정확한 금액을 입력 부탁드립니다.\n");
			}
		}
	}
	
	//물품을 이름이나 바코드 숫자로 종류별 검색한 후 해당 데이터를 출력하는 메소드
	public void searchItemNameOrBarcode(boolean ItemNameOrBarcode) {
		int checkItem = 0;
		while(run) {
			try {
				switch(checkError) {
				case 1:
					if(ItemNameOrBarcode == true) {
						System.out.print(">> 이름 : ");
						this.selectItemName = scanner.next();
						for(int i = 0; i < totalItemType; i++) {
							if(this.stockDB[i].name.equals(selectItemName)) {
								checkItem = i;
								checkError = 2;
								break;
							}
						}
					} else {
						System.out.print(">> 바코드 번호 : ");
						this.selectBarcode = scanner.nextInt();
						for(int i = 0; i < totalItemType; i++) {
							if(this.stockDB[i].barcode == selectBarcode) {
								checkItem = i;
								checkError = 2;
								break;
							}
						}
					}
				case 2:
					System.out.println("\n---------------------------------------------------------");
					System.out.println("  [ " + (checkItem + 1) + "번 물품 ]");
					System.out.println("---------------------------------------------------------");
					System.out.println("  ㅇ 이름 : " + this.stockDB[checkItem].name);
					System.out.println("  ㅇ 바코드 : " + this.stockDB[checkItem].barcode);
					System.out.println("  ㅇ 가격 : " + this.stockDB[checkItem].price);
					System.out.println("  ㅇ 개수 : " + this.stockDB[checkItem].numberOfItem);
					System.out.println("  ㅇ 총 가격 : " + this.stockDB[checkItem].sumOfPrice);
					System.out.println("  ㅇ 생산지 : " + this.stockDB[checkItem].placeOfProduction);
					System.out.println("  ㅇ 폐기 날짜 : " + this.stockDB[checkItem].disposalDate);
					System.out.println("  ㅇ 성인용 유무 : " + this.stockDB[checkItem].forAdults);
					System.out.println("------------------------------------------------------");
					checkError = 3;
				case 3:
					System.out.println("\nㅇ 현재 총 물품 종류는 " + this.totalItemType + "가지입니다.");
					System.out.print("ㅇ 물품 검색을 더 하시겠습니까?(Y/N) ");
					this.selectYesOrNo = scanner.next();
					System.out.println();
					if(this.selectYesOrNo.equals("Y")) {
						checkError = 1;
						break;
					} else if(this.selectYesOrNo.equals("N")) {
						System.out.println("\nㅇ 물품 검색을 종료합니다.");
						System.out.println("ㅇ 메인 메뉴로 돌아갑니다.");
						run = false;
					} else {
						System.out.println("\n잘못된 입력입니다. (입력값 : Y / N)\n");
						break;
					}
					checkError = 4;
				default:
					checkError = 1;
					run = false;
				}
			} catch (Exception e) {
				scanner = new Scanner(System.in);
				System.out.println("\n잘못된 입력입니다. 다시 입력해 주십시오.\n");
				e.printStackTrace();
			}
		}
	}
	
	//입력하는 이름이나 바코드 숫자에 해당하는 상품 정보를 삭제하기 위한 메소드
	public void deleteItemNameOrBarcode(boolean ItemNameOrBarcode) {
		int checkItem = 0;
		while(run) {
			try {
				switch(checkError) {
				case 1:
					if(ItemNameOrBarcode == true) {
						System.out.print(">> 이름 : ");
						this.selectItemName = scanner.next();
						for(int i = 0; i < totalItemType; i++) {
							if(this.stockDB[i].name.equals(selectItemName)) {
								checkItem = i;
								checkError = 2;
								break;
							}
						}
					} else {
						System.out.print(">> 바코드 번호 : ");
						this.selectBarcode = scanner.nextInt();
						for(int i = 0; i < totalItemType; i++) {
							if(this.stockDB[i].barcode == selectBarcode) {
								checkItem = i;
								checkError = 2;
								break;
							}
						}
					}
				case 2:
					System.out.println("---------------------------------------------------------");
					System.out.println("  [ " + (checkItem + 1) + "번 물품 ]");
					System.out.println("---------------------------------------------------------");
					System.out.println("  ㅇ 이름 : " + this.stockDB[checkItem].name);
					System.out.println("  ㅇ 바코드 : " + this.stockDB[checkItem].barcode);
					System.out.println("  ㅇ 가격 : " + this.stockDB[checkItem].price);
					System.out.println("  ㅇ 개수 : " + this.stockDB[checkItem].numberOfItem);
					System.out.println("  ㅇ 총 가격 : " + this.stockDB[checkItem].sumOfPrice);
					System.out.println("  ㅇ 생산지 : " + this.stockDB[checkItem].placeOfProduction);
					System.out.println("  ㅇ 폐기 날짜 : " + this.stockDB[checkItem].disposalDate);
					System.out.println("  ㅇ 성인용 유무 : " + this.stockDB[checkItem].forAdults);
					System.out.println("---------------------------------------------------------");
					checkError = 3;
				case 3:
					System.out.print("\nㅇ 위 물품을 정말 모두 삭제하시겠습니까?(Y/N) ");
					this.selectYesOrNo = scanner.next();
					if(this.selectYesOrNo.equals("Y")) {
						// 해당 품목 삭제 및 배열 이동
						if(this.stockDB[checkItem + 1].barcode != 0) {
							for(int i = checkItem; checkItem < this.stockDB.length - 1; i++) {
								this.stockDB[i].name = this.stockDB[i + 1].name;
//								this.stockDB[i].barcode = this.stockDB[i + 1].barcode;
								this.stockDB[i].price =  this.stockDB[i + 1].price;
								this.stockDB[i].numberOfItem = this.stockDB[i + 1].numberOfItem;
								this.stockDB[i].sumOfPrice = this.stockDB[i + 1].sumOfPrice;
								this.stockDB[i].placeOfProduction = this.stockDB[i + 1].placeOfProduction;
								this.stockDB[i].disposalDate = this.stockDB[i + 1].disposalDate;
								this.stockDB[i].forAdults = this.stockDB[i + 1].forAdults;
							}
						} else {
							this.stockDB[totalItemType - 1].name = null;
							this.stockDB[totalItemType - 1].barcode = 0;
							this.stockDB[totalItemType - 1].price =  0;
							this.stockDB[totalItemType - 1].numberOfItem = 0;
							this.stockDB[totalItemType - 1].sumOfPrice = 0;
							this.stockDB[totalItemType - 1].placeOfProduction = null;
							this.stockDB[totalItemType - 1].disposalDate = null;
							this.stockDB[totalItemType - 1].forAdults = null;
						}
						this.totalItemType--;
						System.out.println("\nㅇ 해당 물품이 삭제 완료되었습니다.");
					} else if(this.selectYesOrNo.equals("N")) {
						System.out.println("\nㅇ 물품 삭제를 종료합니다.");
						System.out.println("ㅇ 메인 메뉴로 돌아갑니다.");
						run = false;
					} else {
						System.out.println("\n잘못된 입력입니다. (입력값 : Y / N)\n");
					}
					checkError = 3;
				case 4:
					System.out.println("\nㅇ 현재 총 물품 종류는 " + this.totalItemType + "가지입니다.");
					System.out.print("ㅇ 물품을 더 삭제하시겠습니까?(Y/N) ");
					this.selectYesOrNo = scanner.next();
					System.out.println();
					if(this.selectYesOrNo.equals("Y")) {
						checkError = 1;
						break;
					} else if(this.selectYesOrNo.equals("N")) {
						System.out.println("\nㅇ 물품 삭제를 종료합니다.");
						System.out.println("ㅇ 메인 메뉴로 돌아갑니다.");
						run = false;
					} else {
						System.out.println("\n잘못된 입력입니다. (입력값 : Y / N)\n");
						break;
					}
					checkError = 5;
				default:
					checkError = 1;
					run = false;
				}
			} catch (Exception e) {
				scanner = new Scanner(System.in);
				System.out.println("\n잘못된 입력입니다. 다시 입력해 주십시오.\n");
				e.printStackTrace();
			}
		}
	}

}
