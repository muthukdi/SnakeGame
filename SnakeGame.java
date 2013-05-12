import java.awt.*;
import javax.swing.*;
import java.awt.event.*;

public class SnakeGame extends JPanel implements ActionListener, KeyListener, MouseListener, FocusListener
{
	Timer timer;
	int startX, startY;
	String direction;
	Point[] snake;
	int lengthOfSnake;
	Point[] items;
	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		drawGrid(g);
		drawSnake(g);
		drawItems(g);
	}
	public void mousePressed(MouseEvent e)
	{
		requestFocus();
	}
	public void mouseReleased(MouseEvent e) {}
	public void mouseClicked(MouseEvent e) {}
	public void mouseEntered(MouseEvent e) {}
	public void mouseExited(MouseEvent e) {}
	public void keyPressed(KeyEvent e)
	{
		int key = e.getKeyCode();
		if (key == KeyEvent.VK_DOWN)
		{
			if (!direction.equals("Upward"))
			{
				direction = "Downward";
			}
			else
			{
				System.out.println("You are not allowed to move downward right now!");
			}
		}
		else if (key == KeyEvent.VK_UP)
		{
			if (!direction.equals("Downward"))
			{
				direction = "Upward";
			}
			else
			{
				System.out.println("You are not allowed to move upward right now!");
			}
		}
		else if (key == KeyEvent.VK_LEFT)
		{
			if (!direction.equals("Forward"))
			{
				direction = "Backward";
			}
			else
			{
				System.out.println("You are not allowed to move backward right now!");
			}
		}
		else if (key == KeyEvent.VK_RIGHT)
		{
			if (!direction.equals("Backward"))
			{
				direction = "Forward";
			}
			else
			{
				System.out.println("You are not allowed to move forward right now!");
			}
		}
	}
	public void keyReleased(KeyEvent e) {}
	public void keyTyped(KeyEvent e) {}
	public void focusGained(FocusEvent e)
	{	
		timer.start();
	}
	public void focusLost(FocusEvent e)
	{
		timer.stop();
	}
	public void actionPerformed(ActionEvent e)
	{
		updateSnakeArray();
		if (snake[0].getX() == -10 || snake[0].getX() == 400)
		{
			System.out.println("Boundary reached!");
			System.exit(0);
		}
		else if (snake[0].getY() == -10 || snake[0].getY() == 400)
		{
			System.out.println("Boundary reached!");
			System.exit(0);
		}
		for (int i = 1; i < lengthOfSnake; i++)
		{
			if (snake[0].getX() == snake[i].getX() && snake[0].getY() == snake[i].getY())
			{
				System.out.println("Self intersection detected!");
				System.exit(0);
			}
		}
		for (int i = 0; i < items.length; i++)
		{
			if (snake[0].getX() == items[i].getX() && snake[0].getY() == items[i].getY())
			{
				System.out.println("Item collected!");
				snake[lengthOfSnake] = new Point(-10,-10);
				lengthOfSnake++;
				items[i] = new Point(-10,-10);
			}
		}
		repaint();
	}
	public void drawGrid(Graphics g)
	{
		g.setColor(Color.GRAY);
		for (int j = 0; j < 400; j += 10)
		{
			for (int i = 0; i < 400; i += 10)
			{
				g.drawRect(i, j, 10, 10);
			}
		}
	}
	public void drawSnake(Graphics g)
	{
		g.setColor(Color.YELLOW);
		for (int i = 0; i < lengthOfSnake; i++)
		{
			g.fillRect((int)snake[i].getX(), (int)snake[i].getY(), 10, 10);
		}
	}
	public void drawItems(Graphics g)
	{
		g.setColor(Color.RED);
		for (int i = 0; i < items.length; i++)
		{
			g.fillRect((int)items[i].getX(), (int)items[i].getY(), 10, 10);
		}
	}
	public void updateSnakeArray()
	{	
		for (int i = lengthOfSnake-1; i > 0; i--)
		{
			snake[i] = snake[i-1];
		}
		if (direction.equals("Forward"))
		{
			int x = (int)snake[0].getX()+10;
			int y = (int)snake[0].getY();
			snake[0] = new Point(x, y);
		}
		else if (direction.equals("Backward"))
		{
			int x = (int)snake[0].getX()-10;
			int y = (int)snake[0].getY();
			snake[0] = new Point(x, y);
		}
		else if (direction.equals("Upward"))
		{
			int x = (int)snake[0].getX();
			int y = (int)snake[0].getY()-10;
			snake[0] = new Point(x, y);
		}
		else if (direction.equals("Downward"))
		{
			int x = (int)snake[0].getX();
			int y = (int)snake[0].getY()+10;
			snake[0] = new Point(x, y);
		}
	}
	public void initializeSnakeArray()
	{
		for (int i = 0; i < lengthOfSnake; i++)
		{
			snake[i] = new Point(startX-10*i, startY);
		}
	}
	public void initializeItemsArray()
	{
		for (int i = 0; i < items.length; i++)
		{
			Point position = null;
			boolean validPosition = false;
			while (!validPosition)
			{
				int x = ((int)(391*Math.random())/10)*10;
				int y = ((int)(391*Math.random())/10)*10;
				position = new Point(x,y);
				for (int j = 0; j < lengthOfSnake; j++)
				{
					if (position.getX() == snake[j].getX() && position.getY() == snake[j].getY())
					{
						continue;
					}
					else if (j == lengthOfSnake-1)
					{
						items[i] = position;
						validPosition = true;
					}
				}
			}
		}
	}
	public SnakeGame()
	{
		timer = new Timer(100, this);
		setBackground(Color.BLUE);
		addMouseListener(this);
		addKeyListener(this);
		addFocusListener(this);
		startX = 10*5;
		startY = 10*2;
		direction = "Forward";
		snake = new Point[30];
		items = new Point[20];
		lengthOfSnake = 4;
		initializeSnakeArray();
		initializeItemsArray();
		JFrame frame = new JFrame("Snake Game");
		frame.setContentPane(this);
		frame.setSize(406,428);
		frame.setLocation(300,100);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);
		frame.setVisible(true);
		System.out.println(getWidth());
		System.out.println(getHeight());
	}
	public static void main(String[] args)
	{
		new SnakeGame();
	}
	
}