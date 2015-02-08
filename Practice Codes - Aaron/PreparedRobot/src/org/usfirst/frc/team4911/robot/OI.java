package org.usfirst.frc.team4911.robot;

import edu.wpi.first.wpilibj.Joystick;

import edu.wpi.first.wpilibj.buttons.JoystickButton;


import org.usfirst.frc.team4911.robot.RobotConstants;
import org.usfirst.frc.team4911.robot.commands.*;

public class OI {
    public Joystick mainJoy;
    public Joystick payloadJoy;
    
    
    public JoystickButton lockButton;
    public JoystickButton button2;
    public JoystickButton button3;
    public JoystickButton button4;
    public JoystickButton button5;
    public JoystickButton button6;
    public JoystickButton button7;
    public JoystickButton button8;
    public JoystickButton button9;
    public JoystickButton button10;
    public JoystickButton button11;
    public JoystickButton trigger;
    
    public Joystick.AxisType leftPayloadJoy;
    public Joystick.AxisType rightPayloadJoy;
    public JoystickButton payloadButton1;
    public JoystickButton payloadButton2;
    public JoystickButton payloadButton3;
    public JoystickButton payloadButton4;
    public JoystickButton payloadButton5;
    public JoystickButton payloadButton6;
    public JoystickButton payloadButton7;
    public JoystickButton payloadButton8;
    

    public OI() {
    	
    	mainJoy = new Joystick(RobotConstants.JOYSTICK_MAIN);
    	payloadJoy = new Joystick(RobotConstants.JOYSTICK_OPERATOR);
        
    	// DEFINING MAINJOY BUTTONS
    	trigger = new JoystickButton(mainJoy, 1);
    	button2 = new JoystickButton(mainJoy, 2);
        button3 = new JoystickButton(mainJoy, 3);
        button4 = new JoystickButton(mainJoy, 4);
        button5 = new JoystickButton(mainJoy, 5);
        button6 = new JoystickButton(mainJoy, 6);
        button7 = new JoystickButton(mainJoy, 7);
        button8 = new JoystickButton(mainJoy, 8);
        button9 = new JoystickButton(mainJoy, 9);
        button10 = new JoystickButton(mainJoy, 10);
        button11 = new JoystickButton(mainJoy, 11);
        
        // DEFINING PAYLOADJOY BUTTONS
        payloadButton1 = new JoystickButton(payloadJoy, 1);
    	payloadButton2 = new JoystickButton(payloadJoy, 2);
        payloadButton3 = new JoystickButton(payloadJoy, 3);
        payloadButton4 = new JoystickButton(payloadJoy, 4);
        payloadButton5 = new JoystickButton(payloadJoy, 5);
    	payloadButton6 = new JoystickButton(payloadJoy, 6);
        payloadButton7 = new JoystickButton(payloadJoy, 7);
        payloadButton8 = new JoystickButton(payloadJoy, 8);
        
        //leftPayloadJoy = payloadJoy.getAxis();
      
        trigger.whenPressed(new EnableRotate(trigger));
        	
        button2.whenPressed(new SetGoalHeading(0));
        button3.whenPressed(new SetGoalHeading(-90.0));
        button4.whenPressed(new SetGoalHeading(90.0));
        	
        button5.whenPressed(new ZeroYaw(button5));
        	
        button8.whenPressed(new RotateForTime(0.05, -0.25));
        button9.whenPressed(new RotateForTime(0.05, 0.25));
        button10.whenPressed(new MaintainDistance());
        
        payloadButton4.whenPressed(new MoveContainerLift(6));
        payloadButton2.whenPressed(new MoveContainerLift(0));
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
    
    public double getMainJoyThrottle() {
    	return ((-1*mainJoy.getThrottle()) + 1.0) * 0.5;
    }
    
    
        
}
