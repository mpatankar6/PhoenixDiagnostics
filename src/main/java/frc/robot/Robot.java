// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.TalonFXFeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.TalonFX;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * The VM is configured to automatically run this class, and to call the functions corresponding to
 * each mode, as described in the TimedRobot documentation. If you change the name of this class or
 * the package after creating this project, you must also update the build.gradle file in the
 * project.
 */
public class Robot extends TimedRobot {

  public FileWriter fileWriter;
  public BufferedWriter bufferedWriter;
  private static final String kDefaultAuto = "Default";
  private static final String kCustomAuto = "My Auto";
  private String m_autoSelected;
  private final SendableChooser<String> m_chooser = new SendableChooser<>();

  private TalonFX _talon;

  
  // private TalonSRX motor;

  /**
   * This function is run when the robot is first started up and should be used for any
   * initialization code.
   */
  @Override
  public void robotInit() {
    m_chooser.setDefaultOption("Default Auto", kDefaultAuto);
    m_chooser.addOption("My Auto", kCustomAuto);
    SmartDashboard.putData("Auto choices", m_chooser);


    SmartDashboard.putNumber("p", 0.1);
    SmartDashboard.putNumber("i", 0.1);
    SmartDashboard.putNumber("d", .001);
    SmartDashboard.putNumber("f", 0);
    SmartDashboard.putNumber("v", 8000);
    try {
      bufferedWriter = new BufferedWriter(new FileWriter("/home/lvuser/Output.txt", true));
    } catch (IOException e) {
      e.printStackTrace();
    }
    
    _talon = new TalonFX(1);
     /* Factory Default all hardware to prevent unexpected behaviour */
		_talon.configFactoryDefault();
		
		/* Config neutral deadband to be the smallest possible */
		_talon.configNeutralDeadband(0.001);

		/* Config sensor used for Primary PID [Velocity] */
        _talon.configSelectedFeedbackSensor(TalonFXFeedbackDevice.IntegratedSensor,
                                            Constants.kPIDLoopIdx, 
											Constants.kTimeoutMs);
											

		/* Config the peak and nominal outputs */
		_talon.configNominalOutputForward(0, Constants.kTimeoutMs);
		_talon.configNominalOutputReverse(0, Constants.kTimeoutMs);
		_talon.configPeakOutputForward(1, Constants.kTimeoutMs);
		_talon.configPeakOutputReverse(-1, Constants.kTimeoutMs);

		/* Config the Velocity closed loop gains in slot0 */



  }

  /**
   * This function is called every robot packet, no matter the mode. Use this for items like
   * diagnostics that you want ran during disabled, autonomous, teleoperated and test.
   *
   * <p>This runs after the mode specific periodic functions, but before LiveWindow and
   * SmartDashboard integrated updating.
   */
  @Override
  public void robotPeriodic() {

  }

  /**
   * This autonomous (along with the chooser code above) shows how to select between different
   * autonomous modes using the dashboard. The sendable chooser code works with the Java
   * SmartDashboard. If you prefer the LabVIEW Dashboard, remove all of the chooser code and
   * uncomment the getString line to get the auto name from the text box below the Gyro
   *
   * <p>You can add additional auto modes by adding additional comparisons to the switch structure
   * below with additional strings. If using the SendableChooser make sure to add them to the
   * chooser code above as well.
   */
  @Override
  public void autonomousInit() {
    m_autoSelected = m_chooser.getSelected();
    // m_autoSelected = SmartDashboard.getString("Auto Selector", kDefaultAuto);
    System.out.println("Auto selected: " + m_autoSelected);
  }

  /** This function is called periodically during autonomous. */
  @Override
  public void autonomousPeriodic() {
    switch (m_autoSelected) {
      case kCustomAuto:
        // Put custom auto code here
        break;
      case kDefaultAuto:
      default:
        // Put default auto code here
        break;
    }
  }

  /** This function is called once when teleop is enabled. */
  @Override
  public void teleopInit() {
    try {

      //   file = new File("/home/lvuser/Output.txt");
      //   if (!file.exists()) {
      //     file.createNewFile();
      //     System.out.println("Output file created");
      //   }
      //   fileWriter = new FileWriter(file);
      //   bufferedWriter = new BufferedWriter(fileWriter);
  
      // FileWriter writer = new FileWriter("/home/lvuser/Output.txt");  
      // BufferedWriter buffer = new BufferedWriter(writer);  
      // buffer.write("Welcome to javaTpoint.");  
 
      
      } catch (Exception e) {
        e.printStackTrace();
      }

      // motor.set(ControlMode.Velocity, );

  }

  /** This function is called periodically during operator control. */
  @Override
  public void teleopPeriodic() {
    double rpm = _talon.getSelectedSensorVelocity() * (1/100.0) * (1000/1) * (60/1) * (1/2048.0) * Constants.GEAR_RATIO;
    String timeStamp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS").format(System.currentTimeMillis());
    System.out.println(timeStamp + " : " + rpm);
    try {
      bufferedWriter.write(timeStamp + " : " + rpm);
      bufferedWriter.newLine();
      bufferedWriter.flush();
    } catch (IOException e) {
      e.printStackTrace();
    }

		_talon.config_kF(Constants.kPIDLoopIdx, SmartDashboard.getNumber("p", 0.1), Constants.kTimeoutMs);
		_talon.config_kP(Constants.kPIDLoopIdx, SmartDashboard.getNumber("i", 0.1), Constants.kTimeoutMs);
		_talon.config_kI(Constants.kPIDLoopIdx, SmartDashboard.getNumber("d", 0.001), Constants.kTimeoutMs);
		_talon.config_kD(Constants.kPIDLoopIdx, SmartDashboard.getNumber("v", 0), Constants.kTimeoutMs);
    

    // _talon.set(ControlMode.Velocity, 8000);
    // _talon.set(ControlMode.PercentOutput, 0.5);
  }

  /** This function is called once when the robot is disabled. */
  @Override
  public void disabledInit() {
    _talon.set(ControlMode.PercentOutput, 0);
    // try {
    //   if (bufferedWriter != null) {
    //     bufferedWriter.close();
    //   }

    // } catch (IOException e) {
    //   e.printStackTrace();
    // }
  }

  /** This function is called periodically when disabled. */
  @Override
  public void disabledPeriodic() {}

  /** This function is called once when test mode is enabled. */
  @Override
  public void testInit() {}

  /** This function is called periodically during test mode. */
  @Override
  public void testPeriodic() {}
}
