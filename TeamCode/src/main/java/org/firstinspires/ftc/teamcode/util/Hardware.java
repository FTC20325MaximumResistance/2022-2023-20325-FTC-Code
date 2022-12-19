package org.firstinspires.ftc.teamcode.util;

import com.qualcomm.hardware.modernrobotics.ModernRoboticsI2cRangeSensor;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.DigitalChannel;
import com.qualcomm.robotcore.hardware.DistanceSensor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.TouchSensor;
import com.qualcomm.robotcore.util.ElapsedTime;

@SuppressWarnings("unused")
public class Hardware {
    OpMode opMode;
    public DcMotor frontLeft, frontRight, backLeft, backRight;
    public DcMotor[] drive;
    public TouchSensor touch1, touch2;
    public Servo claw, spin;
    public DcMotor arm1, arm2, arm3, test;
    public DistanceSensor distanceSensor;
    public ModernRoboticsI2cRangeSensor tom, jerry, ptoughneigh;
    public DigitalChannel backleft, backright;

    public void init_robot(OpMode opMode) {
        this.opMode = opMode;
        initHardware();
    }

    public void initHardware() {
        test = opMode.hardwareMap.dcMotor.get("test");
        try {
            backleft = opMode.hardwareMap.get(DigitalChannel.class, "BackLeft");
            backright = opMode.hardwareMap.get(DigitalChannel.class, "BackRight");
            backleft.setMode(DigitalChannel.Mode.OUTPUT);
            backright.setMode(DigitalChannel.Mode.OUTPUT);
            backleft.setState(true);
            backright.setState(true);
        }catch (Exception e){
            opMode.telemetry.addLine("Useless LEDs not found");
        }
        try {
            tom = opMode.hardwareMap.get(ModernRoboticsI2cRangeSensor.class, "tom");
            jerry = opMode.hardwareMap.get(ModernRoboticsI2cRangeSensor.class, "jerry");
            ptoughneigh = opMode.hardwareMap.get(ModernRoboticsI2cRangeSensor.class, "ptoughneigh");
        }catch (Exception e){
            opMode.telemetry.addLine("Distance Sensor is not found");
        }
        try {
            frontLeft = opMode.hardwareMap.dcMotor.get("FLM");
            frontRight = opMode.hardwareMap.dcMotor.get("FRM");
            backLeft = opMode.hardwareMap.dcMotor.get("BLM");
            backRight = opMode.hardwareMap.dcMotor.get("BRM");
            frontLeft.setDirection(DcMotorSimple.Direction.REVERSE);
            backRight.setDirection(DcMotorSimple.Direction.REVERSE);
            drive = new DcMotor[]{frontLeft, frontRight, backLeft, backRight};
            for (DcMotor motor : drive) {
                motor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
            }
        } catch (Exception e) {
            opMode.telemetry.addLine("Something went wrong when configuring the drive motors.");
        }
        try {
            touch1 = opMode.hardwareMap.touchSensor.get("touch1");
        } catch (Exception e) {
            opMode.telemetry.addLine("Touch Sensor1 were not found in config.");
        }


        try {

            touch2 = opMode.hardwareMap.touchSensor.get("touch2");
        } catch (Exception e) {
            opMode.telemetry.addLine("Touch Sensor2 were not found in config.");
        }


        try {
            claw = opMode.hardwareMap.servo.get("claw");
            spin = opMode.hardwareMap.servo.get("spin");
        } catch (Exception e) {
            opMode.telemetry.addLine("Servo(s) were not found in config.");
        }

        try {
            arm1 = opMode.hardwareMap.dcMotor.get("arm1");
            arm1.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
            //arm1.setDirection(DcMotorSimple.Direction.REVERSE);
        } catch (Exception e) {
            opMode.telemetry.addLine("Crane Arm Motor1 was not found in config");
        }
        try {

            arm2 = opMode.hardwareMap.dcMotor.get("arm2");
            arm2.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
            arm2.setDirection(DcMotorSimple.Direction.REVERSE);

        } catch (Exception e) {
            opMode.telemetry.addLine("Crane Arm Motor2 was not found in config");
        }
        try {

            arm3 = opMode.hardwareMap.dcMotor.get("arm3");
            arm3.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
            //arm3.setDirection(DcMotorSimple.Direction.REVERSE);

        } catch (Exception e) {
            opMode.telemetry.addLine("Crane Arm Motor3 was not found in config");
        }

        opMode.telemetry.update();

        //arm1.setDirection(DcMotorSimple.Direction.REVERSE);
    }

    public void waiter(int time) {
        ElapsedTime Time = new ElapsedTime();
        Time.reset();
        while (true) {
            if (Time.milliseconds() > time) {
                break;
            }
        }
    }

    public void initAuto() {
        //waiter(500);
    }

    public void setDriverMotorMode(DcMotor.RunMode mode) {
        for (DcMotor dcMotor : drive) dcMotor.setMode(mode);
    }

    public boolean getTolerance(int other, int checked, int tolerance) {
        return checked - tolerance < other && checked + tolerance > other;

    }

    public boolean contains(String[] arr, String value) {
        for (String element : arr) {
            if (element.equals(value)) {
                return true;
            }
        }
        return false;
    }

    public void setToStill(){
        for (DcMotor motor: drive){
            motor.setPower(0);
        }
    }
    public int ceil(double x){
        return ((int) x )+1;

    }


}
