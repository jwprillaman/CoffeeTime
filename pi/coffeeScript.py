import socket
import fcntl
import struct
import sys
import RPi.GPIO as GPIO
import time


REY_PIN = 11
TRIG = 16
ECHO = 18
DIS_MAX = 8
DIS_MIN = 0

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
def setGPIO():
      GPIO.setmode(GPIO.BOARD)
      GPIO.setup(REY_PIN, GPIO.OUT)
      GPIO.setup(ECHO, GPIO.IN)
      GPIO.setup(TRIG, GPIO.OUT)
def useSonar(client):
  distance = 7
  while DIS_MAX > distance and distance > DIS_MIN:
    #let sensor settle for 1 second
    GPIO.output(TRIG, False)
    time.sleep(1)

    GPIO.output(TRIG, True)
    time.sleep(0.00001)
    GPIO.output(TRIG, False)

    while GPIO.input(ECHO)==0:
        pulse_start = time.time()
    while GPIO.input(ECHO)==1:
      pulse_end = time.time()
    distance = (pulse_end - pulse_start) * 17150
    distance = round(distance, 2)

    print "Distance: " , distance, "cm"

  GPIO.output(REY_PIN, False)
  client.send("PickedUp");


#listen for client alarm 1
def receiveAlarm(client):
      data = client.recv(1024)
      print data
      if 'coffeeTime' in data:
        GPIO.output(REY_PIN,1)



#START Setup

setGPIO()

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
#  turnOnSensor(client)
  useSonar(client)
  client.close()
