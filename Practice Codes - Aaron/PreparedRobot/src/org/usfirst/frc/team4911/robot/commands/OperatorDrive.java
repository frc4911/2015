package org.usfirst.frc.team4911.robot.commands;

import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.command.Command;

import org.usfirst.frc.team4911.robot.subsystems.*;
import org.usfirst.frc.team4911.robot.Robot;
import org.usfirst.frc.team4911.robot.OI;
import org.usfirst.frc.team4911.robot.RobotConstants;

public class OperatorDrive extends Command {
    private MecanumDriveSystem mecanumDriveSystem = Robot.mecanumDriveSystem;
    private SensorSystem sensorSystem = Robot.sensorSystem;
    private ContainerLiftSystem containerLiftSystem = Robot.containerLiftSystem;
    private HookLiftSystem hookLiftSystem = Robot.hookLiftSystem;
    private PrintSystem printSystem = Robot.printSystem;
    private OI oi = Robot.oi;
    private Runtime runtime;
    private boolean liftPreset;
    private double prevPOV;
		
    public double speed;
    public boolean containerClampConflict;
    public boolean hookSystemConflict;	
    public boolean driveSystemConflict;
	
	
    public boolean fieldOriented;
    public boolean prevPressed11;
	
    public OperatorDrive(){
	requires(mecanumDriveSystem);
	requires(sensorSystem);
    }
		
		
    @Override
    protected void initialize() {
	//sensorSystem.zeroYaw();
	//sensorSystem.clearAccelBuffer();
			
	mecanumDriveSystem.setGoalHeading(0.0);
	driveSystemConflict = false;
	hookSystemConflict = false;
	speed = RobotConstants.STANDARD_DRIVE_SPEED;
	runtime = Runtime.getRuntime();
	liftPreset = true;
	containerClampConflict = false;
	hookSystemConflict = false;
	prevPOV = -1.0;
	fieldOriented = true;
	prevPressed11 = false;
    }
	
    @Override
    protected void execute() {
	//////////////////////////////////////////////////////////////////////////////////////////////////////////////
	//
	// Tote Lift Nudge Down
	//
	//////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	if (oi.payloadJoy.getPOV() != -1.0 && prevPOV == -1){
	    new MoveToteLiftForTime(0.3, -0.5).start();
	}
	prevPOV = oi.payloadJoy.getPOV();
	//////////////////////////////////////////////////////////////////////////////////////////////////////////////
	//
	// Hook Lift Controls
	//
	//////////////////////////////////////////////////////////////////////////////////////////////////////////////

	if(!hookSystemConflict){
	    if(Math.abs(oi.payloadJoy.getY()) > 0.25) {
		hookLiftSystem.runLiftManually(oi.payloadJoy.getY());
	    }
	    //manual stop code... use ONLY if presets are not running
	    else {
		hookLiftSystem.runLiftManually(0.0);
	    }
	}
	
	//Preset code
	/*else {						
		if(oi.payloadJoy.getPOV() == 0 || oi.payloadJoy.getPOV() == 45 || oi.payloadJoy.getPOV() == 315){
			hookLiftSystem.setLiftToPoint(RobotConstants.TOTE_STACK_POSITION);
		} else if(oi.payloadButton5.get()){
			// Moves the hook lift to the acquire point			
			hookLiftSystem.setLiftToPoint(RobotConstants.TOTE_ACQUIRE_POSITION);
		} else if(oi.payloadJoy.getPOV() == 180 || oi.payloadJoy.getPOV() == 135 || oi.payloadJoy.getPOV() == 225){
			// Moves the hook lift to the ground
			hookLiftSystem.setLiftToPoint(RobotConstants.TOTE_GROUND_POSITION);
		} else if(oi.payloadButton7.get()){
			// Moves the hook lift to release position
			hookLiftSystem.setLiftToPoint(RobotConstants.TOTE_RELEASE_POSITION);
		} else if(hookLiftSystem.getControlMode() == CANTalon.ControlMode.PercentVbus) {
			hookLiftSystem.runLiftManually(0.0);
		}
	}*/
	
