#include <sys/types.h>
#include <sys/socket.h>
#include <netinet/in.h>
#include <netinet/ip.h>
#include <stdio.h>
#include <unistd.h>
#include <arpa/inet.h>
#include <string.h>
int main(){
	int c,numar,div[100],l;
	struct sockaddr_in server;
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
	scanf("%d",&numar);
	numar=htonl(numar);
	send(c,&numar,sizeof(numar),0);
	recv(c,&l,sizeof(l),0);
	l=ntohl(l);
	recv(c,div,l*sizeof(int),0);
	for(int i = 0;i<l;i++){
		div[i]=ntohl(div[i]);
		printf("%d ",div[i]);
	}
	close(c);
	return 0;
}
