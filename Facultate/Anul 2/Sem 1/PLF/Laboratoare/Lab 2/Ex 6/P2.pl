my_append([], L, L).
my_append([H|T], L2, [H|RezT]) :-
    my_append(T, L2, RezT).
inlocuire([],_,_,[]).
inlocuire([H|T],H,L,Rez):-
    inlocuire(T,H,L,RezT),
    my_append(L,RezT,Rez),!.
inlocuire([H|T],E,L,[H|RezT]):-
    inlocuire(T,E,L,RezT).

