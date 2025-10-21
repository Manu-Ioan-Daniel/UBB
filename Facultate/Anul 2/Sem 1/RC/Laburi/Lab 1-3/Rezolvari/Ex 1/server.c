#include <arpa/inet.h>
#include <string.h>
#include <unistd.h>
#include <netinet/in.h>
#include <netinet/ip.h>
#include <stdio.h>
#include <sys/socket.h>
#include <sys/types.h>
int main(){
	struct sockaddr_in server,client;
	int s,c,l;
	uint16_t n,el,suma=0;
	s=socket(AF_INET,SOCK_STREAM,0);
	if(s<0){
		printf("Eroare la crearea socketului server!\n");
		return 1;
	}
	memset(&server,0,sizeof(server));
	server.sin_port=htons(1234);
	server.sin_family=AF_INET;
	server.sin_addr.s_addr=INADDR_ANY;
	if(bind(s,(struct sockaddr*) &server,sizeof(server))<0){
		printf("Eroare la bind!\n");
		return 1;
	}
	listen(s,5);
	l=sizeof(client);
	memset(&client,0,sizeof(client));
	while(1){

		c=accept(s,(struct sockaddr*) &client,&l);
		suma=0;
		printf("S-a conectat un client!\n");
		recv(c,&n,sizeof(n),MSG_WAITALL);
		n=ntohs(n);
		for(int i = 0;i<n;i++){
			recv(c,&el,sizeof(el),MSG_WAITALL);
			el=ntohs(el);
			suma+=el;
		}
		suma=htons(suma);
		send(c,&suma,sizeof(suma),0);
		close(c);
	}
	return 0;
}
