package idir.embag.Infrastructure.Server.Api.Commands.CloseSession;

import idir.embag.Infrastructure.Server.Api.ServerConfigurations;
import idir.embag.Infrastructure.Server.Api.ApiWrappers.CloseSessionWrapper;
import idir.embag.Infrastructure.Server.Api.Commands.CommandsEnum;
import idir.embag.Infrastructure.Server.Api.Requests.CloseSessionRequest;
import idir.embag.Infrastructure.Server.Api.ResponeHandlers.SessionResponse;
import idir.embag.Infrastructure.ServiceProvider.Events.SimpleServiceCommand;
import idir.embag.Types.Api.EHeaders.Headers;

public class CloseSessionCommand extends SimpleServiceCommand<CloseSessionWrapper,CloseSessionEvent>{
    private static final int eventId = CommandsEnum.CloseSession.ordinal();
    final ServerConfigurations serverConfigurations;
    
    public CloseSessionCommand(ServerConfigurations serverConfigurations) {
        this.serverConfigurations = serverConfigurations;
    }
    
    @Override
    public void execute(CloseSessionEvent event) {
        CloseSessionRequest request = new CloseSessionRequest(event.getData());
        request.addHeader(Headers.access_token, serverConfigurations.authToken);

        SessionResponse responseHandler = new SessionResponse();
        request.setResponseHandler(responseHandler);
        request.execute();
        
    }

    @Override
    public int getEventId() {
        return eventId;
    }
    
}