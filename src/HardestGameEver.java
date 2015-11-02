import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.swing.JApplet;


public class HardestGameEver extends JApplet
{
	BufferedImage bgImage;
	static BufferedImage[] lvl;
	static BufferedImage startScreen, endScreen;
	static Background bg;
	public static int numLevels, level;
	public void init()
	{
		level = -1;
		numLevels = 50;
		lvl = new BufferedImage[numLevels];
		try
		{
			bgImage = ImageIO.read(new URL(getCodeBase(), "background.png"));
		}
		catch(IOException e) {}
		int curIm = 0;
		while(true)
		{
			try
			{
				lvl[curIm] = ImageIO.read(new URL(getCodeBase(), "level" + curIm + ".png"));
			}
			catch(IOException e) {numLevels = curIm; break;}
			curIm++;
		}
		try
		{
			startScreen = ImageIO.read(new URL(getCodeBase(), "startScreen.png"));
		}
		catch(IOException e) {}
		try
		{
			endScreen = ImageIO.read(new URL(getCodeBase(), "endScreen.png"));
		}
		catch(IOException e) {}
		resize(600,400);
		bg = new Background(getCodeBase());
		bg.setBackgroundImage(bgImage);
		bg.setLevelImage(startScreen);
		//bg.setLevelImage(lvl[0]);
		bg.setLocation(0,0);
		bg.setSize(600,400);
		bg.setFocusable(true);
		getContentPane().setLayout(null);
		this.add(bg);
	}
	
	public static void nextLevel()
	{
		if(level + 1 == numLevels) {bg.setLevelImage(endScreen); bg.repaint();}
		else bg.setLevelImage(lvl[level + 1]);
		level++;
	}
	
	public void paint(Graphics g)
	{
		
	}
	
}
