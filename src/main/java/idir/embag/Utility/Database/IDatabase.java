package idir.embag.Utility.Database;

import java.sql.ResultSet;
import java.sql.SQLException;


public interface IDatabase {
    public void Connect();
    
    public void Disconnect();

    public ResultSet SelectQuery() throws SQLException;

    public ResultSet SearchQuery(String qString) throws SQLException;

    public void InsertQuery();

    public void DeleteQuery();

    public void CreateQuery();

    public void UpdateQuery();
    
}
