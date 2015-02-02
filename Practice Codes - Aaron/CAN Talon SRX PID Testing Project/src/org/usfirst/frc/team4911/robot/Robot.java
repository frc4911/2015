package org.usfirst.frc.team4911.robot;

import java.io.BufferedOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;

import com.kauailabs.nav6.frc.IMUAdvanced;

import edu.wpi.first.wpilibj.SerialPort;
import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.SampleRobot;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Timer;
//import edu.wpi.first.wpilibj.buttons;

public class Robot extends SampleRobot {
	Joystick stick1;
	Joystick stick2;
	
	//Button button1;
	
	CANTalon leftFront;
	CANTalon leftRear;
	CANTalon rightFront;
	CANTalon rightRear;
	
	PrintStream output;
	
	public static IMUAdvanced imu;
	public static SerialPort serial_port;
	
	double kP;
	double kI;
	double kD;
	
	public Robot() {
		stick1 = new Joystick(0);
		stick2 = new Joystick(1);
		
		kP = 1.1;
		kI = 0.00001;//0.0002;
		kD = 0.0;//5.0;
			  
		leftFront = new CANTalon(2); // Initialize the CanTalonSRX on device 1.
		leftFront.setFeedbackDevice(CANTalon.FeedbackDevice.QuadEncoder);
		leftFront.changeControlMode(CANTalon.ControlMode.Position);
		leftFront.setPID(1.0, 0.0, 0.0);
		  
		leftRear = new CANTalon(1); // Initialize the CanTalonSRX on device 1.
		leftRear.setFeedbackDevice(CANTalon.FeedbackDevice.QuadEncoder);
		leftRear.changeControlMode(CANTalon.ControlMode.Position);
		leftRear.setPID(1.0, 0.0, 0.0);
		  
		rightFront = new CANTalon(3); // Initialize the CanTalonSRX on device 1.
		rightFront.setFeedbackDevice(CANTalon.FeedbackDevice.QuadEncoder);
		rightFront.changeControlMode(CANTalon.ControlMode.Position);
		rightFront.setPID(kP, kI, kD);
		  
		rightRear = new CANTalon(4); // Initialize the CanTalonSRX on device 1.
		rightRear.setFeedbackDevice(CANTalon.FeedbackDevice.QuadEncoder);
		rightRear.changeControlMode(CANTalon.ControlMode.Position);
		rightRear.setPID(1.0, 0.0, 0.0);
		
		
		
		/***************************************
	     * IMU INITIALIZATION
	     ***************************************/
		/*try {
			serial_port = new SerialPort(57600,SerialPort.Port.kUSB );
		          
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
			
		}*/
		Timer.delay(0.3);
	}
	
	public void operatorControl() {
		/*try {
			output = new PrintStream(new BufferedOutputStream(new FileOutputStream("/home/lvuser/natinst/teleLog.txt")));
			System.setOut(output);
		} catch (FileNotFoundException e) {	
		}*/
	  
		while(isOperatorControl() && isEnabled()){
			//System.out.println(imu.getYaw());
			if(stick1.getRawButton(3)) {
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
			System.out.println("Ki: " + kI);
			rightFront.setPID(kP, kI, kD);
			Timer.delay(.15);
			//System.out.println("Current encoder pos: " + rightFront.getEncPosition());
		}
		//output.close();
	}  
	
	public void autonomous(){
		while(isAutonomous() && isEnabled()) {
			rightFront.setPosition(1024);
		}
	}
}
