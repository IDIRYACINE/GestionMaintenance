package idir.embag.Types.Api;

import okhttp3.HttpUrl;
import java.net.InetAddress;
import java.net.UnknownHostException;

public abstract class IApiWrapper {

    protected EApi api;

    protected int apiVersion = 0;

    protected boolean enableApiVersion = true;

    protected String apiBasePath = "api";

    protected String apiVersionPath = "v";

    protected String domain = "embag.duckdns.org";

    protected String host = null;

    protected String protocol = "http";

    protected int port = 3050;

    public void setApi(EApi api) {
        this.api = api;
    }

    /**
     * Defaults to version 0
     * 
     * @param apiVersion
     */
    public void setApiVersion(int apiVersion) {
        this.apiVersion = apiVersion;
    }

    /**
     * Defaults to true
     * 
     * @param bool
     */
    public void enableApiVersion(boolean bool) {
        enableApiVersion = bool;
    }

    /**
     * Defaults to v eg:/v/0/login
     * 
     * @param versionPath defaults to v
     */
    public void setApiVersionPath(String versionPath) {
        apiVersionPath = versionPath;
    }

    public String getJsonData() {
        throw new UnsupportedOperationException("Operation not supported");
    }

    public void setHost(String protocol, String host, int port) {
        this.host = host;
        this.protocol = protocol;
        this.port = port;
    }

    public EApi getApi() {
        return api;
    }

    public HttpUrl.Builder getApiUrl() {
        if (host == null) {
            host = resolveDomainIp();
        }
        HttpUrl.Builder apiUrl = new HttpUrl.Builder();
        apiUrl.scheme(protocol);
        apiUrl.host(host);
        apiUrl.port(port);
        apiUrl.addPathSegment(apiBasePath);
        apiUrl.addPathSegment(apiVersionPath + String.valueOf(apiVersion));
        apiUrl.addPathSegment(api.toString());
        return apiUrl;
    }

    private String resolveDomainIp() {
        try {
            InetAddress inetAddress = InetAddress.getByName(domain);
            return inetAddress.getHostAddress();
        } catch (UnknownHostException e) {
            e.printStackTrace();
            return domain;

        }
    }
}
