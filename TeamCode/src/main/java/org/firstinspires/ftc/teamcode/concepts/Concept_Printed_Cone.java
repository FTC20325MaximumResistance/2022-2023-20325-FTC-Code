package org.firstinspires.ftc.teamcode.concepts;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.util.Hard_Auto;
import org.firstinspires.ftc.teamcode.util.Hardware;

import java.util.jar.Attributes;

@Autonomous(name = "Conecpt_Printed_Cone", group = "Auto")
public class Concept_Printed_Cone extends LinearOpMode {
    Hard_Auto r = new Hard_Auto();


    @Override
    public void runOpMode() throws InterruptedException {
        r.init_robot(this);
        r.initAuto();
        r.ready_vision();
        int cone = r.scan_cone();
        waitForStart();
        switch(cone){
            case 0:
                telemetry.addLine("Error");
                break;
            case 1:
                telemetry.addLine("Bolt");
                break;
            case 2:
                telemetry.addLine("Bulbs");
                break;
            case 3:
                telemetry.addLine("Panels");
                break;
        }
        telemetry.update();
        //r.waiter(5000);
    }
}
