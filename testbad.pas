PROGRAM tester;
{ A meaningless program to test all the features of PASCAL subset }
CONST
  ArraySize = 10;
  Tmp       = -ArraySize ;
  ValT      = True ;
  ValF      = NotValT;
  ValTT     = ValT ;
  TmpErr    = -Tmp ;
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
  Week = (Monday,Tuesday,Wednesday,Thursday,Friday,Saturday,Sunday);
VAR
  Weekdays : Monday..Friday;
  Weekend  : Friday..Sunday;
  day,size : INTEGER;
  i, j : 1..ArraySize;
  list : ARRAY [1..ArraySize] OF RecordType;
  weekday : Week;
FUNCTION double (VAR x:INTEGER):INTEGER ;
VAR i,j,T : INTEGER;
    v : ARRAY [1..5,1..ArraySize] OF INTEGER;
  BEGIN
    T:=0;
    FOR i:=1 TO 5 DO
        FOR j:=1 TO ArraySize DO
        BEGIN
           v[i,j] := i+j;
           T:=T+v[i,j];  
        END;
    double:=T;
  END { double }               
BEGIN
  Weekdays := Friday ;
  Weekend  := Monday ;
  Weekdays := Weekend;
  READLN('Enter integer between 1 and 10>',size);
  IF (size<1) OR (size>10) THEN
      WRITELN('Invalid')
  ELSE
      WRITELN('Valid');
  READLN('Enter date between Sunday and Saturday[0..6] :',day);
  case day of
     'abc' : BEGIN weekday := Sunday; WRITELN('Sunday'); END;
     TRUE : BEGIN weekday := Monday; WRITELN('Monday'); END;
     size : BEGIN weekday := Tuesday; WRITELN('Tuesday'); END;
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
     Wednesday,
     Thursday : WRITELN('Work hours : 8:00am - 1:00pm'); 
     Saturday,
     Sunday   : WRITELN('No work hours');
  END; { case }
  FOR i := 1 TO size DO
  BEGIN
    list[i].val := TRUE;
    list[i].use := i;
  END;
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
      list[i].val := list[i].val + double(list[i].val * 2);
      WRITELN(i,'<5');
     END
    ELSE
      WRITELN(i,'>=5');
  END; { WHILE }
  size := ValF + 2;
END.
