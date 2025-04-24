import java.util.Scanner;
import java.util.EmptyStackException;
import java.security.InvalidAlgorithmParameterException;
import java.util.Scanner;
interface IExpressionEvaluator {
    /**
     * Takes a symbolic/numeric infix expression as input and converts it to
     * postfix notation. There is no assumption on spaces between terms or the
     * length of the term (e.g., two digits symbolic or numeric term)
     *
     * @param expression infix expression
     * @return postfix expression
     */
    public String infixToPostfix(String expression);

    /**
     * Evaluate a postfix numeric expression, with a single space separator
     * 
     * @param expression postfix expression
     * @return the expression evaluated value
     */
    public int evaluate(String expression);
}




class DNode {
    private Object data;
    private DNode next, prev;

    public DNode(Object s, DNode p, DNode n) {
        data = s;
        next = n;
        prev = p;
    }

    public Object Getdata() {
        return data;
    }

    public DNode GetPrev() {
        return prev;
    }

    public DNode GetNext() {
        return next;
    }

    public void SetNext(DNode k) {
        next = k;
    }

    public void SetPrev(DNode f) {
        prev = f;
    }

    public void Setdata(int r) {
        data = r;
    }
}

interface IStack {
    /**
    * Removes the element at the top of stack and returns that element.
    * @return top of stack element, or through exception if empty
    */
    public Object pop();
    /**
    * Get the element at the top of stack without removing it from stack.
    * @return top of stack element, or through exception if empty
    */
    public Object peek();
    /**
    * Pushes an item onto the top of this stack.
    * @param object to insert
    */
    public void push(Object element);
    /**
    * Tests if this stack is empty
    * @return true if stack empty
    */
    public boolean isEmpty();
    
