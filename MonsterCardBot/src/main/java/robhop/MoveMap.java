package robhop;

import java.awt.Dimension;
import java.awt.Robot;
import java.awt.image.BufferedImage;

public class MoveMap extends ParamShared
{

    private final Dimension QUEST = new Dimension(272, 626);

    private final Dimension EXIT_ARROW = new Dimension(436, 576);
    private final int EXIT_RGB = -36241;

    private final Dimension SUNKEN = new Dimension(372, 406);
    private final Dimension PORT = new Dimension(402, 301);
    
    private final Dimension SUNKEN_A1 = new Dimension(272, 96);

    private final Dimension CONTINUE = new Dimension(272, 576);

    public MoveMap(Robot iRobHop, Dimension origine)
    {
        super(iRobHop, origine);
        activeLog(true);
    }

    /**
     * 
     * @param iRobHop
     */
    public void goToArea()
    {
        logIt("Click on Quest");
        mMove(QUEST);
        mClick();

        // loading time 
        rWait(2);
        BufferedImage quest = getAScreen();
        
        if (testColorExact(quest, EXIT_ARROW, EXIT_RGB))
        {
            logIt("Already in a area, moving to Exit");
            mMove(EXIT_ARROW);
            mClick();
        }

        //logIt("On the Map, going to Sunken Ship");
        //mMove(SUNKEN);
        logIt("On the Map, going to Haunted Port");
        mMove(PORT);
        molette();
        rWait(1);
        mClick();
    }

    /**
     * 
     * @param iRobHop
     * @param origine
     */
    public void goFight()
    {
        mMove(SUNKEN_A1);
        mClick();

        mMove(CONTINUE);
        mClick();
    }

    /**
     * 
     */
    private void molette()
    {
        for (int i = 0; i < 10; i++)
        {
            getRH().mouseWheel(1);
            getRH().delay(500);
        }
    }

}
