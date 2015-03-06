package org.usfirst.frc.team4911.robot.commands;

import org.usfirst.frc.team4911.robot.Robot;
import org.usfirst.frc.team4911.robot.Robot;
import org.usfirst.frc.team4911.robot.RobotConstants;
import org.usfirst.frc.team4911.robot.subsystems.ContainerLiftSystem;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;

public class CloseContainerClamp extends Command {
    private ContainerLiftSystem containerLiftSystem;
    private double startTime;
    private double runTime;
	
    public CloseContainerClamp(double runTime) {
    	this.runTime = runTime;
    }

    protected void initialize() {
    	containerLiftSystem = Robot.containerLiftSystem;
    	containerLiftSystem.setLowSpeed(false);
    	startTime = Timer.getFPGATimestamp();
    }

    protected void execute() {
    	containerLiftSystem.runClampManuallyForward();
    }

    protected boolean isFinished() {
    	return Timer.getFPGATimestamp() - startTime >= runTime;
    }

    protected void end() {
    	containerLiftSystem.stopClamp();
    }

    protected void interrupted() {
    }
}
