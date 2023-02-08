package idir.embag.Infrastructure.ServiceProivder;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Future;
import java.util.stream.Stream;

import idir.embag.Infrastructure.ServiceProivder.Events.Command;
import idir.embag.Infrastructure.ServiceProivder.Events.RawServiceEventData;
import idir.embag.Infrastructure.ServiceProivder.Events.ServiceEvent;
import idir.embag.Infrastructure.ServiceProivder.Events.ServiceEventResponse;
import idir.embag.Infrastructure.ServiceProivder.Algorithms.Comparator;
import idir.embag.Infrastructure.ServiceProivder.Algorithms.SearchAlgorithm;

@SuppressWarnings("rawtypes")
public abstract class Service {
    int serviceId;
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