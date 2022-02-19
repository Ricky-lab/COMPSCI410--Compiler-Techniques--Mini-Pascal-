package MPC;
/**
 * This abstract class gives default (no work) implementations for AST visitor
 * classes. It is convenient for subclassing since you need to give
 * implementations only for those methods that do actual work.
 *
 * Note that visit methods for subclass objects (e.g., for DeclConst, a
 * subclass of Decl) defaultly call their parent class visit method, allowing
 * handling of common functionality. However, if you override visitDeclConst,
 * you will need to call visitDecl in the overriding method, if you want it
 * called.
 *
 * Note also that we coded all our accept methods in subclasses so that,
 * before their work, they call super.acceptBefore, and after teir work they
 * call super.acceptAfter. The acceptBefore/After methods for class XXX call
 * visitXXXBefore/After, allowing work to be done for all nodes belonging to a
 * given part of the AST hierarchy, before or after their normal visit.
 *
 * @version 1.0
 */
public abstract class ASTNullVisitor implements ASTVisitor {

  // methods for work related to every node
  public void visitEveryBefore (final ASTNode n) {}
  public void visitEveryAfter  (final ASTNode n) {}
  
  // methods for work related to classes not subclassed
  public void visitBinding (final Binding b) {}

  public void visitBlock (final Block b) {}

  public void visitCaseElement (final CaseElement c) {}

  public void visitCaseLabelList (final CaseLabelList l) {}

  public void visitDecls (final Decls dd) {}

  public void visitExprs (final Exprs ee) {}

  public void visitProgram (final Program p) {}

  public void visitStmts (final Stmts ss) {}


  // methods related to Decl classes
  public void visitDeclBefore (final Decl d) {}  // these are for all kinds
  public void visitDecl       (final Decl d) {}  // this allows factoring of common stuff
  public void visitDeclAfter  (final Decl d) {}

  public void visitDeclConst (final DeclConst d) { visitDecl(d); }

  public void visitDeclField (final DeclField d) { visitDecl(d); }

  public void visitDeclFormal (final DeclFormal d) { visitDecl(d); }

  public void visitDeclMemberBefore (final DeclMember m) {}
  public void visitDeclMember       (final DeclMember m) { visitDecl(m); }
  public void visitDeclMemberAfter  (final DeclMember m) {}

  public void visitDeclMethod (final DeclMethod m) { visitDeclMember(m); }

  public void visitDeclProcFunc (final DeclProcFunc d) { visitDecl(d); }

  public void visitDeclProgram (final DeclProgram d) { visitDecl(d); }

  public void visitDeclSpecial (final DeclSpecial d) { visitDecl(d); }

  public void visitDeclType (final DeclType d) { visitDecl(d); }

  public void visitDeclVar (final DeclVar d) { visitDecl(d); }


  // methods for Expr classes
  public void visitExprBefore (final Expr e) {}  // these are for all kinds
  public void visitExpr       (final Expr e) {}  // this allows factoring of common stuff
  public void visitExprAfter  (final Expr e) {}

  public void visitExprBinary (final ExprBinary e) { visitExpr(e); }

  public void visitExprBinding (final ExprBinding e) { visitExpr(e); }

  public void visitExprCall (final ExprCall e) { visitExpr(e); }

  public void visitExprError (final ExprError e) { visitExpr(e); }

  public void visitExprId (final ExprId e) { visitExpr(e); }

  public void visitExprInt (final ExprInt e) { visitExpr(e); }

  public void visitExprNil (final ExprNil e) { visitExpr(e); }

  public void visitExprString (final ExprString e) { visitExpr(e); }

  public void visitExprUnary (final ExprUnary e) { visitExpr(e); }


  // methods for Stmt classes
  public void visitStmtBefore (final Stmt s) {}  // these are for all kinds
  public void visitStmt       (final Stmt s) {}  // this allows factoring of common stuff
  public void visitStmtAfter  (final Stmt s) {}

  public void visitStmtAssign (final StmtAssign s) { visitStmt(s); }

  public void visitStmtCall (final StmtCall s) { visitStmt(s); }

  public void visitStmtCase (final StmtCase s) { visitStmt(s); }

  public void visitStmtCompound (final StmtCompound s) { visitStmt(s); }

  public void visitStmtEmpty (final StmtEmpty s) { visitStmt(s); }

  public void visitStmtFor (final StmtFor s) { visitStmt(s); }

  public void visitStmtIf (final StmtIf s) { visitStmt(s); }

  public void visitStmtRepeat (final StmtRepeat s) { visitStmt(s); }

  public void visitStmtWhile (final StmtWhile s) { visitStmt(s); }


  // methods for Type classes
  public void visitTypeBefore (final Type t) {}  // these are for all kinds
  public void visitType       (final Type t) {}  // this allows factoring of common stuff
  public void visitTypeAfter  (final Type t) {}

  public void visitTypeArray (final TypeArray t) { visitType(t); }

  public void visitTypeError (final TypeError t) { visitType(t); }

  public void visitTypeId (final TypeId t) { visitType(t); }

  public void visitTypeObject (final TypeObject t) { visitType(t); }

  public void visitTypeObjectPointer (final TypeObjectPointer t) { visitType(t); }

  public void visitTypePointer (final TypePointer t) { visitType(t); }

  public void visitTypePrimBefore (final TypePrim t) {}  // for all Prim types
  public void visitTypePrim       (final TypePrim t) { visitType(t); }  // this allows factoring of common stuff
  public void visitTypePrimAfter  (final TypePrim t) {}

  public void visitTypePrimBool (final TypePrimBool t) { visitTypePrim(t); }

  public void visitTypePrimInt (final TypePrimInt t) { visitTypePrim(t); }

  public void visitTypePrimNil (final TypePrimNil t) { visitTypePrim(t); }

  public void visitTypePrimString (final TypePrimString t) { visitTypePrim(t); }

  public void visitTypeProcFunc (final TypeProcFunc t) { visitType(t); }

  public void visitTypeRecord (final TypeRecord t) { visitType(t); }

  public void visitTypeRange (final TypeRange t) { visitType(t); }

  public void visitOverriding (final Overriding o) {}
}

// Local Variables:
// mode: jde
// c-basic-offset: 2
// indent-tabs-mode: nil
// End:
