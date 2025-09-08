package ThreadTeleOp;

import com.qualcomm.robotcore.util.ElapsedTime;

public class ThreadTeleOp extends ThreadTeleLib
{
    private ElapsedTime loopTimer;
    double loopTime = 0.0;

    @Override
    public void init()
    {
        super.init();
        loopTimer = new ElapsedTime();
    }

    @Override
    public void loop()
    {
        double loop = System.nanoTime();
        ArcadeDrive();
        claw();

        loopTime = loop;
    }

    @Override
    public void stop()
    {
        super.stop();
    }
}