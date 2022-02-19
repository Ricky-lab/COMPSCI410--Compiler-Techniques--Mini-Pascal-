package MPC;
import java.util.*;

/**
 * Representation of a (Mini-)Pascal record type.
 */
public class TypeRecord extends Type {

  /**
   * List of DeclField nodes describing this record type
   */
  public List<DeclField> fields;

  /**
   * Creates a new TypeRecord instance given a List of its
   * DeclField nodes and its source (start/end) position
   *
   * @param ff    list of fields
   * @param left  starting position in program text
   * @param right ending position in program text
   */
  public TypeRecord (List<DeclField> ff, int left, int right) {
    super(left, right);
    fields = ff;
  }

  /**
   * Handles AST visiting for TypeRecord nodes
   *
   * @param v an ASTVisitor
   */
  public void accept (ASTVisitor v) {
    super.acceptBefore(v);
    v.visitTypeRecord(this);
    super.acceptAfter(v);
  }

  public boolean isSelectableType() {
    return true;
  }

  /**
   * convenient for iterating over the DeclField nodes of the record type
   *
   * @return an Iterator that produces each DeclField node in
   * turn
   */
  public Iterator<DeclField> fields () {
    return fields.iterator();
  }

  /**
   * indicates the number of fields of this record Type
   *
   * @return number of fields of this record Type
   */
  public int numFields () {
    return fields.size();
  }

}

// Local Variables:
// mode: jde
// c-basic-offset: 2
// indent-tabs-mode: nil
// End:

