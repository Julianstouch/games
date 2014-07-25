package robhop;

import java.awt.Color;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

public class Verif
{
    private static final int TOKEN_3 = -4261120;
    private static final int TOKEN_2 = -8606174;
    private static final int TOKEN_1 = -13663305;
    
    public static void main(String[] args)
    {
        Color yo = new Color(TOKEN_3);
        // Color yo = new Color(-15643299);
//        System.out.println("R "+yo.getRed());
//        System.out.println("G "+yo.getGreen());
//        System.out.println("B "+yo.getBlue());
//        System.out.println(yo.getRGB());
//        
//        Long time = System.currentTimeMillis();
//        System.out.println(time);
//        time = time / 60000;
//        System.out.println(time);        
        
        Map<Color, String> temoin = new HashMap<Color, String>();
        temoin.put(Color.WHITE,"Blanc");
        temoin.put(Color.GRAY,"Gris");
        temoin.put(Color.BLACK, "Noir");

        temoin.put(new Color(210,20,0),"Rouge");
        temoin.put(new Color(20,120,0),"Vert");
        temoin.put(new Color(0,130,200),"Blue");
        
        temoin.put(new Color(220,110,0),"Orange");
        temoin.put(new Color(80,60,150),"Violet");
        
        for (Entry<Color, String> couple : temoin.entrySet())
        {
            int diff = 0;
            Color colTemoin = couple.getKey();
            diff += Math.abs(colTemoin.getRed() - yo.getRed());
            diff += Math.abs(colTemoin.getGreen() - yo.getGreen());
            diff += Math.abs(colTemoin.getBlue() - yo.getBlue());
            System.out.println(couple.getValue() + " : " + diff);
        }
        
    }
}
