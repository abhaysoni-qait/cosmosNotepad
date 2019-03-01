package cosmosServer;

import java.io.Serializable;

import javax.swing.Icon;

public class TipsDailogBox implements Serializable{
	
	private String Message ;
	private Icon icon;
	private int MessageType ;
	
	
//  									  GETTERS   GETTERS

	
	public String getMessage() {
		return Message;
	}
	public Icon getIcon() {
		return icon;
	}
	public int getMessageType() {
		return MessageType;
	}
	
//	  									 SETTERS   SETTERS
	
	public void setMessage(String message) {
		Message = message;
	}
	public void setIcon(Icon icon) {
		this.icon = icon;
	}
	public void setMessageType(int messageType) {
		MessageType = messageType;
	}

}
