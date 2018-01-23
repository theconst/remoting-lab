package ua.kpi.apeps.server.rmi;

import ua.kpi.apeps.model.EmployeeShiftRecord;
import ua.kpi.apeps.repository.RemoteRepository;
import ua.kpi.apeps.repository.RemoteRepositoryAdapter;
import ua.kpi.apeps.repository.RemoteTransactionTemplate;
import ua.kpi.apeps.repository.RemoteTransactionTemplateAdapter;
import ua.kpi.apeps.repository.Repository;
import ua.kpi.apeps.repository.TransactionTemplate;

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
    private static RemoteRepository<EmployeeShiftRecord, Integer> REPO;
    private static RemoteTransactionTemplate<Void> TEMPLATE;
    private static Remote REMOTE_REPOSITORY;
    private static Remote REMOTE_TEMPLATE;

    public static void run(
            Repository<EmployeeShiftRecord, Integer> repository,
            TransactionTemplate<Void> noResultTemplate,
            int port) {
        if (System.getSecurityManager() == null) {
            System.setSecurityManager(new SecurityManager());
        }
        try {
            Registry registry = LocateRegistry.createRegistry(port);

            REPO = RemoteRepositoryAdapter.of(repository);
            TEMPLATE = RemoteTransactionTemplateAdapter.of(noResultTemplate);
            REMOTE_REPOSITORY = exportObject(REPO, port);
            REMOTE_TEMPLATE = exportObject(TEMPLATE, port);


            registry.rebind(EMPLOYEE_SHIFT_RECORD_REPOSITORY, REMOTE_REPOSITORY);
        } catch (RemoteException e) {
            //throw it further
            throw new IllegalStateException(e);
        }
    }
}
