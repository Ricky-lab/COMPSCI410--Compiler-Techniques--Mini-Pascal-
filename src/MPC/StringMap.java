package MPC;
import java.util.*;

/**
 * This is essentially a variant of HashMap for our purposes:
 * (1) We want to be able to look up based on a reusable StringBuffer, thus
 * avoid making unnecessary objects;
 * (2) Since we are implementing this ourselves, we implement only the
 * functionality we need.
 *
 * StringMap supports a mapping from Strings to Objects.  It provides lookup
 * using a re-usable StringBuffer, thus avoiding unnecessary creation of
 * String objects.
 *
 * @version 1.0
 */
public class StringMap<E> implements Iterable<E> {

  /**
   * An entry in the StringMap.
   */
  private static class StringMapEntry<E> {

    /**
     * chaining pointer
     */
    public StringMapEntry<E> next;  // chaining slot

    /**
     * the key
     */
    public final String key;   // string key

    /**
     * the value associated with the key
     */
    public final E value; // associated value

    /**
     * Creates a new StringMapEntry instance.
     *
     * @param key   the String key
     * @param value the E object to associated with it
     */
    public StringMapEntry (String key, E value) {
      this.key   = key;
      this.value = value;
    }
  }

  /**
   * array of starts of chains of StringMapEntry objects
   */
  private StringMapEntry hashChains[];         // the starts of the hash chains

  /**
   * default number of hash chains;
   * 53 is a prime (good) but otherwise arbitrary
   */
  private static final int defaultChains = 53;  // default number of hash chains

  /**
   * Creates a new StringMap instance with the default number of
   * hash chains.
   */
  public StringMap () {
    this(defaultChains);  // simply call primary constructor with default size
  }

  /**
   * Creates a new StringMap instance with a specified number of
   * hash chains.
   *
   * @param chains number of hash chains
   */
  public StringMap (int chains) {
    hashChains = new StringMapEntry[chains];
  }

  /**
   * Return a hash value given a CharSequence cs.
   *
   * @param cs the CharSequence
   * @return   the hash value
   */
  public static int getHash (CharSequence cs) {
    int hash = 0;
    for (int i = 0, n = cs.length(); i < n; ++i) {
      hash = addToHash(hash, cs.charAt(i));
    }
    return hash;
  }

  /**
   * Add one character to an accumulating hash value
   *
   * @param hash the old hash value
   * @param c the next character
   * @return   the new hash value
   */
  public static int addToHash (int hash, char c) {
    return hash * 31 + (int)(c);
  }

  /**
   * Allows us to separate hashing (getHash(CharSequence)), which is
   * independent of the particular StringMap, from determining the index of
   * the chain we want (which depends on the number of chains in the
   * particular StringMap at hand).
   *
   * @param hash hash code for which we want the hash chain index
   * @return the index of the proper hash chain in this StringMap's array of buckets
   */
  private int hashChainOf (int hash) {
    hash %= hashChains.length;
    if (hash < 0) hash += hashChains.length;
    return hash;
  }

  /**
   * key comparison operator
   *
   * @param key a saved key in the StringMap
   * @param test a CharSequence that we would like to test against key
   * @return whether key and test contain the same sequence of characters
   */
  private static boolean keyMatches (String key, CharSequence test) {
    int klen = key.length();
    int tlen = test.length();
    if (klen != tlen) return false;
    for (int i = 0; i < klen; ++i) {
      if (key.charAt(i) != test.charAt(i)) return false;
    }
    return true;
  }

  /**
   * Gets value if there is a matching string (null if no match)
   *
   * @param cs CharSequence to match
   * @param hash the hash code for cs
   * @return   value if there is a match, null otherwise
   */
  @SuppressWarnings("unchecked")
  public E get (CharSequence cs, int hash) {
    for (StringMapEntry<E> e = (StringMapEntry<E>)hashChains[hashChainOf(hash)];
         e != null;
         e = e.next) {
      if (keyMatches(e.key, cs)) {
        return e.value;
      }
    }
    return null;
  }

  /**
   * Inserts a value into the StringMap given a key.  The insert method
   * <b>assumes</b> the key is not yet present!
   *
   * @param key   the key used to store the object value
   * @param value the E object to store
   */
  @SuppressWarnings("unchecked")
  public void put (CharSequence key, E value) {
    String ks = key.toString();
    StringMapEntry<E> e = new StringMapEntry<E>(ks, value);
    int hash = hashChainOf(getHash(ks));
    e.next = (StringMapEntry<E>)hashChains[hash];
    hashChains[hash] = e;
  }

  /**
   * Inserts a value into the StringMap given a key and its hash code.  The
   * insert method <b>assumes</b> the key is not yet present!
   *
   * @param key   the key used to store the object value
   * @param value the E object to store
   * @param hash  hash code for key, as from getHash()
   */
  @SuppressWarnings("unchecked")
  public void put (CharSequence key, E value, int hash) {
    String ks = key.toString();
    StringMapEntry<E> e = new StringMapEntry<E>(ks, value);
    hash = hashChainOf(hash);
    e.next = (StringMapEntry<E>)hashChains[hash];
    hashChains[hash] = e;
  }

  /**
   * supports iterating over the entries in the StringMap
   *
   * @return Iterator that returns each E value in the map in turn;
   * assumes that the map is not changing during iteration.
   */
  public Iterator<E> iterator () {
    return new StringMapIterator<E>();
  }

  /**
   * An iterator for StringMap.
   */
  public class StringMapIterator<E> implements Iterator<E> {

    // Works by maintaining the index of the current chain it is scanning and
    // a reference to the current StringMapEntry on that chain.

    private int               currChain;
    private StringMapEntry<E> currEntry;

    // assumes the table does not change while iterating!
    // (but because insertions are at chain heads, probably works ok)
    /**
     * Returns a new iterator that goes through all entries in the StringMap
     * <b>Assumes</b> the StringMap does not change while iterating!
     */
    StringMapIterator () {
      currChain = 0;     // runs through chains in increasing order
                         // currChain gives NEXT chain to process
      currEntry = null;  // runs through entries of chain in chain order
      next();            // advance to first entry
    }

    /**
     * Indicates whether there is a next value in the iterator.
     *
     * @return a boolean value
     */
    public boolean hasNext () {
      return currEntry != null;
    }

    /**
     * Gets the next object in the iterator. 
     *
     * @return the object value
     */
    @SuppressWarnings("unchecked")
    public E next () {
      E result = null;

      // advance to next in chain (if not already at end of one)
      if (currEntry != null) {
        result    = currEntry.value;
        currEntry = currEntry.next;
      }

      // try to find a non-null chain
      // note that this also works nicely if currEntry is not null here
      while (currEntry == null && currChain < hashChains.length) {
        currEntry = (StringMapEntry<E>)hashChains[currChain++];
      }

      return result;
    }

    /**
     * does not support removing items iterated over
     */
    public void remove () {
      throw new UnsupportedOperationException();
    }

  }

}

// Local Variables:
// mode: jde
// c-basic-offset: 2
// indent-tabs-mode: nil
// End:

