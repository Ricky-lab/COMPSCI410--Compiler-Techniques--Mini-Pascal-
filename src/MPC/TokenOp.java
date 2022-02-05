package MPC;
/**
 * Represents all operator tokens in the <i>Pascal</i> language that are used
 * in <i>OO Mini-Pascal</i> as defined by the <i>Extended Pascal ISO</i> document.
 */
public enum TokenOp implements Token {

  // single character operators
  LPAREN ("(" , sym.LPAREN),
  RPAREN (")" , sym.RPAREN),
  COMMA  ("," , sym.COMMA ),
  DOT    ("." , sym.DOT   ),
  SEMI   (";" , sym.SEMI  ),
  COLON  (":" , sym.COLON ),
  LT     ("<" , sym.LT    ),
  EQ     ("=" , sym.EQ    ),
  GT     (">" , sym.GT    ),
  AST    ("*" , sym.AST   ),
  PLUS   ("+" , sym.PLUS  ),
  MINUS  ("-" , sym.MINUS ),
  LBRACK ("[" , sym.LBRACK),
  RBRACK ("]" , sym.RBRACK),
  CARET  ("^" , sym.CARET ),

  // double character operators
  LE     ("<=", sym.LE    ),
  NE     ("<>", sym.NE    ),
  GE     (">=", sym.GE    ),
  ASSIGN (":=", sym.ASSIGN),
  DOTDOT ("..", sym.DOTDOT),


  EOF     ("EOF"   , sym.EOF);  // conveniently ends the list
  // EOF is not really an operator, but this was a convenient
  // enum in which to place it :-)

  /**
   * StringMap holding all operator tokens, to allow them to be looked up
   * given their String representation
   *
   * Size large enough to tend to avoid collisions.
   */
  private static final StringMap<TokenOp> opMap = new StringMap<TokenOp>(127);

  /**
   * the string defining this operator
   */
  public final String string;

  /**
   * the Token's unique token code
   */
  private final int code;

  /**
   * return the Token's unique token code
   *
   * @return the Token's unique token code
   */
  public int code () {
    return code;
  }

  /**
   * Creates a new TokenOp instance.
   * <b>NOTE: this assumes that s is new!</b>
   *
   * Private: all instances are created by a static initializer of this class.
   *
   * @param cs CharSequence defining this operator
   * @param code symbol code for this operator
   */
  TokenOp (CharSequence cs, int code) {
    // note: this assume that cs is new!
    this.code   = code;
    this.string = cs.toString();
  }

  /**
   * Retrieves TokenOp given its CharSequence representation and its hash code.
   *
   * @param cs defining CharSequence
   * @param hash the hash code (from StringMap)
   * @return matched TokenOp, null otherwise
   */
  public static TokenOp get (CharSequence cs, int hash) {
    TokenOp t = opMap.get(cs, hash);
    if (t == null) {
      throw new IllegalStateException("Operator was not found in the operator table: " + cs);
    }
    return t;
  }

  /**
   * String representation of an operator.
   *
   * @return the String defining this operator
   */
  public String toString () {
    return string;
  }

  /**
   * Pretty string of an operator.
   *
   * @return the String defining this operator, with a prefix to distinguish
   * it from other kinds of Token objects
   */
  public String toPrettyString () {
    String suffix = this.toString();
    if (this == EOF) return suffix;
    return "@" + suffix;
  }

  static {
    for (TokenOp t : TokenOp.values()) {
      String s = t.string;
      if (t != EOF)
        opMap.put(s, t, StringMap.getHash(s));
    }
  }

}

// Local Variables:
// mode: jde
// c-basic-offset: 2
// indent-tabs-mode: nil
// End:

