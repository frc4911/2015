package org.usfirst.frc.team4911.robot;

import edu.wpi.first.wpilibj.IterativeRobot;


import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;

import org.usfirst.frc.team4911.robot.OI;
import org.usfirst.frc.team4911.robot.RobotMap;
import org.usfirst.frc.team4911.robot.commands.Autonomous;
import org.usfirst.frc.team4911.robot.commands.OperatorDrive;
import org.usfirst.frc.team4911.robot.commands.SetSolenoid;
import org.usfirst.frc.team4911.robot.subsystems.*;

public class Robot extends IterativeRobot {
    public static Command autonomousCommand;
    public static Command teleOp;
    public static Command testCommand;
   
    public static OI oi;
    public static MecanumDriveSystem mecanumDriveSystem;
    public static SensorSystem sensorSystem;
    public static PrintSystem printSystem;
    public static PneumaticSystem pneumaticSystem;
    
    
    public void robotInit() {
    	RobotMap.init();

    	printSystem = new PrintSystem();
    	
    	pneumaticSystem = new PneumaticSystem();

    	mecanumDriveSystem = new MecanumDriveSystem();
    	sensorSystem = new SensorSystem();
        oi = new OI();
        
        autonomousCommand = new Autonomous();
        teleOp = new OperatorDrive();
        testCommand = new SetSolenoid();

        if(RobotConstants.FLAG) {
            System.out.println("Ready To Roll Out!");
        }
    }

    public void autonomousInit() {
    	if(autonomousCommand.isCanceled()) {
    		autonomousCommand = new Autonomous();
    	}
    	printSystem.createNewFile();
        autonomousCommand.start(); 
    }

    public void autonomousPeriodic() {
        Scheduler.getInstance().run();
    }

    public void teleopInit() {
    	if(autonomousCommand != null) {
    		autonomousCommand.cancel();
    	}
    	if(teleOp.isCanceled()){
    		teleOp = new OperatorDrive();
    	}
    	printSystem.createNewFile();
    	teleOp.start();
    }

    public void teleopPeriodic() {
        Scheduler.getInstance().run();
    }
    
    public void testPeriodic() {
    	//LiveWindow.run();
    }
    public void disabledInit(){
    	printSystem.closeOutput();
        autonomousCommand = new Autonomous();
        teleOp = new OperatorDrive();
        Scheduler.getInstance().removeAll();        
    }
}
