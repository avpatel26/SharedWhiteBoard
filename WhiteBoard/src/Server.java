/* Name : Avi Patel
 * ID : 1143213 
 */
import java.rmi.*;
import java.rmi.registry.LocateRegistry;
import java.rmi.server.*;
import java.util.ArrayList;

public class Server extends UnicastRemoteObject implements ServerInterface {
	
	protected Server() throws RemoteException {
		super();
	}

	ArrayList<String> users = new ArrayList<String>();
	ArrayList<Bean> eventsBean;
	int currentEvent;
	String hostName;

	@Override
	public String join(String userName) {
		ArrayList<String> list = new ArrayList<String>(users);		
		if(list.contains(userName)) {
			return "notavailable";
		}
		if ((list.isEmpty()) ) {
			users = new ArrayList<String>();
			eventsBean = new ArrayList<Bean>();
			currentEvent = 0;
			hostName = userName;
			addUser(userName);
		} else {
			Bean event = new Bean();
			event.setEventName("join");
			event.setUserName(userName); 
			addEvent(event);
		}
		System.out.println(users);
		return userName;
		}
	
	@Override
	public String getHost() {
		return hostName;
	}
	
	@Override
	public void addUser(String userName) {
		users.add(userName);
		Bean event = new Bean();
		event.setEventName("users");
		event.setUsers(new ArrayList<String>(users));
		addEvent(event);	
	}
	
	@Override
	public void removeUser(String userName) {
		
		boolean found = false;
		ArrayList<String> list = null;
		synchronized(users) {
			list = new ArrayList<String>(users);
		}for (String auser:list) {
			if(auser.equals("#" + userName)) {
				found = true;
				break;
			} else if(auser.equals(userName)) {
				found = true;
				int idx = list.indexOf(auser);
				synchronized(users) {
					users.set(idx, "#" + userName);
				}
				break;
			}
		}
		if(! found) {
			synchronized(users) {
				users.add("#" + userName);
			}
		}
		Bean event = new Bean();
		event.setEventName("users");
		
		event.setUsers(new ArrayList<String>(users));
		if(userName.equals(hostName)) {
			users.clear();
			event.setUsers(users);
			
		}
		addEvent(event);
			}
	
		@Override
		public synchronized void addEvent(Bean bean) {
			bean.setEventID(currentEvent++);
			eventsBean.add(bean);
		}
		
		@Override
		public synchronized ArrayList<Bean> getEvent(int index) {
			return new ArrayList<Bean>(eventsBean.subList(index, eventsBean.size()));
		}
		
		public static void main(String args[])
		{
			try
			{
				String serverName = "127.0.0.1:5000";
				String serviceName = "whiteBoard";
				ServerInterface obj = new Server();
				LocateRegistry.createRegistry(5000);
				Naming.rebind("rmi://"+serverName+"/"+serviceName, obj);
				System.out.println("Server started...");
			}
			catch (Exception e)
			{
				System.out.println("BoardServerImpl err: " + e.getMessage());
				e.printStackTrace();
			}
		}

}
