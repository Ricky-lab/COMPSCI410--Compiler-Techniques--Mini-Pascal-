package MPC;
/**
 * This interface defines things common to all AST (Abstract Syntax Tree)
 * visitor classes.
 *
 * We omit javadoc on individual methods, since their interface is obvious.
 * @see ASTNullVisitor for additional explanation
 * @version 1.0
 */
public interface ASTVisitor {

  // methods for work related to every node
  public void visitEveryBefore (final ASTNode n);
  public void visitEveryAfter  (final ASTNode n);
  
  // methods for work related to classes not subclassed
  public void visitBinding (final Binding b);

  public void visitBlock (final Block b);

  public void visitCaseElement (final CaseElement c);

  public void visitCaseLabelList (final CaseLabelList l);

  public void visitDecls (final Decls dd);

  public void visitExprs (final Exprs ee);

  public void visitProgram (final Program p);

  public void visitStmts (final Stmts ss);


  // methods related to Decl classes
  public void visitDeclBefore (final Decl d);  // these are for all kinds
  public void visitDecl       (final Decl d);
  public void visitDeclAfter  (final Decl d);

  public void visitDeclConst (final DeclConst d);

  public void visitDeclField (final DeclField d);

  public void visitDeclFormal (final DeclFormal d);

  public void visitDeclMemberBefore (final DeclMember m);
  public void visitDeclMember       (final DeclMember m);
  public void visitDeclMemberAfter  (final DeclMember m);

  public void visitDeclMethod (final DeclMethod m);

  public void visitDeclProcFunc (final DeclProcFunc d);

  public void visitDeclProgram (final DeclProgram d);

  public void visitDeclSpecial (final DeclSpecial d);

  public void visitDeclType (final DeclType d);

  public void visitDeclVar (final DeclVar d);


  // methods for Expr classes
  public void visitExprBefore (final Expr e);  // these are for all kinds
  public void visitExpr       (final Expr e);
  public void visitExprAfter  (final Expr e);

  public void visitExprBinary (final ExprBinary e);

  public void visitExprBinding (final ExprBinding e);

  public void visitExprCall (final ExprCall e);

  public void visitExprError (final ExprError e);

  public void visitExprId (final ExprId e);

  public void visitExprInt (final ExprInt e);

  public void visitExprNil (final ExprNil e);

  public void visitExprString (final ExprString e);

  public void visitExprUnary (final ExprUnary e);


  // methods for Stmt classes
  public void visitStmtBefore (final Stmt s);  // these are for all kinds
  public void visitStmt       (final Stmt s);
  public void visitStmtAfter  (final Stmt s);

  public void visitStmtAssign (final StmtAssign s);

  public void visitStmtCall (final StmtCall s);

  public void visitStmtCase (final StmtCase s);

  public void visitStmtCompound (final StmtCompound s);

  public void visitStmtEmpty (final StmtEmpty s);

  public void visitStmtFor (final StmtFor s);

  public void visitStmtIf (final StmtIf s);

  public void visitStmtRepeat (final StmtRepeat s);

  public void visitStmtWhile (final StmtWhile s);


  // methods for Type classes
  public void visitTypeBefore (final Type t);  // these are for all kinds
  public void visitType       (final Type t);
  public void visitTypeAfter  (final Type t);

  public void visitTypeArray (final TypeArray t);

  public void visitTypeError (final TypeError t);

  public void visitTypeId (final TypeId t);

  public void visitTypeObject (final TypeObject t);

  public void visitTypeObjectPointer (final TypeObjectPointer t);

  public void visitTypePointer (final TypePointer t);

  public void visitTypePrimBefore (final TypePrim t);  // for all Prim types
  public void visitTypePrim       (final TypePrim t);
  public void visitTypePrimAfter  (final TypePrim t);

  public void visitTypePrimBool (final TypePrimBool t);

  public void visitTypePrimInt (final TypePrimInt t);

  public void visitTypePrimNil (final TypePrimNil t);

  public void visitTypePrimString (final TypePrimString t);

  public void visitTypeProcFunc (final TypeProcFunc t);

  public void visitTypeRecord (final TypeRecord t);

  public void visitTypeRange (final TypeRange t);

  public void visitOverriding (final Overriding o);
}

// Local Variables:
// mode: jde
// c-basic-offset: 2
// indent-tabs-mode: nil
// End:
