package org.usfirst.frc.team4911.robot.commands;

import edu.wpi.first.wpilibj.DriverStation;
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
	
	public double speed;
	
    public boolean driveSystemConflict;
    public boolean gridLocked;
	
    Runtime runtime;
    
    long mb = 1024*1024;
    
	public OperatorDrive(){
		requires(mecanumDriveSystem);
		requires(sensorSystem);
	}
	
	
	@Override
	protected void initialize() {
		sensorSystem.zeroYaw();
		mecanumDriveSystem.setGoalHeading(0.0);
		driveSystemConflict = false;
		gridLocked = true;
<<<<<<< HEAD
		speed = RobotConstants.STANDARD_DRIVE_SPEED;
=======
		runtime = Runtime.getRuntime();
>>>>>>> 207d5c6d6e40dd37a593353700b57defb5f5eba3
	}

	@Override
	protected void execute() {
		if(!driveSystemConflict){
			mecanumDriveSystem.driveWithPID(oi.getMainJoyX() * speed,oi.getMainJoyY() * speed);
			
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
		}
		Robot.printSystem.print("Goal Heading:", "" + Robot.mecanumDriveSystem.getGoalHeading());
		Robot.printSystem.print("Current Heading:", "" + Robot.sensorSystem.getYaw());
		Robot.printSystem.print("Used Memory: " , "" + (runtime.totalMemory() - runtime.freeMemory()) / mb);
		Robot.printSystem.print("Free Memory: ", "" + runtime.freeMemory()/mb);
		Robot.printSystem.print("Voltage: ", "" + DriverStation.getInstance().getBatteryVoltage());
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
