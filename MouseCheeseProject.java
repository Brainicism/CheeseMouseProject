// The "MouseCheeseProject" class.
/*
		Final Programming Assignment
		      Transformice
		      By: Brian Le
		    Student #: 441607
		Date: December 10th, 2013
		     Class: ICS-3U0
		   Instructor: A. Sayed
Details:
    The game I have created is called 'Transformice', based off of the popular online flash game of the same name.
    The player plays as a mouse, and has to adventure through different maps and use special power ups to collect
    the cheese, and return to the mouse hole safely to advance to the next level. The game starts off as a very basic
    start screen, with a start button. Once you click the start button, the game begins. The timer will begin when
    you first move, and will end when you complete the game. The game features basic collision detection to detect
    platforms, power ups, and cheese/mousehole. You are also able to press multiple arrow keys at once by using the
    keyDown method. You control your mouse using arrows key or 'WASD' to move,
    and 'z' and 'x' to use your power ups. All of this information can be found by holding 'H' while in game. The game
    is complete with several sound effects, imported pictures. It also features a status bar at the top that includes
    the elapsed time, power up availabiity, and death count.


Known Bugs:
    -Mouse may sometimes fall through certain platforms while using power jump (rare occurence)
    -Holding a key, then tabbing out of the applet, will cause the key to be continually activated
	 - Solution: pressing the glitched button once will fix the error
    - Holding the 'left' and 'right' directional key at the same time will sometimes cause a visual glitch


*/
import java.applet.*;
import java.awt.*;
import java.awt.event.*;

public class MouseCheeseProject extends Applet implements Runnable, ActionListener
{
    // Place instance variables here
    // Initialization of variables

    int xPos;
    int yPos;
    int xSpeed = 0;
    int ySpeed = 0;
    int teleportPower = 0;
    int jumpPower = 0;
    int teleportTimer;
    int currentLevel = 0;
    int deathCount = 0;
    int timer = 0;
    boolean timerRunning = false;
    int seconds, minutes = 0;
    boolean startScreen = true;
    boolean endScreen = false;
    boolean midJump = false;
    boolean upKeyDown = false;
    boolean rightKeyDown = false;
    boolean leftKeyDown = false;
    boolean teleportKeyDown = false;
    boolean jumpKeyDown = false;
    boolean cheeseFree = true;
    boolean teleportFree = true;
    boolean jumpFree = true;
    boolean jumpFree2 = false;
    boolean hasSpawned = false;
    boolean instructionScreen = false;
    boolean[] level = {false, false, false};
    boolean climbing = false;
    private static int maxX = 900;
    private static int maxY = 600;
    Font font = new Font ("Arial", Font.BOLD, 20);
    Font font2 = new Font ("Arial", Font.BOLD, 75);

    private final String PICTURE_PATH = "mouseLeft.gif";
    private final String PICTURE_PATH2 = "mouseRight.gif";
    private final String PICTURE_PATH3 = "cheese.gif";
    private final String PICTURE_PATH4 = "mouseJumpRight.gif";
    private final String PICTURE_PATH5 = "mouseJumpLeft.gif";
    private final String PICTURE_PATH6 = "mouseRunRight.gif";
    private final String PICTURE_PATH7 = "mouseRunLeft.gif";
    private final String PICTURE_PATH8 = "mapOne.gif";
    private final String PICTURE_PATH9 = "teleport.gif";
    private final String PICTURE_PATH10 = "teleportIcon.png";
    private final String PICTURE_PATH11 = "statusBar.png";
    private final String PICTURE_PATH12 = "mapTwo.gif";
    private final String PICTURE_PATH13 = "instructions.png";
    private final String PICTURE_PATH14 = "jumpIcon.png";
    private final String PICTURE_PATH15 = "mapThree.gif";
    private final String PICTURE_PATH16 = "titleScreen.gif";
    private final String PICTURE_PATH17 = "endScreen.png";

    AudioClip deathSound, levelComplete, teleportSound;

    Image mouseLeft, mouseRight, jumpIcon, cheese, titleScreen, endingScreen, mouseJumpRight, mouseJumpLeft, mouseRunRight, mouseRunLeft, mapOne, mapTwo, teleport, teleportIcon, statusBar, instructions, mapThree;
    int picWidth, picHeight;
    int picWidth2, picHeight2;
    int picWidth3, picHeight3;
    int picWidth4, picHeight4;
    int picWidth5, picHeight5;
    int picWidth6, picHeight6;
    int picWidth7, picHeight7;
    int picWidth8, picHeight8;
    int picWidth9, picHeight9;
    int picWidth10, picHeight10;
    int picWidth11, picHeight11;
    int picWidth12, picHeight12;
    int picWidth13, picHeight13;
    int picWidth14, picHeight14;

