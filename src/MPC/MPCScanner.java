package MPC;
import java.io.*;
import java.util.*;
import java_cup.runtime.*;

/**
 * Scans an input character stream into Pascal tokens. Its primary external
 * interface consists of its constructor and the next_token() method (expected
 * by the java_cup Parser). We implement it on top of ScanInStream, which adds
 * line/column position information to an ordinary Java InputStream.
 */
public class MPCScanner implements java_cup.runtime.Scanner {

  /**
   * Character classes, i.e., equivalence classes of input letters
   */
  private static enum CharClass {
    EOF,    // end of file
    SPC,    // white space
    LET,    // letters A-Z a-z
    DIG,    // digits 0-9
    OP,     // single character operators
    LC,     // left  curly bracket {
    RC,     // right curly bracket }
    EQ,     // equals =
    LT,     // less    than <
    GT,     // greater than >
    COL,    // colon :
    OTH,    // other non-bad characters
    BAD,    // illegal characters
    
	//New added char class
	QUO, 	// for the quote character ''
	AST,	// for the asterisk  *
	LP,		// left parenthesis  (
	RP,		// right parenthesis )
	STR, 	// for string
	NL,		// new line sign
	HASH,	// number sign	# 
	UNDER,	// underscore   _
	DOT	// for operator .
	//CAR		// for ^
	//SEMI;	// for ;
  }

  /**
   * table that maps input characters to their classes
   */
  private static CharClass classTable[] = new CharClass[257];

  /**
   * Really just a record/struct, indicating that a range of character values
   * (from lo through hi, inclusive) belong to the indicated character class
   * (and are treated equivalently for purposes of scanning).
   */
  private static class ClassRange {

    /**
     * The CharClass to associate with these input characters
     */
    CharClass charClass;

    /**
     * The low end value of the range of characters having this CharClass
     */
    char lo;

    /**
     * The high end value of the range of characters having this CharClass
     */
    char hi;

    /**
     * The obvious constructor
     *
     * @param charClass the CharClass for this ClassRange
     * @param lo the low end of the character values for this ClassRange
     * @param hi the high end of the character values for this ClassRange
     */
    ClassRange(CharClass charClass, char lo, char hi) {
      this.charClass = charClass;
      this.lo        = lo;
      this.hi        = hi;
    }
  }

  /**
   * data used for initializing classTable
   */
  private static final ClassRange classRanges[] = {
    // - each line gives a ClassRange
    // - we guarantee that the entries are processed in order, so an
    //   earlier entry can give defaults and a later one override
    new ClassRange(CharClass.BAD  , '\000', '\u0100'),  // init all to the default: BAD
    new ClassRange(CharClass.OTH  , '\040', '\176'),  // default for printable range is OTH
    new ClassRange(CharClass.SPC  , '\011', '\012'),  // tab, nl
    new ClassRange(CharClass.SPC  , '\015', '\015'),  // cr
    new ClassRange(CharClass.SPC  , '\040', '\040'),  // space
    new ClassRange(CharClass.LET  , '\101', '\132'),  // A-Z
    new ClassRange(CharClass.LET  , '\141', '\172'),  // a-z
    new ClassRange(CharClass.DIG  , '\060', '\071'),  // 0-9
    new ClassRange(CharClass.OP   , '\050', '\051'),  // ( )
    new ClassRange(CharClass.OP   , '\053', '\053'),  // +
    new ClassRange(CharClass.OP   , '\054', '\054'),  // ,
    new ClassRange(CharClass.OP   , '\055', '\055'),  // -
    new ClassRange(CharClass.OP   , '\056', '\056'),  // .
    new ClassRange(CharClass.OP   , '\073', '\073'),  // ;
    new ClassRange(CharClass.COL  , '\072', '\072'),  // :
    new ClassRange(CharClass.LT   , '\074', '\074'),  // <
    new ClassRange(CharClass.EQ   , '\075', '\075'),  // =
    new ClassRange(CharClass.GT   , '\076', '\076'),  // >
    new ClassRange(CharClass.LC   , '\173', '\173'),  // {
    new ClassRange(CharClass.RC   , '\175', '\175'),  // }
    
    // here is a good place to insert additional entries :-)
    // New added class range according to the new added CharClass
    
  	new ClassRange(CharClass.QUO  , '\047', '\047'),  // '
  	new ClassRange(CharClass.AST  , '\052', '\052'),  // *
  	new ClassRange(CharClass.LP   , '\050', '\050'),  // (
  	new ClassRange(CharClass.RP   , '\051', '\051'),  // )
  	new ClassRange(CharClass.NL   , '\012', '\012'),  // newline
  	new ClassRange(CharClass.HASH , '\043', '\043'),  // #
  	new ClassRange(CharClass.UNDER, '\137', '\137'),  // _
  	new ClassRange(CharClass.DOT  , '\056', '\056'),  // operator ..
  	new ClassRange(CharClass.OP  , '\136', '\136'),  // ^
  	new ClassRange(CharClass.OP  , '\133', '\133'),  // [
  	new ClassRange(CharClass.OP  , '\135', '\135'),  // ]

  	
    new ClassRange(CharClass.EOF  , '\u0100', '\u0100')
  	}; // EOF is 256 (only)
  

