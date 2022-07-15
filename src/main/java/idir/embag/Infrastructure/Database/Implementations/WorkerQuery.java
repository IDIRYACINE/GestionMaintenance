package idir.embag.Infrastructure.Database.Implementations;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;

import idir.embag.Infrastructure.Database.IDatabase;
import idir.embag.Infrastructure.Database.IWorkerQuery;
import idir.embag.Infrastructure.Database.Generics.AttributeWrapper;
import idir.embag.Infrastructure.Database.Generics.MDatabase;
import idir.embag.Infrastructure.Database.Generics.SearchWrapper;

public class WorkerQuery extends IWorkerQuery{
    private IDatabase database;
    

    private static final String WORKERS_TABLE_NAME = MDatabase.Tables.Workers;
    
    public WorkerQuery(IDatabase database) {
        this.database = database;
    }



    @Override
    public void UnregisterWorker() throws SQLException {}

    @Override
    public void RegisterWorker(Collection<AttributeWrapper> attributes ) throws SQLException {
        String query = "INSERT INTO "+WORKERS_TABLE_NAME+ InsertWrapperToQuery(attributes);
        database.InsertQuery(query);
        
    }

    @Override
    public void UpdateWorker(int workerId, Collection<AttributeWrapper> attributes ) throws SQLException {
        String whereClause = " WHERE "+MDatabase.StockAttributes.ArticleId + "=" + workerId;
        String query = "UPDATE "+WORKERS_TABLE_NAME+ UpdateWrapperToQuery(attributes)+ whereClause;
        database.UpdateQuery(query);
    }


    @Override
    public void CreateWorkerTable() throws SQLException {
        String query = "CREATE TABLE "+ WORKERS_TABLE_NAME +" (\n"
           + MDatabase.WorkersAttributes.WorkerId+" INTEGER PRIMARY KEY AUTOINCREMENT,\n"
           + MDatabase.WorkersAttributes.Name+" TEXT,\n"
           + MDatabase.WorkersAttributes.Email+" TEXT,\n"
           + MDatabase.WorkersAttributes.Phone+" INTEGER)\n";
           
        database.CreateQuery(query);
            
    }



    @Override
    public ResultSet SearchWorker(SearchWrapper parametrers) throws SQLException {
        // TODO Auto-generated method stub
        return null;
    }



}