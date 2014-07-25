package robhop;

import java.awt.Dimension;
import java.awt.Robot;
import java.awt.event.KeyEvent;

import robhop.supply.Card;
import robhop.supply.CardAction;
import robhop.supply.EndOfCard;
import robhop.supply.IAction;

public class ManageFightKlusp extends ManageFight
{
    private Boolean restart;
    private Boolean sacrDone;
    private Boolean oneCanBePlay;
    private int crystal;

    public ManageFightKlusp(Robot iRobHop, Dimension origine)
    {
        super(iRobHop, origine);

        activeLog(true);
    }

    public void lancerCombat()
    {
        logIt("Starting Fight in 10 sec, waiting for cards to go in place");
        rWait(10);
        
        // Card definition : monsters 
        Card dragon1 = Card.getMonsterPlay("Dragon1", tRed , 4, MID);
        Card dragon2 = Card.getMonsterSacr("Dragon2", tRed , 4, 2);
        Card snarer = Card.getMonsterPlay("Snarer", tBlue , 3, LEFT);
        
        // items
        Card protector =  Card.getItemPlay("Protector", tRed, 3, MID);
        
        // condition : protector after dragon1 get played
        protector.getPlayCondition().add(dragon1);
        // ranged only if protector got played
        snarer.getPlayCondition().add(protector);
        
        // first card to test is Dragon2 , for sacrifice.
        IAction firstCard = new CardAction(dragon2, this);
        // the last card is special. leave it like this, and always add it at the end
        IAction lastCard = new EndOfCard(null, this);

        // card chain : 
        firstCard.setNext(new CardAction(dragon1, this)).setNext(new CardAction(protector, this)).setNext(new CardAction(snarer, this)).setNext(lastCard);
        
        restart = false;
        sacrDone = false;
        oneCanBePlay = false;
        crystal = 0;

        addCrystal(3);
        IAction chaine = firstCard;

        while (chaine != null)
        {
            chaine.condition();
            if (restart)
            {
                logIt("Restarting");
                chaine = lastCard;
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
