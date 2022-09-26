package idir.embag.Infrastructure.Database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import idir.embag.Types.Infrastructure.Database.IConnectionParameters;
import idir.embag.Types.Infrastructure.Database.IDatabase;

public class MysqlDatabase implements IDatabase {
    private Connection connection;

    @Override
    public void Connect(IConnectionParameters connectionParameters) {
        try {
            connection = DriverManager.getConnection(formatConnectionUrl(connectionParameters),
                    connectionParameters.getDatabaseUser(), connectionParameters.getDatabasePassword());
            if (connection != null) {
                connection.getMetaData();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void Disconnect() {
        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public ResultSet SelectQuery(String query) throws SQLException {
        PreparedStatement statement = connection.prepareStatement(query);
        return statement.executeQuery();
    }

    @Override
    public void InsertQuery(String query)  {
        try {
            connection.createStatement().execute(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void DeleteQuery(String query) {
        
        try {
            connection.createStatement().execute(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void CreateQuery(String query) {
        try {
            connection.createStatement().execute(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void UpdateQuery(String query) {
        try {
            connection.createStatement().execute(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
    }

    private String formatConnectionUrl(IConnectionParameters connectionParameters) {
        return "jdbc:" + connectionParameters.getDatabaseDriver() + "://" + connectionParameters.getDatabaseHost() + ":"
                + connectionParameters.getDatabasePort() + "/" + connectionParameters.getDatabaseName();
    }

}
