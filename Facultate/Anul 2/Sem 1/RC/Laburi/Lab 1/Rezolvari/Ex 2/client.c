#include <unistd.h>
#include <stdio.h>
#include <netinet/in.h>
#include <netinet/ip.h>
#include <arpa/inet.h>
#include <sys/socket.h>
#include <sys/types.h>
#include <string.h>
int main(){
	int c;
	char sir[100];
	uint16_t nrSpatii,lungimeSir;
	struct sockaddr_in server;
	c=socket(AF_INET,SOCK_STREAM,0);
	if(c<0){
		printf("Eroare la crearea socket-ului!\n");
		return 1;
	}
	memset(&server,0,sizeof(server));
	server.sin_port=htons(1234);
	server.sin_family=AF_INET;
	server.sin_addr.s_addr=inet_addr("127.0.0.1");
	if(connect(c,(struct sockaddr*) &server,sizeof(server))<0){
		printf("Eroare la conectare!\n");
		return 1;
	}
	fgets(sir,sizeof(sir),stdin);
	lungimeSir=strlen(sir);
	lungimeSir=htons(lungimeSir);
	send(c,&lungimeSir,sizeof(lungimeSir),0);
	send(c,sir,strlen(sir),0);
	recv(c,&nrSpatii,sizeof(nrSpatii),0);
	nrSpatii=ntohs(nrSpatii);
	printf("Nr de spatii este:%hu",nrSpatii);
	close(c);
	return 0;
}
