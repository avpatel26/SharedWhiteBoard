/* Name : Avi Patel
 * ID : 1143213 
 */
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.rmi.RemoteException;
import java.util.Vector;
import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
public class UserPanel extends JPanel implements ActionListener, ListSelectionListener, KeyListener {

	ServerInterface serverInterface;
	JScrollPane scroll;
	JList<String> users;
	JButton remove;
	Vector<String> userNames = new Vector<String>();
	String currentUser;
	public UserPanel(ServerInterface server, JScrollPane s)
	{
		serverInterface = server;
		scroll = s;
		setBorder(BorderFactory.createTitledBorder("Users"));
		setLayout(new BorderLayout());
		if(CreateWhiteBoard.host) {
			remove = new JButton("Remove");
			add(remove, BorderLayout.SOUTH);
			remove.addActionListener(this);
		}
		users = new JList<String>(userNames);
		UserList ul = new UserList();
		users.setCellRenderer(ul);
		add(users, BorderLayout.CENTER);	
		users.addListSelectionListener(this);
		addKeyListener(this);
	}
	
	public synchronized void refresh(Vector<String> names) {
		userNames.removeAllElements();
		for(String auser : names) {
			if(auser.charAt(0) != '#') {
				userNames.add(auser);
			}
		}
		users.setListData(userNames);
		if(userNames.size() >=2) {
			users.setSelectedIndex(1);
		} else {
			users.setSelectedIndex(0);
		}
		scroll.revalidate();
		scroll.repaint();
	}
	
	@Override
	public void valueChanged(ListSelectionEvent event) {
		if(event.getSource() == users && !event.getValueIsAdjusting()) {
			String user = (String)users.getSelectedValue();
			if(user != null) {
				currentUser = user;
				if(remove != null) {
					if(currentUser.equals(CreateWhiteBoard.hostName)) {
						remove.setEnabled(false);
					} else {
						remove.setEnabled(true);
					}
				}
			}
		}
	}
	
	@Override
	public void actionPerformed(ActionEvent event) {
		if(event.getSource() == remove) {
			int selection = users.getSelectedIndex();
			if(selection >= 0) {
				try {
					serverInterface.removeUser(currentUser);
				} catch (RemoteException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	@Override
	public void keyTyped(KeyEvent e) {
		CreateWhiteBoard.mPanel.keyTyping(e);
	}
	
	@Override
	public void keyPressed(KeyEvent e) {
	}
	
	@Override
	public void keyReleased(KeyEvent e) {
	}
}