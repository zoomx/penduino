#include <GP2Y0A21YK.h>
#include <IRPitchApproximation.h>

GP2Y0A21YK frontIR(0);
GP2Y0A21YK backIR(5);

IRPitchApproximation pitchApprox(&frontIR, &backIR, 18);

void setup(){
  Serial.begin(9600);
}

void loop(){
  double angle = pitchApprox.getAngleDegrees();
  Serial.print(angle);
  delay(125);
}



