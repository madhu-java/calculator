package calculator;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        // put your code here
        boolean exit = false;
        while(!exit){
            String input[] = scanner.nextLine().split("\\s");
        int choice = input.length;
        
        switch(choice){
            case 1:
            if(input[0].equals("/exit")){
                exit=true;
                System.out.println("bye");
                break;
            }else if(input[0].equals("")){
                break;
            }else{
            int a = Integer.parseInt(input[0]);
            System.out.println(a);
            }
            break;
        case 2:
        int a = Integer.parseInt(input[0]);
        int b =Integer.parseInt(input[1]);
        System.out.println(a+b);
        break;
    }
        }
}
}
