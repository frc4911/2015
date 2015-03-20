package org.usfirst.frc.team4911.robot;

import java.io.File;



import java.util.Arrays;
import java.io.PrintStream;
import java.io.PrintWriter;

import com.kauailabs.nav6.frc.IMUAdvanced;

import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.buttons.JoystickButton;
import edu.wpi.first.wpilibj.CANTalon.FeedbackDevice;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.RobotDrive.MotorType;
import edu.wpi.first.wpilibj.SerialPort;
import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.SampleRobot;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.I2C;
import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.smartdashboard.*;
import edu.wpi.first.wpilibj.Relay;
import ExternalLibs.LIDAR;


public class Robot extends SampleRobot {
	Joystick stick1;
	Joystick stick2;
	Joystick stick3;
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
	CANTalon liftMotor1;
	CANTalon liftMotor2;
	CANTalon containerContainer;
	CANTalon containerFollower;
	CANTalon containerLift;
	
	Relay grabber;

	CANTalon motor;
	
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
	
	private double clawSpeed;
	private int cycleNum;
	
	public Robot() {
		stick1 = new Joystick(0);
		/*stick2 = new Joystick(1);
		stick3 = new Joystick(2);
		
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
		button12 = new JoystickButton(stick1, 12);*/
		
		grabber = new Relay(1);

		motor = new CANTalon(3);
		motor.changeControlMode(CANTalon.ControlMode.PercentVbus);
		motor.setFeedbackDevice(FeedbackDevice.QuadEncoder);
		
		/*
		//2	  
		leftFront3 = new CANTalon(3); // Initialize the CanTalonSRX on device 1.
		leftFront3.changeControlMode(CANTalon.ControlMode.PercentVbus);
		leftFront3.setPID(1.0, 0.0, 0.0);
		//leftFront3.ConfigFwdLimitSwitchNormallyOpen(true);
		//1  
		leftRear4 = new CANTalon(4); // Initialize the CanTalonSRX on device 1.
		leftRear4.changeControlMode(CANTalon.ControlMode.Follower);
		//3  
		rightFront7 = new CANTalon(7); // Initialize the CanTalonSRX on device 1.
		rightFront7.changeControlMode(CANTalon.ControlMode.PercentVbus);
		//4  
		rightRear8 = new CANTalon(8); // Initialize the CanTalonSRX on device 1.
		rightRear8.changeControlMode(CANTalon.ControlMode.PercentVbus);
		
		containerContainer = new CANTalon(6);
		containerContainer.changeControlMode(CANTalon.ControlMode.PercentVbus);
		containerContainer.setPID(1.0, 0.0, 0.0);
		
		containerFollower = new CANTalon(5);
		containerFollower.changeControlMode(CANTalon.ControlMode.PercentVbus);
		containerFollower.setPID(1.0, 0.0, 0.0);

		liftMotor1 = new CANTalon(1);
		liftMotor1.changeControlMode(CANTalon.ControlMode.PercentVbus);
		

		liftMotor2 = new CANTalon(2);
		liftMotor2.changeControlMode(CANTalon.ControlMode.PercentVbus);
		
		containerLift = new CANTalon(9);
		containerLift.changeControlMode(CANTalon.ControlMode.PercentVbus);
		containerLift.setFeedbackDevice(FeedbackDevice.QuadEncoder);
		
		
		robot = new RobotDrive(leftFront3, leftRear4, rightFront7, rightRear8);
		//robot.setInvertedMotor(MotorType.kRearLeft, true);
		//robot = new RobotDrive(leftRear4, leftFront3, rightRear8, leftRear4);
		
		//lidar = new LIDAR(I2C.Port.kMXP);
		//lidar.start();
		 */
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
	    while(isOperatorControl() && isEnabled()) {
		System.out.println(grabber.get());
		if(stick1.getRawButton(2)) {
		    grabber.setDirection(Relay.Direction.kReverse);
		}
		else if(stick1.getRawButton(4)) {
		    grabber.set(Relay.Value.kForward);
		}
		else {
		    grabber.set(Relay.Value.kOff);
		}
	    }
		/*try {
			output = new PrintStream(new File("/home/lvuser/natinst/teleLog.txt"));
			System.setOut(output);
		} catch (Exception e){
			
		}
		
		motor.setPosition(0.0);
		
		motor.set(-1.0);
		Timer.delay(1.0);
		/*
		while(isOperatorControl() && isEnabled()){
			motor.set(stick3.getY());
			
			System.out.println("===========================================");
			System.out.println("GET:\t" + motor.get());
			System.out.println("POS:\t" + motor.getPosition());
			System.out.println("ENC POSITION:\t" + motor.getEncPosition());
			System.out.println("VELOCITY:\t" + motor.getEncVelocity());
			System.out.println("SPEED:\t" + motor.getSpeed());
			
		}*/
		/*System.out.println("===========================================");
		System.out.println("START");
		
		double startTime = Timer.getFPGATimestamp();
		double timeElapsed = 0.0;
		while(timeElapsed <= 10.0){
			motor.set(-1.0);
			System.out.println("===========================================");
			System.out.println("POS:\t" + motor.getPosition());
			timeElapsed = Timer.getFPGATimestamp() - startTime;
		}

		System.out.println("===========================================");
		System.out.println("POS:\t" + motor.getPosition());
		motor.set(0.0);
		output.close();*/
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
		
		try {
			output = new PrintStream(new File("/home/lvuser/natinst/autoLog.txt"));
			System.setOut(output);
		} catch (Exception e){
			
		}
		
		motor.setPosition(0.0);
		while(isAutonomous() && isEnabled()) {
			System.out.println("===========================================");
			System.out.println("POS:\t" + motor.getPosition());
		}
		output.close();
	}
}
