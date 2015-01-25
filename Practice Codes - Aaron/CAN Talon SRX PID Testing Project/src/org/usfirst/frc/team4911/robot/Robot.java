package org.usfirst.frc.team4911.robot;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.util.Arrays;

import edu.wpi.first.wpilibj.AnalogPotentiometer;
import edu.wpi.first.wpilibj.Gyro;
import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.SampleRobot;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.BuiltInAccelerometer;
import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.DriverStation;


/**
 * This is a short sample program demonstrating how to use the Talon SRX over
 * CAN to run a closed-loop PID controller with an analog potentiometer.
 */
public class Robot extends SampleRobot {
//Jack Holt has Connected to GitHub
	CANTalon leftFront;
	CANTalon leftRear;
	CANTalon rightFront;
	CANTalon rightRear;
	
	Joystick stick1;
	Joystick stick2;

	PrintStream output;
	AnalogPotentiometer pot1;
	
	BuiltInAccelerometer accel;
	Gyro gyro;
	
	SensorThread sensor;
	
	CameraServer server;
	
	
	enum DebugType {
		FULL, SIMPLE, NONE
	}
	
	static boolean teleopRunning;
	
	DebugType debug;
	
  public Robot() {
	  /*
      motor = new CANTalon(1); // Initialize the CanTalonSRX on device 1.

      // This sets the mode of the m_motor. The options are:
      // PercentVbus: basic throttle; no closed-loop.
      // Current: Runs the motor with the specified current if possible.
      // Speed: Runds a PID control loop to keep the motor going at a constant
      //   speed using the specified sensor.
      // Position: Runs a PID control loop to move the motor to a specified move
      //   the motor to a specified sensor position.
      // Voltage: Runs the m_motor at a constant voltage, if possible.
      // Follower: The m_motor will run at the same throttle as the specified
      //   other talon.
      motor.changeControlMode(CANTalon.ControlMode.Position);
      // This command allows you to specify which feedback device to use when doing
      // closed-loop control. The options are:
      // AnalogPot: Basic analog potentiometer
      // QuadEncoder: Quadrature Encoder
      // AnalogEncoder: Analog Encoder
      // EncRising: Counts the rising edges of the QuadA pin (allows use of a
      //   non-quadrature encoder)
      // EncFalling: Same as EncRising, but counts on falling edges.
      motor.setFeedbackDevice(CANTalon.FeedbackDevice.AnalogPot);
      // This sets the basic P, I , and D values (F, Izone, and rampRate can also
      //   be set, but are ignored here).
      // These must all be positive floating point numbers (reverseSensor will
      //   multiply the sensor values by negative one in case your sensor is flipped
      //   relative to your motor).
      // These values are in units of throttle / sensor_units where throttle ranges
      //   from -1023 to +1023 and sensor units are from 0 - 1023 for analog
      //   potentiometers, encoder ticks for encoders, and position / 10ms for
      //   speeds.
      motor.setPID(1.0, 0.0, 0.0);
      */
	  

      pot1 = new AnalogPotentiometer(1);
	  
	  stick1 = new Joystick(0);
	  stick2 = new Joystick(1);
	  
	  leftFront = new CANTalon(0); // Initialize the CanTalonSRX on device 1.
      leftFront.setFeedbackDevice(CANTalon.FeedbackDevice.QuadEncoder);
      leftFront.changeControlMode(CANTalon.ControlMode.Position);
      leftFront.setPID(1.0, 0.0, 0.0);
	  
      leftRear = new CANTalon(2); // Initialize the CanTalonSRX on device 1.
      leftRear.setFeedbackDevice(CANTalon.FeedbackDevice.QuadEncoder);
      leftRear.changeControlMode(CANTalon.ControlMode.Position);
      leftRear.setPID(1.0, 0.0, 0.0);
	  
      rightFront = new CANTalon(1); // Initialize the CanTalonSRX on device 1.
      rightFront.setFeedbackDevice(CANTalon.FeedbackDevice.QuadEncoder);
      rightFront.changeControlMode(CANTalon.ControlMode.Position);
      rightFront.setPID(1.0, 0.0, 0.0);
	  
      rightRear = new CANTalon(3); // Initialize the CanTalonSRX on device 1.
      rightRear.setFeedbackDevice(CANTalon.FeedbackDevice.QuadEncoder);
      rightRear.changeControlMode(CANTalon.ControlMode.Position);
      rightRear.setPID(1.0, 0.0, 0.0);
      
      accel = new BuiltInAccelerometer();
      
      gyro = new Gyro(0);
      gyro.setSensitivity(0.007);
      gyro.initGyro();
      
      debug = DebugType.FULL;
	  
      //sensor = new SensorThread(accel);
      //sensor.setPriority(9);
      
      server = CameraServer.getInstance();
      server.setQuality(100);
      server.startAutomaticCapture("cam1");
  }

