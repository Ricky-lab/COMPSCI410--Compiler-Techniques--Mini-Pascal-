PROGRAM tester;
{ A meaningless program to test features of PASCAL subset }
CONST
  ArraySize = 10;
TYPE
  RecordType = RECORD
    val : INTEGER;
    use : BOOLEAN;
  END;
  Ptr  = ^Node;
  Node = RECORD 
    val  : INTEGER;
    prev,
    next : Ptr;
  END;
  Week = 0 .. 6;
CONST
  Sunday    = 0;
  Monday    = 1;
  Tuesday   = 2;
  Wednesday = 3;
  Thursday  = 4;
  Friday    = 5;
  Saturday  = 6;
VAR
  day,size : INTEGER;
  i, j : 1..ArraySize;
  list : ARRAY [1..ArraySize] OF RecordType;
  tarr : ARRAY [1..ArraySize,1..5] OF INTEGER;
  weekday : Week;
FUNCTION double (VAR x:INTEGER):INTEGER ;
VAR i,j,k : INTEGER;
    b     : Boolean;
  BEGIN
    i:=2*ArraySize;
    j:=-ArraySize;
    k:=(i+j) DIV ArraySize;
    b:=(i>j);
    double:=x*i;
  END; { double }               
BEGIN
  FOR i:=1 TO ArraySize DO
      FOR j:=1 TO 5 DO
          tarr[i,j] := 0 ;
  
  WRITE ('Enter integer between 1 and 10: ');
  READLN (size);
  IF (size<1) OR (size>10) THEN BEGIN
      WRITELN('Invalid');
      size := 5;
      END
  ELSE
      WRITELN('Valid');
  WRITE ('Enter date between Sunday and Saturday[0..6]: ');
  READLN (day);
  case day of
     0 : BEGIN weekday := Sunday; WRITELN('Sunday'); END;
     1 : BEGIN weekday := Monday; WRITELN('Monday'); END;
     2 : BEGIN weekday := Tuesday; WRITELN('Tuesday'); END;
     3 : BEGIN weekday := Wednesday; WRITELN('Wednesday'); END;
     4 : BEGIN weekday := Thursday; WRITELN('Thursday'); END;
     5 : BEGIN weekday := Friday; WRITELN('Friday'); END;
     6 : BEGIN weekday := Saturday; WRITELN('Saturday'); END;
  END; { case }
  case weekday of
     Monday,    
     Wednesday,
     Friday   : WRITELN('Work hours : 8:00am - 5:00pm');
     Tuesday,
     Thursday : WRITELN('Work hours : 8:00am - 1:00pm'); 
     Saturday,
     Sunday   : WRITELN('No work hours');
  END; { case }
  FOR i := 1 TO size DO
  BEGIN
    list[i].val := i;
    list[i].use := TRUE;
  END;
  i := 1;
  REPEAT
    list[i].val := 0;
    list[i].use := FALSE;
    i := i + 1;
  UNTIL i >= 10;
  i := 1;
  WHILE (list[i].val <> 0) DO
  BEGIN
    IF (i=1) THEN
      WRITELN(1) { i.e. do nothing }
    ELSE IF (i<5) THEN
     BEGIN
      list[i].val := list[i].val + double(list[i].val);
      list[i].use := NOT list[i].use;
      WRITELN(i,'<5');
     END
    ELSE
      WRITELN(i,'>=5');
    i := i + 1;
  END; { WHILE }
END.
