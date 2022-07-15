package idir.embag.Infrastructure.Database.Implementations;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import idir.embag.Infrastructure.Database.IDatabase;

public class Database implements IDatabase{

    private Connection connection;
    private final String DATABASE_URL;

    public Database(String dATABASE_URL) {
        DATABASE_URL = dATABASE_URL;
    }

    @Override
    public void Connect() {
        //TODO: Implement this method
    String AbsolutePath = new File("").getAbsolutePath();
    File directory = new File("Data");
    
    if (! directory.exists()){
        directory.mkdir();
    }

        try{
            connection = DriverManager.getConnection("jdbc:sqlite:"+AbsolutePath+"/"+DATABASE_URL);
            if (connection != null){
                connection.getMetaData();
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

   
}
