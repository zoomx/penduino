#include <GP2Y0A21YK.h>
#include <IRPitchApproximation.h>
#include <Servo.h> 
#include <PID.h>

GP2Y0A21YK frontIR(0);
GP2Y0A21YK backIR(5);

IRPitchApproximation pitchApprox(&frontIR, &backIR, 15.3);

Servo leftServo;
Servo rightServo;

PID pid;

void setup(){
  Serial.begin(9600);
  leftServo.attach(11);
  rightServo.attach(10);
  pid.setMaxOut(30);
  pid.setMinOut(-30);
  
  pid.setPGain(2.4);
  pid.setIGain(0.1);
  pid.setDGain(-1);
}

void loop(){
  long t1 = millis();
  double angle = pitchApprox.getAngleDegrees();
  Serial.print(angle);
  Serial.print("\t");
  int driveAmount = pid.getPID(angle);
  drive(driveAmount, driveAmount);
  Serial.println(driveAmount);
  delay(25-(millis()-t1));
}

void drive(int leftSpeed, int rightSpeed){
 leftServo.write(90+leftSpeed);
 rightServo.write(90-rightSpeed);
}



