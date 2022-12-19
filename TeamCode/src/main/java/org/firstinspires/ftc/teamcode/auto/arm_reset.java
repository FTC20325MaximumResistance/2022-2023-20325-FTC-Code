package org.firstinspires.ftc.teamcode.auto;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.teamcode.util.Hard_Auto;
import org.firstinspires.ftc.teamcode.util.threads.crane.RobotArm;

@Disabled
@Autonomous(name = "Arm Reset", group = "Auto")
public class arm_reset extends LinearOpMode {
    Hard_Auto r = new Hard_Auto();
    @Override
    public void runOpMode() throws InterruptedException {
        r.init_robot(this);
        r.initAuto();
        RobotArm a = new RobotArm(this, r);
        waitForStart();
        while (!r.touch1.isPressed()) {
            r.arm1.setPower(0.5);
        }
        r.arm1.setPower(0);
        r.arm1.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        r.arm1.setTargetPosition(-(int) 9896);

        while (!r.getTolerance(r.arm1.getTargetPosition(), r.arm1.getCurrentPosition(), 5)){
            r.arm1.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            r.arm1.setPower(0.5);
            telemetry.addData("POS",r.arm1.getCurrentPosition());
            telemetry.addData("Target POS",r.arm1.getTargetPosition());

            telemetry.update();
        }

        r.arm1.setPower(0.0);
        r.arm1.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        a.degree_move_arm(1, 0.8, 90);
        while (!r.touch2.isPressed()) {
            r.arm2.setPower(0.5);
            r.arm3.setPower(0.5);
        }
        r.arm2.setPower(0);
        r.arm3.setPower(0);
        r.arm2.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        r.arm3.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
    }

}
