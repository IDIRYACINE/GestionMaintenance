package idir.embag.Types.Api;

import java.net.MalformedURLException;
import java.net.URL;

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

    public URL getApiUrl(){
        String apiPath = "/"+apiVersionPath+"/"+apiVersion+"/"+api;
        URL apiUrl = null;
        try {
            apiUrl = new URL(protocol,host,port,apiPath);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return apiUrl;
    }

    public EApi getApi(){
        return api;
    }
}
