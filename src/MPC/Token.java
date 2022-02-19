package MPC;
/**
 * Interface for all token types contained in the <i>610 Pascal Compiler</i>.
 * Instances of implementors of this interface represent all tokens found in a
 * <i>Pascal</i> program. The implementors are: TokenId (identifiers),
 * TokenInt (integer literals), TokenKey (keywords, implemented with a table
 * shared with TokenId), TokenOp (operators and punctuation), and TokenString
 * (string literals).
 */
public interface Token {

  /**
   * Returns the Token's unique token code
   *
   * @return the Token's unique token code
   */
  public abstract int code ();

  /**
   * Special end of file token; convenient to make it available here
   */
  public static final Token EOF = TokenOp.EOF;

  /**
   * provides a "hook" for a visitor to do something before each Token; note
   * that implementor accept methods should start with a call to
   * acceptBefore() and end with one to acceptAfter()
   *
   * @param v the TokenVisitor whose method we will invoke
   */
  public void acceptBefore (TokenVisitor v);

  /**
   * provides the usual visitor accept functionality
   *
   * @param v the TokenVisitor whose method we will invoke
   */
  public void accept (TokenVisitor v);

  /**
   * provides a "hook" for a visitor to do something after each Token; note
   * that implementor accept methods should start with a call to
   * super.acceptBefore() and end with one to super.acceptAfter()
   *
   * @param v the TokenVisitor whose method we will invoke
   */
  public void acceptAfter (TokenVisitor v);

  /**
   * String representation of a token
   *
   * @return a String value for the Token (no special prefix)
   */
  public abstract String toString ();

  /**
   * pretty representation of token
   *
   * @return a String value for the Token; defaultly the same as toString()
   */
  public String toPrettyString ();

}

// Local Variables:
// mode: jde
// c-basic-offset: 2
// indent-tabs-mode: nil
// End:

