/* 
 * File:   GP2Y0A21YK.h
 * Author: gavin
 *
 * Created on 15 December 2010, 00:12
 */

#ifndef _GP2Y0A21YK_H
#define	_GP2Y0A21YK_H

#include <IRDistance.h>
#include "WProgram.h"

class GP2Y0A21YK : public IRDistance {
public:
	GP2Y0A21YK(int pin);
    double convertVoltageToCM(int voltage);
};


#endif	/* _GP2Y0A21YK_H */

