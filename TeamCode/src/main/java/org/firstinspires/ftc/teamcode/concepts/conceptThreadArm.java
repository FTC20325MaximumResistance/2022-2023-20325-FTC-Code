package org.firstinspires.ftc.teamcode.concepts;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.util.Hardware;

@TeleOp(name = "Threaded Arm Test", group = "TeleOp")
public class conceptThreadArm extends OpMode {
    Hardware r = new Hardware();
    @Override
    public void init() {
        r.init_robot(this);

    }

    @Override
    public void loop() {
        r.test.setPower(gamepad2.left_stick_x*0.9);

    }
}
