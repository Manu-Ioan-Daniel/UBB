%adaug(e:element,L:list,LRez:list)
%(i,i,o)-determinist
adaug(E,[],[E]).%adaug(E,[],Rez):-Rez=[E]
adaug(E,[H|T],[H|Rez]):-adaug(E,T,Rez).
%adaug(E,[H|T],Rez):-adaug(E,T,RezT),Rez=[H|RezT].
%adaug(E,L,Rez):-L=[H|T],adaug(E,T,RezT),Rez=[H|RezT].
