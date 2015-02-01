import socket
import fcntl
import struct
import sys
import RPi.GPIO as GPIO

REY_PIN = 11
SEN_PIN = 15



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

#set GPIO properties
def setGPIO(SEN_PIN, REY_PIN):
      GPIO.setmode(GPIO.BOARD)
      GPIO.setup(REY_PIN, GPIO.OUT)
      GPIO.setup(SEN_PIN, GPIO.IN)

#listen for client alarm 1
def receiveAlarm(client):
      data = client.recv(1024)
      print data
      if 'coffeeTime' in data:
        GPIO.output(REY_PIN,1)

# turn on the coffee sensor
def turnOnSensor(client):
  count = 0
  while 1:
    if GPIO.input(SEN_PIN):
      print "motion detected"
      count = count + 1
    if count > 4:
      break
  client.send("PickedUp");


#START Setup

setGPIO(REY_PIN)

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
  turnOnSensor(client)
  client.close()
