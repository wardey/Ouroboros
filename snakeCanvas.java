/*snakeCanvas.java
 * Kalp Shah & Shichen Chang
 * January 15, 2014
 */
package snake;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.net.URL;
import java.util.LinkedList;
import java.util.Random;
import snake.Direction;

/**
 * draws everything in game screen, refreshes game, checks for input
 * 
 */
public class snakeCanvas extends Canvas implements Runnable, KeyListener
{       
	
        //dimensions for window
        private final int BOX_HEIGHT = 15;
	private final int BOX_WIDTH = 15;
	private final int GRID_WIDTH = 25;
	private final int GRID_HEIGHT = 25;
	
	private LinkedList<Point> snake; //list of points that make up the snake body
	private Point fruit;    //a point that represents the fruit
	private int direction = Direction.NO_DIRECTION; //current direction of the snake
	
	private Thread runThread; //new thread 
	public static int delay = 80;
        public static boolean isInMenu = true; //whether in menu or not
        public static boolean endGame = false; //whether player lost or not
        public static boolean restart = false; //whether player selected to start newgame or not
	private Image menuImage = null; //field for menu image
        
        private int score = 0; //variable for keeping track of score
    
        /**
         * paints all parts of the game onto the window
         * 
         */
        @Override
        public void paint(Graphics g)
	{
            //initialize thread
            if (runThread == null) 
               {
                       this.setPreferredSize(new Dimension(500, 480));
                       this.addKeyListener(this);
                       runThread = new Thread(this);
                       runThread.start();
               }
            
            //paints menu screen
            if(isInMenu) 
            {
                //Draw menu
               drawMenu(g);
            }
            
             //paints end game screen if 
            else if(endGame)
            {
                //draw end game screen
                DrawEndGame(g);
            }
            else if (restart)
            {
                //new game
                restart = false;
                
                GenerateDefaultSnake();
                repaint();
                
            }
            
            else
            {
                //Draw everything else
                if(snake == null)
                {
                    snake = new LinkedList<Point>();
                    GenerateDefaultSnake();
                    PlaceFruit();
                }
                                
                DrawFruit(g);
		DrawGrid(g);
		DrawSnake(g);
		DrawScore(g);
            }
        }
        /**
         * draws the end game screen if player died
         *  
         */
        public void DrawEndGame(Graphics g)
        {
            //creates a new image object
            BufferedImage endGameImage = new BufferedImage(500,480,BufferedImage.TYPE_INT_ARGB);
            Graphics endGameGraphics = endGameImage.getGraphics();
            endGameGraphics.setColor(Color.BLACK);
            
            //creates end game screen
            Font largeFont = new Font("Serif",Font.PLAIN,30);
            endGameGraphics.setFont(largeFont);
            endGameGraphics.drawString("You lost", 190,160);           
            endGameGraphics.drawString("Your score was: "+ score,150  , 190);
            endGameGraphics.drawString("Press \"Space\" to play again", 95, 220);
            
            //draws end game screen
            g.drawImage(endGameImage, 0, 0, this);
        }
        
        /**
         * draws the menu screen at the start of the game or during the pause
         * 
         */
        public void drawMenu(Graphics g)
        {
            //if menu image is not initialized
            if(this.menuImage == null)
            {
                    try 
                {
                    //imports image 
                    URL imagePath = this.getClass().getResource("title.png");
                    this.menuImage = Toolkit.getDefaultToolkit().getImage(imagePath);
                }
                catch (Exception e)
                {
                    //If the image does not exist
                    e.printStackTrace();
                }
            }
            //draws image
            g.drawImage(menuImage, 0 , 0 , 500 , 480 , this);
        }    
        
        @Override
        /**
         * creates a new buffered image and draws it offscreen
         */
        public void update (Graphics g)
        {
            //creates new buffered image object
            Graphics offScreenGraphics;
            BufferedImage offscreen= null;
            Dimension d = this.getSize();
            
            //draws image off screen
            offscreen = new BufferedImage (d.width, d.height, BufferedImage.TYPE_INT_ARGB);
            offScreenGraphics = offscreen.getGraphics();
            offScreenGraphics.setColor(this.getBackground());
            offScreenGraphics.fillRect(0 , 0 , d.width , d.height);
            offScreenGraphics.setColor(this.getForeground());
            paint(offScreenGraphics);
            
            //flip the image onto the screen
            g.drawImage(offscreen, 0 , 0 , this);
            
        }
	
        /**
         * creates the default snake at the start of a game
         */
	public void GenerateDefaultSnake()
	{
                //clears previous score and snake list
		score = 0;
		snake.clear();
		
                //default points for the snake
		snake.add(new Point(10,2));
                snake.add(new Point(10,1));
                snake.add(new Point(10,0));
                //sets snake to stationary
		direction = Direction.NO_DIRECTION;
	}
	

