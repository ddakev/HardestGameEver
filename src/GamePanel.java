import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;


public class GamePanel extends JPanel implements KeyListener, Runnable {
	public Player pl;
	boolean upPressed,downPressed,leftPressed,rightPressed;
	Thread t;
	ArrayList<ArrayList<CheckPoint>> cps;
	LevelBoundaries[] lvl;
	ArrayList<ArrayList<Enemy>> es;
	int lastTouched;
	int deaths;
	JLabel deathsLabel;
	JLabel levelLabel;
	JLabel timeLabel;
	Rectangle statusBar;
	public static int level, numLevels;
	GamePanel(Object codeBase)
	{
		super();
		this.setLayout(new GridLayout());
		level = 0;
		numLevels = HardestGameEver.numLevels;
		deaths = 0;
		upPressed = downPressed = leftPressed = rightPressed = false;
		t = new Thread(this);
		setOpaque(false);
		addKeyListener(this);
		lastTouched = 0;
		statusBar = new Rectangle(0,0,600,50);
		timeLabel = new JLabel("0:00:00");
		timeLabel.setVisible(false);
		timeLabel.setForeground(Color.white);
		//timeLabel.setLocation(520,125);
		timeLabel.setHorizontalAlignment(SwingConstants.LEFT);
		timeLabel.setVerticalAlignment(SwingConstants.TOP);
		timeLabel.setFont(new Font("Arial", Font.BOLD, 30));
		this.add(timeLabel);
		deathsLabel = new JLabel("DEATHS: "+ 0);
		deathsLabel.setVisible(false);
		deathsLabel.setForeground(Color.white);
		//deathsLabel.setLocation(240, 25);
		deathsLabel.setHorizontalAlignment(SwingConstants.CENTER);
		deathsLabel.setVerticalAlignment(SwingConstants.TOP);
		deathsLabel.setFont(new Font("Arial", Font.BOLD, 30));
		this.add(deathsLabel);
		levelLabel = new JLabel("1 / " + numLevels);
		levelLabel.setVisible(false);
		levelLabel.setForeground(Color.white);
		//levelLabel.setLocation(520,125);
		levelLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		levelLabel.setVerticalAlignment(SwingConstants.TOP);
		levelLabel.setFont(new Font("Arial", Font.BOLD, 30));
		this.add(levelLabel);
		
		cps = new ArrayList<ArrayList<CheckPoint>>();
		lvl = new LevelBoundaries[numLevels];
		es = new ArrayList<ArrayList<Enemy>>();
		
		int curFile = 0;
		while(true)
		{
			try
			{
				URL fileLocation = new URL(codeBase.toString() + "level" + curFile + ".txt");
			    InputStream is = fileLocation.openStream();
			    InputStreamReader isr = new InputStreamReader(is);
			    BufferedReader reader = new BufferedReader(isr);
				String line = null;
				line = reader.readLine();
				int n = Integer.parseInt(line);
				lvl[curFile] = new LevelBoundaries();
				for(int i = 0; i <= n-1; i++)
				{
					line = reader.readLine();
					String[] parts = line.split("\\s");
					lvl[curFile].addLine(Integer.parseInt(parts[0]),Integer.parseInt(parts[1]),Integer.parseInt(parts[2]),Integer.parseInt(parts[3]),Integer.parseInt(parts[4]));
				}
				line = reader.readLine();
				n = Integer.parseInt(line);
				cps.add(new ArrayList<CheckPoint>());
				for(int i = 0; i <= n-1; i++)
				{
					line = reader.readLine();
					String[] parts = line.split("\\s");
					cps.get(curFile).add(new CheckPoint(Integer.parseInt(parts[0]),Integer.parseInt(parts[1]),Integer.parseInt(parts[2]),Integer.parseInt(parts[3])));
				}
				line = reader.readLine();
				n = Integer.parseInt(line);
				es.add(new ArrayList<Enemy>());
				for(int i = 0; i <= n-1; i++)
				{
					line = reader.readLine();
					int m = Integer.parseInt(line);
					for(int j = 0; j <= m-1; j++)
					{
						line = reader.readLine();
						String[] parts = line.split("\\s");
						if(j == 0) es.get(curFile).add(new Enemy(Integer.parseInt(parts[0]),Integer.parseInt(parts[1])));
						else es.get(curFile).get(i).addPath(Integer.parseInt(parts[0]),Integer.parseInt(parts[1]));
					}
				}
				is.close();
			}
			catch(IOException e) {break;}
			curFile++;
		}
		
		pl = new Player(cps.get(0).get(0).getX()+cps.get(0).get(0).getWidth()/2,cps.get(0).get(0).getY()+cps.get(0).get(0).getHeight()/2);
	}
	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		if(HardestGameEver.level == HardestGameEver.numLevels)
		{
			this.remove(levelLabel);
			this.remove(timeLabel);
			deathsLabel.setHorizontalAlignment(SwingConstants.CENTER);
			deathsLabel.setVerticalAlignment(SwingConstants.BOTTOM);
			stop();
		}
		if(HardestGameEver.level >= 0 && HardestGameEver.level < HardestGameEver.numLevels)
		{
			Graphics2D g2 = (Graphics2D) g;
			g2.setColor(new Color(60,60,60));
			g2.fill(statusBar);
			deathsLabel.setVisible(true);
			levelLabel.setVisible(true);
			timeLabel.setVisible(true);
			g2.setColor(Color.black);
			g2.fill(pl.getOuterRectangle());
			g2.setColor(Color.red);
			g2.fill(pl.getInnerRectangle());
			if(level == numLevels) return;
			for(int i = 0; i < es.get(level).size(); i++)
			{
				g2.setColor(Color.black);
				g2.fillOval(es.get(level).get(i).getX(), es.get(level).get(i).getY(), es.get(level).get(i).getSize(), es.get(level).get(i).getSize());
				g2.setColor(Color.blue);
				g2.fillOval(es.get(level).get(i).getX()+es.get(level).get(i).getStroke(), es.get(level).get(i).getY()+es.get(level).get(i).getStroke(), es.get(level).get(i).getSize()-2*es.get(level).get(i).getStroke(), es.get(level).get(i).getSize()-2*es.get(level).get(i).getStroke());
			}
		}
	}
	public String getTime(long milis)
	{
		String s = "";
		milis /= 1000;
		s = milis / 3600 + ":";
		if((milis/60)%60 < 10) s += "0" + (milis/60)%60 + ":";
		else s += (milis/60)%60 + ":";
		if(milis%60 < 10) s += "0" + milis%60;
		else s += milis%60;
		return s;
	}
	public void start()
	{
		t.start();
	}
	public void stop()
	{
		t = null;
	}
	public void run()
	{
		long bgTime = 0;
		long startTime = System.currentTimeMillis();
		while(Thread.currentThread() == t)
		{
			if(HardestGameEver.level >= 0)
			{
				if(bgTime == 0) bgTime = System.currentTimeMillis();
				timeLabel.setText(getTime(System.currentTimeMillis() - bgTime));
				int x=0,y=0;
				if(upPressed) y-=1;
				if(downPressed) y+=1;
				if(rightPressed) x+=1;
				if(leftPressed) x-=1;
				if(x != 0 || y != 0) pl.move(x, y, lvl[level]);
				for(int i = 0; i < es.get(level).size(); i++)
				{
					es.get(level).get(i).move();
					if(pl.collideWith(es.get(level).get(i)))
					{
						deaths++;
						pl.goTo(cps.get(level).get(lastTouched));
						deathsLabel.setText("DEATHS: " + deaths);
					}
				}
				for(int i = cps.get(level).size()-1; i > lastTouched; i--)
				{
					if(pl.touches(cps.get(level).get(i))) {lastTouched = i; break;}
				}
				if(lastTouched == cps.get(level).size()-1)
				{
					HardestGameEver.nextLevel();
					level++;
					levelLabel.setText(level+1 + " / " + numLevels);
					if(level == numLevels)
					{
						//System.out.println("You Won! Deaths: " + deaths);
						repaint();
						//stop();
					}
					else
					{
						lastTouched = 0;
						pl.goTo(cps.get(level).get(0));
					}
				}
			}
			this.repaint();
			try
			{
				startTime += 40;
				Thread.sleep(Math.max(0,startTime - System.currentTimeMillis()));
			}
			catch (InterruptedException e) {break;}
		}
	}
	public void keyPressed(KeyEvent e)
	{
		if(e.getKeyCode() == KeyEvent.VK_UP) {upPressed = true;}
		if(e.getKeyCode() == KeyEvent.VK_DOWN) {downPressed = true;}
		if(e.getKeyCode() == KeyEvent.VK_LEFT) {leftPressed = true;}
		if(e.getKeyCode() == KeyEvent.VK_RIGHT) {rightPressed = true;}
		e.consume();
	}
	public void keyReleased(KeyEvent e)
	{
		if(e.getKeyCode() == KeyEvent.VK_UP) {upPressed = false;}
		if(e.getKeyCode() == KeyEvent.VK_DOWN) {downPressed = false;}
		if(e.getKeyCode() == KeyEvent.VK_LEFT) {leftPressed = false;}
		if(e.getKeyCode() == KeyEvent.VK_RIGHT) {rightPressed = false;}
		e.consume();
	}
	public void keyTyped(KeyEvent e)
	{
		
	}
}
