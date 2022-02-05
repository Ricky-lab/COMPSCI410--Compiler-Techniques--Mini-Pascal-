program bigtest;

const
  c1 = 'string constant';
  c2 = 100;

type
  t1 = 100..200;
  t2 = 'string1'..'string2';

var
  v1, v2 : array[10..20,20..30,30..40] of integer;
  v3,v4 : array[10..20] of array[20..30,30..40] of integer;
  v5 : array[10..20] of array [20..30] of array[30..40] of integer;

procedure p1;
  begin
  end;

function f1 : integer;
  begin;
  end;

const
  c1 = +100;
  c2 = -c1;
  c3 = +100;
  c4 = -c3;

var
  a, b, c, d, e, f, g, h, i : 
    array[1..100] of 
      record
        a,b : array[50..60] of integer;
        c,d,e,f : array[100..110] of array[5..10,80..90] of
          record
          end;
      end;

type 
  ptr = ^sometype;

function f1(a:integer;
            var a,b,c:integer;
            d,e,f:integer;
            var g,h,i:integer):sometype;
  type
    a=record a:integer;b:integer;c:integer;end;
    b=record a:integer;b:integer;c:integer end;
  const
    constant = 1000;
  function infunc:type1;
    begin
    end;
  var x,y,z:^integer;
  const c2 = 30000; c4 = -2; c5 = 'this is a test';
  procedure inproc( var a:integer; var b:integer; c:integer;
             d:integer; var e:integer );
    begin
    end;
  begin
  end;

type 
  x = record
        y: record
          z : record
            a: array [1..2,3..4,5..6,7..8,9..10] of integer;
          end;
        end;
      end;

procedure foo;
  begin
  end;

type a = integer;

var 
  a : integer;

function func1
  (var a,b,c,d,e,f,g,h,i,j,k,l,m,n,o,p,q,r,s,t,u,v,w,x,y,z:type1):type2;
  begin
  end;

procedure proc1;
  begin
  end;

begin

  if a = b then
    if c = d then
      if e = f then
        g := h
      else
        g := 2
    else
      g := 3;

  a := 0;
  while (a <= 100) or (b > 20) or (g = 12) do
    begin
    end;
  repeat
    begin
    end;
  until (a <= 100) or (b > 20) or (g = 12);

  A := 10;
  B := 20;
  C := A * B;
  (* C := nil; *)
  A := A + 10 * B - C;
  A := - A + 10 * B - C;

  A := 0;
  B := 2;
  REPEAT
    A := A + 1;
    B := B * 2;
  UNTIL A = 10;

  FOR A := (10 + 20) TO (100 - 10) DO
    BEGIN
    B := B + 1;
    C := C + 2;
    END;
  
  FOR A := (10 + 20) DOWNTO (100 - 10) DO
    BEGIN
    B := B + 1;
    C := C + 2;
    END;
 
  CASE A OF
    1,2,3: B := 10;
    4,5,6: B := 20
  END;

  CASE A OF
    1,2,3: B := 10;
    4,5,6: B := 20;
  END;

  A := A.B.C.D;
  A := A.B.C.D^;
  A := A;

  B := A( 10, A + C, 'this is a test' );

  A := B[1];
  A := B[1,2,3,4,5];
 
end.
