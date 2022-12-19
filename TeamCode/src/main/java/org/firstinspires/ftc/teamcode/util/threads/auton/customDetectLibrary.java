package org.firstinspires.ftc.teamcode.util.threads.auton;

import android.annotation.SuppressLint;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.teamcode.util.Hard_Auto;
import org.openftc.apriltag.AprilTagDetection;
import org.openftc.easyopencv.OpenCvCamera;
import org.openftc.easyopencv.OpenCvCameraFactory;
import org.openftc.easyopencv.OpenCvCameraRotation;

import java.util.ArrayList;

public class customDetectLibrary {

    Hard_Auto r;

    OpenCvCamera camera;
    AprilTagDetectionPipeline aprilTagDetectionPipeline;

    static final double FEET_PER_METER = 3.28084;
    OpMode opMode;
    // Lens intrinsics
    // UNITS ARE PIXELS
    // NOTE: this calibration is for the C920 webcam at 800x448.
    // You will need to do your own calibration for other configurations!
    double fx = 578.272;
    double fy = 578.272;
    double cx = 402.145;
    double cy = 221.506;

    // UNITS ARE METERS
    double tagsize = 0.166;

    // Tag ID 1,2,3 from the 36h11 family
    int LEFT = 1;
    int MIDDLE = 2;
    int RIGHT = 3;
    AprilTagDetection tagOfInterest = null;
    public customDetectLibrary(OpMode opMode, Hard_Auto r){
        this.opMode = opMode;
        this.r = r;
    }
    public int scanAprilTag(int time){
        int cameraMonitorViewId = opMode.hardwareMap.appContext.getResources().getIdentifier("cameraMonitorViewId", "id", opMode.hardwareMap.appContext.getPackageName());
        camera = OpenCvCameraFactory.getInstance().createWebcam(opMode.hardwareMap.get(WebcamName.class, "Webcam 1"), cameraMonitorViewId);
        aprilTagDetectionPipeline = new AprilTagDetectionPipeline(tagsize, fx, fy, cx, cy);
        camera.setPipeline(aprilTagDetectionPipeline);
        camera.openCameraDeviceAsync(new OpenCvCamera.AsyncCameraOpenListener()
        {
            @Override
            public void onOpened()
            {
                camera.startStreaming(800,448, OpenCvCameraRotation.UPRIGHT);
            }

            @Override
            public void onError(int errorCode)
            {

            }
        });
        ElapsedTime Time = new ElapsedTime();
        opMode.telemetry.setMsTransmissionInterval(50);
        Time.reset();
        while (Time.milliseconds() <= time)
        {
            ArrayList<AprilTagDetection> currentDetections = aprilTagDetectionPipeline.getLatestDetections();

            if(currentDetections.size() != 0)
            {
                boolean tagFound = false;

                for(AprilTagDetection tag : currentDetections)
                {
                    if(tag.id == LEFT || tag.id == MIDDLE || tag.id == RIGHT)
                    {
                        tagOfInterest = tag;
                        tagFound = true;
                        opMode.telemetry.addData("Direction", tagOfInterest.id);
                        opMode.telemetry.update();
                        r.waiter(5000);
                        return tagOfInterest.id;

                    }
                }

                if(tagFound)
                {
                    opMode.telemetry.addLine("Tag of interest is in sight!\n\nLocation data:");
                    tagToTelemetry(tagOfInterest);
                    break;
                }
                else
                {
                    opMode.telemetry.addLine("Don't see tag of interest :(");

                    if(tagOfInterest == null)
                    {
                        opMode.telemetry.addLine("(The tag has never been seen)");
                    }
                    else
                    {
                        opMode.telemetry.addLine("\nBut we HAVE seen the tag before; last seen at:");
                        tagToTelemetry(tagOfInterest);
                    }
                }

            }
            else
            {
                opMode.telemetry.addLine("Don't see tag of interest :(");

                if(tagOfInterest == null)
                {
                    opMode.telemetry.addLine("(The tag has never been seen)");
                }
                else
                {
                    opMode.telemetry.addLine("\nBut we HAVE seen the tag before; last seen at:");
                    tagToTelemetry(tagOfInterest);
                }

            }

            opMode.telemetry.update();
            r.waiter(20);
        }
        opMode.telemetry.addLine("Ready to start, tage is " + tagOfInterest.id);
        opMode.telemetry.update();
        return 1;


    }
    @SuppressLint("DefaultLocale")
    private void tagToTelemetry(AprilTagDetection detection)
    {
        opMode.telemetry.addLine(String.format("\nDetected tag ID=%d", detection.id));
        opMode.telemetry.addLine(String.format("Translation X: %.2f feet", detection.pose.x*FEET_PER_METER));
        opMode.telemetry.addLine(String.format("Translation Y: %.2f feet", detection.pose.y*FEET_PER_METER));
        opMode.telemetry.addLine(String.format("Translation Z: %.2f feet", detection.pose.z*FEET_PER_METER));
        opMode.telemetry.addLine(String.format("Rotation Yaw: %.2f degrees", Math.toDegrees(detection.pose.yaw)));
        opMode.telemetry.addLine(String.format("Rotation Pitch: %.2f degrees", Math.toDegrees(detection.pose.pitch)));
        opMode.telemetry.addLine(String.format("Rotation Roll: %.2f degrees", Math.toDegrees(detection.pose.roll)));
    }

}
