0
1
00
10
5000000000 { too big }
2000000000 { ok }
0#0 { bad base }
1#0 { bad base }
37#0 { bad base }
2#101
2#102 { bad digit }
10#10A { bad digit }
16#1FF { ok }
16#G { bad digit }
35#Y
35#y
35#Z { bad digit }
2_000_000_000
2#1000_0001
16#dead_beef { too big }
16#4ead_beef
2__0 { bad }
20_  { bad }
2#1__0 { bad }
2#10_ { bad }
