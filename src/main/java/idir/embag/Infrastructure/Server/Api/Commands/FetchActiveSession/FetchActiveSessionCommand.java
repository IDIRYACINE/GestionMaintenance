package idir.embag.Infrastructure.Server.Api.Commands.FetchActiveSession;

import idir.embag.Infrastructure.Server.Api.ServerConfigurations;
import idir.embag.Infrastructure.Server.Api.ApiWrappers.FetchActiveSessionWrapper;
import idir.embag.Infrastructure.Server.Api.Requests.FetchActiveSessionRecordsRequest;
import idir.embag.Infrastructure.Server.Api.ResponeHandlers.RecordsResponse;
import idir.embag.Infrastructure.ServiceProvider.Events.SimpleServiceCommand;
import idir.embag.Types.Api.EHeaders.Headers;

public class FetchActiveSessionCommand
        extends SimpleServiceCommand<FetchActiveSessionWrapper, FetchActiveSessionEvent> {
    final ServerConfigurations serverConfigurations;

    public FetchActiveSessionCommand(ServerConfigurations serverConfigurations) {
        this.serverConfigurations = serverConfigurations;
    }

    @Override
    public void execute(FetchActiveSessionEvent event) {
        FetchActiveSessionRecordsRequest request = new FetchActiveSessionRecordsRequest(event.getData());
        request.addHeader(Headers.access_token, serverConfigurations.authToken);

        RecordsResponse responseHandler = new RecordsResponse();
        request.setResponseHandler(responseHandler);
        request.execute();

    }

    @Override
    public int getEventId() {
        return FetchActiveSessionEvent.eventId;
    }
}
