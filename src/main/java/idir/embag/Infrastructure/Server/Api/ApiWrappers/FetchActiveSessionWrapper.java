package idir.embag.Infrastructure.Server.Api.ApiWrappers;

import idir.embag.Types.Api.EApi;
import idir.embag.Types.Api.IApiWrapper;

public class FetchActiveSessionWrapper extends IApiWrapper{

    public FetchActiveSessionWrapper() {
        api = EApi.fetchActiveSession;

    }
    
}
