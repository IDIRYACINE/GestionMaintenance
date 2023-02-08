package idir.embag.Infrastructure.ServiceProvider;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Future;
import java.util.stream.Stream;

import idir.embag.Infrastructure.ServiceProvider.Algorithms.Comparator;
import idir.embag.Infrastructure.ServiceProvider.Algorithms.SearchAlgorithm;
import idir.embag.Infrastructure.ServiceProvider.Events.Command;
import idir.embag.Infrastructure.ServiceProvider.Events.RawServiceEventData;
import idir.embag.Infrastructure.ServiceProvider.Events.ServiceEvent;
import idir.embag.Infrastructure.ServiceProvider.Events.ServiceEventResponse;

@SuppressWarnings("rawtypes")
public abstract class Service {
    public int serviceId;
    String serviceName;
    Stream stream;

    List<Command> commands = new ArrayList<Command>();

    final SearchAlgorithm<Command, Integer, Comparator<Command, Integer>> searchAlgorithm;

    public Service(SearchAlgorithm<Command, Integer, Comparator<Command, Integer>> searchAlgorithm) {
        this.searchAlgorithm = searchAlgorithm;
    }

    void registerCommand(Command command) {
        commands.add(command);
    }

    void registerCommandAtIndex(Command command) {
        commands.add(command.commandId, command);
    }

    void replaceCommandAtIndex(Command command) {
        commands.set(command.commandId, command);
    }

    void unregisterCommand(Command command) {
        commands.remove(command);
    }

    void unregisterCommandById(int commandId) {
        Command command = searchAlgorithm.search(commands, commandId);
        if (command != null) {
            commands.remove(command);
        }
    }

    abstract void onEventForCallback(ServiceEvent event);

    abstract Future<ServiceEventResponse> onEventForResponse(ServiceEvent event);

    abstract Future<ServiceEventResponse> onRawEvent(RawServiceEventData event);
}