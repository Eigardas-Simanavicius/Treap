package tree;

import interfaces.Entry;
import interfaces.Position;

import java.io.IOException;

public class BalanceableBinaryTree<K, V> extends LinkedBinaryTree<Entry<K, V>> {

    int aux = 0;
    // positional-based methods related to aux field
    public int getAux(Position<Entry<K, V>> p) {
       
        return ((BSTNode<Entry<K,V>>) p).getAux();
        
    }

    public void setAux(Position<Entry<K, V>> p, int value) {
        ((BSTNode<Entry<K,V>>) p).setAux(value);
    }

    // Override node factory function to produce a BSTNode (rather than a Node)
    @Override
    protected Node<Entry<K, V>> createNode(Entry<K, V> e, Node<Entry<K, V>> parent, Node<Entry<K, V>> left, Node<Entry<K, V>> right) {
        return new BSTNode<>(e, parent, left, right);
    }

    /**
     * Relinks a parent node with its oriented child node.
     */
    private void relink(Node<Entry<K, V>> parent, Node<Entry<K, V>> child, boolean makeLeftChild) {
        child.setParent(parent);
        if(child == root()){
            setRoot(parent);
        }
    
        if (makeLeftChild) {
            parent.setLeft(child);
        }else{
            parent.setRight(child);
        }
    
        
    }

    /**
     * Rotates Position p above its parent. Switches between these configurations,
     * depending on whether p is a or p is b.
     *
     * <pre>
     *          b                  a
     *         / \                / \
     *        a  t2             t0   b
     *       / \                    / \
     *      t0  t1                 t1  t2
     * </pre>
     * <p>
     * Caller should ensure that p is not the root.
     */
    public void rotate(Position<Entry<K, V>> p) {
        Node<Entry<K,V>> x = validate(p);
        Node<Entry<K,V>> y = x.getParent();
        Node<Entry<K, V>> z = y.getParent();


        
        if (z == null) {
            root = x;
            x.setParent(null);

        }else{
            relink(z, x, y == z.getLeft());
        }
       
        if(x == y.getLeft()){
                relink(y, x.getRight(), true);
                 relink(x, y, false);
            
        }else{
            relink(y, x.getLeft(), false);
            relink(x, y ,true);
        }


    }

    /**
     * Returns the Position that becomes the root of the restructured subtree.
     * <p>
     * Assumes the nodes are in one of the following configurations:
     *
     * <pre>
     *     z=a                 z=c           z=a               z=c
     *    /  \                /  \          /  \              /  \
     *   t0  y=b             y=b  t3       t0   y=c          y=a  t3
     *      /  \            /  \               /  \         /  \
     *     t1  x=c         x=a  t2            x=b  t3      t0   x=b
     *        /  \        /  \               /  \              /  \
     *       t2  t3      t0  t1             t1  t2            t1  t2
     * </pre>
     * <p>
     * The subtree will be restructured so that the node with key b becomes its
     * root.
     *
     * <pre>
     *           b
     *         /   \
     *       a       c
     *      / \     / \
     *     t0  t1  t2  t3
     * </pre>
     * <p>
     * Caller should ensure that x has a grandparent.
     */
    public Position<Entry<K, V>> restructure(Position<Entry<K, V>> x) throws IOException {
    
        Position<Entry<K, V>> y = parent(x);
        Position<Entry<K, V>> z = parent(y);

        if(z == null){
            rotate(x);
    
        }
        if( ((x == right(y)) == (y == right(z)))  ) {
            rotate(y);
            return y;
        }else {

            rotate(x);
            rotate(x);
            return x;
        }

    }

    protected static class BSTNode<E> extends Node<E> {
        int aux = 0;

        BSTNode(E e, Node<E> parent, Node<E> leftChild, Node<E> rightChild) {
            super(e, parent, leftChild, rightChild);
        }

        public int getAux() {
            return aux;
        }

        public void setAux(int value) {
            aux = value;
        }

        public String toString() {
            String s = this.getElement() == null ? "" : this.getElement().toString();
            //return s + "/" + height(this);
            return s;
        }
    }


    public static void Main(){
        System.out.println("Balanceable Binary Tree");
    }
}