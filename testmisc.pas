PROGRAM phase4test;

CONST
  firstval = 1;
  lastval  = 5;
  exitval  = 3;

VAR
  i,j,k : INTEGER;
  l     : BOOLEAN;

{ Passing integers to procedure  }

VAR x, y, z : INTEGER;  
  
PROCEDURE passints (x, y, z : INTEGER);
  BEGIN
     WRITELN('Values of arguments x, y, z within passints: ',x,' ',y,' ',z);
  END;

{ Simple recursion }
FUNCTION factorial (n : INTEGER) : integer ;
BEGIN
    IF n <= 2 THEN
      factorial := n
    ELSE
      factorial := n * factorial (n - 1);
END;


{ Boolean valued procedure that tests whether an integer is prime }
FUNCTION prime(n : integer) : BOOLEAN ;
VAR
    RESULT,LOOP : BOOLEAN;
    i : INTEGER;
BEGIN
   RESULT := TRUE ;
   IF n >= 2 THEN
   BEGIN 
    IF n MOD 2 <> 0 THEN 
    BEGIN
     i := 3;
     LOOP := TRUE;
     WHILE LOOP DO
     BEGIN
      IF i*i > n THEN
       BEGIN
        RESULT := TRUE;
        LOOP := FALSE ;
       END
      ELSE
      IF n MOD i = 0 THEN
       BEGIN
        RESULT := FALSE;
        LOOP := FALSE ;
       END
      ELSE i:=i+1 ;
     END;
    END
    ELSE RESULT := FALSE ;
   END
   ELSE RESULT := FALSE ; 
   prime := RESULT ;
END;

{ Test for parameter modes }
PROCEDURE simple (a : INTEGER; VAR b: INTEGER; c: BOOLEAN); 
 BEGIN
     writeln('enter procedure a=',a,', b=',b,', c=',c);
     if c then b := a + 6 
     else      b := a - 6 ;
     writeln('exit procedure a=',a,', b=',b,', c=',c);
 END;


BEGIN

  WRITELN( 'IF tests' );
  WRITE( 'Enter an integer>' );
  READLN( i );
  IF i > 0 THEN
    WRITELN(i,' is a positive integer')
  ELSE IF i < 0 THEN
    WRITELN(i,' is a negative integer')
  ELSE
    WRITELN(i,' is zero');

  WRITELN( 'Nested IFs' );
  IF i > 0 THEN
    IF i MOD 2 = 0 THEN
      WRITELN( i, ' is a positive, even integer' )
    ELSE
      WRITELN( i, ' is a positive, odd integer' )
  ELSE IF i < 0 THEN
    IF i MOD 2 = 0 THEN
      WRITELN( i, ' is a negative, even integer' )
    ELSE
      WRITELN( i, ' is a negative, odd integer' )
  ELSE
    WRITELN( i, ' is zero' );

  WRITELN;
  WRITELN( 'WHILE tests' );
  WRITELN( 'WHILE i <= 5' );
  i := 1;
  WHILE i <= 5 DO
  BEGIN
    WRITELN( 'i = ', i );
    i := i + 1
  END;

  WRITELN( 'WHILE i <= 1' );
  i := 1;
  WHILE i <= 1 DO
  BEGIN
    WRITELN( 'i = ', i );
    i := i + 1
  END;
  WRITELN( 'WHILE i <= 0' );
  i := 1;
  WHILE i <= 0 DO
  BEGIN
    WRITELN( 'i = ', i );
    i := i + 1
  END;

  i := 1;
  WRITELN( 'Nested WHILEs' );
  WHILE i <= 3 DO
  BEGIN
    j := 1;
    WHILE j <= 3 DO
    BEGIN
      WRITELN( i,' + ',j,' = ', i + j );
      j := j + 1
    END;
    i := i + 1;
  END;

  WRITELN;
  WRITELN( 'REPEAT tests' );
  WRITELN( 'REPEAT UNTIL i > 5' );
  i := 1;
  REPEAT
    WRITELN( 'i = ', i );
    i := i + 1
  UNTIL i > 5;
  WRITELN( 'REPEAT UNTIL i > 1' );
  i := 1;
  REPEAT
    WRITELN( 'i = ', i );
    i := i + 1
  UNTIL i > 1;
  WRITELN( 'REPEAT UNTIL i > 0' );
  i := 1;
  REPEAT
    WRITELN( 'i = ', i );
    i := i + 1
  UNTIL i > 0;
  WRITELN( 'Nested REPEATs' );
  i := 1;
  REPEAT
    j := 1;
    REPEAT
      WRITELN( i, ' + ',j,' = ', i + j );
      j := j + 1
    UNTIL j > 3;
    i := i + 1
  UNTIL i > 3;

  j:=2;
  k:=6;
  WRITELN;
  WRITELN( 'FOR tests' );
  WRITELN;
  WRITELN( ' 1 TO 5' );
  FOR i := 1 TO 5 DO
    WRITE( ' ', i );
  WRITELN;
  WRITELN( ' 1 TO j*5=',j*5 );
  FOR i := 1 TO j*5 DO
    WRITE( ' ', i );
  WRITELN;
  WRITELN( ' 1 TO j=',j );
  FOR i := 1 TO j DO
    WRITE( ' ', i );  
  WRITELN;
  WRITELN( 'K=',k,' DOWNTO j=',j);
  FOR i := K DOWNTO j DO
    WRITE( ' ', i );  
  WRITELN;
  WRITELN( ' 5 DOWNTO 1' );
  FOR i := 5 DOWNTO 1 DO
    WRITE( ' ', i );
  WRITELN;
  WRITELN( ' 1 TO 1' );
  FOR i := 1 TO 1 DO
    WRITE( ' ', i );
  WRITELN;
  WRITELN( ' 1 DOWNTO 0' );
  FOR i := 1 DOWNTO 0 DO
    WRITE( ' ', i );
  WRITELN;

  WRITELN;
  WRITELN( 'Nested FORs' );
  FOR i := 1 TO 3 DO
    FOR j := 1 TO 3 DO
      WRITELN( i,' + ',j,' = ', i + j );


{ test passints }
    WRITELN; WRITELN;
    WRITELN( 'Pass by value test' );
    x := 100;
    y := 200;
    z := 300;
    passints (0, 1, 2);
    WRITELN ('Values of x, y, z after passints: ', x, ' ', y, ' ', z);

{ test prime }
    IF prime(19) AND prime(31) AND NOT prime(63) THEN
      WRITELN ('prime passes tests');
  
{ test factorial }
    x:=factorial(8);
    writeln('Recursion = ',x);
    IF (factorial(8) = 40320) THEN
      WRITELN( 'Recursion test passes' )
    ELSE
      WRITELN( 'Recursion test fails' );

{ test parameter mode }
    i := 3;
    j := 4;
    l := TRUE;
        
    simple(i,j,l);     
 
    WRITELN('i=',i,', j=',j,', l=',l);

END.
