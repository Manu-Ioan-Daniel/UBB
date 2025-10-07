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
	int a[100],b[100],d[100],n1,n2,n3;
	scanf("%d",&n1);
	scanf("%d",&n2);
	for(int i = 0;i<n1;i++){
		scanf("%d",&a[i]);
		a[i]=htonl(a[i]);
	}
	for(int i = 0;i<n2;i++){
		scanf("%d",&b[i]);
		b[i]=htonl(b[i]);
	}
	n1=htonl(n1);
	n2=htonl(n2);
	send(c,&n1,sizeof(n1),0);
	send(c,a,ntohl(n1)*sizeof(int),0);
	send(c,&n2,sizeof(n2),0);
	send(c,b,ntohl(n2)*sizeof(int),0);
	recv(c,&n3,sizeof(n3),0);
	n3=ntohl(n3);
	recv(c,d,n3*sizeof(int),0);
	for(int i = 0;i<n3;i++){
		d[i]=ntohl(d[i]);
		printf("%d ",d[i]);
	}
	close(c);
	return 0;
}

