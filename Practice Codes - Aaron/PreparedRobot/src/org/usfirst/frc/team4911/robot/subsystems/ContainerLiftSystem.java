package org.usfirst.frc.team4911.robot.subsystems;

import org.usfirst.frc.team4911.robot.RobotConstants;
import org.usfirst.frc.team4911.robot.subsystems.SensorSystem;
import org.usfirst.frc.team4911.robot.Robot;
import org.usfirst.frc.team4911.robot.RobotMap;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.AnalogPotentiometer;
import edu.wpi.first.wpilibj.CANTalon.ControlMode;

/**
 *
 */
public class ContainerLiftSystem extends Subsystem {
	private CANTalon containerLift;
	private CANTalon containerContainer;
	private CANTalon secondContainerContainer;
	private SensorSystem sensorSystem = Robot.sensorSystem;
	private boolean atLowSpeed = false;
	private double targetPosition;
	private boolean usingLift;
	
    public void initDefaultCommand() {
	
    }
    
    public ContainerLiftSystem(){
    	containerLift = RobotMap.containerLift;
    	containerContainer = RobotMap.containerContainer;
    	secondContainerContainer = RobotMap.secondContainerContainer;
    	targetPosition = 0.0;//sensorSystem.getContainerLiftPot();
    	usingLift = false;
    }
    
    public void setTargetPosition(double pos) {
		if(pos <= RobotConstants.CONTAINER_LIFT_TOP && pos >= RobotConstants.CONTAINER_LIFT_GROUND) {
		    targetPosition = pos;
		    usingLift = true;
		}
    }
    
    public void updateLift(double joyVal) {
		containerLift.changeControlMode(CANTalon.ControlMode.PercentVbus);
		if(Math.abs(joyVal) >= 0.1) {
		    usingLift = false;
		}
		if(usingLift) {
		    double error = targetPosition - 0.0;
		    if(Math.abs(error) > RobotConstants.LIFT_ERROR_TOLERANCE) {
			containerLift.set(error * 1.0); //TODO: Fix this scaler vlaue
		    }
		    else {
			containerLift.set(0.0);
			usingLift = false;
		    }
		}
		else {
		    containerLift.set(joyVal);
		}
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
    	containerContainer.changeControlMode(ControlMode.PercentVbus);
    	secondContainerContainer.changeControlMode(ControlMode.Follower);

    	if(sensorSystem.getPot() < 0.42) {
    	    containerContainer.set(RobotConstants.CONTAINERSYSTEM_CLAMP_SPEED);
    	}
    	else {
    	    if(atLowSpeed) {
    		if(containerContainer.getOutputCurrent() < 5.0) {
    		    containerContainer.set(RobotConstants.CONTAINERSYSTEM_CLAMP_SPEED);
    		    atLowSpeed = false;
    		}
    		else {
    		    containerContainer.set(RobotConstants.CONTAINERSYSTEM_CLAMP_HOLD_POWER);
    		}
    	    }
    	    else {
    		if(containerContainer.getOutputCurrent() > 25.0) {
    		    containerContainer.set(RobotConstants.CONTAINERSYSTEM_CLAMP_HOLD_POWER);
    		    atLowSpeed = true;
    		}
    		else {
    		    containerContainer.set(RobotConstants.CONTAINERSYSTEM_CLAMP_SPEED);
    		}
    	    }
    	}
    	secondContainerContainer.set(RobotConstants.CONTAINER_CONTAINER_CANTALON_PORT);
    }
    
    public void stopClamp(){
    	containerContainer.changeControlMode(ControlMode.PercentVbus);
    	secondContainerContainer.changeControlMode(ControlMode.Follower);
    	containerContainer.set(0.0);
    	secondContainerContainer.set(RobotConstants.CONTAINER_CONTAINER_CANTALON_PORT);
    }
	
    public void runClampManuallyBackward(){
		containerContainer.changeControlMode(ControlMode.PercentVbus);
		secondContainerContainer.changeControlMode(ControlMode.Follower);
		if(sensorSystem.getPot() < 0.720) {
		    containerContainer.set(-RobotConstants.CONTAINERSYSTEM_CLAMP_SPEED);
		}
		else {
		    containerContainer.set(0.0);
		}
		secondContainerContainer.set(RobotConstants.CONTAINER_CONTAINER_CANTALON_PORT);
	    }
	    
	    public boolean lowSpeed() {
		return atLowSpeed;
    }
	 
    public void setLowSpeed(boolean on) {
    	atLowSpeed = on;
    }
	
    public CANTalon getContainerLift(){
    	return containerLift;
    }
    public CANTalon getContainerContainer(){
    	return containerContainer;
    }
    
    public CANTalon getSecondCC() {
    	return secondContainerContainer;
    }
    
    public CANTalon.ControlMode getLiftControlMode() {
    	return containerLift.getControlMode();
    }
}

