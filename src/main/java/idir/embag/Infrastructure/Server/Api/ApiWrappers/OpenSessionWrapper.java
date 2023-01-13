package idir.embag.Infrastructure.Server.Api.ApiWrappers;

import idir.embag.Application.Utility.Serialisers.GsonSerialiser;
import idir.embag.DataModels.Session.Session;
import idir.embag.Types.Api.EApi;
import idir.embag.Types.Api.IApiWrapper;

public class OpenSessionWrapper extends IApiWrapper {

    private Session session;

    public OpenSessionWrapper(Session session) {
        this.session = session;
        api = EApi.openSession;
    }

    public Session getSession() {
        return session;
    }

    @Override
    public String getJsonData() {
        return GsonSerialiser.serialise(session);
    }

    

}
    
