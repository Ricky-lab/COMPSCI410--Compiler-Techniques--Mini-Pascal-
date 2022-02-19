PROGRAM phase5test;

PROCEDURE printscores(a,b,c:INTEGER);
BEGIN
  IF a = b THEN
    WRITELN ('\t:-) GOOD : got full ',a,' pt(s).       TOTAL SCORE: ',c)
  ELSE
    WRITELN ('\t:-( SORRY: missed ',a,' pt(s).         TOTAL SCORE: ',c);

END;
VAR
  score : INTEGER;
  localscore :INTEGER;


{ 1. Simple static chain checks:   (10 pts)
        a. using variables from outer levels
	b. using arguments from outer levels
	c. calling one level within.

   2. Twisted static chain: testing the two types of calling : (20 pts)
		b. same level
		c. levels enclosing current lexical level.
}


FUNCTION scope1 (l,m,n,o:INTEGER):INTEGER;
  VAR
     a :INTEGER;
  PROCEDURE scope2 (m,n,o:INTEGER);
    VAR
      b :INTEGER;
    PROCEDURE scope3 (n,o:INTEGER);
      VAR
        c :INTEGER;
      PROCEDURE scope4 (o:INTEGER);
        VAR
          d :INTEGER;
      BEGIN
        d:=l*o;
        IF (b = 2*a) AND (c = 2*b) AND (d = 2*c) THEN
          localscore := 10
        ELSE
          localscore := 0;
        WRITELN('\ta: ',a,'  b: ',b,'  c: ',c,'  d: ',d);
      END;

    BEGIN
      c := l*o;
      scope4(2*o);
    END;
  BEGIN
    b := l*o;
    scope3(n,2*o);
  END;
BEGIN
  a:= l*o;
  scope2(m,n,2*o);
  scope1 := localscore;
END; { scope1 }

FUNCTION twistedchain(l,m:INTEGER): INTEGER ;
  VAR store,count,localscore : INTEGER;

  PROCEDURE a1(i,j:INTEGER);
    VAR b :INTEGER;
    PROCEDURE a2 (k,l:INTEGER);
    VAR a:INTEGER;
    BEGIN
      a := i;  { checking to see if the arguments from outer scope work
		  right }
      b:= l+1 ; { checking to see if variables from the outer scope work }
      WRITELN('a : ',a,', l : ',l);
      WRITELN('k : ',k,', b : ',b);
      IF (a = store) AND (k = store-1) AND (l = count) AND (b = count+1)
      THEN
      BEGIN
           localscore := localscore+2;
	   store := store - 2;
	   count := count + 2;
      END;
      IF (i <> 0) AND (k <> 0) THEN
         a1(k-1,l+1);
    END;
  BEGIN
    a2(i-1,j+1);
  END;

  VAR outerk, outerl : INTEGER;

  PROCEDURE b1 (i,j:INTEGER);
    PROCEDURE b2a(k,l:INTEGER);
    BEGIN
      WRITELN('k : ',k,', l: ',l);
      outerk := k; outerl := l;
      IF (outerk = store) AND (outerl = count)
      THEN
      BEGIN
           localscore := localscore+1;
	   store := store - 1;
	   count := count + 1;
      END;
      IF (k <> 0) THEN
          b1(k-1, l+1);
    END;
    PROCEDURE b2b(k,l:INTEGER);
    BEGIN
      b2a(k,l);
    END;
  BEGIN
    b2b(i,j);
  END;

BEGIN
  count:=1 ;
  localscore := 0;
  WRITELN('TEST a : 10 pts');
  store := l;
  a1(l,m);
  store := l-2;
  count := 0;
  outerk:= l-2;
  outerl:= 0;
  WRITELN('TEST b : 10 pts');
  b1(l-2,m);
  twistedchain := localscore;
END;


BEGIN
  WRITELN('TESTING STATIC CHAINS:');
  score := score + scope1(2,3,4,5);
  WRITELN('TEST : simple static chain');
  printscores(10,localscore, score);

  WRITELN('TEST : twisted static chain');
  localscore := 2*twistedchain(5,0);
  score := score + localscore;
  printscores(20,localscore, score);
END.
