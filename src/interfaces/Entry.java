package interfaces;

/**
 * Interface for a key-value pair.
 */
public interface Entry<K,V> extends Comparable<Entry<K, V>> {
  /**
   * Returns the key stored in this entry.
   * @return the entry's key
   */
  K getKey();

  /**
   * Returns the value stored in this entry.
   * @return the entry's value
   */
  V getValue();

}

