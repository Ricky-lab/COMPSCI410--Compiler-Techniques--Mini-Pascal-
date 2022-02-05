package MPC;
import java.io.*;

/**
 * Adds line and column number functionality to an ordinary Java
 * InputStream. Also remembers the current line of input for nice scanner
 * error messages.
 */
public class ScanInStream {

  /**
   * the underlying character InputStream
   */
  private InputStream inStr;

  /**
   * the next input character; it is a short so that we can mark end of file
   * as well (by using 0400).
   */
  private short inChar;

  /**
   * current file position in the InputStream; the first character is at
   * position 0
   */
  private int pos = -1;

  /**
   * current column position in the InputStream; the first character of each
   * line is at column 1 (the preceding newline is viewed as being at column
   * 0)
   */
  private int col = 0;

  /**
   * file position at which the current token started; -1 means not yet started
   */
  private int tokenPos = -1;

  /**
   * current input line, for error messages, etc.
   */
  private StringBuffer lineBuf = new StringBuffer(200);

  /**
   * The obvious constructor
   *
   * @param inStr the InputStream from which we will read and track lines and columns
   */
  public ScanInStream (InputStream inStr) {
    this.inStr = inStr;
  }

  /**
   * accessor to observe the current input character
   *
   * @return current input character; a short so that we can mark end of file as 0400
   */
  public short inChar () {
    return inChar;
  }

  /**
   * accessor to acquire the input position where the current token began
   * @see InputPos
   *
   * @return position where the current token began
   */
  public int getPos () {
    return tokenPos;
  }

  /**
   * accessor to acquire the current input position
   * @see InputPos
   *
   * @return the current input position
   */
  public int getEndPos () {
    return pos;
    //    if (col > 0)
    //      return InputPos.posFrom(line, col-1);
    //    else
    //      return InputPos.posFrom(line-1, lineBuf.length());
  }

  /**
   * accessor to observe the current input line
   *
   * @return the current input line
   */
  public String getInputLine () {
    return lineBuf.toString();
  }

  /**
   * indicate that we have not yet started a token
   */
  public void reset () {
    tokenPos = -1;  // indicates we have not yet started a token
  }

  /**
   * indicate that we are now in a token (we may already be so)
   */
  public void inToken () {
    if (tokenPos < 0)
      tokenPos = pos;
  }

  /**
   * advance the input by one character, updating pos, inChar, etc.
   * @return a short giving the next character
   */
  public short advance () {
    try {
      // get the next character (or 0400 for EOF) into inChar
      inChar = (short)inStr.read();
      pos++;
      if (inChar < 0) inChar = 0400;  // our special code for EOF

      // handle newline processing
      if (inChar == (short)'\n') {
        InputPos.noteNL(pos);
        col = 0;
      } else {
        // note: after a newline, this effectively clears the line buffer
        ++col;
        lineBuf.setLength(col);
        lineBuf.setCharAt(col-1, (char)inChar);
      }
      return inChar;
    } catch (IOException e) {
      throw new Error(e);
    }
  }

}


// Local Variables:
// mode: jde
// c-basic-offset: 2
// indent-tabs-mode: nil
// End:

