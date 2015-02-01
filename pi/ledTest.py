import RPi.GPIO as GPIO
import time
import sys

if len(sys.argv) < 2 :
  sys.exit('Usage: %s boolean' % sys.argv[0])

pin = 11 
GPIO.setmode(GPIO.BOARD)

GPIO.setup(pin, GPIO.OUT)
if sys.argv[1] == "True":
	print "turn on"
	GPIO.output(pin,1)
	time.sleep(10)
		 
if sys.argv[1] == "False":
	print "turn off"
	GPIO.output(pin,0)
	time.sleep(10)
#GPIO.cleanup()

