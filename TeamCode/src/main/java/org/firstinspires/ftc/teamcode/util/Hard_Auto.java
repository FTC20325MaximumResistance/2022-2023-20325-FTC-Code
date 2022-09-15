package org.firstinspires.ftc.teamcode.util;

import androidx.annotation.VisibleForTesting;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

public class Hard_Auto extends Hardware{
    Hardware r = new Hardware();
    double dperr = 1;
    @Override
    public void initAuto(){
        super.initAuto();

    }

    @Override
    public void init_robot(OpMode opMode) {
        super.init_robot(opMode);
    }

    public void degree_rotate(double degrees, direction dir, double power){
        setDriverMotorMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        waiter(250);
        setDriverMotorMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        degrees = degrees * dperr;
        switch (dir){
            case LEFT:
                frontLeft.setTargetPosition((int)-degrees);
                frontRight.setTargetPosition((int)degrees);
                backLeft.setTargetPosition((int)-degrees);
                backRight.setTargetPosition((int)degrees);
                break;
            case RIGHT:
                frontLeft.setTargetPosition((int)degrees);
                frontRight.setTargetPosition((int)-degrees);
                backLeft.setTargetPosition((int)degrees);
                backRight.setTargetPosition((int)-degrees);
                break;
        }
        if (frontLeft.getTargetPosition() < 0){
            while(frontLeft.getCurrentPosition() > frontLeft.getTargetPosition()) {
                // Run your motors
                frontLeft.setPower(power*(frontLeft.getTargetPosition()/Math.abs(frontLeft.getTargetPosition())));
                frontRight.setPower(power*(frontRight.getTargetPosition()/Math.abs(frontRight.getTargetPosition())));
                backLeft.setPower(power*(backLeft.getTargetPosition()/Math.abs(backLeft.getTargetPosition())));
                backRight.setPower(power*(backRight.getTargetPosition()/Math.abs(backRight.getTargetPosition())));
            }
        }else if (frontLeft.getTargetPosition() > 0){
            while(frontLeft.getCurrentPosition() < frontLeft.getTargetPosition()){
                frontLeft.setPower(power*(frontLeft.getTargetPosition()/Math.abs(frontLeft.getTargetPosition())));
                frontRight.setPower(power*(frontRight.getTargetPosition()/Math.abs(frontRight.getTargetPosition())));
                backLeft.setPower(power*(backLeft.getTargetPosition()/Math.abs(backLeft.getTargetPosition())));
                backRight.setPower(power*(backRight.getTargetPosition()/Math.abs(backRight.getTargetPosition())));
            }
        }
    }
    public void distance_move(double inches, direction dir, double power){}

    public enum direction{
        FORWARD,
        BACKWARD,
        LEFT,
        RIGHT

    }

}
