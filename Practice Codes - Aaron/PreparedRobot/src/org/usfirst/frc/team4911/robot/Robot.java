package org.usfirst.frc.team4911.robot;

import edu.wpi.first.wpilibj.IterativeRobot;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.Servo;

import org.usfirst.frc.team4911.robot.OI;
import org.usfirst.frc.team4911.robot.RobotMap;
import org.usfirst.frc.team4911.robot.commands.Autonomous;
import org.usfirst.frc.team4911.robot.commands.OperatorDrive;
import org.usfirst.frc.team4911.robot.commands.RunPrintServer;
import org.usfirst.frc.team4911.robot.subsystems.*;

public class Robot extends IterativeRobot {
    public static Autonomous autonomousCommand;
    public static OperatorDrive teleOp;
    public static RunPrintServer printServerCommand;
   
    
    public static OI oi;
    public static MecanumDriveSystem mecanumDriveSystem;
    public static SensorSystem sensorSystem;
    public static PrintSystem printSystem;
    public static HookLiftSystem hookLiftSystem;
    public static ContainerLiftSystem containerLiftSystem;
    public static CameraSystem cameraSystem;
    
    public void robotInit() {
    	RobotMap.init();
    	
    	printSystem = new PrintSystem();
    	mecanumDriveSystem = new MecanumDriveSystem();
    	sensorSystem = new SensorSystem();
        hookLiftSystem = new HookLiftSystem();
        containerLiftSystem = new ContainerLiftSystem();
        cameraSystem = new CameraSystem();
        
        autonomousCommand = new Autonomous();
        teleOp = new OperatorDrive();
  
        oi = new OI();
    }

    public void autonomousInit() {
    	if(autonomousCommand.isCanceled()) {
    		autonomousCommand = new Autonomous();
    	}
    	printServerCommand = new RunPrintServer();
    	printServerCommand.start();
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
    	//if(!cameraSystem.getServer().isAutoCaptureStarted()) {
    	  //  cameraSystem.start();
    	//}
    	printServerCommand = new RunPrintServer();
    	printServerCommand.start();    	
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