	/**
         * adds new points to the head of the snake while deleting the last point
         * based on the direction of the snake to simulate movement
         * detects collision of snake with various surroundings 
         */
	public void Move()
	{
            //does not run method if snake is stationary
            if(direction==Direction.NO_DIRECTION)
                return;
            else
            {
                //assigns first point of the snake to be the head
		Point head = snake.peekFirst();
                //creates new point and assigns it to be head, or first point
		Point newPoint = head;
                //puts the point depending on the direction the snake is moving
		switch (direction) {
		case Direction.NORTH:
			newPoint = new Point(head.x, head.y - 1);
			break;
		case Direction.SOUTH:
			newPoint = new Point(head.x, head.y + 1);
			break;
		case Direction.WEST:
			newPoint = new Point(head.x - 1, head.y);
			break;
		case Direction.EAST:
			newPoint = new Point(head.x + 1, head.y);
			break;
		}
                    //removes the last point in the snake
                    snake.remove(snake.peekLast());
		
                //the snake has hit fruit
		if (newPoint.equals(fruit))
		{
                        //increase score
			score+=10;
			//adds a point to the snake based on current direction
			Point addPoint = (Point) newPoint.clone();
			
			switch (direction) {
			case Direction.NORTH:
				newPoint = new Point(head.x, head.y - 1);
				break;
			case Direction.SOUTH:
				newPoint = new Point(head.x, head.y + 1);
				break;
			case Direction.WEST:
				newPoint = new Point(head.x - 1, head.y);
				break;
			case Direction.EAST:
				newPoint = new Point(head.x + 1, head.y);
				break;
			}
			//puts addPoint at front of snake
			snake.push(addPoint);
			PlaceFruit();
		//detecs collision with walls or snake itself	
		}
		else if (newPoint.x < 0 || newPoint.x > (GRID_WIDTH - 1))
		{
			//snake hit wall, end game
                        endGame = true;
			return;
		}
		else if (newPoint.y < 0 || newPoint.y > (GRID_HEIGHT - 1))
		{
			//snake hit wall, end game
                        endGame = true;
			return;
		}
		else if (snake.contains(newPoint))
		{
			//snake ate itself, end game
                        endGame = true;
			return;

		}
		//puts newPoint at front of snake
		snake.push(newPoint);
            }
        }
	
        /**
         * displays the score on the screen
         */
	public void DrawScore(Graphics g)
	{
		g.drawString("Score: " + score, 0, BOX_HEIGHT * GRID_HEIGHT + 12);
	}
	
        /**
         * draws the rectangle game area 
         * draws borders of the game area
         */
	public void DrawGrid(Graphics g)
	{
		//drawing an outside rect
		g.drawRect(0, 0, GRID_WIDTH * BOX_WIDTH, GRID_HEIGHT * BOX_HEIGHT);
		//drawing the vertical lines
                g.drawLine(0, 0, 0, BOX_HEIGHT * GRID_HEIGHT);
                g.drawLine(GRID_WIDTH * BOX_WIDTH, 0, GRID_WIDTH * BOX_WIDTH, GRID_HEIGHT*BOX_HEIGHT);
		
		//drawing the horizontal lines
		g.drawLine(0, 0, GRID_WIDTH * BOX_WIDTH, 0);
                g.drawLine(0, GRID_HEIGHT*BOX_HEIGHT, GRID_WIDTH * BOX_WIDTH, GRID_HEIGHT*BOX_HEIGHT);
		
	}
	
        /**
         * draws the snake
         * 
         */
	public void DrawSnake(Graphics g)
	{
		g.setColor(Color.GREEN);
                //for each point in the snake, draw a rectangle at that point to form snake body
		for (Point p : snake)
		{
			g.fillRect(p.x * BOX_WIDTH, p.y * BOX_HEIGHT, BOX_WIDTH, BOX_HEIGHT);
		}
		g.setColor(Color.BLACK);
	}
	
        /**
         * draws the apple
         */
	public void DrawFruit(Graphics g)
	{
		g.setColor(Color.RED);
                //draws a red circle at the coordinates of the apple
		g.fillOval(fruit.x * BOX_WIDTH, fruit.y * BOX_HEIGHT, BOX_WIDTH, BOX_HEIGHT);
		g.setColor(Color.BLACK);
	}

        /**
         * select random coordinates for the apple
         */
	public void PlaceFruit()
	{
            //randomizes x and y coordinates for the apple and makes a new point
		Random rand = new Random();
		int randomX = rand.nextInt(GRID_WIDTH);
		int randomY = rand.nextInt(GRID_HEIGHT);
		Point randomPoint = new Point(randomX, randomY);
            //if the point is inside the snake, make a new one
		while (snake.contains(randomPoint))
		{
			randomX = rand.nextInt(GRID_WIDTH);
			randomY = rand.nextInt(GRID_HEIGHT);
			randomPoint = new Point(randomX, randomY);
		}
                //set the new point to be the apple
		fruit = randomPoint;
	}
	
	@Override
        /**
         * thread that continuously repaints the current screen 
         */
	public void run() {
		while (true)
		{
                    //runs indefinitely
                    repaint();                
                                        
                    if(!isInMenu && !endGame)
                    {
                        Move();
                    }
                    			
                    try
                    {
                            Thread.currentThread();
                            Thread.sleep(delay);
                    }
                    catch (Exception e) 
                    {
                            e.printStackTrace();
                    }
                }	
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
        /**
         * checks keyboard input 
         */
	public void keyPressed(KeyEvent e) {
		switch (e.getKeyCode())
		{
            //arrow keys
		case KeyEvent.VK_UP:
			if (direction != Direction.SOUTH)
				direction = Direction.NORTH;
			break;
		case KeyEvent.VK_DOWN:
			if (direction != Direction.NORTH)
				direction = Direction.SOUTH;
			break;
		case KeyEvent.VK_RIGHT:
			if (direction != Direction.WEST)
				direction = Direction.EAST;
			break;
		case KeyEvent.VK_LEFT:
			if (direction != Direction.EAST)
				direction = Direction.WEST;
			break;
            //enter key causes game to leave menu 
                case KeyEvent.VK_ENTER:
                        if (isInMenu)
                        {    
                            isInMenu=false;
                            repaint();
                        }
                        break;
            //p pauses the game and goes into menu    
                case KeyEvent.VK_P:
                        if (!isInMenu)
                        {
                            isInMenu=true;
                            
                        }
            //spacebar resets the game if player died
                case KeyEvent.VK_SPACE:
                        if(endGame)
                        {
                            endGame = false;
                            GenerateDefaultSnake();
                            repaint();
                        }
                        break;  
                    
                
               
		}
		
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}
}
