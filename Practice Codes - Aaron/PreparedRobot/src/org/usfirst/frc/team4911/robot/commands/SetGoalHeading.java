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
	private boolean addingHeading;
	
    public SetGoalHeading(double deltaGoalHeading, JoystickButton button) {
		this.deltaGoalHeading = deltaGoalHeading;
		this.button = button;
		addingHeading = true;
    }
    public SetGoalHeading(int isPositive, JoystickButton button) {
		this.goalHeading = isPositive * 90;
		this.button = button;
		addingHeading = false;
    }

    protected void initialize() {
    	mecanumDriveSystem = Robot.mecanumDriveSystem;
    	if(addingHeading){
    		this.goalHeading = (deltaGoalHeading + mecanumDriveSystem.getGoalHeading());
    	}
    }

    protected void execute() {
    	Robot.printSystem.print("New GoalHeading", "" + goalHeading);

    	Robot.printSystem.print("Old GoalHeading", "" + mecanumDriveSystem.getGoalHeading());
    	
    	mecanumDriveSystem.setGoalHeading(goalHeading);
    	Robot.printSystem.print(("Goal Heading Post Set: " + mecanumDriveSystem.getGoalHeading()));
    	
    }

    protected boolean isFinished() {
        return !button.get();
    }

    protected void end() {
    }

    protected void interrupted() {
    }
}
