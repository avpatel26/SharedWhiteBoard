/* Name : Avi Patel
 * ID : 1143213 
 */
import java.awt.Graphics;
import java.awt.Point;
import java.util.ArrayList;


public class Shape  {
	String shapeName;
	Point start;
	Point end;
	ArrayList<Point> points = new ArrayList<Point>();
	ArrayList<String> input = new ArrayList<String>();
	
	public Shape(String shapeName, Point start, Point end) {
		super();
		this.shapeName = shapeName;
		this.start = start;
		this.end = end;
	}

	public Shape(String shapeName, Point start, ArrayList<String> input) {
		super();
		this.shapeName = shapeName;
		this.start = start;
		this.input = input;
	}

	public Shape(String shapeName, ArrayList<Point> points) {
		super();
		this.shapeName = shapeName;
		this.points = points;
	}

	public void draw(Graphics graphics) {
		if(shapeName.equals("Line")) {
			graphics.drawLine(start.x, start.y, end.x, end.y);
		}
		else if(shapeName.equals("Circle")||shapeName.equals("Rectangle")) {
			int x, y;
			if (start.x < end.x ) {
				x = start.x;
			} else {
				x = end.x;
			}
			if (start.y < end.y ) {
				y = start.y;
			} else {
				y = end.y;
			}
			if(shapeName.equals("Circle")) {
				graphics.drawOval(x, y, Math.abs(start.x-end.x), Math.abs(start.y-end.y));
			}else{
				graphics.drawRect(x, y, Math.abs(start.x-end.x), Math.abs(start.y-end.y));
			}
		
		}else if(shapeName.equals("Pencil")){
			for (int i = 1; i < points.size(); i++)
			{
				Point pStart = points.get(i - 1);
				Point pEnd = points.get(i);
				graphics.drawLine(pStart.x, pStart.y, pEnd.x, pEnd.y);

			}
		}else if(shapeName.equals("Text")) {
			for (String line : input) {
				graphics.drawString(line, start.x, start.y );
			}
		}
	
	}
}
