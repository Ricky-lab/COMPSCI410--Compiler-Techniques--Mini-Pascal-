package MPC;

import static MPC.ASTNode.VisitedState.*;

/**
 * This abstract class defines things common to all AST (Abstract Syntax Tree)
 * nodes.
 *
 * Here is an overview of the AST class hierarchy:
 *
 * ASTNode
 *   Binding       (TokenId plus reference to the appropriate Decl)
 *   Block         (Decls plus Stmts)
 *   CaseElement   (labels plus Stmt of one case in a StmtCase)
 *   CaseLabelList (just the labels on one case in a StmtCase)
 *   Decls         (list of Decl nodes)
 *   Exprs         (list of Expr nodes, for procedure/function calls)
 *   Program       (top node of the AST)
 *   Stmts         (list of Stmt nodes)
 *
 *   Decl           (abstract ancestor of all Decl nodes)
 *     DeclConst    (Decl for a Pascal CONST)
 *     DeclMember   (Decl for a field, formal, or method)
 *       DeclField    (Decl for a record field)
 *       DeclFormal   (Decl for a procedure/function formal argument)
 *       DeclMethod   (Decl for a method in an object type)
 *     DeclProcFunc (Decl for a Pascal PROCEDURE or FUNCTION)
 *     DeclProgram  (Decl for a Pascal PROGRAM name (of little use))
 *     DeclSpecial  (Decl for a Pascal special built-in (write, readln, etc.))
 *     DeclType     (Decl for a Pascal TYPE (see Type below))
 *     DeclVar      (Decl for a Pascal VAR)
 *
 *   Expr           (abstract ancestor of all Expr nodes)
 *     ExprBinary   (Expr for a Pascal binary operator expression)
 *     ExprBinding  (Expr for a Pascal use of an identifier (VAR, CONST, FUNCTION, ...))
 *     ExprCall     (Expr for a Pascal PROCEDURE/FUNCTION call)
 *     ExprError    (placeholder where the input was bad)
 *     ExprId       (Expr for a Pascal id: record field only; others should be ExprBinding)
 *     ExprInt      (Expr for a Pascal integer literal)
 *     ExprNil      (Expr for Pascal NIL)
 *     ExprString   (Expr for a Pascal string literal)
 *     ExprUnary    (Expr for a Pascal unary operator expression)
 *
 *   Stmt           (abstract ancestor of all Stmt nodes)
 *     StmtAssign   (Stmt for a Pascal assignment statement)
 *     StmtCall     (Stmt for a Pascal PROCEDURE/FUNCTION call statement)
 *     StmtCase     (Stmt for a Pascal CASE statement)
 *     StmtCompound (Stmt for a Pascal compound statement (BEGIN/END))
 *     StmtEmpty    (Stmt for a Pascal empty statement)
 *     StmtFor      (Stmt for a Pascal FOR statement)
 *     StmtIf       (Stmt for a Pascal IF statement; if no ELSE, then a null else field)
 *     StmtRepeat   (Stmt for a Pascal REPEAT statement)
 *     StmtWhile    (Stmt for a Pascal WHILE statement)
 *
 *   Type             (abstract ancestor of all Type nodes)
 *     TypeArray      (Type for a Pascal ARRAY type)
 *     TypeError      (placeholder for a "bad" type)
 *     TypeId         (Type for a Pascal type given a name in a TYPE declaration)
 *     TypeObject     (Type for a Mini-Object-Pascal object type)
 *     TypePointer    (Type for a Pascal pointer (^) type)
 *       TypeObjectPointer (Type for a reference to an object)
 *     TypePrim       (abstract parent of the primitive types)
 *       TypePrimBool   (the primitive boolean type of Mini-Pascal)
 *       TypePrimInt    (the Pascal primitive INTEGER type)
 *       TypePrimNil    (the "type" of NIL (matches all pointer types))
 *       TypePrimString (the primitive type for Mini-Pascal string literals)
 *     TypeProcFunc   (Type for a procedure, function, or method)
 *     TypeRecord     (Type for a Pascal RECORD type)
 *     TypeRange      (Type for a Pascal range (..) type)
 *
 */
public abstract class ASTNode {

  /**
   * pos is used for error reporting and indicates the starting
   * position of the item described by the ASTNode on a line in the program source.
   */
  public int pos;

  /**
   * endPos is used for error reporting and indicates the ending
   * position of the item described by the ASTNode on a line in the program source.
   */
  public int endPos;

  // VisitedState assist with breaking recursions and with error checking.
  // We associate it with ASTNode objects to make it available throughout the
  // parse tree, but some visited state is more relevant to some kinds of
  // nodes than to others.

  /**
   * Whether a node has been resolved
   */
  public VisitedState resolved = VisitedState.Unvisited;

  /**
   * Whether a node has been checked
   */
  public VisitedState checked = VisitedState.Unvisited;

  /**
   * Initializer for an ASTNode instance
   * @param left  starting position in the source (see class InputPos)
   * @param right ending   position in the source (see class InputPos)
   */
  public ASTNode (final int left, final int right) {
    pos          = left;
    endPos       = right;
  }

  /**
   * method needed for dispatching AST visitors
   *
   * @param v the ASTVisitor trying to visit this node
   */
  public abstract void accept (final ASTVisitor v);

  /**
   * Our visitor protocol supports work done before visiting each node; this
   * is the routine to dispatch to the visitor to do that work.  Note that it
   * is NOT abstract, but does a useful thing! Subclass implementations should
   * call super(v) before doing their class-specific before-visiting dispatch.
   *
   * @param v the ASTVisitor trying to visit this node
   */
  public void acceptBefore (final ASTVisitor v) {
    v.visitEveryBefore(this);
  }

  /**
   * Our visitor protocol supports work done after visiting each node; this is
   * the routine to dispatch to the visitor to do that work.  Note that it is
   * NOT abstract, but does a useful thing! Subclass implementations should
   * call super(v) after doing their class-specific after-visiting dispatch.
   *
   * @param v the ASTVisitor trying to visit this node
   */
  public void acceptAfter (final ASTVisitor v) {
    v.visitEveryAfter(this);
  }

  /**
   * returns true iff a node has already been visited by a resolver;
   * also, changes the resolved state to InProgress (as a default until
   * the node's resolved state is marked true)
   */
  public boolean startResolveVisit () {
    if (resolved.visited()) {
      return true;
    }
    resolved = InProgress;
    return false;
  }

  /**
   * returns true iff a node has already been visited by a checker;
   * also, changes the checked state to Failure (as a default until
   * the node's checked state is marked true - note that this is not
   * the same default as for resolving)
   */
  public boolean startCheckVisit () {
    if (checked.visited()) {
      return true;
    }
    checked = Failure;
    return false;
  }

  public static enum VisitedState {
    Unvisited {
      public boolean visited  () { return false; }
      public boolean complete () { return false; }
      },
    InProgress {
      public boolean visiting () { return true ; }
      public boolean complete () { return false; }
    },
    Success {
      public boolean success () { return true; }
      },
    Failure {
      public boolean failure () { return true; }
      };
    public boolean visited  () { return true ; };  // has a visit started?
    public boolean visiting () { return false; };  // is a visit in progress?
    public boolean complete () { return true ; };  // has a visit completed?
    public boolean success  () { return false; };  // has a visit completed successfully?
    public boolean failure  () { return false; };  // has a visit completed unsuccessfully?
  }

}

// Local Variables:
// mode: jde
// c-basic-offset: 2
// indent-tabs-mode: nil
// End:
