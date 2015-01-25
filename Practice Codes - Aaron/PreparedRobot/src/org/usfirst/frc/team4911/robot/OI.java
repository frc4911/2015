package org.usfirst.frc.team4911.robot;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.*;
import org.usfirst.frc.team4911.robot.RobotConstants;
import org.usfirst.frc.team4911.robot.commands.SetSolenoid;

public class OI {
    public Joystick mainJoy;
    public Joystick rotationJoy;
    public JoystickButton rightButton1;

    public OI() {
    	mainJoy = new Joystick(RobotConstants.JOYSTICK_MAIN);
    	rotationJoy = new Joystick(RobotConstants.JOYSTICK_ROTATION);
        
        
        rightButton1 = new JoystickButton(mainJoy, 1);
        rightButton1.whenPressed(new SetSolenoid());
    	
    }
    
    /*************************************
     * MAIN JOY METHODS	
     *************************************/
    
    public Joystick getMainJoy(){
    	return mainJoy;
    }
    
    public double getMainJoyX(){
		double pow = 0;
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
    

    /*************************************
     * ROTATION JOY METHODS	
     *************************************/
    public Joystick getRotationJoy(){
    	return mainJoy;
    }
    
    public double getRotationJoyX(){
		double pow = 0;
    	if(Math.abs(rotationJoy.getX()) >= 0.1) {
            pow = Math.round(rotationJoy.getX() * RobotConstants.JOYSTICK_SENSITIVITY) 
            		/ RobotConstants.JOYSTICK_SENSITIVITY;  
        }
    	return pow;
    }
    
    public double getRotationJoyY(){
    	double pow = 0;
    	if(Math.abs(rotationJoy.getY()) >= 0.1) {
            pow = Math.round(rotationJoy.getY() * RobotConstants.JOYSTICK_SENSITIVITY) 
            		/ RobotConstants.JOYSTICK_SENSITIVITY;  
        }
    	return pow;
    }
    
}

