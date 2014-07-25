package robhop.supply;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

public class Card
{

    private String name;
    private Color colors;
    private Boolean toPlay;
    private Integer crystal;
    private Integer dest;
    private Boolean toSacr;
    private Integer sacrCrystal;
    private Boolean monster;
    
    private Boolean played;
    private Boolean sacred;
    
    private List<Card> sacrCondition;
    private List<Card> playCondition;
    
    // -- monster
    public static Card getMonsterPlay(String name, Color coulor, Integer crystal, Integer dest)
    {
        return new Card(true, name, coulor, true, false, crystal, dest, null);
    }

    public static Card getMonsterSacr(String name, Color coulor, Integer crystal, Integer sacrCrystal)
    {
        return new Card(true, name, coulor, false, true, crystal, null, sacrCrystal);
    }

    public static Card getMonsterPlaySacr(String name, Color coulor, Integer crystal, Integer dest, Integer sacrCrystal)
    {
        return new Card(true, name, coulor, true, true, crystal, dest, sacrCrystal);
    }
    
    // -- item
    public static Card getItemPlay(String name, Color coulor, Integer crystal, Integer dest)
    {
        return new Card(false, name, coulor, true, false, crystal, dest , null);
    }

    public static Card getItemSacr(String name, Color coulor, Integer crystal, Integer sacrCrystal)
    {
        return new Card(false, name, coulor, false, true, crystal, null, sacrCrystal);
    }

    public static Card getItemPlaySacr(String name, Color coulor, Integer crystal, Integer dest, Integer sacrCrystal)
    {
        return new Card(false, name, coulor, true, true, crystal, dest, sacrCrystal);
    }
    
    
    /**
     * Play or Sacr
     * @param monster
     * @param name
     * @param coulor
     * @param crystal
     * @param dest
     * @param sacrCrystal
     */
    private Card(Boolean monster, String name, Color coulor, Boolean toPlay, Boolean toSacr, Integer crystal, Integer dest, Integer sacrCrystal)
    {
        this.monster = monster;
        this.name = name;
        this.colors = coulor;
        this.toPlay = toPlay;
        this.toSacr = toSacr;
        this.crystal = crystal;
        this.dest = dest;
        this.sacrCrystal = sacrCrystal;
        
        this.played = false;
        this.sacred = false;
        this.sacrCondition = new ArrayList<Card>();
        this.playCondition = new ArrayList<Card>();
    }

    /**
     * @return the name
     */
    public String getName()
    {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name)
    {
        this.name = name;
    }

    /**
     * @return the colors
     */
    public Color getColors()
    {
        return colors;
    }

    /**
     * @param colors the colors to set
     */
    public void setColors(Color colors)
    {
        this.colors = colors;
    }

    /**
     * @return the toPlay
     */
    public Boolean getToPlay()
    {
        return toPlay;
    }

    /**
     * @param toPlay the toPlay to set
     */
    public void setToPlay(Boolean toPlay)
    {
        this.toPlay = toPlay;
    }

    /**
     * @return the crystal
     */
    public Integer getCrystal()
    {
        return crystal;
    }

    /**
     * @param crystal the crystal to set
     */
    public void setCrystal(Integer crystal)
    {
        this.crystal = crystal;
    }

    /**
     * @return the dest
     */
    public Integer getDest()
    {
        return dest;
    }

    /**
     * @param dest the dest to set
     */
    public void setDest(Integer dest)
    {
        this.dest = dest;
    }

    /**
     * @return the toSacr
     */
    public Boolean getToSacr()
    {
        return toSacr;
    }

    /**
     * @param toSacr the toSacr to set
     */
    public void setToSacr(Boolean toSacr)
    {
        this.toSacr = toSacr;
    }

    /**
     * @return the sacrCrystal
     */
    public Integer getSacrCrystal()
    {
        return sacrCrystal;
    }

    /**
     * @param sacrCrystal the sacrCrystal to set
     */
    public void setSacrCrystal(Integer sacrCrystal)
    {
        this.sacrCrystal = sacrCrystal;
    }

    /**
     * @return the monster
     */
    public Boolean getMonster()
    {
        return monster;
    }

    /**
     * @param monster the monster to set
     */
    public void setMonster(Boolean monster)
    {
        this.monster = monster;
    }

    /**
     * @return the played
     */
    public Boolean getPlayed()
    {
        return played;
    }

    /**
     * @param played the played to set
     */
    public void setPlayed(Boolean played)
    {
        this.played = played;
    }

    /**
     * @return the sacred
     */
    public Boolean getSacred()
    {
        return sacred;
    }

    /**
     * @param sacred the sacred to set
     */
    public void setSacred(Boolean sacred)
    {
        this.sacred = sacred;
    }

    /**
     * @return the sacrCondition
     */
    public List<Card> getSacrCondition()
    {
        return sacrCondition;
    }

    /**
     * @param sacrCondition the sacrCondition to set
     */
    public void setSacrCondition(List<Card> sacrCondition)
    {
        this.sacrCondition = sacrCondition;
    }

    /**
     * @return the playCondition
     */
    public List<Card> getPlayCondition()
    {
        return playCondition;
    }

    /**
     * @param playCondition the playCondition to set
     */
    public void setPlayCondition(List<Card> playCondition)
    {
        this.playCondition = playCondition;
    }
    
}
