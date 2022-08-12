package idir.embag.Infrastructure.Server.Api.ApiWrappers;

import idir.embag.DataModels.Workers.SessionWorker;
import idir.embag.Types.Api.EApi;
import idir.embag.Types.Api.IApiWrapper;

public class RegisterSessionWorkerWrapper extends IApiWrapper{
    SessionWorker sessionWorker;

    public RegisterSessionWorkerWrapper(SessionWorker sessionWorker) {
        this.sessionWorker = sessionWorker;
        api = EApi.registerSessionWorker;
    }

    public SessionWorker getSessionWorker() {
        return sessionWorker;
    }

    
}
