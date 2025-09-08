package Teleop;

@com.qualcomm.robotcore.eventloop.opmode.TeleOp
public class TeleOp extends TeleLib
{
    @Override
    public void loop()
    {
        ArcadeDrive();
    }

    @Override
    public void kill()
    {
        stop();
    }
}