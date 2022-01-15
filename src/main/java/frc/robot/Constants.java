package frc.robot;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Constants {


    public static final double GEAR_RATIO = 2/1;

    public static final int kSlotIdx = 0;

	/**
	 * Talon FX supports multiple (cascaded) PID loops. For
	 * now we just want the primary one.
	 */
	public static final int kPIDLoopIdx = 0;

	/**
	 * Set to zero to skip waiting for confirmation, set to nonzero to wait and
	 * report to DS if action fails.
	 */
    public static final int kTimeoutMs = 30;

	/**
	 * PID Gains may have to be adjusted based on the responsiveness of control loop.
     * kF: 1023 represents output value to Talon at 100%, 20660 represents Velocity units at 100% output
     * 
	 * 	                                    			  kP   	 kI    kD      kF          Iz    PeakOut */
   public final static Gains kGains_Velocit  = new Gains( 2, 0, 20, 0,  0,  1.00);
    //1023.0/20660.0
}
