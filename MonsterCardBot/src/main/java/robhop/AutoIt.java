package robhop;


public class AutoIt
{

    public static void main(String[] args)
    {
        try
        {
            Process proc = Runtime.getRuntime().exec("javac Verif.java");
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }
}
