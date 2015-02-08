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
	}

	@Override
	protected void execute() {
		
		// Moves the hook lift to the stack point
		if(oi.payloadJoy.getPOV() == 90 || oi.payloadJoy.getPOV() == 45 || oi.payloadJoy.getPOV() == 135){
			hookLiftSystem.setLiftToPoint(RobotConstants.TOTE_STACK_POSITION);
		}
		
		// Moves the hook lift to the aquire point
		if(oi.payloadButton5.get()){
			hookLiftSystem.setLiftToPoint(RobotConstants.TOTE_AQUIRE_POSITION);
		}
		
		// Moves the hook lift to the release point
		if(oi.payloadJoy.getPOV() == 270 || oi.payloadJoy.getPOV() == 225 || oi.payloadJoy.getPOV() == 315){
			hookLiftSystem.setLiftToPoint(RobotConstants.TOTE_GROUND_POSITION);
		}
				
		// Grounds the hook lift
		if(oi.payloadButton7.get()){
			hookLiftSystem.setLiftToPoint(RobotConstants.TOTE_RELEASE_POSITION);
		}
		
		if(Math.abs(oi.payloadJoy.getX()) >= 0.1){
			hookLiftSystem.runLiftManually(oi.payloadJoy.getX());
		}
		
		
		if(Math.abs(oi.payloadJoy.getZ()) >= 0.1){
			containerLiftSystem.runLiftManually(oi.payloadJoy.getZ());
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
