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
	SerialPort serial_port;	
	
	private double rotation;
	private double currError;
	private double lastError;
	private double integration;
	private double derivative;
	
	private double goalHeading;
	
	private IMUAdvanced imu = RobotMap.imu;		
	
	public MecanumDriveSystem(){
		super();
		robot.setInvertedMotor(RobotDrive.MotorType.kFrontLeft, false);
		robot.setInvertedMotor(RobotDrive.MotorType.kRearLeft, false);

		robot.setInvertedMotor(RobotDrive.MotorType.kFrontRight, true);
		robot.setInvertedMotor(RobotDrive.MotorType.kRearRight, true);
		
		goalHeading = 0.0;
	}
	
	@Override
	protected void initDefaultCommand() {
		// TODO Auto-generated method stub

	}
	
	public void drive(double x, double y, double rotation){
		
		robot.mecanumDrive_Cartesian(x, y, rotation, (double)imu.getYaw());
		Robot.printSystem.print("X:", "" + x);
		Robot.printSystem.print("Y:", "" + y);
		Robot.printSystem.print("Rotation:", "" + rotation);
		//robot.mecanumDrive_Cartesian(x, y, rotation, 0.0);
		//robot.mecanumDrive_Cartesian(0.0, 0.5, 0.0, 0.0);
	}
	
	public void driveWithPID(double x, double y){
		currError = goalHeading - Robot.sensorSystem.getYaw();//[-180 - 180] degrees
		if(Math.abs(currError) > 180) {
			double delta = 360-Math.abs(currError);
			if(currError > 0) {
				delta*= -1;
			}
			currError = delta;
		}
    	integration += currError;//[0 - 0.5] seconds
    	derivative = lastError - currError;// NO USE
    	lastError = currError;
    	if ( currError * lastError <0){
    		integration = 0.0;
    	}
    	rotation = RobotConstants.kP * currError + RobotConstants.kI * integration + RobotConstants.kD * derivative;//[-1.0 - 1.0] percentage
    	rotation = (rotation < 0) ? Math.max(-0.5, rotation) : Math.min(0.5, rotation);
    	drive(x, y, rotation);
		
    	Robot.printSystem.print("Nav6Val", "" + Robot.sensorSystem.getYaw());
    	Robot.printSystem.print("Integration", "" + RobotConstants.kI * integration);
    	Robot.printSystem.print("Proportion", "" + RobotConstants.kP * currError);
    	Robot.printSystem.print("Derivative", "" + RobotConstants.kD * derivative);
	}
	public void drive(double left, double right){
		robot.tankDrive(left, right);
		
	}
	public void stop(){
		drive(0.0, 0.0, 0.0);
	}
	public void setGoalHeading (double goalHeading) {
		this.goalHeading = goalHeading;
	}
	public double getGoalHeading() {
		return goalHeading;
	}

}
