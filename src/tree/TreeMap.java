package tree;

import interfaces.Entry;
import interfaces.Position;
import utils.MapEntry;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * An implementation of a sorted map using a binary search tree.
 */

public class TreeMap<K extends Comparable<K>, V > extends AbstractSortedMap<K, V> {


	protected BalanceableBinaryTree<K, V> tree = new BalanceableBinaryTree<>();

	/** Constructs an empty map using the natural ordering of keys. */
	public TreeMap() {
		super(); // the AbstractSortedMap constructor
		tree.addRoot(null); // create a sentinel leaf as root
	}

	/**
	 * Constructs an empty map using the given comparator to order keys.
	 *
	 * @param comp comparator defining the order of keys in the map
	 */
	public TreeMap(Comparator<K> comp) {
		super(comp); // the AbstractSortedMap constructor
		tree.addRoot(null); // create a sentinel leaf as root
	}


	/**
	 * Returns the number of entries in the map.
	 *
	 * @return number of entries in the map
	 */
	@Override
	public int size() {
		return (tree.size() - 1) / 2; // only internal nodes have entries
	}

	protected Position<Entry<K, V>> restructure(Position<Entry<K, V>> x) throws IOException {
		return tree.restructure(x);
	}

	/**
	 * Rebalances the tree after an insertion of specified position. This version of
	 * the method does not do anything, but it can be overridden by subclasses.
	 *
	 * @param p the position which was recently inserted
	 */
	protected void rebalanceInsert(Position<Entry<K, V>> p) throws IOException {

	}

	/**
	 * Rebalances the tree after a child of specified position has been removed.
	 * This version of the method does not do anything, but it can be overridden by
	 * subclasses.
	 *
	 * @param p the position of the sibling of the removed leaf
	 */
	protected void rebalanceDelete(Position<Entry<K, V>> p) throws IOException {
		// LEAVE EMPTY
	}

	/**
	 * Rebalances the tree after an access of specified position. This version of
	 * the method does not do anything, but it can be overridden by a subclasses.
	 *
	 * @param p the Position which was recently accessed (possibly a leaf)
	 */
	protected void rebalanceAccess(Position<Entry<K, V>> p) throws IOException {
		// LEAVE EMPTY
	}

	/** Utility used when inserting a new entry at a leaf of the tree */
	protected void expandExternal(Position<Entry<K, V>> p, Entry<K, V> entry) {
		tree.set(p, entry);
		tree.addLeft(p,null);
		tree.addRight(p,null);
	}
	

	/**
	 * Returns the position in p's subtree having the given key (or else the
	 * terminal leaf).
	 *
	 * @param key a target key
	 * @param p   a position of the tree serving as root of a subtree
	 * @return Position holding key, or last node reached during search
	 */
	protected Position<Entry<K, V>> treeSearch(Position<Entry<K, V>> p, K key) {
		if(tree.isExternal(p)){
			return p;
		}else if(key.compareTo(p.getElement().getKey()) == 0) {
			return p;
		}
		else if(key.compareTo(p.getElement().getKey()) < 0){
			return treeSearch(tree.left(p), key);
		}else{
			return treeSearch(tree.right(p), key);
		}

	}

	/**
	 * Returns position with the minimal key in the subtree rooted at Position p.
	 *
	 * @param p a Position of the tree serving as root of a subtree
	 * @return Position with minimal key in subtree
	 */
	protected Position<Entry<K, V>> treeMin(Position<Entry<K, V>> p) {
		if(tree.isExternal(p) ){
			return p;
		}else if(tree.isExternal(tree.left(p))){
			return p;
		}
		else{
			return treeMin(tree.left(p));
		}
	}

	/**
	 * Returns the position with the maximum key in the subtree rooted at p.
	 *
	 * @param p a Position of the tree serving as root of a subtree
	 * @return Position with maximum key in subtree
	 */
	protected Position<Entry<K, V>> treeMax(Position<Entry<K, V>> p) {
		if(tree.isExternal(p) ){
			return p;
		}else if(tree.isExternal(tree.right(p))){
			return p;
		}
		else{
			return treeMax(tree.right(p));
		}
	}


	/**
	 * Returns the value associated with the specified key, or null if no such entry
	 * exists.
	 *
	 * @param key the key whose associated value is to be returned
	 * @return the associated value, or null if no such entry exists
	 */
	@Override
	public V get(K key) throws IllegalArgumentException, IOException {
		return treeSearch(tree.root(), key).getElement().getValue();
	}

