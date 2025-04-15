package linked_liist;

import java.util.Scanner;
import java.io.IOException;
import java.util.HashMap;
import java.util.InputMismatchException;
//import java.util.InputMismatchException;
import java.util.Map;
import java.util.TreeMap;
import java.util.NoSuchElementException;

class DNode {
    private int coef, exponent;

    private DNode next, prev;

    public DNode(int s, int x, DNode p, DNode n) {
        coef = s;
        exponent = x;
        next = n;
        prev = p;
    }

    public int GetCoef() {
        return coef;
    }

    public int GetExponent() {
        return exponent;
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

    public void SetCoef(int r) {
        coef = r;
    }

    public void SetExponent(int c) {
        exponent = c;
    }
}

interface ILinkedList {
    // void add(Object element);
    // Object get(int index);
    // int size();
    ILinkedList sublist(int fromIndex, int toIndex);
}

class Doubly_Linked_List implements ILinkedList {
    private int size;
    private DNode header, trailer;

    /**
     * 
     */
    public Doubly_Linked_List() {
        size = 0;
        header = new DNode(0, 0, null, null);
        trailer = new DNode(0, 0, header, null);
        header.SetNext(trailer);
    }

    /**
     * @param idx
     * @param m
     */
    public void ad(int idx, DNode m) {
        DNode temp = header;
        int temp1 = 0;
        while (temp != trailer) {

            if (temp1 == idx) {
                DNode nextNode = temp.GetNext();
                m.SetPrev(temp);
                m.SetNext(temp.GetNext());
                if (nextNode != null) {
                    nextNode.SetPrev(m);
                }

                temp.SetNext(m);
            }
            temp = temp.GetNext();
            temp1++;

        }
        size++;
    }

    /**
     * @param m
     */
    public void add(DNode m) {
        DNode last = trailer.GetPrev();
        m.SetNext(trailer);
        m.SetPrev(last);
        last.SetNext(m);
        trailer.SetPrev(m);
        size++;
    }

    /**
     * @param idx
     * @return
     */
    public DNode get(int idx) {
        DNode temp = header.GetNext();
        int temp1 = 0;
        if (idx >= size || idx < 0) {

            return null;
        }
        while (temp != trailer) {
            if (temp1 == idx) {
                return temp;
            }
            temp = temp.GetNext();
            temp1++;
        }
        return null;
    }

    /**
     * @param idx
     * @param m
     */
    public void set(int idx, DNode m) {

        if (idx >= size || idx < 0) {
            throw new IndexOutOfBoundsException("Index: " + idx + ", Size: " + size);
        }
        // if (idx == 0) {
        // m.SetNext(header.GetNext());
        // if (header.GetNext() != null) {
        // header.GetNext().SetPrev(m);
        // }
        // header = m;
        // return;
        // }

        DNode prev = header;
        for (int i = 0; i < idx; i++) {
            prev = prev.GetNext();
        }
        DNode old = prev.GetNext();
        m.SetNext(old.GetNext());
        m.SetPrev(prev);
        prev.SetNext(m);
        if (old.GetNext() != null) {
            old.GetNext().SetPrev(m);
        }
    }

    /**
     * 
     */
    public void clear() {
        DNode head = header;
        while (head != trailer) {
            DNode temp = head.GetNext();
            head.SetNext(null);
            head.SetPrev(null);
            head = temp;
        }
        header.SetNext(null);
        size = 0;
    }

    /**
     * @return
     */
    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * @param idx
     */
    public void remove(int idx) {
        DNode prev = header;
        if (idx >= size || idx < 0) {
            throw new IndexOutOfBoundsException("Index: " + idx + ", Size: " + size);
        }
        // if (idx == 0) {
        // DNode temp = header.GetNext();
        // if (temp.GetNext() != null) {
        // header.SetNext(temp.GetNext());
        // } else {
        // header.SetNext(null);
        // }
        // temp.SetNext(null);
        // temp.SetPrev(null);
        // }
        for (int i = 0; i < idx; i++) {
            prev = prev.GetNext();
        }
        DNode target = prev.GetNext();
        if (target.GetNext() != null) {
            target.GetNext().SetPrev(prev);
        }

        prev.SetNext(target.GetNext());
        target.SetNext(null);
        target.SetPrev(null);
        size--;
    }

    public int Size() {
        return size;
    }

    public ILinkedList sublist(int fromidx, int toidx) {
        if (fromidx < 0 || toidx > size || fromidx >= toidx) {
            throw new IndexOutOfBoundsException("Invalid sublist range... ");
        }
        Doubly_Linked_List sub = new Doubly_Linked_List();
        DNode current = header.GetNext();
        for (int i = 0; i < fromidx; i++) {
            current = current.GetNext();
        }

        for (int i = fromidx; i < toidx; i++) {
            DNode temp = new DNode(current.GetCoef(), current.GetExponent(), null, null);
            sub.add(temp);
            current = current.GetNext();
        }
        return sub;
    }

    public boolean contain(DNode o) {
        DNode head = header.GetNext();
        while (head != trailer) {

            if (head.GetCoef() == o.GetCoef() && head.GetExponent() == o.GetExponent()) {
                return true;
            }
            head = head.GetNext();
        }
        return false;
    }
}

interface IPolynomialSolver {
    /**
     * Set polynomial terms (coefficients & exponents)
     * 
     * @param poly:  name of the polynomial
     * @param terms: array of [coefficients][exponents]
     */
    void setPolynomial(char poly, int[][] terms);

    /**
     * Print the polynomial in ordered human readable representation
     * 
     * @param poly: name of the polynomial
     * @return: polynomial in the form like 27x^2+x-1
     */
    String print(char poly);

    /**
     * Clear the polynomial
     * 
     * @param poly: name of the polynomial
     */
    void clearPolynomial(char poly);

    /**
     * Evaluate the polynomial
     * 
     * @param poly:  name of the polynomial
     * @param value: the polynomial constant value
     * @return the value of the polynomial
     */
    float evaluatePolynomial(char poly, float value);

    /**
     * Add two polynomials
     * 
     * @param poly1: first polynomial
     * @param poly2: second polynomial
     * @return the result polynomial
     */
    int[][] add(char poly1, char poly2);

    /**
     * Subtract two polynomials
     * 
     * @param poly1: first polynomial
     * @param poly2: second polynomial
     * @return the result polynomial
     */
    int[][] subtract(char poly1, char poly2);

    /**
     * Multiply two polynomials
     * 
     * @param poly1: first polynomial
     * @param poly2: second polynomial
     * @return: the result polynomial
     */
    int[][] multiply(char poly1, char poly2);
}

// import javax.print.DocFlavor.STRING;

class PolySolver implements IPolynomialSolver {
    public static Map<Character, Doubly_Linked_List> polynomials = new HashMap<>();

    /**
     * @param poly
     * @param terms
     */
    public void setPolynomial(char poly, int[][] terms) {
        Doubly_Linked_List set = new Doubly_Linked_List();

        boolean allZero = true;
        for (int[] term : terms) {
            if (term[0] != 0) {
                allZero = false;
                break;
            }
        }

        if (allZero) {
            if (poly == 'R') {
                polynomials.put(poly, set);
                return;
            } else {
                throw new NoSuchElementException();
            }
        }

        if (polynomials.containsKey(poly)) {
            polynomials.get(poly).clear();
        }

        for (int[] term : terms) {
            int temp1 = term[0];
            int temp2 = term[1];
            DNode temp = new DNode(temp1, temp2, null, null);
            set.add(temp);
        }

        polynomials.put(poly, set);
    }

    /**
     * @param poly
     * @return
     */
    public String print(char poly) {
        if (!polynomials.containsKey(poly)) {
            throw new NoSuchElementException();
        }

        Doubly_Linked_List list = polynomials.get(poly);
        StringBuilder result = new StringBuilder();
        // if (list.Size() == 0) {
        // throw new NoSuchElementException();
        // }
        for (int i = 0; i < list.Size(); i++) {
            DNode temp = list.get(i);
            int coef = temp.GetCoef();
            int exp = temp.GetExponent();

            if (coef == 0)
                continue;

            if (i == 0) {

                if (coef == 1) {
                    if (exp == 0) {
                        result.append("1");
                    } else if (exp == 1) {
                        result.append("x");
                    } else {
                        result.append("x^").append(exp);
                    }
                } else if (coef == -1) {
                    if (exp == 0) {
                        result.append("-1");
                    } else if (exp == 1) {
                        result.append("-1x");
                    } else {
                        result.append("-1x^").append(exp);
                    }
                } else {
                    if (exp == 0) {
                        result.append(coef);
                    } else if (exp == 1) {
                        result.append(coef).append("x");
                    } else {
                        result.append(coef).append("x^").append(exp);
                    }
                }
            } else {

                if (coef > 0) {
                    result.append("+");
                    if (coef == 1) {
                        if (exp == 0) {
                            result.append("1");
                        } else if (exp == 1) {
                            result.append("x");
                        } else {
                            result.append("x^").append(exp);
                        }
                    } else {
                        if (exp == 0) {
                            result.append(coef);
                        } else if (exp == 1) {
                            result.append(coef).append("x");
                        } else {
                            result.append(coef).append("x^").append(exp);
                        }
                    }
                } else if (coef < 0) {
                    if (coef == -1) {
                        if (exp == 0) {
                            result.append("-1");
                        } else if (exp == 1) {
                            result.append("-x");
                        } else {
                            result.append("-x^").append(exp);
                        }
                    } else {
                        if (exp == 0) {
                            result.append(coef);
                        } else if (exp == 1) {
                            result.append(coef).append("x");
                        } else {
                            result.append(coef).append("x^").append(exp);
                        }
                    }
                }
            }
        }
        int cnt = 0;
        for (int i = 0; i < result.length(); i++) {
            if (result.charAt(i) == '0') {
                cnt++;
            }
        }
        if (poly == 'R' && result.length() == cnt) {
            return "0";
        } else if (poly != 'R' && result.length() == 0) {
            return "[]";
        } else {
            return result.toString();
        }

    }

