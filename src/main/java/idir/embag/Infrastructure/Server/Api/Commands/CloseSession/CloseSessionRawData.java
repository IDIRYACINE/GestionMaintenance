package idir.embag.Infrastructure.Server.Api.Commands.CloseSession;

import idir.embag.Infrastructure.ServiceProvider.Events.RawServiceEventData;

public class CloseSessionRawData extends RawServiceEventData{

    public CloseSessionRawData(String requesterId, int messageId, int eventId) {
        super(requesterId, messageId, eventId);
    }
    
}
