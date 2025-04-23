package tree;

import interfaces.BinaryTree;
import interfaces.Position;
import list.SinglyLinkedList;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.stream.IntStream;

/**
 * Concrete implementation of a binary tree using a node-based, linked
 * structure.
 */
public class LinkedBinaryTree<E extends Comparable<E>> implements BinaryTree<E> {


    protected Node<E> root = null; // root of the tree

    // LinkedBinaryTree instance variables
    protected int size = 0; // number of nodes in the tree

    public LinkedBinaryTree() {
        Node<E> root = new Node<E>(null, null, null, null);
    } 

    public static <E extends Comparable<E>> LinkedBinaryTree<E> makeRandom(int n, E[] arr) {
        LinkedBinaryTree<E> bt = new LinkedBinaryTree<>();
        bt.root = randomTree(null, 0, n - 1, arr);
        bt.size = n;
        return bt;
    }

    public static LinkedBinaryTree<Integer> makeRandom(int n) {
        LinkedBinaryTree<Integer> bt = new LinkedBinaryTree<>();
        Integer[] arr = IntStream.range(0, n).boxed().toArray(Integer[]::new);
        bt.root = randomTree(null, 0, n - 1, arr);
        bt.size = n;
        return bt;
    }
    public static <E> Node<E> randomTree(Node<E> parent, int first, int last, E[] arr) {
        java.util.Random r = new java.util.Random();

        if(first > last) { // if the first element is larger than the last one, no tree :(
            return null;
        }else{
            int treeSize = last - first + 1;
            int leftCount = r.nextInt(treeSize); // picks a random number up to the tree max size
            int rightCount = treeSize - leftCount -1 ; // the size of the tree is whatever is left
            int index = first + leftCount;

            Node<E> n = new Node<E>(arr[index], parent, null, null);
            n.setLeft(randomTree(n, first, first+leftCount -1, arr));
            n.setRight(randomTree(n, first+leftCount+1, last, arr));
            return n;
        }
    }


    public static void main(String[] args) throws IOException, ExecutionException, InterruptedException {

        Integer [] arr = new Integer[] {1,2,3,4,5,6};
        LinkedBinaryTree<Integer> bt = makeRandom(6,arr);
        System.out.println(bt.toBinaryTreeString() );


    }

    public boolean isEmpty() {
        return size() == 0;
    }

    /**
     * Returns the Position of p's sibling (or null if no sibling exists).
     *
     * @param p A valid Position within the tree
     * @return the Position of the sibling (or null if no sibling exists)
     * @throws IllegalArgumentException if p is not a valid Position for this tree
     */
    public Position<E> sibling(Position<E> p) {
       Position<E> curr = parent(p);
       left(curr);
       if( left(curr) != p ){
        return left(curr);
       }else{
        return right(curr);
       }
    }

    @Override
    public Iterator<E> iterator() {
        return new ElementIterator();
    }

    /**
     * Returns true if Position p has one or more children.
     *
     * @param p A valid Position within the tree
     * @return true if p has at least one child, false otherwise
     * @throws IllegalArgumentException if p is not a valid Position for this tree.
     */
    @Override
    public boolean isInternal(Position<E> p) {
        return numChildren(p) > 0;
    }

    /**
     * Adds positions of the subtree rooted at Position p to the given
     * snapshot using an inorder traversal
     *
     * @param p        Position serving as the root of a
     * @param snapshot a list to which results are appended
     */
    private void inorderSubtree(Position<E> p, List<Position<E>> snapshot) {
      
        if(!isExternal(left(p))){
              inorderSubtree(left(p), snapshot);
        }
        snapshot.add(p);
        if(!isExternal(right(p))){
            inorderSubtree(right(p), snapshot);
        }
    }

    /**
     * Adds positions of the subtree rooted at Position p to the given
     * snapshot using a preorder traversal
     *
     * @param p        Position serving as the root of a subtree
     * @param snapshot a list to which results are appended
     */
    private void preorderSubtree(Position<E> p, List<Position<E>> snapshot) {
        snapshot.add(p);
        if(left(p) != null){
            preorderSubtree(left(p), snapshot);
        }
        if(right(p) != null){
            preorderSubtree(right(p), snapshot);
        }

    }

    /**
     * Returns an iterable collection of positions of the tree, reported in preorder.
     *
     * @return iterable collection of the tree's positions in preorder
     */
    public Iterable<Position<E>> preorder() {
        List<Position <E>> snapshot = new ArrayList<>();
        preorderSubtree(root, snapshot);
        return snapshot;
    }

