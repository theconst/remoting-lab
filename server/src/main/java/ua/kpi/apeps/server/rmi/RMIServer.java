package ua.kpi.apeps.server.rmi;

import ua.kpi.apeps.model.EmployeeShiftRecord;
import ua.kpi.apeps.repository.EmployeeShiftRecordRepository;
import ua.kpi.apeps.repository.RemoteRepository;
import ua.kpi.apeps.repository.RemoteRepositoryAdapter;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import static java.rmi.server.UnicastRemoteObject.exportObject;
import static ua.kpi.apeps.repository.ServiceRegistry.EMPLOYEE_SHIFT_RECORD_REPOSITORY;

/**
 * Not implemented via spring to provide explicitness of usage
 */
public class RMIServer {

    //prevent gc
    private static RemoteRepository<EmployeeShiftRecord, Integer> REMOTE_REPO;
    private static Remote REMOTE;

    public static void run(EmployeeShiftRecordRepository repository, int port) {
        if (System.getSecurityManager() == null) {
            System.setSecurityManager(new SecurityManager());
        }
        try {
            Registry registry = LocateRegistry.createRegistry(port);

            REMOTE_REPO = RemoteRepositoryAdapter.of(repository);
            REMOTE = exportObject(REMOTE_REPO, port);

            registry.rebind(EMPLOYEE_SHIFT_RECORD_REPOSITORY, REMOTE);
        } catch (RemoteException e) {
            //throw it further
            throw new IllegalStateException(e);
        }
    }
}