	/**
	 * Associates the given value with the given key. If an entry with the key was
	 * already in the map, this replaced the previous value with the new one and
	 * returns the old value. Otherwise, a new entry is added and null is returned.
	 *
	 * @param key   key with which the specified value is to be associated
	 * @param value value to be associated with the specified key
	 * @return the previous value associated with the key (or null, if no such
	 *         entry)
	 */
	@Override
	public V put(K key, V value) throws IllegalArgumentException, IOException {

		Position<Entry<K, V>> p = treeSearch(tree.root(), key);
		Entry<K, V> entry = new MapEntry<>(key, value);

		if(tree.isExternal(p)){
				expandExternal(p, entry);
				if(!tree.isRoot(p)) {
					rebalanceInsert(p);
				}
			return null;
		}else{
			Entry<K,V> old = p.getElement();
			tree.set(p, entry);
			rebalanceInsert(p);
			return old.getValue();
		}

	}

	/**
	 * Removes the entry with the specified key, if present, and returns its
	 * associated value. Otherwise does nothing and returns null.
	 *
	 * @param key the key whose entry is to be removed from the map
	 * @return the previous value associated with the removed key, or null if no
	 *         such entry exists
	 */
	@Override
	public V remove(K key) throws IllegalArgumentException, IOException {
		Position<Entry<K,V>> p = treeSearch(tree.root(), key);

		V old = p.getElement().getValue();
		if(tree.isExternal(p)) {
			tree.remove(treeSearch(tree.root(), key));
			return null;

		}else if(tree.isInternal(tree.left(p)) && tree.isInternal(tree.right(p))){
				 Position<Entry<K,V>> replacement = treeMax(tree.left(p));
				 tree.set(p, replacement.getElement());
				 p = replacement;

		}


		Position<Entry<K, V>> leaf = (tree.isExternal(tree.left(p)) ? tree.right(p) : tree.left(p));
		if(!tree.isExternal(leaf)) {

			rotate(leaf);
		};
		tree.remove(p);

		rebalanceDelete(p);
		return old;
	}

	// additional behaviors of the SortedMap interface

	/**
	 * Returns the entry having the least key (or null if map is empty).
	 *
	 * @return entry with least key (or null if map is empty)
	 */
	@Override
	public Entry<K, V> firstEntry() {
		return treeMin(tree.root()).getElement();
	}

	/**
	 * Returns the entry having the greatest key (or null if map is empty).
	 *
	 * @return entry with greatest key (or null if map is empty)
	 */
	@Override
	public Entry<K, V> lastEntry() {
		return treeMax(tree.root()).getElement();
	}

	/**
	 * Returns the entry with least key greater than or equal to given key (or null
	 * if no such key exists).
	 *
	 * @return entry with least key greater than or equal to given (or null if no
	 *         such entry)
	 * @throws IllegalArgumentException if the key is not compatible with the map
	 */
	@Override
	public Entry<K, V> ceilingEntry(K key) throws IllegalArgumentException {
		Position<Entry<K,V>> p = tree.root();
		while(p != null){
			// if the key is smaller than the current k
			if(key.compareTo(p.getElement().getKey()) < 0) {
				// if there exists a left child go left
				if (tree.left(p).getElement() != null) {
					p = tree.left(p);
				} else {
					// else return p, biggest smallest
					return p.getElement();
				}
				// if the key is larger than the current key
			} else if (key.compareTo(p.getElement().getKey()) == 0) {
				return p.getElement();

			} else if (key.compareTo(p.getElement().getKey()) > 0) {
				// go to the next largerst
				if(tree.right(p).getElement() != null){
					p = tree.right(p);
				}else {
					Position<Entry<K,V>> Parent = tree.parent(p);

					while(Parent != null && p == tree.right(Parent)){
						p = Parent;
						Parent = tree.parent(p);
					}
					return Parent.getElement();

				}

			} else {
				return p.getElement();
			}
		}

		return null;

	}

	/**
	 * Returns the entry with greatest key less than or equal to given key (or null
	 * if no such key exists).
	 *
	 * @return entry with greatest key less than or equal to given (or null if no
	 *         such entry)
	 * @throws IllegalArgumentException if the key is not compatible with the map
	 */
	@Override
	public Entry<K, V> floorEntry(K key) throws IllegalArgumentException {
		Position<Entry<K,V>> p = tree.root();
		while(p != null){
			// key bigger
			if(key.compareTo(p.getElement().getKey()) >= 0) {
				if (tree.right(p).getElement() != null) {
					p = tree.right(p);
				}else {
					return p.getElement();
				}
			}else if(key.compareTo(p.getElement().getKey()) < 0) {
				if(tree.left(p).getElement() != null){
					p = tree.left(p);}
				else{
					Position<Entry<K,V>> Parent = tree.parent(p);
					while(Parent != null && p == tree.left(Parent)){
						p = Parent;
						Parent = tree.parent(p);
					}
					return Parent.getElement();

				}
			}else{
				return p.getElement();
			}
		}
		return null;
	}

