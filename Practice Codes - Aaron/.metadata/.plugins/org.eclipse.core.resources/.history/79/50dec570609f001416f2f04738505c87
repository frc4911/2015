package org.usfirst.frc.team4911.robot;

public class RobotConstants {
    //JOYSTICK CONFIG
    public static final int JOYSTICK_LEFT = 1;
    public static final int JOYSTICK_RIGHT = 2;
    public static final int JOYSTICK_PAYLOAD = 3;
    public static final double JOYSTICK_SENSITIVITY = 10.0;
    
    //DRIVE SYSTEM CONSTANTS
    public static final double WHEEL_DIAMETER = 4.0; //Measured in Inches
    public static final double GEAR_RATIO = 1.0;//39 Teeth on Wheels & 42 Teeth on Encoders
    public static final double ONE_ROTATION_IN_INCHES = Math.PI * WHEEL_DIAMETER;
    public static final double ENCODER_PULSE_PER_ROTATION = 250.0;//Number of Pulse per One Rotation of the Encoder
    public static final double ENCODER_DISTANCE_PER_PULSE = GEAR_RATIO * WHEEL_DIAMETER * Math.PI / ENCODER_PULSE_PER_ROTATION;
    
    public static final int REAR_LEFT_TALON_PORT = 5;//PWM //7
    public static final int FRONT_LEFT_TALON_PORT = 10;//PWM //8
    public static final int REAR_RIGHT_TALON_PORT = 2;//PWN //9
    public static final int FRONT_RIGHT_TALON_PORT = 6;//PWN //10
    
    public static final int LEFT_ENCODER_PORT_A = 14;//Digital //1
    public static final int LEFT_ENCODER_PORT_B = 13;//Digital //2
    public static final int RIGHT_ENCODER_PORT_A = 1;//Digital //3
    public static final int RIGHT_ENCODER_PORT_B = 2;//Digital //4
    
    //SHOOTER SYSTEM CONSTANTS
    public static final int SHOOTER_TALON_PORT = 1;//PWN //7
    
    public static final int SHOOTER_ENCODER_PORT_A = 11;//Digital
    public static final int SHOOTER_ENCODER_PORT_B = 12;//Digital
    
    public static final double DEGREES_PER_PULSE = 360.0 / ENCODER_PULSE_PER_ROTATION;//1.44;
    
    public static final int SWITCH_PORT = 4;//Digital//5
    
    //COLLECTOR SYSTEM CONSTANTS
    public static final int COLLECTOR_AXELE_TALON_PORT_RIGHT = 3;//PWN 
    public static final int COLLECTOR_AXELE_TALON_PORT_LEFT = 7;//PWN //2
    public static final int COLLECTOR_WHEEL_TALON_PORT = 4;//PWN    
    
    public static final int COLLECTOR_POTENTIOMETER_CHANNEL = 2;//Analog
    public static final double POT_MIN_VOLT = 0.51627;
    public static final double VD = 0.022524;
    public static final double COLLECTER_MAX_ANGLE = 115.0;
    public static final double COLLECTER_MIN_ANGLE = 50.0;
    public static final double COLLECTER_HOLD_ANLGE = 90.0;
    public static final double COLLECTER_MIN_VOLT = 2.0;
    public static final double COLLECTER_MAX_VOLT = 4.0;
    
    //GYRO
    public static final int MAIN_GYRO_PORT = 1;//Analog
    public static final double GYRO_SENSITIVITY = 0.007;

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
    
    //PNEUMATIC COLLECTOR
    public static final int PNEUMATIC_COLLECTOR_SWITCH_CHANNEL_PORT = 5;//Digital IO ???
    public static final int PNEUMATIC_COLLECTOR_RELAY_CHANNEL_PORT = 1;//Relay
    public static final int PNEUMATIC_COLLECTOR_IN = 1;//Solenoid
    public static final int PNEUMATIC_COLLECTOR_OUT = 2;//Solenoid
    
    //DEBUG SWITCH
    public static final boolean DEBUG_SWITCH = false;
}
