import java.awt.Point;
import java.util.ArrayList;

public class Enemy {
	int x,y,size,stroke;
	int speed;
	ArrayList<Point> path;
	int curPath;
	public Enemy(int initX, int initY)
	{
		path = new ArrayList<Point>();
		path.add(new Point(initX, initY));
		x = initX;
		y = initY;
		size = 20;
		stroke = 3;
		speed = 7;
		curPath = 0;
	}
	public int getX()
	{
		return x;
	}
	public int getY()
	{
		return y;
	}
	public int getSize()
	{
		return size;
	}
	public int getStroke()
	{
		return stroke;
	}
	public void addPath(int x, int y)
	{
		path.add(new Point(x,y));
	}
	public void move()
	{
		if(path.size() == 1) return;
		int x1 = (int)path.get(curPath).getX();
		int y1 = (int)path.get(curPath).getY();
		int p = curPath + 1;
		if(p == path.size()) p = 0;
		int x2 = (int)path.get(p).getX();
		int y2 = (int)path.get(p).getY();
		if(x1 != x2 && y1 != y2)
		{
			double ang = Math.atan((double)Math.abs(y2-y1)/(double)(Math.abs(x2-x1)));
			x += (int)Math.cos(ang)*speed;
			y += (int)Math.sin(ang)*speed;
		}
		else if(x1 == x2)
		{
			if(y1 > y2) y -= speed;
			else y += speed;
		}
		else if(y1 == y2)
		{
			if(x1 > x2) x -= speed;
			else x += speed;
		}
		if((x1 < x2 && x > x2) || (x1 > x2 && x < x2) || (y1 < y2 && y > y2) || (y1 > y2 && y < y2))
		{
			x = x2;
			y = y2;
			curPath++;
			if(curPath == path.size()) curPath = 0;
		}
	}
}
