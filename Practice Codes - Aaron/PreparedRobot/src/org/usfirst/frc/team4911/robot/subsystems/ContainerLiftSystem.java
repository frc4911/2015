package org.usfirst.frc.team4911.robot.subsystems;

import org.usfirst.frc.team4911.robot.RobotConstants;

import org.usfirst.frc.team4911.robot.RobotMap;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.CANTalon;

/**
 *
 */
public class ContainerLiftSystem extends Subsystem {
	private CANTalon containerLift;
	private CANTalon containerContainer;
	private DigitalInput switchIn;
	private DigitalInput switchOut;
	private boolean isLiftBeingUsed;
	private boolean isClampBeingUsed;
	
    public void initDefaultCommand() {

    }
    
    public ContainerLiftSystem(){
    	containerLift = RobotMap.containerLift;
    	containerContainer = RobotMap.containerContainer;
    	isLiftBeingUsed = false;
    }
    
    public void liftViaPercent(double position){
    	containerLift.set(RobotConstants.CONTAINERSYSTEM_TOTAL_DISTANCE * position / RobotConstants.CONTAINERSYSTEM_ENCODER_DISTANCE_PER_PULSE);
    }

    //This will move the bottom of the container to the height of the tote specified 
    //toteNum = number of totes below the Container you want to lift
    public void liftViaTote(double toteNum){
    	if(toteNum >= 0){
    		containerLift.set(RobotConstants.TOTE_HEIGHT * toteNum / RobotConstants.CONTAINERSYSTEM_ENCODER_DISTANCE_PER_PULSE);
    	}
    }
    
    //True = switch Triggered
    //False = switch Not Triggered
    public boolean getSwitchIn(){
    	return switchIn.get();
    }
    
    //True = switch Triggered
    //False = switch Not Triggered
    public boolean getSwitchOut(){
    	return switchOut.get();
    }

    //input < 0 : moving In/ Clamping
    //input > 0 : moving Out/ Releasing
    public void driveClamp(double input){
    	if((getSwitchIn() && input < 0.0)  || (getSwitchOut() && input > 0.0)){
    		containerContainer.set(0.0);
    	} else {
    		containerContainer.set(input);
    	}
    }
    
    public boolean isLiftBeingUsed(){
    	return isLiftBeingUsed;
    }
    
    public void setLiftBeingUsed(boolean b){
    	this.isLiftBeingUsed = b;
    }
    
    public boolean isClampBeingUsed(){
    	return isClampBeingUsed;
    }
    
    public void setClampBeingUsed(boolean b){
    	this.isClampBeingUsed = b;
    }
    
    //CHECK IF THE SIGNS ARE CORRECT
    //returns values in inches from the ground
    public double getLiftDistance(){
    	return containerLift.get() / RobotConstants.CONTAINERSYSTEM_ENCODER_DISTANCE_PER_PULSE;
    }

    //CHECK IF THE SIGNS ARE CORRECT
    //returns values in inches from the ground
    public double getClampDistance(){
    	return containerLift.get() / RobotConstants.CONTAINERSYSTEM_CLAMP_ENCODER_DISTANCE_PER_PULSE;
    }
    
    public CANTalon getContainerLift(){
    	return containerLift;
    }
    public CANTalon getContainerContainer(){
    	return containerContainer;
    }
    
    public enum ContainerStatus {
    	CLOSE, OPEN, CLAMP;
    }
}

