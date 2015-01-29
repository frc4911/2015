package org.usfirst.frc.team4911.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import org.usfirst.frc.team4911.robot.Robot;
import edu.wpi.first.wpilibj.buttons.JoystickButton;

/**
 *
 */
public class SetGoalHeading extends Command {

	private double goalHeading;
	private JoystickButton button;
	
    public SetGoalHeading(double goalHeading, JoystickButton button) {
		this.goalHeading = goalHeading;
		this.button = button;
    }

    protected void initialize() {
    }

    protected void execute() {
    	Robot.mecanumDriveSystem.setGoalHeading(goalHeading);
    	
    }

    protected boolean isFinished() {
        return !button.get();
    }

    protected void end() {
    }

    protected void interrupted() {
    }
}
