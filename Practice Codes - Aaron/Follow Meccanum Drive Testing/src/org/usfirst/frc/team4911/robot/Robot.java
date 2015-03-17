package org.usfirst.frc.team4911.robot;

import java.io.File;

import java.io.PrintStream;

import com.kauailabs.nav6.frc.IMUAdvanced;
import com.ni.vision.NIVision;
import com.ni.vision.NIVision.Image;
import com.ni.vision.NIVision.ImageType;

import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.CANTalon.ControlMode;
import edu.wpi.first.wpilibj.buttons.JoystickButton;
import edu.wpi.first.wpilibj.AnalogPotentiometer;
import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.SampleRobot;
import edu.wpi.first.wpilibj.SerialPort;
import edu.wpi.first.wpilibj.Timer;

public class Robot extends SampleRobot {
	private Joystick mainJoystick;
	private Joystick operatorJoystick;

	private JoystickButton button1;
	private JoystickButton button2;
	private JoystickButton button3;
	private JoystickButton button4;
	private JoystickButton button5;
	private JoystickButton button8;
	private JoystickButton button9;
	private JoystickButton button10;
	private JoystickButton closeClampButton;
	private JoystickButton openClampButton;
	private JoystickButton stowClampButton;
	
	private CANTalon leftFront3;
	private CANTalon leftRear4;
	private CANTalon rightFront7;
	private CANTalon rightRear8;
	
	private CANTalon hookLift1;
	private CANTalon hookLift2;
	
	private CANTalon containerLift;
	private CANTalon containerContainer1;
	private CANTalon containerContainer2;

	private IMUAdvanced imu;
	private SerialPort serial_port;
	
	private AnalogPotentiometer clampPot;
	
	private int session;
	private Image frame;
	private CameraServer cameraServer;

	private double rotation;
	private double currError;
	private double lastError;
	private double integration;
	private double derivative;

	private double goalHeading;
	private double speedLimit;
	private boolean fieldOriented;

	private PrintStream output;

	/////////////////////////////////////////////////////////////
	//
	//	CONSTRUCTOR / INITIALIZATION
	//
	/////////////////////////////////////////////////////////////
	public Robot() {
		mainJoystick = new Joystick(1);
		button1 = new JoystickButton(mainJoystick, 1);
		button2 = new JoystickButton(mainJoystick, 2);
		button3 = new JoystickButton(mainJoystick, 3);
		button4 = new JoystickButton(mainJoystick, 4);
		button5 = new JoystickButton(mainJoystick, 5);
		button8 = new JoystickButton(mainJoystick, 8);
		button9 = new JoystickButton(mainJoystick, 9);
		button10 = new JoystickButton(mainJoystick, 10);

		operatorJoystick =  new Joystick(2);
		closeClampButton = new JoystickButton(operatorJoystick, 7);
		openClampButton = new JoystickButton(operatorJoystick, 8);
		stowClampButton = new JoystickButton(operatorJoystick, 5);
		
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
		
		hookLift1 = new CANTalon(1);
		hookLift1.changeControlMode(CANTalon.ControlMode.PercentVbus);
		hookLift1.reverseOutput(true);
		hookLift2 = new CANTalon(2);
		hookLift2.changeControlMode(CANTalon.ControlMode.Follower);
		hookLift1.reverseOutput(true);
		
		containerLift = new CANTalon(5);	
		containerLift.changeControlMode(CANTalon.ControlMode.PercentVbus);	
		containerLift.reverseOutput(false);
		
		containerContainer1 = new CANTalon(6);
		containerLift.changeControlMode(CANTalon.ControlMode.PercentVbus);	
		containerContainer2 = new CANTalon(9);
		containerLift.changeControlMode(CANTalon.ControlMode.Follower);	
		
		clampPot = new AnalogPotentiometer(0);
		

		frame = NIVision.imaqCreateImage(ImageType.IMAGE_RGB, 0);
        session = NIVision.IMAQdxOpenCamera("cam2",
                NIVision.IMAQdxCameraControlMode.CameraControlModeController);
        NIVision.IMAQdxConfigureGrab(session);

        cameraServer = CameraServer.getInstance();
        cameraServer.setQuality(30);
        
		/***************************************
		 *
	     * IMU INITIALIZATION
	     ***************************************/
		try {
			serial_port = new SerialPort(57600,SerialPort.Port.kOnboard);
		
			byte update_rate_hz = 20;
			imu = new IMUAdvanced(serial_port,update_rate_hz);
		} catch (Exception ex) {
			ex.printStackTrace();
			
		}
		imu.initIMU();
		Timer.delay(0.3);
		
		goalHeading = 0.0;
		speedLimit = 0.0;
		fieldOriented = true;
	}

