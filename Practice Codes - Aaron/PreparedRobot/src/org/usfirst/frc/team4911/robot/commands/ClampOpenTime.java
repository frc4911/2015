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

    protected void execute() {
    	containerSystem.runClampManuallyBackward();
    }

    protected boolean isFinished() {
        return ((Timer.getFPGATimestamp() - startTime) >= runTime);
    }

    protected void end() {
    	containerSystem.stopClamp();
    	teleop.containerClampConflict = false;
    }

    protected void interrupted() {
    }
}
