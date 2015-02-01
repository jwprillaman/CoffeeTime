import socket
import fcntl
import struct
import sys

if len(sys.argv) < 2 :
  sys.exit('Usage: %s interface' % sys.argv[0])

#interface that server will be on
IF = sys.argv[1]

print 'interface: ' ,IF

#get the ip address for interface IF
def get_ip_address(ifname):
      s = socket.socket(socket.AF_INET, socket.SOCK_DGRAM)
      return socket.inet_ntoa(fcntl.ioctl(
        s.fileno(),
        0x8915,  # SIOCGIFADDR
        struct.pack('256s', ifname[:15])
      )[20:24])

def receiveAlarm(client):
      data = conn.recv(1024)
      print data
        
print get_ip_address(IF)

#create socket
sock = socket.socket()

print "created socket"

#set port
PORT = 5432

#Bind socket to port
sock.bind((get_ip_address(IF),PORT))

print "socket binded to %s" %(PORT)

#listen on port to one connection at a time
sock.listen(1)

print "socket is listening"

#continue listening for clients until exit
while True:
  client, addr = sock.accept()

  print 'Got connection from', addr
  
  client.send('Begin Brewing')
  receiveAlarm(client)

  client.close()
