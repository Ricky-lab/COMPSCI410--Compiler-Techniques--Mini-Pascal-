PROGRAM testregs2;

TYPE
  RR = RECORD x, y : INTEGER END;
  Range1 = 1 .. 10;
  Range2 = 1 .. 2;
VAR
  a: ARRAY [ Range1 ] OF RR;
  b: ARRAY [ Range2, Range2 ] OF RR;
  r: RR;
  i, j: Range1;
  m, n: Range2;
FUNCTION f1: INTEGER;
BEGIN
  f1 := 1;
END; { f1 }

BEGIN
  i := f1;
  j := f1;
  m := f1;
  n := f1;
  a[i].x := a[j].x;
  a[i].x := a[j].y;
  a[i].y := a[j].x;
  a[i].y := a[j].y;
  b[m][n].x := b[n][m].x;
  b[m][n].x := b[n][m].y;
  b[m][n].y := b[n][m].x;
  b[m][n].y := b[n][m].y;
END.
