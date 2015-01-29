package org.usfirst.frc.team4911.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import org.usfirst.frc.team4911.robot.Robot;
import org.usfirst.frc.team4911.robot.commands.OperatorDrive;

/**
 *
 */
public class UnlockGrid extends Command {
	private OperatorDrive operatorDriveCommand;
	
    public UnlockGrid() {
        
    }

    protected void initialize() {
    	operatorDriveCommand.gridLocked = false;
    	
    }

    protected void execute() {
    }

    protected boolean isFinished() {
        return false;
    }

    protected void end() {
    }

    protected void interrupted() {
    }
}
