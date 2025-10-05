#include <unistd.h>
#include <stdio.h>
#include <string.h>
#include <netinet/in.h>
#include <netinet/ip.h>
#include <sys/types.h>
#include <sys/socket.h>
#include <arpa/inet.h>
int main(){
	int c,s,l;
	struct sockaddr_in server,client;
	uint16_t lungimeSir,nrSpatii=0;
	char litera;
	s=socket(AF_INET,SOCK_STREAM,0);
	if(s<0){
		printf("Eroare la crearea socket-ului!\n");
		return 1;
	}
	memset(&server,0,sizeof(server));
	server.sin_port=htons(1234);
	server.sin_family=AF_INET;
	server.sin_addr.s_addr=INADDR_ANY;
	if(bind(s,(struct sockaddr*)&server,sizeof(server))<0){
		printf("Eroare la bind!\n");
		return 1;
	}
	listen(s,5);
	l=sizeof(client);
	memset(&client,0,sizeof(client));
	while(1){
		c=accept(s,(struct sockaddr*)&client,&l);
		nrSpatii=0;
		printf("S-a conectat un client!\n");
		recv(c,&lungimeSir,sizeof(lungimeSir),MSG_WAITALL);
		lungimeSir=ntohs(lungimeSir);
		for(int i = 0;i<lungimeSir;i++){
			recv(c,&litera,sizeof(litera),MSG_WAITALL);
			if(litera==' '){
				nrSpatii+=1;
			}
		}
		nrSpatii=htons(nrSpatii);
		send(c,&nrSpatii,sizeof(nrSpatii),0);
		close(c);
	}
	close(s);
	return 0;
}
