
#include<stdio.h>
#include<string.h>
#include<stdlib.h>
#include<arpa/inet.h>
#include<sys/socket.h>
#include <unistd.h>
#define SERVER "127.0.0.1"
#define BUFLEN 512
#define PORT 1234

int main(void)
{
    struct sockaddr_in serveraddr;
    int s, slen=sizeof(serveraddr), clientaddr,nr1,nr2;
    char buf[512],bufr[512];

    s=socket(AF_INET, SOCK_DGRAM, 0);

    memset((char *) &serveraddr, 0, sizeof(serveraddr));
    serveraddr.sin_family = AF_INET;
    serveraddr.sin_port = htons(PORT);
    serveraddr.sin_addr.s_addr = inet_addr(SERVER);

    printf("Introdu numerele: ");
    scanf("%d %d",&nr1,&nr2);
    sprintf(buf,"%d %d",nr1,nr2);
    sendto(s, &buf, strlen(buf)*sizeof(char) , 0 , (struct sockaddr *) &serveraddr, slen);
    recvfrom(s, &bufr, sizeof(bufr), 0, (struct sockaddr *) &clientaddr, &slen);
    printf("Am primit de la server: %s\n" , bufr);
    close(s);
    return 0;
}
