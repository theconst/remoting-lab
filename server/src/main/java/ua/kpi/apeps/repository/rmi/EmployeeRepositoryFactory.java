package ua.kpi.apeps.repository.rmi;

import ua.kpi.apeps.repository.EmployeeRepository;

import static java.lang.String.format;

public final class EmployeeRepositoryFactory {

    public enum Modes {
        STUB, PRODUCTION;
    }

    public static EmployeeRepository createRepository(Modes mode) {
        switch (mode) {
            case STUB:
                return new EmployeeRepositoryStub();
        }
        throw new IllegalArgumentException(format("No mode '%s'", mode));
    }
}
