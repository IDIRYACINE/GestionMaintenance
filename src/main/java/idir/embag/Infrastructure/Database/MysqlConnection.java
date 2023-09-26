package idir.embag.Infrastructure.Database;

import idir.embag.Types.Infrastructure.Database.IConnectionParameters;

public class MysqlConnection extends IConnectionParameters {

    public MysqlConnection(String databaseName, String databaseUser, String databasePassword, String databaseHost,
            int databasePort) {
        super(databaseName, databaseUser, databasePassword, databaseHost, databasePort);
        databaseDriver = "mariadb";
    }

}
