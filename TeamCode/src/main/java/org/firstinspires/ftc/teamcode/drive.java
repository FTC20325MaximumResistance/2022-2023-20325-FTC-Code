package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.util.Hardware;
import org.firstinspires.ftc.teamcode.util.threads.crane.RobotArm;

@TeleOp(name = "Drive",group = "TeleOp")
public class drive extends OpMode {
    Hardware r = new Hardware();
    RobotArm ra;
    @Override
    public void init() {
        r.init_robot(this);
        ra = new RobotArm(this, r.arm1, r.arm2, r.arm3);

    }

    @Override
    public void loop() {

        double deflator;
        deflator = gamepad1.left_bumper && gamepad1.right_bumper ? 0.1: gamepad1.left_bumper ? 0.4 : 0.7;
        //Right here is where you put all the TeleOp black magic
        //great comment Evan 10/10 would recommend
        {

            /*Message to future Evan: Don't mess with this
            Everything down here you will not understand
            Let Ben mess with this and get to learning how to use java
            stop scrolling down here
            I said stop!
            No Evan...
            Don't
            This is painful down here
            Stop
            Do you like black holes?
            You will somehow create one if you
            mess with this
            */
            if (gamepad1.left_bumper)
                deflator = 0.9;
            gamepad1.rumble(500);
            if (gamepad1.right_bumper) {
                deflator = 0.4;
                gamepad1.rumble(500);
            }
            //legacy code that runs our mecanum drive wheels

            //this first section creates the variables that will be used later

            //first we must translate the rectangular values of the joystick into polar coordinates;
            double y = -gamepad1.left_stick_y;
            double x = -gamepad1.left_stick_x;
            double angle = 0;

            if (y > 0 && x > 0)//quadrant 1
                angle = Math.atan(y / x);
            else {
                double angle1 = Math.toRadians(180) + Math.atan(y / x);
                if (y > 0 && x < 0)//quadrant 2
                    angle = angle1;
                else if (y < 0 && x < 0)//quadrant 3
                    angle = angle1;
                else if (y < 0 && x > 0)//quadrant 4
                    angle = Math.toRadians(360) + Math.atan(y / x);
            }

            if (y == 0 && x > 1)
                angle = 0;
            if (y > 0 && x == 0)
                angle = Math.PI / 2;
            if (y == 0 && x < 0)
                angle = Math.PI;
            if (y < 0 && x == 0)
                angle = 3 * Math.PI / 2;

            double velocity = Math.sqrt(Math.pow(gamepad1.left_stick_y, 2) + Math.pow(gamepad1.left_stick_x, 2));
            double rotation = -gamepad1.right_stick_x;

            //equations taking the polar coordinates and turing them into motor powers
            double v1 = velocity * Math.cos(angle + (Math.PI / 4));
            double v2 = velocity * Math.sin(angle + (Math.PI / 4));
            double power1 = v1 - rotation;
            double power2 = v2 - rotation;
            double power3 = v2 + rotation;
            double power4 = v1 + rotation;

            r.frontLeft.setPower(power1 * deflator);
            r.frontRight.setPower(power2 * deflator);
            r.backLeft.setPower(power3 * deflator);
            r.backRight.setPower(power4 * deflator);

            composeTelemetry();

        }
    }
    void composeTelemetry(){
        telemetry.addLine("Debug - ")
                .addData("Left X", () -> String.valueOf(gamepad1.left_stick_x))
                .addData("Left Y", () -> String.valueOf(gamepad1.left_stick_y))
                .addData("Right X", () -> String.valueOf(gamepad1.right_stick_x))
                .addData("Right Y", () -> String.valueOf(gamepad1.right_stick_y));

        telemetry.addLine("Gamepad")
                .addData("Ultra Precision Mode", () -> String.valueOf(gamepad1.left_bumper && gamepad1.right_bumper))
                .addData("Precision Mode", () -> String.valueOf(gamepad1.left_bumper && !gamepad1.right_bumper));

        }
}

