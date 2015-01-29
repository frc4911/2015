package org.usfirst.frc.team4911.robot;

import org.usfirst.frc.team4911.robot.subsystems.PrintSystem;


import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.BuiltInAccelerometer;
import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.Gyro;
import edu.wpi.first.wpilibj.I2C;
import edu.wpi.first.wpilibj.SerialPort;
import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.Timer;
import ExternalLibs.LIDAR;

//import edu.wpi.first.wpilibj.vision.AxisCamera;
//import java.net.Inet4Address;
import com.kauailabs.nav6.frc.IMUAdvanced;

public class RobotMap {
	public static CANTalon leftFront;
	public static CANTalon leftRear;
	public static CANTalon rightFront;
	public static CANTalon rightRear;
	
	public static Solenoid testSolenoid;
	public static Compressor compressor;
	
	public static IMUAdvanced imu;
	public static BuiltInAccelerometer accelerometer;
	public static Gyro gyro;
	public static LIDAR lidar;	
	
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
	      	
		accelerometer = new BuiltInAccelerometer();
		gyro = new Gyro(RobotConstants.MAIN_GYRO_PORT);
		lidar = new LIDAR(I2C.Port.kOnboard);

	    /***************************************
	     * IMU INITIALIZATION
	     ***************************************/
		try {
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
			
		}
		Timer.delay(0.3);
	}
}