    int picWidth15, picHeight15;
    int picWidth16, picHeight16;
    int picWidth17, picHeight17;

    Button start; // starting button
    boolean facingLeft, facingRight = true;

    // declare two instance variables at the head of the program
    private Image dbImage;
    private Graphics dbg;

    public void init ()
    {
	setLayout (null);
	start = new Button ("Start");
	start.setBounds (650, 400, 100, 30);
	add (start);
	start.addActionListener (this);

	// Place the body of the initialization method here
	setBackground (Color.BLACK);
	resize (900, 600);

	deathSound = getAudioClip (getDocumentBase (), "teleport.wav");
	levelComplete = getAudioClip (getDocumentBase (), "finishlevel.wav");
	teleportSound = getAudioClip (getDocumentBase (), "teleport.wav");

	mouseLeft = getToolkit ().getImage (PICTURE_PATH);
	mouseRight = getToolkit ().getImage (PICTURE_PATH2);
	cheese = getToolkit ().getImage (PICTURE_PATH3);
	mouseJumpRight = getToolkit ().getImage (PICTURE_PATH4);
	mouseJumpLeft = getToolkit ().getImage (PICTURE_PATH5);
	mouseRunRight = getToolkit ().getImage (PICTURE_PATH6);
	mouseRunLeft = getToolkit ().getImage (PICTURE_PATH7);
	mapOne = getToolkit ().getImage (PICTURE_PATH8);
	teleport = getToolkit ().getImage (PICTURE_PATH9);
	teleportIcon = getToolkit ().getImage (PICTURE_PATH10);
	statusBar = getToolkit ().getImage (PICTURE_PATH11);
	mapTwo = getToolkit ().getImage (PICTURE_PATH12);
	instructions = getToolkit ().getImage (PICTURE_PATH13);
	jumpIcon = getToolkit ().getImage (PICTURE_PATH14);
	mapThree = getToolkit ().getImage (PICTURE_PATH15);
	titleScreen = getToolkit ().getImage (PICTURE_PATH16);
	endingScreen = getToolkit ().getImage (PICTURE_PATH17);

	prepareImage (mouseLeft, this);
	prepareImage (mouseRight, this);
	prepareImage (cheese, this);
	prepareImage (mouseJumpRight, this);
	prepareImage (mouseJumpLeft, this);
	prepareImage (mouseRunRight, this);
	prepareImage (mouseRunLeft, this);
	prepareImage (mapOne, this);
	prepareImage (teleport, this);
	prepareImage (teleportIcon, this);
	prepareImage (statusBar, this);
	prepareImage (mapTwo, this);
	prepareImage (instructions, this);
	prepareImage (jumpIcon, this);
	prepareImage (mapThree, this);
	prepareImage (titleScreen, this);
	prepareImage (endingScreen, this);

	MediaTracker tracker = new MediaTracker (this);
	tracker.addImage (mouseLeft, 0);
	tracker.addImage (mouseRight, 1);
	tracker.addImage (cheese, 2);
	tracker.addImage (mouseJumpRight, 2);
	tracker.addImage (mouseJumpLeft, 2);
	tracker.addImage (mouseRunRight, 2);
	tracker.addImage (mouseRunLeft, 2);
	tracker.addImage (mapOne, 2);
	tracker.addImage (teleport, 2);
	tracker.addImage (teleportIcon, 2);
	tracker.addImage (statusBar, 2);
	tracker.addImage (mapTwo, 2);
	tracker.addImage (instructions, 2);
	tracker.addImage (jumpIcon, 2);
	tracker.addImage (mapThree, 2);
	tracker.addImage (titleScreen, 2);
	tracker.addImage (endingScreen, 2);
	picWidth = mouseLeft.getWidth (null);
	picHeight = mouseLeft.getHeight (null);
	picWidth2 = mouseRight.getWidth (null);
	picHeight2 = mouseRight.getHeight (null);
	picWidth3 = cheese.getWidth (null);
	picHeight3 = cheese.getHeight (null);
	picWidth4 = mouseJumpRight.getWidth (null);
	picHeight4 = mouseJumpRight.getHeight (null);
	picWidth5 = mouseJumpLeft.getWidth (null);
	picHeight5 = mouseJumpLeft.getHeight (null);
	picWidth6 = mouseRunRight.getWidth (null);
	picHeight6 = mouseRunRight.getHeight (null);
	picWidth7 = mouseRunLeft.getWidth (null);
	picHeight7 = mouseRunLeft.getHeight (null);
	picWidth8 = mapOne.getWidth (null);
	picHeight8 = mapOne.getHeight (null);
	picWidth9 = teleport.getWidth (null);
	picHeight9 = teleport.getHeight (null);
	picWidth10 = teleportIcon.getWidth (null);
	picHeight10 = teleportIcon.getHeight (null);
	picWidth11 = statusBar.getWidth (null);
	picHeight11 = statusBar.getHeight (null);
	picWidth12 = mapTwo.getWidth (null);
	picHeight12 = mapTwo.getHeight (null);
	picWidth13 = instructions.getWidth (null);
	picHeight13 = instructions.getHeight (null);
	picWidth14 = jumpIcon.getWidth (null);
	picHeight14 = jumpIcon.getHeight (null);
	picWidth15 = mapThree.getWidth (null);
	picHeight15 = mapThree.getHeight (null);
	picWidth16 = titleScreen.getWidth (null);
	picHeight16 = titleScreen.getHeight (null);
	picWidth17 = endingScreen.getWidth (null);
	picHeight17 = endingScreen.getHeight (null);
	// Add the picture to the list of images to be tracked

	// Wait until all the images are loaded.  This can throw an
	// InterruptedException although it's not likely, so we ignore
	// it if it occurs.


    } // init method


