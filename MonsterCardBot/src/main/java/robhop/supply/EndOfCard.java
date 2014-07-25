package robhop.supply;

import java.awt.image.BufferedImage;

import robhop.ManageFight;

public class EndOfCard extends AbstractCardAction
{
    private int antiLoop;
    
    public EndOfCard(Card card, ManageFight manager)
    {
        super(card, manager);
        antiLoop = 0;
    }

    public Boolean testPresence(BufferedImage screen)
    {
        logIt("End of chaine");
        antiLoop++;

        if(antiLoop > 4)
        {
            logIt("End tested 5 times, ending.");
            return false;
        }
        
        if (getHead() != null)
        {
            logIt("At least one card not played, restarting chaine");
            return true;
        }
        return false;
        
    }
    
    public Boolean doAction()
    {
        getManager().postAction();
        return false;
    }
}
