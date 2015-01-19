package org.usfirst.frc.team4911.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.Timer;
import org.usfirst.frc.team4911.robot.Robot;
import org.usfirst.frc.team4911.robot.subsystems.MecanumDriveSystem;
import org.usfirst.frc.team4911.robot.subsystems.SensorSystem;
import org.usfirst.frc.team4911.robot.RobotConstants;


public class DriveStraight extends Command {
	private MecanumDriveSystem mecanumDriveSystem = Robot.mecanumDriveSystem;
	private SensorSystem sensorSystem = Robot.sensorSystem;
	
	private double kp;
    private double goalDistance;
    private double error;
    private double power;
	
    private double startTime;
    private double TIMELIMIT = 1.0;//sec
    
    public DriveStraight(double goal) {
    	requires(mecanumDriveSystem);
    	requires(sensorSystem);
    }

    protected void initialize() {
        mecanumDriveSystem.stop();
        sensorSystem.zeroYaw();
        kp = RobotConstants.DRIVESTRAIGHT_CORRECTION_CONSTANT;
        error = 0.0;
        power = 0.0;
        startTime = Timer.getFPGATimestamp();
    }

    protected void execute() {
        error = kp * sensorSystem.getYaw();
        power = getRampedPower(goalDistance, sensorSystem.getDistance());            

        //mecanumDriveSystem.drive(power - error , power + error, 0.0);
        mecanumDriveSystem.drive(power, power, 0.0);
    }

    protected boolean isFinished() {
        return Timer.getFPGATimestamp() - startTime >= TIMELIMIT;
    }

    protected void end() {
        mecanumDriveSystem.stop();
    }

    protected void interrupted() {
    }
    
    private double getRampedPower(double goalDistance, double currentDistance){
        double fractionOfGoalDistance = Math.min(currentDistance / goalDistance, 1.0);        
        double rampedPower = RobotConstants.AMPLITUDE 
        		* Math.pow(Math.cos(0.5 * Math.PI * fractionOfGoalDistance) , RobotConstants.RAMP_UP) 
        		* Math.pow(fractionOfGoalDistance , RobotConstants.RAMP_DOWN);
        rampedPower = Math.min(rampedPower + RobotConstants.FLOOR, RobotConstants.CEILING);
        return rampedPower;
    }
}
