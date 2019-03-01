package cosmosServer;

import java.rmi.Remote;
import java.rmi.RemoteException;

import javax.swing.JOptionPane;

public interface RemoteMethods extends Remote{
	UpdateDailogBox versionCheck(double userVersion) throws RemoteException;
	TipsDailogBox tips(int i) throws RemoteException;
}
