package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
@Disabled
public class TeleOp extends LinearOpMode {

    DcMotor frontRight;
    DcMotor frontLeft;
    DcMotor backRight;
    DcMotor backLeft;
    DcMotor armmove;
    Servo woblearm;
    boolean previousRb = false;
    boolean previousLb = false;
    boolean previousA = false;
    boolean previousB = false;
    boolean previousCross = false;
    boolean armManual = true;
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
            grabset();

            setmode();
            //this is mostly so we have a working prototype which lets us use the
            if (armManual == true) {
            armcontroll();
            }
            else {
            testarmadd();
            testarmsub();
            grabmove();
            }
            //armmove.setTargetPosition(1);
            //grabmove();

        }


    }

    private void driveset() {
        double r = Math.hypot(gamepad1.left_stick_x, -gamepad1.left_stick_y); //we might have to take out the negatives.
        double robotAngle = Math.atan2(-gamepad1.left_stick_y, gamepad1.left_stick_x) - Math.PI / 4;
        double rightX = gamepad1.right_stick_x;

        final double v1 = r * Math.cos(robotAngle) + rightX;//we may not need the final.
        final double v2 = r * Math.sin(robotAngle) - rightX;
        final double v3 = r * Math.sin(robotAngle) + rightX;
        final double v4 = r * Math.cos(robotAngle) - rightX;

        frontLeft.setPower(v1*1.41);//multiply them all by 1.414
        frontRight.setPower(v2*1.41);
        backLeft.setPower(v3*1.41);
        backRight.setPower(v4*1.41);
    }

    private void grabset(){
        boolean rb = gamepad1.right_bumper;


        if(rb && !previousRb){
            if(woblearm.getPosition() == 1.0){
                woblearm.setPosition(0.0);
            }
            else{
                woblearm.setPosition(1.0);
            }
        }
        previousRb = rb;
    }

    private void  grabmove(){
        boolean lb = gamepad1.left_bumper;

        if(lb && !previousLb){
            if(armmove.getTargetPosition() == 90){//90 is probably not the bast value.
                armmove.setTargetPosition(0);//0 is probably not the right value, in order to use 0 we would have to hold it in the same position we init at.
            }
            else{
                armmove.setTargetPosition(90);//90 is probably not the best value.
            }
        }
        previousLb = lb;
    }
    //swich between manual forward and back and less well tested systems using set target position.
    private void setmode (){
        if(gamepad1.cross && !previousCross){
            armManual=!armManual;
            if(armManual){
                armmove.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            }
            else {
                armmove.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            }
        }
    }
    //manual contoll of the arm.
    private void armcontroll(){
        if(gamepad1.a) {
            armmove.setPower(6.0);
        }
        else if(gamepad1.b){
            armmove.setPower(-6.0);
        }
        else {
            armmove.setPower(0.0);

        }
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
    public void initialize() {

        //initialize servos
        woblearm = hardwareMap.get(Servo.class, "wa");
        woblearm.setPosition(1);//all we need to ever use.


        //initialize drive train motors,
        frontRight = hardwareMap.get(DcMotor.class, "fr");
        frontLeft = hardwareMap.get(DcMotor.class, "fl");
        backRight = hardwareMap.get(DcMotor.class, "br");
        backLeft = hardwareMap.get(DcMotor.class, "bl");

        frontRight.setPower(0);
        frontLeft.setPower(0);
        backLeft.setPower(0);
        backRight.setPower(0);

        //initialize the weird wobble arm motor.
        armmove = hardwareMap.get(DcMotor.class, "wm");
        armmove.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        //we set it so that the position the motor is in does not go any farther back when we reset it, and so that the motor hopefully does less work holding up the goal.
        armmove.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        //we make sure that the position is set to an acurate position we know.
        armmove.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        //let the motor use run to position.
        //armmove.setTargetPosition(1);//ONE WILL PROBABLY BRAKE SOMETHING, FIND THE WORKING VALUES AND CHANGE THIS!
        //armmove.setTargetPosition(10);
    }
    //a manual test program that lets you manualy use set trget mode. split into a forward and backwards based on two buttons.
    private void testarmadd(){
        boolean Apress = gamepad1.a;
        if(Apress && ! previousA){
            int next = armmove.getTargetPosition() + 10;
            armmove.setTargetPosition(next);
        }
        previousA = Apress;
    }
    private void testarmsub(){
        boolean Bpress = gamepad1.b;
        if(Bpress && ! previousB){
            int next = armmove.getTargetPosition() + 10;
            armmove.setTargetPosition(next);
        }
        previousB = Bpress;
    }

}