package idir.embag.Infrastructure.Server.Api.Commands.FetchActiveSession;

import idir.embag.Infrastructure.Server.Api.ApiWrappers.FetchActiveSessionWrapper;
import idir.embag.Infrastructure.Server.Api.Commands.CommandsEnum;
import idir.embag.Infrastructure.ServiceProvider.Events.SimpleServiceEvent;

public class FetchActiveSessionEvent extends SimpleServiceEvent<FetchActiveSessionWrapper>{
    public static final int eventId = CommandsEnum.FetchActiveSession.ordinal();
    
    public FetchActiveSessionEvent(String requesterName, FetchActiveSessionWrapper data) {
        super(requesterName, data);
    }

    @Override
    public int getEventId() {
        return eventId;
    }
}