    public int size();
    }




 class MyStack implements IStack {

    private DNode top;
    private int size;

    /**
     *construct my stack 
     */
    public MyStack() {
        top = null;
        size = 0;
    }
     /**
    * Tests if this stack is empty
    * @return true if stack empty
    */
    public boolean isEmpty() {
        return top == null;
    }

    /**
     * Removes the element at the top of stack and returns that element.
     * 
     * @return top of stack element, or through exception if empty
     */
    public Object pop() {
        if (isEmpty()) {
            throw new EmptyStackException();
        }
        Object temp = top.Getdata();
        top = top.GetNext();
        size--;
        return temp;
    }

    /**
     * Get the element at the top of stack without removing it from stack.
     * 
     * @return top of stack element, or through exception if empty
     */
    public Object peek() {
        if (isEmpty()) {
            throw new EmptyStackException();
        }
        Object temp = top.Getdata();
        return temp;
    }

    /**
     * Pushes an item onto the top of this stack.
     * 
     * @param object to insert
     */
    public void push(Object elem) {

        DNode temp = new DNode(elem, null, top);
        top = temp;
        size++;
    }
    /*
     * return the size of the MyStack
     */
    public int size() {
        return size;
    }
}





 class ExpressionEvaluator {
    /**
     * @param ch
     * @return
     *         check precedence and return the value of precedence
     */
    private int precedence(char ch) {
        if (ch == '+' || ch == '-' || ch == '~')
            return 1;
        if (ch == '*' || ch == '/')
            return 2;
        if (ch == '^') {
            return 3;
        }
        if(ch == '~'){
            return 4;
        }
        return 0;
    }

    private boolean isOperator(char ch) {
        return ch == '+' || ch == '-' || ch == '*' || ch == '/' || ch == '^';
    }
    // private MyStack check(MyStack x){
    // char top = (char)x.pop();
    // if (top == (char)x.peek() && top == '-') {
    // x.pop();
    // x.push('+');
    // }
    // return x;
    // }

    /**
     * Takes a symbolic/numeric infix expression as input and converts it to
     * postfix notation. There is no assumption on spaces between terms or the
     * length of the term (e.g., two digits symbolic or numeric term)
     *
     * @param expression infix expression
     * @return postfix expression
     */
    public String infixToPostfix(String expression) {
        StringBuilder result = new StringBuilder();
        MyStack operator = new MyStack();
        for (int i = 0; i < expression.length(); i++) {
            if (i == 0 && expression.charAt(i) == '+') {
                continue;
            }
            char ch = expression.charAt(i);
            if (Character.isDigit(ch)) {
                // if there is muultidiget number as that numbers is separated using space
                while (i < expression.length() && (Character.isDigit(expression.charAt(i)))) {
                    result.append(expression.charAt(i));
                    i++;
                }
                result.append(' '); // Space to separate operands
                i--; // as the i increaments in the for loop
            } else if (Character.isLetter(ch)) { // if the expression contain variables like a,b,c
                result.append(ch);
                result.append(' ');

            } else if (ch == '(') {
                 if (expression.charAt(i+1) == ')') {
                    throw new IllegalArgumentException();
                }
                operator.push((Object) '(');
            } else if (ch == '^') {
                while (!operator.isEmpty() && precedence((char) operator.peek()) > precedence(ch)) {// to consider right
                    // to left
                    // precedence
                    result.append(operator.pop());
                    result.append(' ');
                }
                operator.push((Object) '^');
            } else if (ch == '*') {
                while (!operator.isEmpty() && precedence((char) operator.peek()) >= precedence(ch)) {// to consider left
                                                                                                     // to right
                                                                                                     // precedence
                    result.append(operator.pop());
                    result.append(' ');
                }
                operator.push((Object) '*');
            } else if (ch == '/') {
                while (!operator.isEmpty() && precedence((char) operator.peek()) >= precedence(ch)) {// to consider left
                                                                                                     // to right
                                                                                                     // precedence
                    result.append(operator.pop());
                    result.append(' ');
                }
                operator.push((Object) '/');
            } else if (ch == '+') {
                while (!operator.isEmpty() && precedence((char) operator.peek()) >= precedence(ch)) { // to consider
                                                                                                      // left to right
                                                                                                      // precedence
                    result.append(operator.pop());
                    result.append(' ');
                }
                operator.push((Object) '+');
            } else if (ch == '-') {
                if (i == 0 || expression.charAt(i - 1) == '(' || isOperator(expression.charAt(i - 1))
                        ) {
                    operator.push('~'); // using '~' as a placeholder for unary minus
                } else {
                    while (!operator.isEmpty() && precedence((char) operator.peek()) >= precedence(ch)) {
                        result.append(operator.pop()).append(' ');
                    }
                    operator.push('-');
                }
            } else if (ch == ')') {
                while (!operator.isEmpty() && (char) operator.peek() != '(') {
                    char top = (char) operator.pop();
                    result.append(top).append(' ');
                }
                if (operator.isEmpty()) {
                    throw new IllegalArgumentException();
                }
                operator.pop(); // to pop the open parentheses
            } else if (ch == ' ') {
                continue;

            } else {
                throw new IllegalArgumentException(); // if the expression contain invalid operation or invalid
                                                      // character
            }
        }
        while (!operator.isEmpty()) {
            char op = (char) operator.pop(); // add the elements in the stack to the result
            if (op == '(') {
                throw new IllegalArgumentException();
            }
            result.append(op).append(' ');
        }
        return result.toString();
    }

    /**
     * Evaluate a postfix numeric expression, with a single space separator
     * 
     * @param expression postfix expression
     * @return the expression evaluated value
     */
    public int evaluate(String expression) {

        int result = 0;
        MyStack operands = new MyStack();
        for (int i = 0; i < expression.length(); i++) {
            char ch = expression.charAt(i);
            if (Character.isDigit(ch)) {
                int num = 0;

                while (i < expression.length() && Character.isDigit(expression.charAt(i))) {
                    num = num * 10 + (expression.charAt(i) - '0'); // multidigit number
                    i++;
                }
                operands.push(num);
                i--; // step back because for loop also increments
            } else if (ch == ' ') {
                continue;
            } else if (ch == '-' && (i < expression.length()-1 && Character.isDigit(expression.charAt(i+1)) )) {
                i++; // move to digit
                int num = 0;
                while (i < expression.length() && Character.isDigit(expression.charAt(i))) {
                    num = num * 10 + (expression.charAt(i) - '0');
                    i++;
                }
                operands.push(-num);
                i--; // because for loop will increment again

            } else if (ch == '^') {
                int op1 = (int) operands.pop();
                int op2 = (int) operands.pop();
                int res = (int) Math.pow(op2, op1);
                operands.push(res);
            } else if (ch == '~') {
                int op1 = (int) operands.pop();
                operands.push(-op1);
            } else if (ch == '*') {
                int op1 = (int) operands.pop();
                int op2 = (int) operands.pop();
                int res = op1 * op2;
                operands.push(res);
            } else if (ch == '/') {
                int op1 = (int) operands.pop();
                int op2 = (int) operands.pop();
                int res = op2 / op1;
                operands.push(res);
            } else if (ch == '+') {
                int op1 = (int) operands.pop();
                int op2 = (int) operands.pop();
                int res = op1 + op2;
                operands.push(res);
            } else if (ch == '-') {
                if(operands.size() == 1){
                    int op1 = (int) operands.pop();
                    operands.push(-op1);
                }
                int op1 = (int) operands.pop();
                int op2 = (int) operands.pop();
                int res = op2 - op1;
                operands.push(res);
            }

        }
        result = (int) operands.pop();
        return result;
    }

    public String replace(String x, int a, int b, int c) {
        String result = x;
        result = result.replace("a", String.valueOf(a));
        result = result.replace("b", String.valueOf(b));
        result = result.replace("c", String.valueOf(c));
        return result;
    }
}




