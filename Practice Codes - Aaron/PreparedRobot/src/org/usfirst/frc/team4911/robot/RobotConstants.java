package org.usfirst.frc.team4911.robot;

public class RobotConstants {
    //JOYSTICK CONFIG
    public static final int JOYSTICK_MAIN = 1;
    public static final double JOYSTICK_SENSITIVITY = 10.0;
    public static final int JOYSTICK_POV_NUM = 0;
    public static final int POV_UP = 0;
    public static final int POV_DOWN = 180;
    public static final int POV_LEFT = 270;
    public static final int POV_RIGHT = 90;
    
    public static final int JOYSTICK_OPERATOR = 2;
    public static final int CONTAINER_LIFT_AXIS = 3;
    
    //DRIVE SYSTEM CONSTANTS
    public static final double DRIVESYSTEM_WHEEL_DIAMETER = 4.0; //Measured in Inches
    public static final double DRIVESYSTEM_GEAR_RATIO = 1.0;//39 Teeth on Wheels & 42 Teeth on Encoders
    public static final double DRIVESYSTEM_ENCODER_PULSE_PER_ROTATION = 1024.0;//Number of Pulse per One Rotation of the Encoder
    public static final double DRIVESYSTEM_ENCODER_DISTANCE_PER_PULSE = DRIVESYSTEM_GEAR_RATIO * DRIVESYSTEM_WHEEL_DIAMETER * Math.PI / DRIVESYSTEM_ENCODER_PULSE_PER_ROTATION;
    
    public static final int LEFT_FRONT_CANTALON_PORT = 3;
    public static final int LEFT_REAR_CANTALON_PORT = 4;
    public static final int RIGHT_FRONT_CANTALON_PORT = 7;
    public static final int RIGHT_REAR_CANTALON_PORT = 8;

    public static final double ROTATE_SPEED = 0.75;
    public static final double STANDARD_DRIVE_SPEED = 0.4;
    
    public static final double DRIVESYSTEM_kP = 0.05;  //1.0 / 175.0;
    public static final double DRIVESYSTEM_kI = 0.0;
    public static final double DRIVESYSTEM_kD = 0.0; //0.005
    
    public static final int Y_SLIDE_ENCODER_PORT_A = 3; //Digital IO
    public static final int Y_SLIDE_ENCODER_PORT_B = 4; //Digital IO
    public static final int X_SLIDE_ENCODER_PORT_A = 5; //Digital IO
    public static final int X_SLIDE_ENCODER_PORT_B = 6; //Digital IO
    public static final double DRIVE_ONE_STAGE_ZONE = 0.0;
    
    //HOOK SYSTEM CONSTANTS
    public static final int HOOK_LEFT_CANTALON_PORT = 1;
    public static final int HOOK_RIGHT_CANTALON_PORT = 2;
    
    public static final double HOOKSYSTEM_TOTAL_DISTANCE = 76.0;//Measured in Inches
    public static final double HOOKSYSTEM_WHEEL_DIAMETER = 0.67; //Measured in Inches
    public static final double HOOKSYSTEM_GEAR_RATIO = 1.0 / 64.0;
    public static final double HOOKSYSTEM_ENCODER_PULSE_PER_ROTATION = 1024.0;//Number of Pulse per One Rotation of the Encoder
    public static final double HOOKSYSTEM_ENCODER_DISTANCE_PER_PULSE = HOOKSYSTEM_GEAR_RATIO * HOOKSYSTEM_WHEEL_DIAMETER * Math.PI / HOOKSYSTEM_ENCODER_PULSE_PER_ROTATION;
    
    public static final double TOTE_HEIGHT = 12.1;//Measured in Inches
    
    public static final double TOTE_LIFT_SPEED = 0.5;
    
    public static final double TOTE_GROUND_POSITION = 50.0;
    public static final double TOTE_RELEASE_POSITION = 50.0;
    public static final double TOTE_ACQUIRE_POSITION = 50.0;
    public static final double TOTE_STACK_POSITION = 5.0;
    public static final double TOTE_CLEAR_CONTAINER_POSITION = 50.0;
    
    public static final int TOTE_LIFT_POTENTIOMETER_PORT = 2;
    
    public static final double LIFT_ERROR_TOLERANCE = 0.0;
    
    //CONTAINER SYSTEM CONSTANTS
    public static final int CONTAINER_LIFT_CANTALON_PORT = 5;
    public static final int CONTAINER_CONTAINER_CANTALON_PORT = 6;
    public static final int CONTAINER_CONTAINER_SECOND_CANTALON_PORT = 9;
    
