package idir.embag.Infrastructure.Server.Api.Commands.FetchActiveSessionRecords;

import idir.embag.Infrastructure.Server.Api.ServerConfigurations;
import idir.embag.Infrastructure.Server.Api.ApiWrappers.FetchActiveSessionRecordsWrapper;
import idir.embag.Infrastructure.Server.Api.Requests.FetchActiveSessionRequest;
import idir.embag.Infrastructure.Server.Api.ResponeHandlers.SessionResponse;
import idir.embag.Infrastructure.ServiceProvider.Events.SimpleServiceCommand;
import idir.embag.Types.Api.EHeaders.Headers;

public class FetchActiveSessionRecordsCommand extends SimpleServiceCommand<FetchActiveSessionRecordsWrapper, FetchActiveSessionRecordsEvent>{
    final ServerConfigurations serverConfigurations;
    
    public FetchActiveSessionRecordsCommand(ServerConfigurations serverConfigurations) {
        this.serverConfigurations = serverConfigurations;
    }
    
    @Override
    public void execute(FetchActiveSessionRecordsEvent event) {
        FetchActiveSessionRequest request = new FetchActiveSessionRequest(event.getData());
        request.addHeader(Headers.access_token, serverConfigurations.authToken);

        SessionResponse responseHandler = new SessionResponse();
        request.setResponseHandler(responseHandler);
        request.execute();
        
    }

    @Override
    public int getEventId() {
        return FetchActiveSessionRecordsEvent.eventId;
    }
}
