package frc.robot.subsystems;

import com.ctre.phoenix6.configs.TalonFXConfiguration;
import com.ctre.phoenix6.controls.DutyCycleOut;
import com.ctre.phoenix6.controls.PositionDutyCycle;
import com.ctre.phoenix6.controls.PositionVoltage;
import com.ctre.phoenix6.hardware.CANcoder;
import com.ctre.phoenix6.hardware.TalonFX;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.IntakeConstants;

import frc.robot.Constants.IntakeConstants.IntakeState;

public class Intake extends SubsystemBase{
    private final TalonFX angleMotor;
    private final TalonFX spinMotor;
    private final CANcoder angleEncoder;

    private final PositionDutyCycle angle_request = new PositionDutyCycle(0);

    private final DutyCycleOut roller_request = new DutyCycleOut(0);

    private int rollerDirection = 1;

    public Intake(){
        angleMotor = new TalonFX(IntakeConstants.ANGLE_MOTOR_ID);
        spinMotor = new TalonFX(IntakeConstants.SPIN_MOTOR_ID);
        angleEncoder = new CANcoder(IntakeConstants.ANGLE_ENCODER_ID);

        TalonFXConfiguration ANGLE_CONFIG = IntakeConstants.INTAKE_ANGLE_CONFIG.clone();

        intakeConfigureCurrentLimits(ANGLE_CONFIG);

        angleEncoder.getConfigurator().apply(IntakeConstants.ANGLE_CAN_CODER_CONFIG);
        angleMotor.getConfigurator().apply(ANGLE_CONFIG);
    }

    private void intakeConfigureCurrentLimits(TalonFXConfiguration config){
        config.CurrentLimits.StatorCurrentLimit = IntakeConstants.INTAKE_STATOR_CURRENT_LIMIT;
        config.CurrentLimits.StatorCurrentLimitEnable = true;
        config.CurrentLimits.SupplyCurrentLimit = IntakeConstants.INTAKE_SUPPLY_CURRENT_LIMIT;
        config.CurrentLimits.SupplyCurrentLimitEnable = true;
    }
    
    public double getAngle(){
        return angleMotor.getPosition().getValueAsDouble();
    }

    public void setAngle(double angle){
        angleMotor.setControl(angle_request.withPosition(angle));
    }

    //to get values for intake
    // public void slowMoveDown(){
    //     angleMotor.setControl(new DutyCycleOut(-0.05));
    // }
    // public void slowMoveUp(){
    //     angleMotor.setControl(new DutyCycleOut(0.05));
    // }
    // public void stopAngleMotor(){
    //     angleMotor.setControl(new DutyCycleOut(0));
    // }

    public void setDown(){
        setAngle(IntakeConstants.ANGLE_DOWN);
    }
    public void setUp(){
        setAngle(IntakeConstants.ANGLE_UP);
    }

    public void setReverseRoller(boolean reverse){rollerDirection = reverse ? -1 : 1;}

    //roller
    public void runRollers(){
        spinMotor.setControl(roller_request.withOutput(IntakeConstants.ROLLER_SPEED * rollerDirection));
    }
    public void stopRollers(){
        spinMotor.setControl(roller_request.withOutput(0));
    }

    public void setState(IntakeState state){
        switch (state) {
            case INTAKE_DOWN_RUN:
                setDown();
                runRollers();
                break;
            
            case INTAKE_STOP_RUN:
                stopRollers();
                break;
            
            case INTAKE_UP:
                setUp();
                break;

            // case INTAKE_DOWN:
            //     setDown();
            //     break;

            // case OUTTAKE:
            //     runReverseRollers(); ///not used in container yet
            //     break;
        }
    }

    @Override
    public void periodic(){
        SmartDashboard.putNumber("Intake Angle (rotations)", getAngle());
    }
}
