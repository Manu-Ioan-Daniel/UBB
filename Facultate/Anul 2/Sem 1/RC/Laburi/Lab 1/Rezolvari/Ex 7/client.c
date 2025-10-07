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
	char sir[100],subsir[100];
	int i,l,dimSubsir,len;
	scanf("%d",&i);
	scanf("%d",&l);
	getchar();
	fgets(sir,sizeof(sir),stdin);
	i=htonl(i);
	l=htonl(l);
	len=htonl(strlen(sir));
	send(c,&i,sizeof(i),0);
	send(c,&l,sizeof(l),0);
	send(c,&len,sizeof(len),0);
	send(c,sir,strlen(sir),0);
	recv(c,&dimSubsir,sizeof(dimSubsir),0);
	dimSubsir=ntohl(dimSubsir);
	recv(c,subsir,dimSubsir,0);
	subsir[dimSubsir]='\0';
	printf("%s",subsir);
	close(c);
	return 0;
}
