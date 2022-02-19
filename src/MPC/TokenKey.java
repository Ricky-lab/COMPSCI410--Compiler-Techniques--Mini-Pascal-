package MPC;
/**
 * TokenKey is used to represent a <b>keyword</b> in the <i>Pascal</i>
 * language as defined by the <i>Pascal User Manual and Report</i>.
 */
public enum TokenKey implements Token {

  ABSTRACT    ("abstract"   , sym.ABSTRACT   ),
  AND         ("and"        , sym.AND        ),
  ARRAY       ("array"      , sym.ARRAY      ),
  BEGIN       ("begin"      , sym.BEGIN      ),
  CASE        ("case"       , sym.CASE       ),
  CONST       ("const"      , sym.CONST      ),
  DIV         ("div"        , sym.DIV        ),
  DO          ("do"         , sym.DO         ),
  DOWNTO      ("downto"     , sym.DOWNTO     ),
  ELSE        ("else"       , sym.ELSE       ),
  END         ("end"        , sym.END        ),
  FOR         ("for"        , sym.FOR        ),
  FUNCTION    ("function"   , sym.FUNCTION   ),
  IF          ("if"         , sym.IF         ),
  METHODS     ("methods"    , sym.METHODS    ),
  MOD         ("mod"        , sym.MOD        ),
  NIL         ("nil"        , sym.NIL        ),
  NOT         ("not"        , sym.NOT        ),
  OBJECT      ("object"     , sym.OBJECT     ),
  OF          ("of"         , sym.OF         ),
  OR          ("or"         , sym.OR         ),
  OVERRIDES   ("overrides"  , sym.OVERRIDES  ),
  PROCEDURE   ("procedure"  , sym.PROCEDURE  ),
  PROGRAM     ("program"    , sym.PROGRAM    ),
  RECORD      ("record"     , sym.RECORD     ),
  REPEAT      ("repeat"     , sym.REPEAT     ),
  THEN        ("then"       , sym.THEN       ),
  TO          ("to"         , sym.TO         ),
  TYPE        ("type"       , sym.TYPE       ),
  UNTIL       ("until"      , sym.UNTIL      ),
  VAR         ("var"        , sym.VAR        ),
  WHILE       ("while"      , sym.WHILE      );

  /**
   * Constructs a TokenKey instance with the given string as its canonical
   * input form (lower case) and the given code as its Token code.
   *
   * @param string the String form of the keyword
   * @param code   the int Token code of the keyword
   */
  TokenKey (String string, int code) {
    this.string = string;
    this.code   = code;
  }

  /**
   * the string defining this keyword
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

  // There is no "get" operation, since we place all keywords in the TokenId
  // map at class initialization time

  /**
   * provides a "hook" for a visitor to do something before each Token
   *
   * @param v the TokenVisitor whose method we will invoke
   */
  public void acceptBefore (TokenVisitor v) {
    v.visitEveryBefore(this);
  }

  /**
   * Supports visiting objects in the Token class hierarchy; calls
   * acceptBefore and acceptAfter hooks as well as a visit method particular
   * to this class.
   *
   * @param v the TokenVisitor to call, indicating this object is a TokenKey
   */
  public void accept (TokenVisitor v) {
    this.acceptBefore(v);
    v.visitTokenKey(this);
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
   * String representation of this TokenKey.
   *
   * @return String defining this TokenKey
   */
  public String toString () {
    return string;
  }

  /**
   * String representation of this TokenKey.
   *
   * @return String defining this TokenKey; same as toString()
   */
  public String toPrettyString () {
    return toString();
  }

  /**
   * Enters all keywords into the table given; should be called only once.
   *
   * @param map the table into which to enter the keywords
   * @see TokenId which provides the table and calls this
   */
  protected static void EnterKeywords (StringMap<Token> map) {
    for (TokenKey tk : TokenKey.values()) {
      map.put(tk.string, tk);
    }
  }

}

// Local Variables:
// mode: jde
// c-basic-offset: 2
// indent-tabs-mode: nil
// End:

