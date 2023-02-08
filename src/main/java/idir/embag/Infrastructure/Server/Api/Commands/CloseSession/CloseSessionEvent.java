package idir.embag.Infrastructure.Server.Api.Commands.CloseSession;

import java.util.function.Consumer;
import idir.embag.Infrastructure.ServiceProvider.Events.ServiceEvent;
import idir.embag.Infrastructure.ServiceProvider.Events.ServiceEventData;

public class CloseSessionEvent extends ServiceEvent<CloseSessionRawData, CloseSessionResponse> {
   

    public CloseSessionEvent(
            ServiceEventData<CloseSessionRawData> eventData, Consumer<CloseSessionResponse> callback) {
        super(CloseSessionMetadata.serviceId, CloseSessionMetadata.eventId, CloseSessionMetadata.eventName, eventData, callback);
    }

}
