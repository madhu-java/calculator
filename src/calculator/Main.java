package calculator;

import java.util.ArrayDeque;
import java.util.Deque;
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
                    System.out.println("The program calculates the sum of numbers"+
                            "expressions like these: 4 + 6 - 8, 2 - 3 - 4, "+
                            "this way: 2 -- 2 equals 2 - (-2) equals 2 + 2.");
                    break;
                case "":
                    break;

                default:
                    if (input.matches("^/[a-z]+")) {
                        System.out.println("Unknown command");
                        break;
                    }

                    try {
                        String[] symbols = input.split("\\s+");
                        int sum = 0;
                        Deque<String> stack = new ArrayDeque<>();
                        //int digit = 1;
                        for (String s : symbols) {
                           int digit =1;
                            //if a string matches - or + multiply them with 1 and mutliply it eith next int(converted fom string)
                            if (s.matches("[+-]+")) {
                                for (int i = 0; i < s.length(); i++) {
                                    String operation = String.valueOf(s.charAt(i));
                                    //System.out.println("operation "+operation);
                                    if (operation.equals("+")) {
                                        digit *= 1;
                                    } else if (operation.equals("-")) {
                                        digit *= -1;
                                    }
                                }
                                if (digit == 1) {
                                    stack.push("+");
                                } else {
                                    stack.push("-");
                                  // System.out.println(stack);
                                }
                            } else {
                                stack.push(s);
                             // System.out.println(stack);

                            }

                        }

                            int total = Integer.parseInt(stack.pollLast());

                            while (!stack.isEmpty()) {
                              //  System.out.println(stack);
                                String symbol = stack.pollLast();
                                if (symbol.equals("-")) {

                                    total = total - Integer.parseInt(stack.pollLast());
                                } else if (symbol.equals("+")) {
                                    total = total + Integer.parseInt(stack.pollLast());

                                }else{
                                    throw new NumberFormatException();
                                }

                            }
                            System.out.println(total);

                    }catch (NumberFormatException e) {
                        System.out.println("Invalid expression");
                    }
                    break;
            }
        }
    }
}
