program testptrptr;

type pi = ^integer;
    ppi = ^pi;
var p: pi; pp: ppi;
begin
  new (p);
  p^ := 3;
  new (pp);
  pp^ := p;
  writeln (pp^^ + 5);
end.
