package org.firstinspires.ftc.teamcode.concepts;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.util.Hard_Auto;
@Disabled
@Autonomous(name = "Concept_Printed_Cone", group = "Auto")
public class Concept_Printed_Cone extends LinearOpMode {
    Hard_Auto r = new Hard_Auto();


    @Override
    public void runOpMode() throws InterruptedException {
        r.init_robot(this);
        r.initAuto();
        r.ready_vision();
        String cone = r.scan_object(new String[]{"wire","engine","power", "computer", "1", "2", "3"}, 12);
        waitForStart();
        /*
        switch(cone){
            case "":
                telemetry.addLine("Error");
                break;
            case "1":
                telemetry.addLine("Gear");
                break;
            case "2":
                telemetry.addLine("Omega");
                break;
            case "3":
                telemetry.addLine("Resistor");
                break;
        }
        */

         telemetry.addData("Object", cone);
        telemetry.update();
        //r.waiter(5000);
    }
}
