substituie([],_,_,[]).
substituie([H|T],H,L,[L|RezT]):-
    substituie(T,H,L,RezT),!.
substituie([H|T],E,L,[H|RezT]):-
    substituie(T,E,L,RezT).
eliminaN([],_,[]):-!.
eliminaN([_|T],1,RezT):-
    eliminaN(T,0,RezT),!.
eliminaN([H|T],N,[H|RezT]):-
    N2 is N-1,
    eliminaN(T,N2,RezT).