    /**
     * Returns an iterable collection of positions of the tree, reported in inorder.
     *
     * @return iterable collection of the tree's positions reported in inorder
     */
    public Iterable<Position<E>> inorder() {
        List<Position <E>> snapshot = new ArrayList<>();
        inorderSubtree(root, snapshot);
        return snapshot;
    }

    /**
     * Adds positions of the subtree rooted at Position p to the given
     * snapshot using a postorder traversal
     *
     * @param p        Position serving as the root of a subtree
     * @param snapshot a list to which results are appended
     */
    private void postorderSubtree(Position<E> p, List<Position<E>> snapshot) {
        if(left(p) != null){

            postorderSubtree(left(p), snapshot);
        }
        if(right(p) != null){
            postorderSubtree(right(p), snapshot);
        }
        snapshot.add(p);
    }

    /**
     * Returns an iterable collection of positions of the tree, reported in postorder.
     *
     * @return iterable collection of the tree's positions in postorder
     */
    public Iterable<Position<E>> postorder() {
        List<Position <E>> snapshot = new ArrayList<>();
        preorderSubtree(root, snapshot);
        return snapshot;
    }

    public Iterable<Position<E>> positions() {
        return inorder();
    }

    /**
     * Returns the number of levels separating Position p from the root.
     *
     * @param p A valid Position within the tree
     * @throws IllegalArgumentException if p is not a valid Position for this tree.
     */
    public int depth(Position<E> p) throws IllegalArgumentException {
        if(isRoot(p)){
            return 0;
        }
        return 1 + depth(parent(p));
    }

    /**
     * Returns the height of the tree.
     * <p>
     * Note: This implementation works, but runs in O(n^2) worst-case time.
     */
    private int heightBad() {
        int h = 0; 
            for (Position<E> p : positions()) 
            { if (isExternal(p)) 
                {          
                     h = Math.max(h, depth(p));        
                }     
            } 
        return h; 

    }

    /**
     * Returns the height of the subtree rooted at Position p.
     *
     * @param p A valid Position within the tree
     * @throws IllegalArgumentException if p is not a valid Position for this tree.
     */
    public int height(Position<E> p) throws IllegalArgumentException {
        int h = 0;
        for(Position<E> c: children(p)){
            h = Math.max(h, 1+height(c));
        }
        return h;
    }

    /**
     * Returns true if Position p represents the root of the tree.
     *
     * @param p A valid Position within the tree
     * @return true if p is the root of the tree, false otherwise
     */
    public boolean isRoot(Position<E> p) {
        return p == root();
    }
    // nonpublic utility

    /**
     * Returns true if Position p does not have any children.
     *
     * @param p A valid Position within the tree
     * @return true if p has zero children, false otherwise
     * @throws IllegalArgumentException if p is not a valid Position for this tree.
     */
    public boolean isExternal(Position<E> p) {
        return left(p) == null && right(p) == null;
    }

    /**
     * Returns an iterable collection of the Positions representing p's children.
     *
     * @param p A valid Position within the tree
     * @return iterable collection of the Positions of p's children
     * @throws IllegalArgumentException if p is not a valid Position for this tree.
     */
    public Iterable<Position<E>> children(Position<E> p) {
        List<Position <E>> children = new ArrayList<>();
        if(left(p) != null){
            children.add(left(p));
        }
        if(right(p) != null){
            children.add(right(p));
        }
        
        return children;
    }

    /**
     * Returns the number of children of Position p.
     *
     * @param p A valid Position within the tree
     * @return number of children of Position p
     * @throws IllegalArgumentException if p is not a valid Position for this tree.
     */
    public int numChildren(Position<E> p) {
        int n = 0;
        if(left(p) != null){
            n++;          
        }
        if(right(p) != null){
            n++;
        }
        
        return n;
    }

    // Function to find minimum value node in a given BST
    private Node<E> findMinimum(Node<E> n) {
        return null;
    }

    // Function to find minimum value node in a given BST
    private Node<E> findMaximum(Node<E> n) {
        // TODO
        return null;
    }

    // Recursive function to find an inorder successor
    private Node<E> inorderSuccessor(Node<E> node, Node<E> succ, E key) {
        // TODO
        return null;

    }

    private Node<E> inorderPredecessor(Node<E> node, Node<E> pred, E key) {
        // TODO
        return null;
    }

    public Position<E> inorderSuccessor(E key) {
        return inorderSuccessor(root, null, key);
    }

    public Position<E> inorderPredecessor(E key) {
        return inorderPredecessor(root, null, key);
    }


