package idir.embag.Types.Infrastructure.Database;

import java.sql.ResultSet;
import java.sql.SQLException;


public interface IDatabase {
    public void Connect(IConnectionParameters connectionParameters);
    
    public void Disconnect();

    public ResultSet SelectQuery(String query) throws SQLException;

    public void InsertQuery(String query);

    public void DeleteQuery(String query);

    public void CreateQuery(String query);

    public void UpdateQuery(String query);
    
}
