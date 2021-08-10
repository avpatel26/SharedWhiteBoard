/* Name : Avi Patel
 * ID : 1143213 
 */
import java.awt.*;
import java.awt.event.*;
import java.rmi.RemoteException;
import java.util.ArrayList;
import javax.swing.*;
public class MasterPanel extends JPanel implements ActionListener, KeyListener {
	ServerInterface serverInterface;
	
	ArrayList<Shape> shapes = new ArrayList<Shape>();
	Point start = new Point(5, 10);
	String shape = "Pencil";
	ArrayList<Point> points;
	ArrayList<String> input;
	JFrame frame;
	
	public MasterPanel(ServerInterface serverInterface, JFrame frame)	{
		this.serverInterface = serverInterface;
		this.frame = frame;
		MouseAdapter mouseAdapter = new MouseAdapter(){
			@Override
			public void mouseMoved(MouseEvent e)
			{
				if((input != null) && ( ! input.isEmpty())) {
					//send to server
					Bean event = new Bean();
					event.setEventName("move");
					event.setShape(shape);
					event.setStart(start);
					event.setInput(input);
					try {
						serverInterface.addEvent(event);
					} catch (RemoteException e1) {
						e1.printStackTrace();
					}
					start = new Point(start.x, start.y );
					input = null;
				}
			}
			
		@Override
		public void mousePressed(MouseEvent e)
		{
			start = e.getPoint();
			if(shape.equals("Pencil")) {
				points = new ArrayList<Point>();
			}
		}
		
		@Override
		public void mouseDragged(MouseEvent e)
		{
			Point endPoint = e.getPoint();
			if(shape.equals("Pencil")){
				points.add(new Point(e.getX(), e.getY()));
			}
			//send to server
			Bean event = new Bean();
			event.setEventName("drag");
			
			event.setShape(shape);
			event.setStart(start);
			event.setEnd(endPoint);
			event.setPoints(points);
			try {
				serverInterface.addEvent(event);
			} catch (RemoteException ex) {
				ex.printStackTrace();
			}
		}
		
		
		@Override
		public void mouseReleased (MouseEvent e)
		{
			Point endPoint = e.getPoint();
			//send to server
			Bean event = new Bean();
			event.setEventName("release");
			event.setShape(shape);
			event.setStart(start);
			event.setEnd(endPoint);
			event.setPoints(points);
			try {
				serverInterface.addEvent(event);
			} catch (RemoteException e1) {
				e1.printStackTrace();
			}
			}
		};
		addMouseListener(mouseAdapter);
		addMouseMotionListener(mouseAdapter);
		addKeyListener(this);
		Tools();
	}
	
	private void Tools() {
		JMenuBar menuBar = new JMenuBar();
		JMenu tools = new JMenu("Tools");
		ButtonGroup b = new ButtonGroup();
		
		JToggleButton pencil = new JToggleButton("Pencil");
		pencil.setActionCommand("Pencil");
		pencil.addActionListener(this);
		pencil.setSelected(true);
		b.add(pencil);
		tools.add(pencil);

		JToggleButton circle = new JToggleButton("Circle");
		circle.setActionCommand("Circle");
		circle.addActionListener(this);
		b.add(circle);
		tools.add(circle);
		
		JToggleButton line = new JToggleButton("Line");
		line.setActionCommand("Line");
		line.addActionListener(this);
		b.add(line);
		tools.add(line);

		
		JToggleButton rectangle = new JToggleButton("Rectangle");
		rectangle.setActionCommand("Rectangle");
		rectangle.addActionListener(this);
		b.add(rectangle);
		tools.add(rectangle);
						
		JToggleButton text = new JToggleButton("Text");
		text.setActionCommand("Text");
		text.addActionListener(this);
		b.add(text);
		tools.add(text);
		
		menuBar.add(tools);
		frame.setJMenuBar(menuBar);
	}
	
	public synchronized void addShape(Shape shape) {
		shapes.add(shape);
	}
	
	@Override
	public synchronized void paintComponent(Graphics gfx) {
		for (Shape shape : shapes) {
			shape.draw(gfx);
		}
	}
	
	@Override
	public void actionPerformed(ActionEvent e)
	{
		String action = e.getActionCommand();
		if (action.equals("Line")) {
			shape = "Line";
		} else if (action.equals("Rectangle")) {
			shape = "Rectangle";
		} else if (action.equals("Circle")) {
			shape = "Circle";
		} else if (action.equals("Pencil")) {
			shape = "Pencil";
		} else if (action.equals("Text")) {
			shape = "Text";
		} 
	}
	
	
	@Override
	public void keyTyped(KeyEvent e){
		keyTyping(e);
	}
	
	public void keyTyping(KeyEvent e)
	{
		if(shape.equals("Text")) {
			if(input == null) {
				input = new ArrayList<String>();
				input.add(new String());
			}
			char c = e.getKeyChar();
			int lastLine = input.size()-1;
			String lastStr = input.get(lastLine);
			input.set(lastLine, lastStr + c);
			Bean event = new Bean();
			event.setEventName("keypress");
			event.setShape(shape);
			event.setStart(start);
			event.setInput(input);
			try {
				serverInterface.addEvent(event);
			} catch (RemoteException e1) {
				e1.printStackTrace();
			}
		}
	}
	
	@Override
	public void keyPressed(KeyEvent e) {
		}
	
	@Override
	public void keyReleased(KeyEvent e) {
	}
	
}
