package idir.embag.Infrastructure.Server.Api.ApiWrappers;

import idir.embag.Application.Utility.GsonSerialiser;
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

    @Override
    public String getJsonData() {
        return GsonSerialiser.serialise(sessionWorker);
    }

    
}
