import RPi.GPIO as GPIO
import time

GPIO.setmode(GPIO.BOARD)
PIN = 15

GPIO.setup(PIN, GPIO.IN)

def Motion(Pin):
  print "Motion Detected"
time.sleep(2)
print "Ready"
while 1:
  if GPIO.input(PIN):
    print "motion detected"
    time.sleep(1)