  /**
   * routine to initialize the characters class table given the data
   * immediately above here
   */
  private static void initClassTable () {
    // initialize classTable
    for (ClassRange cr : classRanges) {
      Arrays.fill(classTable, (int)(cr.lo), (int)(cr.hi)+1, cr.charClass);
    }
  }

  /**
   * codes for accepting states, one for each kind of token initially scanned
   * (an ID may turn out to be a keyword)
   */
  private static enum Accept {
    NONE,       // no action
    ID,         // identifier
    INT,        // integer literal
    OP,         // operator
    STR,        // string literal
    EOF;        // end of file
  }

  /**
   * codes for actions that can happen at transitions
   */
  private static enum Action {
    CLEAR,      // clear token buffer (useful when transitioning to comment)
    DIGIT,      // accumulate a digit according to the number base
    SETBASE,    // set the number base
    LOWER,      // change to lower case (useful for both identifiers and integer literals)
    HASH        // useful for identifiers and strings
  }

  /**
   * the scanner is written as a finite state machine; this nested (but not
   * "inner") class defines the information we associate with each state
   */
  private static enum State {

    SBeg(Accept.NONE), // starting state
    SId (Accept.ID  ), // in an identifier
    SNum(Accept.INT ), // in an integer literal
    SOp (Accept.OP  ), // an operator
    SLt (Accept.OP  ), // saw a <
    SGC (Accept.OP  ), // saw a > or :
    SCom(Accept.NONE), // in a comment
    SErr(Accept.NONE), // the error state
    SEOF(Accept.EOF ), // saw EOF
    SQUO(Accept.STR ), // saw ' (left ')
    
    //New added states according to new characters
	SDOT(Accept.OP  ), // for operator ..
	SLP (Accept.OP  ), // for just having see a left parenthesis
	SCA (Accept.NONE), // for being in a comment but having seen an AST
	SSTR(Accept.NONE) ,   // in the string
	
	SUND(Accept.NONE), // for identifier just after an underscore
	
	SHas(Accept.NONE), // for integers just having seen hash 
	SUNH(Accept.NONE), // for integers just having seen an underscore without ever having seen a hash
	SNtH(Accept.INT), // for integers just having hash after digits
	SUaH(Accept.NONE) // for integers just seen an underscore after having seen an earlier hash
	
	//SNLS(Accept.STR)  //nl within String
	 
	; 
	//SSQUO(Accept.STR);// for the ending quo, accept string
	
	

    /**
     * Which kind of token to accept at this, if any, if the input does not
     * allow a transitions
     */
    public final Accept accept;         // accept

    /**
     * transition array for this state: the next State to which we should go
     * for each class of input characters
     */
    private final EnumMap<CharClass,Transition> transitions;  // transition map

    /**
     * constructor to create State with given action and input keeping
     * behavior; we use other methods to set up transitions after creating
     * State objects
     *
     * @param accept the Accept for this state
     */
    State (Accept accept) {
      this.accept      = accept;
      this.transitions = new EnumMap<CharClass,Transition>(CharClass.class); // transitions NOT set up yet
    }
      
    public static Transition errorTransition = new Transition(SErr);

