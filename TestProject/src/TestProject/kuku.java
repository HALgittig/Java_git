package TestProject;

import java.util.Scanner;

public class kuku {

	public static void main(String[] args) {
		
		Scanner scanner = new Scanner(System.in);
		int num = 0;
        boolean validInput = false;
		
        while(!validInput) {
        	System.out.println("九九を計算し表示します。");
            String input = scanner.nextLine();
            try {
            		num = Integer.parseInt(input);
            		validInput = true;
                } catch (NumberFormatException e) {
                    System.out.println("入力が数値または整数ではありません。");
                    System.out.println("入力をやり直してください。");
                }
            }
        scanner.close();
        
        //System.out.println(num + "の段を表示します。しばらくお待ちください。");
        Calc(num);
	}
	public static void Calc(int num) {                                                                                                                                                                                                                                                                                                                                                                                                                                                                        
		for(int i = 1; i < 10; i++) {
			for(int j = 1; j < 10; j++) {
				System.out.print(" " + String.format("%3d", num * j) + " |");
			}
			System.out.println();
			num++;
		}
	}
}
