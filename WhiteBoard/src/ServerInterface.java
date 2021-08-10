/* Name : Avi Patel
 * ID : 1143213 
 */
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;

public interface ServerInterface extends Remote {
	
	String join(String userName) throws RemoteException;
	String getHost() throws RemoteException;
	void addUser(String userName) throws RemoteException;
	void removeUser(String userName) throws RemoteException;
	void addEvent(Bean bean) throws RemoteException;
	ArrayList<Bean> getEvent(int index) throws RemoteException;
	
}