    /**
     * Returns an iterable collection of positions of the tree in breadth-first order.
     *
     * @return iterable collection of the tree's positions in breadth-first order
     */
    public Iterable<Position<E>> breadthfirst() {
        interfaces.List<Position<E>> snapshot = new SinglyLinkedList<>();
        if (!isEmpty()) {
            java.util.Queue<Position<E>> q = new java.util.LinkedList<>();
            q.add(root());                 // start with the root
            while (!q.isEmpty()) {
                Position<E> p = q.remove();     // remove from front of the queue
                snapshot.addLast(p);                      // report this position
                children(p).forEach(c -> q.add(c));           // add children to back of queue
            }
        }
        return snapshot;

    }

    public void construct(E[] inorder, E[] preorder) {
        // TODO
    }

    private Node<E> construct_tree(E[] inorder, E[] preorder, int pStart, int pEnd, int iStart, int iEnd) {
        // TODO
        return null;
    }


    /**
     * Factory function to create a new node storing element e.
     */
    protected Node<E> createNode(E e, Node<E> parent, Node<E> left, Node<E> right) {
        return new Node<E>(e, parent, left, right);
    }

    /**
     * Verifies that a Position belongs to the appropriate class, and is not one
     * that has been previously removed. Note that our current implementation does
     * not actually verify that the position belongs to this particular list
     * instance.
     *
     * @param p a Position (that should belong to this tree)
     * @return the underlying Node instance for the position
     * @throws IllegalArgumentException if an invalid position is detected
     */
    protected Node<E> validate(Position<E> p) throws IllegalArgumentException {
        if (!(p instanceof Node<E> node)) throw new IllegalArgumentException("Not valid position type");
        // safe cast
        if (node.getParent() == node) // our convention for defunct node
            throw new IllegalArgumentException("p is no longer in the tree");
        return node;
    }

    /**
     * Returns the number of nodes in the tree.
     *
     * @return number of nodes in the tree
     */
    public int size() {
        return size;
    }

    /**
     * Returns the root Position of the tree (or null if tree is empty).
     *
     * @return root Position of the tree (or null if tree is empty)
     */
    public Position<E> root() {
    
        return this.root;
    }

    /**
     * Returns the Position of p's parent (or null if p is root).
     *
     * @param p A valid Position within the tree
     * @return Position of p's parent (or null if p is root)
     * @throws IllegalArgumentException if p is not a valid Position for this tree.
     */
    public Position<E> parent(Position<E> p) throws IllegalArgumentException {
        return ((Node<E>) p).getParent();
    }

    /**
     * Returns the Position of p's left child (or null if no child exists).
     *
     * @param p A valid Position within the tree
     * @return the Position of the left child (or null if no child exists)
     * @throws IllegalArgumentException if p is not a valid Position for this tree
     */
    public Position<E> left(Position<E> p) throws IllegalArgumentException {

        return ((Node<E>) p).getLeft();
    }

    // update methods supported by this class

    /**
     * Returns the Position of p's right child (or null if no child exists).
     *
     * @param p A valid Position within the tree
     * @return the Position of the right child (or null if no child exists)
     * @throws IllegalArgumentException if p is not a valid Position for this tree
     */
    public Position<E> right(Position<E> p) throws IllegalArgumentException {
        if (p == null) {return null;}
        return ((Node<E>) p).getRight();
    }

    /**
     * Places element e at the root of an empty tree and returns its new Position.
     *
     * @param e the new element
     * @return the Position of the new element
     * @throws IllegalStateException if the tree is not empty
     */
    public Position<E> addRoot(E e) throws IllegalStateException {
        size = 1;
        root = createNode(e,null,null,null);
        return root;
    }

    /*
     * Create a detached node!
     */
    public Position<E> add(E e, Position<E> parent, Position<E> left, Position<E> right) {
        Node<E> node = createNode(e,(Node<E>) parent,(Node<E>) left,(Node<E>) right);
        size++;
        return node;
    }

    /**
     * Creates a new left child of Position p storing element e and returns its
     * Position.
     *
     * @param p the Position to the left of which the new element is inserted
     * @param e the new element
     * @return the Position of the new element
     * @throws IllegalArgumentException if p is not a valid Position for this tree
     * @throws IllegalArgumentException if p already has a left child
     */
    public Position<E> addLeft(Position<E> p, E e) throws IllegalArgumentException {
        Node<E> child = createNode(e, (Node<E>) p, null, null);
       ((Node<E>) p).setLeft(child);
        size++;
        return child;
    }

