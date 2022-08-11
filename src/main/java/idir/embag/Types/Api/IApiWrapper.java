package idir.embag.Types.Api;

import okhttp3.HttpUrl;

public abstract class IApiWrapper {

    protected EApi api;

    protected int apiVersion = 0;

    protected boolean enableApiVersion = true;

    protected String apiVersionPath = "v";

    protected String host = "localhost";

    protected String protocol = "http";

    protected int port = 3000;
    
    public void setApi(EApi api){
        this.api = api;
    }

    /**
     * Defaults to version 0 
     * @param apiVersion
     */
    public void setApiVersion(int apiVersion){
        this.apiVersion = apiVersion;
    }

    /**
     * Defaults to true
     * @param bool
     */
    public void enableApiVersion(boolean bool){
        enableApiVersion = bool;
    }
    
    /**
     * Defaults to v eg:/v/0/login
     * @param versionPath defaults to v
     */
    public void setApiVersionPath(String versionPath){
        apiVersionPath = versionPath;
    }

    public void setHost(String protocol ,String host,int port){
        this.host = host;
        this.protocol = protocol;
        this.port = port;
    }

    public EApi getApi(){
        return api;
    }

    public HttpUrl.Builder getApiUrl(){
        HttpUrl.Builder apiUrl = new HttpUrl.Builder();
        apiUrl.scheme(protocol);
        apiUrl.host(host);
        apiUrl.port(port);
        apiUrl.addPathSegment(apiVersionPath);
        apiUrl.addPathSegment(String.valueOf(apiVersion));
        apiUrl.addPathSegment(api.toString());
        return apiUrl;
    }
}
