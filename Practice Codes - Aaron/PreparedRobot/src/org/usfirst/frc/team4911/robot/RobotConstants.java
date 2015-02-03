package org.usfirst.frc.team4911.robot;

public class RobotConstants {
    //JOYSTICK CONFIG
    public static final int JOYSTICK_MAIN = 1;
    public static final double JOYSTICK_SENSITIVITY = 10.0;
    public static final int JOYSTICK_POV_NUM = 0;
    public static final int POV_UP = 180;
    public static final int POV_DOWN = 0;
    public static final int POV_LEFT = 90;
    public static final int POV_RIGHT = 270;
    
    //JOYSTICK SWAP
    public static final boolean USING_BEN_JOY = true;
    
    //DRIVE SYSTEM CONSTANTS
    public static final double WHEEL_DIAMETER = 4.0; //Measured in Inches
    public static final double GEAR_RATIO = 1.0;//39 Teeth on Wheels & 42 Teeth on Encoders
    public static final double ENCODER_PULSE_PER_ROTATION = 1024.0;//Number of Pulse per One Rotation of the Encoder
    public static final double ENCODER_DISTANCE_PER_PULSE = GEAR_RATIO * WHEEL_DIAMETER * Math.PI / ENCODER_PULSE_PER_ROTATION;
    
    public static final int LEFT_FRONT_CANTALON_PORT = 2;
    public static final int LEFT_REAR_CANTALON_PORT = 1;
    public static final int RIGHT_FRONT_CANTALON_PORT = 3;
    public static final int RIGHT_REAR_CANTALON_PORT = 4;
    
    public static final double ROTATE_SPEED = 0.30;
    public static final double STANDARD_DRIVE_SPEED = 0.4;
    
    
    //DRIVESTRAIGHT CONSTANTS
    public static final double DRIVESTRAIGHT_CORRECTION_CONSTANT = 0.05;
    public static final double AMPLITUDE = 20;
    public static final double RAMP_UP = 5.0;
    public static final double RAMP_DOWN = 1.0;
    public static final double CEILING = 1.0;
    public static final double FLOOR = 0.15;
    
    //PID CONSTANTS
    public static final double kP = 1.0 / 175.0;
    public static final double kI = 0.00000;
    public static final double kD = 0.005;
    
    //GYRO
    public static final int MAIN_GYRO_PORT = 1;//Analog
    public static final double GYRO_SENSITIVITY = 0.007;
    
    //IMU CONSTANTS
    public static final double YAW_DRIFT_PER_TICK = 0.004;
    
    //CAMERA
    public static final int CAMERA_SERVO_PORT = 0;
    public static final double CAMERA_SERVO_LEFT = 0.0;
    public static final double CAMERA_SERVO_MID = 0.5;
    public static final double CAMERA_SERVO_RIGHT = 1.0;
    
    //DEBUG SWITCH
    public static final boolean FLAG = true;
    
    //CM to IN conversion (LIDAR)
    public static final double inToCM = 2.54;
    
    //PrintSystem Constants
    public static final int printFrequency = 1;
    public static final boolean STATIC_INFO_ALLOWED = false;
    
}
