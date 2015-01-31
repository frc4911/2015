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
        
    	trigger = new JoystickButton(mainJoy, 1);
    	button2 = new JoystickButton(mainJoy, 2);
        button3 = new JoystickButton(mainJoy, 3);
        button4 = new JoystickButton(mainJoy, 4);
        button5 = new JoystickButton(mainJoy, 5);
        button6 = new JoystickButton(mainJoy, 6);
        button11 = new JoystickButton(mainJoy, 11);
        
        trigger.whenPressed(new SetDriveSpeed(1.0, trigger));
        
        button2.whenPressed(new EnableRotate(button2));
        
        button5.whenPressed(new SetGoalHeading(-90.0, button5)); 
        button6.whenPressed(new SetGoalHeading(90.0, button6));
        
        button3.whenPressed(new RotateForTime(10, -0.25)); // rotates for two ticks at 50% power going left
        button4.whenPressed(new RotateForTime(10, 0.25)); // rotates for two ticks at 50% power going right

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
