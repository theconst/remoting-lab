package ua.kpi.apeps.server.rmi;

import ua.kpi.apeps.model.Employee;
import ua.kpi.apeps.repository.EmployeeRepository;
import ua.kpi.apeps.repository.rmi.EmployeeRepositoryFactory;
import ua.kpi.apeps.repository.rmi.RemoteRepository;
import ua.kpi.apeps.repository.rmi.RemoteRepositoryAdapter;

import java.rmi.AlreadyBoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import static java.rmi.server.UnicastRemoteObject.exportObject;
import static ua.kpi.apeps.repository.rmi.EmployeeRepositoryFactory.Modes.STUB;

public class RMIServer {

    public static final int PORT = 1099;

    public static void main(String[] args) {
        if (System.getSecurityManager() == null) {
            System.setSecurityManager(new SecurityManager());
        }
        try {
            EmployeeRepository repository = EmployeeRepositoryFactory.createRepository(STUB);
            RemoteRepository<Employee, Integer> remoteRepository = RemoteRepositoryAdapter.of(repository);
            Registry registry = LocateRegistry.createRegistry(PORT);
            Remote remote = exportObject(remoteRepository, PORT);
            registry.bind(EmployeeRepository.NAME, remote);

        } catch (RemoteException | AlreadyBoundException e) {
            e.printStackTrace(); //TODO:handle
        }
    }
}
