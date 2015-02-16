package org.usfirst.frc.team4911.robot;


import java.io.File;
import java.text.DecimalFormat;
import java.io.PrintStream;

import com.kauailabs.nav6.frc.IMUAdvanced;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.SerialPort;
import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.SampleRobot;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.buttons.JoystickButton;


public class Robot extends SampleRobot {
	Joystick joystick;
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
	RobotDrive robot;	
	
	RingBuffer accelXBuffer;
	RingBuffer accelYBuffer;
	
	RingBuffer velXBuffer;
	RingBuffer velYBuffer;
	
	RingBuffer posXBuffer;
	RingBuffer posYBuffer;
	
	
	PrintStream output;
	IMUAdvanced imu;
	SerialPort serial_port;

	public Robot() {
		joystick = new Joystick(0);
		button1 = new JoystickButton(joystick, 1);
		button2 = new JoystickButton(joystick, 2);
		button3 = new JoystickButton(joystick, 3);
		button4 = new JoystickButton(joystick, 4);
		button5 = new JoystickButton(joystick, 5);
		button6 = new JoystickButton(joystick, 6);
		button7 = new JoystickButton(joystick, 7);
		button8 = new JoystickButton(joystick, 8);
		button9 = new JoystickButton(joystick, 9);
		button10 = new JoystickButton(joystick, 10);
		button11 = new JoystickButton(joystick, 11);
		button12 = new JoystickButton(joystick, 12);
		
		
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
		
			
		accelXBuffer = new RingBuffer(100);
		accelYBuffer = new RingBuffer(100);
		
		velXBuffer = new RingBuffer(10);
		velYBuffer = new RingBuffer(10);
		
		posXBuffer = new RingBuffer(1);
		posYBuffer = new RingBuffer(1);
		
		/***************************************
		 *
		 * IMU INITIALIZATION
		 ***************************************/
		try {
			serial_port = new SerialPort(57600,SerialPort.Port.kUSB);
	
			byte update_rate_hz = 20;
			imu = new IMUAdvanced(serial_port,update_rate_hz);
		} catch (Exception ex) {
			System.out.println("Probs be happen\'n");
			ex.printStackTrace();
		}
		imu.initIMU();
		Timer.delay(0.3);
	}
	
	double prevTime;
	/**
	 * Runs the motor.
	 */
	public void operatorControl() {
		try {
			output = new PrintStream(new File("/home/lvuser/natinst/teleLog.txt"));
			System.setOut(output);
		} catch (Exception e){
		}
		imu.zeroYaw();
		goalHeading = 0.0;	
		
		prevTime = Timer.getFPGATimestamp();
		accelXBuffer.empty();
		accelYBuffer.empty();
		velXBuffer.empty();
		velYBuffer.empty();
		posXBuffer.empty();
		posYBuffer.empty();
		
		while (isOperatorControl() && isEnabled()) {
			//Update Time
			double currentTime = Timer.getFPGATimestamp();
			double timeDelta = currentTime - prevTime;
			prevTime = currentTime;
			
			//Accelerometer Values Must be Converted into Feet Per Second Per Second
			accelXBuffer.add((double)imu.getWorldLinearAccelX() * 32.174);
			accelYBuffer.add((double)imu.getWorldLinearAccelY() * 32.174);

			velXBuffer.add(velXBuffer.getData() + accelXBuffer.getData() * timeDelta);
			velYBuffer.add(velYBuffer.getData() + accelYBuffer.getData() * timeDelta);


			posXBuffer.add(posXBuffer.getData() + velXBuffer.getData() * timeDelta);
			posYBuffer.add(posYBuffer.getData() + velYBuffer.getData() * timeDelta);
			
			
			//Modify Joystick Inputs
			double x = modifyInput(joystick.getX());
			double y = modifyInput(joystick.getY());
			double z = modifyInput(joystick.getZ());
			double accelX = accelXBuffer.getData();
			double accelY = accelYBuffer.getData();
			
			if(button11.get()){
				//Zero Yaw Button
				imu.zeroYaw();
				goalHeading = 0.0;
			} 
			if(button2.get()){
				//Turn Back to Zero Button
				goalHeading = 0.0;
			}
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
			
			System.out.println("============================================");
			System.out.println("Time:\t" + Timer.getFPGATimestamp());
			System.out.println("Acceler X:\t" + new DecimalFormat("#0.000").format(accelXBuffer.getData()));
			System.out.println("Acceler Y:\t" + new DecimalFormat("#0.000").format(accelYBuffer.getData()));
			System.out.println("Velocity X:\t" + new DecimalFormat("#0.000").format(velXBuffer.getData()));
			System.out.println("Velocity Y:\t" + new DecimalFormat("#0.000").format(velYBuffer.getData()));
			System.out.println("Position X:\t" + new DecimalFormat("#0.000").format(posXBuffer.getData()));
			System.out.println("Position Y:\t" + new DecimalFormat("#0.000").format(posYBuffer.getData()));
		}
		
		output.close();
	}
	
