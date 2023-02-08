package idir.embag.Infrastructure.Server.Api.Commands.CloseSession;

import idir.embag.Infrastructure.Server.Api.ApiWrappers.CloseSessionWrapper;
import idir.embag.Infrastructure.Server.Api.Commands.CommandsEnum;
import idir.embag.Infrastructure.ServiceProvider.Events.SimpleServiceEvent;

public class CloseSessionEvent extends SimpleServiceEvent<CloseSessionWrapper> {
    public static final int eventId = CommandsEnum.CloseSession.ordinal();


    public CloseSessionEvent(String requesterName, CloseSessionWrapper data) {
        super(requesterName, data);
    }

    @Override
    public int getEventId() {
        return eventId;
    }

}
