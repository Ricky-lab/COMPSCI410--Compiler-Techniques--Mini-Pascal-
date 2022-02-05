package MPC;
import java.util.*;

/**
 * This is essentially a variant of HashMap for our purposes:
 * (1) We want to be able to look up based on an int, thus avoid making
 * unnecessary objects;
 * (2) Since we are implementing this ourselves, we implement only the
 * functionality we need.
 */

public class IntMap<E> {

  /**
   * Nested, but not inner, class for entries in the map; we will have buckets
   * that start chains of these entries that have the same hash code for their
   * key
   */
  private static class IntMapEntry<E> {

    /**
     * chaining pointer
     */
    public IntMapEntry<E> next;

    /**
     * the key
     */
    public final int key;

    /**
     * the value associated with the key
     */
    public final E value;

    /**
     * the obvious constructor
     *
     * @param key the int key for this entry
     * @param value the value for this entry
     */
    public IntMapEntry (int key, E value) {
      this.key   = key;
      this.value = value;
    }
  }

  /**
   * array that roots the hash chains of IntMapEntry objects
   */
  private IntMapEntry hashChains[];            // the starts of the hash chains

  /**
   * constant for the default number of chains
   * 53 is prime (good), but otherwise arbitrary
   */
  private static final int defaultChains = 53;  // default number of hash chains

  /**
   * constructor that builds a map with the default number of hash chains
   */
  public IntMap () {
    this(defaultChains);  // simply call primary constructor with default size
  }

  /**
   * constructor that builds a map with the specified number of hash chains
   * @param chains an int giving the number of hash chains (buckets) to use
   */
  public IntMap (int chains) {
    hashChains = new IntMapEntry[chains];
  }

  /**
   * private methods to compute the index of a hash chain given hash code int
   * key value
   *
   * @param hash the hash code value to reduce modulo the table size
   * @return the hash chain index to use
   */
  private int getHash (int hash) {
    hash %= hashChains.length;
    if (hash < 0) hash += hashChains.length;
    return hash;
  }

  /**
   * looks up a value given its int key; returns null if the the key is not in
   * the table
   *
   * @param i the int key to look up
   * @return the associated Object, or null if there is none
   */
  @SuppressWarnings("unchecked")
  public E get (int i) {
    int hash = getHash(i);
    for (IntMapEntry<E> e = (IntMapEntry<E>)hashChains[hash];
         e != null;
         e = e.next) {
      if (e.key == i) {
        return e.value;
      }
    }
    return null;
  }

  /**
   * method for adding a key/value pair to the map; it ASSUMES the key is not
   * already present!
   *
   * @param i the key for this new map entry
   * @param value the Object value for this new map entry
   */
  @SuppressWarnings("unchecked")
  public void put (int i, E value) {
    IntMapEntry<E> e = new IntMapEntry<E>(i, value);
    int hash = getHash(i);
    e.next = (IntMapEntry<E>)hashChains[hash];
    hashChains[hash] = e;
  }

  /**
   * method to remove from the table a specific key and its associated value
   * @param i a key to remove
   */
  @SuppressWarnings("unchecked")
  public void remove (int i) {
    int hash = getHash(i);
    IntMapEntry<E> prev = null;
    IntMapEntry<E> curr = (IntMapEntry<E>)hashChains[hash];
    while (curr != null && curr.key != i) {
      prev = curr;
      curr = curr.next;
    }
    if (curr == null) return;  // key was not present
    if (prev == null) {
      hashChains[hash] = curr.next;  // was first on the chain
    } else {
      prev.next = curr.next;
    }
  }

  /**
   * method for removing all entries from the map
   */
  public void clear () {
    for (int i = 0, n = hashChains.length; i < n; ++i)
      hashChains[i] = null;
  }

}

// Local Variables:
// mode: jde
// c-basic-offset: 2
// indent-tabs-mode: nil
// End:

