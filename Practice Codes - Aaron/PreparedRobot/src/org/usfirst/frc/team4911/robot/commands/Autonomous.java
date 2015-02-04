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
	private SensorSystem sensorSystem;
	private MecanumDriveSystem mecanumDriveSystem;
	
	private double error;
	private boolean endCommand;
	
	
	@Override
	protected void initialize() {
		printSystem = Robot.printSystem;
		containerLiftSystem = Robot.containerLiftSystem;
		hookLiftSystem = Robot.hookLiftSystem;
		sensorSystem = Robot.sensorSystem;
		mecanumDriveSystem = Robot.mecanumDriveSystem;
		error = 0.0;
		
		sensorSystem.zeroYaw();
		mecanumDriveSystem.setGoalHeading(0.0);
		endCommand = false;
		
		/*
		hookLiftSystem.getLeft().set(12.0 / RobotConstants.HOOKSYSTEM_ENCODER_DISTANCE_PER_PULSE);
		Timer.delay(2.0);
		hookLiftSystem.getRight().set(-12.0 / RobotConstants.HOOKSYSTEM_ENCODER_DISTANCE_PER_PULSE);
		Timer.delay(2.0);
		printSystem.print("Hook", hookLiftSystem.getDistance(), "Inches");

		containerLiftSystem.getContainerLift().set(12.0 / RobotConstants.CONTAINERSYSTEM_ENCODER_DISTANCE_PER_PULSE);
		Timer.delay(2.0);
		printSystem.print("ContainerLift", containerLiftSystem.getLiftDistance(), "Inches");
		
		//MUST STOP BEFORE IT HITS THE LIMIT SWITCH
		containerLiftSystem.getContainerContainer().set(12.0 / RobotConstants.CONTAINERSYSTEM_ENCODER_DISTANCE_PER_PULSE);
		Timer.delay(2.0);
		printSystem.print("ContainerClamp", containerLiftSystem.getClampDistance(), "Inches");
		*/
	}

	@Override
	protected void execute() {
		error = sensorSystem.getIN() - 16.0;
		for(double startTime = Timer.getFPGATimestamp(); error > 10; ){
			if(Timer.getFPGATimestamp() - startTime > 0.5){
				endCommand = true;
				break;
			}
			error = sensorSystem.getIN() - 16.0;
		}
		if(!endCommand){
			mecanumDriveSystem.driveWithPID(-0.4, error * 0.002);
		}
		printSystem.print("ERROR", error * 0.05);
	}

	@Override
	protected boolean isFinished() {
		return Math.abs(error) > 10.0 || endCommand;
	}

	@Override
	protected void end() {	
		printSystem.print("EndingError", error);
	}

	@Override
	protected void interrupted() {

	}

}
