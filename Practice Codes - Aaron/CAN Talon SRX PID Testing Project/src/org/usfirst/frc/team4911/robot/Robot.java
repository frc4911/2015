package org.usfirst.frc.team4911.robot;

import java.io.File;
import java.util.Arrays;
import java.io.PrintStream;
import java.io.PrintWriter;

import com.kauailabs.nav6.frc.IMUAdvanced;

import edu.wpi.first.wpilibj.buttons.JoystickButton;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.SerialPort;
import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.SampleRobot;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.I2C;
import edu.wpi.first.wpilibj.CameraServer;
import ExternalLibs.LIDAR;


public class Robot extends SampleRobot {
	Joystick stick1;
	Joystick stick2;
	Joystick opStick;
	
	JoystickButton button1;
	JoystickButton button2;
	JoystickButton button3;
	JoystickButton button4;
	JoystickButton button5;
	JoystickButton button6;
	JoystickButton button7;
	JoystickButton button8;
	JoystickButton button9;
	JoystickButton button10;
	JoystickButton button11;
	JoystickButton button12;
	CANTalon leftFront3;
	CANTalon leftRear4;
	CANTalon rightFront7;
	CANTalon rightRear8;
	PrintStream output;
	RobotDrive robot;
	IMUAdvanced imu;
	SerialPort serial_port;
	LIDAR lidar;
	CameraServer server;

	private double rotation;
	private double currError;
	private double lastError;
	private double integration;
	private double derivative;

	private double goalHeading;
	
	public Robot() {
		stick1 = new Joystick(0);
		stick2 = new Joystick(1);

		button1 = new JoystickButton(stick1, 1);
		button2 = new JoystickButton(stick1, 2);
		button3 = new JoystickButton(stick1, 3);
		button4 = new JoystickButton(stick1, 4);
		button5 = new JoystickButton(stick1, 5);
		button6 = new JoystickButton(stick1, 6);
		button7 = new JoystickButton(stick1, 7);
		button8 = new JoystickButton(stick1, 8);
		button9 = new JoystickButton(stick1, 9);
		button10 = new JoystickButton(stick1, 10);
		button11 = new JoystickButton(stick1, 11);
		button12 = new JoystickButton(stick1, 12);
		
		//2	  
		leftFront3 = new CANTalon(3); // Initialize the CanTalonSRX on device 1.
		leftFront3.changeControlMode(CANTalon.ControlMode.PercentVbus);
		leftFront3.setPID(1.0, 0.0, 0.0);
		//1  
		leftRear4 = new CANTalon(4); // Initialize the CanTalonSRX on device 1.
		leftRear4.changeControlMode(CANTalon.ControlMode.PercentVbus);
		//3  
		rightFront7 = new CANTalon(7); // Initialize the CanTalonSRX on device 1.
		rightFront7.changeControlMode(CANTalon.ControlMode.PercentVbus);
		//4  
		rightRear8 = new CANTalon(8); // Initialize the CanTalonSRX on device 1.
		rightRear8.changeControlMode(CANTalon.ControlMode.PercentVbus);
		
		robot = new RobotDrive(leftFront3, leftRear4, rightFront7, rightRear8);
		//robot = new RobotDrive(leftRear4, leftFront3, rightRear8, leftRear4);
		
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
			output = new PrintStream(new File("/home/lvuser/natinst/teleLog.txt"));
			System.setOut(output);
		} catch (Exception e){
			
		}
		