    /**
     * Sets the default Transition for this State.  In general, this must be
     * done AFTER all the State objects are created.  The default default is
     * SErr with no actions, indicating no (legal) transition.  Note that
     * transitions for BAD and EOF are _ALWAYS_ to SErr, unless set
     * individually.
     *
     * @param transition the default Transition out of this State
     */
    public void setDefault (Transition transition) {
      for (CharClass cc : CharClass.values()) {
        transitions.put(cc, transition);
      }
      transitions.put(CharClass.BAD, errorTransition);
      transitions.put(CharClass.EOF, errorTransition);
    }

    /**
     * Sets a particular transition; generally done after setDefault.
     *
     * @param charClass the character class causing the transition
     * @param transition the Transition
     */
    public void setTransition (CharClass charClass, Transition transition) {
      transitions.put(charClass, transition);
    }

    /**
     * Looks up the Transition from this State, given an input character
     * class.
     *
     * @param charClass the input character class for which we wish to make a
     * transition
     * @return the Transition for the given character class
     */
    public Transition move (CharClass charClass) {
      return transitions.get(charClass);
    }
  }

  /**
   * Represents a transition to a new State, with a sequence of Actions to take
   */
  private static class Transition {
    /**
     * The State that is the target of this Transition
     */
    public final State state;

    /**
     * The sequence of Actions to cause for this transition, in order; may be empty
     */
    public final Action actions[];

    /**
     * constructor to create Transition to given state with given actions
     *
     * @param state the target state
     * @param actions the actions to take on this transition
     */
    public Transition (State state, Action... actions) {
      this.state   = state;
      this.actions = actions;
    }
  }

