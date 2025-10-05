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
		uint16_t l1,l2,l3;
		char sir[100],sir2[100],sirOrdonat[100];
		recv(c,&l1,sizeof(l1),MSG_WAITALL);
		l1=ntohs(l1);
		recv(c,sir,l1,MSG_WAITALL);
		recv(c,&l2,sizeof(l2),MSG_WAITALL);
		l2=ntohs(l2);
		recv(c,sir2,l2,MSG_WAITALL);
		int i=0,j=0,k=0;
		while(i<l1 && j<l2){
			if(sir[i]<sir2[j]){
				sirOrdonat[k]=sir[i];
				i++;
			}
			else{
				sirOrdonat[k]=sir2[j];
				j++;
			}
			k++;
		}
		while(i<l1){
			sirOrdonat[k]=sir[i];
			k++;
			i++;
		}
		while(j<l2){
			sirOrdonat[k]=sir2[j];
			j++;
			k++;
		}
		sirOrdonat[k]='\0';
		l3=k;
		l3=htons(l3);
		send(c,&l3,sizeof(l3),0);
		send(c,sirOrdonat,k,0);
		close(c);


	}
	close(s);
	return 0;
}
