/* Name : Avi Patel
 * ID : 1143213 
 */
import java.awt.*;
import java.io.Serializable;
import java.util.ArrayList;

public class Bean implements Serializable {

	private int eventID;
	private String eventName;
	
	private String userName;
	private ArrayList<String> users;
	
	private String shape;
	private ArrayList<Shape> shapes;
	private Point start;
	private Point end;
	private ArrayList<Point> points;
	private ArrayList<String> input;
	
	public int getEventID() {
		return eventID;
	}

	public void setEventID(int eventID) {
		this.eventID = eventID;
	}

	public String getEventName() {
		return eventName;
	}

	public void setEventName(String eventName) {
		this.eventName = eventName;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public ArrayList<String> getUsers() {
		return users;
	}

	public void setUsers(ArrayList<String> users) {
		this.users = users;
	}

	public String getShape() {
		return shape;
	}

	public void setShape(String shape) {
		this.shape = shape;
	}

	public Point getStart() {
		return start;
	}

	public void setStart(Point start) {
		this.start = start;
	}

	public Point getEnd() {
		return end;
	}

	public void setEnd(Point end) {
		this.end = end;
	}

	public ArrayList<Point> getPoints() {
		return points;
	}

	public void setPoints(ArrayList<Point> points) {
		this.points = points;
	}

	public ArrayList<String> getInput() {
		return input;
	}

	public void setInput(ArrayList<String> input) {
		this.input = input;
	}

	public ArrayList<Shape> getShapes() {
		return shapes;
	}

	public void setShapes(ArrayList<Shape> shapes) {
		this.shapes = shapes;
	}
	
	
}
