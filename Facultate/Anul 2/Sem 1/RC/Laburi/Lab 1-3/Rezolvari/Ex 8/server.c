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
		int n1,n2,n3=0,a[100],b[100],d[100];
		recv(c,&n1,sizeof(n1),MSG_WAITALL);
		n1=ntohl(n1);
		recv(c,a,n1*sizeof(int),MSG_WAITALL);
		recv(c,&n2,sizeof(n2),MSG_WAITALL);
		n2=ntohl(n2);
		recv(c,b,n2*sizeof(int),MSG_WAITALL);
		for(int i = 0;i<n1;i++)
			for(int j = 0;j<n2;j++)
				if(a[i]==b[j])
					d[n3++]=a[i];
		n3=htonl(n3);
		send(c,&n3,sizeof(n3),0);
		send(c,d,ntohl(n3)*sizeof(int),0);
		close(c);


	}
	close(s);
	return 0;
}
