package MPC;
/**
 * Representation of string tokens found in a <i>Pascal</i> program.
 */
public final class TokenString implements Token {

  /**
   * StringMap holding all string literal tokens
   */
  public static final StringMap<TokenString> strMap =
    new StringMap<TokenString>(1031); // a largish prime

  /**
   * the actual string of this string literal token
   */
  public final String string;

  /**
   * sequence number given to the string; used in forming a unique label for
   * the string within the assembly language eventually output, etc.
   */
  public final int number;

  /**
   * sequence number counter used to assign the sequence numbers
   */
  private static int counter = 0;

  /**
   * return sym.STRING as the token code
   *
   * @return sym.STRING as the token code
   */
  public int code () {
    return sym.STRING;
  }

  /**
   * Creates a new TokenString instance.  <b>Note: assumes s is new (not in
   * the table)!</b>
   *
   * @param cs the CharSequence of the string literal
   * @param hash hash code for that string (avoids computing it twice)
   */
  private TokenString (CharSequence cs, int hash) {
    string = cs.toString();
    number = ++counter;
    strMap.put(string, this, hash);  // insert into string table
  }

  /**
   * Retrieves the TokenString given its string literal value and hash code.
   * Creates a new TokenString if it does not already exist.
   *
   * @param cs CharSequence literal value
   * @param hash its hash code (from StringMap)
   * @return  matched TokenString
   */
  public static TokenString get (CharSequence cs, int hash) {
    TokenString str = strMap.get(cs, hash);
    if (str != null)
      return str;
    return new TokenString(cs, hash);
  }

  /**
   * provides a "hook" for a visitor to do something before each Token
   *
   * @param v the TokenVisitor whose method we will invoke
   */
  public void acceptBefore (TokenVisitor v) {
    v.visitEveryBefore(this);
  }

  /**
   * Supports visiting objects in the Token class hierarchy; calls superclass
   * acceptBefore() and acceptAfter() hooks as well as a visit method
   * particular to this class.
   *
   * @param v the TokenVisitor to call, indicating this object is a TokenString
   */
  public void accept (TokenVisitor v) {
    this.acceptBefore(v);
    v.visitTokenString(this);
    this.acceptAfter(v);
  }

  /**
   * provides a "hook" for a visitor to do something after each Token
   *
   * @param v the TokenVisitor whose method we will invoke
   */
  public void acceptAfter (TokenVisitor v) {
    v.visitEveryAfter(this);
  }

  /**
   * String representation of TokenString.
   *
   * @return the Java String representing this Pascal string literal
   */
  public String toString () {
    return string;
  }

  /**
   * Pretty string of TokenString.
   *
   * @return the Java String representing this Pascal string literals, with
   * single quotes around it to make clear that it is a string literal
   */
  public String toPrettyString () {
    return "'" + this + "'";
  }

}

// Local Variables:
// mode: jde
// c-basic-offset: 2
// indent-tabs-mode: nil
// End:

