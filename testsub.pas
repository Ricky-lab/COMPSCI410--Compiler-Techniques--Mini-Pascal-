PROGRAM testsub;

TYPE
  at = array [ 1 .. 10 ] of integer;
  aat = array [ 1 .. 10, 1 .. 10 ] of integer;
  bt = array [ 0 .. 9 , 0 ..15 ] of integer;

VAR
  a : at;
  aa: aat;
  i, j: integer;
  ii, jj: 1 .. 10;
  ll: 0 .. 9;
  hh: 2 .. 11;
  b: bt;

FUNCTION f1: integer;
BEGIN
  f1 := 1;
END; { f1 }

BEGIN
  IF f1 = 0 THEN BEGIN
    i := a[0] + a[11];
    a[0] := j;
    a[11] := i;
    j := aa[0][i] + aa[i][0] + aa[11][i] + aa[i][11] +
         aa[0][11];
  END;
  j := f1;
  i := aa[j][1];
  i := f1;
  aa[i][1] := j;
  ii := f1;
  jj := f1;
  i := a[i] + a[ii] + aa[i][j] + aa[i][jj] + aa[ii][j] + aa[ii][jj];
  ii := jj;
  ii := i;
  IF f1 = 0 THEN BEGIN
    ii := 0;
    ii := 5;
    ii := 11;
  END;
  ll := f1;
  hh := f1 + 1;
  ii := ll;
  ii := hh;
  i := a[ll] + a[hh];
  i := b[0][ll] + b[ll][0] + b[ll][ll];
END.
