package org.usfirst.frc.team4911.robot;

import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.RobotDrive;

public class DriveSystem {
    private CANTalon leftFront3 = new CANTalon(3);
    private CANTalon leftRear4 = new CANTalon(4);
    private CANTalon rightFront7 = new CANTalon(7);
    private CANTalon rightRear8 = new CANTalon(8);
    
    private RobotDrive robot = new RobotDrive(leftFront3, leftRear4, rightFront7, rightRear8);
   
    private double currError;
    private double lastError;
    private double integration;
    private double derivative;
	
    private double goalHeading;
    
    public DriveSystem() {
	robot.setInvertedMotor(RobotDrive.MotorType.kFrontLeft, false);
	robot.setInvertedMotor(RobotDrive.MotorType.kRearLeft, false);

	robot.setInvertedMotor(RobotDrive.MotorType.kFrontRight, true);
	robot.setInvertedMotor(RobotDrive.MotorType.kRearRight, true);
		
	goalHeading = 0.0;
	currError = 0.0;
	lastError = 0.0;
	integration = 0.0;
	derivative = 0.0;
    }
    
    public void updateDrive(double x, double y, double rotation, double angle) {
	if(rotation != 0.0) {
	    drive(x, y, rotation, angle);
	}
	else {
	    driveWithPID(x, y, angle);
	}
    }
    
    public void setGoalHeading (double goalHeading) {
    	double moddedHeading = goalHeading%360.0;
    	if(moddedHeading > 180) {
    		moddedHeading -= 360;
    	}
    	this.goalHeading = moddedHeading;
			
    }
    
    public void drive(double x, double y, double rotation, double angle){
	//Speed Correction
	//x *= speedLimit;
	//y *= speedLimit;
	//rotation *= speedLimit;
	
	double[] wheelSpeeds = new double[4];
	byte syncGroup = 0;
	        
	y = -y;
		
	// Compenstate for gyro angle.
	double rotated[] = rotateVector(x, y, angle);
	x = rotated[0];
	y = rotated[1];

	if(Math.abs(rotation) <= 0.05){
	    wheelSpeeds[0] = x + y;
	    wheelSpeeds[2] = (-x + y) * -1.0;
		
	    leftFront3.changeControlMode(CANTalon.ControlMode.PercentVbus);
	    leftRear4.changeControlMode(CANTalon.ControlMode.Follower);
	    rightFront7.changeControlMode(CANTalon.ControlMode.PercentVbus);
	    rightRear8.changeControlMode(CANTalon.ControlMode.Follower);
        
	    leftFront3.set(wheelSpeeds[0] , syncGroup);
	    rightFront7.set(wheelSpeeds[2] , syncGroup);
	    leftRear4.set(7 , syncGroup);
	    rightRear8.set(3, syncGroup);
  
	} else {
	    wheelSpeeds[0] = x + y + rotation;
	    wheelSpeeds[1] = -x + y + rotation;
	    wheelSpeeds[2] = (-x + y - rotation) * -1.0;
	    wheelSpeeds[3] = (x + y - rotation) * -1.0;

	    leftFront3.changeControlMode(CANTalon.ControlMode.PercentVbus);
	    leftRear4.changeControlMode(CANTalon.ControlMode.PercentVbus);
	    rightFront7.changeControlMode(CANTalon.ControlMode.PercentVbus);
	    rightRear8.changeControlMode(CANTalon.ControlMode.PercentVbus);
        
	    leftFront3.set(wheelSpeeds[0], syncGroup);
	    leftRear4.set(wheelSpeeds[1], syncGroup);
	    rightFront7.set(wheelSpeeds[2], syncGroup);
	    rightRear8.set(wheelSpeeds[3], syncGroup);
	}
    }
    
    public void driveWithPID(double x, double y, double currYaw){
	currError = goalHeading - currYaw;//[-180 - 180] degrees
	if(Math.abs(currError) > 180) {
	    double delta = 360-Math.abs(currError);
	    if(currError > 0) {
		delta*= -1;
	    }
	    currError = delta;
	}
	integration += currError;
	derivative = lastError - currError;
	lastError = currError;
	if ( currError * lastError <0){
	    integration = 0.0;
	}
	
	double kP = 1.0 / 30.0;
	double kI = 0.0;
	double kD = 0.0;
	
	double rotation = kP * currError + kI * integration + kD * derivative;//[-1.0 - 1.0] percentage
	rotation = (rotation < 0) ? Math.max(-1.0, rotation) : Math.min(1.0, rotation);
	//if(!fieldOriented){
	//    currYaw = 0.0;
	//}
	drive(x, y, rotation, currYaw);
}
    
    private static double[] rotateVector(double x, double y, double angle) {
        double cosA = Math.cos(angle * (3.14159 / 180.0));
        double sinA = Math.sin(angle * (3.14159 / 180.0));
        double out[] = new double[2];
        out[0] = x * cosA - y * sinA;
        out[1] = x * sinA + y * cosA;
        return out;
    }
    
    
}
