#include <arpa/inet.h>
#include <stdio.h>
#include <unistd.h>
#include <netinet/in.h>
#include <netinet/ip.h>
#include <sys/types.h>
#include <sys/socket.h>
#include <string.h>
#include <signal.h>
#include <stdlib.h>
int main(int argc,char* argv[]){
	int c,s;
	socklen_t clientLen;
	struct sockaddr_in server,client;
	s=socket(AF_INET,SOCK_STREAM,0);
	if(s<0){
		printf("Eroare");
		return 1;
	}
	memset(&server,0,sizeof(server));
	server.sin_port=htons(atoi(argv[1]));
	server.sin_family=AF_INET;
	server.sin_addr.s_addr=INADDR_ANY;
	if(bind(s,(struct sockaddr*)&server,sizeof(server))<0){
		printf("Eroare");
		return 1;
	}
	listen(s,5);
	clientLen=sizeof(client);
	memset(&client,0,sizeof(client));
	signal(SIGCHLD, SIG_IGN);
	while(1){
		c=accept(s,(struct sockaddr*)&client,&clientLen);
		int pid=fork();
		if(pid<0){
			printf("Eroare la fork!");
			return 1;
		}
		if(pid==0){
			close(s);
			printf("S-a conectat un client!\n");
			char caracter,sir[100];
			int lungimeSir,lungimePoz,poz[100],k=0;
			recv(c,&caracter,sizeof(caracter),MSG_WAITALL);
			recv(c,&lungimeSir,sizeof(lungimeSir),MSG_WAITALL);
			lungimeSir=ntohl(lungimeSir);
			recv(c,sir,lungimeSir,MSG_WAITALL);
			for(int i = 0;i<strlen(sir);i++){
				if(sir[i]==caracter)
					poz[k++]=htonl(i);
			}
			lungimePoz=k;
			k=htonl(k);
			send(c,&k,sizeof(k),0);
			send(c,poz,lungimePoz*sizeof(int),0);
			close(c);
			exit(0);
		}else{
			close(c);
		}
	}
	close(s);
	return 0;
}
