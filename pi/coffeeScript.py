import socket
import fcntl
import struct

IF = 'wlan0'

def get_ip_address(ifname):
      s = socket.socket(socket.AF_INET, socket.SOCK_DGRAM)
      return socket.inet_ntoa(fcntl.ioctl(
        s.fileno(),
        0x8915,  # SIOCGIFADDR
        struct.pack('256s', ifname[:15])
      )[20:24])

print get_ip_address(IF)

sock = socket.socket()

print "created socket"

PORT = 5432

sock.bind((get_ip_address(IF),PORT))

print "socket binded to %s" %(PORT)

sock.listen(1)

print "socket is listening"

while True:
  client, addr = sock.accept()

  print 'Got connection from', addr

  client.send('you have connected')

  client.close()
