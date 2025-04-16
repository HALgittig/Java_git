package TestProject;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class puch {

	public static void main(String[] args) {
		
		Scanner scanner = new Scanner(System.in);
		int num_Max = 0;
		int num_Min = 0;
        boolean validInput = false;
        
        while(!validInput) {
        	System.out.println("素数を判定する範囲を決めます。");
            System.out.println("初期値を整数で入力してください。");
            String input = scanner.nextLine();
            try {
            		num_Min = Integer.parseInt(input);
            		validInput = true;
                } catch (NumberFormatException e) {
                    System.out.println("入力が数値または整数ではありません。");
                    System.out.println("入力をやり直してください。");
                }
            }
        
        validInput = false;
        
        while(!validInput) {
            System.out.println("最終値を整数で入力してください。");
            String input = scanner.nextLine();
            try {
            		num_Max = Integer.parseInt(input);
            		validInput = true;
                } catch (NumberFormatException e) {
                    System.out.println("入力が数値または整数ではありません。");
                    System.out.println("入力をやり直してください。");
                }
            }
        
        scanner.close();
        
        System.out.println("素数を出力します。しばらくお待ちください。");
        PrimeNum(num_Max,num_Min);
		
	}
	static void PrimeNum(int max,int min) {
		DecimalFormat df = new DecimalFormat("#,###");
		List<Integer> list = new ArrayList<Integer>();
		boolean[] prime = new boolean[max + 1];
		Arrays.fill(prime, true);
		int loop = 0;
		
		//素数判定にエラトステネスの篩を使用
		if(max >= 0) prime[0] = false;
		if(max >= 1) prime[1] = false;
		for(int i = 2; i * i <= max; i++) {
			if(prime[i]) {
				for(int j = i * i; j <= max; j += i) {
					prime[j] = false;
				}
			}
		}
		for(int i = Math.max(2, min); i <= max; i++) {
			if(prime[i]) {
				list.add(i);
				System.out.print(df.format(i));
				loop++;
				if(loop >= 15) {
					System.out.println();
					loop = 0;
				}
				if(loop > 0) System.out.print(" , ");
			}
		}
			
		System.out.println();
		System.out.print(df.format(min) + " ～ " + df.format(max) + " までの範囲に素数は、"+ df.format(list.size()) +" 個見つかりました。");
	}
}
