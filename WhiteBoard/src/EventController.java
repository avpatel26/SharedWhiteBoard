/* Name : Avi Patel
 * ID : 1143213 
 */
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Vector;
import javax.swing.JOptionPane;

public class EventController implements Runnable {
	
	MasterPanel mPanel;
	UserPanel uPanel;
	boolean join = false;
	
	boolean authorized = false;
	int nextEvent = 0;
	
	public EventController(MasterPanel mPanel, UserPanel uPanel) {
		this.mPanel = mPanel;
		this.uPanel = uPanel;
	}
	
	public void execute(Bean event) {
		if (event.getEventName().equals("Exit")) {	
			JOptionPane.showMessageDialog(mPanel.frame, "Session terminated by Admin","Exit", JOptionPane.INFORMATION_MESSAGE);
			try {
				mPanel.serverInterface.removeUser(CreateWhiteBoard.userName);
			} catch (RemoteException e) {
				e.printStackTrace();
			}
			System.exit(0);
		}
		else if (event.getEventName().equals("join")) {
			int choice = JOptionPane.showConfirmDialog(mPanel.frame,"Someone wants share your Whiteboard.Allow?","Join", JOptionPane.YES_NO_OPTION);
			try {
				if(choice == JOptionPane.YES_OPTION) {
					CreateWhiteBoard.server.addUser(event.getUserName());
				} else {
					CreateWhiteBoard.server.removeUser(event.getUserName());
				}
			} catch (RemoteException e1) {
				e1.printStackTrace();
			}
		}
		else if (event.getEventName().equals("users")) {
			authorized = false;
			for (String auser:event.getUsers()) {
				if(auser.equals(CreateWhiteBoard.userName)) {
					join = true;
					authorized = true;
					break;
				} else if(auser.equals("#" + CreateWhiteBoard.userName)) {
					join = true;
					authorized = false;
					break;
				}
			}
			if(join) {
				if(authorized) {
					mPanel.requestFocusInWindow();
					mPanel.frame.setVisible(true);
					Vector<String> uIDs = new Vector<String>(event.getUsers());
					uPanel.refresh(uIDs);
				} else {
					JOptionPane.showMessageDialog(mPanel.frame, "Access denied","AccessDenied", JOptionPane.INFORMATION_MESSAGE);
					System.exit(0);
				}
			}
		}
			else if (event.getEventName().equals("keypress")) {
			Shape draw = new Shape(event.getShape(), event.getStart(), event.getInput());
			draw.draw(mPanel.getGraphics());
		} 
		else if (event.getEventName().equals("move")) {
			mPanel.addShape(new Shape(event.getShape(), event.getStart(), event.getInput()));
		}
		else if (event.getEventName().equals("drag")) {
			
			if(event.getShape().equals("Line") || event.getShape().equals("Rectangle") ||  event.getShape().equals("Circle")) {
				Shape draw = new Shape(event.getShape(),event.getStart(),event.getEnd());
				draw.draw(mPanel.getGraphics());
			}
			else if(event.getShape().equals("Pencil")) {
				Shape draw = new Shape(event.getShape(),event.getPoints());
				draw.draw(mPanel.getGraphics());
			}
			mPanel.frame.repaint();
		} 
		else if (event.getEventName().equals("release")) {
	
			if(event.getShape().equals("Line") || event.getShape().equals("Rectangle") ||  event.getShape().equals("Circle")) {
				mPanel.addShape(new Shape(event.getShape(),event.getStart(),event.getEnd()));
			}
			else if(event.getShape().equals("Pencil")) {
				mPanel.addShape(new Shape(event.getShape(),event.getPoints()));
			}
	
			mPanel.frame.repaint();
		}
	}
	
	@Override
	public void run() {
		while (true) {
		ArrayList<Bean> beans;
			try {
				
				beans = mPanel.serverInterface.getEvent(nextEvent);
				for (Bean bean : beans) {
					if(bean.getEventName().equals("join") && CreateWhiteBoard.host ) {
						execute(bean);
					}
				}
				Bean latestUserList = null;
				for (Bean bean : beans) {
					if(bean.getEventName().equals("users")) {
						latestUserList = bean;
					}
				}
				if(latestUserList != null) {
					execute(latestUserList);
				}
				if(!join){
					continue;
				}
				for (Bean event : beans) {
					if(!(event.getEventName().equals("join") || event.getEventName().equals("users"))){
						execute(event);
					}
				}
				if (beans.size() > 0) {
					nextEvent = beans.get(beans.size()-1).getEventID() + 1;
				}
			} catch (RemoteException e) {
				e.printStackTrace();
			}
			}
	}
}
