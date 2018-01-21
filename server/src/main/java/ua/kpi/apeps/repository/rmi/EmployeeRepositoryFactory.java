package ua.kpi.apeps.repository.rmi;

import ua.kpi.apeps.model.Employee;
import ua.kpi.apeps.repository.Repository;

import static java.lang.String.format;

public final class EmployeeRepositoryFactory {

    public enum Modes {
        STUB, PRODUCTION;
    }

    public static Repository<Employee, Integer> createRepository(Modes mode) {
        switch (mode) {
            case STUB:
                return new EmployeeRepositoryStub();
        }
        throw new IllegalArgumentException(format("No mode '%s'", mode));
    }
}
