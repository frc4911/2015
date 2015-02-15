package org.usfirst.frc.team4911.robot.commands;

import org.usfirst.frc.team4911.robot.Robot;
import org.usfirst.frc.team4911.robot.Robot;
import org.usfirst.frc.team4911.robot.RobotConstants;
import org.usfirst.frc.team4911.robot.subsystems.ContainerLiftSystem;

import edu.wpi.first.wpilibj.command.Command;

public class CloseContainerClamp extends Command {
	private ContainerLiftSystem containerLiftSystem;
	
    public CloseContainerClamp() {
    	//requires(...) not necessary because "MoveContainerLift" command
    	//is already using it.
    }

    protected void initialize() {
    	containerLiftSystem = Robot.containerLiftSystem;
    	requires(containerLiftSystem);
    	containerLiftSystem.setLowSpeed(false);
    }

    protected void execute() {
    	// one should be positive, one should be negative
    	// depending on which way the motors go
    	
    	containerLiftSystem.runClampManuallyForward();
    }

    protected boolean isFinished() {
        return (containerLiftSystem.lowSpeed() && containerLiftSystem.getContainerContainer().getOutputCurrent() 
        									      > RobotConstants.CONTAINERSYSTEM_CLAMP_LOW_AMPERAGE_THRESHHOLD);
    }

    protected void end() {
    	containerLiftSystem.stopClamp();
    }

    protected void interrupted() {
    }
}
