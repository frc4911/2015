package org.usfirst.frc.team4911.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import org.usfirst.frc.team4911.robot.subsystems.*;
import org.usfirst.frc.team4911.robot.Robot;
import org.usfirst.frc.team4911.robot.OI;
import org.usfirst.frc.team4911.robot.RobotConstants;

/**
 *
 */
public class DrivePastObstacle extends Command {
	OperatorDrive operatorDrive;
	SensorSystem sensorSystem;
	MecanumDriveSystem mecanumDriveSystem;
	
	int expectedObstacles;
	int obstaclesFound;
	boolean obstacleExpected;
	
    public DrivePastObstacle(int expectedObstacles) {
    	this.expectedObstacles = expectedObstacles;
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	operatorDrive = Robot.teleOp;
    	sensorSystem = Robot.sensorSystem;
    	mecanumDriveSystem = Robot.mecanumDriveSystem;
    	
    	obstaclesFound = 0;
    	obstacleExpected = false;
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	/*============================================================
    	 * This will check if we are directly in front of a container
    	 * or tote and continue to drive until it has found an expected 
    	 * number of objects. This number is defined as the parameter
    	 * expected obstacles.
    	 *==========================================================*/
    	
    	if (sensorSystem.getCM() <= 20){
    		if(!obstacleExpected){
    			obstaclesFound ++;
    			obstacleExpected = true;
    		}
    	}
    	else{
    		if(obstacleExpected){
    			obstacleExpected = false;
    		}
    	}
    	mecanumDriveSystem.driveWithPID(50, 0);
    	
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return (obstaclesFound == expectedObstacles && obstacleExpected == false);
    }

    // Called once after isFinished returns true
    protected void end() {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
