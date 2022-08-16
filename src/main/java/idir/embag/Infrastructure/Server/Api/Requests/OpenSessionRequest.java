package idir.embag.Infrastructure.Server.Api.Requests;

import idir.embag.Types.Api.IApi;
import java.io.IOException;
import idir.embag.Types.Api.IApiWrapper;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import okhttp3.Response;

public class OpenSessionRequest extends IApi{

    OkHttpClient client;

    Callback resultCallback;

    HttpUrl url;

    RequestBody requestBody;

    public OpenSessionRequest(IApiWrapper wrapper){
        client = new OkHttpClient();

        resultCallback = new Callback() {
            public void onResponse(Call call, Response response) throws IOException {
                responseHandler.handleResponse(response);
            }
            
            public void onFailure(Call call, IOException e) {
                handleFaillure(call);
            }
        };
        
        url = wrapper.getApiUrl().build();

        String jsonData = wrapper.getJsonData();
        requestBody = RequestBody.create(jsonData, JSON);
    }

    @Override
    public void execute() {
        requestBuilder.url(url);
        requestBuilder.setBody$okhttp(requestBody);
        addHeadersToRequest(requestBuilder);
        requestBuilder.build();        
        
        Call call = client.newCall(requestBuilder.build());
        try {
            if(!isAsync){
                Response response = call.execute();
                responseHandler.handleResponse(response);
            }
            else{
                call.enqueue(resultCallback);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
}