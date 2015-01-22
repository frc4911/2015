package org.usfirst.frc.team4911.robot;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;

import edu.wpi.first.wpilibj.AnalogPotentiometer;
import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.SampleRobot;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.BuiltInAccelerometer;

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
	
	SensorThread sensor;
	
	
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
      leftFront.changeControlMode(CANTalon.ControlMode.PercentVbus);
      leftFront.setPID(1.0, 0.0, 0.0);
	  
      leftRear = new CANTalon(2); // Initialize the CanTalonSRX on device 1.
      leftRear.setFeedbackDevice(CANTalon.FeedbackDevice.QuadEncoder);
      leftRear.changeControlMode(CANTalon.ControlMode.PercentVbus);
      leftRear.setPID(1.0, 0.0, 0.0);
	  
      rightFront = new CANTalon(1); // Initialize the CanTalonSRX on device 1.
      rightFront.setFeedbackDevice(CANTalon.FeedbackDevice.QuadEncoder);
      rightFront.changeControlMode(CANTalon.ControlMode.PercentVbus);
      rightFront.setPID(1.0, 0.0, 0.0);
	  
      rightRear = new CANTalon(3); // Initialize the CanTalonSRX on device 1.
      rightRear.setFeedbackDevice(CANTalon.FeedbackDevice.QuadEncoder);
      rightRear.changeControlMode(CANTalon.ControlMode.PercentVbus);
      rightRear.setPID(1.0, 0.0, 0.0);
      
      accel = new BuiltInAccelerometer();
      
      debug = DebugType.FULL;
	  
      //sensor = new SensorThread(accel);
      //sensor.setPriority(9);
      
      teleopRunning = false;
  }

  public void operatorControl() {
	  /*try {
			output = new PrintStream(new BufferedOutputStream(new FileOutputStream("/home/lvuser/natinst/teleLog.txt")));
			System.setOut(output);
		} catch (FileNotFoundException e) {
			
		}*/
	  teleopRunning = true;
	  sensor.start();
	  
	  
    while (isOperatorControl() && isEnabled()) {
      
      // In closed loop mode, this sets the goal in the units mentioned above.
      // Since we are using an analog potentiometer, this will try to go to
      //   the middle of the potentiometer range.
	//		motor.set(512);

      //Timer.delay(5.0);
     
    	
    	leftFront.set(stick1.getRawAxis(1));
    	leftRear.set(stick1.getRawAxis(1));

    	rightFront.set(-stick2.getRawAxis(1));
    	rightRear.set(-stick2.getRawAxis(1));
    	
    	System.out.println("--------------------");
    	/*System.out.println("X: " + sensor.accelX);// + "\t\t" + "RealX: " + accel.getX());
    	System.out.println("Y: " + sensor.accelY);// + "\t\t" + "RealY: " + accel.getY());
    	System.out.println("Z: " + sensor.accelZ);// + "\t\t" + "RealZ: " + accel.getZ());*/
    	System.out.println("Encoder Positions:\t" + leftFront.getEncPosition() + " ,\t" + rightFront.getEncPosition());
    	System.out.println("Encoder INCHES:\t" + getInch(leftFront) + " ,\t" + getInch(leftFront));
    	//System.out.println("Encoder Velocity:\t" + leftFront.getEncVelocity() + " ,\t" + rightFront.getEncVelocity());
    	Timer.delay(0.05);
      
    }
  
  }
  public void autonomous(){
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
	  
  }
  public void disabled(){
	  teleopRunning = false;
	  sensor = new SensorThread(accel);  
	  if(output != null) {
		System.out.println("Thread State: " + sensor.getState());
  		System.out.println("Time: " + Timer.getFPGATimestamp());
  		output.close();
  	}
  }
  
  public void print() {
	  if(debug == DebugType.NONE) {
		  return;
	  }
	  printBorder();
	  switch(debug) {
	  	case FULL:
	  		System.out.println("RIOAccel X: " + sensor.accelX);
	  		System.out.println("RIOAccel Y: " + sensor.accelY);
	  		System.out.println("RIOAccel Z: " + sensor.accelZ);
	  		System.out.println("Thread State: " + sensor.getState());
	  		
	  	case SIMPLE:
	  }
	  System.out.println("Voltage: " + DriverStation.getInstance().getBatteryVoltage());
	  printBorder();
	  
  }
  
  public void printBorder() {
	  System.out.println("===================");
  }
  
  public static int PULSES_PER_ROTATION = 1024;
  public static double DIAMETER_OF_WHEEL = 4.0;//inch
  
  public double getInch(CANTalon talon){
	  return talon.getPosition() / PULSES_PER_ROTATION * (DIAMETER_OF_WHEEL * Math.PI);
  }
}
