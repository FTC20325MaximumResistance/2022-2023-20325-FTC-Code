package org.firstinspires.ftc.teamcode.auto;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.teamcode.util.Hard_Auto;
@Autonomous(name = "Arm Reset", group = "Auto")
public class arm_reset extends LinearOpMode {
    Hard_Auto r = new Hard_Auto();
    @Override
    public void runOpMode() throws InterruptedException {
        r.init_robot(this);
        r.initAuto();
        waitForStart();
        while (!r.touch1.isPressed()) {
            r.arm1.setPower(0.5);
        }
        r.arm1.setPower(0);
        r.arm1.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        while (!r.touch2.isPressed()) {
            r.arm2.setPower(0.45);
            r.arm3.setPower(0.45);
        }
        r.arm2.setPower(0);
        r.arm3.setPower(0);
        r.arm2.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        r.arm3.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
    }
}
