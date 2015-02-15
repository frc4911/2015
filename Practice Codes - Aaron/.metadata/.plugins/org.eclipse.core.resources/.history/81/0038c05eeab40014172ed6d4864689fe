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
	
	public double speed;
	
    public boolean driveSystemConflict;
	
	public OperatorDrive(){
		requires(mecanumDriveSystem);
		requires(sensorSystem);
	}
	
	
	@Override
	protected void initialize() {
		sensorSystem.zeroYaw();
		sensorSystem.clearAccelBuffer();
		
		mecanumDriveSystem.setGoalHeading(0.0);
		driveSystemConflict = false;
		speed = RobotConstants.STANDARD_DRIVE_SPEED;
		runtime = Runtime.getRuntime();
		liftPreset = true;
	}

	@Override
	protected void execute() {
		/*
		//////////////////////////////////////////////////////////////////////////////////////////////////////////////
		//
		// Hook Lift Controls
		//
		//////////////////////////////////////////////////////////////////////////////////////////////////////////////

		if(Math.abs(oi.payloadJoy.getY()) > 0.1) {
			hookLiftSystem.runLiftManually(oi.payloadJoy.getY());
		}
		else {						
			if(oi.payloadJoy.getPOV() == 0 || oi.payloadJoy.getPOV() == 45 || oi.payloadJoy.getPOV() == 315){
				hookLiftSystem.setLiftToPoint(RobotConstants.TOTE_STACK_POSITION);
			}
		
			// Moves the hook lift to the acquire point
			else if(oi.payloadButton5.get()){
				hookLiftSystem.setLiftToPoint(RobotConstants.TOTE_ACQUIRE_POSITION);
			}
			// Moves the hook lift to the ground

			else if(oi.payloadJoy.getPOV() == 180 || oi.payloadJoy.getPOV() == 135 || oi.payloadJoy.getPOV() == 225){
				hookLiftSystem.setLiftToPoint(RobotConstants.TOTE_GROUND_POSITION);
			}
			// Moves the hook lift to release position
			else if(oi.payloadButton7.get()){
				hookLiftSystem.setLiftToPoint(RobotConstants.TOTE_RELEASE_POSITION);
			}
			
			else if(hookLiftSystem.getControlMode() == CANTalon.ControlMode.PercentVbus) {
				hookLiftSystem.runLiftManually(0.0);
			}
		}
		
		///////////////////////////////////////////////////////////////////////////
		//
		// Container Lift Controls
		//
		///////////////////////////////////////////////////////////////////////////
		if(Math.abs(oi.payloadJoy.getZ()) >= 0.1){
			containerLiftSystem.runLiftManually(oi.payloadJoy.getZ());
		}
		else{
			if(oi.payloadButton8.get()) {
				containerLiftSystem.runLiftToPreset(RobotConstants.CONTAINER_LIFT_RELEASE);
			}
			else if(oi.payloadButton6.get()) {
				containerLiftSystem.runLiftToPreset(RobotConstants.CONTAINER_LIFT_TEST);
			}
			else if(oi.payloadButton4.get()) {
				containerLiftSystem.runLiftToPreset(RobotConstants.CONTAINER_LIFT_TOP);
			}
			else if(oi.payloadButton2.get()) {
				containerLiftSystem.runLiftToPreset(RobotConstants.CONTAINER_LIFT_GROUND);
			}
			else if(containerLiftSystem.getLiftControlMode() == CANTalon.ControlMode.PercentVbus) {
				containerLiftSystem.runLiftManually(0.0);
			}
		}
		
		////////////////////////////////////////////////////////////////////////////
		//
		//  Container Clamp Controls
		//
		////////////////////////////////////////////////////////////////////////////
		if(oi.payloadButton3.get()){
			containerLiftSystem.runClampManuallyForward();
		}
		else if(oi.payloadButton1.get()){
			containerLiftSystem.runClampManuallyBackward();
		}
		else{
			containerLiftSystem.stopClamp();
		}
		 */
		//////////////////////////////////////////////////////////////////////////////////////////////////////////
		//
		// Drive System Controls
		//
		//////////////////////////////////////////////////////////////////////////////////////////////////////////

		mecanumDriveSystem.setSpeed(oi.getMainJoyThrottle());
		if(!driveSystemConflict){
			if(oi.getPOV() == RobotConstants.POV_UP){
	        	mecanumDriveSystem.driveWithPID(0.0, -1.0);
			}
	        else if(oi.getPOV() == RobotConstants.POV_DOWN) {
	        	mecanumDriveSystem.driveWithPID(0.0, 1.0);
	        }
	        else if(oi.getPOV() == RobotConstants.POV_LEFT) {
	        	mecanumDriveSystem.driveWithPID(-1.0, 0.0);
	        }
	        else if(oi.getPOV() == RobotConstants.POV_RIGHT) {
	        	mecanumDriveSystem.driveWithPID(1.0, 0.0);
	        } else {		
				double xIn = Math.pow(oi.getMainJoyX(), 3);
				double yIn = Math.pow(oi.getMainJoyY(), 3);	
				mecanumDriveSystem.driveWithPID(xIn, yIn);	        	
	        }
			/*
			if(oi.getPOV() == RobotConstants.POV_UP) {
	        	new PIDAxisDrive(0.0, -1.0, 0.0, oi.mainJoy, RobotConstants.POV_UP).start();
	        	//0.05 for x
	        	//0.3 for y
			}
	        else if(oi.getPOV() == RobotConstants.POV_DOWN) {
	        	new PIDAxisDrive(0.0, 1.0, 0.0, oi.mainJoy, RobotConstants.POV_DOWN).start();
	        	//-0.05 for x
	        	//0.3 for y
	        }
	        else if(oi.getPOV() == RobotConstants.POV_LEFT) {
	        	new PIDAxisDrive(-1.0, 0.0, 0.0, oi.mainJoy, RobotConstants.POV_LEFT).start();
	        	//-0.3 for x
	        	//0.0 for y
	        }
	        else if(oi.getPOV() == RobotConstants.POV_RIGHT) {
	        	new PIDAxisDrive(1.0, 0.0, 0.0, oi.mainJoy, RobotConstants.POV_RIGHT).start();
	        	//0.3 for x
	        	//0.0 for y
	        }*/
		}
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
