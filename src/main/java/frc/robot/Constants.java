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

  public static class IntakeConstants{
    public static final int ANGLE_MOTOR_ID = 6;
    public static final int SPIN_MOTOR_ID = 7;
    public static final int ANGLE_ENCODER_ID = 1;

    public static final CANcoderConfiguration ANGLE_CAN_CODER_CONFIG = new CANcoderConfiguration();
    public static final TalonFXConfiguration ANGLE_CONFIG = new TalonFXConfiguration() {{
      MotorOutput.withNeutralMode(NeutralModeValue.Brake);
    }};

    public static final double ANGLE_KP = 1;
    public static final double ANGLE_KI = 0;
    public static final double ANGLE_KD = 0;

    public static final double ANGLE_DOWN = 0.0; //place holder plz read dashboard and get down intake angle ;-;
    public static final double ANGLE_UP = 0.25; //idk man, plz read dashboard, i dont have bot w me... 

    public static final double ROLLER_SPEED = 0.5; //test val
    
    public enum IntakeState {
      DOWN_ON,
      DOWN_OFF,
      UP_OFF
    }

    static{
      ANGLE_CAN_CODER_CONFIG.MagnetSensor.SensorDirection = SensorDirectionValue.CounterClockwise_Positive;
      ANGLE_CONFIG.Feedback.FeedbackRemoteSensorID = ANGLE_ENCODER_ID;
      ANGLE_CONFIG.Feedback.FeedbackSensorSource = FeedbackSensorSourceValue.RemoteCANcoder;

      ANGLE_CONFIG.Slot0.kP = ANGLE_KP;
      ANGLE_CONFIG.Slot0.kI = ANGLE_KI;
      ANGLE_CONFIG.Slot0.kD = ANGLE_KD;
    }
  }

  public static class ShooterConstants {
    public static final int FLYWHEEL_MOTOR_TOP_LEFT_ID = 10;
    public static final int FLYWHEEL_MOTOR_BOTTOM_LEFT_ID = 11;
    public static final int FLYWHEEL_MOTOR_TOP_RIGHT_ID = 12;
    public static final int FLYWHEEL_MOTOR_BOTTOM_RIGHT_ID = 13;

    public static final int FEEDER_MOTOR_ID = 14;

    public static final double FLYWHEEL_SPEED = 50;
    public static final double FEEDER_SPEED = 0.5;

    public static final double FLYWHEEL_KP = 0.1;
    public static final double FLYWHEEL_KI = 0;
    public static final double FLYWHEEL_KD = 0;

    public static final TalonFXConfiguration FLYWHEEL_CONFIG =
      new TalonFXConfiguration();

    static {
      FLYWHEEL_CONFIG.Slot0.kP = FLYWHEEL_KP;
      FLYWHEEL_CONFIG.Slot0.kI = FLYWHEEL_KI;
      FLYWHEEL_CONFIG.Slot0.kD = FLYWHEEL_KD;
    }
  }
}
