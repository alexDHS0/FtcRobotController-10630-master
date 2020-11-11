package org.firstinspires.ftc.teamcode;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;

@Autonomous(name="Auto1",group="Basic")
@Disabled
public class Auto1 extends LinearOpMode {
//this is a test.
    DcMotorEx leftfront;
    DcMotorEx leftback;
    DcMotorEx rightfront;
    DcMotorEx rightback;

    @Override
    public void runopmode(){

        Initialize();

        waitForStart();



        int ticks =3*20;
        forward(100,0.5);
        backward(100,0.5);
        left(100,0.5);
        right(100,0.5);

    }
    private void Initialize(){
        leftfront = hardwareMap.get(DcMotorEx.class,"LF");
        leftback = hardwareMap.get(DcMotorEx.class,"LB");
        rightfront = hardwareMap.get(DcMotorEx.class,"RF");
        rightback = hardwareMap.get(DcMotorEx.class,"RB");
        //the wheles dont have clean cut oreintations, we need to change some
        //leftfront.setDirection(DcMotorSimple.Direction.REVERSE);
        //we assume weve done this.
        reset();

    }
    private void reset(){
        //this resets the encoder value to be 0
        leftfront.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        leftback.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rightfront.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rightback.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        // put them back into running mode
        leftfront.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        leftback.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        rightback.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        rightfront.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    }
    public void right(int ticks, double power){
        double encodervalue = averagencodervalue();
        while (encodervalue < ticks) {
            leftback.setPower(-power);
            rightfront.setPower(-power);
            leftfront.setPower(power);
            rightback.setPower(power);
            encodervalue= averagencodervalue();
        }
        reset();
    }
    public void left(int ticks, double power){
        double encodervalue = averagencodervalue();
        while (encodervalue < ticks) {
            leftback.setPower(power);
            rightfront.setPower(power);
            leftfront.setPower(-power);
            rightback.setPower(-power);
            encodervalue= averagencodervalue();
        }
        reset();
    }
    private double averagencodervalue(){
        return (Math.abs(leftfront.getCurrentPosition()) + Math.abs(leftback.getCurrentPosition()) + Math.abs(rightfront.getCurrentPosition()) + Math.abs(rightback.getCurrentPosition()) / 4.0);
    }

    private void forward(int ticks,double power){
        //find some way to make them move the number of ticks.

        //setvelocity(only works with Ex) mnually input the ticks per second velocity
        //setpower from -1 to 1 , converts to velocity automatically.

        int encodervalue = leftfront.getCurrentPosition();
        while(encodervalue<ticks){
            //move forward
            leftfront.setPower(power);
            leftback.setPower(power);
            rightback.setPower(power);
            rightfront.setPower(power);

            encodervalue = leftfront.getCurrentPosition();
        }
        //stop the motrs from moving.
        leftfront.setPower(0);
        leftback.setPower(0);
        rightback.setPower(0);
        rightfront.setPower(0);

        reset();
    }
    private void backward(int ticks,double power){
        //find some way to make them move the number of ticks.

        //setvelocity(only works with Ex) mnually input the ticks per second velocity
        //setpower from -1 to 1 , converts to velocity automatically.

        int encodervalue = leftfront.getCurrentPosition();
        while(encodervalue > -ticks){
            //move forward
            leftfront.setPower(-power);
            leftback.setPower(-power);
            rightback.setPower(-power);
            rightfront.setPower(-power);

            encodervalue = leftfront.getCurrentPosition();
        }
        //stop the motrs from moving.
        leftfront.setPower(0);
        leftback.setPower(0);
        rightback.setPower(0);
        rightfront.setPower(0);

        reset();
    }

}
