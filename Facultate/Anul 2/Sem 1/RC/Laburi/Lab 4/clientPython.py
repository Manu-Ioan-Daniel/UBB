import socket
import struct

server_ip = input("Server IP: ")
port = int(input("Port: "))
s = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
s.connect((server_ip, port))
caracter = input("Caracter: ")[0].encode()
sir = input("Sir: ").encode()
s.sendall(caracter)
lungimeSir = len(sir)
s.sendall(struct.pack('!I', lungimeSir))  # ! inseamna htons basically, I=int pe 4 bytes
s.sendall(sir)
data = s.recv(4) # primim lungimea sirului poz
lungimePoz = struct.unpack('!I', data)[0] # transformam din network byte order in host byte order
poz = []
for _ in range(lungimePoz):
    data = s.recv(4) # primim fiecare element din poz pe rand
    poz.append(struct.unpack('!I', data)[0]) # transformam din network byte order in host byte order
print("Pozitii:", poz)
s.close()
