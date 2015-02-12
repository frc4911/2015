package org.usfirst.frc.team4911.robot.subsystems;

import org.usfirst.frc.team4911.robot.RobotConstants;

import org.usfirst.frc.team4911.robot.RobotMap;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.AnalogPotentiometer;

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
	private AnalogPotentiometer clampPot;
	private boolean atLowSpeed = false;
	
	private double previousClampPos;
	
    public void initDefaultCommand() {

    }
    
    public ContainerLiftSystem(){
    	containerLift = RobotMap.containerLift;
    	containerContainer = RobotMap.containerContainer;
    	isLiftBeingUsed = false;
    }
    
    public void runLiftManually(double speed) {
    	containerLift.changeControlMode(CANTalon.ControlMode.PercentVbus);
    	containerLift.set(speed);
    }
    
    public void runLiftToPreset(double pos) {
    	containerLift.changeControlMode(CANTalon.ControlMode.Position);
    	containerLift.set(pos);
    }
    
    public void runClampManuallyForward() {
    	if (containerContainer.getEncPosition() < RobotConstants.CONTAINERSYSTEM_CLAMP_MAX_WIDTH){
			if(!atLowSpeed) {
				if(containerContainer.getOutputCurrent() > RobotConstants.CONTAINERSYSTEM_CLAMP_HIGH_VOLTAGE_THRESHHOLD) {
					containerContainer.set(RobotConstants.CONTAINERSYSTEM_CLAMP_HOLD_POWER);
					atLowSpeed = true;
				}
				else {
					containerContainer.set(RobotConstants.CONTAINERSYSTEM_CLAMP_SPEED);
				}
			}
			else {
				if(containerContainer.getOutputCurrent() < RobotConstants.CONTAINERSYSTEM_CLAMP_LOW_VOLTAGE_THRESHHOLD) {
					containerContainer.set(RobotConstants.CONTAINERSYSTEM_CLAMP_SPEED);
					atLowSpeed = false;
				}
				else {
					containerContainer.set(RobotConstants.CONTAINERSYSTEM_CLAMP_HOLD_POWER);
				}
			}
    	}
    }
    public void stopClamp(){
    	containerContainer.set(0.0);
	}
	
	public void runClampManuallyBackward(){
		if(!switchIn.get() && containerContainer.getEncPosition() > RobotConstants.CONTAINERSYSTEM_CLAMP_MIN_WIDTH){
			containerContainer.set(-RobotConstants.CONTAINERSYSTEM_CLAMP_SPEED);
		}
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
    
    public CANTalon.ControlMode getLiftControlMode() {
    	return containerLift.getControlMode();
    }
    
    public enum ContainerStatus {
    	CLOSE, OPEN, CLAMP;
    }
}

