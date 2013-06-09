void setupTimer2(){
  /*
    Timer/Counter Control Register
    CS22, CS21, CS20 all refer to Clock select bits
    To get a prescaler of 1024 they are all set to 1 (says the datasheet)
  */
  TCCR2B |= (1<<CS22)|(1<<CS21)|(1<<CS20);
  
  //Interrupt mask register. TOIE2 is the overflow enabled bit
  TIMSK2 |= (1<<TOIE2);
  
  //Set the timer/counter to 0. 0 is the default value anyway!!!
  TCNT2 = 0;
}

ISR(TIMER2_OVF_vect){
  flagBalance();
}
