package org.usfirst.frc.team4911.robot;

import edu.wpi.first.wpilibj.Joystick;

public class ModdedJoystick extends Joystick{
    
    public ModdedJoystick(int port) {
	super(port);
    }
    
    public double getRoundedXValue() {
	return super.getX() > 0.1 ? Math.pow(super.getX(), 3) : 0.0;
    }
    public double getRoundedYValue() {
	return super.getY() > 0.1 ? Math.pow(super.getY(), 3) : 0.0;
    }
    public double getRoundedZValue() {
	return super.getZ() > 0.1 ? Math.pow(super.getZ(), 3) : 0.0;
    }
    
    public double getLimitedXValue() {
	return limitValue(getRoundedXValue());
    }
    public double getLimitedYValue() {
	return limitValue(getRoundedYValue());
    }
    public double getLimitedZValue() {
	return limitValue(getRoundedZValue());
    }

    public double limitValue(double value) {
	return (Math.round(value * 0.1) / 0.1) * super.getThrottle();
    }
}