	private double currError;
	private double lastError;
	private double integration;
	private double derivative;

	private double currRollError;
	private double currPitchError;
	
	private double goalHeading;
	private final double goalRoll = 0.0;//degrees
	private final double goalPitch = 0.0;//degrees
	//private final double TILT_THRESHOLD = 0.3;//percent
	
	
	public void driveWithPID(double x, double y){
		double rotation = 0.0;
		double currentYaw = imu.getYaw();
		currError = goalHeading - currentYaw;//[-180 - 180] degrees
		currRollError = imu.getRoll() - goalRoll;
		currPitchError = imu.getPitch() - goalPitch;
		
		//180 Degree Mark Problem
		if(Math.abs(currError) > 180) {
			double delta = 360-Math.abs(currError);
			if(currError > 0) {
				delta*= -1;
			}
			currError = delta;
		}
		
		//Calculate Values
    	integration += currError;
    	derivative = lastError - currError;
    	lastError = currError;
    	
    	//When Passing Goal Point zero Integration
    	if ( currError * lastError <0){
    		integration = 0.0;
    	}
    	
    	double kP = 1.0 / 100.0;
    	double kI = 0.0;
    	double kD = 0.005;
    	
    	rotation = kP * currError + kI * integration + kD * derivative;//[-1.0 - 1.0] percentage
    	rotation = (rotation < 0) ? Math.max(-0.75, rotation) : Math.min(0.75, rotation);

    	/*
    	//Tilt Compensation #1: Front & Back Hemisphere
    	
    	//The Scoring Zone is 16.0 Degrees
    	double kRollP = 1.0 / 7.0;
    	double kPitchP = 1.0 / 7.0;
    	
    	
    	if(-90.0 < currentYaw && currentYaw < 90.0){
    		y +=  Math.pow(kRollP * currRollError, 3);
    		x -=  Math.pow(kPitchP * currPitchError, 3);
    	} else {
    		y -= Math.pow(kRollP * currRollError, 3);
    		x += Math.pow(kPitchP * currPitchError, 3);
    	}
		*/
    	
		/*
		//Tilt Compensation #2: Suspension of Field Orientation
		
    	double tiltCompensatedY = y + Math.pow(kRollP * currRollError, 3);
    	double tiltCompensatedX = x - Math.pow(kRollP * currPitchError, 3);
		if(Math.abs(tiltCompensatedY - y) > TILT_THRESHOLD 
			|| Math.abs(tiltCompensatedX - x) >TILT_THRESHOLD ){
			System.out.println(tiltCompensatedX + "\t" + tiltCompensatedY + "\t" + rotation);
			robot.mecanumDrive_Cartesian(tiltCompensatedX, tiltCompensatedY, rotation, 0.0);
		} else {
			System.out.println(x + "\t" + y + "\t" + rotation);
			robot.mecanumDrive_Cartesian(x, y, rotation, currentYaw);	
		}*/
    	
    	robot.mecanumDrive_Cartesian(x,y,rotation, currentYaw);
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
	
	private class RingBuffer{
		private double[] buffer;
		private int size;
		private int currIndex;
		
		public RingBuffer(int size){
			this.currIndex = 0;
			this.size = size;
			this.buffer = new double[size];
		}
		
		public void add(double val){
			this.buffer[currIndex] = val;
			currIndex++;
			currIndex %= size;
 		}
		public double getData(){
			double temp = 0.0;
			for(int i = 0; i < size; i++){
				temp += buffer[i];
			}
			temp /= (double)size;
			return temp;
		}
		public void empty(){
			this.buffer = new double[size];
		}
		
	}
}