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
	
	RobotDrive robot;

	JoystickButton button9;
	JoystickButton button10;
	JoystickButton button11;
	JoystickButton button12;
	double goal;
	
	CANTalon leftFront;
	CANTalon leftRear;
	CANTalon rightFront;
	CANTalon rightRear;
	
	CANTalon extra1;
	CANTalon extra2;
	
	PrintStream output;
	
	public static IMUAdvanced imu;
	public static SerialPort serial_port;
	
	LIDAR lidar;
	
	double kP;
	double kI;
	double kD;
	
	boolean prevPressed;
	
	CameraServer server;
	
	public Robot() {
		stick1 = new Joystick(0);
		stick2 = new Joystick(1);

		button9 = new JoystickButton(stick1, 9);
		button10 = new JoystickButton(stick1, 10);
		button11 = new JoystickButton(stick1, 11);
		button12 = new JoystickButton(stick1, 12);
		
		goal = 0.0;
		prevPressed = false;
		
		kP = 1.1;
		kI = 0.00001;//0.0002;
		kD = 0.0;//5.0;
		
		extra1 = new CANTalon(1); // Initialize the CanTalonSRX on device 1.
		extra1.setFeedbackDevice(CANTalon.FeedbackDevice.QuadEncoder);
		extra1.changeControlMode(CANTalon.ControlMode.PercentVbus);
		
		extra2 = new CANTalon(2); // Initialize the CanTalonSRX on device 1.
		extra2.setFeedbackDevice(CANTalon.FeedbackDevice.QuadEncoder);
		extra2.changeControlMode(CANTalon.ControlMode.PercentVbus);
		
		//2	  
		leftFront = new CANTalon(3); // Initialize the CanTalonSRX on device 1.
		leftFront.setFeedbackDevice(CANTalon.FeedbackDevice.QuadEncoder);
		leftFront.changeControlMode(CANTalon.ControlMode.PercentVbus);
		leftFront.setPID(1.0, 0.0, 0.0);
		//1  
		leftRear = new CANTalon(4); // Initialize the CanTalonSRX on device 1.
		leftRear.setFeedbackDevice(CANTalon.FeedbackDevice.QuadEncoder);
		leftRear.changeControlMode(CANTalon.ControlMode.PercentVbus);
		leftRear.setPID(1.0, 0.0, 0.0);
		//3  
		rightFront = new CANTalon(7); // Initialize the CanTalonSRX on device 1.
		rightFront.setFeedbackDevice(CANTalon.FeedbackDevice.QuadEncoder);
		rightFront.changeControlMode(CANTalon.ControlMode.PercentVbus);
		rightFront.setPID(kP, kI, kD);
		//4  
		rightRear = new CANTalon(8); // Initialize the CanTalonSRX on device 1.
		rightRear.setFeedbackDevice(CANTalon.FeedbackDevice.QuadEncoder);
		rightRear.changeControlMode(CANTalon.ControlMode.PercentVbus);
		rightRear.setPID(1.0, 0.0, 0.0);
		
		robot = new RobotDrive(leftFront, leftRear, rightFront, rightRear);
		
		lidar = new LIDAR(I2C.Port.kMXP);
		lidar.start();
		/*
		server = CameraServer.getInstance();
		server.setQuality(50);
		//server.startAutomaticCapture("cam1");
		*/
		/***************************************
		 *
	     * IMU INITIALIZATION
	     ***************************************/
		try {
			serial_port = new SerialPort(57600,SerialPort.Port.kOnboard);
		          
			// You can add a second parameter to modify the 
			// update rate (in hz) from 4 to 100.  The default is 100.
			// If you need to minimize CPU load, you can set it to a
			// lower value, as shown here, depending upon your needs.
		  
			// You can also use the IMUAdvanced class for advanced
			// features.
		
			byte update_rate_hz = 20;
			// roneckor - dead code?
			//imu = new IMU(serial_port,update_rate_hz);
			imu = new IMUAdvanced(serial_port,update_rate_hz);
		} catch (Exception ex) {
			System.out.println("Probs be happen\'n");
			ex.printStackTrace();
			
		}
		imu.initIMU();
		Timer.delay(0.3);
	}
	/*
	float[] x;
	float[] y;
	int iX;
	int iY;
	*/
	public void operatorControl() {
		/*
		x = new float[100];
		y = new float[100];
		//Arrays.fill(x, imu.getWorldLinearAccelX() * 32.174f);
		//Arrays.fill(y, imu.getWorldLinearAccelY() * 32.174f);
		iX = 0;
		iY = 0;
		*/
		try {
			output = new PrintStream(new File("/home/lvuser/natinst/teleLog.txt"));
			System.setOut(output);
		} catch (Exception e){
			
		}
		
		//server.startAutomaticCapture("cam2");
		while(isOperatorControl() && isEnabled()){
			if(button9.get()){
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
			if(button11.get()){
				//Left
				leftFront.set(0.5);
				leftRear.set(0.5);
				rightFront.set(-0.5);
				rightRear.set(-0.5);
				
			}
			if(button12.get()){
				//extra1.set(0.5);
				//extra2.set(0.5);
				
				//Right
				leftFront.set(-0.5);
				leftRear.set(-0.5);
				rightFront.set(0.5);
				rightRear.set(0.5);
				
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
			/*if(stick1.getRawButton(3)) {
				rightFront.set(1024);
				System.out.println("Position 1!");
		
			}
			else if(stick1.getRawButton(4)) {
				rightFront.set(0);
				System.out.println("Position 0!");
			}
			
			if(stick1.getRawButton(1)) {
				kI += .00001;
			}
			else if (stick1.getRawButton(2)) {
				kI -= .00001;
			}
			rightFront.setPID(kP, kI, kD);*/
		}
		output.close();
		
	}  
	
	public void autonomous(){
		while(isAutonomous() && isEnabled()) {
			rightFront.setPosition(1024);
		}
	}
}
