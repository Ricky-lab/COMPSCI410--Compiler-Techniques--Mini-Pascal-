package MPC;
/**
 * TokenId is used to represent an <b>identifier</b> token
 * found in a <i>Pascal</i> program.
 */
public final class TokenId implements Token {

  /**
   * the StringMap used to hold all identifiers (and keywords),
   * so that we can share the String (and the TokenId/TokenKey) when the name
   * is the same
   */
  private static final StringMap<Token> idMap = new StringMap<Token>(1031); // a largish prime

  /**
   * the string defining this identifier
   */
  public final String string;   
  
  /**
   * arbitrary info, settable by client of this class
   */
  public Object info = null;

  /**
   * return sym.ID as the token code
   *
   * @return sym.ID as the token code
   */
  public int code () {
    return sym.ID;
  }

  /**
   * Creates a new TokenId instance.
   * <b>NOTE: this assumes that s is new!</b>
   *
   * @param cs a CharSequence defining an identifier
   * @param hash the StringMap.getHash result for String s
   */
  private TokenId (CharSequence cs, int hash) {
    // note: this ASSUMES that cs is new!
    string = cs.toString();
    idMap.put(string, this, hash);  // insert into identifier table
  }

  /**
   * Retrieves a Token given its defining string.
   * NOTE: Assumes Token is already in lower case!
   *
   * @param cs defining CharSequence
   * @return  Token representing the identifier (may be a TokenKey)
   */
  public static Token get (CharSequence cs) {
    int hash = StringMap.getHash(cs);
    Token t = idMap.get(cs, hash);
    if (t != null)
      return t;
    return new TokenId(cs, hash);
  }

  /**
   * Retrieves a Token given its defining string and hash.
   * NOTE: Assumes Token is already in lower case!
   *
   * @param cs defining CharSequence
   * @param hash the has value for that CharSequence, from StringMap
   * @return  Token representing the identifier (may be a TokenKey)
   */
  public static Token get (CharSequence cs, int hash) {
    Token t = idMap.get(cs, hash);
    if (t != null)
      return t;
    return new TokenId(cs, hash);
  }

  /**
   * String representation of this TokenId.
   *
   * @return the String value used when we created the TokenId
   */
  public String toString () {
    return string;
  }

  /**
   * Pretty representation of this TokenId.
   *
   * @return the String value used when we created the TokenId,
   * with a prefix to distinguish a TokenId from other kinds of Token objects
   */
  public String toPrettyString () {
    return "$" + this;
  }

  /**
   * Causes TokenKey to enter all keywords into the shared id/keyword table.
   */
  static {
    TokenKey.EnterKeywords(idMap);
  }

}

// Local Variables:
// mode: jde
// c-basic-offset: 2
// indent-tabs-mode: nil
// End:
