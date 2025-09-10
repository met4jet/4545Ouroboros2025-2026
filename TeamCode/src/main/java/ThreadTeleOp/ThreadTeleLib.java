package ThreadTeleOp;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;
import org.firstinspires.ftc.teamcode.ThreadHandler;

public abstract class ThreadTeleLib extends OpMode
{
    public Servo clawRight;
    public Servo clawLeft;
    private DcMotor leftFront;
    private DcMotor leftBack;
    private DcMotor rightFront;
    private DcMotor rightBack;
    public ThreadHandler th_Claw;

    public void init()
    {
        clawRight = hardwareMap.get(Servo.class, "clawRight");
        clawLeft = hardwareMap.get(Servo.class, "clawLeft");

        clawRight.setDirection(Servo.Direction.REVERSE);
        clawLeft.setDirection(Servo.Direction.FORWARD);

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

        th_Claw = new ThreadHandler();
    }
    Thread clawOpen = new Thread(new Runnable()
    {
        @Override
        public void run()
        {
            ElapsedTime time = new ElapsedTime();
            time.reset();
            while(time.milliseconds() < 300)
            {
                clawRight.setPosition(1);
                clawLeft.setPosition(1);
            }
        }
    });

    Thread clawClose = new Thread(new Runnable()
    {
        @Override
        public void run()
        {
            ElapsedTime time = new ElapsedTime();
            time.reset();
            while(time.milliseconds() < 300)
            {
                clawRight.setPosition(0);
                clawLeft.setPosition(0);
            }
        }
    });

    public void ArcadeDrive()
    {
        double right_stick_x = gamepad1.right_stick_x;
        double left_stick_x = gamepad1.left_stick_x;
        double left_stick_y = gamepad1.left_stick_y;

        if (Math.abs(left_stick_x) > 0.1 || Math.abs(right_stick_x) > 0.1 || Math.abs(left_stick_y) > 0.1) {
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

    public void claw()
    {
        if (gamepad1.aWasPressed())
        {
            th_Claw.queue(clawOpen);
        }

        if (gamepad1.bWasPressed())
        {
            th_Claw.queue(clawClose);
        }
    }

    public void stop()
    {
        th_Claw.th_kill();

        leftFront.setPower(0);
        leftBack.setPower(0);
        rightFront.setPower(0);
        rightBack.setPower(0);
    }
}