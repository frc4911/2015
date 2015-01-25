package org.usfirst.frc.team4911.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import org.usfirst.frc.team4911.robot.subsystems.*;
import org.usfirst.frc.team4911.robot.Robot;
import org.usfirst.frc.team4911.robot.OI;

public class OperatorDrive extends Command {
	MecanumDriveSystem mecanumDriveSystem = Robot.mecanumDriveSystem;
	SensorSystem sensorSystem = Robot.sensorSystem;
	PrintSystem printSystem = Robot.printSystem;

	OI oi = Robot.oi;
	
	public OperatorDrive(){
		requires(mecanumDriveSystem);
		requires(sensorSystem);
	}
	
	@Override
	protected void initialize() {
		sensorSystem.zeroYaw();
	}

	@Override
	protected void execute() {
		mecanumDriveSystem.drive(oi.getMainJoyX(), oi.getMainJoyY(), oi.getRotationJoyY());
		//mecanumDriveSystem.drive(0.0, 0.3, 0.0);
		//mecanumDriveSystem.drive(0.4, 0.4);
		//mecanumDriveSystem.drive(oi.getMainJoyY(), oi.getRotationJoyY());
		printSystem.print("Teleop");
		System.out.println("==================");
		System.out.println(sensorSystem.getYaw());
		System.out.println("X: " + oi.getMainJoyX() + " Y: " + oi.getMainJoyY() + " Rot: " + oi.getRotationJoyY());
		System.out.println("==================");
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
