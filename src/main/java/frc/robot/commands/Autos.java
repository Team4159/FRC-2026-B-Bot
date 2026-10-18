// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import frc.robot.subsystems.Drivetrain;
import frc.robot.subsystems.Shooter;
import frc.robot.subsystems.Intake;
import choreo.auto.AutoFactory;
import choreo.auto.AutoRoutine;
import choreo.auto.AutoTrajectory;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;

public class Autos {
    private final AutoFactory factory;
    private final Drivetrain drivetrain;
    private final Shooter shooter;
    private final Intake intake;

    public Autos(Drivetrain drivetrain, Shooter shooter, Intake intake) {
        this.drivetrain = drivetrain;
        this.shooter = shooter;
        this.intake = intake;
        factory = new AutoFactory(
                () -> drivetrain.getState().Pose,
                drivetrain::resetPose,
                drivetrain::followTrajectory,
                true, // flip path for red alliance
                drivetrain);
    }

    public AutoRoutine driveAndShoot() {
        AutoRoutine routine = factory.newRoutine("driveAndShoot");
        AutoTrajectory path1 = routine.trajectory("StartToShoot"); // file name add later idk what to do for it lwk
        AutoTrajectory path2 = routine.trajectory("ShootToDepot");

        routine.active().onTrue(Commands.sequence(
                Commands.print("test"),
                path1.resetOdometry(),
                path1.cmd(),
                shoot()));
        // path.done().onTrue(shoot());

        return routine;
    }

    private Command shoot() {
        return Commands.sequence(
                Commands.runOnce(drivetrain::stop),
                Commands.runOnce(shooter::runFlywheelTower, shooter),
                Commands.waitSeconds(1.5),
                Commands.runOnce(shooter::runFeeder),
                Commands.waitSeconds(5.0),
                Commands.runOnce(() -> {
                    shooter.stopFeeder();
                    shooter.stopFlywheel();
                }));
    }

    private Command intake() {
        return Commands.sequence(
                Commands.runOnce(intake::setDown, intake),
                Commands.waitSeconds(0.5),
                Commands.runOnce(intake::runRollers, intake),
                Commands.waitSeconds(5.0),
                Commands.runOnce(intake::stopRollers, intake));
    }
}
