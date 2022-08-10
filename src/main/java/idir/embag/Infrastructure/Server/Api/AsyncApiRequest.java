package idir.embag.Infrastructure.Server.Api;

import java.io.IOException;
import idir.embag.Types.Api.IApi;
import idir.embag.Types.Api.IApiWrapper;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Response;

public class AsyncApiRequest extends IApi {

    OkHttpClient client;

    Callback resultCallback;

    public AsyncApiRequest(IApiWrapper wrapper){
        client = new OkHttpClient();

        resultCallback = new Callback() {
            public void onResponse(Call call, Response response) throws IOException {
                responseHandler.handleResponse(response);
            }
            
            public void onFailure(Call call, IOException e) {
                handleFaillure(call);
            }
        };

        builder.url(wrapper.getApiUrl());
    }

    @Override
    public void execute() {
        builder.build();        
        addHeadersToRequest(builder);
        
        Call call = client.newCall(builder.build());
        call.enqueue(resultCallback);
    }

}
