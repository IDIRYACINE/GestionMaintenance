package idir.embag.Infrastructure.Server.Api.Commands.CloseSession;

import idir.embag.Infrastructure.Server.Api.Commands.CommandsEnum;
import idir.embag.Infrastructure.ServiceProvider.Events.ServiceEventData;

public class CloseSessionData extends ServiceEventData<CloseSessionRawData> {
    static final int eventId = CommandsEnum.closeSession.ordinal();

    public CloseSessionData(String requesterId) {
        super(requesterId);
    }

    public void setMessageId (int messageId) {
        this.messageId = messageId;
    }

    

    @Override
    public CloseSessionRawData toRawServiceEventData() {
        return new CloseSessionRawData(requesterId, messageId, eventId);
    }

}
