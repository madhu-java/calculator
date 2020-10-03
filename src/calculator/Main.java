package calculator;

import java.util.*;

public class Main {
    static Map<String,Integer> map= new HashMap<>();
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
                    System.out.println("The program calculates the sum of numbers" +
                            "expressions like these: 4 + 6 - 8, 2 - 3 - 4, " +
                            "this way: 2 -- 2 equals 2 - (-2) equals 2 + 2.");
                    break;
                case "":
                    break;

                default:
                    if (input.matches("^/[a-z]+")) {
                        System.out.println("Unknown command");
                        break;
                    }else if(input.trim().matches("[a-zA-Z0-9.]+\\s*=\\s*[0-9a-zA-Z]+")){
                   // System.out.println("inside variable value check");

                        int value=0;
                        String key="";
                        String[] variableValue = input.split("\\s*=\\s*");
                        if(variableValue.length==2) {
                            if(variableValue[0].trim().matches("[a-zA-Z]+")) {
                               // System.out.println("matches [a-zA-Z]+");
                                key = variableValue[0].trim();
                            }else{
                                System.out.println("Invalid identifier");
                                //System.out.println("does not matches [a-zA-Z]+");
                                break;
                            }
                            if(variableValue[1].trim().matches("[0-9]+")) {
                                 value = Integer.parseInt(variableValue[1].trim());

                              //  System.out.println("matches 0-9");
                            }else if(variableValue[1].trim().matches("[a-zA-Z]+")){
                               // System.out.println("matches a-Z");
                               if( map.containsKey(variableValue[1].trim())){
                                   value=map.get(variableValue[1].trim());
                               }else{
                                   System.out.println("Unknown variable");
                                   break;
                               }
                        }else{
                                System.out.println("Invalid assignment");
                                break;
                            }

                        }
                        map.put(key,value);
                      //  map.forEach((k,v)-> System.out.println(k+" "+v));
                }else if(input.matches("(\\w+\\s*?)+(\\s?[+-]*\\s?\\w+)*")) {
                       // \w+(\s?[+-]\s?\w)*
                       // System.out.println("inside expression check");
                        Deque<String> stack = new ArrayDeque<>();
                        addSymbolsToStack(input, stack);
                        getTotalByReadingSymbolsFromStack(stack);
                    }else
                    {
                        System.out.println("invalid");
                    }
                    break;
            }
        }
    }

    private static void getTotalByReadingSymbolsFromStack(Deque<String> stack) {
        try {
           // System.out.println("stack "+stack);
            int total = Integer.parseInt(stack.pollLast());
           // System.out.println("totoal = "+total);
            while (!stack.isEmpty()) {
               // System.out.println("inside stack trace");
  //System.out.println(stack);
                String symbol = stack.pollLast();
                if (symbol.equals("-")) {

                    total = total - Integer.parseInt(stack.pollLast());
                } else if (symbol.equals("+")) {
                    total = total + Integer.parseInt(stack.pollLast());

                } else {
                    throw new NumberFormatException();
                }

            }
            System.out.println(total);
        } catch (Exception e) {
            System.out.println("Unknown variable");
        }
    }

    private static void addSymbolsToStack(String input, Deque<String> stack) {
        try {
            //System.out.println("input "+input);
            String[] symbols = input.split("\\s+");
            int sum = 0;
            //System.out.println("adding to stack");
            //int digit = 1;
            for (String s : symbols) {
                int digit = 1;
                //if a string matches - or + multiply them with 1 and mutliply it eith next int(converted fom string)
                if (s.trim().matches("[+-]+")) {
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
                       //  System.out.println(stack);
                    }
                } else {
                    if(s.trim().matches("\\d+")) {
                       // System.out.println("insidde adding stack \\d");
                        stack.push(s);
                    }else if(s.matches(("[a-zA-Z]+"))) {
                       // System.out.println("insidde adding stack [a-z");
                        boolean variableExists = map.containsKey(s);
                        if (variableExists) {
                            stack.push(String.valueOf(map.get(s)));
                        }
                    }else {
                     System.out.println("stack in add "+stack);
                        System.out.println("Unknown variable");
throw new NumberFormatException();
                }


            }}

        } catch (NumberFormatException e) {
            System.out.println("Invalid expression");
        }
    }
}