package robhop;

import java.awt.Dimension;
import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.util.Calendar;

public class TimeManager extends ParamShared
{

    private static final int TOKEN_3 = -4261120;
    private static final int TOKEN_2 = -8606174;
    private static final int TOKEN_1 = -13663305;
    
    private Dimension firstOrigine = null;
    private Dimension navTitle = null;

    private Long minElapsed;
    private Long currentLight;
    
    private int lightMin = 230;
    private int questCost = 14;
    private int goldGain = 175;
    private int i;
    
    public TimeManager(Robot iRobHop)
    {
        super(iRobHop, null);
        activeLog(true);
    }

    public void startIt(String cash, String light)
    {
        minElapsed = System.currentTimeMillis();
        currentLight = Long.parseLong(light);
        navTitle = new Dimension(700,10);
        i = 1;
        int fightPlayed = 0;
        while (i == 1)
        {
            logIt("Starting bot in 10 sec ... Starting with $" + cash);
            logIt("Debug mode", debug);
            rWait(10);
            
            boolean isOk = getOrigineCoords();
                
            if (isOk)
            {
                MoveMap moveInit = new MoveMap(getRH(), getOrigine());
                ManageFight fight = new ManageFight(getRH(), getOrigine());
                SecurityBug secure = new SecurityBug(getRH(), getOrigine());

                if (debug)
                {
                    ManageFightDebug fightAnalyse = new ManageFightDebug(getRH(), getOrigine());
                    fightAnalyse.lancerCombat();
                    // fight.lancerCombat();
                }
                else
                {
                    // going to sunker a1
                    moveInit.goToArea();

                    Long debutFight = System.currentTimeMillis();//Long.valueOf(0);

                    while (isOk && i == 1)
                    {
                        checkLight();

                        if (currentLight > questCost)// (System.currentTimeMillis() - debutFight > 720000 || first) // 12min * 60sec * 1000ms
                        {
                            currentLight -= questCost;
                            logIt("Light left : " + currentLight);
                            moveInit.goFight();
                            debutFight = System.currentTimeMillis();
                            fight.lancerCombat();
                            fightPlayed++;
                            // long waitTime = ((720000 - (System.currentTimeMillis() - debutFight)) / 60) / 1000;
                            // logIt("Starting waiting " + waitTime + " min before next fight.");
                            int newCash = Integer.valueOf(cash) + (fightPlayed * goldGain);
                            logIt(Calendar.getInstance().getTime().toString() + " : " + fightPlayed + " fights for "+ newCash+"$");
                        }
                        else
                        {
                            rWait(30);

                            // antilock mouse moving + surrender check
                            boolean soon = secure.betweenFight(debutFight);
                            // 2 min left, re-get token coords
                            if (soon)
                            {
                                isOk = getOrigineCoords();
                            }
                        }
                    }

                    // game expired, crashed, or login from another computer
                    relaunch();
                }
            }
            else
            {
                // game crash during a game. a reaload F5 in a game.
                SecurityBug secure = new SecurityBug(getRH(), firstOrigine);
                boolean haveSurr = secure.surrender();
                if (!haveSurr)
                {
                    relaunch();
                }
                else
                {
                    logIt("Surrendered after game reload");
                }
            }
        }
        logIt("Finished at "+Calendar.getInstance().getTime().toString() + " : " + fightPlayed + " fights");

    }

    /**
     * 
     */
    private boolean getOrigineCoords()
    {

        //logIt("Scanning screen for the Token Icone ...");
        BufferedImage screen = getAScreen();

        // debut 350 - 150
        int height = screen.getHeight();
        int width = screen.getWidth();

        setOrigine(new Dimension(0, 0));
        
        dual: for (int y = 150; y < height; y++)
        {
            for (int x = 350; x < width; x++)
            {
                if (screen.getRGB(x, y) == TOKEN_1)
                {
                    if (screen.getRGB(x + 1, y) == TOKEN_2)
                    {
                        if (screen.getRGB(x + 2, y) == TOKEN_3)
                        {
                            logIt("Found in " + x + " / " + y, debug);
                            setOrigine(new Dimension(x, y));
                            if(firstOrigine == null)
                            {
                                firstOrigine = new Dimension(x, y);
                            }
                            break dual;
                        }
                    }
                }
            }
        }

        if (origineX() == 0)
        {
            if(debug)
            {
                setOrigine(new Dimension(961, 245));
            }
            else
            {
                logIt("Token not found");
            }
        }

        return origineX() != 0;
    }

    /**
     * 
     */
    private void relaunch()
    {
        logIt("Restarting the game in 30 min.");
        // screen is white, so relaunching game in 30min * 60sec *1000 ms
        for (int i = 0; i < 29; i++)
        {
            rWait(60);
            getRH().mouseMove(500 + aleatoire(0, 50), 600 + aleatoire(0, 50));
            // press(KeyEvent.VK_ENTER);
        }
        logIt("Refreshing navigator and waiting for 1 minute.");
        //mMove(navTitle);
        //mClick();
        press(KeyEvent.VK_F5);
        // waiting for game to reload : 1 minute
        //rWait(10);
        //press(KeyEvent.VK_ENTER);
        rWait(50);
    }
    
    /**
     * 
     */
    private void checkLight()
    {
        Long newTime = System.currentTimeMillis();
        Long lightBonus = (newTime - minElapsed ) / 60000; 
        
        if(lightBonus > 0)
            minElapsed = newTime;
        
        currentLight += lightBonus;
        if(currentLight > 350)
            currentLight = 350L;
        
        logIt("Current light : " + currentLight);
//        int hour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY);
//        int day = Calendar.getInstance().get(Calendar.DAY_OF_WEEK);
//        if(hour > 7 && hour < 17 && day != Calendar.SATURDAY && day != Calendar.SUNDAY);
//            i = 0;
    }

}
