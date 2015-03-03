package org.usfirst.frc.team4911.robot.subsystems;

import org.usfirst.frc.team4911.robot.Robot;

import org.usfirst.frc.team4911.robot.RobotConstants;
import org.usfirst.frc.team4911.robot.RobotMap;
import org.usfirst.frc.team4911.robot.subsystems.*;

import java.math.*;

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
	
	public MecanumDriveSystem(){
		super();
		robot.setInvertedMotor(RobotDrive.MotorType.kFrontLeft, false);
		robot.setInvertedMotor(RobotDrive.MotorType.kRearLeft, false);

		robot.setInvertedMotor(RobotDrive.MotorType.kFrontRight, true);
		robot.setInvertedMotor(RobotDrive.MotorType.kRearRight, true);
		
		goalHeading = 0.0;
		speed = 1.0;
	}
	
	@Override
	protected void initDefaultCommand() {

	}
	
	//================ DRIVE CONTROL METHODS ==========================	
	public void driveRobotOriented(double x, double y, double rotation){
		//Speed Correction 
    	x *= speed;
    	y *= speed;
    	rotation *= speed;
    	
		robot.mecanumDrive_Cartesian(x ,y ,rotation , 0.0);
	}
	
	public void drive(double x, double y, double rotation){
		//Speed Correction 
    	x *= speed;
    	y *= speed;
    	rotation *= speed;
		robot.mecanumDrive_Cartesian(x, y, rotation, (double)imu.getYaw());
	}
	
	public void driveWithPID(double x, double y){
		currError = goalHeading - Robot.sensorSystem.getYawWithCompensation();//[-180 - 180] degrees
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
    	rotation = (rotation < 0) ? Math.max(-0.75, rotation) : Math.min(0.75, rotation);
    	    	
    	drive(x, y, rotation);
	}
	
	public void drive(double left, double right){
		//Speed Correction 
    	left *= speed;
    	right *= speed;
    	
		robot.tankDrive(left, right);
	}
	public void stop(){
		drive(0.0, 0.0, 0.0);
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
