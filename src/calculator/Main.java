package calculator;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        // put your code here
        boolean exit = false;
        while (!exit) {
            String input = scanner.nextLine();
            {
                switch (input) {
                    case "/exit":
                        exit = true;
                        System.out.println("Bye!");
                        break;
                    case "/help":

                        System.out.println("The program calculates the sum of numbers");
                        break;
                    case "":
                        break;
                    default:
                        String[] numbers = input.split("\\s");
                        int sum = 0;
                        for (String s : numbers) {
                            sum += Integer.parseInt(s);
                        }
                        System.out.println(sum);

                        break;
                }
            }
        }
    }
}