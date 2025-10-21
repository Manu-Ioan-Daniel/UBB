#include <arpa/inet.h>
#include <stdio.h>
#include <unistd.h>
#include <netinet/in.h>
#include <netinet/ip.h>
#include <sys/types.h>
#include <sys/socket.h>
#include <string.h>
int main(){
	int c,s;
	socklen_t clientLen;
	struct sockaddr_in server,client;
	s=socket(AF_INET,SOCK_STREAM,0);
	if(s<0){
		printf("Eroare");
		return 1;
	}
	memset(&server,0,sizeof(server));
	server.sin_port=htons(1234);
	server.sin_family=AF_INET;
	server.sin_addr.s_addr=INADDR_ANY;
	if(bind(s,(struct sockaddr*)&server,sizeof(server))<0){
		printf("Eroare");
		return 1;
	}
	listen(s,5);
	clientLen=sizeof(client);
	memset(&client,0,sizeof(client));
	while(1){
		c=accept(s,(struct sockaddr*)&client,&clientLen);
		uint16_t lungimeSir;
		char sir[100],reverseSir[100];
		recv(c,&lungimeSir,sizeof(lungimeSir),MSG_WAITALL);
		lungimeSir=ntohs(lungimeSir);
		recv(c,sir,lungimeSir,MSG_WAITALL);
		for(int i = 0;i<lungimeSir;i++){
			reverseSir[lungimeSir-i-1]=sir[i];
		}
		reverseSir[lungimeSir]='\0';
		printf("%hu\n",lungimeSir);
		send(c,reverseSir,lungimeSir,0);
		close(c);
	}
	close(s);
	return 0;
}
