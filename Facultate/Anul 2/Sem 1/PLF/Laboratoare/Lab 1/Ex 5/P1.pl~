%sterge(L:lista,E:element,Rez:lista)
%model de flux (i,i,o)
sterge(L,E,Rez):-L=[],Rez=[].
sterge(L,E,Rez):-L=[H|T],H=E,sterge(T,E,Rez).
sterge(L,E,Rez):-L=[H|T],H\=E,sterge(T,E,Rez2),Rez=[H|Rez2].
%nrAparitii(L:lista,E:element,Rez:intreg)
%model de flux(i,i,o)
nrAparitii([],E,0).
nrAparitii([H|T],E,Rez):-
    H=E,
    nrAparitii(T,E,Rez2),
    Rez is Rez2+1.
nrAparitii([H|T],E,Rez):-
    H\=E,
    nrAparitii(T,E,Rez).
%listaPerechi(L:lista,Rez:lista)
%model de flux(i,o)
listaPerechi([],[]).
listaPerechi(L,Rez):-
    L=[H|T],
    nrAparitii(L,H,NrAp),
    sterge(L,H,L2),
    listaPerechi(L2,Rez2),
    Rez=[[H,NrAp]|Rez2].
