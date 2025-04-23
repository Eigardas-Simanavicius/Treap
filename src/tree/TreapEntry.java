package tree;

import utils.MapEntry;

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