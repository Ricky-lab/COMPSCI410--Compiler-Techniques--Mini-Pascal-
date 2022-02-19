PROGRAM testcall;

TYPE R = RECORD a, b: INTEGER; END;
     PT = ^R;

VAR rr: R; ii: INTEGER; pp: PT;

PROCEDURE p1 (x: R; VAR y: R; i: INTEGER; VAR j: INTEGER;
              p: PT; VAR q: PT);
BEGIN
  y := x;
  x.a := y.a;
  i := j;
  j := i;
  i := p^.a;
  i := p^.b;
  i := q^.a;
  i := q^.b;
END;

FUNCTION f1 (x: R): R;
BEGIN
  f1 := x;
  f1 := rr;
END;

FUNCTION f2 (VAR i: INTEGER): INTEGER;
BEGIN
  f2 := i + 2;
END;

FUNCTION f3: PT;
BEGIN
  f3 := pp;
END;

VAR nesting: INTEGER;

PROCEDURE nest1;
  PROCEDURE nest2;
    PROCEDURE nest3;
    BEGIN
      nesting := nesting - 1;
      IF nesting > 0 THEN BEGIN
        nest3;
        nest2;
        nest1;
      END;
      nesting := nesting + 1;
    END;
  BEGIN
    nesting := nesting - 1;
    IF nesting > 0 THEN BEGIN
      nest3;
      nest2;
      nest1;
    END;
    nesting := nesting + 1;
  END;
BEGIN
  nesting := nesting - 1;
  IF nesting > 0 THEN BEGIN
    nest2;
    nest1;
  END;
  nesting := nesting + 1;
END;

BEGIN
  nesting := 10;
  new (pp);
  p1 (rr, rr, ii, ii, pp, pp);
  rr := f1 (rr);
  ii := f2 (ii);
  pp := f3;
  nest1;
END.
