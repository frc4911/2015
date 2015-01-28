package org.usfirst.frc.team4911.robot.commands;

import org.usfirst.frc.team4911.robot.OI;
import org.usfirst.frc.team4911.robot.Robot;
import org.usfirst.frc.team4911.robot.subsystems.MecanumDriveSystem;
import org.usfirst.frc.team4911.robot.subsystems.SensorSystem;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.buttons.*;

/**
 *
 */
public class EnableRotate extends Command {
	private MecanumDriveSystem mecanumDriveSystem;
	private SensorSystem sensorSystem;	
	private OI oi;
	private OperatorDrive operatorDrive;
	private JoystickButton button;
	
    public boolean driveSystemConflict;
    public boolean gridLocked;
    
    public EnableRotate(JoystickButton button) {
    	this.button = button;
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	oi = Robot.oi;
    	operatorDrive = Robot.teleOp;
    	mecanumDriveSystem = Robot.mecanumDriveSystem;
    	sensorSystem = Robot.sensorSystem;
    	if(operatorDrive.driveSystemConflict){
    		this.cancel();
    	}    		
    	operatorDrive.driveSystemConflict = true;
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	mecanumDriveSystem.drive(oi.getMainJoyX(), oi.getMainJoyY(), oi.getMainJoyZ());
    	System.out.println("Heading: " + sensorSystem.getYaw());
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return !(button.get());
    }

    // Called once after isFinished returns true
    protected void end() {
    	mecanumDriveSystem.setGoalHeading(sensorSystem.getYaw());
    	operatorDrive.driveSystemConflict = false;
    	System.out.println("====================");
    	System.out.println("End yaw: " + sensorSystem.getYaw());
    	System.out.println("====================");
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
