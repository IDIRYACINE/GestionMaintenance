package idir.embag.Infrastructure.Server.Api.ApiWrappers;

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

}
    
