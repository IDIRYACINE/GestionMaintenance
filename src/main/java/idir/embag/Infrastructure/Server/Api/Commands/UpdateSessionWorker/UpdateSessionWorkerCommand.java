package idir.embag.Infrastructure.Server.Api.Commands.UpdateSessionWorker;

import idir.embag.Infrastructure.Server.Api.ServerConfigurations;
import idir.embag.Infrastructure.Server.Api.ApiWrappers.UpdateSessionWorkerWrapper;
import idir.embag.Infrastructure.Server.Api.Requests.UpdateSessionWorkerRequest;
import idir.embag.Infrastructure.Server.Api.ResponeHandlers.WorkerOperationResponse;
import idir.embag.Infrastructure.ServiceProvider.Events.SimpleServiceCommand;
import idir.embag.Types.Api.EHeaders.Headers;

public class UpdateSessionWorkerCommand
        extends SimpleServiceCommand<UpdateSessionWorkerWrapper, UpdateSessionWorkerEvent> {
    final ServerConfigurations serverConfigurations;

    public UpdateSessionWorkerCommand(ServerConfigurations serverConfigurations) {
        this.serverConfigurations = serverConfigurations;
    }

    @Override
    public void execute(UpdateSessionWorkerEvent event) {
        UpdateSessionWorkerRequest request = new UpdateSessionWorkerRequest(event.getData());
        request.addHeader(Headers.access_token, serverConfigurations.authToken);

        WorkerOperationResponse responseHandler = new WorkerOperationResponse();
        request.setResponseHandler(responseHandler);
        request.execute();

    }

    @Override
    public int getEventId() {
        return UpdateSessionWorkerEvent.eventId;
    }

}
