package tree;

import interfaces.Entry;
import interfaces.Position;

import java.io.IOException;
import java.util.Comparator;



/**
 *
 * Implementation of Treap class
 * This is done through the use of a new Entry type, TreapEntry,
 * which holds the primitive P, this also allows us to reuse all other classes
 *
 * @author Eigardas Simanavius
 *
 */
public class Treap <K extends  Comparable<K>, V> extends TreeMap<K, V> {

    int MaxSize = 1;
    public Treap() {
        super();
        tree.addRoot(null);
    }

    public Treap(Comparator<K> comp) {
        super(comp); // the AbstractSortedMap constructor
        tree.addRoot(null); // create a sentinel leaf as root
    }

    // you can call Treap with the Maxsize function which will let the priorities scale up to the size of the array
    public Treap(int MaxSize) {
        super(); // the AbstractSortedMap constructor
        tree.addRoot(null); // create a sentinel leaf as root
        this.MaxSize = MaxSize;
    }


    public static void main(String[] args) throws IOException {
        Treap<Integer, Integer> treap = new Treap<>();
        Integer[] keys = new Integer[]{22, 16, 49, 79, 41, 40, 93, 58, 68, 66};

        for (int i = 0; i < keys.length; i++) {
            treap.put   (keys[i], keys[i]);
        }

        System.out.println(treap.toBinaryTreeString());
    }

    //overrides put to create a TreapEntry value, and give it a priority
    @Override
    public V put(K key, V value) throws IllegalArgumentException, IOException {

        TreapEntry<K, V> entry = new TreapEntry<K, V>(key, value, prioBuilder());
        Position<Entry<K, V>> p = treeSearch(tree.root(), key);

        if (tree.isExternal(p)) {
            expandExternal(p, entry);
            if (!tree.isRoot(p)) {
                rebalanceInsert(p);
            }
            return null;
        } else {
            Entry<K, V> old = p.getElement();
            tree.set(p, entry);
            rebalanceInsert(p);
            return old.getValue();
        }
    }


    //overrides the remove function
    @Override
    public V remove(K key) throws IOException {
        Position<Entry<K, V>> p = treeSearch(tree.root(), key);
        V old = p.getElement().getValue();
        if (tree.isExternal(p)) {

            return null;
        }
        //rotates the node down to the leaf positions

        // continuesly rotates down the node , in a way that does not break heap order
        while (!tree.isExternal(tree.left(p)) || !tree.isExternal(tree.right(p))) {
            if (tree.numChildren(p) == 2 && !tree.isExternal(tree.left(p)) && !tree.isExternal(tree.right(p))) {
                if (getPriority(tree.right(p).getElement()) > (getPriority(tree.left(p).getElement())) ) {
                    tree.rotate(tree.right(p));
                } else {
                    tree.rotate(tree.left(p));
                }
            } else if (!tree.isExternal(tree.left(p))) {
                tree.rotate(tree.left(p));
            } else {
                tree.rotate(tree.right(p));
            }
        }
        // then removes it
        tree.remove(p);

        return old;
    }

    // reblanaces an insert into heap order
    @Override
    protected void rebalanceInsert(Position<Entry<K, V>> p) {
        while (!tree.isRoot(p) && getPriority(p.getElement()) > (getPriority(tree.parent(p).getElement())) ) {
            tree.rotate(p);
            if (tree.parent(p) == null) {
                tree.setRoot(p);
            }
        }
    }

    //returns an TreapEntries priority
    private  int getPriority(Entry<K, V> entry) {
        return ((TreapEntry<K,V>)entry).getPriority() ;
    }

    // generates a random number
    private int prioBuilder() {
        return (int) (Math.random() * (10000+MaxSize));
    }

}




      



