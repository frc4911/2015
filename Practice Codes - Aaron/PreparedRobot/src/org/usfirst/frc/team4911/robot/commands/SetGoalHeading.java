package org.usfirst.frc.team4911.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import org.usfirst.frc.team4911.robot.Robot;
import org.usfirst.frc.team4911.robot.subsystems.MecanumDriveSystem;
import edu.wpi.first.wpilibj.buttons.JoystickButton;

/**
 *
 */
public class SetGoalHeading extends Command {

	private double goalHeading;
	private double deltaGoalHeading;
	private JoystickButton button;
	private MecanumDriveSystem mecanumDriveSystem;
	
    public SetGoalHeading(double deltaGoalHeading, JoystickButton button) {
		this.deltaGoalHeading = deltaGoalHeading;
		this.button = button;
    }
    public SetGoalHeading(int isPositive, JoystickButton button) {
		this.goalHeading = isPositive * 90;
		this.button = button;
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	mecanumDriveSystem = Robot.mecanumDriveSystem;
		this.goalHeading = (deltaGoalHeading + mecanumDriveSystem.getGoalHeading()) % 180 ;
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	Robot.printSystem.print("DeltaGoalHeading", "" + goalHeading);

    	Robot.printSystem.print("GoalHeading", "" + mecanumDriveSystem.getGoalHeading());
    	
    	Robot.mecanumDriveSystem.setGoalHeading(goalHeading);
    	
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return !button.get();
    }

    // Called once after isFinished returns true
    protected void end() {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
