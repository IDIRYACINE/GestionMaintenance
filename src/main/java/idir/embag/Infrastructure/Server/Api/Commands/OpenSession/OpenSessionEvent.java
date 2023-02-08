package idir.embag.Infrastructure.Server.Api.Commands.OpenSession;

import idir.embag.Infrastructure.Server.Api.ApiWrappers.OpenSessionWrapper;
import idir.embag.Infrastructure.Server.Api.Commands.CommandsEnum;
import idir.embag.Infrastructure.ServiceProvider.Events.SimpleServiceEvent;

public class OpenSessionEvent extends SimpleServiceEvent<OpenSessionWrapper> {
    public static final int eventId = CommandsEnum.OpenSession.ordinal();

    public OpenSessionEvent(String requesterName, OpenSessionWrapper data) {
        super(requesterName, data);
    }


    @Override
    public int getEventId() {
        return eventId;
    }
    
}
