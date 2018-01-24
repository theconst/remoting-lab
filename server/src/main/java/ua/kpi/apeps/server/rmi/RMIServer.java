package ua.kpi.apeps.server.rmi;

import ua.kpi.apeps.repository.RemoteEmployeeBatchUpdateService;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import static java.rmi.server.UnicastRemoteObject.exportObject;

/**
 * Not implemented via spring to provide explicitness of usage
 */
public class RMIServer {

    private static Remote REMOTE_SERVICE;

    public static void run(RemoteEmployeeBatchUpdateService remoteService, int port) {
        if (System.getSecurityManager() == null) {
            System.setSecurityManager(new SecurityManager());
        }
        try {
            Registry registry = LocateRegistry.createRegistry(port);
            REMOTE_SERVICE = exportObject(remoteService, port);
            registry.rebind(RemoteEmployeeBatchUpdateService.NAME, REMOTE_SERVICE);
        } catch (RemoteException e) {
            throw new IllegalStateException(e);
        }
    }
}
