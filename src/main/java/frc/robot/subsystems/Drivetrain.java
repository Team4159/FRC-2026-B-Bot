package frc.robot.subsystems;

import edu.wpi.first.units.measure.LinearVelocity;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import frc.robot.generated.CommandSwerveDrivetrain;
import frc.robot.generated.TunerConstants;
import java.util.function.Supplier;

import com.ctre.phoenix6.swerve.SwerveModule.DriveRequestType;
import com.ctre.phoenix6.swerve.SwerveRequest;
import com.ctre.phoenix6.swerve.SwerveRequest.ForwardPerspectiveValue;

public class Drivetrain extends CommandSwerveDrivetrain{
    public final SwerveRequest.FieldCentric fieldCentricDrive = new SwerveRequest.FieldCentric()
        .withForwardPerspective(ForwardPerspectiveValue.BlueAlliance)
        .withDriveRequestType(DriveRequestType.OpenLoopVoltage);

    private final Supplier<Double> inputX;
    private final Supplier<Double> inputY; 
    private final Supplier<Double> inputRotation;

    public Drivetrain(CommandXboxController controller){
        super(
            TunerConstants.DrivetrainConstants,
            TunerConstants.FrontLeft,
            TunerConstants.FrontRight,
            TunerConstants.BackLeft,
            TunerConstants.BackRight
        );

        this.inputX = () -> -controller.getLeftY();
        this.inputY = () -> -controller.getLeftX();
        this.inputRotation = () -> -controller.getRightX();
    }

    public Command driveCommand() {
        return run(() -> {
            setControl(fieldCentricDrive.withVelocityX(inputX.get() * TunerConstants.kSpeedAt12Volts.baseUnitMagnitude())
                .withVelocityY(inputY.get() * TunerConstants.kSpeedAt12Volts.baseUnitMagnitude())
                .withRotationalRate(inputRotation.get() * TunerConstants.kSpeedAt12Volts.baseUnitMagnitude()));
        });
    }

    public Command zeroDirectionCommand(){
        return runOnce(this::seedFieldCentric);
    }
}