    /**
     * Creates a new right child of Position p storing element e and returns its
     * Position.
     *
     * @param p the Position to the right of which the new element is inserted
     * @param e the new element
     * @return the Position of the new element
     * @throws IllegalArgumentException if p is not a valid Position for this tree.
     * @throws IllegalArgumentException if p already has a right child
     */
    public Position<E> addRight(Position<E> p, E e) throws IllegalArgumentException {
        Node<E> parent = validate(p);
        Node<E> child = createNode(e, parent, null, null);
        parent.setRight(child);
        size++;
        return child;
    }

    /**
     * Replaces the element at Position p with element e and returns the replaced
     * element.
     *
     * @param p the relevant Position
     * @param e the new element
     * @return the replaced element
     * @throws IllegalArgumentException if p is not a valid Position for this tree.
     */
    public E set(Position<E> p, E e) throws IllegalArgumentException {
        Node<E> node = validate(p);
        E temp = node.getElement();
        node.setElement(e);

        return temp;
    }

    public void setRoot(Position<E> e) throws IllegalArgumentException {
        Node<E> node = validate(e);
        root = node;
    }

    /**
     * Removes the node at Position p and replaces it with its child, if any.
     *
     * @param p the relevant Position
     * @return element that was removed
     * @throws IllegalArgumentException if p is not a valid Position for this tree.
     * @throws IllegalArgumentException if p has two children.
     */
    public E remove(Position<E> p) throws IllegalArgumentException {
        if(numChildren(p) > 2){
            throw new IllegalArgumentException("Too many children");
        }
        if(isExternal(left(p)) && isExternal(right(p)) ){
            if(p == left(parent(p))){
                addLeft(parent(p),null);
            }else if(p == right(parent(p))){
                addRight(parent(p),null);
            }
            size--;

        }
        E removed = p.getElement();
        ((Node<E>) p).setElement(null);
        if (numChildren(p) == 1){
            p = left(p);
            ((Node<E>) p).setParent(((Node<E>) p).getParent());
        }

        size--;
        return removed;
    }

    public String toString() {
        return positions().toString();
    }

    public void createLevelOrder(ArrayList<E> l) {
        root = createLevelOrderHelper(l, root, 0);
    }

    private Node<E> createLevelOrderHelper(ArrayList<E> l, Node<E> p, int i) {
        if (i < l.size()) {
            Node<E> n = null;
            if (l.get(i) != null) {
                n = createNode(l.get(i), p, null, null);
                n.left = createLevelOrderHelper(l, n, 2 * i + 1);
                n.right = createLevelOrderHelper(l, n, 2 * i + 2);
                ++size;
            }
            return n;
        }
        return p;
    }

    public void createLevelOrder(E[] arr) {
        root = createLevelOrderHelper(arr, root, 0);
    }

    private Node<E> createLevelOrderHelper(E[] arr, Node<E> parent, int i) {
        // corrected 2024
        if (i < arr.length && arr[i] != null) {
            Node<E> n = createNode(arr[i], parent, null, null);
            n.left = createLevelOrderHelper(arr, n, 2 * i + 1);
            n.right = createLevelOrderHelper(arr, n, 2 * i + 2);
            ++size;
            return n;
        }
        return null;
    }


    public String toBinaryTreeString() {
        BinaryTreePrinter<E> btp = new BinaryTreePrinter<>(this);
        return btp.print();
    }

    /*
     * Nested static class for a binary tree node.
     */
    protected static class Node<E> implements Position<E> {
        private E element;
        private Node<E> left, right, parent;

        public Node(E e, Node<E> p, Node<E> l, Node<E> r) {
            element = e;
            left = l;
            right = r;
            parent = p;
        }

        // accessor
        public E getElement() {
            return element;
        }

        // modifiers
        public void setElement(E e) {
            element = e;
        }

        public Node<E> getLeft() {
            return left;
        }

        public void setLeft(Node<E> n) {
            left = n;
        }

        public Node<E> getRight() {
            return right;
        }

        public void setRight(Node<E> n) {
            right = n;
        }

        public Node<E> getParent() {
            return parent;
        }

        public void setParent(Node<E> n) {
            
            parent = n;
        }

        public String toString() {
            // (e)
            StringBuilder sb = new StringBuilder();
            if (element == null) {
                sb.append("\u29B0");
            } else {
                sb.append(element);
            }
            // sb.append(" l:").append(left.element).append(" r:").append(right.element);
            // sb.append();
            return sb.toString();
        }
    }

    /* This class adapts the iteration produced by positions() to return elements. */
    private class ElementIterator implements Iterator<E> {
        Iterator<Position<E>> posIterator = positions().iterator();

        public boolean hasNext() {
            return posIterator.hasNext();
        }

        public E next() {
            return posIterator.next().getElement();
        }

        public void remove() {
            posIterator.remove();
        }
    }

}
