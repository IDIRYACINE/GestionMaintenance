package idir.embag.Infrastructure.Server.Api.ApiWrappers;

import idir.embag.Types.Api.EApi;
import idir.embag.Types.Api.IApiWrapper;

public class CloseSessionWrapper extends IApiWrapper {

    private int sessionId;

    public CloseSessionWrapper(int sessionId) {
        this.sessionId = sessionId;
        api = EApi.closeSession;
    }

    public void setSessionId(int sessionId) {
        this.sessionId = sessionId;
    }

    public int getSessionId() {
        return sessionId;
    }
    
    
}
    