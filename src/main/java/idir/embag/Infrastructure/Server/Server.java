package idir.embag.Infrastructure.Server;

import idir.embag.Infrastructure.Server.Api.ServerConfigurations;
import idir.embag.Infrastructure.Server.Api.Commands.CommandsEnum;
import idir.embag.Infrastructure.Server.Api.Commands.FetchActiveSession.FetchActiveSessionCommand;
import idir.embag.Infrastructure.Server.Api.Commands.FetchActiveSessionRecords.FetchActiveSessionRecordsCommand;
import idir.embag.Infrastructure.Server.Api.Commands.Login.LoginCommand;
import idir.embag.Infrastructure.Server.Api.Commands.OpenSession.OpenSessionCommand;
import idir.embag.Infrastructure.Server.Api.Commands.UnregisterSessionWorker.UnregisterSessionWorkerCommand;
import idir.embag.Infrastructure.Server.Api.Commands.UpdateSessionWorker.UpdateSessionWorkerCommand;
import idir.embag.Infrastructure.Server.WebSocket.WebSocketImpl;
import java.util.function.BiFunction;
import idir.embag.Infrastructure.ServiceProvider.Service;
import idir.embag.Infrastructure.ServiceProvider.Algorithms.SearchAlgorithm;
import idir.embag.Infrastructure.ServiceProvider.Algorithms.BinarySearch.BinarySearchAlgorithm;
import idir.embag.Infrastructure.ServiceProvider.Algorithms.BinarySearch.BinarySearchComparator;
import idir.embag.Infrastructure.ServiceProvider.Events.ServiceEvent;
import idir.embag.Infrastructure.ServiceProvider.Events.SimpleServiceCommand;
import idir.embag.Infrastructure.ServiceProvider.Events.SimpleServiceEvent;
import idir.embag.Infrastructure.ServiceProvider.Types.SimpleCommandSearchAglorithm;

@SuppressWarnings({ "rawtypes", "unchecked" })
public class Server extends Service {

    public WebSocketImpl webSocketClient;

    private final ServerConfigurations serverConfigurations;

    private Server(SimpleCommandSearchAglorithm searchAlgorithm, ServerConfigurations serverConfigurations) {
        super(searchAlgorithm);
        this.serverConfigurations = serverConfigurations;
        registerDefaultCommands();
    }

    private static Server instance;

    public static Server getInstance() {
        return instance;
    }

    public static Server getInstance(ServerConfigurations serverConfigurations) {
        if (instance == null) {
            SimpleCommandSearchAglorithm searchAlgorithm = (SimpleCommandSearchAglorithm) ((SearchAlgorithm) createSearchAlgorithm());

            instance = new Server(searchAlgorithm, serverConfigurations);
        }
        return instance;
    }

    private static BinarySearchAlgorithm<SimpleServiceCommand, Integer> createSearchAlgorithm() {
        BinarySearchAlgorithm<SimpleServiceCommand, Integer> searchAlgorithm = new BinarySearchAlgorithm<SimpleServiceCommand, Integer>();

        BiFunction<SimpleServiceCommand, Integer, Boolean> isGreaterThan = (command, id) -> command.getEventId() > id;
        BiFunction<SimpleServiceCommand, Integer, Boolean> isLessThan = (command, id) -> command.getEventId() < id;

        BinarySearchComparator<SimpleServiceCommand, Integer> comparator = new BinarySearchComparator<SimpleServiceCommand, Integer>(
                isGreaterThan, isLessThan);

        searchAlgorithm.setComparator(comparator);

        return searchAlgorithm;
    }

    private void registerDefaultCommands() {
        int length = CommandsEnum.values().length;
        commands = new SimpleServiceCommand[length];

        SimpleServiceCommand.EmptyCommand emptyCommand = new SimpleServiceCommand.EmptyCommand();
        for (int i = 0; i < length; i++) {
            commands[i] = emptyCommand;
        }

        registerCommandAtIndex(new LoginCommand(serverConfigurations));
        registerCommandAtIndex(new UpdateSessionWorkerCommand(serverConfigurations));
        registerCommandAtIndex(new UnregisterSessionWorkerCommand(serverConfigurations));
        registerCommandAtIndex(new OpenSessionCommand(serverConfigurations));
        registerCommandAtIndex(new FetchActiveSessionCommand(serverConfigurations));
        registerCommandAtIndex(new FetchActiveSessionRecordsCommand(serverConfigurations));

    }

    @Override
    public void sendEvent(ServiceEvent event) {

    }

    @Override
    public void dispatchEvent(SimpleServiceEvent event) {
        SimpleServiceCommand command = searchAlgorithm.search(commands, event.getEventId());
        if (command != null) {
            command.execute(event);
        }

    }

}
