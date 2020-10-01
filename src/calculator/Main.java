package calculator;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        // put your code here
        boolean exit = false;
        while (!exit) {
            String input = scanner.nextLine();

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
                    String[] symbols = input.split("\\s+");
                    int sum = 0;

                    int digit = 1;
                    for (String s : symbols) {
                        //if a string matches - or + multiply them with 1 and mutliply it eith next int(converted fom string)
                        if (s.matches("[+-]+")) {
                            for (int i = 0; i < s.length(); i++) {
                                String operation = String.valueOf(s.charAt(i));

                                if (operation.equals("+")) {
                                    digit *= 1;
                                } else if (operation.equals("-")) {
                                    digit *= -1;
                                }
                            }
                        } else {
                            sum = sum + digit * Integer.parseInt(s);
                            digit = 1;

                        }

                    }
                    System.out.println(sum);

                    break;
            }
        }
    }
}
