package list;

import interfaces.List;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.NoSuchElementException;


public class DoublyLinkedList<E> implements List<E> {

    private static class Node<E> {
        private E data; 
        private Node next; 
        private Node prev;

        public Node(E e, Node<E> n,Node<E> p) {
            this.data = e;
            this.next = n;
            this.prev = p;
        }

        public E getData() {
            return data;
        }
        
        public void setData(E data) {
            this.data = data;
        }

        public Node<E> getNext(){
            if(next != null){
            return next;
            }else{
                return null;
            }
        }
        public Node<E> getPrev(){
            if(prev != null){
                return prev;
            }else{
                return null;
            }
        }
        public void setNext(Node<E> n){
            next = n;
        }
        public void setPrev(Node<E> p){
            prev = p;
        }
    }
    private Node<E> head;
    private Node<E> tail;
    private int size = 0;

    public DoublyLinkedList() {
        tail = new Node<>(null,null,null);
        head = new Node<>(null,tail,null);   
        tail.setPrev(head);
    }

    private void addBetween(E e, Node<E> pred, Node<E> succ) {
        Node curr = head;
        while(curr.getNext() != null){
        if(curr == pred && curr.getNext() == succ){
            Node sec = curr.getNext();
           Node<E> created = new Node<E>(e,curr,sec);
           curr.setNext(created);
           sec.setPrev(created);
           size++;
           return;

        }else{
            curr = curr.getNext();
        }
    }
        
        
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public E get(int i) {
        Node curr = head;
        for (int n = 0; n < i; n++) {
            curr = curr.getNext();
        }
        return  (E) curr.getData();
    }

    @Override
    public void add(int i, E e) {
        Node<E> curr = head;
        System.err.println(i);
        
        for (int n = 0; n < i-1; n++) {
            if(curr.getNext() != null){
                curr = curr.getNext();
                
            }
        }
        System.err.println(curr.getData().toString());
        Node<E> next = curr.getNext();
        Node<E> Recent = new Node<>(e, next,curr);
        curr.setNext(Recent);
        next.setPrev(Recent);
        size++;
    }

    @Override
    public E remove(int position) {
        Node<E> curr = head;
        for (int i = 0; i < position-1; i++) {
            if(curr.getNext() != null){
                curr = curr.getNext();
            }
        }
        Node<E> explode = curr.getNext();
        Node<E> next = explode.getNext();

        curr.setNext(next);
        next.setPrev(curr);
        E data = explode.getData();
        explode.setNext(null);
        explode.setPrev(null);
        size--;
        return data;
    }

    private class DoublyLinkedListIterator<E> implements Iterator<E> {
        Node<E> curr = (Node<E>) head;
        
        @Override
        public boolean hasNext() {
            return curr != null;
        }

        @Override
        public E next() {
            if (!hasNext()) throw new NoSuchElementException();
            System.out.println("boop");
            E res = (E) curr.getData();
            curr = curr.getNext();
            return res;
        }
    }

    @Override
    public Iterator<E> iterator() {
        return new DoublyLinkedListIterator<E>();
    }

    

    private E remove(Node<E> n) {
        Node<E> curr = head;
       while(curr == n && curr != null){
        curr = curr.getNext();
       }
        Node<E> explode = curr.getNext();
        Node<E> next = explode.getNext();

        curr.setNext(next);
        next.setPrev(curr);
        E data = explode.getData();
        explode.setNext(null);
        explode.setPrev(null);
        size--;
        return data;
    }

    public E first() {

        return head.getData();
    }

    public E last() {
        if(tail.getData() == null){
            if((tail.getPrev()).getData() != null && tail.getPrev() != head){
                tail = tail.getPrev();
                return tail.getData();
            }else{
                System.out.println((tail.getPrev()).getData());
                return head.getData();
            }
        }else{
        return tail.getData();
        }
    }

    @Override
    public E removeFirst() {
        Node curr = head;
        if(head.getNext() != null){
        head = curr.getNext();
        }
        curr.setNext(null);
        head.setPrev(null);
        size--;
        return (E) curr.getData();
    }

    @Override
    public E removeLast() {
        Node curr = tail;
        tail = tail.getPrev();

        curr.setPrev(null);
        tail.setNext(null);
        size--;
        return (E) curr.getData();
    }

    @Override
    public void addLast(E e) {
        if(tail.getData() == null){
            tail.setData(e);
            
        }else{
            Node curr = tail;
            tail = new Node<E>(e,null,curr);
            curr.setNext(tail);
            if(head.getData() == null){

                head = curr;
                head.setNext(tail);
            }
        
            
            
        }
        size++;
        
        

    }

    @Override
    public void addFirst(E e) {
        Node curr = new Node<E>(e, head, null);
        head.setPrev(curr);
        head = curr;
        if(tail.getData() == null){
            tail = curr;
        }
        size++;


    }
    @Override
    public String toString() {
        Node curr = head;
        String s = "[";
        for (int i = 0;i <= size-1;i++ ) {
            s += curr.getData().toString();

            if( i != size-1){
                s += ",";
            }
        
            curr = curr.getNext();
            
        }
        s += "]";
        return s;

    }

    public void reverseInplace() {
        Node prev = head;
        Node curr = head.getNext();
        Node next = curr.getNext();
        prev.setNext(null);
        prev.setPrev(curr);

        while(next != null){
            curr.setNext(prev);
            curr.setPrev(next);
            tail = prev;
            prev = prev.getNext();
            curr = curr.getNext();
            next.getNext();
            
        }
        curr.setNext(prev);
        head = curr;


    }

    public static void main(String [] args) {
       
        DoublyLinkedList<Integer> ll = new DoublyLinkedList<>();
        for(int i = 0; i < 5; ++i) ll.addLast(i);

        ArrayList<Integer> buf = new ArrayList<>();
        for(Integer i : ll) {
            buf.add(i);
        }
      
        System.out.println(ll.toString());
       System.out.println(buf.toString());
}
}
