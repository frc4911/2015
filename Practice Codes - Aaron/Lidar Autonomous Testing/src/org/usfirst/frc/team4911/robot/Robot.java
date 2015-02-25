package org.usfirst.frc.team4911.robot;


import java.io.File;
import java.io.PrintStream;

import com.kauailabs.nav6.frc.IMUAdvanced;

import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.JoystickButton;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.SampleRobot;
import edu.wpi.first.wpilibj.I2C;
import edu.wpi.first.wpilibj.SerialPort;
import edu.wpi.first.wpilibj.Timer;
import ExternalLibs.LIDAR;


public class Robot extends SampleRobot {
	Joystick stick;
	JoystickButton button1;
	
	CANTalon leftFront3;
	CANTalon leftRear4;
	CANTalon rightFront7;
	CANTalon rightRear8;
	RobotDrive robot;

	LIDAR lidar;
	PrintStream output;
	IMUAdvanced imu;
	SerialPort serial_port;
	

	private double rotation;
	private double currError;
	private double lastError;
	private double integration;
	private double derivative;

	private double goalHeading;
	
	
	public Robot() {
		stick = new Joystick(0);
		button1 = new JoystickButton(stick, 1);
		
		leftFront3 = new CANTalon(3);
		leftFront3.changeControlMode(CANTalon.ControlMode.PercentVbus);
		leftRear4 = new CANTalon(4);
		leftRear4.changeControlMode(CANTalon.ControlMode.PercentVbus);
		rightFront7 = new CANTalon(7);
		rightFront7.changeControlMode(CANTalon.ControlMode.PercentVbus);
		rightRear8 = new CANTalon(8);
		rightRear8.changeControlMode(CANTalon.ControlMode.PercentVbus);
		
		robot = new RobotDrive(leftFront3, leftRear4, rightFront7, rightRear8);
		robot.setInvertedMotor(RobotDrive.MotorType.kFrontRight, true);
		robot.setInvertedMotor(RobotDrive.MotorType.kRearRight, true);
		lidar = new LIDAR(I2C.Port.kMXP);
		lidar.start();
		
		/***************************************
		 *
	     * IMU INITIALIZATION
	     ***************************************/
		try {
			serial_port = new SerialPort(57600,SerialPort.Port.kOnboard);
		
			byte update_rate_hz = 20;
			imu = new IMUAdvanced(serial_port,update_rate_hz);
		} catch (Exception ex) {
			System.out.println("Probs be happen\'n");
			ex.printStackTrace();
			
		}
		imu.initIMU();
		Timer.delay(0.3);		
	}

	public void operatorControl() {
		try {
			output = new PrintStream(new File("/home/lvuser/natinst/TeleLog.txt"));
			System.setOut(output);
		} catch (Exception e){
		}
		imu.zeroYaw();
		goalHeading = 0.0;
		
		while (isOperatorControl() && isEnabled()) {
			System.out.println("LIDAR:\t" + lidar.getDistance() / 2.54);
			
			//Modify Joystick Inputs
			double x = modifyInput(stick.getX());
			double y = modifyInput(stick.getY());
			double z = modifyInput(stick.getZ());
			
			if(button1.get()){
				goalHeading += 2.0 * z;
				//Passing 180 Mark Problem
				if(goalHeading >= 180.0){
					goalHeading -= 360.0;
				} else if(goalHeading <= -180.0){
					goalHeading += 360.0;
				}
			} 
			driveWithPID(x, y);
		}

		leftFront3.set(0.0);
		leftRear4.set(0.0);
		rightFront7.set(0.0);
		rightRear8.set(0.0);
		
		output.close();
	}
	
	public void autonomous(){
		try {
			output = new PrintStream(new File("/home/lvuser/natinst/AutoLog.txt"));
			System.setOut(output);
		} catch (Exception e){
		}
		
		imu.zeroYaw();
		goalHeading = 0.0;
		double prevVal = 0.0;
		double curVal = 0.0;
		boolean firstIteration = false;
		
		//DRIVING SIDE TO THE TOTE
		while(isAutonomous() && isEnabled()){
			curVal = lidar.getDistance() / 2.54;
			System.out.println("=================================================");
			System.out.println("LIDAR:\t" + curVal + " Inches");
			double delta = prevVal - curVal;
			System.out.println("DELTA:\t" + delta);
			driveWithPID(-0.3, 0.0);
			
			
			if(delta < -40.0){
				if(!firstIteration){
					firstIteration = true;
				} else {
					System.out.println("++++++++++++++++++++++++++I SEE IT+++++++++++++++++++++++++++++++++++++++++++++");
					driveWithPID(0.3, 0.0);
					Timer.delay(0.5);
					driveWithPID(0.0,0.0);
					break;
				}
			}
			prevVal = curVal;
		}
		leftFront3.set(0.0);
		leftRear4.set(0.0);
		rightFront7.set(0.0);
		rightRear8.set(0.0);
		
		double distanceIn = lidar.getDistance() / 2.54;
		double offset = 5.0;
		//APPROACH THE TOTE
		while(isAutonomous() && isEnabled()){
			curVal = lidar.getDistance() / 2.54;
			if(curVal < 6.0){
				break;
			}
			driveWithPID(0.0, 0.3);
		}
		
		
		//APPROACH THE TOTE
		while(isAutonomous() && isEnabled()){
			curVal = lidar.getDistance() / 2.54;
			if(curVal > distanceIn){
				break;
			}
			driveWithPID(0.0, -0.3);
		}
		
		output.close();
	}
	public void driveWithPID(double x, double y){
		double currYaw = imu.getYaw();
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
    	
    	double kP = 1.0 / 175.0;
    	double kI = 0.0;
    	double kD = 0.005;
    	
    	rotation = kP * currError + kI * integration + kD * derivative;//[-1.0 - 1.0] percentage
    	rotation = (rotation < 0) ? Math.max(-0.5, rotation) : Math.min(0.5, rotation);
    	robot.mecanumDrive_Cartesian(x, y, rotation, currYaw);
	}
	private double modifyInput(double val){
		double pow = 0.0;
		double sensitivity = 10.0;
    	if(Math.abs(val) >= 0.1) {
            pow = Math.round(val * sensitivity) 
            		/ sensitivity;  
        }
    	return pow;
	}
}
