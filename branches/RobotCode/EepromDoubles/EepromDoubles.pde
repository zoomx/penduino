#include <avr/eeprom.h>

double number = 0.10;


void setup(){
  Serial.begin(9600);
  double toWrite = 3.21;
  //eeprom_write_block(&toWrite,0, sizeof(double));
  eeprom_read_block(&number, 0, sizeof(double));
  Serial.println(number);
}

void loop(){
  //do nothing
}
