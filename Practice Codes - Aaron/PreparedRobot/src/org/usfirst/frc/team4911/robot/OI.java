package org.usfirst.frc.team4911.robot;

import edu.wpi.first.wpilibj.Joystick;

import edu.wpi.first.wpilibj.buttons.JoystickButton;


import org.usfirst.frc.team4911.robot.RobotConstants;
import org.usfirst.frc.team4911.robot.commands.*;

public class OI {
    public Joystick mainJoy;
    
    public JoystickButton lockButton;
    public JoystickButton button2;
    public JoystickButton button3;
    public JoystickButton button4;
    public JoystickButton button5;
    public JoystickButton button6;
    public JoystickButton button11;
    public JoystickButton trigger;
    

    

    public OI() {
    	mainJoy = new Joystick(RobotConstants.JOYSTICK_MAIN);
        
    	//lockButton = new JoystickButton(mainJoy, 2);
    	//lockButton.whenPressed(new UnlockGrid());
    	//lockButton.whenReleased(new LockGrid());
    	/*
    	driveStraightForward = new DriveStraightForward();
    	driveStraightBackward = new DriveStraightBackward();
    	strafeLeft = new StrafeLeft();
    	strafeRight = new StrafeRight();
        */
    	
    	trigger = new JoystickButton(mainJoy, 1);
    	button2 = new JoystickButton(mainJoy, 2);
        button3 = new JoystickButton(mainJoy, 3);
        button4 = new JoystickButton(mainJoy, 4);
        button5 = new JoystickButton(mainJoy, 5);
        button6 = new JoystickButton(mainJoy, 6);
        button11 = new JoystickButton(mainJoy, 11);
        /*
        button3.whenPressed(driveStraightForward);        
        button4.whenPressed(driveStraightBackward);        
        button5.whenPressed(strafeLeft);        
        button6.whenPressed(strafeRight);
        */
        
        /*button3.whenPressed(new PIDAxisDrive(0.0 , -0.3, 0.0, button3));        
        button4.whenPressed(new PIDAxisDrive(0.0 , 0.3, 0.0, button4));        
        button5.whenPressed(new PIDAxisDrive(-0.3 , 0.0, 0.0, button5));        
        button6.whenPressed(new PIDAxisDrive(0.3 , 0.0, 0.0, button6));*/
        
        trigger.whenPressed(new SetDriveSpeed(1.0, trigger));
        
        button2.whenPressed(new EnableRotate(button2));
        
        button3.whenPressed(new SetGoalHeading(0, button3));
        button4.whenPressed(new SetGoalHeading(180, button4));
        button6.whenPressed(new SetGoalHeading(90, button6));
        button5.whenPressed(new SetGoalHeading(-90, button5));

        button11.whenPressed(new ZeroYaw(button11));
        
        
        
    }
    
    /*************************************
     * MAIN JOY METHODS	
     *************************************/
    
    public Joystick getMainJoy(){
    	return mainJoy;
    }
    
    public double getMainJoyX(){
		double pow = 0;
		// roneckor - does this return a value 0 to 1?
    	if(Math.abs(mainJoy.getX()) >= 0.1) {
            pow = Math.round(mainJoy.getX() * RobotConstants.JOYSTICK_SENSITIVITY) 
            		/ RobotConstants.JOYSTICK_SENSITIVITY;  
        }
    	return pow;
    }
    
    public double getMainJoyY(){
    	double pow = 0;
    	if(Math.abs(mainJoy.getY()) >= 0.1) {
            pow = Math.round(mainJoy.getY() * RobotConstants.JOYSTICK_SENSITIVITY) 
            		/ RobotConstants.JOYSTICK_SENSITIVITY;  
        }
    	return pow;
    }
    
    public double getMainJoyZ() {
    	double pow = 0;
    	if(Math.abs(mainJoy.getZ()) >= 0.1) {
            pow = Math.round(mainJoy.getZ() * RobotConstants.JOYSTICK_SENSITIVITY) 
            		/ RobotConstants.JOYSTICK_SENSITIVITY;  
        }
    	return pow;
    }
    
    public int getPOV() {
    	return mainJoy.getPOV(RobotConstants.JOYSTICK_POV_NUM);
    }
        
}
