package org.usfirst.frc.team4911.robot.subsystems;

import org.usfirst.frc.team4911.robot.RobotMap;
import org.usfirst.frc.team4911.robot.RobotConstants;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.CANTalon;

public class HookLiftSystem extends Subsystem {
	public CANTalon leftHook;
	public CANTalon rightHook;
	
    public void initDefaultCommand() {
    	
    }
    
    public HookLiftSystem(){
    	leftHook = RobotMap.hookLeft;
    	rightHook = RobotMap.hookRight;
    }

    //CHECK IF THE SIGNS ARE CORRECT
    public void liftViaPercent(double position){
    	leftHook.set(RobotConstants.HOOKSYSTEM_TOTAL_DISTANCE * position / RobotConstants.HOOKSYSTEM_ENCODER_DISTANCE_PER_PULSE);
    	rightHook.set(-RobotConstants.HOOKSYSTEM_TOTAL_DISTANCE * position / RobotConstants.HOOKSYSTEM_ENCODER_DISTANCE_PER_PULSE);
    }
    
    //CHECK IF THE SIGNS ARE CORRECT
    //This will move the Lift right upto the lips of the Tote specified
    //toteNum = level of the tote on the ground
    public void liftViaTote(int toteNum){
    	if(toteNum > 0){
	    	leftHook.set(RobotConstants.TOTE_HEIGHT * (toteNum - 1) / RobotConstants.HOOKSYSTEM_ENCODER_DISTANCE_PER_PULSE);
	    	rightHook.set(-RobotConstants.TOTE_HEIGHT * (toteNum - 1) / RobotConstants.HOOKSYSTEM_ENCODER_DISTANCE_PER_PULSE);
    	}
    }
}

