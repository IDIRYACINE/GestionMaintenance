package idir.embag.Infrastructure.Database;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;

import idir.embag.Types.Infrastructure.Database.IDatabase;
import idir.embag.Types.Infrastructure.Database.IWorkerQuery;
import idir.embag.Types.Infrastructure.Database.Generics.AttributeWrapper;
import idir.embag.Types.Infrastructure.Database.Generics.LoadWrapper;
import idir.embag.Types.Infrastructure.Database.Generics.MDatabase;
import idir.embag.Types.Infrastructure.Database.Generics.SearchWrapper;

public class WorkerQuery extends IWorkerQuery{
    private IDatabase database;
    

    private static final String WORKERS_TABLE_NAME = MDatabase.Tables.Workers;
    
    public WorkerQuery(IDatabase database) {
        this.database = database;
    }



    @Override
    public void UnregisterWorker(int workerId) throws SQLException {}

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
        String query = "CREATE TABLE IF NOT EXISTS "+ WORKERS_TABLE_NAME +" (\n"
           + MDatabase.WorkersAttributes.WorkerId+" INTEGER PRIMARY KEY AUTOINCREMENT,\n"
           + MDatabase.WorkersAttributes.Name+" TEXT,\n"
           + MDatabase.WorkersAttributes.Email+" TEXT,\n"
           + MDatabase.WorkersAttributes.Phone+" INTEGER)\n";
           
        database.CreateQuery(query);
            
    }



    @Override
    public ResultSet SearchWorker(SearchWrapper parametrers) throws SQLException {
        String whereClause = " WHERE "+ SearchWrapperToWhereClause(parametrers);
        String query = "SELECT * FROM "+WORKERS_TABLE_NAME+ whereClause;
        ResultSet result = database.SelectQuery(query);
        return result;
    }



    @Override
    public ResultSet LoadSWorkers(LoadWrapper parametrers) throws SQLException {
        String extraClause = " LIMIT "+ parametrers.getLimit() + " OFFSET " + parametrers.getOffset();
        String query = "SELECT * FROM "+WORKERS_TABLE_NAME+ extraClause;
        ResultSet result = database.SelectQuery(query);
        return result;
    }



}