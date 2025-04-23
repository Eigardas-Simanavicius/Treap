package list;

import interfaces.List;

import java.util.Iterator;

public class CircularlyLinkedList<E> implements List<E> {

    private class Node<T> {

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
    }

    private Node<E> tail = null;
    private int size = 0;

    public CircularlyLinkedList() {
        tail = new Node<E> (null,tail);

    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public E get(int i) {
        Node curr = tail;
        for (int n = 0; n < i; n++) {
            curr = curr.getNext();
        }
        return  (E) curr.getData();
    
    }

    /**
     * Inserts the given element at the specified index of the list, shifting all
     * subsequent elements in the list one position further to make room.
     *
     * @param i the index at which the new element should be stored
     * @param e the new element to be stored
     */
    @Override
    public void add(int i, E e) {
        Node<E> curr = tail;
        if(curr == null){
            tail.setData(e);
        }else{
         for (int n = 0; n < i-1; n++) {
            if(curr.getNext() != null){
                curr = curr.getNext();
            }
        }
     }
     Node<E> Recent = new Node<>(e, null);
     if(curr.getNext() != null){
        Node<E> next = curr.getNext();
        Recent.setNext(next);;
     }else{
        Recent.setNext(tail);
     }
        curr.setNext(Recent);
        size++;
    }

    @Override
    public E remove(int i) {
        Node<E> curr = tail;
        for (int n = 0;n < size; n++) {
            if(curr.getNext() != null){
                curr = curr.getNext();
            }
        }
        Node<E> explode = curr.getNext();
        Node<E> next = explode.getNext();

        curr.setNext(next);
        E data = explode.getData();
        explode.setNext(null);;
        size--;
        return data;

    }

    public void rotate() {
        Node curr = tail;
        for(int i = 0; i< size-1; i++){
            curr = curr.getNext();
        }
        tail = curr;
    }

    private class CircularlyLinkedListIterator<E> implements Iterator<E> {
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
    public Iterator<E> iterator() {
        return new CircularlyLinkedListIterator<E>();
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public E removeFirst() {
        Node curr = tail;
        if(size > 0){
        tail = tail.getNext();
        for(int i = 0; i< size; i++){
            curr = curr.getNext();
        }
        E n = (E) curr.getData();
        curr.setNext(tail);
        size--;
        return n;
    }
        return null;
    }

    @Override
    public E removeLast() {
        Node curr = tail;
        for(int i = 0; i< size-2; i++){
            curr = curr.getNext();
        }
        E n = (E) (curr.getNext()).getData();

        curr.setNext(tail);
        size--;
        return n;
    }

    @Override
    public void addFirst(E e) {
        Node<E> curr = tail;
        for(int i = 0; i< size-1; i++){
            curr = curr.getNext();
        }
        tail = new Node<>(e, tail);
        curr.setNext(tail);
        size++;
    }

    @Override
    public void addLast(E e) {
        Node curr = tail;
        if(tail.getData() == null){
            tail.setData(e);
            tail.setNext(tail);

        }else{
        for(int i = 0; i< size-1; i++){  
            curr = curr.getNext();
        }

        Node Recent = new Node<>(e, tail);
        if(tail.getNext() == null){
            tail.setNext(Recent);
        }else{
        curr.setNext(Recent);
        }

        }
      
      //  System.out.println(Recent.getData());


        size++;
    }


    public String toString() {
        Node curr = tail;
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
        
         List<Integer> ll = new CircularlyLinkedList<Integer>();
    
        ll.removeFirst();
         ll.addLast(1);
         ll.addLast(2);
         ll.addLast(3);
         
    

    }
}
