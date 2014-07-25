So, to make it works :

The game must be in 100% navigator, no zoom, width: 960 height: 720 (default from edgebee),
It must be 100% visible too, meaning the "+Add token" and the "Quest" button must be visible.
Nothing must be on the game board, like another window.
Never move the navigator window while running. 
Don't go in flash full screen (key pushing doesn't work)
I advice to put the navigator windows in full screen.
Don't lock your Windows session.

All that because the bot read color, and mouse mouves with fixed size.

Start the game : right click on GoRobHop > run as Java Application.
First, it is in debug mode, to test if all is ok.
This is set in ParamShared near the first lines, with : public Boolean debug = true;
To go in normal mode, replace with : public Boolean debug = false;

In debug mode :
Start it, with the game on any screen but combat, in order the "+Add token" is visible. 
I use it to detect where is the game panel.
You should see in the console :

Starting bot in 10 sec ... Starting with $1000
Debug mode
Found in xxx / yyy
Starting Fight Analyser in 10 sec, waiting for cards to go in place

Then some line like this :

 - - - - - - - - - - - - - - - - 

Then, Go in fight, you should see something like that :

Card found.
Item 1 : cost is 5
Card found.
Item 2 : cost is 4
Card found and active.
Mon 3 : cost is 3
Card found and active.
Mon 4 : cost is 3

If it's correct with your actual cards in the game, then it's ready.

If you wanna restart the debug mode, but your game is in fight already, 
go in the TimeManager.java file, near line 160, and in the code :
if (origineX() == 0)
        {
            if(debug)
            {
                setOrigine(new Dimension(xxx, yyy));
            }

Replace the xxx,yyy by the coords you got in the console on last launch : Found in xxx / yyy.
But don't move your navigator window.

Now it's ok, change the debug mode, and set it to false.

When you start, it will ask you CASH, then current LIGHT.
As soon you valide your LIGHT input, be sure that your game is ready like described at top at this file.
Then, just let it do.
It will go in quest, move to HauntedPort aera 2, and start a game if you have enought light.

It is set to be SLOW playing, to prevent  'connection problem' or lag.
So each action take like 10 sec. Anyway, it has 14 minutes to make a fight.

After a combat, it waits 4 min, and check if it can restart a game. 
If not enought light, the bot move your mouse to prevent Windows lock.
