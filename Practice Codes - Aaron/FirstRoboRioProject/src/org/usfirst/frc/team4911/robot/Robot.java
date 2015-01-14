package org.usfirst.frc.team4911.robot;

import edu.wpi.first.wpilibj.IterativeRobot;

import java.util.*;
import java.io.*;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.Servo;
import edu.wpi.first.wpilibj.Gyro;
import edu.wpi.first.wpilibj.AnalogPotentiometer;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj.PIDSource.PIDSourceParameter;
import edu.wpi.first.wpilibj.CounterBase.EncodingType;
import edu.wpi.first.wpilibj.BuiltInAccelerometer;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class Robot extends IterativeRobot {
	RobotDrive myRobot;
	Joystick stick1;
	Joystick stick2;
	int autoLoopCounter;
	Encoder LeftEncoder;
	Encoder RightEncoder;
	
	Gyro gyro1;
	Servo servo1;
	AnalogPotentiometer pot1;
	
	BuiltInAccelerometer accel = new BuiltInAccelerometer();
	
	PrintStream output;
	int fileNum;
	double startTime;
	
	
	//DRIVE SYSTEM CONSTANTS
    public static final double WHEEL_DIAMETER = 4.0; //Measured in Inches
    public static final double GEAR_RATIO = 1.0;//39 Teeth on Wheels & 42 Teeth on Encoders
    public static final double ONE_ROTATION_IN_INCHES = Math.PI * WHEEL_DIAMETER;
    public static final double ENCODER_PULSE_PER_ROTATION = 250.0;//Number of Pulse per One Rotation of the Encoder
    public static final double ENCODER_DISTANCE_PER_PULSE = GEAR_RATIO * WHEEL_DIAMETER * Math.PI / ENCODER_PULSE_PER_ROTATION;
   
    public static final int LEFT_ENCODER_PORT_A = 0;//Digital //1
    public static final int LEFT_ENCODER_PORT_B = 1;//Digital //2
    public static final int RIGHT_ENCODER_PORT_A = 2;//Digital //3
    public static final int RIGHT_ENCODER_PORT_B = 3;//Digital //4
	
    /**
     * This function is run when the robot is first started up and should be
     * used for any initialization code.
     */
    public void robotInit() {
    	myRobot = new RobotDrive(2,3,0,1);
    	stick1 = new Joystick(0);
    	stick2 = new Joystick(1);
    	LeftEncoder = new Encoder(LEFT_ENCODER_PORT_A, LEFT_ENCODER_PORT_B, true, EncodingType.k4X);
        LeftEncoder.setDistancePerPulse(ENCODER_DISTANCE_PER_PULSE);
        LeftEncoder.setPIDSourceParameter(PIDSourceParameter.kRate);
        
        RightEncoder = new Encoder(RIGHT_ENCODER_PORT_A, RIGHT_ENCODER_PORT_B, false, EncodingType.k4X);
        RightEncoder.setDistancePerPulse(ENCODER_DISTANCE_PER_PULSE);
        RightEncoder.setPIDSourceParameter(PIDSourceParameter.kRate);
        
        servo1 = new Servo(4);
        gyro1 = new Gyro(0);
        pot1 = new AnalogPotentiometer(1);
    }
    
    /**
     * This function is run once each time the robot enters autonomous mode
     */
    public void autonomousInit() {
    	try {
			output = new PrintStream(new BufferedOutputStream(new FileOutputStream(new File("/home/lvuser/natinst/autoLog.txt"))));
			System.setOut(output);
		} catch (FileNotFoundException e) {
			
		}
    	autoLoopCounter = 0;
    	//driveStraight(60.0);
    	gyro1.reset();
    	
    	potVal = 0.0;
    	servoVal = 0.0;
    	prevPotVal = 0.0;
    }
    double potVal;
    double prevPotVal;
    double servoVal;
    double servoPos = 0.0;
    
    /**
     * This function is called periodically during autonomous
     */
    public void autonomousPeriodic() {
    	/*
    	//DEFAULT CODE
    	if(autoLoopCounter < 100) //Check if we've completed 100 loops (approximately 2 seconds)
		{
			myRobot.drive(-0.5, 0.0); 	// drive forwards half speed
			autoLoopCounter++;
			} else {
			myRobot.drive(0.0, 0.0); 	// stop robot
		}*/
    	/*
    	servo1.set(0.0);
    	Timer.delay(2.0);
    	servo1.set(1.0);
    	Timer.delay(2.0);
    	*/
    	
    	potVal = pot1.get();
    	double deltaPot = potVal - prevPotVal;
    	servoVal = 0.1 * (deltaPot / 0.07) + 0.51;
    	servo1.set( servoVal );
    	System.out.println(Timer.getFPGATimestamp() + "\t\t\t: " + servoVal + "\t" + deltaPot);
    	prevPotVal = potVal;
    	
    	
    	
    	/*
    	double potValDeg;
    	double servoVoltage = 0.55;
    	double servoSpeed = 4.12;
    	potVal = pot1.get();
    	potValDeg = potVal * 360;
    	
    	if (servoPos >= potValDeg + servoSpeed){
    		servo1.set(servoVoltage * -1);
    		servoPos -= servoSpeed;
    		System.out.println("correcting backwards");
    	}
    	else if (servoPos <= potValDeg - servoSpeed){
    		servo1.set(servoVoltage);
    		servoPos += servoSpeed;
    		System.out.println("correcting forwards");
    	}
    	else{
    		if (servoPos >= potValDeg + 0.5){
        		servo1.set(0.52 * -1);
        		servoPos -= 0.0009;
        	}
        	else if (servoPos <= potValDeg - 0.5){
        		servo1.set(0.52);
        		servoPos += 0.0009;
        	}
        	
        		
    	}
    	
    	
    	*/
    }
    
    
    /**
     * This function is called once each time the robot enters tele-operated mode
     */
    public void teleopInit(){    	
        try {
			output = new PrintStream(new BufferedOutputStream(new FileOutputStream("/home/lvuser/natinst/log" + fileNum + ".txt")));
			System.setOut(output);
		} catch (FileNotFoundException e) {
			
		}
    }

    /**
     * This function is called periodically during operator control
     */
    public void teleopPeriodic() {
    	myRobot.tankDrive(stick1.getRawAxis(1), stick2.getRawAxis(1));
    	
    	servo1.set( gyro1.getRate() );
    	System.out.println(Timer.getFPGATimestamp() + "\t\t" + gyro1.getRate());
    }
    
    /**
     * This function is called periodically during test mode
     */
    public void testPeriodic() {
    	LiveWindow.run();
    }
    
    public void disabledInit() {
    	gyro1.reset();
    	LeftEncoder.reset();
    	RightEncoder.reset();
    	if(output != null) {
    		System.out.println("Time: " + Timer.getFPGATimestamp());
    		output.close();
    	}
    	
    }
    public void driveStraight(double distance){    	
    	double curDist = 0.0;
    	double kp = RobotConstants.DRIVESTRAIGHT_CORRECTION_CONSTANT;
        double error = 0.0;
        double power = 0.0;
    	
    	
    	while(curDist < distance){
            error = kp * gyro1.getAngle();
            curDist = (LeftEncoder.getDistance() + RightEncoder.getDistance() ) / 2.0;
            power = getRampedPower(distance, curDist);         
    		myRobot.tankDrive(power - error, power + error);

    		System.out.println(Timer.getFPGATimestamp() + "\t:" + power);
    		//print("drivestraight");
    	}
    } 
    private double getRampedPower(double goalDistance, double currentDistance){
        double fractionOfGoalDistance = Math.min(currentDistance / goalDistance, 1.0);        
        double rampedPower = RobotConstants.AMPLITUDE * Math.pow(Math.cos(0.5 * Math.PI * fractionOfGoalDistance) , RobotConstants.RAMP_UP) * Math.pow(fractionOfGoalDistance , RobotConstants.RAMP_DOWN);
        rampedPower = Math.min(rampedPower + RobotConstants.FLOOR, RobotConstants.CEILING);
        return rampedPower;
    }
    public void print(String method){
    	System.out.println("++++++++++++++++++++++++++++");
    	System.out.println("Timestamp: " + Timer.getFPGATimestamp());
    	System.out.println("Method:" + method);
    	//System.out.println("ENCODER VALUES: " + LeftEncoder.getDistance() + " ,\t" + RightEncoder.getDistance());
    	//System.out.println("GYRO VALUES: " + gyro1.getAngle());
    	System.out.println("++++++++++++++++++++++++++++");
    }
    public void updateServoPosition(double voltage){
    	servo1.set(voltage);
    }
}
