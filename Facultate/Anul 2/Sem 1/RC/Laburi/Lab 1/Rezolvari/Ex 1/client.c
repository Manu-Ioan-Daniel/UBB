#include <sys/types.h>
#include <unistd.h>
#include <sys/socket.h>
#include <stdio.h>
#include <netinet/in.h>
#include <netinet/ip.h>
#include <string.h>
#include <arpa/inet.h>
int main(){
        int c;
        struct sockaddr_in server;
	uint16_t n,vec[1000],suma;
        c=socket(AF_INET,SOCK_STREAM,0);
        if(c<0){
                printf("Eroare la crearea socketului client!\n");
                return 1;
        }
        memset(&server,0,sizeof(server));
        server.sin_port=htons(1234);
        server.sin_family=AF_INET;
        server.sin_addr.s_addr=inet_addr("127.0.0.1");
        if(connect(c,(struct sockaddr *) &server,sizeof(server)) <0){
                printf("Eroare la conectarea la server!\n");
                return 1;
        }
        //senduri si recvuri
	printf("Introdu nr de numere:");
	scanf("%hu",&n);
	for(uint16_t i = 0;i<n;i++){
		scanf("%hu",&vec[i]);
	}
	for(int i = 0;i<n;i++){
		vec[i]=htons(vec[i]);
	}
	n=htons(n);
	send(c,&n,sizeof(n),0);
	send(c,vec,n*sizeof(uint16_t),0);
	recv(c,&suma,sizeof(suma),0);
	suma=ntohs(suma);
	printf("Suma este %hu",suma);
        close(c);
}
