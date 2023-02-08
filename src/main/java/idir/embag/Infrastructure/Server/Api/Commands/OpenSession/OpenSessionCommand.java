package idir.embag.Infrastructure.Server.Api.Commands.OpenSession;

import idir.embag.Infrastructure.Server.Api.ServerConfigurations;
import idir.embag.Infrastructure.Server.Api.ApiWrappers.OpenSessionWrapper;
import idir.embag.Infrastructure.Server.Api.Requests.OpenSessionRequest;
import idir.embag.Infrastructure.Server.Api.ResponeHandlers.SessionResponse;
import idir.embag.Infrastructure.ServiceProvider.Events.SimpleServiceCommand;
import idir.embag.Types.Api.EHeaders.Headers;

public class OpenSessionCommand extends SimpleServiceCommand<OpenSessionWrapper, OpenSessionEvent> {
    final ServerConfigurations serverConfigurations;

    public OpenSessionCommand(ServerConfigurations serverConfigurations) {
        this.serverConfigurations = serverConfigurations;
    }

    @Override
    public void execute(OpenSessionEvent event) {

        OpenSessionRequest request = new OpenSessionRequest(event.getData());
        request.addHeader(Headers.access_token, serverConfigurations.authToken);

        SessionResponse responseHandler = new SessionResponse();
        request.setResponseHandler(responseHandler);
        request.execute();

    }

    @Override
    public int getEventId() {
        return OpenSessionEvent.eventId;
    }

}
