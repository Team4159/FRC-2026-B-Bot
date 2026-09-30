package frc.robot.subsystems;

import com.ctre.phoenix6.controls.DutyCycleOut;
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

    private final PositionVoltage angle_request = new PositionVoltage(0);

    private final DutyCycleOut roller_request = new DutyCycleOut(0);

    public Intake(){
        angleMotor = new TalonFX(IntakeConstants.ANGLE_ENCODER_ID);
        spinMotor = new TalonFX(IntakeConstants.SPIN_MOTOR_ID);
        angleEncoder = new CANcoder(IntakeConstants.ANGLE_ENCODER_ID);

        angleEncoder.getConfigurator().apply(IntakeConstants.ANGLE_CAN_CODER_CONFIG);
        angleMotor.getConfigurator().apply(IntakeConstants.ANGLE_CONFIG);
    }
    
    public double getAngle(){
        return angleEncoder.getAbsolutePosition().getValueAsDouble();
    }

    public void setAngle(double angle){
        angleMotor.setControl(angle_request.withPosition(angle));
    }

    public void setDown(){
        setAngle(IntakeConstants.ANGLE_DOWN);
    }
    public void setUP(){
        setAngle(IntakeConstants.ANGLE_UP);
    }

    //roller kraken x60
    public void runRollers(){
        spinMotor.setControl(roller_request.withOutput(IntakeConstants.ROLLER_SPEED));
    }
    public void reverseRollers(){
        spinMotor.setControl(roller_request.withOutput(-IntakeConstants.ROLLER_SPEED));
    }
    public void stopRollers(){
        spinMotor.setControl(roller_request.withOutput(0));
    }

    public void setState(IntakeState state){
        switch (state) {
            case DOWN_ON:
                setDown();
                runRollers();
                break;

            case DOWN_OFF:
                setDown();
                stopRollers();
                break;
            
            case UP_OFF:
                setUP();
                stopRollers();
                break;
        }
    }

    @Override
    public void periodic(){
        SmartDashboard.putNumber("Intake Angle", getAngle());
    }
}