    public void start ()
    {

	// define a new thread
	Thread th = new Thread (this);
	// start this thread
	th.start ();

    }


    public void delay (int time)  //adds an adjustable delay
    {
	try
	{
	    Thread.sleep (time);
	}
	catch (InterruptedException ex)
	{
	}

    }


    public void spawnPoint ()  //defines the spawn points for each level
    {
	if (level [0] == true)
	{
	    xPos = 50;
	    yPos = 500;
	}
	if (level [1] == true)
	{
	    xPos = 120;
	    yPos = 80;
	    teleportPower = 0;
	}
	if (level [2] == true)
	{
	    xPos = 120;
	    yPos = 80;
	}

    }


    public void platformDetect (int x, int y, int xWidth)  //creates an if statement for each platform
    {
	if (xPos >= x - 10 && xPos <= x + xWidth && yPos == y - 45)
	{
	    yPos = y - 50;
	    midJump = false;
	}
    }


    public void collisionDetection ()  //detects collisions of platforms
    {
	if (level [0] == true) //level one
	{

	    if (yPos > maxY + 50) //re-initializes level upon

		{
		    xPos = 50;
		    yPos = 500;
		    cheeseFree = true;
		    deathSound.play ();
		    deathCount++;

		}

	    platformDetect (0, maxY - 5, 200); // g.fillRect (0, maxY - 5, 200, 10);
	    platformDetect (400, maxY - 5, 300); //g.fillRect(400, maxY-5, 300, 10);
	    platformDetect (500, 300, 120); //    g.fillRect (500, 300, 100, 10);

	    if (xPos > maxX - 70) //   ladder
	    {
		xPos = maxX - 70;
		yPos -= 10;
		climbing = true;
	    }
	    else
		climbing = false;

	    if (yPos <= -30) //upper boundary
	    {
		yPos = -30;
	    }
	}


	else if (level [1] == true) //level 2
	{
	    if (yPos > maxY + 50) //re-initializes level upon death
	    {
		xPos = 120;
		yPos = 80;
		teleportPower = 0;
		cheeseFree = true;
		teleportFree = true;
		deathSound.play ();
		deathCount++;

	    }

	    platformDetect (80, 150, 100); // // g.fillRect (80, 150, 100, 10);
	    platformDetect (80, 450, 100); //    g.fillRect (80, 450, 100, 5);
	    platformDetect (300, 120, 100); ////g.fillRect (300, 120, 100, 5);
	    platformDetect (550, 120, 100);   // g.fillRect (550, 120, 100, 5);
	    platformDetect (300, 400, 200); // g.fillRect (300, 400, 200, 5);
	    platformDetect (300, 580, 200); //g.fillRect (300, 580, 200, 5);


	    if (yPos >= 400 && yPos <= 600 && xPos >= 260 && xPos <= 320)
	    {
		xPos = 260;
	    }
	    else if
		(yPos >= 400 && yPos <= 600 && xPos >= 320 && xPos <= 340)
	    {
		xPos = 340;
	    }
	}


	else if (level [2] == true)
	{
	    if (yPos > maxY + 50) //re-initializes level upon death
	    {
		xPos = 120;
		yPos = 80;
		teleportPower = 0;
		cheeseFree = true;
		teleportFree = true;
		jumpFree = true;
		jumpFree2 = false;
		jumpPower = 0;
		deathSound.play ();
		deathCount++;

	    }

	    if (xPos >= 110 && xPos <= 145 && yPos >= 0 && yPos <= 155) // vertical wall
	    {
		xPos = 110;
	    }
	    platformDetect (90, 150, 50);
	    platformDetect (90, 550, 50);
	    platformDetect (maxX - 200, 550, 50);
	    platformDetect (maxX - 200, 150, 50);
	    platformDetect (maxX / 2 - 50, maxY / 2, 50);
	    platformDetect (maxX - 100, 500, 50);




	}

    }


