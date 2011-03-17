#include <GP2Y0A21YK.h>
#include "WProgram.h"

GP2Y0A21YK::GP2Y0A21YK(int pin):IRDistance(pin){
}

double GP2Y0A21YK::convertVoltageToCM(int voltage){
	return 12343.85 * pow(voltage, -1.15);
}
