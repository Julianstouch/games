package robhop.supply;

import java.awt.Dimension;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;

import robhop.ManageFight;
import robhop.ParamShared;

public abstract class AbstractCardAction  extends ParamShared implements IAction
{

    private IAction next;
    private IAction head;
    private ManageFight manager;
    private Card card;
    
    private Dimension pos;
    
    public AbstractCardAction(Card card, ManageFight manager)
    {
        super(manager.getRH(), manager.getOrigine());
        this.manager = manager;
        this.card = card;
    }

    public final Boolean condition()
    {
        if(card != null)
            logIt("Testing " + getCard().getName());
        BufferedImage screen = getAScreen();
        Boolean isHere = testPresence(screen);
        if(isHere)
            action();
        return isHere;
    }
    
    public final void action()
    {
        boolean did = doAction();
        if(did)
        {
            remove();
            rWait(pressDelay);
        }
    }
    
    public Boolean testPresence(BufferedImage screen)
    {
        if (card.getMonster())
            return present(screen, MON3) || present(screen, MON4);
        else
            return present(screen, ITEM1) || present(screen, ITEM2);
    }
        
    private Boolean present(BufferedImage screen, Dimension testDim)
    {
        Boolean cardActive = testCardPresence(testDim, screen, true);
        Boolean cardPresent = testCardPresence(testDim, screen, false);
        
        if (cardActive || cardPresent)
        {
            Boolean colorOk = testColor(screen, testDim, card.getColors().getRGB());
            int costFound = evalCost(testDim, screen);
            if (colorOk && card.getCrystal().equals(costFound))
            {
                pos = testDim;
                logIt("Card found in " + getManager().getPos(testDim), debug);
                return true;
            }
        }
        return false;
    }
    
    public Boolean doAction()
    {
        if (card.getToPlay())
        {
            Boolean okToPlay = true;
            for (Card cardCond : card.getPlayCondition())
            {
                okToPlay &= cardCond.getPlayed();
            }

            if (okToPlay)
            {
                if (getManager().enoughCrystal(getCard().getCrystal()))
                {
                    play();
                    return true;
                }
                else
                {
                    logIt("Not enough");
                }
            }
            else
            {
                logIt("Card not ok to play", debug);
            }
        }
     
        if (card.getToSacr())
        {
            Boolean okToSacr = false;
            for (Card cardCond : card.getSacrCondition())
            {
                okToSacr |= cardCond.getSacred();
            }

            if (!okToSacr)
            {
                if (!getManager().isSacrDone())
                {
                    sacr();
                    return true;                    
                }
                else
                {
                    logIt("Sacrifce already done");
                }
            }
            else
            {
                logIt("Card not ok to sacr", debug);
            }
        }

        // if not played, checking if it could be played based only on cost 
        getManager().notPlayingAPlayable(card.getCrystal());
        
        return false;
    }
    
    public void play()
    {
        logIt("Playing card");
        // get Card
        press(getPos(pos));
        // put Card
        press(card.getDest());
        
        card.setPlayed(true);
        
        getManager().reduceCrystal(card.getCrystal());
    }
    
    public void sacr()
    {
        logIt("Sacrifice card");
        // active sacr
        press(KeyEvent.VK_C);
        // choose card
        press(getPos(pos));
        
        card.setSacred(true);
        
        getManager().addCrystal(card.getSacrCrystal());
        
        getManager().sacrDone();
    }
    
    protected void remove()
    {
        if(next == null)
            return;
        if(head != null)
           head.setNext(next);
        else
           next.setHead(null);
    }

    public IAction setNext(IAction theNext)
    {
        this.next = theNext;
        next.setHead(this);
        return theNext;
    }
    
    public IAction getNext()
    {
        return next;
    }
    
    public IAction getHead()
    {
        return head;
    }
    
    public void setHead(IAction theHead)
    {
        this.head = theHead;
    }

    /**
     * @return the manager
     */
    public ManageFight getManager()
    {
        return manager;
    }

    /**
     * @return the card
     */
    public Card getCard()
    {
        return card;
    }

    protected int getPos(Dimension pos)
    {
        return manager.getPos(pos);
    }
}
