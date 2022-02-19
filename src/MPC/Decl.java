package MPC;
import java.util.ArrayList;
import java.util.List;
/**
 * An abstract representation of a <i>Pascal</i> declaration.
 *
 * @version 1.0
 */
public abstract class Decl extends ASTNode {

  /**
   * The Binding in the Pascal declaration, which gives access to the name
   * (TokenId)
   */
  public final Binding bind;

  /**
   * Creates a new Decl instance, given the Binding (name) and
   * its source code start and end positions
   *
   * @param b     Binding (name) of this declaration
   * @param left  starting position in program text
   * @param right ending position in program text
   */
  public Decl (final Binding b, final int left, final int right) {
    super(left, right);
    bind   = b;
    b.decl = this;
  }

  /**
   * Creates a new Decl instance when the ending position
   * is not conveniently known.
   *
   * @param b Binding (name) of this declaration
   */
  public Decl (final Binding b) {
    this(b, b.pos, -1);
  }

  /**
   * Static factory method for DeclField objects all with the same type
   *
   * @param bb a list of bindings (names) for the fields
   * @param t the type for the fields
   * @return a list of declarations of those fields
   */
  public static List<DeclField> fieldDeclsOf (List<Binding> bb, Type t) {
    assert(bb.size() > 0);
    List<DeclField> ff = new ArrayList<DeclField>(bb.size());
    for (Binding b: bb) {
      ff.add(new DeclField(b, t));
    }
    return ff;
  }

  /**
   * Static factory method for DeclVar objects all with the same type
   *
   * @param bb a list of bindings (names) for the variables
   * @param t the type for the variables
   * @return a list of declarations of those variables
   */
  public static List<Decl> varDeclsOf (List<Binding> bb, Type t) {
    assert(bb.size() > 0);
    List<Decl> dd = new ArrayList<Decl>(bb.size());
    for (Binding b: bb) {
      dd.add(new DeclVar(b, t));
    }
    return dd;
  }

  /**
   * Static factory method for the formals in a group with the same type.
   * A factory for List<Decl>, not Decl, but this seems the best place
   * for it.
   *
   * @param isVar true for VAR parameters, false for by-value ones
   * @param bb a list of Bindings with the variable names
   * @param t the type of the formals
   * @return a List<Decl> os the FormalDecl objects for the formals
   */
  public static List<Decl> formalDeclsOf (boolean isVar, List<Binding> bb, Type t, int endPos) {
    assert(bb.size() > 0);
    List<Decl> dd = new ArrayList<Decl>(bb.size());
    for (Binding b: bb) {
      dd.add(new DeclFormal(isVar, b, t, endPos));
    }
    return dd;
  }

  /**
   * Handles AST visiting work that should happen before visiting each Decl
   * (or subclass) node
   *
   * @param v an ASTVisitor
   */
  public void acceptBefore (final ASTVisitor v) {
    super.acceptBefore(v);
    v.visitDeclBefore(this);
  }

  /**
   * Handles AST visiting work that should happen after visiting each Decl
   * (or subclass) node
   *
   * @param v an ASTVisitor
   */
  public void acceptAfter (final ASTVisitor v) {
    v.visitDeclAfter(this);
    super.acceptAfter(v);
  }

  /**
   * Gives a simple printable form of the Decl, namely the String form of the
   * TokenId of its declaration
   *
   * @return a String for the Decl's TokenId (name)
   */
  public String toString () {
    return bind.id.toString();
  }

}

// Local Variables:
// mode: jde
// c-basic-offset: 2
// indent-tabs-mode: nil
// End:

