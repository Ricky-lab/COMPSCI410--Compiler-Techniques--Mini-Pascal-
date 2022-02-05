{ More OBJECT test cases - checking for correct behaviors }

PROGRAM testobject4;

TYPE
  HasEquals = ABSTRACT OBJECT
    METHODS
      FUNCTION equals (self: HasEquals; other: ROOT): Boolean;
  END;

  Iterator = ABSTRACT OBJECT
    METHODS
      FUNCTION  next    (self: Iterator): ROOT;
      FUNCTION  hasNext (self: Iterator): Boolean;
      PROCEDURE remove  (self: Iterator);
  END;

  Iterable = ABSTRACT OBJECT
    METHODS
      FUNCTION getIterator (self: Iterable): Iterator;
  END;

  Collection = Iterable ABSTRACT OBJECT
    METHODS
      (* size related methods *)
      FUNCTION size    (self: Collection): Integer;
      FUNCTION isEmpty (self: Collection): Boolean;
      (* element related methods *)
      FUNCTION contains (self: Collection; o: HasEquals): Boolean;
      FUNCTION add      (self: Collection; o: HasEquals): Boolean;
      FUNCTION remove   (self: Collection; o: HasEquals): Boolean;
      (* whole collection methods *)
      PROCEDURE clear (self: Collection);
      FUNCTION addAll      (self, other: Collection): Boolean;
      FUNCTION containsAll (self, other: Collection): Boolean;
      FUNCTION equals      (self, other: Collection): Boolean;
      FUNCTION removeAll   (self, other: Collection): Boolean;
      FUNCTION retainAll   (self, other: Collection): Boolean;
  END;

TYPE
  AbsColl = Collection ABSTRACT OBJECT
    OVERRIDES
      isEmpty   = ACIsEmpty;
      contains  = ACContains;
      clear     = ACClear;
      addAll      = ACAddAll;
      containsAll = ACContainsAll;
      removeAll   = ACRemoveAll;
      retainAll   = ACRetainAll;
  END;

FUNCTION ACIsEmpty (self: AbsColl): Boolean;
BEGIN
  ACIsEmpty := (self.size = 0);
END;

FUNCTION ACContains (self: AbsColl; item: HasEquals): Boolean;
VAR
  it       : Iterator;
  searching: Boolean;
BEGIN
  ACContains := false;
  searching := true;
  it := self.getIterator;
  WHILE searching DO BEGIN
    IF it.hasNext THEN BEGIN
      IF NARROW(it.next, HasEquals).equals(item) THEN BEGIN
        ACContains := true;
        searching := false;
      END;
    END ELSE BEGIN
      searching := false;
    END;
  END;
END;

PROCEDURE ACClear (self: AbsColl);
VAR
  it: Iterator;
  o : ROOT;
BEGIN
  it := self.getIterator;
  WHILE it.hasNext DO BEGIN
    o := it.next;
    it.remove;
  END;
END;

FUNCTION ACAddAll (self:AbsColl; other: Collection): Boolean;
VAR it: Iterator;
BEGIN
  it := other.getIterator;
  ACAddAll := false;
  WHILE it.hasNext DO BEGIN
    IF self.add(NARROW(it.next, HasEquals)) THEN BEGIN
      ACAddAll := true;
    END;
  END;
END;

FUNCTION ACContainsAll (self: AbsColl; other: Collection): Boolean;
VAR
  it       : Iterator;
  searching: Boolean;
BEGIN
  ACContainsAll := true;
  searching := true;
  it := other.getIterator;
  WHILE searching DO BEGIN
    IF it.hasNext THEN BEGIN
      IF NOT self.contains(NARROW(it.next, HasEquals)) THEN BEGIN
        ACContainsAll := false;
        searching     := false;
      END;
    END;
  END;
END;

FUNCTION ACRemoveAll (self: AbsColl; other: Collection): Boolean;
VAR it: Iterator;
BEGIN
  it := other.getIterator;
  ACRemoveAll := false;
  WHILE it.hasNext DO BEGIN
    IF self.remove(NARROW(it.next, HasEquals)) THEN BEGIN
      ACRemoveAll := true;
    END;
  END;
END;

FUNCTION ACRetainAll (self: AbsColl; other: Collection): Boolean;
VAR it: Iterator;
BEGIN
  it := self.getIterator;
  ACRetainAll := false;
  WHILE it.hasNext DO BEGIN
    IF NOT other.contains(NARROW(it.next, HasEquals)) THEN BEGIN
      it.remove;
      ACRetainAll := true;
    END;
  END;
END;

TYPE
  ListIterator = Iterator ABSTRACT OBJECT
    METHODS
      (* new modification methods *)
      PROCEDURE add (self: ListIterator; o: ROOT);
      PROCEDURE set (self: ListIterator; o: ROOT);
      (* new accessing methods *)
      FUNCTION nextIndex (self: ListIterator): Integer;
      FUNCTION hasPrevious   (self: ListIterator): Boolean;
      FUNCTION previous      (self: ListIterator): ROOT;
      FUNCTION previousIndex (self: ListIterator): Integer;
  END;

  AbsList = AbsColl ABSTRACT OBJECT
    METHODS
      (* index based element access *)
      FUNCTION addIth      (self: AbsList; index: Integer; o: HasEquals): Boolean;
      FUNCTION getIth      (self: AbsList; index: Integer): HasEquals;
      FUNCTION removeIth   (self: AbsList; index: Integer): Boolean;
      FUNCTION setIth      (self: AbsList; index: Integer; o: HasEquals): HasEquals;
      (* search *)
      FUNCTION indexOf     (self: AbsList; o: HasEquals): Integer = ALIndexOf;
      FUNCTION lastIndexOf (self: AbsList; o: HasEquals): Integer = ALLastIndexOf;
      (* iterators *)
      FUNCTION getListIterator    (self: AbsList                ): ListIterator = ALGetListIterator;
      FUNCTION getListIteratorIth (self: AbsList; index: Integer): ListIterator = ALGetListIteratorIth;
      (* aggregate operations *)
      FUNCTION removeRange (self: AbsList; fromIndex, toIndex: Integer): Boolean = ALRemoveRange;
      FUNCTION addAllIth (self: AbsList; index: Integer; other: Collection): Boolean = ALAddAllIth;
    OVERRIDES
      add         = ALAdd;
      clear       = ALCLear;
      equals      = ALEquals;
      getIterator = ALGetIterator;
  END;

  ALItr = Iterator OBJECT
      cursor : Integer;
      lastRet: Integer;
      theList: AbsList;
    METHODS
      FUNCTION init (self: ALItr; lst: AbsList): ALItr = ALIInit;
    OVERRIDES
      hasNext = ALIHasNext;
      next    = ALINext;
      remove  = ALIRemove;
  END;

  ALLstItr = ListIterator OBJECT
      cursor : Integer;
      lastRet: Integer;
      theList: AbsList;
    METHODS
      FUNCTION init (self: ALLstItr; index: Integer; lst: AbsList): ALLstItr = ALLIInit;
    OVERRIDES
      hasNext       = ALLIHasNext;
      next          = ALLINext;
      nextIndex     = ALLINextIndex;
      hasPrevious   = ALLIHasPrevious;
      previous      = ALLIPrevious;
      previousIndex = ALLIPreviousIndex;
      set           = ALLISet;
      add           = ALLIAdd;
      remove        = ALLIRemove;
  END;

FUNCTION ALAdd (self: AbsList; o: HasEquals): Boolean;
BEGIN
  ALAdd := self.addIth(self.size, o);
END;

FUNCTION ALIndexOf (self: AbsList; o: HasEquals): Integer;
VAR
  it       : ListIterator;
  searching: Boolean;
BEGIN
  it := self.getListIterator;
  ALIndexOf := -1;
  searching := true;
  WHILE searching DO BEGIN
    IF it.hasNext THEN BEGIN
      IF o.equals(it.next) THEN BEGIN
        ALIndexOf := it.previousIndex;
        searching := false;
      END;
    END ELSE BEGIN
      searching := false;
    END;
  END;
END;

FUNCTION ALLastIndexOf (self: AbsList; o: HasEquals): Integer;
VAR
  it       : ListIterator;
  searching: Boolean;
BEGIN
  it := self.getListIteratorIth(self.size);
  ALLastIndexOf := -1;
  searching := true;
  WHILE searching DO BEGIN
    IF it.hasPrevious THEN BEGIN
      IF o.equals(it.previous) THEN BEGIN
        ALLastIndexOf := it.nextIndex;
        searching := false;
      END;
    END ELSE BEGIN
      searching := false;
    END;
  END;
END;

PROCEDURE ALClear (self: AbsList);
VAR b: Boolean;
BEGIN
  b := self.removeRange(0, self.size);
END;

FUNCTION ALAddAllIth (self: AbsList; index: Integer; other: Collection): Boolean;
VAR
  it: Iterator;
  b : Boolean;
BEGIN
  ALAddAllIth := false;
  IF (index >= 0) AND (index <= self.size) THEN BEGIN
    it := other.getIterator;  
    WHILE it.hasNext DO BEGIN
      b := self.addIth(index, NARROW(it.next, HasEquals));
      index := index + 1;
      ALAddAllIth := true;
    END;
  END;
END;

FUNCTION ALGetIterator (self: AbsList): Iterator;
BEGIN
  ALGetIterator := NEW(ALItr).init(self);
END;

FUNCTION ALGetListIterator (self: AbsList): ListIterator;
BEGIN
  ALGetListIterator := self.getListIteratorIth(0);
END;

FUNCTION ALGetListIteratorIth (self: AbsList; index: Integer): ListIterator;
BEGIN
  ALGetListIteratorIth := NEW(ALLstItr).init(index, self);
END;

FUNCTION ALRemoveRange (self: AbsList; fromIndex, toIndex: Integer): Boolean;
VAR
  it: ListIterator;
  i : Integer;
  o : ROOT;
BEGIN
  it := self.getListIteratorIth(fromIndex);
  FOR i := 0 TO (toIndex - fromIndex - 1) DO BEGIN
    o := it.next;
    it.remove;
  END;
END;

FUNCTION ALEquals (self: AbsList; other: Collection): Boolean;
VAR
  it1, it2 : Iterator;
  searching: Boolean;
  o1, o2   : HasEquals;
BEGIN
  IF self = other THEN BEGIN
    ALEquals := true;
  END ELSE IF NOT ISTYPE(other, AbsList) THEN BEGIN
    ALEquals := false;
  END ELSE BEGIN
    it1 := self .getIterator;
    it2 := other.getIterator;
    searching := true;
    ALEquals := true;
    WHILE searching DO BEGIN
      IF it1.hasNext AND it2.hasNext THEN BEGIN
        o1 := NARROW(it1.next, HasEquals);
        o2 := NARROW(it2.next, HasEquals);
        IF NOT o1.equals(o2) THEN BEGIN
          ALEquals := false;
          searching := false;
        END;
      END ELSE BEGIN
        searching := false;
      END;
    END;
    IF it1.hasNext OR it2.hasNext THEN BEGIN
      ALEquals := false;
    END;
  END;
END;


FUNCTION ALIInit (self: ALItr; lst: AbsList): ALItr;
BEGIN
  self.cursor  := 0;
  self.lastRet := -1;
  self.theList := lst;
  ALIInit := self;
END;

FUNCTION ALIHasNext (self: ALItr): Boolean;
BEGIN
  ALIHasNext := (self.cursor <> self.theList.size);
END;

FUNCTION ALINext (self: ALItr): ROOT;
BEGIN
  IF self.cursor >= self.theList.size THEN BEGIN
    ALINext := NIL;
  END ELSE BEGIN
    ALINext      := self.theList.getIth(self.cursor);
    self.lastRet := self.cursor;
    self.cursor  := self.cursor + 1;
  END;
END;

PROCEDURE ALIRemove (self: ALItr);
VAR b: Boolean;
BEGIN
  IF self.lastRet >= 0 THEN BEGIN
    b := self.theList.removeIth(self.lastRet);
    IF self.lastRet < self.cursor THEN BEGIN
      self.cursor := self.cursor - 1;
    END;
    self.lastRet := -1;
  END;
END;


FUNCTION ALLIInit (self: ALLstItr; index: Integer; lst: AbsList): ALLstItr;
BEGIN
  self.cursor  := index;
  self.lastRet := -1;
  self.theList := lst;
  ALLIInit := self;
END;

FUNCTION ALLIHasNext (self: ALLstItr): Boolean;
BEGIN
  ALLIHasNext := (self.cursor <> self.theList.size);
END;

FUNCTION ALLINext (self: ALLstItr): ROOT;
BEGIN
  IF self.cursor >= self.theList.size THEN BEGIN
    ALLINext := NIL;
  END ELSE BEGIN
    ALLINext := self.theList.getIth(self.cursor);
    self.lastRet := self.cursor;
    self.cursor  := self.cursor + 1;
  END;
END;

FUNCTION ALLINextIndex (self: ALLstItr): Integer;
BEGIN
  ALLINextIndex := self.cursor;
END;

FUNCTION ALLIHasPrevious (self: ALLstItr): Boolean;
BEGIN
  ALLIHasPrevious := (self.cursor <> 0);
END;

FUNCTION ALLIPrevious (self: ALLstItr): ROOT;
BEGIN
  IF self.cursor <= 0 THEN BEGIN
    ALLIPrevious := NIL;
  END ELSE BEGIN
    self.cursor := self.cursor - 1;
    self.lastRet := self.cursor;
    ALLIPrevious := self.theList.getIth(self.cursor);
  END;
END;

FUNCTION ALLIPreviousIndex (self: ALLstItr): Integer;
BEGIN
  ALLIPreviousIndex := self.cursor - 1;
END;

PROCEDURE ALLISet (self: ALLstItr; o: HasEquals);
VAR oo: HasEquals;
BEGIN
  IF self.lastRet >= 0 THEN BEGIN
    oo := self.theList.setIth(self.lastRet, o);
  END;
END;

PROCEDURE ALLIAdd (self: ALLstItr; o: HasEquals);
VAR b: Boolean;
BEGIN
  IF (self.cursor >= 0) AND (self.cursor <= self.theList.size) THEN BEGIN
    b := self.theList.addIth(self.cursor, o);
    self.lastRet := -1;
    self.cursor := self.cursor + 1;
  END;
END;

PROCEDURE ALLIRemove (self: ALLstItr);
VAR
  oo: HasEquals;
  b : Boolean;
BEGIN
  IF self.lastRet >= 0 THEN BEGIN
    b := self.theList.removeIth(self.lastRet);
    IF self.lastRet < self.cursor THEN BEGIN
      self.cursor := self.cursor - 1;
    END;
    self.lastRet := -1;
  END;
END;


TYPE
  LLEntry = Object
      element : HasEquals;
      next    : LLEntry;
      previous: LLEntry;
    METHODS
      FUNCTION init (self: LLEntry; element: HasEquals; next, previous: LLEntry): LLEntry = LLEInit;
  END;

  LinkedList = AbsList OBJECT
      header: LLEntry;
      mySize: Integer;
    METHODS
      FUNCTION init (self: LinkedList): LinkedList = LLInit;
      FUNCTION getFirst (self: LinkedList): HasEquals = LLGetFirst;
      FUNCTION getLast  (self: LinkedList): HasEquals = LLGetLast;
      FUNCTION removeFirst (self: LinkedList): HasEquals = LLRemoveFirst;
      FUNCTION removeLast  (self: LinkedList): HasEquals = LLRemoveLast;
      FUNCTION removeEntry (self: LinkedList; entry: LLEntry): HasEquals = LLRemoveEntry;
      PROCEDURE addFirst (self: LinkedList) = LLAddFirst;
      PROCEDURE addLast  (self: LinkedList) = LLAddLast;
      PROCEDURE addBeforeEntry (self: LinkedList; e: HasEquals; entry: LLEntry) = LLAddBeforeEntry;
      FUNCTION entryIth (self: LinkedList; index: Integer): LLEntry = LLEntryIth;
    OVERRIDES
      contains           = LLContains;
      size               = LLSize;
      add                = LLAdd;
      remove             = LLRemove;
      addAll             = LLAddAll;
      addAllIth          = LLAddAllIth;
      clear              = LLClear;
      getIth             = LLGetIth;
      setIth             = LLSetIth;
      addIth             = LLAddIth;
      removeIth          = LLRemoveIth;
      indexOf            = LLIndexOf;
      lastIndexOf        = LLLastIndexOf;
      getListIteratorIth = LLGetListIteratorIth;
  END;

  LLLstItr = ListIterator OBJECT
      lastReturned: LLEntry;
      nextEnt     : LLEntry;
      nextIdx     : Integer;
      theList     : LinkedList;
    METHODS
      FUNCTION init (self: LLLstItr; lst: LinkedList; index: Integer): LLLstItr = LLLIInit;
    OVERRIDES
      hasNext       = LLLIHasNext;
      next          = LLLINext;
      nextIndex     = LLLINextIndex;
      hasPrevious   = LLLIHasPrevious;
      previous      = LLLIPrevious;
      previousIndex = LLLIPreviousIndex;
      remove        = LLLIRemove;
      set           = LLLISet;
      add           = LLLIAdd;
  END;

FUNCTION LLEInit (self: LLEntry; element: HasEquals; next, previous: LLEntry): LLEntry;
BEGIN
  self.element  := element;
  self.next     := next;
  self.previous := previous;
  LLEInit := self;
END;

FUNCTION LLInit (self: LinkedList): LinkedList;
BEGIN
  self.header := NEW(LLEntry).init(NIL, NIL, NIL);
  self.header.next     := self.header;
  self.header.previous := self.header;
  self.size := 0;
  LLInit := self;
END;

FUNCTION LLGetFirst (self: LinkedList): HasEquals;
BEGIN
  IF self.size = 0 THEN BEGIN
    LLGetFirst := NIL;
  END ELSE BEGIN
    LLGetFirst := self.header.next.element;
  END;
END;

FUNCTION LLGetLast (self: LinkedList): HasEquals;
BEGIN
  IF self.size = 0 THEN BEGIN
    LLGetLast := NIL;
  END ELSE BEGIN
    LLGetLast := self.header.previous.element;
  END;
END;

FUNCTION LLRemoveFirst (self: LinkedList): HasEquals;
BEGIN
  LLRemoveFirst := self.removeEntry(self.header.next);
END;

FUNCTION LLRemoveLast (self: LinkedList): HasEquals;
BEGIN
  LLRemoveLast := self.removeEntry(self.header.previous);
END;

PROCEDURE LLAddFirst (self: LinkedList; e: HasEquals);
BEGIN
  self.addBeforeEntry(e, self.header.next);
END;

PROCEDURE LLAddLast (self: LinkedList; e: HasEquals);
BEGIN
  self.addBeforeEntry(e, self.header);
END;

FUNCTION LLContains (self: LinkedList; e: HasEquals): Boolean;
BEGIN
  LLContains := (self.indexOf(e) <> -1);
END;

FUNCTION LLSize (self: LinkedList): Integer;
BEGIN
  LLSize := self.mySize;
END;

FUNCTION LLAdd (self: LinkedList; e: HasEquals): Boolean;
BEGIN
  self.addBeforeEntry(e, self.header);
  LLAdd := true;
END;

FUNCTION LLRemove (self: LinkedList; o: HasEquals): Boolean;
VAR
  e        : LLEntry;
  searching: Boolean;
  oo       : HasEquals;
BEGIN
  LLRemove := false;
  searching := true;
  e := self.header.next;
  WHILE searching DO BEGIN
    IF e <> self.header THEN BEGIN
      IF o.equals(e.element) THEN BEGIN
        oo := self.removeEntry(e);
        LLRemove := true;
        searching := false;
      END;
    END ELSE BEGIN
      searching := false;
    END;
  END;
END;

FUNCTION LLAddAll (self: LinkedList; other: Collection): Boolean;
BEGIN
  LLAddAll := self.addAllIth(self.size, other);
