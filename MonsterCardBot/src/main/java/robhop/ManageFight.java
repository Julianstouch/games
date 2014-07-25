package robhop;

import java.awt.Dimension;
import java.awt.Robot;
import java.awt.event.KeyEvent;

import robhop.supply.Card;
import robhop.supply.CardAction;
import robhop.supply.EndOfCard;
import robhop.supply.IAction;

public class ManageFight extends ParamShared
{
    private Boolean restart;
    private Boolean sacrDone;
    private Boolean oneCanBePlay;
    private int crystal;

    public ManageFight(Robot iRobHop, Dimension origine)
    {
        super(iRobHop, origine);

        activeLog(true);
    }

    public void lancerCombat()
    {
        logIt("Starting Fight in 10 sec, waiting for cards to go in place");
        rWait(10);

        Card tank = Card.getMonsterPlay("Headless", tRed , 4, MID);
        Card range = Card.getMonsterPlay("Destroyher", tOrange, 3, LEFT);
        Card heal = Card.getMonsterSacr("Squid", tGreen , 3, 3);
        //Card sacr = Card.getMonsterSacr("BigRat", tOrange /*ORANGE*/, 3, 3);
        
        Card shield = Card.getItemPlay("Shield", tRed, 5, MID);
        Card dagger = Card.getItemPlaySacr("Dagger", tRed, 2, LEFT, 1);
        Card bow =  Card.getItemPlaySacr("Bow", tRed, 4, LEFT, 1);
        
        shield.getPlayCondition().add(tank);
        range.getPlayCondition().add(shield);
        bow.getPlayCondition().add(range);
        bow.getSacrCondition().add(dagger);
        dagger.getPlayCondition().add(range);
        dagger.getSacrCondition().add(bow);
        
        IAction debut = new CardAction(heal, this);
        IAction fin = new EndOfCard(null, this);

        debut.setNext(new CardAction(tank, this)).setNext(new CardAction(shield, this)).setNext(new CardAction(range, this))
                .setNext(new CardAction(bow, this)).setNext(new CardAction(dagger, this)).setNext(fin);
        
        restart = false;
        sacrDone = false;
        oneCanBePlay = false;
        crystal = 0;

        addCrystal(3);
        IAction chaine = debut;

        while (chaine != null)
        {
            chaine.condition();
            if (restart)
            {
                logIt("Restarting");
                chaine = fin;
                while (chaine.getHead() != null)
                {
                    chaine = chaine.getHead();
                }
                restart = false;
            }
            else
                chaine = chaine.getNext();
        }

        logIt("Chaine over, waiting 4 minutes for combat ending");
        rWait(60);
        rWait(60);
        rWait(60);
        rWait(60);
    }

    public void addCrystal(Integer cost)
    {
        crystal = crystal + cost;
        logIt("Add crystal : " + crystal, debug);
    }

    public void reduceCrystal(Integer cost)
    {
        crystal = crystal - cost;
        restart = true;
        oneCanBePlay = false; // reseting variable
        logIt("Reduce crystal " + crystal, debug);
    }

    public Boolean enoughCrystal(Integer cost)
    {
        return crystal >= cost;
    }

    public boolean isSacrDone()
    {
        return sacrDone;
    }

    public void postAction()
    {
        logIt("Round ended");
        if (!sacrDone || oneCanBePlay)
        {
            logIt("Pushing Space");
            press(KeyEvent.VK_SPACE);
        }
        // re init
        sacrDone = false;
        oneCanBePlay = false;
        restart = true;
        rWait(combatDelay);
        addCrystal(3);
        logIt("New round");
    }

    public void sacrDone()
    {
        sacrDone = true;
        restart = true;
        logIt("Sacrifice Done", debug);
    }

    public int getPos(Dimension pos)
    {
        return POS.get(pos);
    }
    
    public void notPlayingAPlayable(Integer cost)
    {
        if(enoughCrystal(cost))
        {
            logIt("Not playing a playable");
            oneCanBePlay = true;
        }
    }
}
