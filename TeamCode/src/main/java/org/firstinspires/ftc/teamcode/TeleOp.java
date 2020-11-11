package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

public class TeleOp extends LinearOpMode {

    DcMotor frontRight;
    DcMotor frontLeft;
    DcMotor backRight;
    DcMotor backLeft;
    DcMotor armmove;
    Servo woblearm;
    boolean previousRb = false;
    boolean previousLb = false;

    @Override
    //remember to comment you slop.
    public void runOpMode() throws InterruptedException {

        initialize();
        //initialize evrything,

        waitForStart();
        //wait for the start button.

        //do what we want to do(where stuff goes wrong)
        while (opModeIsActive()) {
          driveset();
          //grabset();
          //armmove.setTargetPosition(1);

        }


    }
    /*
    private void  grabmove(){
        boolean lb = gamepad1.left_bumper;

        if(lb&& !previousLb){
            if(armmove.getTargetPosition() == 1){//1 is nit the right value
                armmove.setTargetPosition(0);//1 is nit the right value
            }
            else{
                armmove.setTargetPosition(90);//1 is nit the right value
            }
        }
        previousLb = lb;
    }
    */
    /*
    private void grabset(){
        boolean rb = gamepad1.right_bumper;


        if(rb&& !previousRb){
            if(woblearm.getPosition() == 1){
                woblearm.setPosition(0.0);
            }
            else{
                woblearm.setPosition(1.0);
            }
        }
        previousRb = rb;
    }
    */
    private void driveset(){
        double r = Math.hypot(gamepad1.left_stick_x, -gamepad1.left_stick_y); //we might have to take out the negatives.
        double robotAngle = Math.atan2(-gamepad1.left_stick_y,gamepad1.left_stick_x) - Math.PI / 4;
        double rightX = gamepad1.right_stick_x;

        final double v1 = r * Math.cos(robotAngle) + rightX;//we may not need the final.
        final double v2 = r * Math.sin(robotAngle) - rightX;
        final double v3 = r * Math.sin(robotAngle) + rightX;
        final double v4 = r * Math.cos(robotAngle) - rightX;

        frontLeft.setPower(v1);//multiply them all by 1.414
        frontRight.setPower(v2);
        backLeft.setPower(v3);
        backRight.setPower(v4);
    }
    /*
    private void moveForeward() {
        //find the value the gamepad joystick reads.
        double power = gamepad1.right_stick_y;
        //power 1 to -1
        frontRight.setPower(power);
        frontLeft.setPower(power);
        backLeft.setPower(power);
        backRight.setPower(power);
    }
    */ //we dont need this anymore
    public void initialize(){
        //do the init stuff

        woblearm =hardwareMap.get(Servo.class,"wa");
        woblearm.setPosition(1);//all we need to ever use.

        //armmove = hardwareMap.get(DcMotor.class,"wm");
        //armmove.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        //armmove.setTargetPosition(1);
        frontRight = hardwareMap.get(DcMotor.class,"fr");
        frontLeft = hardwareMap.get(DcMotor.class,"fl");
        backRight = hardwareMap.get(DcMotor.class,"br");
        backLeft = hardwareMap.get(DcMotor.class,"bl");

        frontRight.setPower(0);
        frontLeft.setPower(0);
        backLeft.setPower(0);
        backRight.setPower(0);

    }
}
