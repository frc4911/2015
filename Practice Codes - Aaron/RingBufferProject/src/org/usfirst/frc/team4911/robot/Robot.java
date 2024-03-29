package org.usfirst.frc.team4911.robot;


import java.io.File;

import java.io.PrintStream;
import java.util.Arrays;

import com.kauailabs.nav6.frc.IMUAdvanced;

import edu.wpi.first.wpilibj.SerialPort;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.SampleRobot;
import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.buttons.JoystickButton;

public class Robot extends SampleRobot {
	Joystick joystick;
	JoystickButton button;
	CANTalon leftFront3;
	CANTalon leftRear4;
	CANTalon rightFront7;
	CANTalon rightRear8;
	RobotDrive robot;	
	DriverInputBuffer buffer;
	PrintStream output;
	IMUAdvanced imu;
	SerialPort serial_port;
	

	public Robot() {
		joystick = new Joystick(0);
		button = new JoystickButton(joystick, 1);
		leftFront3 = new CANTalon(3);
		leftFront3.changeControlMode(CANTalon.ControlMode.PercentVbus);
		leftRear4 = new CANTalon(4);
		leftRear4.changeControlMode(CANTalon.ControlMode.PercentVbus);
		rightFront7 = new CANTalon(7);
		rightFront7.changeControlMode(CANTalon.ControlMode.PercentVbus);
		rightRear8 = new CANTalon(8);
		rightRear8.changeControlMode(CANTalon.ControlMode.PercentVbus);
		robot = new RobotDrive(leftFront3, leftRear4, rightFront7, rightRear8);
		robot.setInvertedMotor(RobotDrive.MotorType.kFrontLeft, false);
		robot.setInvertedMotor(RobotDrive.MotorType.kRearLeft, false);
		robot.setInvertedMotor(RobotDrive.MotorType.kFrontRight, true);
		robot.setInvertedMotor(RobotDrive.MotorType.kRearRight, true);
		
		
		buffer = new DriverInputBuffer(80);
		
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
	
	public void operatorControl() {try {
		output = new PrintStream(new File("/home/lvuser/natinst/teleLog.txt"));
			System.setOut(output);
		} catch (Exception e){

		}
		imu.zeroYaw();
	
		while (isOperatorControl() && isEnabled()) {
			double x = getX();
			double y = getY();
			double yaw = imu.getYaw();
			double rotation = 0.0;
			double magnitude = Math.sqrt( 
					Math.pow(x, 2) + Math.pow(y, 2) ) 
					/ Math.sqrt(2.0);
			double rawDirection = Math.atan2(y, x) * 180.0 / Math.PI + 90.0;
			double processedDirection = rawDirection - yaw;
			if(button.get()){
				rotation = joystick.getZ() * 0.25;
				buffer.add(magnitude, processedDirection, rotation);
				double[] processedInputs = buffer.getData();			
				robot.mecanumDrive_Polar(magnitude, processedDirection, rotation);
				goalHeading = yaw;
			} else {
				buffer.add(magnitude, processedDirection, rotation);
				double[] processedInputs = buffer.getData();			
				driveWithPID(magnitude, processedDirection);
			}
			
			/*
			System.out.println("X\t" + x);
			System.out.println("Y\t" + y);
			System.out.println("Z\t" + rotation);
			System.out.println("Magnitude:\t" + magnitude);
			System.out.println("Raw Direction:\t" + rawDirection);
			System.out.println("Processed Direction:\t" + processedDirection);
			System.out.println("Rotation:\t" + rotation);
			System.out.println("Yaw:\t" + yaw);
			*/
		}
		output.close();
	}
	
	private double getX(){
		double pow = 0.0;
		double sensitivity = 10.0;
		double joyX = joystick.getX();
    	if(Math.abs(joyX) >= 0.1) {
            pow = Math.round(joyX * sensitivity) 
            		/ sensitivity;  
        }
    	return pow;
	}
	private double getY(){
		double pow = 0.0;
		double sensitivity = 10.0;
		double joyY = joystick.getY();
    	if(Math.abs(joyY) >= 0.1) {
            pow = Math.round(joyY * sensitivity) 
            		/ sensitivity;  
        }
    	return pow;
	}
	

	private double currError;
	private double lastError;
	private double integration;
	private double derivative;
	
	private double goalHeading;
	
	
	
	public void driveWithPID(double magnitude, double direction){
		double rotation = 0.0;
		currError = goalHeading - imu.getYaw();//[-180 - 180] degrees
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
    	
    	double kP = 1.0 / 100.0;
    	double kI = 0.0;
    	double kD = 0.005;
    	
    	rotation = kP * currError + kI * integration + kD * derivative;//[-1.0 - 1.0] percentage
    	rotation = (rotation < 0) ? Math.max(-0.75, rotation) : Math.min(0.75, rotation);

		System.out.println("========================================================");
		System.out.println("Magnitude:\t" + magnitude);
		System.out.println("Direction:\t" + direction);
		System.out.println("Rotation:\t" + rotation);
		robot.mecanumDrive_Polar(magnitude, direction, rotation);
	}
	
	
	
	public void autonomous(){
		while(isAutonomous()){
			robot.mecanumDrive_Polar(0.25, 0.0, 0.0);
		}
	}
	
	
	
	private class DriverInputBuffer{
		private double[] magnitude;
		private double[] direction;
		private double[] rotation;
		private int size;
		private int currIndex;
		
		public DriverInputBuffer(int size){
			this.currIndex = 0;
			this.size = size;
			this.magnitude = new double[size];
			this.direction = new double[size];
			this.rotation = new double[size];
		}
		
		public void add(double magnitude, double direction, double rotation){
			this.magnitude[currIndex] = magnitude;
			this.direction[currIndex] = direction;
			this.rotation[currIndex] = rotation;
			currIndex++;
			currIndex %= size;
 		}
		public double[] getData(){
			double[] average = new double[3];
			for(int i = 0; i < size; i++){
				average[0] += magnitude[i];
				average[1] += direction[i];
				average[2] += rotation[i];
			}
			/*
			System.out.println("========================================================");
			System.out.println("Magnitude:\t" + Arrays.toString(magnitude));
			System.out.println("Direction:\t" + Arrays.toString(direction));
			System.out.println("Rotation:\t" + Arrays.toString(rotation));
			*/
			average[0] /= (double)size;
			average[1] /= (double)size;
			average[2] /= (double)size;
			return average;
		}
		
	}
}