public class Main {
    private String check(String x) {
        MyStack temp = new MyStack();
        for (int i = 0; i < x.length(); i++) {
            if (!temp.isEmpty() && x.charAt(i) == '-' && (char) temp.peek() == '-') {
                temp.pop();
                temp.push('+');
            } else {
                temp.push(x.charAt(i));
            }
        }
        MyStack temp2 = new MyStack();
        while (!temp.isEmpty()) {
            temp2.push(temp.pop());
        }
        StringBuilder s = new StringBuilder();
        while (!temp2.isEmpty()) {
            s.append(temp2.pop());
        }
         if (s.charAt(0) == '+') {
            s.deleteCharAt(0);
        }
         String result = s.toString();
        result = result.replace("+-", "-");
        result = result.replace("-+", "-");
         result = result.replace("^+", "^");
        result = result.replace("*+", "*");
        result = result.replace("/+", "/");
        result = result.replace("++", "+");
        return result;
    }

    /**
     * @param args
     */
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String input = scanner.nextLine();
        ExpressionEvaluator solve = new ExpressionEvaluator();
        Main w = new Main();
        String postfix = w.check(input);
        //System.out.println(postfix);
        ExpressionEvaluator y = new ExpressionEvaluator();
        int a = 0, b = 0, c = 0;

        for (int i = 0; i < 3; i++) {
            String line = scanner.nextLine().trim();
            String[] parts = line.split("="); // split into ["a", "1"]
            String var = parts[0].trim();
            int value = Integer.parseInt(parts[1].trim());

            switch (var) {
                case "a":
                    a = value;
                    break;
                case "b":
                    b = value;
                    break;
                case "c":
                    c = value;
                    break;
                default:
                    System.out.println("Error");
                    return;
            }
        }
        int result = 0;
        try {
            postfix = solve.infixToPostfix(postfix);
            String postfix2 = y.replace(postfix, a, b, c);
            result = solve.evaluate(postfix2);
            postfix = postfix.replace(" ", "");
            postfix = postfix.replace('~', '-');
            // System.out.println(postfix);
        } catch (Exception e) {
            // e.printStackTrace();
            System.out.println("Error");
            return;
        }

        System.out.println(postfix);
        System.out.println(result);
    }
}


