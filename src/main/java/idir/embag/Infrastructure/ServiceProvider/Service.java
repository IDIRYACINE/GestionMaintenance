package idir.embag.Infrastructure.ServiceProvider;

import java.util.stream.Stream;

import idir.embag.Infrastructure.ServiceProvider.Algorithms.Comparator;
import idir.embag.Infrastructure.ServiceProvider.Algorithms.SearchAlgorithm;
import idir.embag.Infrastructure.ServiceProvider.Events.SimpleServiceCommand;
import idir.embag.Infrastructure.ServiceProvider.Events.SimpleServiceEvent;
import idir.embag.Infrastructure.ServiceProvider.Types.SimpleCommandSearchAglorithm;
import idir.embag.Infrastructure.ServiceProvider.Events.ServiceEvent;

@SuppressWarnings("rawtypes")
public abstract class Service {
    public int serviceId;
    String serviceName;
    Stream stream;

    SimpleServiceCommand emptyCommand = new SimpleServiceCommand.EmptyCommand();

    protected SimpleServiceCommand[] commands ;

    protected final SearchAlgorithm<SimpleServiceCommand, Integer, Comparator<SimpleServiceCommand, Integer>> searchAlgorithm;

    public Service(SimpleCommandSearchAglorithm searchAlgorithm) {
        this.searchAlgorithm = searchAlgorithm;
    }


    public void registerCommandAtIndex(SimpleServiceCommand command) {
        commands[command.getEventId()] = command;
    }


    public void unregisterCommand(SimpleServiceCommand command) {
        commands[command.getEventId()] = emptyCommand;
    }

    public void unregisterCommandById(int commandId) {
        SimpleServiceCommand command = searchAlgorithm.search(commands, commandId);
        if (command != null) {
            commands[commandId] = emptyCommand;
        }
    }

    public abstract  void sendEvent(ServiceEvent event);

    public abstract  void dispatchEvent(SimpleServiceEvent event);

}