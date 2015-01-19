package org.usfirst.frc.team4911.robot;

import edu.wpi.first.wpilibj.CANTalon;
//import edu.wpi.first.wpilibj.vision.AxisCamera;
//import java.net.Inet4Address;
//import com.kauailabs.nav6.frc.IMUAdvanced;

public class RobotMap {
	public static CANTalon leftFront;
	public static CANTalon leftRear;
	public static CANTalon rightFront;
	public static CANTalon rightRear;

    //public static Encoder leftEncoder;
    //public static Encoder rightEncoder;
	
	//public static AxisCamera camera;
	//public static AnalogChannel ultraSonicSensor;
	//public static IMUAdvanced imu;
	
	
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
		  
	      rightRear = new CANTalon(3); // Initialize the CanTalonSRX on device 1.
	      rightRear.setFeedbackDevice(CANTalon.FeedbackDevice.QuadEncoder);
	      rightRear.changeControlMode(CANTalon.ControlMode.PercentVbus);
	      rightRear.setPID(1.0, 0.0, 0.0);
	      	      
	      /*
	      leftEncoder = new Encoder(RobotConstants.LEFT_ENCODER_PORT_A, RobotConstants.LEFT_ENCODER_PORT_B, false, EncodingType.k4X);
	      leftEncoder.setDistancePerPulse(RobotConstants.ENCODER_DISTANCE_PER_PULSE);
	      leftEncoder.setPIDSourceParameter(PIDSourceParameter.kRate);
	      leftEncoder.start();
	      
	      rightEncoder = new Encoder(RobotConstants.RIGHT_ENCODER_PORT_A, RobotConstants.RIGHT_ENCODER_PORT_B, false, EncodingType.k4X);
	      rightEncoder.setDistancePerPulse(RobotConstants.ENCODER_DISTANCE_PER_PULSE);
	      rightEncoder.setPIDSourceParameter(PIDSourceParameter.kRate);
	      rightEncoder.start();
	      
	      //Camera IP cannot be part of RobotMap (Static), because it is Dynamic
	      camera = new AxisCamera(Inet4Address.getLocalHost().getHostAddress());
	      
	      ultraSonicSensor = new AnalogChannel(RobotConstants.ULTRASONIC_SENSOR_PORT);
	      
	      
	      try {
	      	serial_port = new SerialPort(57600,SerialPort.Port.kOnboard);
			byte update_rate_hz = 50;//[4 - 100]
			imu = new IMUAdvanced(serial_port, update_rate_hz);
		  } catch( Exception ex ) {

		  } 
		  */
	}
}