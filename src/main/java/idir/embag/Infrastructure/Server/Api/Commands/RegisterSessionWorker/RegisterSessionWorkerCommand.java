package idir.embag.Infrastructure.Server.Api.Commands.RegisterSessionWorker;

import idir.embag.Infrastructure.Server.Api.ServerConfigurations;
import idir.embag.Infrastructure.Server.Api.ApiWrappers.RegisterSessionWorkerWrapper;
import idir.embag.Infrastructure.Server.Api.Requests.RegisterSessionWorkerRequest;
import idir.embag.Infrastructure.Server.Api.ResponeHandlers.WorkerOperationResponse;
import idir.embag.Infrastructure.ServiceProvider.Events.SimpleServiceCommand;
import idir.embag.Types.Api.EHeaders.Headers;

public class RegisterSessionWorkerCommand extends SimpleServiceCommand<RegisterSessionWorkerWrapper,RegisterSessionWorkerEvent>{
    final ServerConfigurations serverConfigurations;
        
    public RegisterSessionWorkerCommand(ServerConfigurations serverConfigurations) {
        this.serverConfigurations = serverConfigurations;
    }

    @Override
    public void execute(RegisterSessionWorkerEvent event) {
            RegisterSessionWorkerRequest request = new RegisterSessionWorkerRequest(event.getData());
            request.addHeader(Headers.access_token, serverConfigurations.authToken);
    
            WorkerOperationResponse responseHandler = new WorkerOperationResponse();
            request.setResponseHandler(responseHandler);
            request.execute();
        
    }

    @Override
    public int getEventId() {
       return RegisterSessionWorkerEvent.eventId;
    }
    
}