  /**
   * This static initializer sets up the State transitions, after the above
   * enums are initialized.
   */
  private static void initStates () {
    // set the default default
    for (State state : State.values()) {
      state.setDefault(State.errorTransition);
    }
    // set default transition, if not SErr
    State.SCom .setDefault(new Transition(State.SCom)); 					// in the comments
    State.SCA  .setDefault(new Transition(State.SCom)); 	
    
    State.SSTR .setDefault(new Transition(State.SSTR, Action.HASH)); 		// in the string
    
    // set individual, non-default, transitions for each state
    State.SBeg.setTransition(CharClass.EOF  , new Transition(State.SEOF)); // start: end of file
    State.SBeg.setTransition(CharClass.SPC  , new Transition(State.SBeg)); // start: white space
    State.SBeg.setTransition(CharClass.LET  , new Transition(State.SId , Action.LOWER, Action.HASH)); // start: letter => get identifier
    State.SBeg.setTransition(CharClass.DIG  , new Transition(State.SNum, Action.DIGIT)); // start: digit => get integer
    State.SBeg.setTransition(CharClass.OP   , new Transition(State.SOp , Action.HASH )); // start: single character operator
    State.SBeg.setTransition(CharClass.EQ   , new Transition(State.SOp , Action.HASH )); // start: single character operator
    State.SBeg.setTransition(CharClass.LT   , new Transition(State.SLt , Action.HASH )); // start: single/double character operator
    State.SBeg.setTransition(CharClass.GT   , new Transition(State.SGC , Action.HASH )); // start: single/double character operator
    State.SBeg.setTransition(CharClass.COL  , new Transition(State.SGC , Action.HASH )); // start: single/double character operator
    State.SBeg.setTransition(CharClass.LC   , new Transition(State.SCom));               // start: left curly bracket => comment
    State.SBeg.setTransition(CharClass.NL   , new Transition(State.SBeg));               // start: new line of the text; head of the text
    State.SBeg.setTransition(CharClass.DOT  , new Transition(State.SDOT , Action.HASH));// start: .
    State.SBeg.setTransition(CharClass.LP   , new Transition(State.SLP  , Action.HASH));  // start: for the comments: just saw the left (
    State.SBeg.setTransition(CharClass.QUO  , new Transition(State.SSTR ));				// start: for the start of a string
    State.SBeg.setTransition(CharClass.RP   , new Transition(State.SOp  , Action.HASH));//)
    State.SBeg.setTransition(CharClass.AST  , new Transition(State.SOp  , Action.HASH));

    
    //Identifiers: 
    State.SId .setTransition(CharClass.LET  , new Transition(State.SId , Action.LOWER, Action.HASH)); // identifiers continue with LET or DIG
    State.SId .setTransition(CharClass.DIG  , new Transition(State.SId , Action.HASH));
    State.SId .setTransition(CharClass.UNDER, new Transition(State.SUND, Action.HASH));	//Id to an _
    State.SUND.setTransition(CharClass.DIG  , new Transition(State.SId , Action.HASH));	// _ to a digit
    State.SUND.setTransition(CharClass.LET  , new Transition(State.SId , Action.LOWER, Action.HASH));	// _ to a letter
    
    
    //Integers:
    State.SNum.setTransition(CharClass.DIG  , new Transition(State.SNum, Action.DIGIT)); // integer literals continue with DIG
    State.SNum.setTransition(CharClass.UNDER, new Transition(State.SUNH)); // dit_
    State.SUNH.setTransition(CharClass.DIG  , new Transition(State.SNum, Action.DIGIT)); //dig_dig
    State.SNum.setTransition(CharClass.HASH , new Transition(State.SHas, Action.SETBASE)); // set the base of this number
    State.SHas.setTransition(CharClass.DIG  , new Transition(State.SNtH, Action.DIGIT)); // Int#dit
    State.SHas.setTransition(CharClass.LET  , new Transition(State.SNtH, Action.LOWER, Action.DIGIT)); // Int#dit
    State.SNtH.setTransition(CharClass.DIG  , new Transition(State.SNtH, Action.DIGIT)); // 
    State.SNtH.setTransition(CharClass.LET  , new Transition(State.SNtH, Action.LOWER, Action.DIGIT)); // 
    State.SNtH.setTransition(CharClass.UNDER, new Transition(State.SUaH)); // Int#dit_
    State.SUaH.setTransition(CharClass.DIG  , new Transition(State.SNtH, Action.DIGIT)); // 
    State.SUaH.setTransition(CharClass.LET  , new Transition(State.SNtH, Action.LOWER, Action.DIGIT)); // 


    //String:
    State.SSTR .setTransition(CharClass.NL  , new Transition(State.SErr));
    State.SSTR .setTransition(CharClass.QUO , new Transition(State.SQUO)); 	 			 // find one quo
    State.SQUO .setTransition(CharClass.NL  , new Transition(State.SErr)); 
    State.SQUO .setTransition(CharClass.QUO , new Transition(State.SSTR, Action.HASH)); 
    
    //Operator ..:
    State.SDOT .setTransition(CharClass.DOT , new Transition(State.SOp , Action.HASH)); // operator ..
    
    				// star in the comments but not ) following
    State.SLP  .setTransition(CharClass.AST , new Transition(State.SCom, Action.CLEAR)); 	// first left parenthesis
    State.SCom .setTransition(CharClass.AST , new Transition(State.SCA ));					// saw an * in comment
    State.SCA  .setTransition(CharClass.AST , new Transition(State.SCA)); 				// finish a comment with *)
    State.SCA  .setTransition(CharClass.RP  , new Transition(State.SBeg)); 				// finish a comment with *)
    State.SCA  .setTransition(CharClass.RC  , new Transition(State.SBeg)); 				// finish a comment with } from 
    State.SCom .setTransition(CharClass.RC  , new Transition(State.SBeg)); 			    // finish a comment with }
    // SOp has no outgoing transitions

    State.SLt .setTransition(CharClass.EQ , new Transition(State.SOp, Action.HASH)); // double char operator <=
    State.SLt .setTransition(CharClass.GT , new Transition(State.SOp, Action.HASH)); // double char operator <>

    State.SGC .setTransition(CharClass.EQ , new Transition(State.SOp, Action.HASH)); // double char operators >= and :=

    
                                      // most things continue it; see defaults

    // SEOF has no outgoing transitions

  }

  /**
   * The ScanInStream that we are scanning
   */
  private ScanInStream scanStr; // the customized input stream abstraction

  /**
   * A PrintStream to which we log all tokens created (but if tokstr is null,
   * we silently omit such logging)
   */
  private PrintStream  tokStr;  // output token logging stream (ok if null)

  /**
   * A PrintStream on which we can emit any error messages
   */
  private PrintStream  errStr;  // stream for error output

  /**
   * A buffer in which we accumulate characters for the current token
   */
  private StringBuffer tokBuf;  // buffer for building up a token

  /**
   * The current number base (10 to 36)
   */
  private int base;

  /**
   * The current integer value
   */
  private long currValue;

  /**
   * The current hash value
   */
  private int currHash;

