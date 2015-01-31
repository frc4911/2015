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

    protected void initialize() {
    	mecanumDriveSystem = Robot.mecanumDriveSystem;
		this.goalHeading = (deltaGoalHeading + mecanumDriveSystem.getGoalHeading()) % 180 ;
    }

    protected void execute() {
    	Robot.printSystem.print("DeltaGoalHeading", "" + goalHeading);

    	Robot.printSystem.print("GoalHeading", "" + mecanumDriveSystem.getGoalHeading());
    	
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
