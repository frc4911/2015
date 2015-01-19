package org.usfirst.frc.team4911.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import org.usfirst.frc.team4911.robot.subsystems.*;
import org.usfirst.frc.team4911.robot.Robot;
import org.usfirst.frc.team4911.robot.OI;

public class OperatorDrive extends Command {
	MecanumDriveSystem mecanumDriveSystem = Robot.mecanumDriveSystem;
	SensorSystem sensorSystem = Robot.sensorSystem;

	OI oi = Robot.oi;
	
	public OperatorDrive(){
		requires(mecanumDriveSystem);
		requires(sensorSystem);
	}
	
	@Override
	protected void initialize() {

	}

	@Override
	protected void execute() {
		mecanumDriveSystem.drive(oi.getMainJoyX(), oi.getMainJoyY(), oi.getRotationJoyY());
	}

	@Override
	protected boolean isFinished() {
		return false;
	}

	@Override
	protected void end() {
		this.cancel();
	}

	@Override
	protected void interrupted() {

	}

}