package idir.embag.Infrastructure.Server.Api.Commands.UnregisterSessionWorker;

import idir.embag.Infrastructure.Server.Api.ApiWrappers.UnregisterSessionWorkerWrapper;
import idir.embag.Infrastructure.Server.Api.Commands.CommandsEnum;
import idir.embag.Infrastructure.ServiceProvider.Events.SimpleServiceEvent;

public class UnregisterSessionWorkerEvent extends SimpleServiceEvent<UnregisterSessionWorkerWrapper>{
    public static final int eventId = CommandsEnum.UnregisterSessionWorker.ordinal();

    public UnregisterSessionWorkerEvent(String requesterName, UnregisterSessionWorkerWrapper data) {
        super(requesterName, data);
    }


    @Override
    public int getEventId() {
        return eventId;
    }
    
}
