// Alexis' branch - Lesson 2
// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.sensors.RomiGyro;
import edu.wpi.first.wpilibj.XboxController;

//tells it that you want to pull from the xbox controller file
//traces the path to find that file or import

/**
 * The VM is configured to automatically run this class, and to call the functions corresponding to
 * each mode, as described in the TimedRobot documentation. If you change the name of this class or
 * the package after creating this project, you must also update the build.gradle file in the
 * project.
 */
public class Robot extends TimedRobot {
  private static final String kDefaultAuto = "Default";
  private static final String kCustomAuto = "My Auto";
  private String m_autoSelected;
  private final SendableChooser<String> m_chooser = new SendableChooser<>();

  private final RomiDrivetrain m_drivetrain = new RomiDrivetrain();
  private XboxController m_controller = new XboxController(0);

  private static final double TARGET_DISTANCE = 54; //inches 54" x 48" for around the desk
  private static final double TARGET_DISTANCESHORT = 48; // shorter side of the desk
  private static final double TARGET_ANGLE = 90; // degrees
  private static final double TARGET_ANGLEEND = 450; //degree for the last turn 450
  private int autoState = 1;

  private RomiGyro m_gyro = new RomiGyro();

  
  /**
   * This function is run when the robot is first started up and should be used for any
   * initialization code.
   */
  // init - initialization

  @Override
  public void robotInit() {
    m_chooser.setDefaultOption("Default Auto", kDefaultAuto);
    m_chooser.addOption("My Auto", kCustomAuto);
    SmartDashboard.putData("Auto choices", m_chooser);
  }

  /**
   * This function is called every 20 ms, no matter the mode. Use this for items like diagnostics
   * that you want ran during disabled, autonomous, teleoperated and test.
   *
   * <p>This runs after the mode specific periodic functions, but before LiveWindow and
   * SmartDashboard integrated updating.
   */
  @Override
  public void robotPeriodic() {
    SmartDashboard.putNumber("Left Distance (in)", m_drivetrain.getLeftDistanceInch());
    SmartDashboard.putNumber("Right Distance (in)", m_drivetrain.getRightDistanceInch());
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

    m_drivetrain.resetEncoders();
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
      defaultAuto();
        // Put default auto code here
        break;
    }
  }

  private void defaultAuto() {
    double error;
    switch(autoState) {
      case 1: //initialize first drive distance
      m_drivetrain.resetEncoders();
      autoState++;
      break;

      case 2: //execute (drive the longer distance)
      error = TARGET_DISTANCE - m_drivetrain.getAverageDistanceInch();
      if (error > 1) {
        error = 1;
      }
      m_drivetrain.arcadeDrive(0.5*error, 0);
      if (Math.abs(error) < 0.5) {
        autoState++;
      }
      break;

      case 3: //end 
      m_drivetrain.arcadeDrive (0,0);
      autoState++;
      break;

      case 4: // initialize first right turn
      m_gyro.reset();
      autoState++;
      break;

      case 5: // turns 90 degrees
      error = TARGET_ANGLE - m_gyro.getAngle();
      error = error/10;
      if (error > 1) {
        error = 1;
      }
      m_drivetrain.arcadeDrive(0, -0.5*error);
      if (Math.abs(error) < 0.2) {
        autoState++;
      }
      break;

      case 6: //end
      m_drivetrain.arcadeDrive (0,0);
      autoState++;
      break;

      case 7: //initialize drive second distance 
      m_drivetrain.resetEncoders();
      autoState++;
      break;

      case 8: //drive to shorter distance
      error = TARGET_DISTANCESHORT - m_drivetrain.getAverageDistanceInch();
      if (error > 1) {
        error = 1;
      }
      m_drivetrain.arcadeDrive (.5*error, 0);
      if (Math.abs(error) < 0.5) {
        autoState++;
      }
      break;

      case 9: // end
      m_drivetrain.arcadeDrive (0,0);
      autoState++;
      break;

      case 10: // initialize second turn
      m_gyro.reset();
      autoState++;
      break;

      case 11: // second 90 degree turn
      error = TARGET_ANGLE - m_gyro.getAngle();
      error = error/10;
      if (error > 0.2) {
        if (error > 1)
        error = 1;
      }
      m_drivetrain.arcadeDrive (0,-0.5*error);
      if (Math.abs(error) < 0.2) {
      autoState++;
      }
      break;

      case 12: //end second turn
      m_drivetrain.arcadeDrive(0,0);
      autoState++;
      break;

      case 13: //initialize second long distance
      m_drivetrain.resetEncoders();
      autoState++;
      break;

      case 14: //drive the second long distance
      error = TARGET_DISTANCE - m_drivetrain.getAverageDistanceInch();
      if (error > 1) {
        error = 1;
      }
      m_drivetrain.arcadeDrive(0.5*error, 0);
      if (Math.abs(error) < 0.5) {
        autoState++;
      }
      break;
      
      case 15: //end the second drive
      m_drivetrain.arcadeDrive (0,0);
      autoState++;
      break;

      case 16: //initialize the last turn
      m_gyro.reset();
      autoState++;
      break;

      case 17: // execute the final 90 degree turn
      error = TARGET_ANGLE - m_gyro.getAngle();
      error = error/10;
      if (error > 0.2) {
        if (error > 1)
        error = 1;
      }
      m_drivetrain.arcadeDrive (0,-0.5*error);
      if (Math.abs(error) < 0.2) {
      autoState++;
      }
      break;

      case 18: //end the last turn
      m_drivetrain.arcadeDrive (0,0);
      autoState++;
      break;

      case 19: //initialize last short distance
      m_drivetrain.resetEncoders();
      autoState++;
      break;

      case 20: //drive last short distance
      error = TARGET_DISTANCESHORT - m_drivetrain.getAverageDistanceInch();
      if (error > 1) {
        error = 1;
      }
      m_drivetrain.arcadeDrive (0.5*error, 0);
      if (Math.abs(error) < 0.5) {
        autoState++;
      }
      break;

      case 21: //end
      m_drivetrain.arcadeDrive(0,0);
      autoState++;
      break;
      
      case 22: //initialize last turn to reset the position
      m_gyro.reset();
      autoState++;
      break;

      case 23: // execute 450 degree turn
      error = TARGET_ANGLEEND - m_gyro.getAngle();
      error = error/10;
      if (error > 0.2) {
        if (error > 1)
        error = 1;
      }
      m_drivetrain.arcadeDrive (0,-0.5*error);
      if (Math.abs(error) < 0.2) {
      autoState++;
      }
      break;

      case 24: //end
      m_drivetrain.arcadeDrive(0,0);
      autoState++;
      break;
      
      default: //done runs whenever none of the case values match
      m_drivetrain.arcadeDrive(0,0); //feed the watchdog
      break;
      }
    }
  

  /** This function is called once when teleop is enabled. */
  @Override
  public void teleopInit() {}

  /** This function is called periodically during operator control. */
  @Override
  public void teleopPeriodic() {
    m_drivetrain.arcadeDrive(-m_controller.getLeftY(), -m_controller.getRightX());
    if (m_controller.getBButton() == true) {
      m_drivetrain.resetEncoders();
    }
    // resets the encoder - distance value when the B button is pressed
  }

  /** This function is called once when the robot is disabled. */
  @Override
  public void disabledInit() {}

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

//different modes have different codes that run when the robot is put into the mode