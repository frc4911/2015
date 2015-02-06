package org.usfirst.frc.team4911.robot.commands;

import org.usfirst.frc.team4911.robot.subsystems.MecanumDriveSystem;
import org.usfirst.frc.team4911.robot.subsystems.SensorSystem;
import org.usfirst.frc.team4911.robot.subsystems.PrintSystem;
import org.usfirst.frc.team4911.robot.OI;
import org.usfirst.frc.team4911.robot.Robot;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;


public class MaintainDistance extends Command {
	private MecanumDriveSystem mecanumDriveSystem;
	private SensorSystem sensorSystem;
	private PrintSystem printSystem;
	private OperatorDrive operatorDrive;
	private OI oi;

	private double error;
	private double previousError;
	private double derivative;
	private double goalDistance;
	private boolean endCommand;
	
	private double threshold;
	
    public MaintainDistance() {
    	//requires(mecanumDriveSystem);
    	//requires(sensorSystem);
    }

    protected void initialize(){
    	mecanumDriveSystem = Robot.mecanumDriveSystem;
    	sensorSystem = Robot.sensorSystem;
    	printSystem = Robot.printSystem;
    	operatorDrive = Robot.teleOp;
    	oi = Robot.oi;
    	if(operatorDrive.driveSystemConflict){
    		this.cancel();
    	}    		
    	operatorDrive.driveSystemConflict = true;
    	goalDistance = sensorSystem.getIN();
		mecanumDriveSystem.setGoalHeading(0.0);
		sensorSystem.zeroYaw();
		endCommand = false;
    	printSystem.print("INITIALIZED");
    	
    	error = sensorSystem.getIN() - goalDistance;
    	previousError = error;
    }

    protected void execute() {
		error = sensorSystem.getIN() - goalDistance;
		derivative = previousError - error;
		previousError = error;
		/*
		for(double startTime = Timer.getFPGATimestamp(); error > 10; ){
			if(Timer.getFPGATimestamp() - startTime > 0.5){
				endCommand = true;
				break;
			}
			error = sensorSystem.getIN() - 16.0;
		}
		if(!endCommand){
			mecanumDriveSystem.driveWithPID(oi.getMainJoyX(), error * 0.002);
		}
		*/
		mecanumDriveSystem.driveWithPID(oi.getMainJoyX(), error * 0.1);//+ derivative * 0.01
    	printSystem.print("GOAL DISTANCE", goalDistance, "inches");
    	printSystem.print("PROPORTION", error * 0.03);
    	printSystem.print("DERIVATIVE", derivative * 0.01);
    	
    }

    protected boolean isFinished() {
		return Math.abs(error) > 10.0 || !oi.button10.get();
    	//return Math.abs(error) > 10.0 || endCommand || !oi.button10.get();
    }

    protected void end() {
    	printSystem.print("CONFLICT IS FALSE");
    	operatorDrive.driveSystemConflict = false;
    }

    protected void interrupted() {
    }
}