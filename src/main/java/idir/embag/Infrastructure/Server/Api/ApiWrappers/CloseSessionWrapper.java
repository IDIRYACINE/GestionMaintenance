package idir.embag.Infrastructure.Server.Api.ApiWrappers;

import idir.embag.Types.Api.EApi;
import idir.embag.Types.Api.IApiWrapper;
import idir.embag.Types.Infrastructure.Server.EServerKeys;
import okhttp3.HttpUrl.Builder;

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

    @Override
    public Builder getApiUrl() {
        Builder urlBuilder =  super.getApiUrl();
        urlBuilder.addQueryParameter(EServerKeys.sessionId.toString(), String.valueOf(sessionId));
        return urlBuilder;
    }
    
}
    