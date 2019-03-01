package cosmosServer;

import java.net.MalformedURLException;
import java.rmi.AlreadyBoundException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class CosmosServer {
	final static int PORT = 13500;
	static Registry registry= null;
	public static void main(String[] args) {
		
		RemoteMethods remoteMethods = null;
		try {
			remoteMethods = (RemoteMethods)new RemoteMethodsImplementation();
			registry = LocateRegistry.createRegistry(PORT);
			System.out.println("Registry Created");
//			registry.bind("cosmosRemoteMethods", remoteMethods);
			registry.rebind("cosmos", remoteMethods);
			System.out.println("Object Binded in Registry Successfully");
		} catch (RemoteException e) {
			System.out.println("ERROR : " + e.getMessage());
//			e.printStackTrace();
			System.exit(1);
		}
		
		System.out.println("Server Started Successfully");
		
		
	}

}
