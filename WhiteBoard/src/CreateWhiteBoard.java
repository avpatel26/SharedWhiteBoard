/* Name : Avi Patel
 * ID : 1143213 
 */
import java.awt.*;
import java.awt.event.*;
import java.net.MalformedURLException;
import java.rmi.*;
import javax.swing.*;

public class CreateWhiteBoard
{
	private static String ipAddress;
	private static String port;
	static String userName;
	
	static ServerInterface server;
	
	static String hostName=null;
	static boolean host;
	
	static UserPanel userUI;
	static MasterPanel mPanel;
	
	public static void main(String[] args) {
		
		try {
			if(args.length == 3) {
				ipAddress = args[0];
				port = args[1];
				userName = args[2];
				}
			else{
				System.out.println(" Improper Argument Error!! Enter proper number of arguments");
				System.exit(0);
			}
			String url = "rmi://"+ipAddress+":"+port+"/whiteBoard";
			server = (ServerInterface) Naming.lookup(url);
			
			userName = server.join(userName);
			if(userName.equals("notavailable")) {
				System.out.println("Name already exist..Enter another user name");
				System.exit(0);
			}
			hostName = server.getHost();
			if(hostName.equals(userName)) {
				host = true;
			}else {
				host = false;
			}
			
			System.out.println("Create Whiteboard is running..."+userName);
			
			
		}catch(NumberFormatException e) {
			System.out.println("Improper Argument Error!! Run again with proper argument format: java –jar CreateWhiteBoard.jar <serverIPAddress> <serverPort> <username>");	
			System.exit(0);
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NotBoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			public void run()
			{
				initialize();
			}
		});
	}
	
	
	
	private static void initialize()
	{
		final JFrame frame = new JFrame("SharedBoard");
		frame.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent w) {
				if(host) {
					int x = JOptionPane.showConfirmDialog(frame,"Do you want to exit?","Exit" ,JOptionPane.YES_NO_OPTION);
					if(x == JOptionPane.NO_OPTION || x == JOptionPane.CANCEL_OPTION) {
							frame.setVisible(true);
					}else if(x==JOptionPane.YES_OPTION)
						try {
							server.removeUser(CreateWhiteBoard.userName);
							System.exit(0);
						} catch (RemoteException e) {
							e.printStackTrace();
						}
				}else {
					int x = JOptionPane.showConfirmDialog(frame,"Do you want to exit?","Exit", JOptionPane.YES_NO_OPTION);
					if(x == JOptionPane.NO_OPTION || x == JOptionPane.CANCEL_OPTION) {
						frame.setVisible(true);
					}else if(x==JOptionPane.YES_OPTION)
						if(server != null) {
							Bean event = new Bean();
							event.setEventName("Exit");
							try {
								server.addEvent(event);
							} catch (RemoteException e) {
								e.printStackTrace();
							}
						}
					}		
			}
		});
		frame.getContentPane().setLayout(new BorderLayout());
		mPanel = new MasterPanel(server, frame);
		frame.getContentPane().add(mPanel, BorderLayout.CENTER);
		JScrollPane scroll = new JScrollPane();
		userUI = new UserPanel(server, scroll);
		scroll.setPreferredSize(new Dimension(80,600));
		scroll.getViewport().add(userUI);
		frame.getContentPane().add(scroll, BorderLayout.EAST);
		frame.setSize(800,600);
		frame.setVisible(false);
		frame.addWindowFocusListener(new WindowAdapter() {
			public void windowGainedFocus(WindowEvent e) {
				mPanel.requestFocusInWindow();
			}
		});
		new Thread(new EventController(mPanel, userUI),"EventDispatcher").start();
	}
	
}