	/////////////////////////////////////////////////////////////
	//
	//	OPERATOR CONTROLLED PERIOD 
	//
	/////////////////////////////////////////////////////////////
	public void operatorControl() {
		boolean rotateEnabled = false;
		
		//Creating a File to Print to
		try {
			output = new PrintStream(new File("/home/lvuser/natinst/teleLog.txt"));
			System.setOut(output);
		} catch (Exception e){
			
		}
		
		//Starting the USB Camera
		NIVision.IMAQdxStartAcquisition(session);
		
		//Direction Initialization
		reinitializingIMU(2.0);//Two Second Max
		
		//CAMERA THREAD
		Thread cameraThread = new Thread(new Runnable(){
			public void run(){
				while(isOperatorControl() && isEnabled()){
					//Camera Update
					 NIVision.IMAQdxGrab(session, frame, 1);
					 cameraServer.setImage(frame);
				}
			}
		});
		cameraThread.start();
		
		//PAYLOAD OPERATOR"S THREAD
		Thread payloadOperatorThread = new Thread(new Runnable(){
			public void run(){
				Thread nudgeToteLiftThread = createNudgeToteLiftDownThread();
				boolean toteLiftCommandedByPreset = false;
				while(isOperatorControl() && isEnabled()){
					double hookLiftVal = operatorJoystick.getY();
					double containerLiftVal = operatorJoystick.getRawAxis(3);
					
					//CLAMP LOGIC
				    if(closeClampButton.get()){
				    	runClampInward();
				    }
				    else if(openClampButton.get()){
				    	runClampOutward();
				    }
				    else if(stowClampButton.get()) {
				    	runClamp(1.0);
				    }
				    else{
				    	runClamp(0.0);
				    }			

				    //CONTAINER LIFT LOGIC
				    if(Math.abs(containerLiftVal) >= 0.1){
					    runContainerLift(containerLiftVal);
					} else {
						runContainerLift(0.0);
					}
				    
				    //HOOK LIFT LOGIC
				    if(!toteLiftCommandedByPreset){
				    	//NUDGE HOOK LIFT DOWN
					    if(operatorJoystick.getPOV() != -1.0){
					    	toteLiftCommandedByPreset = true;
					    	nudgeToteLiftThread = createNudgeToteLiftDownThread();
					    	nudgeToteLiftThread.start();
					    } else if(Math.abs(hookLiftVal) > 0.1) {
			    			runHookLift(hookLiftVal);
			    		}
			    		//manual stop code... use ONLY if presets are not running
			    		else {
			    			runHookLift(0.0);
			    		}
				    } else if(!nudgeToteLiftThread.isAlive()){
				    	toteLiftCommandedByPreset = false;
				    }
				}
			}//End of "PaylodOperatorThread" RUN()
		});
		payloadOperatorThread.start();
		
		while (isOperatorControl() && isEnabled()) {			
			//Modify Joystick Inputs
			speedLimit = ((-1 * mainJoystick.getThrottle()) + 1.0) * 0.5;
			double x = modifyInput(mainJoystick.getX());
			double y = modifyInput(mainJoystick.getY());
			double z = modifyInput(mainJoystick.getZ());
			double angle = imu.getYaw();
			
    		//TUNE PID VALUES
		    //HEADING OPERATOR INTERFACE
    		if(button2.get()){
    			goalHeading = 0.0;
    		} else if(button3.get()){
    			goalHeading = -90.0;
    		} else if(button4.get()){
    			goalHeading = 90.0;
    		} else if(button10.get()){
    			goalHeading = 180.0;
    		}
    		

    		//TOP HAT FORWARD ROBOT ORIENTED
    		if(mainJoystick.getPOV(0) == 180){
			    x = 0.0;
			    y = -1.0;
			    fieldOriented = false;
			}
    		//TOP HAT BACKWARD ROBOT ORIENTED
			else if(mainJoystick.getPOV(0) == 0) {
			    x = 0.0;
			    y = 1.0;
			    fieldOriented = false;
			}
    		//TOP HAT LEFT ROBOT ORIENTED
			else if(mainJoystick.getPOV(0) == 90) {
			    x = -1.0;
			    y = 0.0;
			    fieldOriented = false;
			}
    		//TOP HAT RIGHT ROBOT ORIENTED
			else if(mainJoystick.getPOV(0) == 270) {
			    x = 1.0;
			    y = 0.0;
			    fieldOriented = false;
			} else {
			    fieldOriented = true;
			}
    		
    		
    		//ZERO YAW BUTTON
    		if(button5.get()){
    			reinitializingIMU(2.0);
    		}
    		//NUDGE LEFT 
    		else if(button8.get()){
    			drive(0.0, 0.0, -0.4, angle);
    			Timer.delay(0.01);
    			drive(0.0, 0.0, 0.0, angle);
    			Timer.delay(0.1);
    			//must pull new imu value for Nudging
    			goalHeading = imu.getYaw();
    		}
    		//NUDGE RIGHT 
    		else if(button9.get()){
    			drive(0.0, 0.0, 0.4, angle);
    			Timer.delay(0.01);
    			drive(0.0, 0.0, 0.0, angle);
    			Timer.delay(0.1);
    			//must pull new imu value for Nudging
    			goalHeading = imu.getYaw();
    		}
    		//ENABLE ROTATE
    		else if(button1.get()){
    			rotateEnabled = true;
				drive(x, y, z, angle);
			} 
    		else {
    			//WHEN RETURNING FROM ENABLE ROTATE
				if(rotateEnabled){
					drive(0.0, 0.0, 0.0, angle);
					//Waiting for goalHeading reassignment period
					int clearNum = 0;
					double prevAngle = angle + 1.1;//1.1 is an arbitrary number
					//WAITING FOR FOUR ITERATION
					while(clearNum < 4){
						if(Math.abs(angle - prevAngle) <= 1.0){
							clearNum++;
						} else {
							clearNum = 0;
						}
						prevAngle = angle;
						angle = imu.getYaw();
						System.out.println("Waiting for robot to Stop\t" + clearNum);
						Timer.delay(0.01);
					}
					rotateEnabled = false;
					goalHeading = angle;
				}
	    		//PID DRIVE  
				else {
					driveWithPID(x, y, angle);					
				}
			}
		}//END of Main TeleOp Loop
		
		drive(0.0, 0.0, 0.0, 0.0);	
		output.close();
	}
	

