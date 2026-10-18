package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import frc.robot.subsystems.Drivetrain;
import frc.robot.subsystems.Shooter;

public class AutoShoot {
    private final Shooter shooter;
    private final Drivetrain drivetrain;

    public AutoShoot(Shooter shooter, Drivetrain drivetrain){
        this.shooter = shooter;
        this.drivetrain = drivetrain;
    }

    public Command autoShoot(){
        return Commands.sequence(
            Commands.runOnce(drivetrain::zeroDirectionCommand),
            Commands.runOnce(shooter::runFeeder),
            Commands.waitSeconds(3.0),
            Commands.runOnce(shooter::runFlyWheelHub),
            Commands.waitSeconds(3.0),
            drivetrain.driveWithVelocity(-1, 0).withTimeout(1.0)

        );
    }
}