	/**
	 * Returns the entry with greatest key strictly less than given key (or null if
	 * no such key exists).
	 *
	 * @return entry with greatest key strictly less than given (or null if no such
	 *         entry)
	 * @throws IllegalArgumentException if the key is not compatible with the map
	 */
	@Override
	public Entry<K, V> lowerEntry(K key) throws IllegalArgumentException {

		Position<Entry<K,V>> p = treeSearch(tree.root(), key);
		if (tree.isInternal(p) && tree.isInternal(tree.left(p))) {
			return treeMax(tree.left(p)).getElement();
		}
		while (!tree.isRoot(p)) {
			if (p == tree.right(tree.parent(p)))
				return tree.parent(p).getElement( ); // parent has next lesser key
			 else
			 p = tree.parent(p);
			 }

		return null;
	}

	/**
	 * Returns the entry with least key strictly greater than given key (or null if
	 * no such key exists).
	 *
	 * @return entry with least key strictly greater than given (or null if no such
	 *         entry)
	 * @throws IllegalArgumentException if the key is not compatible with the map
	 */
	@Override
	public Entry<K, V> higherEntry(K key) throws IllegalArgumentException {
		Position<Entry<K,V>> p = treeSearch(tree.root(), key);
		if (tree.isInternal(p) && tree.isInternal(tree.right(p))) {
			return treeMin(tree.right(p)).getElement();
		}
		while (!tree.isRoot(p)) {
			if (p == tree.left(tree.parent(p)))
				return tree.parent(p).getElement( ); // parent has next lesser key
			else
				p = tree.parent(p);
		}


		return null;


	}

	// Support for iteration

	/**
	 * Returns an iterable collection of all key-value entries of the map.
	 *
	 * @return iterable collection of the map's entries
	 */
	@Override
	public Iterable<Entry<K, V>> entrySet() {
		List<Entry<K,V>> set = new ArrayList<>();
		Iterable<Position<Entry<K,V>>> p = tree.inorder();
		for (Position<Entry<K,V>> p1 : p) {
			if(tree.isInternal(p1)) {
				set.add(p1.getElement());
			}
		}
		return set;
	}



	@Override
	public double loadFactor() {
		return 0;
	}

	@Override
	public int numCollisions() {
		return 0;
	}


	public String toString() {
		return tree.toString();
	}

	/**
	 * Returns an iterable containing all entries with keys in the range from
	 * <code>fromKey</code> inclusive to <code>toKey</code> exclusive.
	 *
	 * @return iterable with keys in desired range
	 * @throws IllegalArgumentException if <code>fromKey</code> or
	 *                                  <code>toKey</code> is not compatible with
	 *                                  the map
	 */
	@Override
	public Iterable<Entry<K, V>> subMap(K fromKey, K toKey) throws IllegalArgumentException {
		ArrayList<Entry<K,V>> set = new ArrayList<>();

		if(fromKey.compareTo(toKey) < 0) {
			subMapRecurse(fromKey,toKey,tree.root(),set);
		}
		return set;
	}

	// utility to fill subMap buffer recursively (while maintaining order)
	private void subMapRecurse(K fromKey, K toKey, Position<Entry<K, V>> p,
							   ArrayList<Entry<K, V>> buffer) {
		if(tree.isInternal(p)){

			if(compare(p.getElement().getKey(),fromKey) < 0) {

				subMapRecurse(fromKey,toKey,tree.right(p),buffer);
			}else {
				subMapRecurse(fromKey,toKey,tree.left(p),buffer);
				if(p.getElement().getKey().compareTo(toKey) < 0) {

					buffer.add(p.getElement());
					subMapRecurse(fromKey,toKey,tree.right(p),buffer);

				}
			}
		}
	}
	
	protected void rotate(Position<Entry<K, V>> p) {
		tree.rotate(p);
	}



	public String toBinaryTreeString() {
		BinaryTreePrinter< Entry<K, V> > btp = new BinaryTreePrinter<>(this.tree);
		return btp.print();
	}

	public static void main(String[] args) throws IOException {
		TreeMap<Integer, String> map = new TreeMap<>();
		Integer[] arr = new Integer[] {35,26,15,24,33,4,12,1,23,21,2,5,37};

		for (Integer i : arr) {
			map.put(i, Integer.toString(i));
		}
		System.out.println(map.toBinaryTreeString());
	}


}

