#include <GP2Y0A21YK.h>
#include <IRPitchApproximation.h>

GP2Y0A21YK frontIR(0);
GP2Y0A21YK backIR(5);

IRPitchApproximation pitchApprox(&frontIR, &backIR, 15.3);

void setup(){
  Serial.begin(9600);
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
  delay(250);
}



