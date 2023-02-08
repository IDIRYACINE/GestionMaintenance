package idir.embag.Infrastructure.Server.Api.Commands.RegisterSessionWorker;

import idir.embag.Infrastructure.Server.Api.ApiWrappers.RegisterSessionWorkerWrapper;
import idir.embag.Infrastructure.Server.Api.Commands.CommandsEnum;
import idir.embag.Infrastructure.ServiceProvider.Events.SimpleServiceEvent;

public class RegisterSessionWorkerEvent extends SimpleServiceEvent<RegisterSessionWorkerWrapper>{
    public static final int eventId = CommandsEnum.RegisterSessionWorker.ordinal();
    public RegisterSessionWorkerEvent(String requesterName, RegisterSessionWorkerWrapper data) {
        super(requesterName, data);
    }

    @Override
    public int getEventId() {
        return eventId;
    }
    
}
