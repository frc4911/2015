package org.usfirst.frc.team4911.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.Timer;

import org.usfirst.frc.team4911.robot.Robot;
import org.usfirst.frc.team4911.robot.subsystems.ContainerLiftSystem;
import org.usfirst.frc.team4911.robot.OI;
import org.usfirst.frc.team4911.robot.commands.OperatorDrive;
/**
 *
 */
public class ClampOpenTime extends Command {
    
    private double runTime;
    private ContainerLiftSystem containerSystem;
    private OI oi;
    private OperatorDrive teleop;
    private double startTime;
    
    public ClampOpenTime(double runTime) {
	this.runTime = runTime;
    }

    // Called just before this Command runs the first time
    protected void initialize() {
	containerSystem = Robot.containerLiftSystem;
	oi = Robot.oi;
	teleop = Robot.teleOp;
	if(teleop.containerClampConflict) {
	    this.cancel();
	}
	teleop.containerClampConflict = true;
	startTime = Timer.getFPGATimestamp();
	
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
	containerSystem.runClampManuallyBackward();
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return ((Timer.getFPGATimestamp() - startTime) >= runTime);
    }

    // Called once after isFinished returns true
    protected void end() {
	containerSystem.stopClamp();
	teleop.containerClampConflict = false;
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}