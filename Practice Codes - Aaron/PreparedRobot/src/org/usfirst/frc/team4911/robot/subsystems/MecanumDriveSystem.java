package org.usfirst.frc.team4911.robot.subsystems;

import org.usfirst.frc.team4911.robot.Robot;

import org.usfirst.frc.team4911.robot.RobotMap;

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
	
	private IMUAdvanced imu = RobotMap.imu;		
	
	public MecanumDriveSystem(){
		super();
		robot.setInvertedMotor(RobotDrive.MotorType.kFrontLeft, false);
		robot.setInvertedMotor(RobotDrive.MotorType.kRearLeft, false);

		robot.setInvertedMotor(RobotDrive.MotorType.kFrontRight, true);
		robot.setInvertedMotor(RobotDrive.MotorType.kRearRight, true);
	}
	
	@Override
	protected void initDefaultCommand() {
		// TODO Auto-generated method stub

	}
	
	public void drive(double x, double y, double rotation){
		
		robot.mecanumDrive_Cartesian(x, y, rotation, (double)imu.getYaw());
		//robot.mecanumDrive_Cartesian(x, y, rotation, 0.0);
		//robot.mecanumDrive_Cartesian(0.0, 0.5, 0.0, 0.0);
	}
	public void drive(double left, double right){
		robot.tankDrive(left, right);
		
	}
	public void stop(){
		drive(0.0, 0.0, 0.0);
	}

}
