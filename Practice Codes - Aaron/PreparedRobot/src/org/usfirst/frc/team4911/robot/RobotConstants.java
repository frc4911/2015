package org.usfirst.frc.team4911.robot;

public class RobotConstants {
    //JOYSTICK CONFIG
    public static final int JOYSTICK_MAIN = 1;
    public static final int JOYSTICK_ROTATION = 2;
    public static final double JOYSTICK_SENSITIVITY = 10.0;
    
    //DRIVE SYSTEM CONSTANTS
    public static final double WHEEL_DIAMETER = 4.0; //Measured in Inches
    public static final double GEAR_RATIO = 1.0;//39 Teeth on Wheels & 42 Teeth on Encoders
    public static final double ONE_ROTATION_IN_INCHES = Math.PI * WHEEL_DIAMETER;
    public static final double ENCODER_PULSE_PER_ROTATION = 250.0;//Number of Pulse per One Rotation of the Encoder
    public static final double ENCODER_DISTANCE_PER_PULSE = GEAR_RATIO * WHEEL_DIAMETER * Math.PI / ENCODER_PULSE_PER_ROTATION;
    
    public static final int LEFT_ENCODER_PORT_A = 14;//Digital //1
    public static final int LEFT_ENCODER_PORT_B = 13;//Digital //2
    public static final int RIGHT_ENCODER_PORT_A = 1;//Digital //3
    public static final int RIGHT_ENCODER_PORT_B = 2;//Digital //4

    //DRIVESTRAIGHT CONSTANTS
    public static final double DRIVESTRAIGHT_CORRECTION_CONSTANT = 0.05;
    public static final double AMPLITUDE = 20;
    public static final double RAMP_UP = 5.0;
    public static final double RAMP_DOWN = 1.0;
    public static final double CEILING = 1.0;
    public static final double FLOOR = 0.15;
    
    //PID CONSTANTS
    public static final double TOLERANCE_TIME_LIMIT = 0.250;//seconds
    public static final double TOLERANCE = 1.0;//degrees
    public static final double MINIMUM_TURN_POWER = 0.01;//10% for the Talons
    
    public static final double KP_ANGLE = 3.9;//4.9;
    public static final double KI_ANGLE = 0.009;
    public static final double KD_ANGLE = 5.0;//3.0
    
    public static final double KP_TURNRATE = 0.001;
    public static final double KI_TURNRATE = 0.001;
    public static final double KD_TURNRATE = 0.00025;  
    
    //ULTRASONIC SENSOR
    public static final int ULTRASONIC_SENSOR_PORT = 7;
    public static final double vPerI = 5.002/204.6645;    
    
    //DEBUG SWITCH
    public static final boolean FLAG = false;
}