package org.usfirst.frc.team4911.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import org.usfirst.frc.team4911.robot.Robot;
import org.usfirst.frc.team4911.robot.commands.OperatorDrive;

/**
 *
 */
public class LockGrid extends Command {
	private OperatorDrive operatorDriveCommand;
	
    public LockGrid() {
        
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	operatorDriveCommand.gridLocked = true;
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return false;
    }

    // Called once after isFinished returns true
    protected void end() {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