    public void levelComplete ()  //detects when mouse has cheese and enters holes, moves user to next level
    {
	if (level [0] == true)
	{
	    if (cheeseFree == false && (xPos >= 10 && xPos <= 60) && (yPos >= maxY - 50 && yPos <= maxY))  //  completion of level one      g.fillOval (10, maxY - 50, 50, 50);

		{
		    levelComplete.play ();
		    delay (500);

		    level [0] = false;
		    level [1] = true;
		    hasSpawned = false;
		    cheeseFree = true;
		    currentLevel++;

		}

	}


	else if (level [1] == true) // level 2
	{

	    if (cheeseFree == false && (xPos >= 380 && xPos <= 430) && (yPos >= 535 && yPos <= 590))  // completion of level two g.fillOval (380, 540, 50, 50);
	    {
		levelComplete.play ();
		delay (500);
		level [1] = false;
		level [2] = true;
		hasSpawned = false;
		cheeseFree = true;
		teleportFree = true;
		currentLevel++;

	    }

	}


	else if (level [2] == true) //            g.fillOval (90, 505, 50, 50);

	    {
		if (cheeseFree == false && (xPos >= 90 && xPos <= 140) && yPos == 505)
		{
		    delay (500);
		    endScreen = true;
		    timerRunning = false;
		}
	    }
    }


    public void pickUpDetect ()  //detects pick up of powerups and cheese
    { //cheese 88x43
	if (level [0] == true)
	{
	    if (xPos >= 310 && xPos <= 378 && yPos <= 300 && yPos >= 257)
	    {
		cheeseFree = false;
	    }
	}


	else if (level [1] == true)

	    {

		if (xPos >= 80 && xPos <= 168 && yPos <= 563 && yPos >= 520)  //  g.drawImage (cheese, 80, 520, null); 80x43
		{
		    cheeseFree = false;
		}

		if (xPos >= 580 && xPos <= 610 && yPos <= 110 && yPos >= 75 && teleportFree == true) //  g.drawImage (teleportIcon, 580, 80, null); 30x30 // detects pick up of teleport power up

		    {
			teleportFree = false;
			teleportPower = 1;
		    }

	    }


	else if (level [2] == true)
	{

	    if (xPos >= 100 && xPos <= 135 && yPos <= 540 && yPos >= 505 && jumpFree == true) //  g.drawImage (jumpIcon, 100, 510, null);
	    {
		jumpFree = false;
		jumpPower = 1;
		jumpFree2 = true;
	    }
	    if (xPos >= 810 && xPos <= 840 && yPos == 455) //g.drawImage (jumpIcon, maxX - 90, 470, null);

		{
		    jumpFree2 = false;
		    jumpPower = 1;
		}
	    if (xPos >= 675 && xPos <= 763 && yPos >= 105 && yPos <= 148)  //g.drawImage (cheese, 675, 105, null);

		{
		    cheeseFree = false;
		}
	}
    }


