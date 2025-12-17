produs([],_,[]):-!.
produs(L, Cifra, Rez):-
    prod(L, Cifra, Rez0, CF),
    (CF > 0 -> Rez = [CF|Rez0]; Rez = Rez0).
prod([], _, [], 0):-!.
prod([], _, [], CF) :- CF > 0.
prod([H|T], Cifra, [H2|RezT], CFOut) :-
    prod(T, Cifra, RezT, CFTail),
    Elem is H * Cifra + CFTail,
    H2 is Elem mod 10,
    CFOut is Elem // 10,!.
