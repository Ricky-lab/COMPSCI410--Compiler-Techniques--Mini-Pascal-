package MPC;
/**
 * A Binding consists of an identifier (TokenId) from the Pascal source, plus
 * the (initially null) reference to the Decl node for the declaration that
 * pertains in the context in which the TokenId appears.
 *
 * @author Eliot Moss
 * @version 1.0
 */
public final class Binding extends ASTNode {

  /**
   * A TokenId that occurs in the Pascal source
   */
  public final TokenId id;

  /**
   * The Decl of this TokenId pertinent to the context in which the TokenId
   * occurs. Filled in by Phase 3 (symbol table and checking).
   */
  public Decl decl = null;

  /**
   * Creates a new Binding instance given the TokenId and its
   * location (start and end) in the source.
   *
   * @param i the TokenId that occurs in the source
   * @param left an int giving the start source position of the TokenId
   * @param right an int giving the end source position of the TokenId
   */
  public Binding (TokenId i, int left, int right) {
    super(left, right);
    id     = i;
    decl   = null;
  }

  /**
   * Handles AST visiting for this AST node class.
   *
   * @param v an ASTVisitor
   */
  public void accept (ASTVisitor v) {
    super.acceptBefore(v);
    v.visitBinding(this);
    super.acceptAfter(v);
  }

}

// Local Variables:
// mode: jde
// c-basic-offset: 2
// indent-tabs-mode: nil
// End:

