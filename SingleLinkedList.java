package sll;

import java.util.Scanner;

interface ILinkedList {
/**
* Inserts a specified element at the specified position in the list.
* @param index
* @param element
*/
public void add(int index, Object element);
/**
* Inserts the specified element at the end of the list.
* @param element
*/
public void add(Object element);
/**
* @param index
* @return the element at the specified position in this list.
*/
public Object get(int index);

/**
* Replaces the element at the specified position in this list with the
* specified element.
* @param index
* @param element
*/
public void set(int index, Object element);
/**
* Removes all of the elements from this list.
*/
public void clear();
/**
* @return true if this list contains no elements.
*/
public boolean isEmpty();
/**
* Removes the element at the specified position in this list.
* @param index
*/
public void remove(int index);
/**
* @return the number of elements in this list.
*/
public int size();
/**
* @param fromIndex
* @param toIndex
* @return a view of the portion of this list between the specified fromIndex and toIndex, inclusively.
*/
public ILinkedList sublist(int fromIndex, int toIndex);
/**
* @param o
* @return true if this list contains an element with the same value as the specified element.
*/
public boolean contains(Object o);
}

public class SingleLinkedList implements ILinkedList{
    
    class node {
        private int val;
        private node next;
    
        node getnext() {
            return next;
        }
        void setnext(node x) {
            next = x;
        }
        void setval(int x) {
            val = x;
        }
        int getval() {
            return val;
        }
    }
    
    public node head = new node();
    
    
    public static void main(String[] args) {
        
        SingleLinkedList sll = new SingleLinkedList();
        Scanner sc = new Scanner(System.in);
        String sin = sc.nextLine().replaceAll("\\[|\\]", "");
        String in =sc.nextLine().replaceAll("\\ ", "");
  
        
        String[] s = sin.split(", ");;
        int[] arr = new int[s.length];
        if (s.length == 1 && s[0].isEmpty()) {
            arr = new int[]{};
            sll.head =null;
        }
        else {

            for(int i = 0; i < s.length; i++) {
               arr[i] = Integer.parseInt(s[i]);
            }
            sll.head.setval(arr[0]);
            for(int i = 1 ; i < arr.length ;i++) {
                sll.add(arr[i]);

           }
        }
        
        
        if (in.equals("add")) {
            int a =sc.nextInt();
            sll.add(a);
            print_list(sll);
        }
        else if (in.equals("addToIndex")) {
            int a =sc.nextInt();
            sc.nextLine();
            int b =sc.nextInt();
            sll.add(a, b);
            print_list(sll);
        }
        else if (in.equals("get")) {
            int a =sc.nextInt();
            node c= (node) sll.get(a);
            if(c==null) {
                System.out.print("Error");
            }
            else {
                System.out.print(c.getval());
            }

        }
        else if (in.equals("set")) {
            int a =sc.nextInt();
            sc.nextLine();
            int b =sc.nextInt();
            sll.set(a, b);
            print_list(sll);
        }
        else if (in.equals("clear")) {
            sll.clear();
            print_list(sll);
        }
        else if (in.equals("isEmpty")) {
            boolean c = sll.isEmpty();
            if(c) {
                System.out.print("True");
            }
            else {
                System.out.print("False");
            }
        }
        else if (in.equals("remove")) {
            int a =sc.nextInt();
            sll.remove(a);
            print_list(sll);
        }
        else if (in.equals("sublist")) {
            int a =sc.nextInt();
            sc.nextLine();
            int b =sc.nextInt();
            ILinkedList h=sll.sublist(a, b);
                node hh =(node) h.get(0);
                if(hh == null) {
                    System.out.print("[]");
                }
                else {
                    System.out.print("[");
                    while(hh.getnext() != null) {
                        System.out.print(hh.getval());
                        if(hh.getnext().getnext() != null) {
                            System.out.print(", ");
                        }
                        hh=hh.getnext();
                        
                    }
                    System.out.print("]");
                }
        }
        else if (in.equals("size")) {
            System.out.print(sll.size());
        }
        else if(in.equals("contains")) {
            int a =sc.nextInt();
            boolean c = sll.contains(a);
            if(c) {
                System.out.print("True");
            }
            else {
                System.out.print("False");
            }
        }
        else {
            System.out.print("Error");
        }

        
    

}

    
    public void add(int index, Object element) {
        
        if(index < 0) {
            System.out.print("Error");
            System.exit(0);
        }
        
        if(size() == 0) {
            if(index == 0) {
                head = new node();
                head.setval((int) element);
                head.setnext(null);
            }
            else {
                System.out.print("Error");
                System.exit(0);
            }
        }
        else {
            int n =size();
            node x= head;
            if(n == index) {
                add(element);
            }
            else if(index == 0){
                  node y = new node();
                  y.setval((int) element);
                  y.setnext(head);
                  head=y;
            }
            else if(n-1 >= index ) {
              for(int i = 1 ; i < index ; i++) {
                  x=x.getnext();
              }
              node temp=x.getnext();
              node y = new node();
              y.setval((int) element);
              y.setnext(temp);
              x.setnext(y);
            }
            else {
                System.out.print("Error");    
                System.exit(0);
            }
        }

    }

