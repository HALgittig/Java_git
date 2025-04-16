package TestProject;

import java.util.Scanner;

public class CoinCase {

	public static void main(String[] args) {
		
		Case ce = new Case();
		Scanner scanner = new Scanner(System.in);
		int kinds = 0;
		int count = 0;
        boolean validInput = false;
        
        for(int i = 0; i < 10; i++) {
        	while(!validInput) {
        		System.out.println("硬貨の種類を入力してください。");
        		String input = scanner.nextLine();
        		try {
        			kinds = Integer.parseInt(input);
            		validInput = true;
        		} catch (NumberFormatException e) {
                    System.out.println("入力値が有効ではありません。");
                    System.out.println("入力をやり直してください。");
                }
            }
        
        	validInput = false;
        
        	while(!validInput) {
        		System.out.println("枚数を入力してください。");
        		String input = scanner.nextLine();
        		try {
            		count = Integer.parseInt(input);
            		validInput = true;
                } catch (NumberFormatException e) {
                    System.out.println("入力値が有効ではありません。");
                    System.out.println("入力をやり直してください。");
                }
            }
        	ce.AddCoins(kinds, count);
        	validInput = false;
        }
        while(!validInput) {
    		System.out.println("検索する硬貨の種類を入力してください。");
    		String input = scanner.nextLine();
    		try {
    			kinds = Integer.parseInt(input);
    			System.out.println("硬貨の総数は、" + ce.GetCount() + "枚です。");
    	        System.out.println(kinds + "円硬貨は、" + ce.GetAmount(kinds) + "枚で、" + kinds * ce.GetAmount(kinds) + "円です。");
    	        System.out.println("総額は、" + ce.total + "円です。");
        		validInput = true;
    		} catch (NumberFormatException e) {
                System.out.println("入力値が有効ではありません。");
                System.out.println("入力をやり直してください。");
            }
        }
        
        scanner.close();
	}
}
class Case{
	int yen500 = 0;
	int yen100 = 0;
	int yen50 = 0;
	int yen10 = 0;
	int yen5 = 0;
	int yen1 = 0;
	int total = 0;
	
	public void AddCoins(int kinds,int count) {
		switch(kinds) {
		case 500:
			yen500 += count;
			break;
		case 100:
			yen100 += count;
			break;
		case 50:
			yen50 += count;
			break;
		case 10:
			yen10 += count;
			break;
		case 5:
			yen5 += count;
			break;
		case 1:
			yen1 += count;
			break;
		}
	}
	public int GetCount() {

		return (yen500 + yen100 + yen50 + yen10 + yen5 + yen1);
	}
	public int GetAmount(int kinds) {
		total = (500*yen500 + 100*yen100 + 50*yen50 + 10*yen10 + 5*yen5 + 1*yen1);
		
		switch(kinds) {
		case 500:
			return yen500;
		case 100:
			return yen100;
		case 50:
			return yen50;
		case 10:
			return yen10;
		case 5:
			return yen5;
		case 1:
			return yen1;
		}
		return 0;
	}
	
}
