package idir.embag.Infrastructure.Database;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import idir.embag.Types.Infrastructure.Database.IDatabase;
import idir.embag.Types.Infrastructure.Database.IWorkerQuery;
import idir.embag.Types.Infrastructure.Database.Generics.AttributeWrapper;
import idir.embag.Types.Infrastructure.Database.Generics.LoadWrapper;
import idir.embag.Types.Infrastructure.Database.Generics.SearchWrapper;
import idir.embag.Types.Infrastructure.Database.Metadata.EIndex;
import idir.embag.Types.Infrastructure.Database.Metadata.ETables;
import idir.embag.Types.Infrastructure.Database.Metadata.EWorkerAttributes;

public class WorkerQuery extends IWorkerQuery{
    private IDatabase database;
    

    
    public WorkerQuery(IDatabase database) {
        this.database = database;
    }



    @Override
    public void UnregisterWorker(int workerId) throws SQLException {
        String whereClause = " WHERE "+EWorkerAttributes.WorkerId + "=" + workerId;
        String query = "DELETE FROM "+ETables.Workers + whereClause;
        
        database.DeleteQuery(query);
    }

    @Override
    public void RegisterWorker(Collection<AttributeWrapper> attributes ) throws SQLException {
        String query = "INSERT INTO "+ETables.Workers+ InsertWrapperToQuery(attributes);
        database.InsertQuery(query);
        
    }

    @Override
    public void UpdateWorker(int workerId, Collection<AttributeWrapper> attributes ) throws SQLException {
        String whereClause = " WHERE "+EWorkerAttributes.WorkerId + "=" + workerId;
        String query = "UPDATE "+ETables.Workers+ UpdateWrapperToQuery(attributes)+ whereClause;
        database.UpdateQuery(query);
    }


    @Override
    public void CreateWorkerTable() throws SQLException {
        String query = "CREATE TABLE IF NOT EXISTS "+ ETables.Workers +" ("
           + EWorkerAttributes.WorkerId+" INTEGER PRIMARY KEY AUTO_INCREMENT,"
           + EWorkerAttributes.WorkerName+" TEXT,"
           + EWorkerAttributes.WorkerEmail+" TEXT,"
           + EWorkerAttributes.WorkerPhone+" INTEGER)";
        database.CreateQuery(query);
            
    }



    @Override
    public ResultSet SearchWorker(SearchWrapper parametrers) throws SQLException {
        String whereClause = " WHERE "+ SearchWrapperToWhereClause(parametrers);
        String query = "SELECT * FROM "+ETables.Workers+ whereClause;
        ResultSet result = database.SelectQuery(query);
        return result;
    }



    @Override
    public ResultSet LoadSWorkers(LoadWrapper parametrers) throws SQLException {
        String extraClause = " LIMIT "+ parametrers.getLimit() + " OFFSET " + parametrers.getOffset();
        String query = "SELECT * FROM "+ETables.Workers+ extraClause;
        ResultSet result = database.SelectQuery(query);
        return result;
    }

    @Override
    public void CreateIndexes() throws SQLException {
        String query = "CREATE INDEX " + EIndex.NamePhoneIndex +" ON "
        + ETables.Workers 
        +"(" + EWorkerAttributes.WorkerName 
        +"," +EWorkerAttributes.WorkerPhone
        +")";

        database.CreateQuery(query);
    }



    @Override
    public void RegisterWorkerCollection(Collection<AttributeWrapper[]> collection) throws SQLException {
        String query = "INSERT INTO "+ETables.Workers+ InsertCollectionToQuery(collection);
        database.InsertQuery(query);        
    }

}