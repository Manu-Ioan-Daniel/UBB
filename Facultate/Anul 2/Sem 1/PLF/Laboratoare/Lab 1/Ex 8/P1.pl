esteMultime([]).
esteMultime([H|T]):-
    \+apartine(T,H),
    esteMultime(T).
apartine([H|_],H):-!.
apartine([_|T],E):-
    apartine(T,E).

elimina3(L,E,Rez):-
    eliminaN(L,E,3,Rez).
eliminaN([],_,_,[]):-!.
eliminaN([H|T],H,N,Rez):-
    N>0,
    N2 is N-1,
    eliminaN(T,H,N2,Rez),!.
eliminaN([H|T],E,N,[H|RezT]):-
    eliminaN(T,E,N,RezT).



