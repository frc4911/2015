package org.usfirst.frc.team4911.robot.commands;

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
		mecanumDriveSystem.setGoalHeading(0.0);
		driveSystemConflict = false;
		speed = RobotConstants.STANDARD_DRIVE_SPEED;
		runtime = Runtime.getRuntime();
		liftPreset = true;
	}

	@Override
	protected void execute() {
		
<<<<<<< HEAD
		//////////////////////////////////////////////////////////////////////////////////////////////////////////////
		//
		// Hook Lift Controls
		//
		//////////////////////////////////////////////////////////////////////////////////////////////////////////////
=======
		// Moves the hook lift to the stack point
		if(oi.payloadJoy.getPOV() == 315 || oi.payloadJoy.getPOV() == 0 || oi.payloadJoy.getPOV() == 45){
			hookLiftSystem.setLiftToPoint(RobotConstants.TOTE_STACK_POSITION);
		}
>>>>>>> d73d0dd2020052a47cf406cd36cf5dc547774a9c
		
		
<<<<<<< HEAD
		if(liftPreset) {
			// Moves the hook lift to the stack point
			if(oi.payloadJoy.getPOV() == 0 || oi.payloadJoy.getPOV() == 45 || oi.payloadJoy.getPOV() == 315){
				hookLiftSystem.setLiftToPoint(RobotConstants.TOTE_STACK_POSITION);
			}
		
			// Moves the hook lift to the aquire point
			else if(oi.payloadButton5.get()){
				hookLiftSystem.setLiftToPoint(RobotConstants.TOTE_AQUIRE_POSITION);
			}
		
			// Moves the hook lift to the release point
			else if(oi.payloadJoy.getPOV() == 180 || oi.payloadJoy.getPOV() == 135 || oi.payloadJoy.getPOV() == 225){
				hookLiftSystem.setLiftToPoint(RobotConstants.TOTE_GROUND_POSITION);
			}
				
			// Grounds the hook lift
			else if(oi.payloadButton7.get()){
				hookLiftSystem.setLiftToPoint(RobotConstants.TOTE_RELEASE_POSITION);
			}
			else {
				liftPreset = false;
			}
=======
		// Moves the hook lift to the ground
		if(oi.payloadJoy.getPOV() == 135 || oi.payloadJoy.getPOV() == 180 || oi.payloadJoy.getPOV() == 225){
			hookLiftSystem.setLiftToPoint(RobotConstants.TOTE_GROUND_POSITION);
		}
				
		// Moves the hook lift to the release poisiton
		if(oi.payloadButton7.get()){
			hookLiftSystem.setLiftToPoint(RobotConstants.TOTE_RELEASE_POSITION);
>>>>>>> d73d0dd2020052a47cf406cd36cf5dc547774a9c
		}
		
		if(!liftPreset) {
			if(Math.abs(oi.payloadJoy.getY()) >= 0.1){
				hookLiftSystem.runLiftManually(oi.payloadJoy.getX());
			}
			else{
				hookLiftSystem.runLiftManually(0);
				liftPreset = true;
			}
		}
		
	///////////////////////////////////////////////////////////////////////////
	//
	// Runs container lift manually
		if(Math.abs(oi.payloadJoy.getZ()) >= 0.1){
			containerLiftSystem.runLiftManually(oi.payloadJoy.getZ());
		}
		else{
			containerLiftSystem.runLiftManually(0.0);
		}
		
	////////////////////////////////////////////////////////////////////////////
	//
	//  Runs container clamp manually
		if(oi.payloadButton1.get()){
			containerLiftSystem.runClampManually(0.3);
		}
		else if(oi.payloadButton3.get()){
			containerLiftSystem.runClampManually(-0.3);
		}
		else {
			containerLiftSystem.runClampManually(0.0);
		}
		
		// only for commands that use the Drive System
		if(!driveSystemConflict){
			speed = oi.getMainJoyThrottle();
			mecanumDriveSystem.driveWithPID(oi.getMainJoyX() * speed, oi.getMainJoyY() * speed);
			
			if(oi.getPOV() == RobotConstants.POV_UP) {
	        	new PIDAxisDrive(0.0, 0.3, 0.0, oi.mainJoy, RobotConstants.POV_UP).start();
	        }
	        else if(oi.getPOV() == RobotConstants.POV_DOWN) {
	        	new PIDAxisDrive(0.0, -0.3, 0.0, oi.mainJoy, RobotConstants.POV_DOWN).start();
	        }
	        else if(oi.getPOV() == RobotConstants.POV_LEFT) {
	        	new PIDAxisDrive(0.3, 0.0, 0.0, oi.mainJoy, RobotConstants.POV_LEFT).start();
	        }
	        else if(oi.getPOV() == RobotConstants.POV_RIGHT) {
	        	new PIDAxisDrive(-0.3, 0.0, 0.0, oi.mainJoy, RobotConstants.POV_RIGHT).start();
	        }
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