END;

FUNCTION LLAddAllIth (self: LinkedList; index: Integer; other: Collection): Boolean;
VAR
  it    : Iterator;
  succ  : LLEntry;
  pred  : LLEntry;
  o     : HasEquals;
  newEnt: LLentry;
BEGIN
  LLAddAllIth := false;
  IF (index >= 0) AND (index <= self.size) THEN BEGIN
    it := other.getIterator;
    IF index = self.size THEN BEGIN
      succ := self.header;
    END ELSE BEGIN
      succ := self.entryIth(index);
    END;
    pred := succ.previous;
    WHILE it.hasNext DO BEGIN
      o := NARROW(it.next, HasEquals);
      newEnt := NEW(LLEntry).init(o, succ, pred);
      pred.next := newEnt;
      pred := newEnt;
      self.mySize := self.mySize + 1;
    END;
    succ.previous := pred;
    LLAddAllIth := true;
  END;
END;

PROCEDURE LLClear (self: LinkedList);
VAR e, next: LLEntry;
BEGIN
  e := self.header.next;
  WHILE e <> self.header DO BEGIN
    next := e.next;
    e.next := NIL;
    e.previous := NIL;
    e := next;
  END;
  self.header.next     := self.header;
  self.header.previous := self.header;
  self.mySize := 0;
END;

FUNCTION LLGetIth (self: LinkedList; index: Integer): HasEquals;
BEGIN
  LLGetIth := self.entryIth(index).element;
END;

FUNCTION LLSetIth (self: LinkedList; index: Integer; element: HasEquals): HasEquals;
VAR e: LLEntry;
BEGIN
  LLSetIth := NIL;
  e := self.entryIth(index);
  IF e <> NIL THEN BEGIN
    LLSetIth := e.element;
    e.element := element;
  END;
END;

FUNCTION LLAddIth (self: LinkedList; index: Integer; element: HasEquals): Boolean;
VAR e: LLEntry;
BEGIN
  IF index = self.size THEN BEGIN
    e := self.header;
  END ELSE BEGIN
    e := self.entryIth(index);
  END;
  self.addBeforeEntry(element, e);
  LLAddIth := true;
END;

FUNCTION LLRemoveIth (self: LinkedList; index: Integer): Boolean;
VAR oo: HasEquals;
BEGIN
  oo := self.removeEntry(self.entryIth(index));
  LLRemoveIth := true;
END;

FUNCTION LLEntryIth (self: LinkedList; index: Integer): LLEntry;
VAR e: LLEntry;
BEGIN
  LLEntryIth := NIL;
  IF (index >= 0) AND (index < self.size) THEN BEGIN
    e := self.header.next;
    WHILE index > 0 DO BEGIN
      e := e.next;
      index := index - 1;
    END;
    LLEntryIth := e;
  END;
END;

FUNCTION LLIndexOf (self: LinkedList; o: HasEquals): Integer;
VAR
  index    : Integer;
  e        : LLEntry;
  searching: Boolean;
BEGIN
  LLIndexOf := -1;
  index := 0;
  e := self.header.next;
  searching := true;
  WHILE searching DO BEGIN
    IF e <> self.header THEN BEGIN
      IF o.equals(e.element) THEN BEGIN
        LLIndexOf := index;
        searching := false;
      END ELSE BEGIN
        index := index + 1;
        e := e.next;
      END;
    END ELSE BEGIN
      searching := false;
    END;
  END;
END;

FUNCTION LLLastIndexOf (self: LinkedList; o: hasEquals): Integer;
VAR
  index    : Integer;
  e        : LLEntry;
  searching: Boolean;
BEGIN
  LLLastIndexOf := -1;
  index := self.size;
  e := self.header.previous;
  searching := true;
  WHILE searching DO BEGIN
    IF e <> self.header THEN BEGIN
      index := index - 1;
      IF o.equals(e.element) THEN BEGIN
        LLLastIndexOf := index;
        searching := false;
      END ELSE BEGIN
        e := e.previous;
      END;
    END ELSE BEGIN
      searching := false;
    END;
  END;
