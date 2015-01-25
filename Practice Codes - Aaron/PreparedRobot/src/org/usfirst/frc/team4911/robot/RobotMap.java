package org.usfirst.frc.team4911.robot;

import edu.wpi.first.wpilibj.BuiltInAccelerometer;

import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.Gyro;
import edu.wpi.first.wpilibj.I2C;
import edu.wpi.first.wpilibj.SerialPort;
import edu.wpi.first.wpilibj.CameraServer;

import ExternalLibs.LIDAR;

//import edu.wpi.first.wpilibj.vision.AxisCamera;
//import java.net.Inet4Address;
import com.kauailabs.nav6.frc.IMUAdvanced;

public class RobotMap {
	public static CANTalon leftFront;
	public static CANTalon leftRear;
	public static CANTalon rightFront;
	public static CANTalon rightRear;
	
	//public static AxisCamera camera;
	//public static AnalogChannel ultraSonicSensor;
	public static IMUAdvanced imu;

	public static BuiltInAccelerometer accelerometer;
	public static Gyro gyro;
	public static LIDAR	   lidar;
	
	public static CameraServer camera;
	private static SerialPort serial_port;
	
	public static void init(){
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
	      rightFront.reverseOutput(true);
		  
	      rightRear = new CANTalon(3); // Initialize the CanTalonSRX on device 1.
	      rightRear.setFeedbackDevice(CANTalon.FeedbackDevice.QuadEncoder);
	      rightRear.changeControlMode(CANTalon.ControlMode.PercentVbus);
	      rightRear.setPID(1.0, 0.0, 0.0);
	      rightRear.reverseOutput(true);
	      	      
	      /*	      
	      //Camera IP cannot be part of RobotMap (Static), because it is Dynamic
	      camera = new AxisCamera(Inet4Address.getLocalHost().getHostAddress());
	      
	      ultraSonicSensor = new AnalogChannel(RobotConstants.ULTRASONIC_SENSOR_PORT);
	      
	      
		  */
	      try {
	    	  serial_port = new SerialPort(57600,SerialPort.Port.kOnboard);
	    	  byte update_rate_hz = 50;//[4 - 100]
	    	  imu = new IMUAdvanced(serial_port, update_rate_hz);
		  } catch( Exception ex ) {
		  } 
	      accelerometer = new BuiltInAccelerometer();

	      gyro = new Gyro(RobotConstants.MAIN_GYRO_PORT);
	      gyro.setSensitivity(RobotConstants.GYRO_SENSITIVITY);
	      gyro.initGyro();
	      
	      lidar = new LIDAR(I2C.Port.kOnboard);
	      
	      camera = CameraServer.getInstance();
	      camera.setQuality(RobotConstants.CAMERA_QUALITY);
	}
}
