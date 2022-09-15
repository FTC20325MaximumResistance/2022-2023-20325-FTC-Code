package org.firstinspires.ftc.teamcode.concepts;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import org.firstinspires.ftc.teamcode.util.Hardware;

public class Concept_Tank_Drive extends OpMode {
    Hardware r = new Hardware();
    @Override
    public void init() {
        r.init_robot(this);
    }

    @Override
    public void loop() {
        double deflator;
        deflator = gamepad1.left_bumper ? 0.4 : 0.9;
        double r_y = gamepad1.right_stick_y;
        double l_y = -gamepad1.left_stick_y;
        double l_pow = l_y * deflator;
        double r_pow = r_y * deflator;
    }
}
