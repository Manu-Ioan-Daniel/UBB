%submultime(L:lista,Rez:o submultim a listei)
%model de flux (i,o)
submultime([], []).
submultime([H|T], [H|Sub]) :-
    submultime(T, Sub).
submultime([_|T], Sub) :-
    submultime(T, Sub).
%suma(L:lista,S:numar ce reprezinta suma elementelor listei)
%model de flux(i,o)
suma([], 0).
suma([H|T], S) :-
    suma(T, S1),
    S is H + S1.
% submultimi_divizibile(L:lista,Rez:lista ce contine toate submultimile
% lui L cu suma divizibila cu lungimea lui L
% model de flux(i,o)
submultimi_divizibile([],[]).
submultimi_divizibile(List, Rez) :-
    my_length(List, N),
    findall(Sub, (submultime(List, Sub), Sub \= [], suma(Sub, S), 0 is S mod N), Rez).
%my_length(L:lista,Rez:numar care reprez lungimea listei)
%model de flux(i,o)
my_length([],0).
my_length([_|T],Rez):-
    my_length(T,RezT),
    Rez is RezT+1.
%submultimi_divizibile([1,2,3,4],Rez).
%Rez = [[1, 3, 4], [1, 3], [4]].

