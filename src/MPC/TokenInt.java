package MPC;
/**
 * Token class for integer literals of a <i>Pascal</i> program.
 */
public final class TokenInt implements Token {

  /**
   * We keep each TokenInt in this table, with the key being its Java int
   * value, so that we get the same object for each instance of the same
   * value.
   *
   * @see IntMap a mapping structure specifically designed to avoid
   * constructing a Java object (such as an Integer) for its keys.
   */
  private static final IntMap<TokenInt> intMap = new IntMap<TokenInt>();

  /**
   * The Java int value of the token
   */
  public final int value;

  /**
   * return sym.INT as the token code
   *
   * @return sym.INT as the token code
   */
  public int code () {
    return sym.INT;
  }

  /**
   * Constructor, which is private so that we avoid constructing new TokenInt
   * objects if the value is already in the table.
   *
   * @param i the int value for the TokenInt being constructed
   */
  private TokenInt (int i) {
    // note: this assume that s is new!
    value = i;
    intMap.put(i, this);  // insert into identifier table
  }

  /**
   * Method that returns a pre-existing TokenInt for the value passed in, or
   * if there is not one, constructs and returns it.
   *
   * @param i Java int value for the TokenInt
   * @return the TokenInt for a given int value
   */
  public static TokenInt get (int i) {
    TokenInt t = intMap.get(i);
    if (t != null)
      return t;
    return new TokenInt(i);
  }

  /**
   * String representation of this TokenInt.
   *
   * @return the decimal String representation of the TokenInt's value
   */
  public String toString () {
    return String.valueOf(value);
  }

  /**
   * Pretty representation of this TokenInt.
   *
   * @return the decimal String representation of the TokenInt's value, with a
   * prefix to distinguish it from other kinds of tokens
   */
  public String toPrettyString () {
    return "#" + this;
  }

}

// Local Variables:
// mode: jde
// c-basic-offset: 2
// indent-tabs-mode: nil
// End:

