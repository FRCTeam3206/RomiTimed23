package frc.robot;

import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.button.CommandGenericHID;
import frc.robot.subsystems.RomiDrivetrain;

public class RobotContainer {
    RomiDrivetrain m_drive = new RomiDrivetrain();
    CommandGenericHID m_xbox = new CommandGenericHID(Constants.XBOX_CONTROLLER_PORT);

    SendableChooser<Command> m_autonChooser = new SendableChooser<>();

    public RobotContainer() {
        configureBindings();
        auton();
    }

    private void configureBindings() {
        m_autonChooser.setDefaultOption("Test", new Command[] {});
    }

    private void auton() {
        //
    }
}
