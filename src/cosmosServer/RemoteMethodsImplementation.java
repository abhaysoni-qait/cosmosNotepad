package cosmosServer;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class RemoteMethodsImplementation extends UnicastRemoteObject implements RemoteMethods{

	private double lattestVersion = 4.0;
	
	private String[] tipsList = { "Make each day your masterpiece \n		-	John Wooden",
								  "Someday is not a day of the week \n		- Denise Nelson",
								  "The best Revenge is massive success \n		- Frank Sinatra",
								  "The Only way to do great things is to \n love what you do \n		-Steve Jobs",
								  "No more Tips"
								  };
	
	public RemoteMethodsImplementation() throws RemoteException{
	}

	@Override
	public UpdateDailogBox versionCheck(double userVersion) {
		UpdateDailogBox updateDailogBox = new UpdateDailogBox();
		if (userVersion < lattestVersion) {
			updateDailogBox.setMessageType(JOptionPane.WARNING_MESSAGE);
			updateDailogBox.setMessage("You are using an Old Version of Cosmos \n Current Version : " + userVersion + "\n Lattest Version : " + lattestVersion + " \n Please Update for Best Experience");
			updateDailogBox.setIcon(null);
		}else {
			 updateDailogBox.setMessageType(JOptionPane.INFORMATION_MESSAGE);
			 updateDailogBox.setMessage("You are using Lattest Version of Cosmos \n Thank you for your Support");
			 updateDailogBox.setIcon(null);
		}
		
		return updateDailogBox;
	}
	
	
	@Override
	public TipsDailogBox tips(int i) throws RemoteException {
		
//		Safety Measures
		if(i >= tipsList.length-1) {i=tipsList.length-1;}
		if(i<0) { i = 0; }
//		Safety Measures	
		
		TipsDailogBox tipsDailogBox = new TipsDailogBox();
		tipsDailogBox.setMessageType(JOptionPane.INFORMATION_MESSAGE);
		tipsDailogBox.setMessage(tipsList[i]);
		tipsDailogBox.setIcon(null);
		
		return tipsDailogBox;
	}
	
	
	
}
