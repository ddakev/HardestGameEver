import java.util.ArrayList;



public class LevelBoundaries {
	private ArrayList<Line> b;
	public LevelBoundaries()
	{
		b = new ArrayList<Line>();
	}
	public void addLine(int x1, int y1, int x2, int y2, int dir)
	{
		b.add(new Line(x1,y1,x2,y2,dir));
	}
	public Line getLine(int i)
	{
		return b.get(i);
	}
	public int getSize()
	{
		return b.size();
	}
}
