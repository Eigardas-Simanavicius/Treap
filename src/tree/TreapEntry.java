package tree;

import utils.MapEntry;
// used by Treap , its just map entry but with an extra value, p wich is used for the priority
public class TreapEntry<K extends Comparable<K>,V> extends MapEntry<K, V> {
    private K k; // key
    private V v; // value
    private final int p;

    public TreapEntry(K key, V value, int prio) {
        super(key,value);
        p = prio;
    }

    public int getPriority(){
        return p;
    }

    public String toPrioString() {
        return "<" + getKey() + ", " + getValue() + ", " + p + ">";
    }

} // ----------- end of nested MapEntry class -----------