  /**
   * This clears the token buffer and alerts our ScanInStream that we are
   * looking for a new token. We should call it right before starting to scan
   * each token.
   */
  private void reset () {
    tokBuf.setLength(0);
    scanStr.reset();
    base = 10;
    currValue = 0;
    currHash = 0;
  }

  /**
   * Adds an input character to the token buffer, first insuring that the
   * buffer can hold it (i.e., by growing the buffer if need be).
   *
   * @param newChar the character (as a Java short) that we wish to add
   */
  private void append (short newChar) {
    int oldLen = tokBuf.length();
    tokBuf.setLength(oldLen+1);
    tokBuf.setCharAt(oldLen, (char)newChar);
    scanStr.inToken();
  }

  /**
   * Builds a new scanner object given input, token output, and error output streams.
   *
   * @param inStream input stream to scan
   * @param tokStream stream on which to log created tokens (null means don't log)
   * @param errStream stream for any error messages
   */
  public MPCScanner (InputStream inStream,
                     PrintStream tokStream,
                     PrintStream errStream) {
    // set up stream instance variables
    scanStr = new ScanInStream(inStream);
    tokStr  = tokStream;
    errStr  = errStream;

    // initialize the token buffer
    tokBuf = new StringBuffer();

    // get the first character into the character scanner
    scanStr.advance();

    // get first token
    // next_token();
  }

  /**
   * Method that does the actual work of running the state machine to scan a
   * token; its name and interface are specified by java_cup.
   */
  public Symbol next_token () {
    // Start in SBeg and reset the token buffer and ScanInStream for start of token
    State currState = State.SBeg;
    reset();

    // run the state machine
    while (true) {
      // get the current input character and its class
      short inChar = scanStr.inChar();
      CharClass currClass = classTable[inChar];

      // check for a BAD input character
      if (currClass == CharClass.BAD) {
        System.err.println("========================================");
        mpc.ShowError(scanStr.getEndPos(), "Illegal character: " + charDescription(inChar));
        markError("ignoring bad character");
        System.err.println("========================================");
        System.err.flush();
      }
      else {
        // have a good character, but do we have a transition?
        Transition tr = currState.move(currClass);
        if (tr.state == State.SErr) {
          // if no transition, either accepting or an error
          Accept accept = currState.accept;
          if (accept != Accept.NONE) {
            // There is an action, so this is an accepting state; t is to
            // receive the Token for what we just scanned
            Token t = null;
            switch (accept) {
            case ID:
              // TokenId.get can also return keywords: TokenId has TokenKey
              // insert them all into the identifier table at class
              // initialization time.
              t = TokenId.get(tokBuf, currHash);
              break;
            case INT:
              t = TokenInt.get((int)(currValue));
              break;
            case OP:
              t = TokenOp.get(tokBuf, currHash);
              break;
            case EOF:
              t = Token.EOF;
              break;
            case STR:
              t = TokenString.get(tokBuf, currHash);
              break;
            default:
              mpc.ShowError(scanStr.getEndPos(),
                            "Unexpected accept code value: " + accept);
              markError();
              throw new AssertionError("Unhandled scanner accept code: scanner is broken");
            }
            // log the token we just built
            if (tokStr != null) {
              tokStr.println(t.toPrettyString());

            }
            // return a suitable new Symbol object to Parser (java_cup conforming)
            return new Symbol(t.code(), getPos(), getEndPos(), t);
          }
          // non-accepting state: complain and restart
          mpc.ShowError(scanStr.getEndPos(),
                        "Unexpected character: " + charDescription(inChar));
			markError("resetting to scan a new token");
          //tokStr.println(currState.toString());
          reset();
          currState = State.SBeg;
        }
        else {
          // have a transition: make it, and append input if we should
          currState = tr.state;
          for (Action action: tr.actions) {
            switch (action) {

            case CLEAR:
              reset();
              break;

            case DIGIT: {
              int converted = 0;
              if ((short)('0') <= inChar && inChar <= (short)('9')) {
                converted = inChar - (short)('0');
              } else if ((int)('a') <= inChar && inChar <= (int)('z')) {
                converted = 10 + (inChar - (short)('a'));
              } else {
                converted = 100;  // intentionally illegal
              }
              if (converted < base) {
                currValue = currValue * base + converted;
                if (currValue >= 0x80000000L) {
                  mpc.ShowError(scanStr.getEndPos(), "Integer literal value too large");
                  markError("resetting to scan a new token");
                  reset();
                  currState = State.SBeg;
                }
              } else {
                mpc.ShowError(scanStr.getEndPos(), "Integer literal digit " + (char)(inChar) + " too large for number base " + base);
                markError("resetting to scan a new token"/*. The current base is: " + base*/);
                reset();
                currState = State.SBeg;
              }
              break;
            }

            case SETBASE: {
              if (2 <= currValue && currValue <= 36) {
                base = (int)(currValue);
                currValue = 0;
              } else {
                mpc.ShowError(scanStr.getEndPos(), "Requested integer base " + currValue + " not in range 2 to 36");
                markError("resetting to scan a new token");
                reset();
                currState = State.SBeg;
              }
              break;
            }

            case LOWER:
              inChar = (short)(Character.toLowerCase((char)inChar));
              break;

            case HASH:
              currHash = StringMap.addToHash(currHash, (char)inChar);
              append(inChar);
              
              //System.out.println(currHash);
              break;

            default:
              mpc.ShowError(scanStr.getEndPos(),
                            "Unexpected accept code value: " + action);
              markError();
              throw new AssertionError("Unhandled scanner action code: scanner is broken");

            }
          }
        }
      }
      // if we do not return, go to the next character in the input
      scanStr.advance();
    }
    // never get here
  }

