package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.util.Hardware;
import org.firstinspires.ftc.teamcode.util.threads.crane.RobotArm;
@Disabled
@TeleOp(name="Arm Test", group = "TeleOp")
public class armTest extends OpMode {
    Hardware r = new Hardware();
    RobotArm a;
    int angle = 0;
    @Override
    public void init() {
        r.init_robot(this);

        a = new RobotArm(this, r);

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
