package idir.embag.Infrastructure.Server.Api.Commands.CloseSession;

import idir.embag.Infrastructure.ServiceProvider.Events.ServiceEventResponse;
import idir.embag.Infrastructure.ServiceProvider.Events.ServiceEventResponseStatus;

public class CloseSessionResponse extends ServiceEventResponse {

    public CloseSessionResponse(int messageId, ServiceEventResponseStatus responseType) {
        super(messageId, responseType);
    }

}
