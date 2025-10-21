#include <sys/types.h>
#include <sys/socket.h>
#include <netinet/in.h>
#include <netinet/ip.h>
#include <stdio.h>
#include <unistd.h>
#include <arpa/inet.h>
#include <string.h>
int main(int argc,char* argv[]){
	int c,lungimeSir,poz[100],lungimePoz;
	char sir[100],caracter;
	struct sockaddr_in server;
	c=socket(AF_INET,SOCK_STREAM,0);
	if(c<0){
		printf("Eroare");
		return 1;
	}
	memset(&server,0,sizeof(server));
	server.sin_port=htons(atoi(argv[1]));
	server.sin_family=AF_INET;
	server.sin_addr.s_addr=inet_addr(argv[2]);
	if(connect(c,(struct sockaddr*) &server,sizeof(server))<0){
		printf("Eroare");
		return 1;
	}
	scanf("%c",&caracter);
	getchar();
	fgets(sir,sizeof(sir),stdin);
	send(c,&caracter,sizeof(caracter),0);
	lungimeSir=htonl(strlen(sir));
	send(c,&lungimeSir,sizeof(lungimeSir),0);
	send(c,sir,strlen(sir),0);
	recv(c,&lungimePoz,sizeof(lungimePoz),0);
	lungimePoz=ntohl(lungimePoz);
	recv(c,poz,lungimePoz*sizeof(int),0);
	for(int i = 0;i<lungimePoz;i++){
		poz[i]=ntohl(poz[i]);
		printf("%d ",poz[i]);
	}
	close(c);
	return 0;
}
