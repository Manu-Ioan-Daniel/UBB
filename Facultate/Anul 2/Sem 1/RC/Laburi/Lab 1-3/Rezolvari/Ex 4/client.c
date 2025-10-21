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
	char sir[100],sir2[100],sirOrdonat[100];
	uint16_t l1,l2,l3;
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
	fgets(sir2,sizeof(sir2),stdin);
	sir[strcspn(sir, "\n")] = 0;
	sir2[strcspn(sir2, "\n")] = 0;
	l1=strlen(sir);
	l2=strlen(sir2);
	l1=htons(l1);
	send(c,&l1,sizeof(l1),0);
	send(c,sir,strlen(sir),0);
	l2=htons(l2);
	send(c,&l2,sizeof(l2),0);
	send(c,sir2,strlen(sir2),0);
	recv(c,&l3,sizeof(l3),0);
	l3=ntohs(l3);
	recv(c,sirOrdonat,l3,0);
	sirOrdonat[l3]='\0';
	printf("%s",sirOrdonat);
	close(c);
	return 0;
}
