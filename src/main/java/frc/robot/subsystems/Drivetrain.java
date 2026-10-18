package frc.robot.subsystems;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import frc.robot.generated.CommandSwerveDrivetrain;
import frc.robot.generated.TunerConstants;
import java.util.function.Supplier;

import com.ctre.phoenix6.swerve.SwerveModule.DriveRequestType;
import com.ctre.phoenix6.swerve.SwerveRequest;

import choreo.trajectory.SwerveSample;

public class Drivetrain extends CommandSwerveDrivetrain {
    public final SwerveRequest.FieldCentric fieldCentricDrive = new SwerveRequest.FieldCentric()
            .withDriveRequestType(DriveRequestType.OpenLoopVoltage)
            .withDeadband(TunerConstants.kSpeedAt12Volts.baseUnitMagnitude() * 0.1)
            .withRotationalDeadband(0.1);

    private final Supplier<Double> inputX;
    private final Supplier<Double> inputY;
    private final Supplier<Double> inputRotation;

    private double speedMultiplier = 1.0;

    // for choreo
    private final PIDController xController = new PIDController(10, 0, 0);
    private final PIDController yController = new PIDController(10, 0, 0);
    private final PIDController headingController = new PIDController(7, 0, 0);
    private final SwerveRequest.ApplyFieldSpeeds pathRequest = new SwerveRequest.ApplyFieldSpeeds();

    public void followTrajectory(SwerveSample sample) {
        headingController.enableContinuousInput(-Math.PI, Math.PI);
        var pose = getState().Pose;
        var speeds = sample.getChassisSpeeds();
        speeds.vxMetersPerSecond += xController.calculate(pose.getX(), sample.x);
        speeds.vyMetersPerSecond += yController.calculate(pose.getY(), sample.y);
        speeds.omegaRadiansPerSecond += headingController.calculate(pose.getRotation().getRadians(), sample.heading);
        setControl(pathRequest.withSpeeds(speeds)
                .withWheelForceFeedforwardsX(sample.moduleForcesX())
                .withWheelForceFeedforwardsY(sample.moduleForcesY()));
    }
    //

    public Drivetrain(CommandXboxController controller) {
        super(
                TunerConstants.DrivetrainConstants,
                TunerConstants.FrontLeft,
                TunerConstants.FrontRight,
                TunerConstants.BackLeft,
                TunerConstants.BackRight);

        this.inputX = () -> -controller.getLeftY();
        this.inputY = () -> -controller.getLeftX();
        this.inputRotation = () -> -controller.getRightX();
    }

    @Override
    public void periodic() {
        super.periodic();
        var pose = getState().Pose;
        SmartDashboard.putNumberArray("Drivetrain Pose", new double[] {
                pose.getX(),
                pose.getY(),
                pose.getRotation().getRadians()
        });
    }

    public void setSlowMode(boolean slow) {
        speedMultiplier = slow ? 0.5 : 1.0;
    }

    public Command driveCommand() {
        return run(() -> {
            setControl(fieldCentricDrive
                    .withVelocityX(inputX.get() * TunerConstants.kSpeedAt12Volts.baseUnitMagnitude() * speedMultiplier)
                    .withVelocityY(inputY.get() * TunerConstants.kSpeedAt12Volts.baseUnitMagnitude() * speedMultiplier)
                    .withRotationalRate(inputRotation.get() * TunerConstants.kSpeedAt12Volts.baseUnitMagnitude()
                            * speedMultiplier));
        });
    }

    public void stop() {
        setControl(fieldCentricDrive.withVelocityX(0)
                .withVelocityY(0)
                .withRotationalRate(0));
    }

    public Command zeroDirectionCommand() {
        return runOnce(this::seedFieldCentric);
    }
}
