package Teleop;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
public abstract class TeleLib extends OpMode
{
    private DcMotor leftFront;
    private DcMotor leftBack;
    private DcMotor rightFront;
    private DcMotor rightBack;

    public void init()
    {
        leftFront = hardwareMap.get(DcMotor.class, "leftFront");
        leftBack = hardwareMap.get(DcMotor.class, "leftBack");
        rightFront = hardwareMap.get(DcMotor.class, "rightFront");
        rightBack = hardwareMap.get(DcMotor.class, "rightBack");

        leftFront.setDirection(DcMotorSimple.Direction.FORWARD);
        leftBack.setDirection(DcMotorSimple.Direction.REVERSE);
        rightFront.setDirection(DcMotorSimple.Direction.REVERSE);
        rightBack.setDirection(DcMotorSimple.Direction.FORWARD);

        leftFront.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        leftBack.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        rightFront.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        rightBack.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
    }

    public void ArcadeDrive()
    {
        double right_stick_x = gamepad1.right_stick_x;
        double left_stick_x = gamepad1.left_stick_x;
        double left_stick_y = gamepad1.left_stick_y;

        if (Math.abs(left_stick_x) > 0.1 || Math.abs(right_stick_x) > 0.1 || Math.abs(left_stick_y) > 0.1)
        {
            leftFront.setPower((left_stick_y - left_stick_x) - right_stick_x);
            leftBack.setPower((left_stick_y + left_stick_x) - right_stick_x);
            rightFront.setPower((left_stick_y + left_stick_x) + right_stick_x);
            rightBack.setPower((left_stick_y - left_stick_x) + right_stick_x);
        }
        else
        {
            leftFront.setPower(0);
            leftBack.setPower(0);
            rightFront.setPower(0);
            rightBack.setPower(0);
        }

        telemetry.addData("leftFront", leftFront.getPower());
        telemetry.addData("leftBack", leftBack.getPower());
        telemetry.addData("rightFront", rightFront.getPower());
        telemetry.addData("rightBack", rightBack.getPower());
    }

    public void kill()
    {
        leftFront.setPower(0);
        leftBack.setPower(0);
        rightFront.setPower(0);
        rightBack.setPower(0);
    }
}