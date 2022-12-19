package org.firstinspires.ftc.teamcode.util.threads.crane;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.teamcode.util.Hardware;

public class towel extends Thread{
    OpMode opMode;
    Hardware r;
    int armNum;
    double power;
    int position;
    @Override
    public void run(){
        super.run();
        switch (armNum){
            case 1:
                r.arm1.setTargetPosition(position);
                r.arm1.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                while (r.getTolerance(r.arm1.getCurrentPosition(), position, 10)){
                    r.arm1.setPower(power);
                }
                r.arm1.setPower(0.0);
                break;
            case 2:
                r.arm2.setTargetPosition(position);
                r.arm3.setTargetPosition(position);
                r.arm2.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                r.arm3.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                while(r.getTolerance(r.arm2.getCurrentPosition(), position, 5)){
                    r.arm2.setPower(power);
                    r.arm3.setPower(power);
                }
                r.arm2.setPower(0);
                r.arm3.setPower(0);
                break;
        }
    }
    public towel(OpMode opMode, Hardware r){
        this.opMode = opMode;
        this.r = r;
    }

    public void moveArm(int armNum,double power, int position){
        this.armNum = armNum;
        this.power = power;
        this.position = position;
        start();
    }

}
