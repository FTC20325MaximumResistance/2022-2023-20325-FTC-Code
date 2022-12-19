package org.firstinspires.ftc.teamcode.auto;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.util.Hard_Auto;
import org.firstinspires.ftc.teamcode.util.dictionary;
import org.firstinspires.ftc.teamcode.util.threads.auton.autoNavigate;
import org.firstinspires.ftc.teamcode.util.threads.auton.customDetectLibrary;

@Disabled
@Autonomous(name="Experimental Auto", group = "Auto")
public class testAuto extends LinearOpMode {
    Hard_Auto r = new Hard_Auto();
    autoNavigate a;
    customDetectLibrary c;

    @Override
    public void runOpMode() throws InterruptedException {
        r.init_robot(this);
        r.spin.setPosition(1);
        String[] cones = new String[]{"1", "2", "3"};
        String[] nav = new String[]{"wire", "computer", "engine", "power"};
        dictionary limits = new dictionary(new String[]{"total poles", "pos"}, new double[]{2, 3});
        dictionary mins = new dictionary(new String[]{"pos","total poles"}, new double[]{0, 0});
        dictionary selections = new dictionary(new String[]{"total poles", "pos"}, new double[]{1, 2});
        dictionary stats = new dictionary(new String[]{"total poles", "pos"}, new double[]{1, 1});
        dictionary poss = new dictionary(new String[]{"Blue Left", "Red Right", "Red Left", "Blue Right"}, new double[]{1, 2, 3, 0});
        int selection = 1;
        int add1 = 0;
        int add2 = 0;
        boolean test = false;
        c = new customDetectLibrary(this, r);
        ElapsedTime time = new ElapsedTime();
        while (!isStarted() && !gamepad1.a && !isStopRequested()) {
            time.reset();
            if (gamepad1.dpad_down) {
                if (selection < selections.length()) {
                    add1 = 1;
                    r.waiter(250);
                }
            } else if (gamepad1.dpad_up) {
                if (selection > 0) {
                    add1 = -1;
                    r.waiter(250);
                }
            }
            if (gamepad1.dpad_right) {
                add2 = 1;
                r.waiter(250);
            } else if (gamepad1.dpad_left) {
                add2 = -1;
                r.waiter(250);
            }
            stats.changeValue(selections.getValue(selection), stats.getValue(selections.getValue(selection)) + add2);
            selection += add1;
            add1 = 0;
            add2 = 0;
            if (stats.getValue(selections.getValue(selection)) < 0) {
                stats.changeValue(selections.getValue(selection), limits.getValue(selections.getValue(selection)));
            }
            if (stats.getValue(selections.getValue(selection)) > limits.getValue(selections.getValue(selection))) {
                stats.changeValue(selections.getValue(selection), 0);
            }
            String thing;
            for (String i : stats.getKeys()) {
                thing = String.valueOf(stats.getValue(i));
                if (i.equals("pos")){
                    thing = poss.getValue(stats.getValue(i));
                }
                if (i.equals(selections.getValue(selection))){
                    thing += " +";
                }
                telemetry.addData(i, thing);
            }
            telemetry.addData("Lag", r.ceil(time.milliseconds()) + " ms");
            telemetry.addLine("Press X to scan cone");

            telemetry.update();


        }
         if (isStopRequested())
             throw new InterruptedException();
         int cone = 1;
         if (gamepad1.a)
             cone = c.scanAprilTag(3000);

         telemetry.clear();
         telemetry.update();

         waitForStart();
         r.distance_move(4, Hard_Auto.direction.FORWARD, 0.5);
         Hard_Auto.direction testi;
         int x, y, tx, ty;
         switch (poss.getValue(stats.getValue("pos"))) {
             case "Blue Left":
                 x = 1;
                 y = 5;
                 tx = 2;
                 ty = 7 - cone;
                 testi = Hard_Auto.direction.LEFT;
                 break;
             case "Red Left":
                 x = 6;
                 y = 2;
                 tx = 5;
                 ty =  (cone);
                 testi = Hard_Auto.direction.LEFT;
                 break;
             case "Red Right":
                 x = 6;
                 y = 5;
                 tx = 5;
                 ty = (cone) + 3;
                 testi = Hard_Auto.direction.RIGHT;
                 break;
             default:
                 x = 1;
                 y = 2;
                 tx = 2;
                 ty = 4 - (cone);
                 testi = Hard_Auto.direction.RIGHT;
                 break;
         }

         a = new autoNavigate(this, r, x, y);
         for (int i = 0; i < stats.getValue("total poles");) {
             r.distance_move(12, testi, 0.5);
             r.distance_move(3, Hard_Auto.direction.FORWARD, 0.5);
             r.spin.setPosition(0);
             r.waiter(1000);
             //r.spin.setPosition(1);
             r.distance_move(3, Hard_Auto.direction.BACKWARD, 0.5);
             r.distance_move(-12, testi, 0.5);
         }
         a.MoveToPosition(tx, ty, 0.5, true);
    }

}
