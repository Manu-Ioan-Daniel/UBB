%apartine(L:lista,E:element,Rez:boolean)
%model de flux(i,i,o)
apartine([],_,false).
apartine([H|_],H,true):-!.
apartine([_|T],E,Rez):-apartine(T,E,Rez).
%diferenta(A:multime,B:multime,Rez:multime)
%model de flux(i,i,o)
diferenta([],_,[]).
diferenta([H|T],B,Rez):-
    apartine(B,H,Ap),
    Ap=false,
    diferenta(T,B,Rez2),
    Rez=[H|Rez2],!.
diferenta([_|T],B,Rez):-
    diferenta(T,B,Rez).
%adaugaPar(L:lista,Rez:lista)
adaugaPar([],[]).
adaugaPar([H|T],Rez):-
    0 is H mod 2,
    adaugaPar(T,Rez2),
    Rez=[H,1|Rez2],!.
adaugaPar([H|T],Rez):-
    adaugaPar(T,Rez2),
    Rez=[H|Rez2].
