package org.usfirst.frc.team4911.robot.commands;

import edu.wpi.first.wpilibj.command.Command;


import org.usfirst.frc.team4911.robot.subsystems.*;
import org.usfirst.frc.team4911.robot.Robot;
import org.usfirst.frc.team4911.robot.OI;

/**
 *
 */
public class DriveStraightBackward extends Command {
	private MecanumDriveSystem mecanumDriveSystem = Robot.mecanumDriveSystem;
	private SensorSystem sensorSystem = Robot.sensorSystem;
	private PrintSystem printSystem = Robot.printSystem;

	private OI oi = Robot.oi;
	private OperatorDrive operatorDrive = Robot.teleOp;
	
    public DriveStraightBackward() {
    	
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	if(operatorDrive.usingDriveSystem){
    		this.cancel();
    	}    		
    	operatorDrive.usingDriveSystem = true;    	
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	mecanumDriveSystem.drive(0, 0.3, 0);
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return !oi.button4.get();
    }

    // Called once after isFinished returns true
    protected void end() {
    	operatorDrive.usingDriveSystem = false;   
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
