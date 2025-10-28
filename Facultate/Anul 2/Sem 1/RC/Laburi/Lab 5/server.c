#include<stdio.h>
#include<string.h>
#include<stdlib.h>
#include<arpa/inet.h>
#include<sys/socket.h>
#include <unistd.h>
#define BUFLEN 512
#define PORT 1234
uint16_t cmmdc(uint16_t a, uint16_t b) {
    while (b != 0) {
        uint16_t r = a % b;
        a = b;
        b = r;
    }
    return a;
}

int main(void)
{
    struct sockaddr_in serveraddr, clientaddr;

    int s,slen = sizeof(clientaddr),counter=0;
    uint16_t nr1,nr2;
    char buf[512], bufr[512];
    s=socket(AF_INET, SOCK_DGRAM, 0);
    memset((char *) &serveraddr, 0, sizeof(serveraddr));
    serveraddr.sin_family = AF_INET;
    serveraddr.sin_port = htons(PORT);
    serveraddr.sin_addr.s_addr = htonl(INADDR_ANY);
    bind(s , (struct sockaddr*)&serveraddr, sizeof(serveraddr));

    while(1)
    {
        printf("Waiting for data...\n");
	memset((char *) &buf, 0, sizeof(buf));
        recvfrom(s, &buf, sizeof(buf), 0, (struct sockaddr *) &clientaddr, &slen);
	char* numar=strtok(buf," ");
	counter=0;
	while(numar!=NULL){
		if(counter==0){
			nr1=atoi(numar);
			counter++;
			numar=strtok(NULL," ");
			printf("Received packet from %s:%d\n", inet_ntoa(clientaddr.sin_addr), ntohs(clientaddr.sin_port));
			printf("Data: %d\n" , nr1);
		}else{
			nr2=atoi(numar);
			printf("Received packet from %s:%d\n", inet_ntoa(clientaddr.sin_addr), ntohs(clientaddr.sin_port));
			printf("Data: %d\n" , nr2);
			break;
		}
	}
	memset((char *) &bufr, 0, sizeof(bufr));
	uint16_t a=cmmdc(nr1,nr2);
	uint16_t b=nr1*nr2/a;
	sprintf(bufr,"%hu %hu",a,b);
	printf("Trimit inapoi: %s\n",bufr);
	sendto(s, &bufr, strlen(bufr)*sizeof(char) , 0 , (struct sockaddr *) &clientaddr, slen);
    }
    close(s);
    return 0;
}