    /**
     * @param poly
     */
    public void clearPolynomial(char poly) {
        if (!polynomials.containsKey(poly)) {
            polynomials.put(poly, new Doubly_Linked_List());
        } else {
            polynomials.get(poly).clear();
        }
    }

    /**
     * @param poly
     * @param value
     * @return
     */
    public float evaluatePolynomial(char poly, float value) {
        if (!polynomials.containsKey(poly) || polynomials.get(poly).isEmpty()) {
            throw new NoSuchElementException();
        }
        Doubly_Linked_List list = new Doubly_Linked_List();

        list = polynomials.get(poly);
        if (list.Size() == 0) {
            throw new NoSuchElementException();
        }
        float result = 0;
        for (int i = 0; i < list.Size(); i++) {
            DNode temp = list.get(i);
            int cof = temp.GetCoef();
            int exp = temp.GetExponent();
            result += cof * (float) Math.pow(value, exp);
        }
        return result;
    }

    /**
     * @param poly1
     * @param poly2
     * @return
     */
    public int[][] add(char poly1, char poly2) {
        if (!polynomials.containsKey(poly1) || polynomials.get(poly1).isEmpty() ||
                !polynomials.containsKey(poly2) || polynomials.get(poly2).isEmpty()) {
            throw new NoSuchElementException();
        }
        Doubly_Linked_List list1 = polynomials.get(poly1);
        Doubly_Linked_List list2 = polynomials.get(poly2);
        Map<Integer, Integer> resultMap = new TreeMap<>((a, b) -> b - a);
        for (int i = 0; i < list1.Size(); i++) {
            DNode temp = list1.get(i);
            int coef = temp.GetCoef();
            int exp = temp.GetExponent();
            resultMap.put(exp, resultMap.getOrDefault(exp, 0) + coef);
        }

        for (int i = 0; i < list2.Size(); i++) {
            DNode temp = list2.get(i);
            int coef = temp.GetCoef();
            int exp = temp.GetExponent();
            resultMap.put(exp, resultMap.getOrDefault(exp, 0) + coef);
        }

        resultMap.entrySet().removeIf(entry -> entry.getValue() == 0);

        int[][] result = new int[resultMap.size()][2];
        int index = 0;
        for (Map.Entry<Integer, Integer> entry : resultMap.entrySet()) {
            result[index][0] = entry.getValue();
            result[index][1] = entry.getKey();
            index++;
        }
        setPolynomial('R', result);
        return result;
    }

    /**
     * @param poly1
     * @param poly2
     * @return
     */
    public int[][] subtract(char poly1, char poly2) {
        if (!polynomials.containsKey(poly1) || polynomials.get(poly1).isEmpty() ||
                !polynomials.containsKey(poly2) || polynomials.get(poly2).isEmpty()) {
            throw new NoSuchElementException();
        }
        Doubly_Linked_List list1 = polynomials.get(poly1);
        Doubly_Linked_List list2 = polynomials.get(poly2);
        Map<Integer, Integer> resultMap = new TreeMap<>((a, b) -> b - a);

        for (int i = 0; i < list1.Size(); i++) {
            DNode temp = list1.get(i);
            int coef = temp.GetCoef();
            int exp = temp.GetExponent();
            resultMap.put(exp, resultMap.getOrDefault(exp, 0) + coef);
        }

        for (int i = 0; i < list2.Size(); i++) {
            DNode temp = list2.get(i);
            int coef = temp.GetCoef();
            int exp = temp.GetExponent();
            resultMap.put(exp, resultMap.getOrDefault(exp, 0) - coef);
        }

        resultMap.entrySet().removeIf(entry -> entry.getValue() == 0);

        int[][] result = new int[resultMap.size()][2];
        int index = 0;
        for (Map.Entry<Integer, Integer> entry : resultMap.entrySet()) {
            result[index][0] = entry.getValue();
            result[index][1] = entry.getKey();
            index++;
        }
        setPolynomial('R', result);
        return result;

    }

