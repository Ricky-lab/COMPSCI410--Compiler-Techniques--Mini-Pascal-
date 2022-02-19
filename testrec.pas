PROGRAM test;
TYPE
  p = ^q;
  q = ^r;
  r = ^p;

  x = ^z;
  y = ^x;
  z = ^y;

  t = ^t;

  a = ^b;
  b = ^c;
  c = ^d;
  d = ^c;
BEGIN
END.
