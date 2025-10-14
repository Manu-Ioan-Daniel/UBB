%Enunt:Dandu se o lista numerica,sa e stearga toate secventele de valori
%strict crescatoare
%elimSecv(L-lista,F-int,Rez-lista)
%model de flux(i,i,o)
elimSecv([],_,[]).
elimSecv([H],0,[H]).
elimSecv([H],1,[]).
elimSecv([H1,H2|T],F,Rez):-
    H1 < H2,
    elimSecv([H2,T],1,Rez).
elimSecv([H1,H2|T],1,Rez):-
    H1 >= H2,
    elimSecv([H2|T],0,Rez).
elimSecv([H1,H2|T],0,Rez):-
    H1>=H2,
    elimSecv([H2|T],0,RezT),
    Rez=[H1|RezT].
elimSecvPrincipal(L,Rez):-
    elimSecv(L,0,Rez).