    public void add(Object element) {
        if(head == null) {
                head =new node();
                head.setval((int) element);
                head.setnext(null);
        }
        else {
            node x=head;
            while (x.getnext() != null) {
                x =x.getnext();
            }
            node y = new node();
            y.setval((int) element);
            y.setnext(null);
            x.setnext(y);
        }
    }


    public Object get(int index) {
        int n =size();
        if (n-1 < index || index < 0) {
            return null;
        }
        else {
            node x=head;
            for(int i=0; i < index;i++) {
                x=x.getnext();
            }
            return x;
        }
    }


    public void set(int index, Object element) {
        int n =size();
        if (n-1 < index || index < 0) {
            System.out.print("Error");
            System.exit(0);
        }
        else {
            node x = head;
            for(int i=0; i < index ;i++) {
                x=x.getnext();
            }
            x.setval((int) element);
            
            }
        
    }


    public void clear() {
            head=null;    
    }

    public boolean isEmpty() {
        int n =size();
        if(n != 0) {
            return false;
        }
        else {
            return true;
        }
    }

    @Override
    public void remove(int index) {
        int n =size();
        if (n-1 < index || index < 0) {
            System.out.print("Error");
            System.exit(0);
        }
        else if(index == n-1) {
            node x=head;
            while(x.getnext().getnext()!=null) {
                x=x.getnext();
            }
            x.setnext(null);
        }
        else if(index == 0){
            head=head.getnext();
        }
        else {
            node x=head;
            for(int i=1;i<index;i++) {
                x=x.getnext();
            }
            node temp=x.getnext().getnext();
            node y= x.getnext();
            y=null;
            x.setnext(temp);
        }
        
    }

    
    public int size() {
        if(head == null) {
        return 0;
        }
        else {
            int count = 0;
            node x = head;
            while (x != null) {
                count++;
                x = x.getnext();
            }
            return count;
        }
    }

    
    public ILinkedList sublist(int fromIndex, int toIndex) {
            int n =size();
            if (fromIndex < 0 || toIndex > n-1 || fromIndex > toIndex) {
                System.out.print("Error");
                System.exit(0);
            }
            if(toIndex == n-1) {
                SingleLinkedList sub = new SingleLinkedList();
                node current = head;
                for (int i = 0; i < fromIndex; i++) {
                    current = current.getnext();
                }
                sub.head.setval(current.getval());
                while (current.getnext() != null) {
                    current = current.getnext();
                    node temp = current;
                    sub.add(temp.getval());
                }
                sub.add(current.getval());
                return sub;
            }
            
            SingleLinkedList sub = new SingleLinkedList();
            node current = head;
            for (int i = 0; i < fromIndex; i++) {
                current = current.getnext();
            }
            sub.head.setval(current.getval());
            for (int i = fromIndex; i <= toIndex; i++) {
                current = current.getnext();
                node temp = current;
                sub.add(temp.getval());
            }
//            sub.add(current.getval());
            return sub;
        }


    public boolean contains(Object o) {
        int n =size();
        if(n==0) {
            return false;
        }
        else {
            node x =head;
            for(int i=0 ; i < n ;i++) {
                if(x.getval() == (int) o) {
                    return true;
                }
                x=x.getnext();
            }
            return false;
        }
    }
    
     static void print_list(SingleLinkedList sll) {
        if(sll.head == null) {
            System.out.print("[]");
        }
        else {
            node x =sll.head;
            System.out.print("[");
            while (x != null) {
                System.out.print(x.getval());
                if (x.getnext() != null) {
                    System.out.print(", ");
                }
                x = x.getnext();
            }
            System.out.print("]");
        }
    }
 
}

