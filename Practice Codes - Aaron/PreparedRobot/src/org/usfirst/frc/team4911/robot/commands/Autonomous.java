package org.usfirst.frc.team4911.robot.commands;

import org.usfirst.frc.team4911.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

public class Autonomous extends Command {

	@Override
	protected void initialize() {
	}

	@Override
	protected void execute() {
		Robot.printSystem.print("Autonomous");
	}

	@Override
	protected boolean isFinished() {
		return false;
	}

	@Override
	protected void end() {		
	}

	@Override
	protected void interrupted() {

	}

}
