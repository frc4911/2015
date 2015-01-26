package org.usfirst.frc.team4911.robot.commands;

import edu.wpi.first.wpilibj.command.Command;

import org.usfirst.frc.team4911.robot.subsystems.*;
import org.usfirst.frc.team4911.robot.Robot;
import org.usfirst.frc.team4911.robot.OI;
import org.usfirst.frc.team4911.robot.RobotConstants;

public class OperatorDrive extends Command {
	private MecanumDriveSystem mecanumDriveSystem = Robot.mecanumDriveSystem;
	private SensorSystem sensorSystem = Robot.sensorSystem;
	private PrintSystem printSystem = Robot.printSystem;	
	private OI oi = Robot.oi;
	
    public boolean usingDriveSystem;
    public boolean gridLocked;
	private int cycleNum;
	
	private double error = 0.0;
	private double kP = 1.0 / 100.0;
	
	public OperatorDrive(){
		requires(mecanumDriveSystem);
		requires(sensorSystem);
	}
	
	
	@Override
	protected void initialize() {
		sensorSystem.zeroYaw();
		usingDriveSystem = false;
		gridLocked = true;
		cycleNum = 1;
	}

	@Override
	protected void execute() {
		if(!usingDriveSystem){
			double goalHeading = 0.0;
			error = goalHeading - sensorSystem.getYaw();
			mecanumDriveSystem.drive(oi.getMainJoyX(),oi.getMainJoyY(), kP * error);
			/*
			if(gridLocked){
				double valX = oi.getMainJoyX();
				double valY = oi.getMainJoyY();
				if(valX > valY){
					mecanumDriveSystem.drive(valX, 0.0, oi.getMainJoyZ());
				} else {
					mecanumDriveSystem.drive(0.0, valY, oi.getMainJoyZ());
				}
			} else {
				mecanumDriveSystem.drive(oi.getMainJoyX(), oi.getMainJoyY(), oi.getMainJoyZ());
			}
			*/
			
			if(oi.getPOV() == RobotConstants.POV_UP) {
	        	new PIDAxisDrive(0.0, 0.3, 0.0, oi.mainJoy, RobotConstants.POV_UP).start();
	        }
	        else if(oi.getPOV() == RobotConstants.POV_DOWN) {
	        	new PIDAxisDrive(0.0, -0.3, 0.0, oi.mainJoy, RobotConstants.POV_DOWN).start();
	        }
	        else if(oi.getPOV() == RobotConstants.POV_LEFT) {
	        	new PIDAxisDrive(0.3, 0.0, 0.0, oi.mainJoy, RobotConstants.POV_LEFT).start();
	        }
	        else if(oi.getPOV() == RobotConstants.POV_RIGHT) {
	        	new PIDAxisDrive(-0.3, 0.0, 0.0, oi.mainJoy, RobotConstants.POV_RIGHT).start();
	        }
			
			if(cycleNum % 4 == 0) {
				//printSystem.print("Teleop");
				//printSystem.print("IMU:\t" + sensorSystem.getYaw());
				System.out.println("==================");
				System.out.println(sensorSystem.getYaw());
				System.out.println("X: " + oi.getMainJoyX() + " Y: " + oi.getMainJoyY() + " Rot: " + oi.getMainJoyZ());
				System.out.println("==================");
			}
			cycleNum++;
		}
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
