package org.usfirst.frc.team4911.robot.subsystems;

import edu.wpi.first.wpilibj.command.Subsystem;

import edu.wpi.first.wpilibj.BuiltInAccelerometer;
import edu.wpi.first.wpilibj.Gyro;

import org.usfirst.frc.team4911.robot.RobotConstants;
import org.usfirst.frc.team4911.robot.RobotMap;

import edu.wpi.first.wpilibj.CANTalon;
import com.kauailabs.nav6.frc.IMUAdvanced;

import ExternalLibs.LIDAR;

public class SensorSystem extends Subsystem {
	//public static AxisCamera camera = RobotMap.camera;
	//public static AnalogChannel ultraSonicSensor = RobotMap.ultraSonicSensor;
	public static IMUAdvanced imu = RobotMap.imu;

	public static CANTalon left = RobotMap.leftFront;
	public static CANTalon right = RobotMap.rightFront;
	
	public static BuiltInAccelerometer accelerometer = RobotMap.accelerometer;
	public static Gyro gyro = RobotMap.gyro;
	
	public static LIDAR lidar = RobotMap.lidar;
	
    public void initDefaultCommand() {
        //setDefaultCommand(new MySpecialCommand());
    }
    
    /***************************************
     * IMU METHODS
     ***************************************/
    public void init(){
    	imu.initIMU();
    }
    public boolean isDoneCalibrating(){
    	return !imu.isCalibrating();
    }
    public void zeroYaw(){
    	imu.zeroYaw();
    }
    
    public float getPitch(){
    	return imu.getPitch();
    }
    public float getRoll(){
    	return imu.getRoll();
    }
    public float getYaw(){
    	return imu.getYaw();
    }
    public float getTemp(){
    	return imu.getTempC();
    }
    public float getAccelX(){
    	return imu.getWorldLinearAccelX();
    }
    public float getAccelY(){
    	return imu.getWorldLinearAccelY();
    }
    public float getAccelZ(){
    	return imu.getWorldLinearAccelZ();
    }
    public float getCompass(){
    	return imu.getCompassHeading();
    }
    
    /***************************************
     * Accelerometer Methods
     ***************************************/
    public double getX(){
    	return accelerometer.getX();
    }
    public double getY(){
    	return accelerometer.getY();
    }
    public double getZ(){
    	return accelerometer.getZ();
    }
    
    /***************************************
     * Gyro Methods
     ***************************************/
    public double getAngle(){
    	return gyro.getAngle();
    }
    public double getRate(){
    	return gyro.getRate();
    }
    public void resetGyro(){
    	gyro.reset();
    }
    
    /***************************************
     * Encoder Methods
     ***************************************/
    public double getDistance(){
        return (getLeftDistance() + getRightDistance()) / 2.0;
    }
    
    public double getLeftDistance(){
        return left.getEncPosition() * RobotConstants.ENCODER_DISTANCE_PER_PULSE;
    }
    
    public double getRightDistance(){
        return left.getEncPosition() * RobotConstants.ENCODER_DISTANCE_PER_PULSE;

    }
    
    /***************************************
     * LIDAR Methods
     ***************************************/
    public void start() {
    	lidar.start();
    }
    
    public int getCM() {
    	return lidar.getDistance();
    }
    
    public double getIN() {
    	return ((double)lidar.getDistance()/(RobotConstants.inToCM));
    }
}

