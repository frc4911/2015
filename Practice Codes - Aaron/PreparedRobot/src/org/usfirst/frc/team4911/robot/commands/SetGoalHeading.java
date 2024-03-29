package org.usfirst.frc.team4911.robot.commands;

import edu.wpi.first.wpilibj.command.Command;

import org.usfirst.frc.team4911.robot.Robot;
import org.usfirst.frc.team4911.robot.subsystems.MecanumDriveSystem;

/**
 *
 */
public class SetGoalHeading extends Command {

    private double goalHeading;
    private double desiredGoalHeading;
    private double deltaGoalHeading;
    private MecanumDriveSystem mecanumDriveSystem;
    private boolean addingHeading;
	
    public SetGoalHeading(double deltaGoalHeading) {
	this.deltaGoalHeading = deltaGoalHeading;
	addingHeading = true;
    }
    
    public SetGoalHeading(int desiredGoalHeading) {
	this.goalHeading = desiredGoalHeading;
	addingHeading = false;
    }

    protected void initialize() {
    	mecanumDriveSystem = Robot.mecanumDriveSystem;
    	if(addingHeading){
    	    this.goalHeading = (deltaGoalHeading + mecanumDriveSystem.getGoalHeading());
    	}
    }

    protected void execute() {
    	//Robot.printSystem.print("New GoalHeading", "" + goalHeading);

    	//Robot.printSystem.print("Old GoalHeading", "" + mecanumDriveSystem.getGoalHeading());
    	
    	mecanumDriveSystem.setGoalHeading(goalHeading);
    	//Robot.printSystem.print(("Goal Heading Post Set: " + mecanumDriveSystem.getGoalHeading()));
    	
    }

    protected boolean isFinished() {
    	//testing now - in theory, the command should finish and not restart if the button is held
        return true;
    }

    protected void end() {
    }

    protected void interrupted() {
    }
}
