package robhop;

import java.awt.Dimension;
import java.awt.Robot;
import java.awt.image.BufferedImage;

public class ManageFightDebug extends ManageFight
{
   
    public ManageFightDebug(Robot iRobHop, Dimension origine)
    {
        super(iRobHop, origine);
        activeLog(true);
    }

    public void lancerCombat()
    {
        logIt("Starting Fight Analyser in 10 sec, waiting for cards to go in place");
        rWait(10);

        while (1 == 1)
        {
            logIt(" - - - - - - - - - - - - - - - - ");
            BufferedImage screen = getAScreen();
            if (testCardPresence(ITEM1, screen, true) || testCardPresence(ITEM1, screen, false))
                logIt("Item 1 : cost is " + evalCost(ITEM1, screen));
            // testColor(screen, ITEM1, tBlack.getRGB());

            if (testCardPresence(ITEM2, screen, true) || testCardPresence(ITEM2, screen, false))
                logIt("Item 2 : cost is " + evalCost(ITEM2, screen));
            // testColor(screen, ITEM2, tBlack.getRGB());

            if (testCardPresence(MON3, screen, true) || testCardPresence(MON3, screen, false))
                logIt("Mon 3 : cost is " + evalCost(MON3, screen));
            // testColor(screen, MON3, tBlack.getRGB());

            if (testCardPresence(MON4, screen, true) || testCardPresence(MON4, screen, false))
                logIt("Mon 4 : cost is " + evalCost(MON4, screen));
            // testColor(screen, MON4, tBlack.getRGB());

            rWait(10);
        }
        
    }
}