		while(isOperatorControl() && isEnabled()){
			/*
			//CONTAINER CONTAINER TEST CODE
			if(opStick.getRawButton(1)) {
				if(!atLowSpeed) {
					if(rightFront.getOutputCurrent() > 7.5) {
						rightFront.set(0.1);
						atLowSpeed = true;
					}
					else {
						rightFront.set(0.5);
					}
				}
				else {
					if(rightFront.getOutputCurrent() < 0.15) {
						rightFront.set(0.5);
						atLowSpeed = false;
					}
					else {
						rightFront.set(0.1);
					}
				}
			}
			else if(opStick.getRawButton(3)) {
				rightFront.set(-0.3);
			}
			System.out.println("--------------------------------");
			System.out.println("Encoder: " + rightFront.getEncPosition());
			System.out.println("Current: " + rightFront.getOutputCurrent());
			System.out.println("Low speed?: " + atLowSpeed);
			*/
			/*if(button9.get()){
				//Forward
				leftFront.set(0.5);
				leftRear.set(-0.5);
				rightFront.set(0.5);
				rightRear.set(-0.5);
			}
			if(button10.get()){
				//Backwards
				leftFront.set(-0.5);
				leftRear.set(0.5);
				rightFront.set(-0.5);
				rightRear.set(0.5);				
			}
			else if(button10.get()){
				//Left
				leftFront3.set(-0.3);
				leftRear4.set(0.3);
				rightFront7.set(-0.3);
				rightRear8.set(0.3);
				
			}
			else if(button11.get()){
				//Forward
				leftFront3.set(0.3);
				leftRear4.set(0.3);
				rightFront7.set(-0.3);
				rightRear8.set(-0.3);
			}
			else if(button12.get()){
				//Backward				
				leftFront3.set(-0.3);
				leftRear4.set(-0.3);
				rightFront7.set(0.3);
				rightRear8.set(0.3);
				
			} else {
				/*
				double inX = Math.pow(stick1.getX(), 3);
				double inY = Math.pow(stick1.getY(), 3);
				double b = 0.0;//Low Input Gain Adjustment
				double a = 0.5;//Inverse Dead Band
				*/
				/*
				if(inX == 0.0){
					inX = 0.0;
				} else if(inX > 0.0){
					inX = b + (1.0 - b) * (a * Math.pow(inX, 3) + (1 - a) * inX);
				} else {
					inX = -b + (1.0 - b) * (a * Math.pow(inX, 3) + (1 - a) * inX);
				}
				
				if(inY == 0.0){
					inY = 0.0;
				} else if(inY > 0.0){
					inY = b + (1.0 - b) * (a * Math.pow(inY, 3) + (1 - a) * inY);
				} else {
					inY = -b + (1.0 - b) * (a * Math.pow(inY, 3) + (1 - a) * inY);
				}
				*/
				double x = stick1.getX();
				double y = stick1.getY();
				System.out.println(x + "\t" + y);
				//driveWithPID(inX, inY);
				robot.mecanumDrive_Cartesian(x, y, 0.0, 0.0);
			}
			
			/*
			//IMU Accelerometer Testing
			iX %= x.length;
			iY %= y.length;
			x[iX] = imu.getWorldLinearAccelX() * 32.174f;
			y[iY] = imu.getWorldLinearAccelY() * 32.174f;
			
			float aveX = 0f;
			for(float i : x){
				aveX += i;
			}
			aveX = aveX / (float)x.length;

			float aveY = 0f;
			for(float i : y){
				aveY += i;
			}
			aveY = aveY / (float)y.length;

			
			robot.mecanumDrive_Cartesian(stick1.getX(), -stick1.getY(), 0.0, 0.0);
			System.out.println("Accel X:\t" + aveX + " ft/s^2");
			System.out.println("Accel Y:\t" + aveY +  "ft/s^2");
			System.out.println("===================================");
			*/
			/*
			//PID Control Mode Switch Tests
            if(button11.get()){
            	if(!prevPressed){
		        	if(rightFront.getControlMode().equals(CANTalon.ControlMode.Position)){
		         		rightFront.changeControlMode(CANTalon.ControlMode.PercentVbus);
		         		prevPressed = true;
		         		System.out.println("PERCENT V BUS");
		        	} else {
		         		rightFront.changeControlMode(CANTalon.ControlMode.Position);
		         		prevPressed = true;
		         		goal = rightFront.get();
		         		System.out.println("POSITION");
		        	}
            	}
            } else {
            	prevPressed = false;
            	if(rightFront.getControlMode().equals(CANTalon.ControlMode.Position)){
            		rightFront.set(goal);
                } else {
                	rightFront.set(stick1.getY());
                }
            }
            */
		}
		//output.close();
		
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
	
	public void autonomous(){
		while(isAutonomous() && isEnabled()) {
			//rightFront.setPosition(1024);
		}
	}
}
