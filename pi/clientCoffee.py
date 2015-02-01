import socket
import sys

HOST = '192.168.1.250'
PORT =  5432

server = socket.socket()

server.connect((HOST,PORT))
data = server.recv(1024)
print repr(data)
server.sendall('coffeeTime')
server.close()
