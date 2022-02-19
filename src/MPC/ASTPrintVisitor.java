package MPC;
import java.util.*;

/**
 * This defines an AST (Abstract Syntax Tree) visitor for printing ASTs.
 *
 * @version 1.0
 */
public class ASTPrintVisitor extends ASTNullVisitor
  implements ObjectVisitor, TokenVisitor
{

  /**
   * the stream to which to print
   */
  public final MPCStream ps;

  /**
   * TypeObjects are inherently recursive (since their methods will normally
   * take a pointer to their TypeObject as an argument).  The other possible
   * avenue for our AST to become a directed cyclic graph, via non-object
   * pointers, does not require us to print the recursion, as in that case
   * we merely print the binding.
   */
  private List<TypeObject> stack;

  /**
   * normally we want only the name of an object type to print,
   * not the full type (they're big!)
   */
  private boolean printFullObjectTypes = false;

  /**
   * the obvious constructor
   *
   * @param ps the stream to which to print
   */
  public ASTPrintVisitor (final MPCStream ps) {
    this.ps = ps;
    stack = new ArrayList<TypeObject>();
  }

  /**
   * Prints a Binding, by printing its TokenId, line number, (declaration
   * number and scope)
   *
   * @param b the Binding to print
   */
  public void visitBinding (final Binding b) {
    b.id.accept(this);
    ps.printf("[%d]", InputPos.lineOf(b.pos));
    
  }

  /**
   * Prints a Block: Decls, (offset), Stmts
   *
   * @param b the Block to print
   */
  public void visitBlock (final Block b) {
    ps.printStart("block: ");
    b.decls.accept(this);
    b.stmts.accept(this);
    ps.printFinish();
  }

  /**
   * Prints a CaseElement, CaseLabelList, (code label), Stmt
   *
   * @param c the CaseElement to print
   */
  public void visitCaseElement (final CaseElement c) {
    c.labels.accept(this);
    ps.printIndent();
    c.stmt.accept(this);
  }

  /**
   * Prints a CaseLabelList by printing each Expr (and its allocated temp
   * object) in the list
   *
   * @param l the CaseLabelList to print
   */
  public void visitCaseLabelList (final CaseLabelList l) {
    ps.printIndent();
    ps.printStart("labellist: ");
    for (Expr c : l.labels) {
      ps.printIndent();
      c.accept(this);
    }
    ps.printFinish();
  }

  /**
   * Prints each Decl in a Decls
   *
   * @param dd the Decls to print
   */
  public void visitDecls (final Decls dd) {
    printListHelper(dd.decls, "decls: ", true);
  }

  /**
   * Prints each Expr in an Exprs
   *
   * @param ee the Exprs to print
   */
  public void visitExprs (final Exprs ee) {
    printListHelper(ee.exprs, "exprs: ", true);
  }

  /**
   * Prints a Program: top level Decls, (offset), Block
   *
   * @param p the Prorgram to print
   */
  public void visitProgram (final Program p) {
    ps.printStart("program: ");
    p.decls.accept(this);
    ps.printIndent();
    p.block.accept(this);
    ps.printFinish();
    ps.println();
  }

  /**
   * Prints each Stmt in a Stmts
   *
   * @param ss the Stmts to print
   */
  public void visitStmts (final Stmts ss) {
    printListHelper(ss.stmts, "stmts: ", true);
  }


  // methods related to Decl classes

  /**
   * Prints an identifying tag before each Decl form
   *
   * @param d the Decl form (ignored since we output the same tag always)
   */
  public void visitDeclBefore (final Decl d) {
    ps.printStart("decl->");
  }

  /**
   * Matches the printStart of visitDeclBefore
   *
   * @param d the Decl form being visited (ignored, since we are just ending
   * indentation)
   */
  public void visitDeclAfter (final Decl d) {
    ps.printFinish();
  }

  /**
   * Prints a DeclConst: tag, Binding, Expr (value)
   *
   * @param d the DeclConst to print
   */
  public void visitDeclConst (final DeclConst d) {
    ps.print("const: ");
    d.bind.accept(this);
    ps.printIndent();
    d.expr.accept(this);
  }

  /**
   * Prints a DeclField: tag, Binding, (offset), Type
   */
  public void visitDeclField (final DeclField d) {
    ps.print("field: ");
    d.bind.accept(this);
    ps.printIndent();
    d.type.accept(this);
  }

  /**
   * Prints a DeclFormal: tag, Binding, mode, Type, (allocated address)
   *
   * @param d the DeclFormal to print
   */
  public void visitDeclFormal (final DeclFormal d) {
    ps.print("formal ");
    d.bind.accept(this);
    ps.printIndent();
    ps.printf("(varmode=%b)", d.isVar);
    ps.printIndent();
    d.type.accept(this);
  }

  /**
   * Prints a DeclMethod: signature (which yields the method tag), and implementation
   * binding (if any)
   *
   * @param m the DeclMethod to print
   */
  public void visitDeclMethod (final DeclMethod m) {
    ps.printStart("");
    m.type.accept(this);
    
    if (m.bind != null) {
      ps.printIndent();
      ps.print("implemented-by: ");
      m.bind.accept(this);
    }
    ps.printFinish();
  }

  /**
   * Prints a DeclProcFunc: function/procedure tag, Binding (name), (offset),
   * formals, result Type (if any), (result address, if any), (code label),
   * Block
   *
   * @param d the DeclProcFunc to print
   */
  public void visitDeclProcFunc (final DeclProcFunc d) {
    ps.print(d.type.resultType != null ? "function: " : "procedure: ");
    d.bind.accept(this);
    printListHelper(d.type.formals.decls, "formals: ", true);
    if (d.type.resultType != null) {
      ps.printIndent();
      d.type.resultType.accept(this);
    }
    ps.printIndent();
    d.block.accept(this);
  }

  /**
   * Prints a DeclProgram: tag, Binding (name)
   *
   * @param d the DeclProgram to print; note: this is NOT the program as a
   * whole: that is of class Program, not DeclProgram
   */
  public void visitDeclProgram (final DeclProgram d) {
    ps.print("program: ");
    d.bind.accept(this);
  }

  /**
   * Prints a DeclSpecial: tag, Binding (name)
   *
   * @param d the DeclSpecial to print
   */
  public void visitDeclSpecial (final DeclSpecial d) {
    ps.print("special: ");
    d.bind.accept(this);
  }

  /**
   * Prints a DeclType: tag, Binding (name), Type
   *
   * @param d the DeclType to print
   */
  public void visitDeclType (final DeclType d) {
    ps.print("type: ");
    d.bind.accept(this);
    ps.printIndent();
    printFullObjectTypes = true;
    d.type.accept(this);
    printFullObjectTypes = false;
  }

  /**
   * Prints a DeclVar: tag, Binding (name), Type, (allocated address)
   *
   * @param d the DeclVar to print
   */
  public void visitDeclVar (final DeclVar d) {
    ps.print("variable: ");
    d.bind.accept(this);
    ps.printIndent();
    d.type.accept(this);
  }


  // methods for Expr classes

  /**
   * Prints a tag for all Expr nodes, and fields common to all Expr's:
   * (allocated object, rhs/lhs (rvalue/lvalue), (estimated registers needed
   * to evaluate)
   *
   * @param e the Expr being visited
   */
  public void visitExprBefore (final Expr e) {
    ps.printStart("expr->");
    if (e.object != null)
      e.object.accept(this);
    ps.print(" ");
  }

  /**
   * Concludes printing an Expr: prints Type (if any) and unindents
   *
   * @param e the Expr being visited
   */
  public void visitExprAfter (final Expr e) {
    if (e.type != null) {
      ps.printIndent();
      ps.print("type->");
      e.type.accept(this);
    }
    ps.printFinish();
  }

  /**
   * Prints an ExprBinary: tag, operator, (evaluation order), left Expr, right
   * Expr
   *
   * @param e the ExprBinary being visited
   */
  public void visitExprBinary (final ExprBinary e) {
    ps.print("binexp: ");
    e.op.accept(this);
    ps.printIndent();
    e.expr1.accept(this);
    ps.printIndent();
    e.expr2.accept(this);
  }

  /**
   * Prints an ExprBinding: tag, (whether it is an argument-less function
   * call), Binding (name)
   *
   * @param e the ExprBinding being visited
   */
  public void visitExprBinding (final ExprBinding e) {
    ps.print("binding: ");
    e.bind.accept(this);
  }

  /**
   * Prints an ExprCall: tag, Binding (name), actuals argument Exprs
   *
   * @param e the ExprCall being visited
   */
  public void visitExprCall (final ExprCall e) {
    ps.printf("call: isMethodCall=%s ", e.isMethodCall);
    e.bind.accept(this);
    e.actuals.accept(this);
  }

  /**
   * Print an ExprError: tag
   *
   * @param e the ExprError being visited
   */
  public void visitExprError (final ExprError e) {
    ps.print("error:");
  }

  /**
   * Prints an ExprId: tag, TokenId
   *
   * @param e the ExprId being visited
   */
  public void visitExprId (final ExprId e) {
    ps.print("id: ");
    e.id.accept(this);
  }

  /**
   * Prints an ExprInt: tag, integer literal value (decimal)
   *
   * @param e the ExprInt being visited
   */
  public void visitExprInt (final ExprInt e) {
    ps.printf("int: %d", e.value);
  }

  /**
   * Prints an ExprNil: tag
   *
   * @param e the ExprNil being visited
   */
  public void visitExprNil (final ExprNil e) {
    ps.print("nil:");
  }

  /**
   * Prints an ExprString: tag, string literal
   *
   * @param e the ExprString being visited
   */
  public void visitExprString (final ExprString e) {
    ps.printf("string: '%s'", e.value);
  }

  /**
   * Prints an ExprUnary: tag, operator, Expr (subexpression)
   *
   * @param e the ExprUnary being visited
   */
  public void visitExprUnary (final ExprUnary e) {
    ps.print("unexp: ");
    e.op.accept(this);
    ps.printIndent();
    e.expr.accept(this);
  }


  // methods for Stmt classes

  /**
   * Prints a tag before each Stmt
   *
   * @param s the Stmt being visited (ignored since we are outputting a fixed
   * tag for all here)
   */
  public void visitStmtBefore (final Stmt s) {
    ps.printStart("stmt->");
  }

  /**
   * Concludes printing a Stmt (unindents)
   *
   * @param s the Stmt being visited (ignored since all we do is unindent)
   */
  public void visitStmtAfter (final Stmt s) {
    ps.printFinish();
  }

  /**
   * Prints a StmtAssign: tag, variable Expr, value Expr
   *
   * @param s the StmtAssign to print
   */
  public void visitStmtAssign (final StmtAssign s) {
    ps.print("assignstmt:");
    ps.printIndent();
    s.var.accept(this);
    ps.printIndent();
    s.expr.accept(this);
  }

  /**
   * Prints a StmtCall: tag, ExprCall
   *
   * @param s the StmtCall to print
   */
  public void visitStmtCall (final StmtCall s) {
    ps.print("callstmt:");
    ps.printIndent();
    s.call.accept(this);
  }

  /**
   * Prints a StmtCase: tag, case Expr, list of CaseElement's
   *
   * @param s the StmtCase to print
   */
  public void visitStmtCase (final StmtCase s) {
    ps.print("casestmt:");
    ps.printIndent();
    s.expr.accept(this);
    ps.printIndent();
    ps.printStart("caselist: ");
    for (final CaseElement ce : s.list) {
      ce.accept(this);
    }
    ps.printFinish();
  }

  /**
   * Prints a StmtCompound: tag, the contained Stmts
   *
   * @param s the StmtCompound to print
   */
  public void visitStmtCompound (final StmtCompound s) {
    ps.print("compoundstmt:");
    s.stmts.accept(this);
  }

  /**
   * Prints a StmtEmpty: just a tag
   *
   * @param s the StmtEmpty to print
   */
  public void visitStmtEmpty (final StmtEmpty s) {
    ps.print("emptystmt:");
  }

  /**
   * Prints a StmtFor: tag, Binding (variable name), init Expr, to Expr, (to
   * (limit) allocated temporary object), (two extra temporary objects used in
   * the generated code), direction (up/down), body Stmt
   *
   * @param s the StmtFor to print
   */
  public void visitStmtFor (final StmtFor s) {
    ps.print("forstmt:");
    s.name.accept(this);
    ps.printIndent();
    s.init.accept(this);
    ps.printIndent();
    s.to.accept(this);
    ps.printIndent();
    ps.printf("(upward:%b)", s.upward);
    ps.printIndent();
    s.stmt.accept(this);
  }

  /**
   * Prints a StmtIf: tag, if Expr, then Stmt, else Stmt (if any)
   *
   * @param s the StmtIf to print
   */
  public void visitStmtIf (final StmtIf s) {
    ps.print("ifstmt: ");
    ps.printIndent();
    s.expr.accept(this);
    ps.printIndent();
    s.stmtThen.accept(this);
    if (s.stmtElse != null) {
      ps.printIndent();
      ps.printStart("else: ");
      ps.printIndent();
      s.stmtElse.accept(this);
      ps.printFinish();
    }
  }

  /**
   * Prints a StmtRepeat: tag, body Stmt, until Expr
   *
   * @param s the StmtRepeat to print
   */
  public void visitStmtRepeat (final StmtRepeat s) {
    ps.print("repeatstmt:");
    ps.printIndent();
    s.expr.accept(this);
    s.stmts.accept(this);
  }

  /**
   * Prints a StmtWhile: tag, while Expr, do Stmt
   *
   * @param s the StmtWhile to print
   */
  public void visitStmtWhile (final StmtWhile s) {
    ps.print("whilestmt:");
    ps.printIndent();
    s.expr.accept(this);
    ps.printIndent();
    s.stmt.accept(this);
  }


  // methods for Type classes

  /**
   * Prints a tag for all Type nodes and some things in common to all nodes:
   * tag, (size), kind
   *
   * @param t the Type being visited (largely ignored, though size is
   * relevant)
   */
  public void visitTypeBefore (final Type t) {
    ps.printStart("type->");
    ps.print("kind=");
  }

  /**
   * Does work necessary after printing each Type, namely unindent
   *
   * @param t the Type being visited (ignored since all we are doing is
   * unindent)
   */
  public void visitTypeAfter (final Type t) {
    ps.printFinish();
  }

  /**
   * Prints a TypeArray: tag, index Type, element Type
   *
   * @param t the TypeArray to print
   */
  public void visitTypeArray (final TypeArray t) {
    ps.print("array:");
    t.indexType.accept(this);
    ps.printIndent();
    t.elementType.accept(this);
  }

  /**
   * Prints a TypeError: tag only
   *
   * @param t the TypeError to print (ignored; we print just a tag)
   */
  public void visitTypeError (final TypeError t) {
    ps.print("ERROR");
  }

  /**
   * Print a TypeId: tag, Binding (name)
   *
   * @param t the TypeId to print
   */
  public void visitTypeId (final TypeId t) {
    ps.print("ident: ");
    t.bind.accept(this);
  }

  /**
   * Print a TypeObject: tag, supertype, fields, methods, overrides
   *
   * @param t the TypeObject to print
   */
  public void visitTypeObject (final TypeObject t) {
    ps.printf("object: name=%s", t.id);
    if (!printFullObjectTypes) return;

    int index = stack.indexOf(t);
    if (index > -1) {
      int depth = stack.size() - index - 1;
      ps.printf(" recursion-depth: %d", depth);
      return;
    }
    stack.add(t);

    if (t.supertype != null) {
      ps.printIndent();
      ps.printStart("extends: ");
      t.supertype.accept(this);
      ps.printFinish();
    }

    ps.printIndent();
    ps.printStart("abstract: ");
    ps.print(t.isAbstract);
    ps.printFinish();

    printListHelper(t.fields   , "fields: "   , true);
    printListHelper(t.methods  , "methods: "  , true);
    printListHelper(t.overrides, "overrides: ", true);

    stack.remove(t);
  }

  /**
   * Prints a TypeObjectPointer: tag, element (pointed at) Type.
   *
   * @param t the TypeObjectPointer to print
   */
  public void visitTypeObjectPointer (final TypeObjectPointer t) {
    ps.print("pointer:");
    ps.printIndent();
    // t.bind is null, so print the element type
    t.element.accept(this);
  }

  /**
   * Prints a TypePointer: tag, binding of the element (pointed at) Type
   *
   * @param t the TypePointer to print
   */
  public void visitTypePointer (final TypePointer t) {
    ps.print("pointer:");
    ps.printIndent();
    if (t.bind != null) {
      t.bind.accept(this);
    }
  }

  /**
   * Does work for each primitive type: the name of the type
   * Note: there are no type-specific methods, since the tag is all we print
   *
   * @param t the TypePrim to print
   */
  public void visitTypePrimBefore (final TypePrim t) {
    ps.print(t.name);
  }

  /**
   * Print a TypeProcFunc: tag, function/procedure, name, formals, resultType
   * 
   * @param t the TypeProcFunc to print
   */
  public void visitTypeProcFunc (final TypeProcFunc t) {
    ps.printf("method %s ", (t.resultType == null) ? "procedure" : "function");
    ps.printf("name=%s", t.name.id);

    printListHelper(t.formals.decls, "formals: ", true);

    if (t.resultType != null) {
      ps.printIndent();
      t.resultType.accept(this);
    }
  }

  /**
   * Prints a TypeRange: tag, low value Expr, high value Expr
   *
   * @param t the TypeRange to print
   */
  public void visitTypeRange (final TypeRange t) {
    ps.print("subrange:");
    ps.printIndent();
    t.lo.accept(this);
    ps.printIndent();
    t.hi.accept(this);
  }


  /**
   * Prints a TypeRecord: tag, each DeclField
   *
   * @param t the TypeRecord to print
   */
  public void visitTypeRecord (final TypeRecord t) {
    ps.print("record:");

    printListHelper(t.fields, "fields: ", true);
  }

  /**
   * Prints an Overriding: binding of what, binding of with
   *
   * @param o the Overriding to print
   */
  public void visitOverriding (final Overriding o) {
    ps.printStart("");
    o.overridden.accept(this);
    ps.print(" ");
    o.with.accept(this);
    ps.printFinish();
  }

  // ObjectVisitor method implementations
  // methods for work related to every kind of MPCObject

  /**
   * Does no work, but must be here to implement the interface
   *
   * @param n the MPCObject being visited
   */
  public void visitEveryBefore (final MPCObject n) {}

  /**
   * Does no work, but must be here to implement the interface
   *
   * @param n the MPCObject being visited
   */
  public void visitEveryAfter (final MPCObject n) {}
  
  // methods for work related to each subclass
  /**
   * Prints an ObjectValueError: just a tag
   *
   * @param e the ObjectValueError to print
   */
  public void visitObjectValueError (final ObjectValueError e) {
    ps.print("[error]");
  }

  /**
   * Print an ObjectValueInteger: tag, integer value (decimal)
   *
   * @param i the ObjectValueInteger to print
   */
  public void visitObjectValueInteger (final ObjectValueInteger i) {
    ps.printf("[value=%s]", i.value.toString());
  }

  /**
   * Prints an ObjectValueString: tag, the (literal) string value
   *
   * @param s the ObjectValueString to print
   */
  public void visitObjectValueString (final ObjectValueString s) {
    ps.printf("[value=\'%s\']", s.text);
  }

  // implementations of TokenVisitor methods
  // methods for work related to every kind of Token

  /**
   * Prints a Token (Id, etc.); uses the toString() method, implemented by
   * each Token class, to get the String to print.
   *
   * @param t the Token to print
   */
  public void visitEveryBefore (final Token t) { ps.print(t.toString()); }

  // The rest of these must be here to complete implementation of the interface

  public void visitToken       (final Token t) {}
  public void visitEveryAfter  (final Token t) {}
  
  // methods for work related to each subclass
  public void visitTokenId     (final TokenId     t) {}
  public void visitTokenInt    (final TokenInt    t) {}
  public void visitTokenKey    (final TokenKey    t) {}
  public void visitTokenOp     (final TokenOp     t) {}
  public void visitTokenString (final TokenString t) {}


  /**
   * A helper method to print a List of ASTNodes.
   *
   * @param list The list of ASTNodes to print
   * @param name name of the list in printed output
   * @param indent whether or not to indent the output
   */
  private void printListHelper (final List<? extends ASTNode> list,
                                final String name,
                                final boolean indent) {
    if ((list != null) &&
        (list.size() > 0)) {
      if (indent) {
        ps.printIndent();
      }

      ps.printStart(name);
      for (final ASTNode entry : list) {
        if (entry == null) {
          continue;
        }

        ps.printIndent();
        entry.accept(this);
      }
      ps.printFinish();
    }
  }
}

// Local Variables:
// mode: jde
// c-basic-offset: 2
// indent-tabs-mode: nil
// End:
