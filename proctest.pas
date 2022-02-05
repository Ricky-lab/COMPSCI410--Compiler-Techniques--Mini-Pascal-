program procfunctest;

type rng = 1 .. 10;

procedure p0;
begin
end;

procedure p1 (x: integer);
begin
end;

procedure p2 (x: integer; y: boolean);
begin
end;

procedure p3 (x: integer; r: rng; VAR xx: integer; VAR rr: rng);
begin
end;

function f0: integer;
begin
  f0 := 0;
end;

function f1 (x: integer): integer;
begin
  f1 := x;
end;

function f2 (x: integer; y: boolean): boolean;
begin
  f2 := y;
end;

function f3: boolean;
  procedure pp;
    procedure qq;
    begin
      pp := 5;
    end;
  begin
    f3 := false;
    pp := 17;
  end;
  function ff: boolean;
  begin
    f3 := true;
    ff := false; (* ok *)
  end;
begin
  f3 := true; (* ok *)
  f3 := 3;
end;


type
  pint = ^integer;

var
  r: rng;
  x: integer;
  y: boolean;
  p: ^integer;
begin
  p0; (* ok *)
  p1;
  p0(x);
  p1(x); (* ok *)
  p1(y);
  p1(x, y);
  p2(x);
  p2(x, y); (* ok *)
  p2(y, x);
  p3(x, r, x, r); (* ok *)
  p3(r, x, x, r); (* ok *)
  p3(x, r, r, x);
  p3(50, 50, x, r); (* ok *)
  p3(50, 50, 50, 50);
  x := f0; (* ok *)
  x := f1;
  x := f0(x);
  x := f1(x); (* ok *)
  x := f1(y);
  y := f2(x);
  x := f2(x, y);
  y := f2(x, y); (* ok *)
  y := f2(y, x);
  readln; (* ok *)
  readln (3);
  readln (x); (* ok *)
  readln (x, 3);
  write;
  writeln; (* ok *)
  write (x, 3); (* ok *)
  writeln (x, 3); (* ok *)
  write (p);
  new(p); (* ok *)
  new(x);
  new(3);
  new;
  new(p, x);
  p := new(pint);
  x := new(pint);
  y := new(boolean);
end.
