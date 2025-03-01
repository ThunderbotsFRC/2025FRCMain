// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import frc.robot.commands.MoveArm;
import frc.robot.subsystems.ArmSubsystem;
import frc.robot.subsystems.DriveSubsystem;
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
	public final DriveSubsystem m_driveSubsystem = new DriveSubsystem();
	public final ArmSubsystem m_armSubsystem = new ArmSubsystem();

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
		m_armSubsystem.setDefaultCommand(new MoveArm(m_armSubsystem));

		m_driverController.pov(0).whileTrue(m_armSubsystem.moveArmTo(0));
		m_driverController.pov(90).whileTrue(m_armSubsystem.moveArmTo(40));
		m_driverController.pov(180).whileTrue(m_armSubsystem.moveArmTo(-300));
		m_driverController.pov(270).whileTrue(m_armSubsystem.moveArmTo(-150));

		m_driverController.x().and(m_driverController.y().negate())
		.whileTrue(m_armSubsystem.SwitchMode(false)
			.andThen(m_armSubsystem.moveArm(0.8)))
		.onFalse(m_armSubsystem.moveArm(0));

		m_driverController.y().and(m_driverController.x().negate())
		.whileTrue(m_armSubsystem.SwitchMode(false)
			.andThen(m_armSubsystem.moveArm(-0.8)))
		.onFalse(m_armSubsystem.moveArm(0));

		m_driverController.a().and(m_driverController.b().negate()).onTrue(m_armSubsystem.setClawMotors(0.6)).onFalse(m_armSubsystem.setClawMotors(0));
		m_driverController.b().and(m_driverController.a().negate()).onTrue(m_armSubsystem.setClawMotors(-0.4)).onFalse(m_armSubsystem.setClawMotors(0));
		
		//m_driverController.y().onTrue(m_armSubsystem.toggleMotors());
		//m_driverController.a().whileTrue(m_armSubsystem.setArmMotors(0).beforeStarting(m_armSubsystem.setArmMotors(-1)));
		//m_driverController.b().whileTrue(m_armSubsystem.setArmMotors(0).beforeStarting(m_armSubsystem.setArmMotors(1)));
	}

	/**
	 * Use this to pass the autonomous command to the main {@link Robot} class.
	 *
	 * @return the command to run in autonomous
	 */
	/*public Command getAutonomousCommand() {
		// An example command will be run in autonomous
		return Autos.mainAuto(m_driveSubsystem, m_armSubsystem);
  	}*/
}
