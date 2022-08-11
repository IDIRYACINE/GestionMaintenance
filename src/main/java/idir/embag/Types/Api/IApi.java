package idir.embag.Types.Api;

import java.util.ArrayList;
import javafx.util.Pair;
import okhttp3.Call;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;
import idir.embag.Types.Api.EHeaders.Headers;


public abstract class IApi {

    ArrayList<Pair<String,String>> headers = new ArrayList<>();

    protected IApiResponseHandler responseHandler;

    protected Request.Builder requestBuilder = new Request.Builder();

    public static final MediaType JSON = MediaType.get("application/json; charset=utf-8");

    /**
     * 
     * @param async defaults to false
     */
    public abstract void setAsync(boolean async);
    
    public abstract void execute();

    public void setResponseHandler(IApiResponseHandler handler){
        responseHandler = handler;
    }

    public void addHeader(Headers key, String value) {
        String headerKey = EHeaders.valueOf(key);
        headers.add(new Pair<String,String>(headerKey,value));
    }

    /**
     * this turns the http request into post
     * @param bodyJson
     */
    public void addPostBody(String bodyJson){
        RequestBody requestBody =  RequestBody.create(bodyJson,JSON);
        requestBuilder.post(requestBody);
    }

    protected void addHeadersToRequest(Request.Builder builder) {
        for(Pair<String,String> pair : headers) {
            builder.addHeader(pair.getKey(), pair.getValue());
        }
    }

    protected void handleFaillure(Call call){

    }

}
