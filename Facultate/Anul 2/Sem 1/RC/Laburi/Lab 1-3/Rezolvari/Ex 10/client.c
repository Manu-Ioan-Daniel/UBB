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
	char sir1[100],sir2[100],maxChar;
	int len1,len2,maxAparitii;
	fgets(sir1,sizeof(sir1),stdin);
	fgets(sir2,sizeof(sir2),stdin);
	len1=htonl(strlen(sir1));
	len2=htonl(strlen(sir2));
	send(c,&len1,sizeof(len1),0);
	send(c,sir1,ntohl(len1),0);
	send(c,&len2,sizeof(len2),0);
	send(c,sir2,ntohl(len2),0);
	recv(c,&maxAparitii,sizeof(maxAparitii),0);
	maxAparitii=ntohl(maxAparitii);
	recv(c,&maxChar,sizeof(maxChar),0);
	printf("%d %c",maxAparitii,maxChar);
	close(c);
	return 0;
}