END;

PROCEDURE LLAddBeforeEntry (self: LinkedList; e: HasEquals; entry: LLEntry);
VAR newEnt: LLEntry;
BEGIN
  newEnt := NEW(LLEntry).init(e, entry, entry.previous);
  newEnt.previous.next := newEnt;
  newEnt.next.previous := newEnt;
  self.mySize := self.mySize + 1;
END;

FUNCTION LLRemoveEntry (self: LinkedList; e: LLEntry): HasEquals;
BEGIN
  LLRemoveEntry := NIL;
  IF (e <> self.header) AND (e <> NIL) THEN BEGIN
    LLRemoveEntry := e.element;
    e.previous.next := e.next;
    e.next.previous := e.previous;
    e.previous := NIL;
    e.next     := NIL;
    e.element  := NIL;
    self.mySize := self.mySize - 1;
  END;
END;

FUNCTION LLGetListIteratorIth (self: LinkedList; index: Integer): ListIterator;
BEGIN
  LLGetListIteratorIth := NEW(LLLstItr).init(self, index);
END;


FUNCTION LLLIInit (self: LLLstItr; lst: LinkedList; index: Integer): LLLstItr;
BEGIN
  self.lastReturned := lst.header;
  self.theList := lst;
  IF (index >= 0) AND (index <= lst.size) THEN BEGIN
    self.nextEnt := self.theList.header.next;
    self.nextIdx := 0;
    WHILE index > 0 DO BEGIN
      self.nextEnt := self.nextEnt.next;
      self.nextIdx := self.nextIdx + 1;
      index := index - 1;
    END;
  END;
END;

FUNCTION LLLIHasNext (self: LLLstItr): Boolean;
BEGIN
  LLLIHasNext := (self.nextIdx <> self.theList.size);
END;

FUNCTION LLLINext (self: LLLstItr): HasEquals;
BEGIN
  LLLINext := NIL;
  IF self.nextIdx <> self.theList.size THEN BEGIN
    self.lastReturned := self.nextEnt;
    self.nextEnt      := self.nextEnt.next;
    self.nextIdx      := self.nextIdx + 1;
    LLLINext          := self.lastReturned.element;
  END;
END;

FUNCTION LLLINextIndex (self: LLLstItr): Integer;
BEGIN
  LLLINextIndex := self.nextIdx;
END;

FUNCTION LLLIHasPrevious (self: LLLstItr): Boolean;
BEGIN
  LLLIHasPrevious := (self.nextIdx <> 0);
END;

FUNCTION LLLIPrevious (self: LLLstItr): HasEquals;
BEGIN
  LLLIPrevious := NIL;
  IF self.nextIdx <> 0 THEN BEGIN
    self.lastReturned := self.nextEnt.previous;
    self.nextEnt      := self.nextEnt.previous;
    self.nextIdx      := self.nextIdx - 1;
    LLLIPrevious      := self.lastReturned.element;
  END;
END;

FUNCTION LLLIPreviousIndex (self: LLLstItr): Integer;
BEGIN
  LLLIPreviousIndex := self.nextIdx - 1;
END;

PROCEDURE LLLIRemove (self: LLLstItr);
VAR
  lastNext: LLEntry;
  oo      : HasEquals;
BEGIN
  lastNext := self.lastReturned.next;
  oo := self.theList.removeEntry(self.lastReturned);
  IF self.nextEnt = self.lastReturned THEN BEGIN
    self.nextEnt := lastNext;
  END ELSE BEGIN
    self.nextIdx := self.nextIdx - 1;
  END;
  self.lastReturned := self.theList.header;
END;

PROCEDURE LLLISet (self: LLLstItr; e: HasEquals);
BEGIN
  IF self.lastReturned <> self.theList.header THEN BEGIN
    self.lastReturned.element := e;
  END;
END;

PROCEDURE LLLIAdd (self: LLLstItr; e: HasEquals);
BEGIN
  self.lastReturned := self.theList.header;
  self.theList.addBeforeEntry(e, self.nextEnt);
  self.nextIdx := self.nextIdx + 1;
END;


BEGIN
END.