  double initLeftEncoder = 0.0;
  double initRightEncoder = 0.0;
  
  public void operatorControl() {
	  try {
			output = new PrintStream(new BufferedOutputStream(new FileOutputStream("/home/lvuser/natinst/teleLog.txt")));
			System.setOut(output);
		} catch (FileNotFoundException e) {
			
		}
	  teleopRunning = true;
	  //sensor.start();
	  //server.startAutomaticCapture("cam1");
	  
	  gyro.reset();
	  initLeftEncoder = leftFront.getEncPosition();
	  initRightEncoder = rightFront.getEncPosition();
	  while(isOperatorControl() && isEnabled()){
		  System.out.println("Gyro:\t" + gyro.getAngle());
	  }
/*	  
    while (isOperatorControl() && isEnabled()) {
    	leftFront.set(stick1.getRawAxis(1));
    	leftRear.set(stick1.getRawAxis(1));

    	rightFront.set(-stick2.getRawAxis(1));
    	rightRear.set(-stick2.getRawAxis(1));
    	
    	System.out.println("--------------------");
    	//System.out.println("Encoder Positions:\t" + leftFront.getEncPosition() + " ,\t" + rightFront.getEncPosition());
    	System.out.println("Encoder INCHES:\t" + getInch(leftFront) + " ,\t" + getInch(rightFront));
    	//System.out.println("Encoder Velocity:\t" + leftFront.getEncVelocity() + " ,\t" + rightFront.getEncVelocity());
    }
*/
	  //direct(1);
	  //Timer.delay(10);
	  System.out.println("DONE");
	output.close();
  
  }
  public void autonomous(){
	  /*
	  try {
			output = new PrintStream(new BufferedOutputStream(new FileOutputStream(new File("/home/lvuser/natinst/autoLog.txt"))));
			System.setOut(output);
		} catch (FileNotFoundException e) {
			
		}
	  
	  double temp = 0.0;
	  while(isAutonomous() && isEnabled()){
		  System.out.println(Timer.getFPGATimestamp() + "\t" + temp);
		  rightFront.set(temp++);
		  Timer.delay(2.0);
	  }
	  */
  }
  /*
  public void disabled(){
	  sensor = new SensorThread(accel);  
	  if(output != null) {
		System.out.println("Thread State: " + sensor.getState());
  		System.out.println("Time: " + Timer.getFPGATimestamp());
  		output.close();
  	}
  }*/
  
  public static int PULSES_PER_ROTATION = 1024;
  public static double DIAMETER_OF_WHEEL = 4.0;//inch
  
  public double getInch(CANTalon talon){
	  if(talon == leftFront)
		  return (talon.getPosition() - initLeftEncoder) / PULSES_PER_ROTATION * (DIAMETER_OF_WHEEL * Math.PI);
	  else 
		  return (talon.getPosition() - initRightEncoder) / PULSES_PER_ROTATION * (DIAMETER_OF_WHEEL * Math.PI);
  }
  public void direct(int rotation){
	  
	   leftFront.set((rotation * 1024) + initLeftEncoder);
	   rightFront.set((rotation * 1024) + initRightEncoder);
	   System.out.println("PID:\t" + leftFront.getP() + "\t" + leftFront.getI() + "\t" + leftFront.getD() + "\t" + leftFront.getF());
  }
}
