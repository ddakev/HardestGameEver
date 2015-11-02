import java.awt.Rectangle;


public class Player {
	int x,y,size,stroke;
	int speed;
	Rectangle innerPlayer,outerPlayer;
	public Player(int initX, int initY)
	{
		size = 23;
		stroke = 4;
		speed = 5;
		x = initX-size/2;
		y = initY-size/2;
		outerPlayer = new Rectangle(x,y,size,size);
		innerPlayer = new Rectangle(x+stroke,y+stroke,size-2*stroke,size-2*stroke);
	}
	public void move(int moveX, int moveY, LevelBoundaries l)
	{
		if(moveX != 0 && moveY != 0) speed = 4;
		else speed = 5;
		this.x += moveX * speed;
		this.y += moveY * speed;
		innerPlayer.setLocation(x+stroke,y+stroke);
		outerPlayer.setLocation(x,y);
		collisionWithLevel(l);
	}
	public boolean collideWith(Enemy e)
	{
		int x1 = x;
		int y1 = y;
		int x2 = x + size;
		int y2 = y + size;
		int ex = e.getX() + e.getSize()/2;
		int ey = e.getY() + e.getSize()/2;
		double side1 = size;
		double side2,side3,ang1,ang2;
		
		side2 = Math.sqrt((x1-ex)*(x1-ex)+(y1-ey)*(y1-ey));
		side3 = Math.sqrt((x2-ex)*(x2-ex)+(y1-ey)*(y1-ey));
		if(side3 == 0) ang1 = 0.0;
		else ang1 = Math.acos((side1*side1+side3*side3-side2*side2)/(2*side1*side3))*180/Math.PI;
		if(side2 == 0) ang2 = 0.0;
		else ang2 = Math.acos((side1*side1+side2*side2-side3*side3)/(2*side1*side2))*180/Math.PI;
		if(ang1 >= 90 || ang2 >= 90)
		{
			if(side2 < (double)e.getSize()/2 || side3 < (double)e.getSize()/2) return true;
		}
		else
		{
			if(Math.sin(ang2*Math.PI/180)*side2 < (double)e.getSize()/2) return true;
		}
		//System.out.println(Math.cos(ang2*Math.PI/180)*side2 + " " + Math.cos(ang1*Math.PI/180)*side3);
		
		side2 = Math.sqrt((x2-ex)*(x2-ex)+(y1-ey)*(y1-ey));
		side3 = Math.sqrt((x2-ex)*(x2-ex)+(y2-ey)*(y2-ey));
		if(side3 == 0) ang1 = 0.0;
		else ang1 = Math.acos((side1*side1+side3*side3-side2*side2)/(2*side1*side3))*180/Math.PI;
		if(side2 == 0) ang2 = 0.0;
		else ang2 = Math.acos((side1*side1+side2*side2-side3*side3)/(2*side1*side2))*180/Math.PI;
		if(ang1 >= 90 || ang2 >= 90)
		{
			if(side2 < (double)e.getSize()/2 || side3 < (double)e.getSize()/2) return true;
		}
		else
		{
			if(Math.sin(ang2*Math.PI/180)*side2 < (double)e.getSize()/2) return true;
		}
		
		side2 = Math.sqrt((x1-ex)*(x1-ex)+(y2-ey)*(y2-ey));
		side3 = Math.sqrt((x2-ex)*(x2-ex)+(y2-ey)*(y2-ey));
		if(side3 == 0) ang1 = 0.0;
		else ang1 = Math.acos((side1*side1+side3*side3-side2*side2)/(2*side1*side3))*180/Math.PI;
		if(side2 == 0) ang2 = 0.0;
		else ang2 = Math.acos((side1*side1+side2*side2-side3*side3)/(2*side1*side2))*180/Math.PI;
		if(ang1 >= 90 || ang2 >= 90)
		{
			if(side2 < (double)e.getSize()/2 || side3 < (double)e.getSize()/2) return true;
		}
		else
		{
			if(Math.sin(ang2*Math.PI/180)*side2 < (double)e.getSize()/2) return true;
		}
		
		side2 = Math.sqrt((x1-ex)*(x1-ex)+(y1-ey)*(y1-ey));
		side3 = Math.sqrt((x1-ex)*(x1-ex)+(y2-ey)*(y2-ey));
		if(side3 == 0) ang1 = 0.0;
		else ang1 = Math.acos((side1*side1+side3*side3-side2*side2)/(2*side1*side3))*180/Math.PI;
		if(side2 == 0) ang2 = 0.0;
		else ang2 = Math.acos((side1*side1+side2*side2-side3*side3)/(2*side1*side2))*180/Math.PI;
		if(ang1 >= 90 || ang2 >= 90)
		{
			if(side2 < (double)e.getSize()/2 || side3 < (double)e.getSize()/2) return true;
		}
		else
		{
			if(Math.sin(ang2*Math.PI/180)*side2 < (double)e.getSize()/2) return true;
		}
		
		return false;
	}
	public boolean touches(CheckPoint p) 
	{
		Rectangle player = new Rectangle(x,y,size,size);
		Rectangle checkpoint = new Rectangle(p.getX(),p.getY(),p.getWidth(),p.getHeight());
		return player.intersects(checkpoint);
	}
	public void goTo(CheckPoint p)
	{
		x = p.getX() + p.getWidth()/2 - size/2;
		y = p.getY() + p.getHeight()/2 - size/2;
		innerPlayer.setLocation(x+stroke,y+stroke);
		outerPlayer.setLocation(x,y);
	}
	public Rectangle getInnerRectangle()
	{
		return innerPlayer;
	}
	public Rectangle getOuterRectangle()
	{
		return outerPlayer;
	}
	public void collisionWithLevel(LevelBoundaries l)
	{
		int n = l.getSize();
		int maxX = 0;
		int maxY = 0;
		int checkY = 0;
		int checkX = 0;
		for(int i = 0; i <= n-1; i++)
		{
			Line curL = l.getLine(i);
			int dir = curL.getDir();
			if(curL.getX1() == curL.getX2())
			{
				if(curL.getX1() > this.x && curL.getX1() < this.x + this.size)
				{
					if((curL.getY1() > this.y && curL.getY2() < this.y) || (curL.getY1() < this.y && curL.getY2() > this.y) ||
						(curL.getY1() > this.y + this.size && curL.getY2() < this.y + this.size) || (curL.getY1() < this.y + this.size && curL.getY2() > this.y + this.size))
					{
						if(dir == -1) maxY = curL.getX1() - (this.x + this.size);
						else if(dir == 1) maxY = curL.getX1() - this.x;
						if(dir == -1 && curL.getY1() >= this.y && curL.getY2() >= this.y) checkX = -2;
						else if(dir == -1 && curL.getY1() <= this.y + this.size && curL.getY2() <= this.y + this.size) checkX = -1;
						if(dir == 1 && curL.getY1() >= this.y && curL.getY2() >= this.y) checkX = 2;
						else if(dir == 1 && curL.getY1() <= this.y + this.size && curL.getY2() <= this.y + this.size) checkX = 1;
					}
				}
			}
			else
			{
				if(curL.getY1() > this.y && curL.getY1() < this.y + this.size)
				{
					if((curL.getX1() > this.x && curL.getX2() < this.x) || (curL.getX1() < this.x && curL.getX2() > this.x) ||
							(curL.getX1() > this.x + this.size && curL.getX2() < this.x + this.size) || (curL.getX1() < this.x + this.size && curL.getX2() > this.x + this.size))
					{
						if(dir == -1) maxX = curL.getY1() - (this.y + this.size);
						else if(dir == 1) maxX = curL.getY1() - this.y;
						if(dir == -1 && curL.getX1() >= this.x && curL.getX2() >= this.x) checkY = -2;
						else if(dir == -1 && curL.getX1() <= this.x + this.size && curL.getX2() <= this.x + this.size) checkY = -1;
						if(dir == 1 && curL.getX1() >= this.x && curL.getX2() >= this.x) checkY = 2;
						else if(dir == 1 && curL.getX1() <= this.x + this.size && curL.getX2() <= this.x + this.size) checkY = 1;
					}
				}
			}
		}
		if((checkX == -1 && checkY == 2) || (checkX == -2 && checkY == -2) || (checkX == 2 && checkY == -1) || (checkX == 1 && checkY == 1))
		{
			if(Math.abs(maxY) < Math.abs(maxX))
			{
				this.x += maxY;
			}
			else if(Math.abs(maxY) > Math.abs(maxX))
			{
				this.y += maxX;
			}
		}
		else
		{
			this.x += maxY;
			this.y += maxX;
		}
		innerPlayer.setLocation(x+stroke,y+stroke);
		outerPlayer.setLocation(x,y);
	}
}
