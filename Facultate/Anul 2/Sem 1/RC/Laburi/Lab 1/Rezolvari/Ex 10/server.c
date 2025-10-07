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
		int len1,len2,maxAparitii=-1,aux;
		char sir1[100],sir2[100],maxChar;
		recv(c,&len1,sizeof(len1),MSG_WAITALL);
		len1=ntohl(len1);
		recv(c,sir1,len1,MSG_WAITALL);
		recv(c,&len2,sizeof(len2),MSG_WAITALL);
		len2=ntohl(len2);
		recv(c,sir2,len2,MSG_WAITALL);
		int fr[256]={0},max=-100;
		if(len2<len1){
			aux=len1;
			len1=len2;
			len2=aux;
		}
		for(int i = 0;i<len1;i++){
			if(sir1[i]==sir2[i]){
				fr[(int)sir1[i]]++;
				if(max<fr[(int)sir1[i]]){
					maxAparitii=fr[(int)sir1[i]];
					maxChar=sir1[i];
					max=fr[(int)sir1[i]];
				}
			}
		}
		maxAparitii=htonl(maxAparitii);
		send(c,&maxAparitii,sizeof(maxAparitii),0);
		send(c,&maxChar,1,0);
		close(c);


	}
	close(s);
	return 0;
}
