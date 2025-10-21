#include <sys/types.h>
#include <sys/socket.h>
#include <netinet/in.h>
#include <netinet/ip.h>
#include <stdio.h>
#include <unistd.h>
#include <arpa/inet.h>
#include <string.h>
int main(){
	int c;
	struct sockaddr_in server;
	char sir[100],sirReverse[100];
	uint16_t lungimeSir;
	c=socket(AF_INET,SOCK_STREAM,0);
	if(c<0){
		printf("Eroare");
		return 1;
	}
	memset(&server,0,sizeof(server));
	server.sin_port=htons(1234);
	server.sin_family=AF_INET;
	server.sin_addr.s_addr=inet_addr("127.0.0.1");
	if(connect(c,(struct sockaddr*) &server,sizeof(server))<0){
		printf("Eroare");
		return 1;
	}
	fgets(sir,sizeof(sir),stdin);
	lungimeSir=strlen(sir);
	lungimeSir=htons(lungimeSir);
	send(c,&lungimeSir,sizeof(lungimeSir),0);
	send(c,sir,strlen(sir),0);
	recv(c,sirReverse,strlen(sir),0);
	printf("%s",sirReverse);
	close(c);
	return 0;
}
