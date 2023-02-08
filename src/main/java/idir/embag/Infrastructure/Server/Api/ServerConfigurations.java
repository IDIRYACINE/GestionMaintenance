package idir.embag.Infrastructure.Server.Api;

public class ServerConfigurations {
    public final String authToken;

    public final int apiVersion;

    public final String serverPath;

    public final int port;

    public ServerConfigurations(String authToken, int apiVersion, String serverPath, int port) {
        this.authToken = authToken;
        this.apiVersion = apiVersion;
        this.serverPath = serverPath;
        this.port = port;
    }
}
