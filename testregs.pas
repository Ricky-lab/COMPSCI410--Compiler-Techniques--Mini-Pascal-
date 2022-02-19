PROGRAM testregs;

TYPE
  RR = RECORD x, y: INTEGER END;
VAR
  a: ARRAY [ 1 .. 10 ] OF RR;
  b: ARRAY [ 1 .. 2, 1 .. 2 ] OF RR;
  r: RR;
  i, j: INTEGER;

FUNCTION f1: INTEGER;
BEGIN
  f1 := 1;
END;

BEGIN
  i := f1;
  j := f1;
  a[3].y := 1;
  a[i].y := 2;
  r.y := 3;
  b[2][2].y := 5;
  b[i][2].y := 6;
  b[2][i].y := 7;
  b[i][j].y := 8;
  b[2][2].x := 5;
  b[i][2].x := 6;
  b[2][i].x := 7;
  b[i][j].x := 8;
  i := i + j;
  i := 9;
  j := ((i + j) + (i + j)) + ((i + j) + (i + j));
  i := 8;
  i := (((i + j) + (i + j)) + ((i + j) + (i + j))) +
       (((i + j) + (i + j)) + ((i + j) + (i + j)));
  i := 7;
  j := a[((((i + j) + (i + j)) + ((i + j) + (i + j))) +
          (((i + j) + (i + j)) + ((i + j) + (i + j)))) MOD 10 + 1].x +
       a[((((i + j) + (i + j)) + ((i + j) + (i + j))) +
          (((i + j) + (i + j)) + ((i + j) + (i + j)))) MOD 10 + 1].y;
  i := 6;
  a[((((i + j) + (i + j)) + ((i + j) + (i + j))) +
     (((i + j) + (i + j)) + ((i + j) + (i + j)))) MOD 10 + 1].x :=
       a[((((i + j) + (i + j)) + ((i + j) + (i + j))) +
          (((i + j) + (i + j)) + ((i + j) + (i + j)))) MOD 10 + 1].y;
  i := 5;
  a[(((i + j) + (i + j)) + ((i + j) + (i + j))) MOD 10 + 1].y :=
    a[(((i + j) + (i + j)) + ((i + j) + (i + j))) MOD 10 + 1].y + 1;
END.
