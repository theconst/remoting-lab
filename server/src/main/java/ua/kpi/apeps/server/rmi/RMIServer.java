package ua.kpi.apeps.server.rmi;

import ua.kpi.apeps.model.EmployeeShiftRecord;
import ua.kpi.apeps.repository.rmi.EmployeeShiftJournalRepositoryStub;
import ua.kpi.apeps.repository.rmi.RemoteRepository;
import ua.kpi.apeps.repository.rmi.RemoteRepositoryAdapter;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import static java.rmi.server.UnicastRemoteObject.exportObject;
import static ua.kpi.apeps.repository.rmi.ServiceRegistry.EMPLOYEE_SHIFT_RECORD_REPOSITORY;

public class RMIServer {

    //hack to prevent gc
    private static EmployeeShiftJournalRepositoryStub REPOSITORY;
    private static RemoteRepository<EmployeeShiftRecord, Integer> REMOTE_REPO;
    private static Remote REMOTE;

    public static void main(String[] args) {
        if (System.getSecurityManager() == null) {
            System.setSecurityManager(new SecurityManager());
        }
        try {
            Registry registry = LocateRegistry.createRegistry(1099);

            REPOSITORY = new EmployeeShiftJournalRepositoryStub();
            REMOTE_REPO = RemoteRepositoryAdapter.of(REPOSITORY);
            REMOTE = exportObject(REMOTE_REPO, 1099);

            registry.rebind(EMPLOYEE_SHIFT_RECORD_REPOSITORY, REMOTE);
        } catch (RemoteException e) {
            e.printStackTrace(); //TODO:handle
        }
    }
}
