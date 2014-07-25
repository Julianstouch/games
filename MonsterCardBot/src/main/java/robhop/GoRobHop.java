package robhop;

import java.awt.AWTException;
import java.awt.Robot;

import javax.swing.JOptionPane;

public class GoRobHop
{

    public static void main(String[] args)
    {
        try
        {
            Robot iRobHop = new Robot();
            TimeManager job = new TimeManager(iRobHop);
            
            String cash;
            String light;
            if (job.debug)
            {
                cash = "1000";
                light = "1";
            }
            else
            {
                cash = JOptionPane.showInputDialog(null, "Current Cash : ", "", 1);
                light = JOptionPane.showInputDialog(null, "Current Light : ", "", 1);
            }
            
            job.startIt(cash, light);
            
        }
        catch (AWTException e)
        {
            e.printStackTrace();
        }
    }
}
