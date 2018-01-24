package ua.kpi.apeps.client.rmi;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.Options;
import ua.kpi.apeps.repository.RemoteEmployeeBatchUpdateService;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.Registry;
import java.util.Objects;

import static java.rmi.registry.LocateRegistry.getRegistry;

@Slf4j
public class Client {

    public static final String HOST = "h";
    public static final String PORT = "p";
    public static final String JOURNAL = "j";

    /**
     * Small utility program that populates
     *
     * @param args name of the csv journal to parse
     * @throws Exception if no file exists or server is unavailable; just panics and prints stack-trace
     */
    public static void main(String[] args) throws Exception {
        //parse command line options
        Options options = new Options()
                .addRequiredOption(HOST,"host", true, "Host of the remote RMI server")
                .addRequiredOption(PORT,"port", true, "Port of the remote RMI server")
                .addRequiredOption(JOURNAL,"journal", true, "Path to the journal file");

        CommandLineParser parser = new DefaultParser();
        CommandLine commandLine = parser.parse(options, args);

        String host = commandLine.getOptionValue(HOST);
        Integer port = Integer.valueOf(commandLine.getOptionValue(PORT));
        String journal = commandLine.getOptionValue(JOURNAL);

        //set up security manager for RMI
        setSecurityManager();

        //execute update of the remote repository
        log.info("Started importing of journal {}", journal);
        Registry registry = getRegistry(host, port);
        UpdateRepositoryTask.of(journal, getRemoteBatchService(registry)).call();
        log.info("Finished importing of journal");
    }

    @SuppressWarnings("unchecked") //getting object known by a contract
    private static RemoteEmployeeBatchUpdateService getRemoteBatchService(Registry registry)
            throws RemoteException, NotBoundException {
        return (RemoteEmployeeBatchUpdateService) registry.lookup(RemoteEmployeeBatchUpdateService.NAME);
    }

    private static void setSecurityManager() {
        if (Objects.isNull(System.getSecurityManager())) {
            System.setSecurityManager(new SecurityManager());
        }
    }
}