    public void run ()
    {

	// lower ThreadPriority
	Thread.currentThread ().setPriority (Thread.MIN_PRIORITY);

	// run a long while (true) this means in our case "always"
	while (true) //level one
	{
	    if (hasSpawned == false) //spawns mouse upon new level
	    {
		spawnPoint ();
		hasSpawned = true;
	    }
	    levelComplete ();

	    collisionDetection (); //calls the collision detection method

	    if (xSpeed > 0)  //decelerates movement after press
	    {
		xSpeed--;
	    }
	    if (xSpeed < 0)
	    {
		xSpeed++;
	    }
	    if (ySpeed < 0)
	    {
		ySpeed++;
	    }

	    if (upKeyDown == true) //movement with arrow keys
	    {
		if (midJump == false)
		{
		    ySpeed = -19;
		    midJump = true;
		}

	    }
	    if (leftKeyDown == true)
	    {
		xSpeed = -5;
	    }
	    if (rightKeyDown == true)
	    {
		xSpeed = 5;
	    }
	    if (teleportKeyDown == true && teleportPower >= 1 && teleportTimer > 50) // teleport

		{
		    teleportTimer = 0;
		    teleportPower--;
		    if (facingRight == true)
		    {
			xPos += 150;
		    }
		    else if (facingLeft == true)
		    {
			xPos -= 150;
		    }
		}
	    if (jumpKeyDown == true && jumpPower >= 1) //power jump
	    {

		jumpPower--;
		ySpeed -= 20;
	    }
	    teleportTimer++;
	    xPos = xPos + xSpeed; //moves horizontal position based on x speed
	    yPos = yPos + ySpeed; //moves vertical position based on y speed

	    yPos += 5; //effects of gravity
	    repaint ();
	    if (timerRunning == true) //keeps track of elapsed time
	    {
		timer++;
	    }

	    minutes = ((timer * 17) / (60 * 1000)); //calculates the minutes and seconds according to the 17 ms delay
	    seconds = ((timer * 17 / 1000) % 60); //calculates seconds
	    pickUpDetect (); //calls method that detects power ups being picked up
	    delay (17); //stops thread for 17 milliseconds

	    // set ThreadPriority to maximum value
	    Thread.currentThread ().setPriority (Thread.MAX_PRIORITY);
	}
    }


    /** Update - Method, implements double buffering */
    public void update (Graphics g)
    {

	// initialize buffer
	if (dbImage == null)
	{
	    dbImage = createImage (this.getSize ().width, this.getSize ().height);
	    dbg = dbImage.getGraphics ();
	}


	// clear screen in background
	dbg.setColor (getBackground ());
	dbg.fillRect (0, 0, this.getSize ().width, this.getSize ().height);

	// draw elements in background
	dbg.setColor (getForeground ());
	paint (dbg);

	// draw image on the screen
	g.drawImage (dbImage, 0, 0, this);


    }


    public void actionPerformed (ActionEvent evt)  //checks when start button is pressed
    {
	if (evt.getSource () == start)
	{
	    remove (start);
	    level [0] = true;
	    startScreen = false;
	    spawnPoint ();
	}
    }


    boolean inAir = false;
    public boolean keyDown (Event e, int key)  //detects when key is pressed
    {

	if (key == Event.LEFT || key == 97)
	{
	    leftKeyDown = true;
	    facingLeft = true;
	    facingRight = false;
	}


	if (key == Event.RIGHT || key == 100)
	{
	    rightKeyDown = true;
	    facingRight = true;
	    facingLeft = false;
	    timerRunning = true;          //starts timer when first key is pressed

	}


	if (key == Event.UP || key == 119)
	{
	    upKeyDown = true;


	}


	if (key == 122)
	{
	    teleportKeyDown = true;
	}




	if (key == 104) 
	{
	    instructionScreen = true;
	}


	if (key == 120)
	{
	    jumpKeyDown = true;
	}



	return true;

    }


    public boolean keyUp (Event f, int key)  //detects when key is released
    {

	if (key == Event.LEFT || key == 97)
	{
	    leftKeyDown = false;

	}


	if (key == Event.RIGHT || key == 100)
	{

	    rightKeyDown = false;
	}


	if (key == Event.UP || key == 119)
	{
	    upKeyDown = false;
	}


	if (key == 122)
	{
	    teleportKeyDown = false;
	}


	if (key == 104)
	{
	    instructionScreen = false;
	}


	if (key == 120)
	{
	    jumpKeyDown = false;
	}


	// DON'T FORGET (although it has no meaning here)
	return true;

    }


