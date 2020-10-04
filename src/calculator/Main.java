package calculator;

import java.math.BigInteger;
import java.util.*;

public class Main {
    static Map<String, BigInteger> map = new HashMap<>();

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
                    System.out.println("The program support for multiplication *, " +
                            "integer division / and parentheses (...). They have a higher priority than addition + " +
                            "and subtraction - 3 + 8 * ((4 + 3) * 2 + 1) - 6 / (2 + 1)" +
                            " The result is 121.");
                    break;
                case "":
                    break;

                default:
                    if (input.matches("^/[a-z]+")) {
                        System.out.println("Unknown command");
                        break;
                    } else if (input.trim().matches("[a-zA-Z0-9.]+\\s*=\\s*?-?[0-9a-zA-Z]+")) {
                        //check if input is a variable assignment
                        BigInteger value = BigInteger.ZERO;
                        String key = "";
                        String[] variableValue = input.split("\\s*=\\s*");
                        if (variableValue.length == 2) {
                            if (variableValue[0].trim().matches("[a-zA-Z]+")) {
                                key = variableValue[0].trim();
                            } else {
                                System.out.println("Invalid identifier");
                                break;
                            }
                            if (variableValue[1].trim().matches("-?[0-9]+")) {
                                value = new BigInteger(variableValue[1].trim());
                            } else if (variableValue[1].trim().matches("[a-zA-Z]+")) {
                                if (map.containsKey(variableValue[1].trim())) {
                                    value = map.get(variableValue[1].trim());
                                } else {
                                    System.out.println("Unknown variable");
                                    break;
                                }
                            } else {
                                System.out.println("Invalid assignment");
                                break;
                            }

                        }
                        map.put(key, value);
                        // map.forEach((k, v) -> System.out.println(k + " " + v));
                    } else if (input.matches("[()-]*?\\w+[()-]*?\\s*?([()]*?\\s*[-+]*?[-+*/]\\s*?[()]*?\\w+[()]*?)+")) {
                        //if input is in infix form
                        ArrayDeque<String> inputStack = converToInputStack(input);
                        //convert to postfix form
                        String prefix = convertToPostFix(inputStack);
                        if (prefix.equals("invalid")) {
                            System.out.println("Invalid");
                        } else {
                            //  System.out.println("prefix " + prefix);
                            //evaluate the post fix and get the result
                            BigInteger result = getResulByEvalPostFix(prefix, map);
                            System.out.println(result);
                        }
                    } else if (input.matches("-?\\d+")) {
                        System.out.println(input);
                    } else if (input.trim().matches("[a-zA-Z]+")) {
                        if (map.containsKey(input.trim())) {
                            System.out.println(map.get(input.trim()));
                        } else {
                            System.out.println("unknown");
                        }

                    } else {
                        System.out.println("invalid");
                    }
                    break;
            }
        }

    }

    //evaluate postfix and return result
    public static BigInteger getResulByEvalPostFix(String input, Map<String, BigInteger> map) {
        int result = 0;
        ArrayDeque<BigInteger> stack = new ArrayDeque<>();
        for (String s : input.split("\\s")) {
            //  System.out.println("current s "+s);
            if (s.matches("\\d+")) {

                stack.push(new BigInteger(s));
            } else if (s.matches("[a-zA-Z]+") && map.containsKey(s.trim())) {
                stack.push(map.get(s.trim()));
            } else if (s.matches("[+*/^-]")) {
                BigInteger a = stack.poll();
                BigInteger b = stack.poll();
                switch (s) {
                    case "+":
                        stack.push(b.add(a));
                        break;
                    case "" +
                            "-":
                        stack.push(b.subtract(a));
                        break;
                    case "/":
                        stack.push(b.divide(a));
                        break;
                    case "*":
                        stack.push(a.multiply(b));
                        break;
                    case "^":
                        // stack.push((int) Math.pow(b, a));
                        break;
                }
            }
            // System.out.println("stack total "+stack);
        }
        return stack.poll();
    }

    public static ArrayDeque<String> converToInputStack(String input) {
        //String[] in = input.split("[/*^+-]{1,}|[()]");
        ArrayDeque<String> inputStack = new ArrayDeque<>();
        for (int i = 0; i < input.length(); i++) {
            String currentChar = String.valueOf(input.charAt(i));
            if (currentChar == " ") {
                continue;
            }
            if (inputStack.isEmpty() || currentChar.matches("[()]|[a-zA-Z]+")) {
                inputStack.addLast(currentChar);
            } else {
                if (currentChar.matches("\\d")) {
                    if (inputStack.peekLast().matches("\\d+")) {
                        String newValue = inputStack.pollLast() + currentChar;
                        inputStack.addLast(newValue);
                    } else if (inputStack.peekLast().matches("[-+*/^()]+")) {
                        inputStack.addLast(currentChar);
                    }
                } else if (currentChar.matches("[-+*/^]")) {
                    //if(currentChar=='-'){
                    if (inputStack.peekLast().matches("[" + currentChar + "]+")) {
                        String newValue = inputStack.pollLast() + (currentChar);
                        inputStack.addLast(newValue);
                    } else {
                        inputStack.addLast(currentChar);
                    }
                }
            }
        }
        //System.out.println(inputStack);
        return inputStack;
    }

    public static String convertToPostFix(ArrayDeque<String> input) {

        ArrayDeque<Character> operatersStack = new ArrayDeque<>();
        StringBuilder sb = new StringBuilder();
        while (!input.isEmpty()) {
            String symbol = input.pollFirst();
            if (symbol.matches("\\w+")) {
                sb.append(symbol).append(" ");
            } else if (symbol.matches("[+*()^/-]+")) {
                if (symbol.matches("[+]{2,}")) {
                    //  System.out.println(("match+++"));
                    symbol = "+";
                } else if (symbol.matches("[-]{2,}")) {
                    // System.out.println(("match---"));
                    symbol = (symbol.length() % 2 == 0) ? "+" : "-";
                } else if (symbol.matches("[*]{2,}|[/]{2,}")) {
                    //  System.out.println(("match***///"));
                    System.out.println("invalid");
                    break;
                }
                if (operatersStack.isEmpty() || operatersStack.peekFirst() == '(') {
                    operatersStack.addFirst(symbol.toCharArray()[0]);
                } else if (symbol.equals("(")) {
                    operatersStack.addFirst(symbol.toCharArray()[0]);
                } else if (symbol.equals(")")) {
                    boolean foundOpenBracket = false;
                    while (!operatersStack.isEmpty()) {
                        if (operatersStack.peekFirst() != '(') {
                            sb.append(operatersStack.pollFirst()).append(" ");
                        } else {
                            foundOpenBracket = true;
                            operatersStack.pollFirst();
                            break;
                        }
                    }
                    if (!foundOpenBracket) {
                        return "invalid";
                    }
                } else {
                    if (iFIncomingPrecedenceIsHigh(symbol.toCharArray()[0], operatersStack.peekFirst())) {
                        operatersStack.push(symbol.toCharArray()[0]);
                    } else {
                        sb.append(operatersStack.pollFirst()).append(" ");

                        while (!operatersStack.isEmpty() && operatersStack.peekFirst() != '(') {
                            if (!iFIncomingPrecedenceIsHigh(symbol.toCharArray()[0], operatersStack.peek())) {
                                sb.append(operatersStack.pollFirst()).append(" ");
                            }
                        }
                        operatersStack.push(symbol.toCharArray()[0]);
                    }
                }
            }

        }
        // System.out.println("end of input string " +operatersStack);

        while (!operatersStack.isEmpty()) {
            char c = operatersStack.pollFirst();
            if (c == '(') {
                return "invalid";
            } else {
                sb.append(c).append(" ");
            }
        }
        return sb.toString();
    }

    private static boolean iFIncomingPrecedenceIsHigh(char opt1, char opt2) {
        boolean highIncomingPrecedence = false;
        switch (opt1) {
            case '+':
            case '-':
                if (opt2 == '+' || opt2 == '-') {
                    highIncomingPrecedence = false;
                } else {
                    highIncomingPrecedence = false;

                }
                break;
            case '*':
            case '/':
                if (opt2 == '+' || opt2 == '-') {
                    highIncomingPrecedence = true;

                } else if (opt2 == '^') {
                    highIncomingPrecedence = false;

                } else {
                    highIncomingPrecedence = false;

                }
                break;
            case '^':
                if (opt2 == '^') {
                    highIncomingPrecedence = false;
                } else {
                    highIncomingPrecedence = true;
                }
                break;
            case '(':
            case ')':
                if (opt2 == '(' || opt2 == ')') {
                    highIncomingPrecedence = false;
                } else {
                    highIncomingPrecedence = true;
                }
                break;
        }
        return highIncomingPrecedence;
    }

    private static void getTotalByReadingSymbolsFromStack(Deque<String> stack) {
        try {
            int total = Integer.parseInt(stack.pollLast());
            while (!stack.isEmpty()) {
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
            String[] symbols = input.split("\\s+");
            int sum = 0;
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
                    if (s.trim().matches("-?\\d+")) {
                        stack.push(s);
                    } else if (s.matches(("[a-zA-Z]+"))) {
                        boolean variableExists = map.containsKey(s);
                        if (variableExists) {
                            stack.push(String.valueOf(map.get(s)));
                        }
                    } else {
                        System.out.println("stack in add " + stack);
                        System.out.println("Unknown variable");
                        throw new NumberFormatException();
                    }
                }
            }

        } catch (NumberFormatException e) {
            System.out.println("Invalid expression");
        }
    }
}