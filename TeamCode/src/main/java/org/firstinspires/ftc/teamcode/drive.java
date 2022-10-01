package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.robotcore.external.Func;
import org.firstinspires.ftc.teamcode.util.Hardware;
import org.firstinspires.ftc.teamcode.util.threads.crane.RobotArm;
import org.firstinspires.ftc.teamcode.util.threads.poles.object_detection;
import org.tensorflow.lite.task.vision.detector.ObjectDetector;
@TeleOp(name = "drive",group = "TeleOp")
public class drive extends OpMode {
    Hardware r = new Hardware();
    int height = 1;
    boolean var_init = false;
    RobotArm ra;
    object_detection od;
    @Override
    public void init() {
        r.init_robot(this);
        composeTelemetry();
    }

    @Override
    public void loop() {
        if (var_init){
            ra = new RobotArm(this, r.arm1, r.arm2);
            od = new object_detection(this, height);
            var_init = false;
        }


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
            if (gamepad1.right_bumper){
                gamepad1.rumble(500);
            }
            //legacy code that runs our mecanum drive wheels

            //this first section creates the variables that will be used later

            //first we must translate the rectangular values of the joystick into polar coordinates;
            double y = -1 * gamepad1.left_stick_y;
            double x = gamepad1.left_stick_x;
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
            double power2 = v2 + rotation;
            double power3 = v2 - rotation;
            double power4 = v1 + rotation;

            r.frontLeft.setPower(power1 * deflator);
            r.frontRight.setPower(power2 * deflator);
            r.backLeft.setPower(power3 * deflator);
            r.backRight.setPower(power4 * deflator);

        }
        if (gamepad2.dpad_up){
            if (height != 3) {
                height += 1;
            }
        }else if (gamepad2.dpad_down){
            if (height != 1){
                height -= 1;
            }
        }
        od.setHeight(height);
        if (gamepad2.a && !ra.folded){
            ra.fold_arm();
        }
        od.start();
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

