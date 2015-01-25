package org.usfirst.frc.team4911.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import org.usfirst.frc.team4911.robot.subsystems.*;
import org.usfirst.frc.team4911.robot.Robot;
import org.usfirst.frc.team4911.robot.OI;

public class OperatorDrive extends Command {
	private MecanumDriveSystem mecanumDriveSystem = Robot.mecanumDriveSystem;
	private SensorSystem sensorSystem = Robot.sensorSystem;
	private PrintSystem printSystem = Robot.printSystem;	
	private OI oi = Robot.oi;
	
    public boolean usingDriveSystem;
    public boolean gridLocked;
	private int cycleNum;
	
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
			if(gridLocked){
				double valX = oi.getMainJoyX();
				double valY = oi.getMainJoyY();
				if(valX > valY){
					mecanumDriveSystem.drive(valX, 0.0, oi.getRotationJoyY());
				} else {
					mecanumDriveSystem.drive(0.0, valY, oi.getRotationJoyY());
				}
			} else {
				mecanumDriveSystem.drive(oi.getMainJoyX(), oi.getMainJoyY(), oi.getRotationJoyY());
			}
				
			if(cycleNum % 4 == 0) {
				printSystem.print("Teleop");
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
