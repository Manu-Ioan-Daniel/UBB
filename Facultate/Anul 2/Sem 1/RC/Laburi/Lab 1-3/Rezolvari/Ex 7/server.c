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
		int i,l,len,dimSubsir;
		char sir[100],subsir[100];
		recv(c,&i,sizeof(i),MSG_WAITALL);
		recv(c,&l,sizeof(l),MSG_WAITALL);
		recv(c,&len,sizeof(len),MSG_WAITALL);
		len=ntohl(len);
		recv(c,sir,len,MSG_WAITALL);
		i=ntohl(i);
		l=ntohl(l);
		sir[len]='\0';
		int k=0;
		for(int j = 0;j<strlen(sir);j++){
			if(j>=i && l>0){
				subsir[k++]=sir[j];
				l--;
			}
			if(l==0)
				break;
		}
		subsir[k]='\0';
		k=htonl(k);
		send(c,&k,sizeof(k),0);
		send(c,subsir,strlen(subsir),0);
		close(c);


	}
	close(s);
	return 0;
}
