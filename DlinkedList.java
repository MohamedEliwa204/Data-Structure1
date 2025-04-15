package com.example;

import java.util.Scanner;

class DNode {
    private int data;
    private DNode next, prev;

    public DNode(int s, DNode p, DNode n) {
        data = s;
        next = n;
        prev = p;
    }

    public int Getdata() {
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

interface ILinkedList {
    ILinkedList sublist(int fromIndex, int toIndex);
}

class Doubly_Linked_List implements ILinkedList {
    private int size;
    private DNode header, trailer;

    public Doubly_Linked_List() {
        size = 0;
        header = new DNode(0, null, null);
        trailer = new DNode(0, header, null);
        header.SetNext(trailer);
    }

    public void ad(int idx, DNode m) {
        if (idx < 0 || idx > size) {
            throw new IndexOutOfBoundsException();
        }

        DNode temp = header;
        for (int i = 0; i < idx; i++) {
            temp = temp.GetNext();
        }

        DNode nextNode = temp.GetNext();
        m.SetPrev(temp);
        m.SetNext(nextNode);
        temp.SetNext(m);
        nextNode.SetPrev(m);
        size++;
    }

    public void add(DNode m) {
        DNode last = trailer.GetPrev();
        m.SetNext(trailer);
        m.SetPrev(last);
        last.SetNext(m);
        trailer.SetPrev(m);
        size++;
    }

    public DNode get(int idx) {
        if (idx < 0 || idx >= size) return null;
        DNode temp = header.GetNext();
        for (int i = 0; i < idx; i++) {
            temp = temp.GetNext();
        }
        return temp;
    }

    public void set(int idx, DNode m) {
        if (idx < 0 || idx >= size) throw new IndexOutOfBoundsException();
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

    public void clear() {
        header.SetNext(trailer);
        trailer.SetPrev(header);
        size = 0;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public void remove(int idx) {
        if (idx < 0 || idx >= size) throw new IndexOutOfBoundsException();
        DNode prev = header;
        for (int i = 0; i < idx; i++) {
            prev = prev.GetNext();
        }
        DNode target = prev.GetNext();
        DNode nextNode = target.GetNext();
        prev.SetNext(nextNode);
        if (nextNode != null) {
            nextNode.SetPrev(prev);
        }
        size--;
    }

    public int Size() {
        return size;
    }

    public ILinkedList sublist(int fromidx, int toidx) {
        if (fromidx < 0 || toidx >= size || fromidx > toidx) {
            throw new IndexOutOfBoundsException();
        }

        Doubly_Linked_List sub = new Doubly_Linked_List();
        DNode current = header.GetNext();
        for (int i = 0; i < fromidx; i++) {
            current = current.GetNext();
        }
        for (int i = fromidx; i <= toidx; i++) {
            DNode temp = new DNode(current.Getdata(), null, null);
            sub.add(temp);
            current = current.GetNext();
        }

        return sub;
    }

    public boolean contain(DNode o) {
        DNode head = header.GetNext();
        while (head != trailer) {
            if (head.Getdata() == o.Getdata()) {
                return true;
            }
            head = head.GetNext();
        }
        return false;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("[");
        DNode current = header.GetNext();
        while (current != trailer) {
            sb.append(current.Getdata());
            if (current.GetNext() != trailer) {
                sb.append(", ");
            }
            current = current.GetNext();
        }
        sb.append("]");
        return sb.toString();
    }
}

public class Solution {
    public static void main(String[] args) {
        try {
            Scanner scanner = new Scanner(System.in);
            String line = scanner.nextLine();
            Doubly_Linked_List list = new Doubly_Linked_List();

            if (!line.equals("[]")) {
                line = line.replaceAll("\\[|\\]", "");
                String[] parts = line.split(", ");
                for (String part : parts) {
                    int val = Integer.parseInt(part.trim());
                    list.add(new DNode(val, null, null));
                }
            }

            String command = scanner.nextLine();
            switch (command) {
                case "add":
                    int addVal = scanner.nextInt();
                    list.add(new DNode(addVal, null, null));
                    System.out.println(list.toString());
                    break;

                case "addToIndex":
                    int idx = scanner.nextInt();
                    int val = scanner.nextInt();
                    list.ad(idx, new DNode(val, null, null));
                    System.out.println(list.toString());
                    break;

                case "get":
                    int getIdx = scanner.nextInt();
                    DNode getNode = list.get(getIdx);
                    if (getNode == null) System.out.println("Error");
                    else System.out.println(getNode.Getdata());
                    break;

                case "set":
                    int setIdx = scanner.nextInt();
                    int newVal = scanner.nextInt();
                    list.set(setIdx, new DNode(newVal, null, null));
                    System.out.println(list.toString());
                    break;

                case "clear":
                    list.clear();
                    System.out.println("[]");
                    break;

                case "remove":
                    int remIdx = scanner.nextInt();
                    list.remove(remIdx);
                    System.out.println(list.toString());
                    break;

                case "isEmpty":
                    System.out.println(list.isEmpty() ? "True" : "False");
                    break;

                case "sublist":
                    int from = scanner.nextInt();
                    int to = scanner.nextInt();
                    System.out.println(((Doubly_Linked_List) list.sublist(from, to)).toString());
                    break;

                case "contains":
                    int target = scanner.nextInt();
                    System.out.println(list.contain(new DNode(target, null, null)) ? "True" : "False");
                    break;

                case "size":
                    System.out.println(list.Size());
                    break;

                default:
                    System.out.println("Error");
            }

        } catch (Exception e) {
            System.out.println("Error");
        }
    }
}
