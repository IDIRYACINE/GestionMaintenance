package idir.embag.Infrastructure.Server.Api.ApiWrappers;

import idir.embag.DataModels.Workers.SessionWorker;
import idir.embag.Types.Api.EApi;
import idir.embag.Types.Api.IApiWrapper;

public class UpdateSessionWorkerWrapper extends IApiWrapper{
    SessionWorker sessionWorker;

    public UpdateSessionWorkerWrapper(SessionWorker sessionWorker) {
        this.sessionWorker = sessionWorker;
        api = EApi.updateSessionWorker;
    }
}
