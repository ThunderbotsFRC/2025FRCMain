// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import frc.robot.commands.Autos;
import frc.robot.subsystems.ArmSubsystem;
import frc.robot.subsystems.DriveSubsystem;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import edu.wpi.first.wpilibj2.command.button.Trigger;

/**
 * This class is where the bulk of the robot should be declared. Since Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in the {@link Robot}
 * periodic methods (other than the scheduler calls). Instead, the structure of the robot (including
 * subsystems, commands, and trigger mappings) should be declared here.
 */
public class RobotContainer {
	// The robot's subsystems and commands are defined here...
	//private final AutoSubsystem m_autoSubsystem = new AutoSubsystem();
	private final DriveSubsystem m_driveSubsystem = new DriveSubsystem();
	private final ArmSubsystem m_armSubsystem = new ArmSubsystem();

	// Replace with CommandPS4Controller or CommandJoystick if needed
	private final CommandXboxController m_driverController =
		new CommandXboxController(0);

	/** The container for the robot. Contains subsystems, OI devices, and commands. */
	public RobotContainer() {
		// Configure the trigger bindings
		configureBindings();
	}

	/**
	 * Use this method to define your trigger->command mappings. Triggers can be created via the
	 * {@link Trigger#Trigger(java.util.function.BooleanSupplier)} constructor with an arbitrary
	 * predicate, or via the named factories in {@link
	 * edu.wpi.first.wpilibj2.command.button.CommandGenericHID}'s subclasses for {@link
	 * CommandXboxController Xbox}/{@link edu.wpi.first.wpilibj2.command.button.CommandPS4Controller
	 * PS4} controllers or {@link edu.wpi.first.wpilibj2.command.button.CommandJoystick Flight
	 * joysticks}
	 */
	private void configureBindings() {
		// Schedule `ExampleCommand` when `exampleCondition` changes to `true`
		//new Trigger(m_exampleSubsystem::exampleCondition).onTrue(new ExampleCommand(m_exampleSubsystem));

		// Schedule `exampleMethodCommand` when the Xbox controller's B button is pressed,
		// cancelling on release.
		
		// Sets the default command to be always driving based on controller input
		m_driveSubsystem.setDefaultCommand(m_driveSubsystem.TankDrive(m_driverController::getLeftY, m_driverController::getRightX));

		m_driverController.x().onTrue(m_armSubsystem.toggleArmPosition());
		m_driverController.a().and(m_driverController.b().negate()).onTrue(m_armSubsystem.setMotors(-1));
		m_driverController.b().and(m_driverController.a().negate()).onTrue(m_armSubsystem.setMotors(1));
		//m_driverController.y().onTrue(m_armSubsystem.toggleMotors());
		//m_driverController.a().whileTrue(m_armSubsystem.setArmMotors(0).beforeStarting(m_armSubsystem.setArmMotors(-1)));
		//m_driverController.b().whileTrue(m_armSubsystem.setArmMotors(0).beforeStarting(m_armSubsystem.setArmMotors(1)));
	}

	/**
	 * Use this to pass the autonomous command to the main {@link Robot} class.
	 *
	 * @return the command to run in autonomous
	 */
	public Command getAutonomousCommand() {
		// An example command will be run in autonomous
		return Autos.mainAuto(m_driveSubsystem, m_armSubsystem);
  	}
}
