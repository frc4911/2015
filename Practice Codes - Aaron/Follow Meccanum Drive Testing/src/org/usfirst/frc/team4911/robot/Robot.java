package org.usfirst.frc.team4911.robot;


import java.io.File;
import java.io.PrintStream;
import java.util.Arrays;

import com.kauailabs.nav6.frc.IMUAdvanced;

import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.JoystickButton;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.SampleRobot;
import edu.wpi.first.wpilibj.SerialPort;
import edu.wpi.first.wpilibj.Timer;


public class Robot extends SampleRobot {
	Joystick stick;
	JoystickButton button1;
	
	CANTalon leftFront3;
	CANTalon leftRear4;
	CANTalon rightFront7;
	CANTalon rightRear8;

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
		leftFront3.reverseOutput(false);
		leftRear4 = new CANTalon(4);
		leftRear4.changeControlMode(CANTalon.ControlMode.Follower);
		leftRear4.reverseOutput(true);
		rightFront7 = new CANTalon(7);
		rightFront7.changeControlMode(CANTalon.ControlMode.PercentVbus);
		rightFront7.reverseOutput(false);
		rightRear8 = new CANTalon(8);
		rightRear8.changeControlMode(CANTalon.ControlMode.Follower);
		rightRear8.reverseOutput(true);
		
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
		goalHeading = 0.0;
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
			//Modify Joystick Inputs
			double x = modifyInput(stick.getX());
			double y = modifyInput(stick.getY());
			double z = modifyInput(stick.getZ());
			double angle = imu.getYaw();
			
			
			if(button1.get()){
				drive(x, y, z, angle);
				goalHeading = angle;
			} else {
				driveWithPID(x, y, angle);
			}
			
			/*
			if(!button1.get()){
				z = 0.0;
			}
			drive(x, y, z, angle);
			*/
		}

		drive(0.0, 0.0, 0.0, 0.0);		
		output.close();
	}
	public void drive(double x, double y, double rotation, double angle){
		
		double[] wheelSpeeds = new double[4];
		byte syncGroup = 0;
		        
		y = -y;
		
		// Compenstate for gyro angle.
        double rotated[] = rotateVector(x, y, angle);
        x = rotated[0];
        y = rotated[1];
		
		if(rotation == 0.0){
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
	        
			System.out.println("======================================");
			System.out.println("X:\t" + x);
			System.out.println("Y:\t" + y);
			System.out.println("R:\t" + rotation);
			System.out.println("A:\t" + angle);
			System.out.println("[ " + wheelSpeeds[0] + " ,  , " + wheelSpeeds[2] + " ,  ]");
			
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
	        
			System.out.println("======================================");
			System.out.println("X:\t" + x);
			System.out.println("Y:\t" + y);
			System.out.println("R:\t" + rotation);
			System.out.println("A:\t" + angle);
			System.out.println(Arrays.toString(wheelSpeeds));   
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
    	
    	rotation = kP * currError + kI * integration + kD * derivative;//[-1.0 - 1.0] percentage
    	rotation = (rotation < 0) ? Math.max(-1.0, rotation) : Math.min(1.0, rotation);
    	drive(x, y, rotation, currYaw);
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
	

	private static double[] rotateVector(double x, double y, double angle) {
        double cosA = Math.cos(angle * (3.14159 / 180.0));
        double sinA = Math.sin(angle * (3.14159 / 180.0));
        double out[] = new double[2];
        out[0] = x * cosA - y * sinA;
        out[1] = x * sinA + y * cosA;
        return out;
    }

}