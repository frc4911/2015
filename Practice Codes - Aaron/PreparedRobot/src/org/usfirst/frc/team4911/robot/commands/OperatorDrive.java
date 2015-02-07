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
		
		// Checks for the Container Lift Buttons
		if(oi.payloadButton6.get()){
			containerLiftSystem.runLiftManually(RobotConstants.CONTAINERSYSTEM_LIFT_SPEED);
		}
		else if (oi.payloadButton8.get()){
			containerLiftSystem.runLiftManually(-RobotConstants.CONTAINERSYSTEM_LIFT_SPEED);
		}
		else{
			containerLiftSystem.runLiftManually(0.0);
		}
		
		// Checks for the Tote Lift Buttons
		if(oi.payloadButton5.get()){
			hookLiftSystem.runLiftManually(RobotConstants.TOTE_LIFT_SPEED);
		}
		else if (oi.payloadButton7.get()){
			hookLiftSystem.runLiftManually(-RobotConstants.TOTE_LIFT_SPEED);
		}
		else{
			hookLiftSystem.runLiftManually(0.0);
		}
		
		// Checks for the clamp button
		if(oi.payloadButton3.get()){
			containerLiftSystem.runClampManually(RobotConstants.CONTAINERSYSTEM_CLAMP_SPEED);
		}
		else if(oi.payloadButton1.get()){
			containerLiftSystem.runClampManually(-RobotConstants.CONTAINERSYSTEM_CLAMP_SPEED);
		}
		else{
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
