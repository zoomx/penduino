
double pGain = 0;
double iGain = 0;
double dGain = 0;

void setup(){
  Serial.begin(9600);
}

void loop(){
  if(Serial.available() != 0){
    int first = Serial.read();
    int noInstructs=0;
    switch(first){
    case 'p':
      pGain +=add();
      break;
    case 'i':
      iGain +=add();
      break;
    case 'd':
      dGain+=add();
      break;
    default :
      Serial.println("WTF");
    }
  }
  delay(10);
  Serial.print(pGain);
  Serial.print("\t");
  Serial.print(iGain);
  Serial.print("\t");
  Serial.print(dGain);
  Serial.println("\t");
}


double add(){
  int noInstructs = Serial.read()-48;
  double total = 0;
  for(int i = 0; i < noInstructs; i++){
    int value = Serial.read();
    switch(value){
    case '+':
      total += 1;
      break;
    case '>':
      total += 0.1;
      break;
    case '}':
      total += 0.01;
      break;
    case '-':
      total -= 1;
      break;
    case '<':
      total -= 0.1;
      break;
    case '{':
      total -= 0.01;
      break;
    default:;
    }
  }
  Serial.flush();
  return total;
}



