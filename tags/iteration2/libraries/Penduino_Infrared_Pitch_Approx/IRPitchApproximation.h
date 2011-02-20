/* 
 * File:   IRPitchApproximation.h
 * Author: Gavin Esberger
 *
 * Created on 14 December 2010, 23:54
 */

#ifndef _IRPITCHAPPROXIMATION_H
#define	_IRPITCHAPPROXIMATION_H

#include <IRDistance.h>

class IRPitchApproximation {
public:
    IRPitchApproximation(IRDistance * front, IRDistance * rear, double width);
    double getAngleDegrees();
    double getAngleRadian();
private:
    IRDistance * _front;
    IRDistance * _rear;
    double _width;
    double convertRadianToDegree(double radians);
};

#endif	/* _IRPITCHAPPROXIMATION_H */

