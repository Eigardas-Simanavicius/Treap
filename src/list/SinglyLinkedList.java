package list;

import interfaces.List;

import java.util.Iterator;

public class SinglyLinkedList<E> implements List<E> {

    private static class Node<E> {
        private E data; 
        private Node next; 

        public Node(E e, Node<E> n) {
            this.data = e;
            this.next = n;
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
        public void setNext(Node<E> n){
            next = n;
        }


    } //----------- end of nested Node class -----------

    /**
     * The head node of the list
     */
    private Node<E> head = null;               // head node of the list (or null if empty)

    /**
     * The last node of the list
     */
    private Node<E> tail = null;               // last node of the list (or null if empty)

    /**
     * Number of nodes in the list
     */
    private int size = 0;                      // number of nodes in the list

    public SinglyLinkedList() { 
    tail = new Node<>(null,null);
    head = new Node<>(null, tail);       


    }              // constructs an initially empty list


    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public E get(int position) {
        Node<E> curr = head;
        for (int i = 0; i < position; i++) {
            if(curr.getNext() != null){
                curr = curr.getNext();
            }else{
                return null;
            }
        }
        return curr.getData();
    }

    @Override
    public void add(int position, E e) {
        Node<E> curr = head;
        for (int i = 0; i < position-1; i++) {
            if(curr.getNext() != null){
                curr = curr.getNext();
            }
        }
        Node<E> next = curr.getNext();
        Node<E> Recent = new Node<>(e, next);
        curr.setNext(Recent);
        size++;


    }


    @Override
    public void addFirst(E e) {
        if(head.getData() == null){
            head.setData(e);
        }else{
        head = new Node<E> (e, head);
        }
        size++;
    }

    @Override
    public void addLast(E e) {
        if(head.getData() == null){
            addFirst(e);
            return;
        }
        else if(tail.getData() == null ){
            tail.setData(e);
        }else{
        Node<E> curr = tail;
        tail = new Node<>(e,null);
        curr.setNext(tail);     

        }
        
        size++;
    }

    @Override
    public E remove(int position) {
        Node<E> curr = head;
        for (int i = 0; i < position+1; i++) {
            if(curr.getNext() != null){
                curr = curr.getNext();
            }
        }
        
        size--;
        return curr.getData();
    }

    @Override
    public E removeFirst() {
        Node<E> curr = head;
        head = head.getNext();
        curr.setNext(null);
        if(curr.getData() != null){
        size--;
        }
        return curr.getData();
    }

    @Override
    public E removeLast() {
    Node<E> curr = head;
    while((curr.getNext()).getNext() != null){
        curr = curr.getNext();
    }
        E removed = tail.getData();
        tail = curr;
        tail.setNext(null);
        size--;
        return removed;
    }

     @Override
     public Iterator<E> iterator() {
        return new SinglyLinkedListIterator<E>();
    }

    private class SinglyLinkedListIterator<E> implements Iterator<E> {
        Node curr; 
        @Override
        public boolean hasNext() {
        
            return curr != null;
        }

        @Override
        public E next() {
            E res = (E) curr.getData();
            curr = curr.getNext();
            return res;
        }
    
    }

    @Override
    public String toString() {
        Node curr = head;
        String s = "[";
        for (int i = 0;i <= size-1;i++ ) {
            s += curr.getData().toString();
            if(i != size-1){
                s += ",";
            }
            curr = curr.getNext();
        }
        s += "]";
        return s;

    }

    public static void main(String[] args) {
        SinglyLinkedList<Integer> ll = new SinglyLinkedList<Integer>();
        System.out.println("ll " + ll + " isEmpty: " + ll.isEmpty());
        //LinkedList<Integer> ll = new LinkedList<Integer>();

        ll.addLast(1);
        ll.addLast(2);
        ll.addLast(3);
        ll.add(1, 100);

        System.out.println(ll.toString());
    

    }
}
