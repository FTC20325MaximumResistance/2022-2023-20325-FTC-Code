package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.teamcode.util.Hardware;
import org.firstinspires.ftc.teamcode.util.threads.crane.RobotArm;

@TeleOp(name="Arm Test", group = "TeleOp")
public class armTest extends OpMode {
    Hardware r = new Hardware();
    RobotArm a;
    int angle = 0;
    @Override
    public void init() {
        r.init_robot(this);
        DcMotor arm1 = hardwareMap.dcMotor.get("arm1");
        DcMotor arm2 = hardwareMap.dcMotor.get("arm2");
        DcMotor f = hardwareMap.dcMotor.get("arm3");
        a = new RobotArm(this, arm1, arm2, f);
        arm1.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
    }


    @Override
    public void loop() {
        angle += gamepad1.left_stick_x;
        if (gamepad1.left_stick_x != 0){
            r.waiter(25);
        }
        if (angle < 0){
            angle = 0;

        }
        if (angle > 180){
            angle = 180;
        }
        telemetry.addData("Set degrees", angle);
        telemetry.update();
        if (gamepad1.a){
            a.degree_move_arm(1,0.9, angle);
        }

    }
}
