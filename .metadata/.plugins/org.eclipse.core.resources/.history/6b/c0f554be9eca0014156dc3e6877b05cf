package org.usfirst.frc.team4911.robot;

import org.usfirst.frc.team4911.robot.subsystems.PrintSystem;

import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.BuiltInAccelerometer;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Gyro;
import edu.wpi.first.wpilibj.I2C;
import edu.wpi.first.wpilibj.SerialPort;
import edu.wpi.first.wpilibj.Servo;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.AnalogPotentiometer;
import edu.wpi.first.wpilibj.CameraServer;
import com.ni.vision.NIVision.Image;
import ExternalLibs.LIDAR;

//import edu.wpi.first.wpilibj.vision.AxisCamera;
//import java.net.Inet4Address;
import com.kauailabs.nav6.frc.IMUAdvanced;

public class RobotMap {
    public static CANTalon leftFront;
    public static CANTalon leftRear;
    public static CANTalon rightFront;
    public static CANTalon rightRear;
    public static CANTalon hookLeft;
    public static CANTalon hookRight;	
    public static CANTalon containerLift;
    public static CANTalon containerContainer;
    public static CANTalon secondContainerContainer;
	
    public static AnalogPotentiometer clampPot;
    //public static AnalogPotentiometer hookLiftPot;
    //public static AnalogPotentiometer containerLiftPot;
	
    public static IMUAdvanced imu;
    public static BuiltInAccelerometer accelerometer;
    public static Gyro gyro;

    public static Encoder xSlideEncoder;
    public static Encoder ySlideEncoder;

    public static LIDAR lidar;
    
    public static CameraServer server;
	
    public static Image frame;
    public static int toteSession;
    
    private static SerialPort serial_port;
		
    public static void init(){
			
	leftFront = new CANTalon(RobotConstants.LEFT_FRONT_CANTALON_PORT); // Initialize the CanTalonSRX on device 1.
	leftFront.setFeedbackDevice(CANTalon.FeedbackDevice.QuadEncoder);
	leftFront.changeControlMode(CANTalon.ControlMode.PercentVbus);
	leftFront.setPID(1.0, 0.0, 0.0);
		  
	leftRear = new CANTalon(RobotConstants.LEFT_REAR_CANTALON_PORT); // Initialize the CanTalonSRX on device 1.
	leftRear.setFeedbackDevice(CANTalon.FeedbackDevice.QuadEncoder);
	leftRear.changeControlMode(CANTalon.ControlMode.PercentVbus);
	leftRear.setPID(1.0, 0.0, 0.0);
	  
	rightFront = new CANTalon(RobotConstants.RIGHT_FRONT_CANTALON_PORT); // Initialize the CanTalonSRX on device 1.
      	rightFront.setFeedbackDevice(CANTalon.FeedbackDevice.QuadEncoder);
      	rightFront.changeControlMode(CANTalon.ControlMode.PercentVbus);
      	rightFront.setPID(1.0, 0.0, 0.0);
	  
      	rightRear = new CANTalon(RobotConstants.RIGHT_REAR_CANTALON_PORT); // Initialize the CanTalonSRX on device 1.
      	rightRear.setFeedbackDevice(CANTalon.FeedbackDevice.QuadEncoder);
      	rightRear.changeControlMode(CANTalon.ControlMode.PercentVbus);
      	rightRear.setPID(1.0, 0.0, 0.0);

      	hookLeft = new CANTalon(RobotConstants.HOOK_LEFT_CANTALON_PORT); // Initialize the CanTalonSRX on device 1.
      	hookLeft.setFeedbackDevice(CANTalon.FeedbackDevice.QuadEncoder);
      	//hookLeft.changeControlMode(CANTalon.ControlMode.Position);
      	hookLeft.changeControlMode(CANTalon.ControlMode.PercentVbus);
      	hookLeft.setPID(1.0, 0.0, 0.0);

      	hookRight = new CANTalon(RobotConstants.HOOK_RIGHT_CANTALON_PORT); // Initialize the CanTalonSRX on device 1.
      	hookRight.setFeedbackDevice(CANTalon.FeedbackDevice.QuadEncoder);
      	//hookRight.changeControlMode(CANTalon.ControlMode.Position);
      	hookRight.changeControlMode(CANTalon.ControlMode.PercentVbus);
      	hookRight.setPID(1.0, 0.0, 0.0);
      	
      	containerLift = new CANTalon(RobotConstants.CONTAINER_LIFT_CANTALON_PORT); // Initialize the CanTalonSRX on device 1.
      	containerLift.setFeedbackDevice(CANTalon.FeedbackDevice.QuadEncoder);
      	//containerLift.changeControlMode(CANTalon.ControlMode.Position);
      	containerLift.changeControlMode(CANTalon.ControlMode.PercentVbus);
      	containerLift.setPID(1.0, 0.0, 0.0);
      	
      	containerContainer = new CANTalon(RobotConstants.CONTAINER_CONTAINER_CANTALON_PORT); // Initialize the CanTalonSRX on device 1.
      	containerContainer.setFeedbackDevice(CANTalon.FeedbackDevice.QuadEncoder);
      	containerContainer.changeControlMode(CANTalon.ControlMode.PercentVbus);
      	containerContainer.setPID(1.0, 0.0, 0.0);
      	
      	secondContainerContainer = new CANTalon(RobotConstants.CONTAINER_CONTAINER_SECOND_CANTALON_PORT);
      	containerContainer.changeControlMode(CANTalon.ControlMode.PercentVbus);
      	containerContainer.setPID(1.0, 0.0, 0.0);
      	
      	clampPot = new AnalogPotentiometer(RobotConstants.CONTAINERSYSTEM_CLAMP_POTENTIOMETER_PORT);
      	//hookLiftPot = new AnalogPotentiometer(RobotConstants.TOTE_LIFT_POTENTIOMETER_PORT);
      	//containerLiftPot = new AnalogPotentiometer(RobotConstants.CONTAINER_LIFT_POTENTIOMETER_PORT);

	accelerometer = new BuiltInAccelerometer();
	gyro = new Gyro(RobotConstants.MAIN_GYRO_PORT);
	lidar = new LIDAR(I2C.Port.kMXP);
		
	xSlideEncoder = new Encoder(RobotConstants.X_SLIDE_ENCODER_PORT_A,RobotConstants.X_SLIDE_ENCODER_PORT_B);
	ySlideEncoder = new Encoder(RobotConstants.Y_SLIDE_ENCODER_PORT_A,RobotConstants.Y_SLIDE_ENCODER_PORT_B);
	
	server = CameraServer.getInstance();
	server.setQuality(20);
		
	//frame = RobotConstants.FRAME;
	//toteSession = RobotConstants.TOTE_SESSION;
		
	/***************************************
	 * IMU INITIALIZATION
	 ***************************************/
	try {
	    serial_port = new SerialPort(57600,SerialPort.Port.kOnboard );
	          
	    // You can add a second parameter to modify the 
	    // update rate (in hz) from 4 to 100.  The default is 100.
	    // If you need to minimize CPU load, you can set it to a
	    // lower value, as shown here, depending upon your needs.
		  
	    // You can also use the IMUAdvanced class for advanced
	    // features.
		
	    byte update_rate_hz = 20;
	    imu = new IMUAdvanced(serial_port,update_rate_hz);
	} catch (Exception ex) {
	    ex.printStackTrace();
	}
	Timer.delay(0.3);
    }
}