    public void paint (Graphics g)
    {
	// Place the body of the drawing method here

	// set color

	if (startScreen == true)
	{
	    g.drawImage (titleScreen, 0, 0, null);
	}
	else if (endScreen == true) //displays end screen when game is done
	{
	    levelComplete.play ();
	    g.setFont (font2);
	    g.drawImage (endingScreen, 0, 0, null);
	    g.drawString (Integer.toString (minutes) + ":" + Integer.toString (seconds) + ":" + Integer.toString ((timer * 17) % 1000), 450, 270);

	    g.drawString (Integer.toString (deathCount), 445, 435);

	}
	else
	{
	    if (level [0] == true) //level one
	    {
		g.drawImage (mapOne, 0, 0, null);


		if (cheeseFree == true) //removes cheese after taken
		{
		    g.drawImage (cheese, 300, 300, null);
		}

		else //draws cheese
		{
		    g.drawImage (cheese, xPos - 50, yPos - 40, null);
		}
	    }

	    else if (level [1] == true)
	    {
		g.drawImage (mapTwo, 0, 0, null);


		if (cheeseFree == true) //removes cheese after taken
		{
		    g.drawImage (cheese, 80, 520, null);
		}

		else //draws cheese
		{
		    g.drawImage (cheese, xPos - 50, yPos - 40, null);
		}
		if (teleportFree == true) //draws tele
		{
		    g.drawImage (teleportIcon, 580, 80, null);
		}




	    }


	    else if (level [2] == true)
	    {
		g.drawImage (mapThree, 0, 0, null);


		if (jumpFree == true)
		{
		    g.drawImage (jumpIcon, 100, 510, null);
		}
		if (jumpFree2 == true)
		{
		    g.drawImage (jumpIcon, maxX - 90, 470, null);
		    //  g.drawImage (jumpIcon, 100, 510, null);

		}

		if (cheeseFree == true) //removes cheese after taken
		{
		    g.drawImage (cheese, 675, 105, null);
		}

		else //draws cheese
		{
		    g.drawImage (cheese, xPos - 50, yPos - 40, null);
		}

	    }


	    if (facingLeft == true) //facing left
	    {
		if (teleportTimer < 20) //teleport animation
		{
		    g.drawImage (teleport, xPos - 60, yPos - 43, null);
		}
		else if (climbing == true) //climbing left
		{
		    g.drawImage (mouseRunLeft, xPos - 50, yPos - 43, null);

		}
		else if (midJump == true) //jumping left
		{
		    g.drawImage (mouseJumpLeft, xPos - 50, yPos - 43, null);

		}
		else if (leftKeyDown == true) //running left
		{
		    g.drawImage (mouseRunLeft, xPos - 50, yPos - 43, null);

		}


		else //standing still left
		{
		    g.drawImage (mouseLeft, xPos - 50, yPos - 43, null);

		}
	    }

	    else if (facingRight == true) //facing right
	    {
		if (teleportTimer < 20) //teleport animation
		{
		    g.drawImage (teleport, xPos - 50, yPos - 43, null);

		}
		else if (climbing == true) //climbing right
		{
		    g.drawImage (mouseRunRight, xPos - 50, yPos - 43, null);

		}

		else if (midJump == true) //jumping right
		{
		    g.drawImage (mouseJumpRight, xPos - 50, yPos - 43, null);

		}
		else if (rightKeyDown == true) //running right
		{
		    g.drawImage (mouseRunRight, xPos - 50, yPos - 43, null);

		}

		else
		    //standing still right
		    {
			g.drawImage (mouseRight, xPos - 50, yPos - 43, null);
		    }
	    }
	    g.setFont (font);

	    g.setColor (Color.white);
	    g.drawImage (statusBar, 0, 0, null);
	    g.drawString (Integer.toString (currentLevel + 1), 65, 25);
	    g.drawString (Integer.toString (teleportPower), 185, 25);
	    g.drawString (Integer.toString (jumpPower), 290, 25);
	    g.drawString (Integer.toString (deathCount), 865, 25);

	    g.drawString ("Press H for instructions", 555, 25);

	    if (seconds < 10) //displays the time remaining
	    {
		g.drawString (Integer.toString (minutes) + ":0" + Integer.toString (seconds) + ":" + Integer.toString ((timer * 17) % 1000), 380, 25);
	    }
	    else
	    {
		g.drawString (Integer.toString (minutes) + ":" + Integer.toString (seconds) + ":" + Integer.toString ((timer * 17) % 1000), 380, 25);

	    }


	    if (instructionScreen == true) //shows instruction screen when "h" is pressed
	    {
		g.drawImage (instructions, 0, 0, null);
		g.drawImage (mouseRunRight, 250, 200, null);
		g.drawImage (cheese, 550, 250, null);

		delay (50);

	    }
	}
    } // paint method
} // MovingBallApplet5 class