  /**
   * Returns a printable version of an input character given its code as a short;
   * may consists of multiple characters, for printing.
   *
   * @param ch a short giving the code of the charcter to describe
   * @return a printable String for that character
   */
  private String charAsPrintString (short ch) {
    return (ch  >= 040 && ch < 0177) ? String.valueOf((char)(ch)) : String.format("\\u%04x", ch);
  }

  /**
   * Offers a description of an input character given its code as a short
   *
   * @param ch a short giving the code of the charcter to describe
   * @return a String describing that character code
   */
  private String charDescription (short ch) {
    String numericDesc = String.format("code = %d = %03o = \\u%04x", ch, ch, ch);
    String desc = charAsPrintString(ch);
    return "\'" + desc + "\'" + " (" + numericDesc + ")";
  }

  /**
   * Returns the input position of the start of the token most recently scanned.
   * @see InputPos
   * @return an int describing the position in the input ok the start of the token
   */
  public int getPos () {
    return scanStr.getPos();
  }

  /**
   * Returns the input position of the end of the token most recently scanned.
   * @see InputPos
   * @return an int describing the position in the input on the end of the token
   */
  public int getEndPos () {
    return scanStr.getEndPos();
  }

  /**
   * Marks a scanning error, with no extra message;
   * @see #markError(int, String)
   */
  public void markError () {
    markError(InputPos.columnOf(scanStr.getEndPos()), null);
  }

  /**
   * Marks a scanning error, with an extra message;
   * @see #markError(int, String)
   *
   * @param more the extra message to print
   */
  public void markError (String more) {
    markError(InputPos.columnOf(scanStr.getEndPos()), more);
  }

  /**
   * Marks a scanning error in the current input line, given a column position
   * and an extra message. Does this to the error stream output, and marks by
   * writing dashes and then a caret in the position being marked. The first
   * column is number 1.
   *
   * @param col the column number to mark
   * @param more the additional String message to print
   */
  public void markError (int col, String more) {
    String rawLine = scanStr.getInputLine();
    int numChars = rawLine.length();
    int cols[] = new int[numChars];
    StringBuffer buf = new StringBuffer();
    int curcol = 1;
    for (int i = 0; i < rawLine.length(); ++i) {
      String p = charAsPrintString((short)(rawLine.charAt(i)));
      cols[i] = curcol;
      curcol += p.length();
      buf.append(p);
    }
    errStr.println(buf.toString());
    int dashcol = (((numChars == 0) || (col == 0)) ? 0 : cols[col-1]);
    for (int i = 1; i < dashcol; ++i) {
      errStr.print("-");
    }
    errStr.println("^");
    if (more != null) {
      errStr.println(more);
    }
    errStr.flush();
  }

  /**
   * This static initializer makes sure that we set up the character class
   * table and the parsing State objects.
   */
  static {
    // initialize the character class table
    initClassTable();

    // initialize the static state objects
    initStates();
  }

}

// Local Variables:
// mode: jde
// c-basic-offset: 2
// indent-tabs-mode: nil
// End:

