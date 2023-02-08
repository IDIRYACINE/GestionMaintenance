package idir.embag.Infrastructure.Server.Api.Commands.UpdateSessionWorker;

import idir.embag.Infrastructure.Server.Api.ApiWrappers.UpdateSessionWorkerWrapper;
import idir.embag.Infrastructure.Server.Api.Commands.CommandsEnum;
import idir.embag.Infrastructure.ServiceProvider.Events.SimpleServiceEvent;

public class UpdateSessionWorkerEvent extends SimpleServiceEvent<UpdateSessionWorkerWrapper>{
    public static final int eventId = CommandsEnum.UpdateSessionWorker.ordinal();
    
    public UpdateSessionWorkerEvent(String requesterName, UpdateSessionWorkerWrapper data) {
        super(requesterName, data);
    }

    @Override
    public int getEventId() {
        return eventId;
    }
    
}
