package MPC;
import java.util.*;

/**
 * A class for packing an source input position (line, column) into a single
 * Java int. At present it merely uses 16 bits each for line and column, and
 * does no overflow checking. This is adequate for MPC but falls short of the
 * robustness one desires in a production quality compiler.
 *
 * By convention (an unlike Java array indexing) the first line and first
 * column are numbered 1.
 *
 * @version 1.0
 */
public abstract class InputPos {

  /**
   * stores file positions of new line characters for computing line/col from
   * file position
   */
  private static ArrayList<Integer> NLpos = new ArrayList<Integer>(1000);

  static {
    // so first char of input is viewed as first of a line, etc.
    NLpos.add(-1);
  }

  /**
   * Notes, in NLpos, where an NL character appears in the input; assumes NLs
   * are noted in increasing order of file position :-)
   *
   * @param filePos position in the input file where an NL character appears;
   * the first position is 0, i.e., we use offsets from the beginning of the
   * file
   */
  public static void noteNL (int filePos) {
    NLpos.add(filePos);
  }

  /**
   * returns the line number of a file position
   *
   * line numbers start with 1, and we view the newlines as being on the next
   * line at column 0
   *
   * @param filePos the file position
   * @return the line containing that position; first line is 1
   */
  public static int lineOf (int filePos) {
    if (filePos < 0) return -1;
    int n = NLpos.size();
    int i = 0;
    while (i < n && NLpos.get(i).intValue() <= filePos)
      ++i;
    return i;
  }

  /**
   * returns the column number for a file position
   *
   * @param filePos the file position
   * @return the column number for that position; 0 means the newline that
   * started the line
   */
  public static int columnOf (int filePos) {
    if (filePos < 0) return -1;
    int line = lineOf(filePos);
    return filePos - NLpos.get(line-1).intValue();
  }

}

// Local Variables:
// mode: jde
// c-basic-offset: 2
// indent-tabs-mode: nil
// End:

