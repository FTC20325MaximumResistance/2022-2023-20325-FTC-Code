package org.firstinspires.ftc.teamcode.util;

import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.hardwareMap;
import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.telemetry;


import java.util.List;
import org.firstinspires.ftc.robotcore.external.ClassFactory;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer.CameraDirection;
import org.firstinspires.ftc.robotcore.external.tfod.TFObjectDetector;
import org.firstinspires.ftc.robotcore.external.tfod.Recognition;

import androidx.annotation.VisibleForTesting;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.ClassFactory;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
import org.firstinspires.ftc.robotcore.external.tfod.Recognition;
import org.firstinspires.ftc.robotcore.external.tfod.TFObjectDetector;

import java.util.List;

public class Hard_Auto extends Hardware{

    double dperr = 1;
    @Override
    public void initAuto(){
        super.initAuto();

    }

    @Override
    public void init_robot(OpMode opMode) {
        super.init_robot(opMode);
    }

    public void degree_rotate(double degrees, direction dir, double power){
        setDriverMotorMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        waiter(250);
        setDriverMotorMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        degrees = degrees * dperr;
        switch (dir){
            case LEFT:
                frontLeft.setTargetPosition((int)-degrees);
                frontRight.setTargetPosition((int)degrees);
                backLeft.setTargetPosition((int)-degrees);
                backRight.setTargetPosition((int)degrees);
                break;
            case RIGHT:
                frontLeft.setTargetPosition((int)degrees);
                frontRight.setTargetPosition((int)-degrees);
                backLeft.setTargetPosition((int)degrees);
                backRight.setTargetPosition((int)-degrees);
                break;
        }
        if (frontLeft.getTargetPosition() < 0){
            while(frontLeft.getCurrentPosition() > frontLeft.getTargetPosition()) {
                // Run your motors
                frontLeft.setPower(power*(frontLeft.getTargetPosition()/Math.abs(frontLeft.getTargetPosition())));
                frontRight.setPower(power*(frontRight.getTargetPosition()/Math.abs(frontRight.getTargetPosition())));
                backLeft.setPower(power*(backLeft.getTargetPosition()/Math.abs(backLeft.getTargetPosition())));
                backRight.setPower(power*(backRight.getTargetPosition()/Math.abs(backRight.getTargetPosition())));
            }
        }else if (frontLeft.getTargetPosition() > 0){
            while(frontLeft.getCurrentPosition() < frontLeft.getTargetPosition()){
                frontLeft.setPower(power*(frontLeft.getTargetPosition()/Math.abs(frontLeft.getTargetPosition())));
                frontRight.setPower(power*(frontRight.getTargetPosition()/Math.abs(frontRight.getTargetPosition())));
                backLeft.setPower(power*(backLeft.getTargetPosition()/Math.abs(backLeft.getTargetPosition())));
                backRight.setPower(power*(backRight.getTargetPosition()/Math.abs(backRight.getTargetPosition())));
            }
        }
    }
    public void distance_move(double inches, direction dir, double power){}

    public enum direction{
        FORWARD,
        BACKWARD,
        LEFT,
        RIGHT

    }
    //Computer Vision

        /*
         * Specify the source for the Tensor Flow Model.
         * If the TensorFlowLite object model is included in the Robot Controller App as an "asset",
         * the OpMode must to load it using loadModelFromAsset().  However, if a team generated model
         * has been downloaded to the Robot Controller's SD FLASH memory, it must to be loaded using loadModelFromFile()
         * Here we assume it's an Asset.    Also see method initTfod() below .
         */
        private static final String TFOD_MODEL_ASSET = "PowerPlay.tflite";
        // private static final String TFOD_MODEL_FILE  = "/sdcard/FIRST/tflitemodels/CustomTeamModel.tflite";

