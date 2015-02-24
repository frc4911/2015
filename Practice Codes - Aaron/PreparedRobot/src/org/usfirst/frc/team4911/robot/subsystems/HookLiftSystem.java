package org.usfirst.frc.team4911.robot.subsystems;

import org.usfirst.frc.team4911.robot.Robot;
import org.usfirst.frc.team4911.robot.RobotMap;
import org.usfirst.frc.team4911.robot.RobotConstants;
import org.usfirst.frc.team4911.robot.subsystems.SensorSystem;

import edu.wpi.first.wpilibj.CANTalon.ControlMode;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.CANTalon;

public class HookLiftSystem extends Subsystem {
	private CANTalon leftHook;
	private CANTalon rightHook;
	private boolean isBeingUsed;
	private double targetPos;
	private SensorSystem sensorSystem = Robot.sensorSystem;
	
    public void initDefaultCommand() {
    	
    }
    
    public HookLiftSystem(){
    	leftHook = RobotMap.hookLeft;
    	rightHook = RobotMap.hookRight;
    	isBeingUsed = false;
    	targetPos = sensorSystem.getHookLiftPot(); //TODO: add potentiometer and set targetPos to current reading
    }
    
    public void runLiftManually(double speed) {
    	leftHook.changeControlMode(CANTalon.ControlMode.PercentVbus);
    	rightHook.changeControlMode(CANTalon.ControlMode.Follower);
    	leftHook.set(-speed);
    	rightHook.set(RobotConstants.HOOK_LEFT_CANTALON_PORT);
    }
    
    public void updateLift(double joyVal) {
	leftHook.changeControlMode(ControlMode.PercentVbus);
	rightHook.changeControlMode(ControlMode.Follower);
	if(Math.abs(joyVal) >= 0.1) {
	    isBeingUsed = false;
	}
	if(isBeingUsed) {
	    double error = targetPos - sensorSystem.getHookLiftPot();
	    if(Math.abs(error) > RobotConstants.LIFT_ERROR_TOLERANCE) {
		leftHook.set(error * 1.0); //TODO: Fix this scaler value
	    }
	    else {
		leftHook.set(0.0);
		isBeingUsed = false;
	    }
	}
	else {
	    leftHook.set(-joyVal);
	}
	rightHook.set(RobotConstants.HOOK_LEFT_CANTALON_PORT);
    }
    
    public void setTargetPos(double pos) {
	isBeingUsed = true;
	targetPos = pos;
    }
    
    public void setLiftToPoint(double pos) {
    	
    	//Encoder code
    	leftHook.changeControlMode(CANTalon.ControlMode.Position);
    	rightHook.changeControlMode(CANTalon.ControlMode.Follower);
    	leftHook.set(pos);
    	rightHook.set(RobotConstants.HOOK_LEFT_CANTALON_PORT);

    }
    

    public void liftViaPercent(double position){
    	leftHook.set(RobotConstants.HOOKSYSTEM_TOTAL_DISTANCE * position / RobotConstants.HOOKSYSTEM_ENCODER_DISTANCE_PER_PULSE);
    	rightHook.set(RobotConstants.HOOK_LEFT_CANTALON_PORT);
    }
    
    //This will move the Lift right upto the lips of the Tote specified
    //toteNum = level of the tote on the ground
    public void liftViaTote(double toteNum){
    	if(toteNum > 0){
    		setLiftToPoint(RobotConstants.TOTE_HEIGHT * (toteNum - 1) / RobotConstants.HOOKSYSTEM_ENCODER_DISTANCE_PER_PULSE);
    	}
    }
    
    //returns values in inches from the ground
    public double getDistance(){
    	return (leftHook.get() + rightHook.get()) / RobotConstants.HOOKSYSTEM_ENCODER_DISTANCE_PER_PULSE;
    }
    

    public boolean isBeingUsed(){
    	return isBeingUsed;
    }
    
    public void setBeingused(boolean b){
    	this.isBeingUsed = b;
    }
    
    public CANTalon getLeft(){
    	return leftHook;
    }
    
    public CANTalon getRight(){
    	return rightHook;
    }
    
    public CANTalon.ControlMode getControlMode() {
    	return leftHook.getControlMode();
    }
}

