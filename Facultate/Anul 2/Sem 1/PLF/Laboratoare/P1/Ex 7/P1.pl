apartine([H|_], H) :- !.
apartine([_|T], E) :- apartine(T, E).
reuniune([], L, L):-!.
reuniune([H|T], L, Rez) :-
    apartine(L, H),
    reuniune(T, L, Rez),!.
reuniune([H|T], L, [H|RezT]) :-
    reuniune(T, L, RezT).
perechiLista([_],[]):-!.
perechiLista([H|T],Rez):-
    perechiElement(T,H,RezE),
    perechiLista(T,RezT),
    my_append(RezE,RezT,Rez).
perechiElement([], _, []) :- !.
perechiElement([H],E,[[E,H]]):-!.
perechiElement([H|T],E,[[E,H]|RezT]):-
    perechiElement(T,E,RezT).
my_append([], L, L).
my_append([H|T], L, [H|Rez]) :-
    my_append(T, L, Rez).

