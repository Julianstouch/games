package robhop;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

public abstract class ParamShared
{
    public Boolean debug = false;
    
    public int combatDelay = 20; //20
    public int pressDelay = 10;  //10
    public int moveDelay = 1;    //1
    public int clickDelay = 2;  //2
    
    private Dimension origine;
    private int origineX;
    private int origineY;
    private Rectangle screenRect;
    private Robot iRobHop;
    private Boolean logEnable;
    
    public static final Color tWhite = Color.WHITE;
    public static final Color tGray = Color.GRAY;
    public static final Color tBlack = Color.BLACK;
    
    public static final Color tRed = new Color(210, 20, 0);
    public static final Color tGreen = new Color(20, 120, 0);
    public static final Color tBlue = new Color(0, 130, 200);
    public static final Color tOrange = new Color(220, 110, 0);
    public static final Color tViolet = new Color(80, 60, 150);
    
    public static final Color tBorderActive = new Color(229, 254, 229);
    
    public static final Dimension ITEM1 = new Dimension(-171, 111);
    public static final Dimension ITEM2 = new Dimension(-27, 129);
    public static final Dimension MON3 = new Dimension(-171, 346);
    public static final Dimension MON4 = new Dimension(-27, 364);

    public static final Dimension POSBA = new Dimension(0, -12);
    public static final Dimension POSBB = new Dimension(0, -9);
    
    public static final Dimension COST4 = new Dimension(-13, 6); // not black = 4
    public static final Dimension COST4Bis = new Dimension(-14, 5); // not black = 4
    public static final Dimension COST56 = new Dimension(-14, 9); // black = 5 or 6
    public static final Dimension COST6 = new Dimension(-14, 13); // black = 6
    public static final Dimension COST2 = new Dimension(-11, 10); // black = 2
    public static final Dimension COST1 = new Dimension(-10, 8); // black = 1
    
    public static final Dimension BICOL = new Dimension(46, 0);
    
    public int LEFT = KeyEvent.VK_1;
    public int MID = KeyEvent.VK_2;
    public int RIGHT = KeyEvent.VK_3;
    
    public static final Map<Dimension, Integer> POS = new HashMap<Dimension, Integer>();
    static
    {
        POS.put(ITEM1, KeyEvent.VK_1);
        POS.put(ITEM2, KeyEvent.VK_2);
        POS.put(MON3, KeyEvent.VK_3);
        POS.put(MON4, KeyEvent.VK_4);
    }
    
    public static final Map<Color, String> temoin = new HashMap<Color, String>();
    static
    {
        temoin.put(tWhite, "White");
        temoin.put(tGray, "Gray");
        temoin.put(tBlack, "Black");

        temoin.put(tRed, "Red");
        temoin.put(tGreen, "Green");
        temoin.put(tBlue, "Blue");

        temoin.put(tOrange, "Orange");
        temoin.put(tViolet, "Violet");
    }
    
    public ParamShared(Robot iRobHop, Dimension origine)
    {
        // {width: 960, height: 720}
        this.iRobHop = iRobHop;
        if (origine != null)
        {
            this.origine = origine;
            origineX = (int) origine.getWidth();
            origineY = (int) origine.getHeight();
        }
        logEnable = true;
    }

    public BufferedImage getAScreen()
    {
        if (screenRect == null)
            screenRect = new Rectangle(Toolkit.getDefaultToolkit().getScreenSize());
        return iRobHop.createScreenCapture(screenRect);
    }

    /**
     * Press a key and wait 10s
     * 
     * @param key
     */
    public void press(int key)
    {
        iRobHop.keyPress(key);
        iRobHop.keyRelease(key);
        rWait(pressDelay);
    }

    /**
     * move the mouse to this coords, from origines
     * 
     * @param coords
     */
    public void mMove(Dimension coords)
    {
        iRobHop.mouseMove(origineX + coords.width, origineY + coords.height);
        rWait(moveDelay);
    }

    /**
     * left click of the mouse
     */
    public void mClick()
    {
        iRobHop.mousePress(InputEvent.BUTTON1_MASK);
        iRobHop.mouseRelease(InputEvent.BUTTON1_MASK);
        rWait(clickDelay);
    }

    /**
     * Test a pixel on a screen
     * 
     * @param screen
     * @param coords
     * @param rgb
     * @return
     */
    public boolean testColor(BufferedImage screen, Dimension coords, int rgb)
    {
        Color screenColorTrue = new Color(screen.getRGB(origineX + coords.width, origineY + coords.height));
        //Color screenColorBiTrue = new Color(screen.getRGB(origineX + coords.width + BICOL.width, origineY + coords.height + BICOL.height));
        
        Color screenColor = evalColor(screenColorTrue);
        //Color screenColorBi = evalColor(screenColorBiTrue);
        
//        if(screenColor.getRGB() != screenColorBi.getRGB())
//        {
//            logIt("Color found : " + evalColorEntry(screenColor).getValue() + " / " + evalColorEntry(screenColorBi).getValue(), debug);
//        }
//        else
//        {
//            logIt("Color found : " + evalColorEntry(screenColor).getValue(), debug);
//        }
        return evalColor(screenColor).getRGB() == rgb;
    }

    /**
     * Test a pixel on a screen
     * 
     * @param screen
     * @param coords
     * @param rgb
     * @return
     */
    public boolean testColorExact(BufferedImage screen, Dimension coords, int rgb)
    {
        Color screenColorTrue = new Color(screen.getRGB(origineX + coords.width, origineY + coords.height));
        return screenColorTrue.getRGB() == rgb;
    }
    
