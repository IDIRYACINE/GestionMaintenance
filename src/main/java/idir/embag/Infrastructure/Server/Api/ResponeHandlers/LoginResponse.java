package idir.embag.Infrastructure.Server.Api.ResponeHandlers;

import java.io.IOException;
import idir.embag.Application.Utility.GsonSerialiser;
import idir.embag.DataModels.ApiBodyResponses.DLoginResponse;
import idir.embag.Types.Api.IApiResponseHandler;
import okhttp3.Response;

public class LoginResponse implements IApiResponseHandler {

    Runnable initWebSocketCallback;

    public LoginResponse(Runnable initWebSocket) {
        this.initWebSocketCallback = initWebSocket;
    }

    @Override
    public void handleResponse(Response response) {
        try {
            String jsonBody = response.body().string();
            if (response.code() == 200) {

                DLoginResponse parsedResponse = GsonSerialiser.deserialise(jsonBody, DLoginResponse.class);

                if (parsedResponse.isAutherised) {

                    initWebSocketCallback.run();

                             
                    return;
                }

            } else {
                System.out.println("response : " + jsonBody);
            }
        }

        catch (IOException e) {
            e.printStackTrace();
        }
    }


}
