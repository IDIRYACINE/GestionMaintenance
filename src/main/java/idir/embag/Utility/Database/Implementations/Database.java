package idir.embag.Utility.Database.Implementations;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


import idir.embag.Utility.Database.IDatabase;

public class Database implements IDatabase{

    private Connection connection;
    private final String DATABASE_URL;

    public Database(String dATABASE_URL) {
        DATABASE_URL = dATABASE_URL;
    }

    @Override
    public void Connect() {
        try{
            connection = DriverManager.getConnection("jdbc:sqlite:"+DATABASE_URL);
            if (connection != null){
                connection.getMetaData();
                //CreateTables();
            }    
        }
        catch(Exception e){
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
    public ResultSet SearchQuery(String query) throws SQLException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void InsertQuery(String query)  {
        PreparedStatement statement;
        try {
            statement = connection.prepareStatement(query);
            statement.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void DeleteQuery(String query) {
        PreparedStatement statement;
        try {
            statement = connection.prepareStatement(query);
            statement.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void CreateQuery(String query) {
        PreparedStatement statement;
        try {
            statement = connection.prepareStatement(query);
            statement.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
    }

    @Override
    public void UpdateQuery(String query) {
        PreparedStatement statement;
        try {
            statement = connection.prepareStatement(query);
            statement.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
    }

   
}