    /**
     * 
     * @param toEval
     * @return
     */
    private Color evalColor(Color toEval)
    {
        return evalColorEntry(toEval).getKey();
    }
    
    /**
     * 
     * @param toEval
     * @return
     */
    private Entry<Color, String> evalColorEntry(Color toEval)
    {
        Entry<Color, String> ret = null;
        int laBonne = 1000;
        for (Entry<Color, String> couple : temoin.entrySet())
        {
            int diff = 0;
            Color colTemoin = couple.getKey();
            diff += Math.abs(colTemoin.getRed() - toEval.getRed());
            diff += Math.abs(colTemoin.getGreen() - toEval.getGreen());
            diff += Math.abs(colTemoin.getBlue() - toEval.getBlue());
            if (laBonne > diff)
            {
                laBonne = diff;
                ret = couple;
            }
        }
        return ret;
    }
    
    /**
     * Test card presence
     * 
     * @param pos
     * @param posType
     * @param screen
     * @param text
     * @return
     */
    public boolean testCardPresence(Dimension pos, BufferedImage screen, boolean active)
    {
        Color cToTest;
        Dimension posToTest;
        if(active)
        {
            cToTest = tBorderActive;
            posToTest = POSBA;
        }
        else
        {
            cToTest = tBlack;
            posToTest = POSBB;
        }
        Dimension testB = new Dimension(pos);
        
        testB.setSize(testB.getWidth() + posToTest.getWidth(), testB.getHeight() + posToTest.getHeight());
        boolean isBorder = testColorExact(screen, testB, cToTest.getRGB());
        if(isBorder)
        {
            for (int i = 0; i < 10; i++)
            {
                testB.setSize(testB.getWidth() + 2, testB.getHeight());
                isBorder &= testColorExact(screen, testB, cToTest.getRGB());
                if(!isBorder)
                    break;
            }
        }
        if(active && isBorder)
            logIt("Card found and active.");
        else if(!active && isBorder)
            logIt("Card found.");
        return isBorder;
    }
    
    /**
     * 
     * @param pos
     * @param screen
     * @return
     */
    public int evalCost(Dimension pos, BufferedImage screen)
    {
        int posX = origineX + pos.width;
        int posY = origineY + pos.height;
        
        // 4
        Color spot4true = new Color(screen.getRGB(posX + COST4.width, posY + COST4.height));
        //logIt("4 : " + evalColorEntry(spot4true).getValue());
        Color spot4 = evalColorEntry(spot4true).getKey();
        Color spot4btrue = new Color(screen.getRGB(posX + COST4Bis.width, posY + COST4Bis.height));
        //logIt("4b : " + evalColorEntry(spot4btrue).getValue());
        Color spot4b = evalColorEntry(spot4btrue).getKey();
        if (spot4.getRGB() != tBlack.getRGB() && spot4b.getRGB() != tBlack.getRGB() )
        {
            return 4;
        }
        
        // 5 ou 6
        Color spot56true = new Color(screen.getRGB(posX + COST56.width, posY + COST56.height));
        //logIt("56 : " + evalColorEntry(spot56true).getValue());
        Color spot56 = evalColor(spot56true);
        if (spot56.getRGB() == tBlack.getRGB())
        {
            Color spot6true = new Color(screen.getRGB(posX + COST6.width, posY + COST6.height));
            //logIt("6 : " + evalColorEntry(spot6true).getValue());
            Color spot6 = evalColor(spot6true);
            if(spot6.getRGB() == tBlack.getRGB())
                return 6;
            else
                return 5;    
        }
        
        // 2
        Color spot2true = new Color(screen.getRGB(posX + COST2.width, posY + COST2.height));
        Color spot2 = evalColor(spot2true);
        //logIt("2 : " + evalColorEntry(spot2true).getValue());
        if (spot2.getRGB() != tBlack.getRGB())
        {
            return 2;
        }
        
        // 1
        Color spot1true = new Color(screen.getRGB(posX + COST1.width, posY + COST1.height));
        Color spot1 = evalColor(spot1true);
        //logIt("1 : " + evalColorEntry(spot1true).getValue());
        if (spot1.getRGB() == tBlack.getRGB())
        {
            return 1;
        }
        
        return 3;
    }
    
    /**
     * Logging
     * 
     * @param text
     */
    public void logIt(String text, Boolean debug)
    {
        if(debug)
        {
            logIt(text);
        }
    }
    
    /**
     * Logging
     * 
     * @param text
     */
    public void logIt(String text)
    {
        if(logEnable)
        {
            System.out.println(text);
        }
    }
    

    /**
     * 
     * @param sec
     */
    public void rWait(int sec)
    {
        // security , if already in ms, do not x1000
        if (sec > 999)
            iRobHop.delay(sec);
        else
            iRobHop.delay(sec * 1000);
    }

    /**
     * 
     * @return
     */
    public Robot getRH()
    {
        return iRobHop;
    }

    public int origineX()
    {
        return origineX;
    }

    public int origineY()
    {
        return origineY;
    }

    /**
     * @return the origine
     */
    public Dimension getOrigine()
    {
        return origine;
    }

    /**
     * @param origine
     *            the origine to set
     */
    public void setOrigine(Dimension origine)
    {
        this.origine = origine;
        origineX = (int) origine.getWidth();
        origineY = (int) origine.getHeight();
    }
    
    public void activeLog(Boolean active)
    {
        logEnable = active;
    }
    
    /**
     * Random number
     * 
     * @param min
     * @param max
     * @return
     */
    public int aleatoire(int min, int max)
    {
        return ((int) (Math.random() * (max - min + 1))) + min;
    }
}
