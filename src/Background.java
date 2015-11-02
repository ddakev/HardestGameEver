import java.awt.BorderLayout;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;


public class Background extends JPanel implements MouseListener {
	BufferedImage background;
	BufferedImage level;
	GamePanel gp;
	Background(Object codeBase)
	{
		super();
		addMouseListener(this);
		gp = new GamePanel(codeBase);
		gp.setFocusable(true);
		gp.setLocation(0,0);
		gp.setSize(600,400);
		setLayout(new BorderLayout());
		this.add(gp);
		this.setFocusable(false);
		addKeyListener(gp);
		gp.requestFocus();
		gp.start();
	}
	public void startGame()
	{
		//removeMouseListener(this);
		HardestGameEver.nextLevel();
		
	}
	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;
		g2.drawImage(getBackgroundImage(), 0, 0, this);
		g2.drawImage(getLevelImage(), 0, 0, this);
	}
	public void setBackgroundImage(BufferedImage bg)
	{
		this.background = bg;
	}
	public Image getBackgroundImage()
	{
		return this.background;
	}
	public void setLevelImage(BufferedImage lvl)
	{
		this.level = lvl;
		repaint();
	}
	public Image getLevelImage()
	{
		return this.level;
	}
	public void mouseClicked(MouseEvent e)
	{
		int x = e.getX();
		int y = e.getY();
		Rectangle button = new Rectangle(215,260,145,50);
		if(button.contains(new Point(x,y))) {removeMouseListener(this); startGame();}
	}
	public void mouseEntered(MouseEvent e)
	{
		
	}
	public void mouseExited(MouseEvent e)
	{
		
	}
	public void mousePressed(MouseEvent e)
	{
		
	}
	public void mouseReleased(MouseEvent e)
	{
		
	}
}
