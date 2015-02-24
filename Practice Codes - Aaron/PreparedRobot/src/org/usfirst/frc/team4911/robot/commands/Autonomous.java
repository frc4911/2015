package org.usfirst.frc.team4911.robot.commands;

import org.usfirst.frc.team4911.robot.Robot;

import org.usfirst.frc.team4911.robot.RobotConstants;
import org.usfirst.frc.team4911.robot.subsystems.PrintSystem;
import org.usfirst.frc.team4911.robot.subsystems.SensorSystem;
import org.usfirst.frc.team4911.robot.subsystems.MecanumDriveSystem;
import org.usfirst.frc.team4911.robot.subsystems.ContainerLiftSystem;
import org.usfirst.frc.team4911.robot.subsystems.HookLiftSystem;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;

public class Autonomous extends Command {
	private PrintSystem printSystem;
	private ContainerLiftSystem containerLiftSystem;
	private HookLiftSystem hookLiftSystem;
	
	@Override
	protected void initialize() {
		printSystem = Robot.printSystem;
		containerLiftSystem = Robot.containerLiftSystem;
		hookLiftSystem = Robot.hookLiftSystem;
			
		new AutoZoneContainer().start();
	}

	@Override
	protected void execute() {
	}

	@Override
	protected boolean isFinished() {
		return true;
	}

	@Override
	protected void end() {	
	}

	@Override
	protected void interrupted() {

	}

}
