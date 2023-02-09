package idir.embag.Application.State;

public class ConfigState {

    public final String databaseName;
    public final String databaseUser;
    public final String databasePassword;
    public final String databaseHost;
    public final int databasePort;
    public final String serverHost;
    public final int serverPort;
    public final String serverAuthToken;
    public final int serverApiVersion;

    public ConfigState(String databaseName, String databaseUser, String databasePasword, String databaseHost,
            int databasePort, String serverHost, int servertPort, String serverAuthToken, int serverApiVersion) {
        this.databaseName = databaseName;
        this.databaseUser = databaseUser;
        this.databasePassword = databasePasword;
        this.databaseHost = databaseHost;
        this.databasePort = databasePort;
        this.serverHost = serverHost;
        this.serverPort = servertPort;
        this.serverAuthToken = serverAuthToken;
        this.serverApiVersion = serverApiVersion;
    }

    public static enum Keys {
        databaseName,
        databaseUser,
        databasePassword,
        databaseHost,
        databasePort,
        serverHost,
        serverPort,
        serverAuthToken,
        serverApiVersion
    }

    public static ConfigState defaultState() {

        return new ConfigState(
                "test", "idir", "idir", "127.0.0.1", 3306, "idir", 3050, "embag343adminvcs", 0);
    }
}

/*
}
 */