program test;

const n=10;

function incr (x: integer): integer;
begin
  incr := x + 1;
end;

var i:integer;
    w1,w2,w3 : integer;
begin i:=0; 
      readln(w1,w2,w3);
      repeat i:=i+1;
             w1:=w1*1;
             w2:=w2*2;
             w3:=w3*3;
             writeln('this is a test ', i, ' :');
             writeln(' ', w3, ' ', w2, ' ', w1)
      Until i=incr(n)
end.
