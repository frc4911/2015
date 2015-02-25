package org.usfirst.frc.team4911.robot.commands;

import org.usfirst.frc.team4911.robot.Robot;
import org.usfirst.frc.team4911.robot.RobotConstants;
import org.usfirst.frc.team4911.robot.subsystems.ContainerLiftSystem;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;

public class MoveContainerLiftForTime extends Command {
	private ContainerLiftSystem containerLiftSystem;

	private double time;
	private double startTime;
	
	private double power;

    public MoveContainerLiftForTime(double time, double power) {
    	requires(containerLiftSystem);
    	this.power = power;
    	this.time = time;
    }

    protected void initialize() {
    	containerLiftSystem = Robot.containerLiftSystem;
    	startTime = Timer.getFPGATimestamp();
    }

    protected void execute() {
	containerLiftSystem.runLiftManually(power);
    }

    protected boolean isFinished() {
        return Timer.getFPGATimestamp() - startTime >= time ;
    }

    protected void end() {
	containerLiftSystem.runLiftManually(0.0);
    }

    protected void interrupted() {
    }
}
