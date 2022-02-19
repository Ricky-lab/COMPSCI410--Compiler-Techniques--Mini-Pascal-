package MPC;
/**
 * This interface defines the functionality of all Token visitor classes.
 */
public interface TokenVisitor {

  // methods for work related to every kind of Token
  public void visitEveryBefore (Token t);
  public void visitToken       (Token t);
  public void visitEveryAfter  (Token t);
  
  // methods for work related to each subclass
  public void visitTokenId (TokenId t);

  public void visitTokenInt (TokenInt t);

  public void visitTokenKey (TokenKey t);

  public void visitTokenOp (TokenOp t);

  public void visitTokenString (TokenString t);

}

// Local Variables:
// mode: jde
// c-basic-offset: 2
// indent-tabs-mode: nil
// End:
