package frc.robot;

import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.RunCommand;
import edu.wpi.first.wpilibj2.command.button.CommandGenericHID;
import frc.robot.subsystems.RomiDrivetrain;

public class RobotContainer {
    RomiDrivetrain drive = new RomiDrivetrain();
    CommandGenericHID xbox = new CommandGenericHID(Constants.Ports.XBOX_CONTROLLER_PORT);

    SendableChooser<Command> m_autonChooser = new SendableChooser<>();

    public RobotContainer() {
        configureBindings();
        auton();
    }

    private void configureBindin

    drive.setDefaultCommand(new RunCommand(()->drive.arcadeDrive(0.0,0.0),drive));
    }

    private void auton() {
        // m_autonChooser.setDefaultOption("Test", new Command[] {});
    }
}
