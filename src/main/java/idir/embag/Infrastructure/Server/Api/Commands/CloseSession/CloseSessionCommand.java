package idir.embag.Infrastructure.Server.Api.Commands.CloseSession;

import java.util.concurrent.Future;

import idir.embag.Infrastructure.ServiceProvider.Events.Command;

public class CloseSessionCommand extends Command<CloseSessionData,CloseSessionRawData,CloseSessionResponse>{


    CloseSessionCommand() {
        super(CloseSessionMetadata.eventId, CloseSessionMetadata.eventName);
    }

    @Override
    public Future<CloseSessionResponse> handleEvent(CloseSessionData eventData) {
        
        return null;
    }

    @Override
    public Future<CloseSessionResponse> handleRawEvent(CloseSessionRawData eventData) {
        
        return null;
    }
    
}
