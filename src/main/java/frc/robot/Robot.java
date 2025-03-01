// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;

import edu.wpi.first.apriltag.AprilTagDetection;
import edu.wpi.first.apriltag.AprilTagDetector;
import edu.wpi.first.apriltag.AprilTagPoseEstimator;
import edu.wpi.first.cameraserver.CameraServer;
import edu.wpi.first.cscore.CvSink;
import edu.wpi.first.cscore.CvSource;
import edu.wpi.first.cscore.UsbCamera;
import edu.wpi.first.math.geometry.Transform3d;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import edu.wpi.first.wpilibj2.command.WaitCommand;

/**
 * The VM is configured to automatically run this class, and to call the functions corresponding to
 * each mode, as described in the TimedRobot documentation. If you change the name of this class or
 * the package after creating this project, you must also update the build.gradle file in the
 * project.
 */
public class Robot extends TimedRobot {
	private Command m_autonomousCommand;

	private RobotContainer m_robotContainer;
	private Thread visionThread;

	private Timer timer = new Timer();

	/**
	 * This function is run when the robot is first started up and should be used for any
	 * initialization code.
	 */
	@Override
	public void robotInit() {
		// Instantiate our RobotContainer.  This will perform all our button bindings, and put our
		// autonomous chooser on the dashboard.

		m_robotContainer = new RobotContainer();
		/*visionThread = new Thread(
            () -> {
              	// Get the UsbCamera from CameraServer
				UsbCamera camera = CameraServer.startAutomaticCapture();
				// Set the resolution
				camera.setResolution(640, 480);
				camera.setFPS(30);

				// Get a CvSink. This will capture Mats from the camera
				CvSink cvSink = CameraServer.getVideo();
				// Setup a CvSource. This will send images back to the Dashboard
				CvSource outputStream = CameraServer.putVideo("Front Cam", 640, 480);

				// Mats are very memory expensive. Lets reuse this Mat.
				Mat mat = new Mat();
				Mat grayMat = new Mat();

				final AprilTagDetector detector = new AprilTagDetector();
				//detector.addFamily("tag16h5");
        		detector.addFamily("tag36h11", 1);

				AprilTagPoseEstimator.Config config = new AprilTagPoseEstimator.Config(0.165, 699.3778103158814, 677.7161226393544, 345.6059345433618, 207.12741326228522);
				final AprilTagPoseEstimator estimator = new AprilTagPoseEstimator(config);

				// This cannot be 'true'. The program will never exit if it is. This
				// lets the robot stop this thread when restarting robot code or
				// deploying.
				while (!Thread.interrupted()) {
					// Tell the CvSink to grab a frame from the camera and put it
					// in the source mat.  If there is an error notify the output.
					if (cvSink.grabFrame(mat) == 0) {
						// Send the output the error.
						outputStream.notifyError(cvSink.getError());
						continue;
					}

					Imgproc.cvtColor(mat, grayMat, Imgproc.COLOR_BGR2GRAY);
					
					// run detection
					for (AprilTagDetection detection : detector.detect(grayMat)) {
						for (int i = 0; i < 4; i++) {
							int j = (i + 1) % 4;
							Point pt1 = new Point(detection.getCornerX(i), detection.getCornerY(i));
							Point pt2 = new Point(detection.getCornerX(j), detection.getCornerY(j));
							Imgproc.line(mat, pt1, pt2, new Scalar(0, 255, 0), 2);
						}

						var cx = detection.getCenterX();
						var cy = detection.getCenterY();
						var ll = 10;
						Imgproc.line(mat, new Point(cx - ll, cy), new Point(cx + ll, cy), new Scalar(255, 0, 0), 2);
						Imgproc.line(mat, new Point(cx, cy - ll), new Point(cx, cy + ll), new Scalar(255, 0, 0), 2);
						Imgproc.putText(mat, Integer.toString(detection.getId()), new Point (cx + ll, cy), Imgproc.FONT_HERSHEY_SIMPLEX, 1, new Scalar(255, 0, 0), 3);
					
						Transform3d pose = estimator.estimate(detection);
						Imgproc.putText(mat, Double.toString(Math.round(pose.getX()*100)), new Point(5, 25), Imgproc.FONT_HERSHEY_COMPLEX, 1, new Scalar(255,0 ,0));
						Imgproc.putText(mat, Double.toString(Math.round(pose.getY()*100)), new Point(105, 25), Imgproc.FONT_HERSHEY_COMPLEX, 1, new Scalar(255,0 ,0));
						Imgproc.putText(mat, Double.toString(Math.round(pose.getZ()*100)), new Point(205, 25), Imgproc.FONT_HERSHEY_COMPLEX, 1, new Scalar(255,0 ,0));
					}
					
					// Give the output stream a new image to display
					outputStream.putFrame(mat);
				}
				detector.close();
    		});
    	visionThread.setDaemon(true);
    	visionThread.start();*/
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
		// Runs the Scheduler.  This is responsible for polling buttons, adding newly-scheduled
		// commands, running already-scheduled commands, removing finished or interrupted commands,
		// and running subsystem periodic() methods.  This must be called from the robot's periodic
		// block in order for anything in the Command-based framework to work.
		SmartDashboard.putNumber("enc", m_robotContainer.m_armSubsystem.ArmEncoder.getDistance());
		CommandScheduler.getInstance().run();
	}

	/** This function is called once each time the robot enters Disabled mode. */
	@Override
	public void disabledInit() {}

	@Override
	public void disabledPeriodic() {}

	/** This autonomous runs the autonomous command selected by your {@link RobotContainer} class. */
	@Override
	public void autonomousInit() {
		timer.start();
		timer.reset();
		m_autonomousCommand = m_robotContainer.m_driveSubsystem.TankDrive(-1, 0);//m_robotContainer.getAutonomousCommand();

		// schedule the autonomous command (example)
		if (m_autonomousCommand != null) {
			CommandScheduler.getInstance().cancelAll();
			System.out.println("pluh");
			m_autonomousCommand.schedule();
		}
	}

	/** This function is called periodically during autonomous. */
	@Override
	public void autonomousPeriodic() {
		// if (timer.get() < 5) {
		// 	m_robotContainer.m_driveSubsystem.TankDrive(-0.2, 0);
		// 	System.out.println("runnimg");
		// 	//m_autonomousCommand.cancel();
		// } else {
		// 	m_robotContainer.m_driveSubsystem.TankDrive(0, 0);
		// }
	}

	@Override
	public void teleopInit() {
		// This makes sure that the autonomous stops running when
		// teleop starts running. If you want the autonomous to
		// continue until interrupted by another command, remove
		// this line or comment it out.
		if (m_autonomousCommand != null) {
		m_autonomousCommand.cancel();
		}
	}

	/** This function is called periodically during operator control. */
	@Override
	public void teleopPeriodic() {

	}

	@Override
	public void testInit() {
		// Cancels all running commands at the start of test mode.
		CommandScheduler.getInstance().cancelAll();
	}

	/** This function is called periodically during test mode. */
	@Override
	public void testPeriodic() {}

	/** This function is called once when the robot is first started up. */
	@Override
	public void simulationInit() {}

	/** This function is called periodically whilst in simulation. */
	@Override
	public void simulationPeriodic() {}
}