	/////////////////////////////////////////////////////////////
	//
	//	AUTONOMOUS CONTROLLED PERIOD
	//
	/////////////////////////////////////////////////////////////
	public void autonomous(){
		//Creating a File to Print to
		try {
			output = new PrintStream(new File("/home/lvuser/natinst/autoLog.txt"));
			System.setOut(output);
		} catch (Exception e){
		}
		speedLimit = 1.0;
		imu.zeroYaw();
		
		//CONTAINER AND TOTE AUTONOMOUS
		double startTime = Timer.getFPGATimestamp();
		double currTime = startTime;
		//MOVING TOTE LIFT UP
		while(isAutonomous() && isEnabled() && currTime - startTime <= 1.0){
			runHookLift(-1.0);
			currTime = Timer.getFPGATimestamp();
		}
		runHookLift(0.0);
		startTime = Timer.getFPGATimestamp();
		currTime = startTime;
		//ROTATE 
		while(isAutonomous() && isEnabled() && currTime - startTime <= 1.75){
			drive(0.0, 0.0, -0.7, imu.getYaw());
			currTime = Timer.getFPGATimestamp();
		}
		drive(0.0, 0.0, 0.0, 0.0);
		
		startTime = Timer.getFPGATimestamp();
		currTime = startTime;
		//CONTAINER LIFT DOWN
		while(isAutonomous() && isEnabled() && currTime - startTime <= 3.5){
			runContainerLift(0.25);
			currTime = Timer.getFPGATimestamp();
		}
		runContainerLift(0.0);
		/*
		startTime = Timer.getFPGATimestamp();
		currTime = startTime;
		//CLAMP IN
		while(isAutonomous() && isEnabled() && currTime - startTime <= 3.75){
			runClampInward();
			currTime = Timer.getFPGATimestamp();
		}
		runClamp(0.0);
		*/
		startTime = Timer.getFPGATimestamp();
		currTime = startTime;
		//CONTAINER LIFT UP
		while(isAutonomous() && isEnabled() && currTime - startTime <= 0.8){
			runContainerLift(-1.0);
			currTime = Timer.getFPGATimestamp();
		}
		runContainerLift(0.0);
		startTime = Timer.getFPGATimestamp();
		currTime = startTime;
		//ROTATE 
		while(isAutonomous() && isEnabled() && currTime - startTime <= 0.6){
			drive(0.0, 0.0, 0.7, imu.getYaw());
			currTime = Timer.getFPGATimestamp();
		}
		drive(0.0, 0.0, 0.0, 0.0);
		/*
		startTime = Timer.getFPGATimestamp();
		currTime = startTime;
		//STRAFE LEFT
		while(isAutonomous() && isEnabled() && currTime - startTime <= 1.75){
			driveWithPID(0.0, -1.0, imu.getYaw());
			currTime = Timer.getFPGATimestamp();
		}
		drive(0.0, 0.0, 0.0, 0.0);
		*/
		
		
		
		/*
		//TWO CONTAINER AUTONOMOUS
		double startTime = Timer.getFPGATimestamp();
		double currTime = startTime;
		//CLAMP IN and LIFT UP
		while(currTime - startTime <= 2.0){
			runClampInward();
			runContainerLift(1.0);
			currTime = Timer.getFPGATimestamp();
		}
		startTime = Timer.getFPGATimestamp();
		currTime = startTime;
		//STRAFE LEFT
		while(currTime - startTime <= 1.3){
			driveWithPID(0.5, 0.0, imu.getYaw());
			currTime = Timer.getFPGATimestamp();
		}
		startTime = Timer.getFPGATimestamp();
		currTime = startTime;
		//ROTATING
		while(currTime - startTime <= 2.55){
			drive(0.0, 0.0, 0.7, imu.getYaw());
			currTime = Timer.getFPGATimestamp();
		}
		startTime = Timer.getFPGATimestamp();
		currTime = startTime;
		//DRIVING IN
		while(currTime - startTime <= 2.0){
			drive(0.0, -0.5, 0.0, imu.getYaw());
			currTime = Timer.getFPGATimestamp();
		}
		startTime = Timer.getFPGATimestamp();
		currTime = startTime;
		//STRAFING LEFT
		while(currTime - startTime <= 0.9){
			drive(0.5, 0.0, 0.0, imu.getYaw());
			currTime = Timer.getFPGATimestamp();
		}
		startTime = Timer.getFPGATimestamp();
		currTime = startTime;
		//MOVING TOTE LIFT UP
		while(currTime - startTime <= 2.0){
			runHookLift(-1.0);
			currTime = Timer.getFPGATimestamp();
		}
		startTime = Timer.getFPGATimestamp();
		currTime = startTime;
		//ROTATING
		while(currTime - startTime <= 0.75){
			drive(0.0, 0.0, 0.9, imu.getYaw());
			currTime = Timer.getFPGATimestamp();
		}
		startTime = Timer.getFPGATimestamp();
		currTime = startTime;
		//DRIVING TO AUTOZONE
		while(currTime - startTime <= 1.75){
			drive(0.0, 1.0, 0.0, imu.getYaw());
			currTime = Timer.getFPGATimestamp();
		}
		*/
		drive(0.0, 0.0, 0.0, 0.0);	
		output.close();
	}
	
