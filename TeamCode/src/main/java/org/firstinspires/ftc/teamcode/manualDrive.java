package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.util.Hardware;

@TeleOp(name = "Manual Drive", group = "TeleOp")
public class manualDrive extends OpMode {
    Hardware r = new Hardware();
    double r1a = 0;
    double r2a = 360;
    double deflator;
    @Override
    public void init() {
        r.init_robot(this);
    }

    @Override
    public void loop() {

        deflator = gamepad1.left_bumper && gamepad1.right_bumper ? 0.1: gamepad1.left_bumper ? 0.4 : 0.7;
        double deflator2 = 0.5;
        double deflator3 = 0.9;
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
            code that runs our mecanum drive wheels
            */
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


        }
        r.arm2.setPower(gamepad2.right_stick_x* deflator2);
        r.arm3.setPower(gamepad2.right_stick_x* deflator2);
        r.arm1.setPower(gamepad2.left_stick_x*deflator3);
        int thing = gamepad2.right_bumper ? 360: gamepad2.left_bumper ? -360: 0;
        r1a += thing;
        if (r1a > 360){
            r1a = 360;
        }else if (r1a < 0){
            r1a = 0;
        }
        if (gamepad2.left_trigger > 0){
            r2a = 0;
        }else if (gamepad2.right_trigger > 0){
            r2a = 360;
        }
        r.claw.setPosition(r2a);
        r.spin.setPosition(r1a);
        composeTelemetry();



    }

    public void composeTelemetry(){
        telemetry.addData("Second Joint Power", r.arm2.getPower());
        telemetry.addData("Precision Mode", deflator < 0.7);
        try{
            telemetry.addData("Distance From Nearest Object", r.distanceSensor.cmOptical() * 2.54);
            telemetry.addData("Close Enough?", r.distanceSensor.cmOptical()*2.54 < 24);
        }catch(Exception e){
            telemetry.addLine("Range Sensor Offline");
        }
        String thing = r2a == 360 ? "Closed" : "Open";
        telemetry.addData("Claw", thing);
        telemetry.addData("Arm 1 Position", r.arm1.getCurrentPosition());
        telemetry.addData("Arm 2 Position", r.arm2.getCurrentPosition());
        telemetry.update();
    }
}
