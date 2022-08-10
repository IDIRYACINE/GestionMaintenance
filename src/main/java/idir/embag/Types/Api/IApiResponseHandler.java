package idir.embag.Types.Api;

import okhttp3.Response;

public interface IApiResponseHandler {
    public void handleResponse(Response response);
}
