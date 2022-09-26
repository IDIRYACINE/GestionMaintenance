package idir.embag.Types.Infrastructure.Database;

public abstract class IConnectionParameters {

    private String databaseName;
    private String databaseUser;
    private String databasePassword;
    private String databaseHost;
    private int databasePort;
    protected String databaseDriver;

    public IConnectionParameters(String databaseName, String databaseUser, String databasePassword, String databaseHost,
            int databasePort) {
        this.databaseName = databaseName;
        this.databaseUser = databaseUser;
        this.databasePassword = databasePassword;
        this.databaseHost = databaseHost;
        this.databasePort = databasePort;
    }

    public String getDatabaseName() {
        return databaseName;
    }

    public String getDatabaseUser() {
        return databaseUser;
    }

    public String getDatabasePassword() {
        return databasePassword;
    }

    public String getDatabaseHost() {
        return databaseHost;
    }

    public int getDatabasePort() {
        return databasePort;
    }

    public String getDatabaseDriver() {
        return databaseDriver;
    }

}
