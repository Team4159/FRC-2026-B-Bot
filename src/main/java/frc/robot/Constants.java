// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import com.ctre.phoenix6.configs.CANcoderConfiguration;
import com.ctre.phoenix6.configs.TalonFXConfiguration;
import com.ctre.phoenix6.signals.FeedbackSensorSourceValue;
import com.ctre.phoenix6.signals.NeutralModeValue;
import com.ctre.phoenix6.signals.SensorDirectionValue;

/**
 * The Constants class provides a convenient place for teams to hold robot-wide numerical or boolean
 * constants. This class should not be used for any other purpose. All constants should be declared
 * globally (i.e. public static). Do not put anything functional in this class.
 *
 * <p>It is advised to statically import this class (or one of its inner classes) wherever the
 * constants are needed, to reduce verbosity.
 */
public final class Constants {
  public static class OperatorConstants {
    public static final int kDriverControllerPort = 0;
  }

  public static class DrivetrainConstants{
    public static final double DRIVETRAIN_STATOR_CURRENT_LIMIT = 60.0;
    public static final double DRIVETRAIN_SUPPLY_CURRENT_LIMIT = 30.0;
  }

  public static class IntakeConstants{
    public static final int ANGLE_MOTOR_ID = 6;
    public static final int SPIN_MOTOR_ID = 7;
    public static final int ANGLE_ENCODER_ID = 1;

    public static final CANcoderConfiguration ANGLE_CAN_CODER_CONFIG = new CANcoderConfiguration(){{
      MagnetSensor.AbsoluteSensorDiscontinuityPoint = 0.8;
    }};
    public static final TalonFXConfiguration INTAKE_ANGLE_CONFIG = new TalonFXConfiguration() {{
      MotorOutput.withNeutralMode(NeutralModeValue.Brake);
    }};

    public static final double ANGLE_DOWN = 0; //place holder plz read dashboard and get down intake angle ;-;
    public static final double ANGLE_UP = 0.71337890625; //idk man, plz read dashboard, i dont have bot w me...

    public static final double ROLLER_SPEED = 1.0;

    public static final double INTAKE_STATOR_CURRENT_LIMIT = 60.0;
    public static final double INTAKE_SUPPLY_CURRENT_LIMIT = 30.0;

    public enum IntakeState {
      INTAKE_DOWN_RUN,
      INTAKE_STOP_RUN,
      INTAKE_UP
      // INTAKE_DOWN,
      // OUTTAKE
    }

    static{
      ANGLE_CAN_CODER_CONFIG.MagnetSensor.SensorDirection = SensorDirectionValue.CounterClockwise_Positive;
      INTAKE_ANGLE_CONFIG.Feedback.FeedbackRemoteSensorID = ANGLE_ENCODER_ID;
      INTAKE_ANGLE_CONFIG.Feedback.FeedbackSensorSource = FeedbackSensorSourceValue.RemoteCANcoder;
      INTAKE_ANGLE_CONFIG.Feedback.RotorToSensorRatio = 25.0;
      INTAKE_ANGLE_CONFIG.Feedback.SensorToMechanismRatio = 2.0;


      INTAKE_ANGLE_CONFIG.Slot0.kP = 0.3;
      INTAKE_ANGLE_CONFIG.Slot0.kI = 0;
      INTAKE_ANGLE_CONFIG.Slot0.kD = 0;
      INTAKE_ANGLE_CONFIG.Slot0.kV = 0.1;
    }
  }

  public static class ShooterConstants {
    public static final int FLYWHEEL_MOTOR_TOP_LEFT_ID = 10;
    public static final int FLYWHEEL_MOTOR_BOTTOM_LEFT_ID = 11;
    public static final int FLYWHEEL_MOTOR_TOP_RIGHT_ID = 12;
    public static final int FLYWHEEL_MOTOR_BOTTOM_RIGHT_ID = 13;

    public static final int FEEDER_MOTOR_ID = 14;

    public static final double FLYWHEEL_TOWER_SPEED = 100.0;
    public static final double FLYWHEEL_HUB_SPEED = 75.0;
    public static final double FEEDER_SPEED = 1.0;

    public static final double SHOOTER_STATOR_CURRENT_LIMIT = 60.0;
    public static final double SHOOTER_SUPPLY_CURRENT_LIMIT = 30.0;

    public static final TalonFXConfiguration FLYWHEEL_CONFIG =
      new TalonFXConfiguration();

    static {
      FLYWHEEL_CONFIG.Slot0.kP = 0.5;
      FLYWHEEL_CONFIG.Slot0.kI = 0.0;
      FLYWHEEL_CONFIG.Slot0.kD = 0.0;
      FLYWHEEL_CONFIG.Slot0.kV = 0.1;
    }
  }
}