	///////////////////////////////////////////////////////////////////////////
	//
	// Container Lift Controls
	//
	///////////////////////////////////////////////////////////////////////////
	if(Math.abs(oi.payloadJoy.getRawAxis(RobotConstants.CONTAINER_LIFT_AXIS)) >= 0.1){
	    containerLiftSystem.runLiftManually(oi.payloadJoy.getRawAxis(RobotConstants.CONTAINER_LIFT_AXIS));
	} 
	/*else if(oi.payloadButton2.get()) {
		containerLiftSystem.runLiftToPreset(RobotConstants.CONTAINER_LIFT_ONE_TOTE);
	}*/
	else {//if(containerLiftSystem.getLiftControlMode() == CANTalon.ControlMode.PercentVbus){
	    //manual stop code...use ONLY if presets are not running
	    containerLiftSystem.runLiftManually(0.0);
	}
	//printSystem.print("EncoderPos: " + containerLiftSystem.getContainerLift().getPosition());
	/*
	//Potentiometer Code  
	if(oi.payloadJoy.getPOV() != -1) {
		containerLiftSystem.setTargetPosition(RobotConstants.CONTAINER_LIFT_TOP);
	}
	containerLiftSystem.updateLift();
	*/
	//Preset Encoder Code
	/*else {
		if(oi.payloadButton8.get()) {
			//For both STEP and 
			containerLiftSystem.runLiftToPreset(RobotConstants.CONTAINER_LIFT_ONE_TOTE);
		}
		else if(oi.payloadButton4.get()) {
			containerLiftSystem.runLiftToPreset(RobotConstants.CONTAINER_LIFT_TOP);
		}
		else if(oi.payloadButton2.get()) {
			containerLiftSystem.runLiftToPreset(RobotConstants.CONTAINER_LIFT_TRAVEL_HEIGHT);
		}
		else if(containerLiftSystem.getLiftControlMode() == CANTalon.ControlMode.PercentVbus) {
			containerLiftSystem.runLiftManually(0.0);
		}
	}*/
	
	////////////////////////////////////////////////////////////////////////////
	//
	//  Container Clamp Controls
	//
	////////////////////////////////////////////////////////////////////////////
	if(!containerClampConflict) {
	    if(oi.payloadButton8.get()){
		containerLiftSystem.runClampManuallyBackward();
	    }
	    else if(oi.payloadButton7.get()){
		containerLiftSystem.runClampManuallyForward();
	    }
	    else if(oi.payloadButton5.get()) {
		containerLiftSystem.stowClamp();
	    }
	    else{
		containerLiftSystem.stopClamp();
	    }
	}
	//////////////////////////////////////////////////////////////////////////////////////////////////////////
	//
	// Drive System Controls
	//
	//////////////////////////////////////////////////////////////////////////////////////////////////////////
		
	//Setting the Master Drive Speed
	mecanumDriveSystem.setSpeed(oi.getMainJoyThrottle());
		
	//Toggling FieldOrientedDrive
	if(oi.button11.get()){
	    if(!prevPressed11){
		fieldOriented = !fieldOriented;
		prevPressed11 = true;
	    }
	} else {
	    prevPressed11 = false;
	}
		
	if(!driveSystemConflict){
	    double xIn = Math.pow(oi.getMainJoyX(), 3);
	    double yIn = Math.pow(oi.getMainJoyY(), 3);	
	    double rIn = Math.pow(oi.getMainJoyZ(), 3);
			
	    if(fieldOriented){	
		if(oi.getPOV() == RobotConstants.POV_UP){
		    xIn = 0.0;
		    yIn = -1.0;
		}
		else if(oi.getPOV() == RobotConstants.POV_DOWN) {
		    xIn = 0.0;
		    yIn = 1.0;
		}
		else if(oi.getPOV() == RobotConstants.POV_LEFT) {
		    xIn = -1.0;
		    yIn = 0.0;
		}
		else if(oi.getPOV() == RobotConstants.POV_RIGHT) {
		    xIn = 1.0;
		    yIn = 0.0;
		} 
				
		if(oi.trigger.get()){
		    mecanumDriveSystem.drive(xIn, yIn, rIn);
		    mecanumDriveSystem.setGoalHeading((double)(sensorSystem.getYawWithCompensation()));
		} else {
		    mecanumDriveSystem.driveWithPID(xIn, yIn);
		}	
	    }
	    else {	
		if(!oi.trigger.get()){
		    rIn = 0.0;
		}
		mecanumDriveSystem.driveRobotOriented(xIn, yIn, rIn);
	    }
	}
	//printSystem.print("Container Pot: " + sensorSystem.getPot());
	//printSystem.print("ContainerContainer Current: " + containerLiftSystem.getContainerContainer().getOutputCurrent());
	//printSystem.print("Follower Current: " + containerLiftSystem.getSecondCC().getOutputCurrent());
	//printSystem.print("Low Speed: " + containerLiftSystem.lowSpeed());
	//printSystem.print("Tote conflict: " + hookSystemConflict);
	//printSystem.print("ContainerConflict: " + containerClampConflict);
	//printSystem.print("Container Limit: " + containerLiftSystem.getContainerContainer().isFwdLimitSwitchClosed());

	printSystem.print("FieldOriented:\t" + fieldOriented );
    }
	
    @Override
    protected boolean isFinished() {
	return false;
    }
	
    @Override
    protected void end() {		
	this.cancel();
    }
	
    @Override
    protected void interrupted() {
    }

}
