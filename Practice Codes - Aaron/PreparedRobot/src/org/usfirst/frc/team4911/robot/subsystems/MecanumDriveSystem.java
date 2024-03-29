package org.usfirst.frc.team4911.robot.subsystems;

import org.usfirst.frc.team4911.robot.Robot;
import org.usfirst.frc.team4911.robot.RobotConstants;
import org.usfirst.frc.team4911.robot.RobotMap;
import org.usfirst.frc.team4911.robot.subsystems.*;

import java.math.*;
import java.util.Arrays;

import com.kauailabs.nav6.frc.IMUAdvanced;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.SerialPort;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.RobotDrive.MotorType;
import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.command.Subsystem;

public class MecanumDriveSystem extends Subsystem {
    private CANTalon frontLeft = RobotMap.leftFront;
    private CANTalon rearLeft = RobotMap.leftRear;
    private CANTalon frontRight = RobotMap.rightFront;
    private CANTalon rearRight = RobotMap.rightRear;
    private RobotDrive robot = new RobotDrive(frontLeft, rearLeft, frontRight, rearRight);

    private Encoder ySlideEncoder = RobotMap.ySlideEncoder;
    private Encoder xSlideEncoder = RobotMap.xSlideEncoder;
	
    private double rotation;
    private double currError;
    private double lastError;
    private double integration;
    private double derivative;
	
    private double goalHeading;
    private double speed;
    private IMUAdvanced imu = RobotMap.imu;		
    
    private PrintSystem printSystem;
	
    public MecanumDriveSystem(){
		super();
		robot.setInvertedMotor(RobotDrive.MotorType.kFrontLeft, false);
		robot.setInvertedMotor(RobotDrive.MotorType.kRearLeft, false);
	
		robot.setInvertedMotor(RobotDrive.MotorType.kFrontRight, true);
		robot.setInvertedMotor(RobotDrive.MotorType.kRearRight, true);
			
		goalHeading = 0.0;
		speed = 1.0;
		
		printSystem = Robot.printSystem;
    }
	
    @Override
    protected void initDefaultCommand() {

    }
	
    //================ DRIVE CONTROL METHODS ==========================	
    public void drive(double x, double y, double rotation, double angle){
	//Speed Control
	x *= speed;
	y *= speed;
	rotation *= speed;
	robot.mecanumDrive_Cartesian(x, y, rotation, angle);
	/*
		double[] wheelSpeeds = new double[4];
		byte syncGroup = 0;
		
		//Speed Control
		x *= speed;
    	y *= speed;
    	rotation *= speed;
		        
		y = -y;
		
		// Compenstate for gyro angle.
        double rotated[] = rotateVector(x, y, angle);
        x = rotated[0];
        y = rotated[1];
		
		if(rotation == 0.0){
			wheelSpeeds[0] = x + y;
			wheelSpeeds[2] = (-x + y) * -1.0;
			
			frontLeft.changeControlMode(CANTalon.ControlMode.PercentVbus);
	        rearLeft.changeControlMode(CANTalon.ControlMode.Follower);
	        frontRight.changeControlMode(CANTalon.ControlMode.PercentVbus);
	        rearRight.changeControlMode(CANTalon.ControlMode.Follower);
	        
	        frontLeft.set(wheelSpeeds[0] , syncGroup);
	        frontRight.set(wheelSpeeds[2] , syncGroup);
	        rearLeft.set(7 , syncGroup);
	        rearRight.set(3, syncGroup);


	        
		} else {
			wheelSpeeds[0] = x + y + rotation;
	        wheelSpeeds[1] = -x + y + rotation;
	        wheelSpeeds[2] = (-x + y - rotation) * -1.0;
	        wheelSpeeds[3] = (x + y - rotation) * -1.0;

	        frontLeft.changeControlMode(CANTalon.ControlMode.PercentVbus);
	        rearLeft.changeControlMode(CANTalon.ControlMode.PercentVbus);
	        frontRight.changeControlMode(CANTalon.ControlMode.PercentVbus);
	        rearRight.changeControlMode(CANTalon.ControlMode.PercentVbus);
	        
	        frontLeft.set(wheelSpeeds[0], syncGroup);
	        rearLeft.set(wheelSpeeds[1], syncGroup);
	        frontRight.set(wheelSpeeds[2], syncGroup);
	        rearRight.set(wheelSpeeds[3], syncGroup);
		}
		*/
     
	}
    private static double[] rotateVector(double x, double y, double angle) {
        double cosA = Math.cos(angle * (3.14159 / 180.0));
        double sinA = Math.sin(angle * (3.14159 / 180.0));
        double out[] = new double[2];
        out[0] = x * cosA - y * sinA;
        out[1] = x * sinA + y * cosA;
        return out;
    }
    public void driveRobotOriented(double x, double y, double rotation){    	
    	drive(x ,y ,rotation , 0.0);
    }
	
    public void driveWithPID(double x, double y, double angle){
		currError = goalHeading - angle;//[-180 - 180] degrees
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
    	
    	rotation = RobotConstants.DRIVESYSTEM_kP * currError + RobotConstants.DRIVESYSTEM_kI * integration + RobotConstants.DRIVESYSTEM_kD * derivative;//[-1.0 - 1.0] percentage
    	rotation = (rotation < 0) ? Math.max(-1.0, rotation) : Math.min(1.0, rotation);
    	    	
    	//robot.drive(x, y, rotation, angle);
    	drive(x, y, rotation, angle);
    }
	
    public void drive(double left, double right){
    	//Speed Correction 
    	left *= speed;
    	right *= speed;
    	
    	robot.tankDrive(left, right);
    }
    
    public void stop(){
    	drive(0.0, 0.0, 0.0, 0.0);
    }
	
    public void setGoalHeading (double goalHeading) {
    	double moddedHeading = goalHeading%360.0;
    	if(moddedHeading > 180) {
    		moddedHeading -= 360;
    	}
    	this.goalHeading = moddedHeading;
			
    }

    public double getGoalHeading() {
	return goalHeading;
    }
    
    public void setSpeed(double speed){
	this.speed = speed;
    }
	
	
    //================ ENCODER METHODS =====================================
	
    public double getDistanceEncoder() {
	return frontLeft.getEncPosition();
    }
    
    public void setDistanceEncoder(double value) {
	frontLeft.setPosition(value);
    }
	
    public double getXSlideEncoder() {
	// Should return the slide encoder for the X axis
	// Returns the wheel encoders for now
	return frontLeft.getEncPosition();
    }

    public double getYSlideEncoder() {
	// Gets the slide encoder for the Y axis
	// Returns the wheel encoders for now
	return frontLeft.getEncPosition();
    }

    public void resetXSlideEncoder() {
	xSlideEncoder.reset();
    }
	
    public void resetYSlideEncoder() {
	ySlideEncoder.reset();
    }
	
	
	
	
    //================ CANTALON METHODS =======================================
	
    public double getLeftFrontCurrent(){
	return frontLeft.getOutputCurrent();
    }
    
    public double getLeftRearCurrent(){
	return rearLeft.getOutputCurrent();
    }
    
    public double getRightFrontCurrent(){
	return frontRight.getOutputCurrent();
    }
    
    public double getRightRearCurrent(){
	return rearRight.getOutputCurrent();
    }
}
