/* Name : Avi Patel
 * ID : 1143213 
 */
import java.awt.Component;
import javax.swing.DefaultListCellRenderer;
import javax.swing.JList;
public class UserList extends DefaultListCellRenderer {

	@Override
	public Component getListCellRendererComponent(JList<?> list,Object value, int index, boolean isSelected, boolean cellHasFocus) {
		String currentUser = value.toString();
		System.out.println(currentUser);
		if(currentUser.equals(CreateWhiteBoard.hostName)) {
			
			setText(currentUser+" (Host)");	
		}else {
			setText(currentUser);
		}
		return this;
	}
}