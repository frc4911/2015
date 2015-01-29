package org.usfirst.frc.team4911.robot.commands;

import org.usfirst.frc.team4911.robot.OI;
import org.usfirst.frc.team4911.robot.Robot;
import org.usfirst.frc.team4911.robot.RobotConstants;
import org.usfirst.frc.team4911.robot.subsystems.MecanumDriveSystem;
import org.usfirst.frc.team4911.robot.subsystems.SensorSystem;
import org.usfirst.frc.team4911.robot.subsystems.PrintSystem;



import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.buttons.*;

/**
 *
 */
public class EnableRotate extends Command {
	private MecanumDriveSystem mecanumDriveSystem;
	private SensorSystem sensorSystem;	
	private PrintSystem printSystem;	
	private OI oi;
	private OperatorDrive operatorDrive;
	private JoystickButton button;
	
    public boolean driveSystemConflict;
    public boolean gridLocked;
    
    private double previousDegree;

	double currentDegree;
	double deltaDegree;
	
	int numIteration;
	
    private static double THRESHOLD = 2.0;//degrees
    
    public EnableRotate(JoystickButton button) {
    	this.button = button;
    }

    protected void initialize() {
    	oi = Robot.oi;
    	operatorDrive = Robot.teleOp;
    	mecanumDriveSystem = Robot.mecanumDriveSystem;
    	sensorSystem = Robot.sensorSystem;
    	printSystem = Robot.printSystem;
    	if(operatorDrive.driveSystemConflict){
    		this.cancel();
    	}    		
    	operatorDrive.driveSystemConflict = true;
    	printSystem.print("EnableRotate Running", "RUNNING");
    	previousDegree = 0.0;//sensorSystem.getYaw();
    	numIteration = 1;
    }

    protected void execute() {
    	if(button.get()){
	    	mecanumDriveSystem.drive(oi.getMainJoyX(), oi.getMainJoyY(), oi.getMainJoyZ() * RobotConstants.ROTATE_SPEED);
    	}
    	mecanumDriveSystem.setGoalHeading(sensorSystem.getYaw());
    }

    protected boolean isFinished() {
    	if(numIteration % 5 == 0){
    		currentDegree = sensorSystem.getYaw();
        	deltaDegree = Math.abs(previousDegree - currentDegree);
        	printSystem.print("PreviousDegree", "" + (previousDegree));
            
        	previousDegree = currentDegree;
        	
        	printSystem.print("CurrDegree", "" + currentDegree);
        	printSystem.print("GoalHeading", "" + mecanumDriveSystem.getGoalHeading());
        	printSystem.print("ButtonPressed", "" + (button.get()));
        	printSystem.print("DeltaDegree", "" + (deltaDegree));
        	printSystem.print("Returning", "" +((deltaDegree < THRESHOLD) && (!button.get())));
        	numIteration = 1;
        	return (deltaDegree < THRESHOLD) && (!button.get() );
    	} else {
        	numIteration++;
    		return false;
    	}
    	
    	
        
    }

    protected void end() {
    	//mecanumDriveSystem.setGoalHeading(sensorSystem.getYaw());
    	operatorDrive.driveSystemConflict = false;
    	mecanumDriveSystem.setGoalHeading(sensorSystem.getYaw());
    	printSystem.print("EnableRotate Running", "NOT RUNNING");
    }

    protected void interrupted() {
    }
}