	/////////////////////////////////////////////////////////////
	//
	//	OPERATOR INTERFACE METHODS
	//
	/////////////////////////////////////////////////////////////
	private double modifyInput(double val){
		double pow = 0.0;
		double sensitivity = 10.0;
    	if(Math.abs(val) >= 0.1) {
            pow = Math.round(val * sensitivity) 
            		/ sensitivity;  
        }
    	return pow;
	}

	/////////////////////////////////////////////////////////////
	//
	//	DRIVE SYSTEM METHODS
	//
	/////////////////////////////////////////////////////////////
	public void drive(double x, double y, double rotation, double angle){
		//Speed Correction
		x *= speedLimit;
		y *= speedLimit;
		rotation *= speedLimit;
		
		double[] wheelSpeeds = new double[4];
		byte syncGroup = 0;
		        
		y = -y;
			
	
		// Compenstate for gyro angle.
        double rotated[] = rotateVector(x, y, angle);
        x = rotated[0];
        y = rotated[1];
	
		if(Math.abs(rotation) <= 0.05){
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

	        
	        System.out.println("==============================================================");
	        System.out.println("GoalHeading:\t" + goalHeading);
	        System.out.println("X:\t" + x);
	        System.out.println("Y:\t" + y);
	        System.out.println("R:\t" + rotation);
	        System.out.println("A:\t" + angle);
	        System.out.println("WheelSpeeds:\t" + wheelSpeeds[0] + " , ," + wheelSpeeds[2]);
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

	        System.out.println("==============================================================");
	        System.out.println("GoalHeading:\t" + goalHeading);
	        System.out.println("X:\t" + x);
	        System.out.println("Y:\t" + y);
	        System.out.println("R:\t" + rotation);
	        System.out.println("A:\t" + angle);
	        System.out.println("WheelSpeeds:\t" + wheelSpeeds[0] + " , " + wheelSpeeds[1]
	        		 + " , " + wheelSpeeds[2]  + " , " + wheelSpeeds[3]);
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
    	if(!fieldOriented){
    		currYaw = 0.0;
    	}
    	drive(x, y, rotation, currYaw);
	}
	
	private static double[] rotateVector(double x, double y, double angle) {
        double cosA = Math.cos(angle * (3.14159 / 180.0));
        double sinA = Math.sin(angle * (3.14159 / 180.0));
        double out[] = new double[2];
        out[0] = x * cosA - y * sinA;
        out[1] = x * sinA + y * cosA;
        return out;
    }

	/////////////////////////////////////////////////////////////
	//
	//	CONTAINER LIFT METHODS
	//
	/////////////////////////////////////////////////////////////	
	public void runClampOutward(){
		containerContainer1.changeControlMode(ControlMode.PercentVbus);
		containerContainer2.changeControlMode(ControlMode.Follower);
		if(clampPot.get() < 0.720) {
			containerContainer1.set(-1.0);
		}
		else {
			containerContainer1.set(0.0);
		}
		containerContainer2.set(containerContainer1.getDeviceID());
	}
	
	public void runClampInward() {
		containerContainer1.changeControlMode(ControlMode.PercentVbus);
		containerContainer2.changeControlMode(ControlMode.Follower);
    	//pot value:   0.4499214632998128 if greater, fast
    	if(clampPot.get() > 0.4499214632998128) {
    	    containerContainer1.set(1.0);
    	}
    	else {
    		containerContainer1.set(0.307);
    	}
		containerContainer2.set(containerContainer1.getDeviceID());
    }
	
	public void runClamp(double speed){
		containerContainer1.changeControlMode(ControlMode.PercentVbus);
		containerContainer2.changeControlMode(ControlMode.Follower);
    	containerContainer1.set(speed);
		containerContainer2.set(containerContainer1.getDeviceID());
	}
	
	public void runContainerLift(double speed) {
    	containerLift.changeControlMode(CANTalon.ControlMode.PercentVbus);
    	containerLift.set(speed);
    }

	/////////////////////////////////////////////////////////////
	//
	//	HOOK LIFT METHODS
	//
	/////////////////////////////////////////////////////////////
	
	public void runHookLift(double speed) {

    	hookLift1.changeControlMode(CANTalon.ControlMode.PercentVbus);
    	hookLift2.changeControlMode(CANTalon.ControlMode.Follower);
    	hookLift1.set(-speed);
    	hookLift2.set(hookLift1.getDeviceID());
    }

	public Thread createNudgeToteLiftDownThread(){
		Thread t = new Thread(new Runnable(){
    		public void run(){
    			double startTime = Timer.getFPGATimestamp();
    			double currTime = startTime;
    			while(currTime - startTime <= 0.3){//3 Seconds
    				runHookLift(0.5);//50% Power
    				currTime = Timer.getFPGATimestamp();
    			}
    		}
    	});
		return t;
	}
	
	/////////////////////////////////////////////////////////////
	//
	//	IMU METHODS
	//
	/////////////////////////////////////////////////////////////
	public void reinitializingIMU(double overrideTime){
		//Zero Yaw and goal heading
		imu.zeroYaw();
		goalHeading = 0.0;
		
		double startTime = Timer.getFPGATimestamp();
		double currTime = startTime;
		while((imu.isCalibrating() || Math.abs(imu.getYaw()) >= 1.0) && currTime - startTime <= overrideTime){
			currTime = Timer.getFPGATimestamp();
			Timer.delay(0.1);
			System.out.println("WAITING FOR IMU TO ZERO AND CALIBRATE");
		}
	}
	
}
