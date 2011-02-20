/* 
 * File:   IRPitchApproximation.cpp
 * Author: Gavin Esberger
 * 
 * Created on 14 December 2010, 23:54
 */

#include "IRDistance.h"

#include "IRPitchApproximation.h"
#include "WProgram.h"

IRPitchApproximation::IRPitchApproximation(IRDistance * front, IRDistance * rear, double width) {
    _front = front;
    _rear = rear;
    _width = width;
}

double IRPitchApproximation::getAngleDegrees() {
    return convertRadianToDegree(getAngleRadian());
}

double IRPitchApproximation::getAngleRadian() {
    double f = _front -> getDistance(10);
    double r = _rear -> getDistance(10);
    double adjacent = f - r;
    double angle = atan(_width / adjacent);
    double normalisedAngle = angle < 0 ? (PI / 2) + angle : -1 * ((PI / 2) - angle);
    return normalisedAngle;
}

double IRPitchApproximation::convertRadianToDegree(double radians) {
    return radians * (180 / PI);
}



