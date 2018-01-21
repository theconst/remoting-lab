package ua.kpi.apeps.client.rmi;

import ua.kpi.apeps.model.Employee;
import ua.kpi.apeps.repository.EmployeeRepository;
import ua.kpi.apeps.repository.Repository;

import java.io.IOException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class Client {

    public static final int PORT = 1099;

    public static void main(String[] args) {
        if (System.getSecurityManager() == null) {
            System.setSecurityManager(new SecurityManager());
        }

        try {
            Registry registry = LocateRegistry.getRegistry(PORT);
            @SuppressWarnings("unchecked") //binding of a known type
            EmployeeRepository repository = (EmployeeRepository) registry.lookup(EmployeeRepository.NAME);

            System.out.println(repository.getAll());
        } catch (RemoteException e) {
            e.printStackTrace();
        } catch (NotBoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
