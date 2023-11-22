// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.
// Corrie Romi practice

package frc.robot;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.sensors.RomiGyro;
import edu.wpi.first.wpilibj.XboxController;

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
  private final SendableChooser<String> m_autonChooser = new SendableChooser<>();

  private final RomiDrivetrain m_drivetrain = new RomiDrivetrain();
  private final RomiGyro m_gyro = new RomiGyro();
  private final XboxController m_controller = new XboxController(Constants.InputDevices.XBOX_CONTROLLER);

  private double speed;
  private double rotate;
  private boolean slowerBackwards = true;
  private boolean slowMode = false;

  // These values are used for an unused part of the code
  // public static final double distWheelsInch = 5.487402;
  // private boolean turnGoalSet = false;
  // private int turnAngle;
  // private double turnDistance;
  // private double turnGoal;

  private static final double TARGET_DISTANCE = 12;
  private static final int TARGET_ANGLE = 90;
  private int autoState = 1;
  private RomiDrivetrain.OffsetEncodersObject autoEncoders = m_drivetrain.new OffsetEncodersObject();
  private RomiDrivetrain.OffsetEncodersObject teleopEncoders = m_drivetrain.new OffsetEncodersObject();

  private String currentMode = "none";

  /**
   * This function is run when the robot is first started up and should be used for any
   * initialization code.
   */
  @Override
  public void robotInit() {
    m_autonChooser.setDefaultOption("Default Auto", kDefaultAuto);
    m_autonChooser.addOption("My Auto", kCustomAuto);
    SmartDashboard.putData("Auto choices", m_autonChooser);
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
    switch (currentMode) {
      case "auton":
        SmartDashboard.putNumber("Left Distance (in)", autoEncoders.getLeftDistanceInch());
        SmartDashboard.putNumber("Right Distance (in)", autoEncoders.getRightDistanceInch());
      case "teleop":
        SmartDashboard.putNumber("Left Distance (in)", teleopEncoders.getLeftDistanceInch());
        SmartDashboard.putNumber("Right Distance (in)", teleopEncoders.getRightDistanceInch());
      default:
        SmartDashboard.putNumber("Left Distance (in)", m_drivetrain.getRawLeftDistanceInch());
        SmartDashboard.putNumber("Right Distance (in)", m_drivetrain.getRawRightDistanceInch());
    }
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
    currentMode = "auton";
    m_autoSelected = m_autonChooser.getSelected();
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
        defaultAuto();
        break;
      default:
        // Put default auto code here
        break;
    }
  }

  private void defaultAuto() {
    double error;
    switch (autoState) {
      case 1:
        autoEncoders.ResetEncoders();
        autoState++;
        break;
      case 2:
        error = TARGET_DISTANCE - autoEncoders.getAverageDistanceInch();
        if (error > 1) {
          error = 1;
        }
        m_drivetrain.arcadeDrive(0.5*error, 0);
        if (error < 0.5) {
          autoState++;
        }
        break;
      case 3:
        m_drivetrain.arcadeDrive(0, 0);
        autoState++;
        break;
      case 4:
        m_gyro.reset();
        autoState++;
        break;
      case 5:
        error = TARGET_ANGLE - m_gyro.getAngle();
        error /= 10;
        if (error > 1) {
          error = 1;
        }
        m_drivetrain.arcadeDrive(0, -0.5*error);
        if (Math.abs(error) < 0.2) {
          autoState++;
          break;
        }
      case 6:
        m_drivetrain.arcadeDrive(0, 0);
      default:
        m_drivetrain.arcadeDrive(0, 0); // feed the watchdog
        break;
    }
  }

  /** This function is called once when teleop is enabled. */
  @Override
  public void teleopInit() {
    currentMode = "teleop";
  }

  /** This function is called periodically during operator control. */
  @Override
  public void teleopPeriodic() {

    // Reset encoders
    if (m_controller.getStartButton()) {
      teleopEncoders.ResetEncoders();
    }
    speed = -0.8 * m_controller.getLeftY();
    rotate = 0.7 * m_controller.getRightX();

    // b deactivates slower backwards, x activates it, does slower backward if slowerBackwards is true
    if (m_controller.getBButton()) {
      slowerBackwards = false;
    } else if (m_controller.getXButton()) {
      slowerBackwards = true;
    }
    if (slowerBackwards && speed < 0) {
      speed *= 0.7;
    }

    // y activates slow mode, a deactivates slow mode, does slow mode if slowMode is true
    if (m_controller.getYButton()) {
      slowMode = true;
    } else if (m_controller.getAButton()) {
      slowMode = false;
    }
    if (slowMode) {
      speed *= 0.7;
    }

    // Drive based on variables set
    m_drivetrain.arcadeDrive(speed, rotate);

    // This is supposed to make the Romi automatically turn to an angle based on POV input, but it's not working yet.
    // // setup for going to angle
    // if (m_controller.getPOV() != -1) {
    //   turnGoalSet = true;
    //   if (m_controller.getPOV() > 180) {
    //     turnAngle = m_controller.getPOV() - 360;
    //   } else {
    //     turnAngle = m_controller.getPOV();
    //   }
    //   turnDistance = distWheelsInch * Math.PI * (turnAngle / 180);
    //   turnGoal = m_drivetrain.getRawLeftDistanceInch() - m_drivetrain.getRawRightDistanceInch() + turnDistance;
    // }
    // // change rotation to go to angle
    // if (turnGoalSet) {
    //   if (turnGoal > 0) { // for turning right or 180
    //     if ((m_drivetrain.getRawLeftDistanceInch() - m_drivetrain.getRawRightDistanceInch()) < turnGoal) { // goal hasn't been reached yet
    //       rotate = 0.7 * ((turnGoal - ((m_drivetrain.getRawLeftDistanceInch() - m_drivetrain.getRawRightDistanceInch()))) / (distWheelsInch * Math.PI / (turnAngle / 360))) + 0.3;
    //     } else { // goal has been reached
    //       turnGoalSet = false;
    //     }
    //   } else { // for turning left
    //     if ((m_drivetrain.getRawLeftDistanceInch() - m_drivetrain.getRawRightDistanceInch()) > turnGoal) { // goal hasn't been reached yet
    //       rotate = -0.7 * ((turnGoal - (m_drivetrain.getRawLeftDistanceInch() - m_drivetrain.getRawRightDistanceInch())) / (distWheelsInch * Math.PI / (-turnAngle / 360))) + 0.3;
    //     } else { // goal has been reached
    //       turnGoalSet = false;
    //     }
    //   }
    // }
    // m_drivetrain.arcadeDrive(speed, rotate);

    // This code can probably be ignored now
    // // Changes mode based on button pressed (Y for slow mode and A for normal)
    // if (m_controller.getYButton() == true) {
    //   mode = "slow";
    // } else if (m_controller.getAButton() == true) {
    //   mode = "normal";
    // }
    // // Drives based on mode
    // if (mode == "normal") {
    //   m_drivetrain.arcadeDrive(-m_controller.getLeftY(), 0.7*m_controller.getRightX());
    // } else if (mode == "slow") {
    //   m_drivetrain.arcadeDrive(-0.5*m_controller.getLeftY(), 0.7*m_controller.getRightX());
    // }
    // // Resets encoders when B button pressed
    // if (m_controller.getBButtonPressed() == true) {
    //   m_drivetrain.resetEncoders();
    // }
  }

  /** This function is called once when the robot is disabled. */
  @Override
  public void disabledInit() {
    currentMode = "disabled";
  }

  /** This function is called periodically when disabled. */
  @Override
  public void disabledPeriodic() {}

  /** This function is called once when test mode is enabled. */
  @Override
  public void testInit() {
    currentMode = "test";
  }

  /** This function is called periodically during test mode. */
  @Override
  public void testPeriodic() {}
}
