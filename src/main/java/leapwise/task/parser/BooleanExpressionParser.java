package leapwise.task.parser;

import leapwise.task.persistence.model.RootNode;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Stack;

public class BooleanExpressionParser {

    private static final Logger logger = LogManager.getLogger(BooleanExpressionParser.class);

    public void checkMathParser() {

        //System.out.println(evaluateExpression("(1+2)*4-3"));
    }

    public static String evaluateExpression(String expression, RootNode root) {
        // Create operandStack to store operands. (values)
        Stack<String> operandStack = new Stack<>();

        // Create operatorStack to store operators ( ==, !=, <=, ||, (, ) and others)
        Stack<String> operatorStack = new Stack<>();

        // Insert blanks around (, ), ==, !=, <, >, <=, >=, && and ||
        expression = regexLogicalExpression(expression, root);

        // Extract operands and operators
        String[] tokens = expression.split(" ");

        // Phase 1: Scan tokens
        for (String token : tokens) {
            if (token.length() == 0) // Blank space
                continue; // Back to the while loop to extract the next token
            else if (token.equals("&&") || token.equals("||")) {
                // Process all ==, !=, <, >, <=, >=, &&, || in the top of the operator stack
                while (!operatorStack.isEmpty() &&
                        (operatorStack.peek().equals("&&") ||
                                operatorStack.peek().equals("||") ||
                                operatorStack.peek().equals("==") ||
                                operatorStack.peek().equals("!=") ||
                                operatorStack.peek().equals("<") ||
                                operatorStack.peek().equals("<=") ||
                                operatorStack.peek().equals(">") ||
                                operatorStack.peek().equals(">="))) {
                    processAnOperator(operandStack, operatorStack);
                }

                // Push the && or || operator into the operator stack
                operatorStack.push(token);

            } else if (token.equals("==") || token.equals("!=")
                    || token.equals("<") || token.equals("<=")
                    || token.equals(">") || token.equals(">=")) {
                // Process all ==, !=, <, <=, >, >= in the top of the operator stack
                while (!operatorStack.isEmpty() &&
                        (operatorStack.peek().equals("==") || operatorStack.peek().equals("!=")
                                || operatorStack.peek().equals("<") || operatorStack.peek().equals("<=")
                                || operatorStack.peek().equals(">") || operatorStack.peek().equals(">="))) {
                    processAnOperator(operandStack, operatorStack);
                }

                // Push the ==, !=, <, <=, > or >= operator into the operator stack
                operatorStack.push(token);

            } else if (token.trim().equals("(")) {
                operatorStack.push("("); // Push '(' to stack
            } else if (token.trim().equals(")")) {
                // Process all the operators in the stack until seeing '('
                while (!operatorStack.peek().equals("(")) {
                    processAnOperator(operandStack, operatorStack);
                }

                operatorStack.pop(); // Pop the '(' symbol from the stack
            } else { // An operand scanned
                // Push an operand to the stack
                operandStack.push(token);
            }
        }

        // Phase 2: Process all the remaining operators in the stack
        while (!operatorStack.isEmpty()) {
            processAnOperator(operandStack, operatorStack);
        }

        // Return the result
        return operandStack.pop();
    }

    public static void processAnOperator(
            Stack<String> operandStack, Stack<String> operatorStack) {
        String operator = operatorStack.pop();
        String operand1 = operandStack.pop();
        String operand2 = operandStack.pop();

        if (operator.equals("=="))
            operandStack.push(operand2.equals(operand1) + "");
        else if (operator.equals("!="))
            operandStack.push(!operand2.equals(operand1) + "");
        else if (operator.equals("<"))
            operandStack.push((Integer.parseInt(operand2) < Integer.parseInt(operand1)) + "");
        else if (operator.equals("<="))
            operandStack.push((Integer.parseInt(operand2) <= Integer.parseInt(operand1)) + "");
        else if (operator.equals(">"))
            operandStack.push((Integer.parseInt(operand2) > Integer.parseInt(operand1)) + "");
        else if (operator.equals(">="))
            operandStack.push((Integer.parseInt(operand2) >= Integer.parseInt(operand1)) + "");
        else if (operator.equals("&&"))
            operandStack.push((Boolean.parseBoolean(operand2) && Boolean.parseBoolean(operand1)) + "");
        else if (operator.equals("||"))
            operandStack.push((Boolean.parseBoolean(operand2) || Boolean.parseBoolean(operand1)) + "");
    }

    public static String regexLogicalExpression(String s, RootNode root) {
        String result = s;

        // Throw an exception if the customer or customer.address objects are null
        if ((root.getCustomer() != null) && (root.getCustomer().getAddress() != null)) {
            result = s
                    .replaceAll("(\\()", " ( ")
                    .replaceAll("(\\))", " ) ")
                    .replaceAll("(==)", " == ")
                    .replaceAll("(!=)", " != ")
                    .replaceAll("(<)", " < ")
                    .replaceAll("(<=)", " <= ")
                    .replaceAll("(>)", " > ")
                    .replaceAll("(>=)", " >= ")
                    .replaceAll("(\\|\\|)", " || ")
                    .replaceAll("(&&)", " && ")
                    .replaceAll("\\b((OR)|(or))\\b", " || ")
                    .replaceAll("\\b((AND)|(and))\\b", " && ")
                    .replaceAll("\\s(customer)\\s", " " + root.getCustomer().hashCode() + " ")
                    .replaceAll("\\s(customer.address)\\s", " " + root.getCustomer().getAddress().hashCode() + " ")
                    .replaceAll("\\b(customer.firstName)\\b", root.getCustomer().getFirstName())
                    .replaceAll("\\b(customer.lastName)\\b", root.getCustomer().getLastName())
                    .replaceAll("\\b(customer.salary)\\b", Integer.toString(root.getCustomer().getSalary()))
                    .replaceAll("\\b(customer.type)\\b", root.getCustomer().getType())
                    .replaceAll("\\b(customer.address.city)\\b", root.getCustomer().getAddress().getCity())
                    .replaceAll("\\b(customer.address.zipCode)\\b", Integer.toString(root.getCustomer().getAddress().getZipCode()))
                    .replaceAll("\\b(customer.address.street)\\b", root.getCustomer().getAddress().getStreet())
                    .replaceAll("\\b(customer.address.houseNumber)\\b", Integer.toString(root.getCustomer().getAddress().getHouseNumber()))
                    .replaceAll("(\")", "");
        } else {
            throw new IllegalArgumentException("Customer object should not be null");
        }

        logger.info("The logical expression before regex {}", s);
        logger.info("The logical expression after regex {}", result);

        return result;
    }

}