    public int[][] multiply(char poly1, char poly2) {
        if (!polynomials.containsKey(poly1) || polynomials.get(poly1).isEmpty() ||
                !polynomials.containsKey(poly2) || polynomials.get(poly2).isEmpty()) {
            throw new NoSuchElementException();
        }

        Doubly_Linked_List list1 = polynomials.get(poly1);
        Doubly_Linked_List list2 = polynomials.get(poly2);
        Map<Integer, Integer> resultMap = new TreeMap<>((a, b) -> b - a); // Sort by exponent in descending order

        if (list1.Size() == 0 || list2.Size() == 0) {
            throw new InputMismatchException();
        }

        for (int i = 0; i < list1.Size(); i++) {
            DNode temp1 = list1.get(i);
            for (int j = 0; j < list2.Size(); j++) {
                DNode temp2 = list2.get(j);
                int coef = temp1.GetCoef() * temp2.GetCoef();
                int exp = temp1.GetExponent() + temp2.GetExponent();
                resultMap.put(exp, resultMap.getOrDefault(exp, 0) + coef); // Sum coefficients for the same exponent
            }
        }

        resultMap.entrySet().removeIf(entry -> entry.getValue() == 0); // Remove terms with zero coefficients

        int[][] result = new int[resultMap.size()][2];
        int index = 0;
        for (Map.Entry<Integer, Integer> entry : resultMap.entrySet()) {
            result[index][0] = entry.getValue();
            result[index][1] = entry.getKey();
            index++;
        }

        setPolynomial('R', result);
        return result;
    }
}

public class Solution {
    public int[][] read(Scanner sc) {

        String line = sc.nextLine();
        line = line.replaceAll("\\[|\\]", "");
        String[] parts = line.split(",");
        if (line.isEmpty()) {
            return new int[0][2];
        }
        if (parts.length == 1 && parts[0].trim().equals("0")) {

            return new int[][] { { 0, 0 } };
        }

        int[] arr = new int[parts.length];
        for (int i = 0; i < parts.length; i++) {
            arr[i] = Integer.parseInt(parts[i].trim());
        }
        int[][] terms = new int[arr.length][2];
        int k = arr.length - 1;
        for (int i = 0; i < terms.length; i++) {
            terms[i][0] = arr[i];
            terms[i][1] = k--;
        }

        return terms;
    }

    public static void main(String[] args) {
        try {
            Scanner scanner = new Scanner(System.in);
            PolySolver solver = new PolySolver(); //
            Solution solution = new Solution();
            while (scanner.hasNextLine()) {

                String command = scanner.nextLine().trim();
                String polyName;
                char var;
                String result1;
                int[][] result2;
                String poly1Name;
                String poly2Name;
                char var1;
                char var2;
                switch (command) {
                    case "set":

                        polyName = scanner.nextLine().trim();
                        var = polyName.charAt(0);
                        int[][] terms = solution.read(scanner);
                        solver.setPolynomial(var, terms);

                        break;

                    case "print":

                        polyName = scanner.nextLine().trim();
                        var = polyName.charAt(0);
                        result1 = solver.print(var);
                        System.out.println(result1);

                        break;

                    case "add":

                        poly1Name = scanner.nextLine().trim();
                        var1 = poly1Name.charAt(0);
                        poly2Name = scanner.nextLine().trim();
                        var2 = poly2Name.charAt(0);

                        result2 = solver.add(var1, var2);
                        solver.setPolynomial('R', result2);

                        System.out.println(solver.print('R'));
                        return;

                    case "sub":

                        poly1Name = scanner.nextLine().trim();
                        var1 = poly1Name.charAt(0);
                        poly2Name = scanner.nextLine().trim();
                        var2 = poly2Name.charAt(0);

                        result2 = solver.subtract(var1, var2);
                        solver.setPolynomial('R', result2);
                        System.out.println(solver.print('R'));
                        return;

                    case "mult":

                        poly1Name = scanner.nextLine().trim();
                        var1 = poly1Name.charAt(0);
                        poly2Name = scanner.nextLine().trim();
                        var2 = poly2Name.charAt(0);

                        result2 = solver.multiply(var1, var2);
                        solver.setPolynomial('R', result2);
                        System.out.println(solver.print('R'));
                        return;

                    case "clear":

                        System.out.println("[]");
                        return;

                    case "eval":

                        polyName = scanner.nextLine().trim();
                        var = polyName.charAt(0);
                        float value = Float.parseFloat(scanner.nextLine().trim());

                        float result = solver.evaluatePolynomial(var, value);
                        if (result == (int) result) {
                            System.out.println((int) result);
                        } else {
                            System.out.println(result);
                        }
                        return;

                    default:
                        System.out.println("Error");
                        return;
                    // break;
                }
            }
        } catch (Exception e) {
            System.out.println("Error");
            return;
        }
    }
}
