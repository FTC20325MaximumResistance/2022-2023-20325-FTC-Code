package org.firstinspires.ftc.teamcode.concepts;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import org.firstinspires.ftc.teamcode.util.Hardware;
@SuppressWarnings("unused")
public class Concept_Two_Wheels extends OpMode {
    Hardware r = new Hardware();
    @Override
    public void init() {
        r.init_robot(this);
    }

    @Override
    public void loop() {
        //Right here is where you put all the TeleOp black magic
        double deflator;
        deflator = gamepad1.left_bumper ? 0.4 : 0.9;
        double x = gamepad1.left_stick_x;
        double y = -gamepad1.left_stick_y;
        double l_pow = x < 0 ? deflator*(y < 0 ? (x*y) : - (x*y)) : deflator*(y);
        double r_pow = x > 0 ? deflator*(y < 0 ? (-x*y) : - (-x*y)) : deflator*(y);




    }
}
