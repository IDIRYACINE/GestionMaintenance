package idir.embag.Infrastructure.Server.Api.Requests;

import idir.embag.DataModels.Workers.SessionWorker;
import idir.embag.Infrastructure.Server.Api.ApiWrappers.UnregisterSessionWorkerWrapper;
import idir.embag.Types.Api.EApiQueries;
import idir.embag.Types.Api.IApi;
import java.io.IOException;
import idir.embag.Types.Api.IApiWrapper;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Response;

public class UnregisterSessionWorkerRequest extends IApi {

    OkHttpClient client;

    Callback resultCallback;

    HttpUrl url;

    public UnregisterSessionWorkerRequest(IApiWrapper wrapper) {
        client = new OkHttpClient();

        resultCallback = new Callback() {
            public void onResponse(Call call, Response response) throws IOException {
                responseHandler.handleResponse(response);
            }

            public void onFailure(Call call, IOException e) {
                handleFaillure(call);
            }
        };

        UnregisterSessionWorkerWrapper helper = (UnregisterSessionWorkerWrapper) wrapper;
        SessionWorker worker = helper.getSessionWorker();
        
        url = wrapper.getApiUrl()
                .addQueryParameter(EApiQueries.workerId.name(), String.valueOf(worker.getWorkerId()))
                .build();
    }

    @Override
    public void execute() {
        requestBuilder.url(url);
        addHeadersToRequest(requestBuilder);
        requestBuilder.build();

        Call call = client.newCall(requestBuilder.build());
        try {
            if (!isAsync) {
                Response response = call.execute();
                responseHandler.handleResponse(response);
            } else {
                call.enqueue(resultCallback);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}