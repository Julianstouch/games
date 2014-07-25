package robhop;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Robot;
import java.awt.image.BufferedImage;

public class SecurityBug extends ParamShared
{
    
    private final Dimension SURRENDER = new Dimension(-180,621);
    private final Color SURRENDER_RGB = new Color(255,4,4);
    private final Dimension CONFIRM = new Dimension(242,366);
    
    private final Dimension LVLUP = new Dimension(733,200);
    private final Color LVLUP_RGB = new Color(246,246,246);
    
    private final Dimension WHITE = new Dimension(142,171);
    private final Color WHITE_RGB = new Color(255,255,255);
    private final Dimension GRAY = new Dimension(262,286);
    private final Color GRAY_RGB = new Color(153,153,153);
    
    public SecurityBug(Robot iRobHop, Dimension origine)
    {
        super(iRobHop, origine);
        activeLog(true);
    }

    /**
     * 
     * @param debutFight
     * @return
     */
    public boolean betweenFight(Long debutFight)
    {
        boolean soon = false;
        // anti windows lock session / screen saver system : move the mouse
        getRH().mouseMove(500 + aleatoire(0, 50), 600 + aleatoire(0, 50));
        
        //if(debutFight == Long.valueOf(0))
        //    return soon;
        
        long waitTime = ((840000 - (System.currentTimeMillis() - debutFight))/60)/1000;
        
        // at last 2 min : if the game is stuck, click on surrender
        if(waitTime <= 2 )
        {
            soon = true;
            surrender();
            levelup();
        }
        
        return soon;
    }

    /**
     * 
     * @return
     */
    public boolean surrender()
    {
        BufferedImage screen = getAScreen();
        if(testColorExact(screen, SURRENDER, SURRENDER_RGB.getRGB()))
        {
            logIt("Surrendering");
            mMove(SURRENDER);
            mClick();
            rWait(2);
            
            mMove(CONFIRM);
            mClick();
            rWait(10);
            return true;
        }
        return false;
    }

    /**
     * 
     */
    private void levelup()
    {
        BufferedImage screen = getAScreen();
        if(testColorExact(screen, LVLUP, LVLUP_RGB.getRGB()))
        {
            logIt("One monster leveled");
            mMove(LVLUP);
            mClick();
            rWait(2);
        }
    }
    
    /**
     * 
     * @return
     */
    public boolean checkRoulette()
    {
        
       return true; 
    }
}