        private static final String[] LABELS = {
                "1 Bolt",
                "2 Bulb",
                "3 Panel"
        };
    private static final String VUFORIA_KEY =
            "AbPlqTr/////AAABmdW9GwFZ8E3Kt/btYVgy+hBMLd/G/emPF/6jLYzmo9hMgEldkQ4YzCzrs7Rqz5Pnh7RwdT+VFIPS8czES3UnsLv33hPsr/DhZ73T7r+0MJPx43fx4PP5ncgoqEToOaK04iZr6kDjQLZMSxaGqz2jx4WrQpO7F6su9GQ4H/qr3ZydWXVgv1YYEZny93GubLpD59a33+NPedV+HgLkSOH7/ksG3hRelkD3f1yjTGNjAK8C9wd30njnjN7QmLmCUghe/5Wj8coc3eCHB5InNR3VJk7NNd2r+MnxYfB8N1vznX0OHYA7NydPvlwnrZ8Zi7nXC38oiEWRABDN4sFEwzc58TloW4uTA9SLA5EJSxjZC1kJ";

    private VuforiaLocalizer vuforia;
    private TFObjectDetector tfod;
    public void ready_vision(){
        try {
            initVuforia();
            initTfod();

            int oh_no = 0;
            if (tfod != null) {
                tfod.activate();
                tfod.setZoom(1.0, 16.0 / 9.0);

            }

        }
        catch(Exception e){
            opMode.telemetry.addLine("Something went wrong, vision software will aim for top level");
            opMode.telemetry.addLine("Why? Because Screw You!");
            opMode.telemetry.update();
        }
    }


    private void initVuforia() {
        try {

            VuforiaLocalizer.Parameters parameters = new VuforiaLocalizer.Parameters();

            parameters.vuforiaLicenseKey = VUFORIA_KEY;
            parameters.cameraDirection = VuforiaLocalizer.CameraDirection.BACK;

            //  Instantiate the Vuforia engine
            vuforia = ClassFactory.getInstance().createVuforia(parameters);

            // Loading trackables is not necessary for the TensorFlow Object Detection engine.
        }
        catch(Exception e){
            opMode.telemetry.addLine("Something went wrong, vision software will aim for top level");
            opMode.telemetry.addLine("Why? Because Screw You!");
            opMode.telemetry.update();
        }
    }

    /**
     * Initialize the TensorFlow Object Detection engine.
     */
    private void initTfod() {
        try {
            int tfodMonitorViewId = opMode.hardwareMap.appContext.getResources().getIdentifier(
                    "tfodMonitorViewId", "id", opMode.hardwareMap.appContext.getPackageName());
            TFObjectDetector.Parameters tfodParameters = new TFObjectDetector.Parameters(tfodMonitorViewId);
            tfodParameters.minResultConfidence = 0.4f;
            tfodParameters.isModelTensorFlow2 = true;
            tfodParameters.inputSize = 320;
            tfod = ClassFactory.getInstance().createTFObjectDetector(tfodParameters, vuforia);
            tfod.loadModelFromAsset(TFOD_MODEL_ASSET, LABELS);
        }
        catch(Exception e){

        }
    }
        public int scan_cone () {
            ElapsedTime time = new ElapsedTime();
            time.reset();
        while (true) {
            if (time.seconds() > 5){return 0;}

            if (tfod != null) {
                // getUpdatedRecognitions() will return null if no new information is available since
                // the last time that call was made.
                List<Recognition> updatedRecognitions = tfod.getUpdatedRecognitions();
                if (time.seconds() > 5){return 0;}
                if (updatedRecognitions != null) {


                    // step through the list of recognitions and display image position/size information for each one
                    // Note: "Image number" refers to the randomized image orientation/number
                    for (Recognition recognition : updatedRecognitions) {
                        switch (recognition.getLabel()) {
                            case "1 Bolt":
                                opMode.telemetry.addLine("Ready To Start");
                                opMode.telemetry.update();
                                return 1;
                            case "2 Bulb":
                                opMode.telemetry.addLine("Ready To Start");
                                opMode.telemetry.update();
                                return 2;
                            case "3 Panel":
                                opMode.telemetry.addLine("Ready To Start");
                                opMode.telemetry.update();
                                return 3;

                        }


                    }

                }
            }
        }

    }
    public void end_vision(){
        try {
            tfod.shutdown();
            vuforia.close();
        }
        catch(Exception e){
            opMode.telemetry.addLine("Something went wrong, vision software will aim for top level");
            opMode.telemetry.addLine("Why? Because Screw You!");
            opMode.telemetry.update();
        }
    }


}
