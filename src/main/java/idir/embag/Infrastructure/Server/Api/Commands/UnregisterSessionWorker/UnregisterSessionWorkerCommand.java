package idir.embag.Infrastructure.Server.Api.Commands.UnregisterSessionWorker;

import idir.embag.Infrastructure.Server.Api.ServerConfigurations;
import idir.embag.Infrastructure.Server.Api.ApiWrappers.UnregisterSessionWorkerWrapper;
import idir.embag.Infrastructure.Server.Api.Requests.UnregisterSessionWorkerRequest;
import idir.embag.Infrastructure.Server.Api.ResponeHandlers.WorkerOperationResponse;
import idir.embag.Infrastructure.ServiceProvider.Events.SimpleServiceCommand;
import idir.embag.Types.Api.EHeaders.Headers;

public class UnregisterSessionWorkerCommand
        extends SimpleServiceCommand<UnregisterSessionWorkerWrapper, UnregisterSessionWorkerEvent> {
    final ServerConfigurations serverConfigurations;

    public UnregisterSessionWorkerCommand(ServerConfigurations serverConfigurations) {
        this.serverConfigurations = serverConfigurations;
    }

    @Override
    public void execute(UnregisterSessionWorkerEvent event) {

        UnregisterSessionWorkerRequest request = new UnregisterSessionWorkerRequest(event.getData());
        request.addHeader(Headers.access_token, serverConfigurations.authToken);

        WorkerOperationResponse responseHandler = new WorkerOperationResponse();
        request.setResponseHandler(responseHandler);
        request.execute();

    }

    @Override
    public int getEventId() {
        return UnregisterSessionWorkerEvent.eventId;
    }

}
