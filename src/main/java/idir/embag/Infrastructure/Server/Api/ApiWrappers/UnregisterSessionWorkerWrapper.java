package idir.embag.Infrastructure.Server.Api.ApiWrappers;

import idir.embag.DataModels.Workers.SessionWorker;
import idir.embag.Types.Api.EApi;
import idir.embag.Types.Api.IApiWrapper;

public class UnregisterSessionWorkerWrapper extends IApiWrapper{
    SessionWorker sessionWorker;

    public UnregisterSessionWorkerWrapper(SessionWorker sessionWorker) {
        this.sessionWorker = sessionWorker;
        api = EApi.unregisterSessionWorker;
    }

    public SessionWorker getSessionWorker() {
        return sessionWorker;
    }

    
}
