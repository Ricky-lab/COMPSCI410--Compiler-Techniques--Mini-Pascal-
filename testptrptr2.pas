program testptrptr2;

type pi = ^integer;
    ppi = ^pi;
var i: integer; p, q: pi; pp, qq: ppi;

procedure show;
begin
  write ('  ');
  if pp = NIL then
    write ('pp = NIL')
  else if pp^ = NIL then
    write ('pp^ = NIL')
  else
    write ('pp^^ = ', pp^^);
  write ('; ');
  if qq = NIL then
    write ('qq = NIL')
  else if qq^ = NIL then
    write ('qq^ = NIL')
  else
    write ('qq^^ = ', qq^^);
  write ('; ');
  if p = NIL then
    write ('p = NIL')
  else
    write ('p^ = ', p^);
  write ('; ');
  if q = NIL then
    write ('q = NIL')
  else
    write ('q^ = ', q^);
  writeln;
end;

begin
  pp := NIL;
  qq := NIL;
  p := NIL;
  q := NIL;
  writeln ('p, q, pp, qq are NIL:');
  show;

  p := q;
  writeln ('after p := q:');
  show;

  new (q);
  q^ := 0;
  writeln ('after new (q) and q^ := 0:');
  show;

  p := q;
  writeln ('after p := q:');
  show;

  p^ := 1;
  writeln ('after p^ := 1:');
  show;

  new (q);
  q^ := 0;
  writeln ('after new (q) and q^ := 0:');
  show;

  i := 2;
  p^ := i;
  writeln ('after i := 2, p^ := i:');
  show;

  q^ := 3;
  writeln ('after q^ := 3:');
  show;

  p^ := q^;
  writeln ('after p^ := q^:');
  show;

  new (pp);
  pp^ := NIL;
  writeln ('after new (pp), pp^ := NIL:');
  show;

  new (qq);
  qq^ := NIL;
  writeln ('after new (qq), qq^ := NIL:');
  show;

  pp^ := p;
  writeln ('after pp^ := p:');
  show;

  qq^ := q;
  writeln ('after qq^ := q:');
  show;

  pp^^ := 4;
  writeln ('after pp^^ := 4:');
  show;

  qq := pp;
  writeln ('after qq := pp:');
  show;

  qq^^ := 5;
  writeln ('after qq^^ := 5:');
  show;
end.
