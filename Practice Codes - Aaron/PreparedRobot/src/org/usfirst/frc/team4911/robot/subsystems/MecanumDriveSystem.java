package org.usfirst.frc.team4911.robot.subsystems;

import org.usfirst.frc.team4911.robot.Robot;

import org.usfirst.frc.team4911.robot.RobotConstants;
import org.usfirst.frc.team4911.robot.RobotMap;
import org.usfirst.frc.team4911.robot.subsystems.*;

import java.math.*;

import com.kauailabs.nav6.frc.IMUAdvanced;

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
	private SerialPort serial_port;	
	private PrintSystem printSystem;
	
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
		printSystem = Robot.printSystem;
		speed = 1.0;
	}
	
	@Override
	protected void initDefaultCommand() {

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
    	rotation = (rotation < 0) ? Math.max(-0.5, rotation) : Math.min(0.5, rotation);
    	
    	//Speed Correction 
    	x *= speed;
    	y *= speed;
    	
    	drive(x, y, rotation);
    	printSystem.print("Drive X", x);
    	printSystem.print("Drive Y", y);
    	printSystem.print("Drive Rotation", rotation);
    	printSystem.print("Speed Constant", speed);
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
