package TestProject;

import java.util.Scanner;

public class ini {

	public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        int size = 0;
        int lengthMax = 3;
        String chr = null;
        boolean validInput = false;
        
        while(!validInput) {
        System.out.println("整数を入力してください。");
        String input = scanner.nextLine();
        try {
        		size = Integer.parseInt(input);
        		validInput = true;
            } catch (NumberFormatException e) {
                System.out.println("入力が数値または整数ではありません。");
                System.out.println("入力をやり直してください。");
            }
        }
        
        validInput = false;
        
        while(!validInput) {
            System.out.println("表示する文字を入力してください。（3文字まで）");
            String input = scanner.nextLine();
            if(input.length() <= lengthMax) {
            	chr = input;
            	validInput = true;
            	}
            else {
            	System.out.println("入力が3文字を超えています。");
                System.out.println("入力をやり直してください。");
                }
            
            }
        
        scanner.close();
        
        Out(size,chr);
	}
	static void Out(int size,String chr) {
		for(int i = 0; i < size+1; i++) {
        	for(int x = 0; x < i; x++) {
        		System.out.print(chr);
        	}
        	System.out.println("");
        }
	}

}
