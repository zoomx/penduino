#include <Servo.h> 

#include <SerialControlledPid.h>
#include <GP2Y0A21YK.h>
#include <IRPitchApproximation.h>

#define  doubleSize  sizeof(double)
#define  zeroPoint  90

boolean balanceFlag = false;

SerialControlledPid pid(0, doubleSize, doubleSize*2);

GP2Y0A21YK frontIR(0);
GP2Y0A21YK backIR(5);
IRPitchApproximation pitchApprox(&frontIR, &backIR, 18);

Servo leftServo;
Servo rightServo;

void setup(){
  Serial.begin(9600);

  leftServo.attach(6);
  rightServo.attach(5);

  pid.setMaxOut(30);
  pid.setMinOut(-30);
  
  //block until horizontal?
  
  setupTimer2();
}

void loop(){
  if(balanceFlag){
    balance();
  }
  pid.readSerialCommands();
}

void flagBalance(){
  balanceFlag = true; 
}

void balance(){
  double angle = pitchApprox.getAngleDegrees();
  int driveAmount = pid.getPID(angle);
  drive(driveAmount, driveAmount);
  printTelemetry(angle);
  balanceFlag = false;
}

void drive(int leftSpeed, int rightSpeed){
  leftServo.write(zeroPoint+leftSpeed);
  rightServo.write(zeroPoint-rightSpeed);
}

void printTelemetry(double pitch){
  Serial.print(pitch);
  Serial.print(' ');
  Serial.print(pid.getPGain());
  Serial.print(' ');
  Serial.print(pid.getIGain());
  Serial.print(' ');
  Serial.print(pid.getDGain());
  Serial.print('\n');
}