    public static final int CONTAINER_LIFT_POTENTIOMETER_PORT = 1;
    
    //TODO: TEST FOR THESE VALUES
    public static final double CONTAINER_LIFT_GROUND = 	0.0;
    public static final double CONTAINER_LIFT_TEST = 	0.0;
    public static final double CONTAINER_LIFT_TOP = 	0.0;
    public static final double CONTAINER_LIFT_RELEASE = 0.0;
    
    
    public static final double CONTAINERSYSTEM_TOTAL_DISTANCE = 76.0;//Measured in Inches
    public static final double CONTAINERSYSTEM_WHEEL_DIAMETER = 0.67; //Measured in Inches
    public static final double CONTAINERSYSTEM_GEAR_RATIO = 1.0/ 13.5;
    public static final double CONTAINERSYSTEM_ENCODER_PULSE_PER_ROTATION = 1024.0;//Number of Pulse per One Rotation of the Encoder
    public static final double CONTAINERSYSTEM_ENCODER_DISTANCE_PER_PULSE = CONTAINERSYSTEM_GEAR_RATIO * CONTAINERSYSTEM_WHEEL_DIAMETER * Math.PI / CONTAINERSYSTEM_ENCODER_PULSE_PER_ROTATION;
	
    
    public static final double CONTAINERSYSTEM_CLAMP_CLOSING_DISTANCE = 30.0;//Measured in Inches
    public static final double CONTAINERSYSTEM_CLAMP_HOLDING_DISTANCE = 15.0;//Measured in Inches
    public static final double CONTAINERSYSTEM_CLAMP_OPENING_DISTANCE = 15.0;//Measured in Inches
    public static final double CONTAINERSYSTEM_CLAMP_AXLE_CIRCUMFERENCE = 1.0 / 6.0; //Measured in Inches
    public static final double CONTAINERSYSTEM_CLAMP_GEAR_RATIO = 1.0/ 11.0;
    public static final double CONTAINERSYSTEM_CLAMP_ENCODER_PULSE_PER_ROTATION = 1024.0;//Number of Pulse per One Rotation of the Encoder
    public static final double CONTAINERSYSTEM_CLAMP_ENCODER_DISTANCE_PER_PULSE = CONTAINERSYSTEM_CLAMP_GEAR_RATIO * CONTAINERSYSTEM_CLAMP_AXLE_CIRCUMFERENCE / CONTAINERSYSTEM_CLAMP_ENCODER_PULSE_PER_ROTATION;
    
    public static final double CONTAINERSYSTEM_LIFT_SPEED = 0.5;
    public static final double CONTAINERSYSTEM_CLAMP_SPEED = 1.0;
    public static final double CONTAINERSYSTEM_CLAMP_HOLD_POWER = 0.0;
    
    public static final double CONTAINERSYSTEM_CLAMP_LOW_AMPERAGE_THRESHHOLD = 0.15;
    public static final double CONTAINERSYSTEM_CLAMP_HIGH_AMPERAGE_THRESHHOLD = 7.5;
    
    public static final int CONTAINERSYSTEM_CLAMP_POTENTIOMETER_PORT = 0;
    
    public static final int CONTAINERSYSTEM_CLAMP_LIMIT_SWITCH_IN_PORT = 1;//Digital IO
    public static final int CONTAINERSYSTEM_CLAMP_LIMIT_SWITCH_OUT_PORT = 2;//Digital IO
    
    
    //GYRO
    public static final int MAIN_GYRO_PORT = 1;//Analog
    public static final double GYRO_SENSITIVITY = 0.007;
    
    //IMU CONSTANTS
    public static final double YAW_DRIFT_PER_TICK = 0.006; // 0.004 for first chassis, 0.006 for second chassis
    public static final float SENSORSYSTEM_GRAVITATIONAL_ACCELERATION = 32.174f;//ft/s^2
    
    
    //DEBUG SWITCH
    public static final boolean FLAG = true;
    
    //CM to IN conversion (LIDAR)
    public static final double inToCM = 2.54;
    
    //PrintSystem Constants
    public static final int printFrequency = 1;
    public static final boolean STATIC_INFO_ALLOWED = false;
    
    //Autonomous Movement Constants
    public static final double HORIZONTAL_DISTANCE_BETWEEN_TOTES = 400;
    public static final double FORWARD_DISTANCE_TO_AUTO_ZONE = 300;
    public static final double CLEAR_TOTE_LIP_DISTANCE = 10;
    
}
