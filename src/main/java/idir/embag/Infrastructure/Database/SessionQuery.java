package idir.embag.Infrastructure.Database;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;

import idir.embag.Types.Infrastructure.Database.Metadata.EInventoryAttributes;
import idir.embag.Types.Infrastructure.Database.Metadata.ESessionAttributes;
import idir.embag.Types.Infrastructure.Database.Metadata.ESessionGroupAttributes;
import idir.embag.Types.Infrastructure.Database.Metadata.ESessionRecordAttributes;
import idir.embag.Types.Infrastructure.Database.Metadata.ESessionWorkerAttributes;
import idir.embag.Types.Infrastructure.Database.Metadata.EStockAttributes;
import idir.embag.Types.Infrastructure.Database.Metadata.ETables;
import idir.embag.Types.Infrastructure.Database.Metadata.EWorkerAttributes;
import idir.embag.Types.Infrastructure.Database.IDatabase;
import idir.embag.Types.Infrastructure.Database.ISessionQuery;
import idir.embag.Types.Infrastructure.Database.Generics.AttributeWrapper;
import idir.embag.Types.Infrastructure.Database.Generics.LoadWrapper;
import idir.embag.Types.Infrastructure.Database.Generics.SearchWrapper;

public class SessionQuery extends ISessionQuery{

    private IDatabase database;
    
    

    public SessionQuery(IDatabase database) {
        this.database = database;
    }

    
    @Override
    public void RegisterSession(Collection<AttributeWrapper> attributes) throws SQLException {
        String query = "INSERT INTO "+ETables.Sessions+ InsertWrapperToQuery(attributes);
        database.InsertQuery(query);
        
    }

    @Override
    public void UpdateSession(int sessionId, Collection<AttributeWrapper> attributes)
            throws SQLException {
                String whereClause = " WHERE "+EStockAttributes.ArticleId + "=" + sessionId;
                String query = "UPDATE "+ETables.Sessions+ UpdateWrapperToQuery(attributes)+ whereClause;
                database.UpdateQuery(query);
        
    }

    @Override
    public void RegisterSessionGroup(Collection<AttributeWrapper> attributes) throws SQLException {
        String query = "INSERT INTO "+ETables.SessionsGroups+ InsertWrapperToQuery(attributes);
        database.InsertQuery(query);
        
    }

    @Override
    public void UnregisterSessionGroup(int groupId) throws SQLException {
        String whereClause = " WHERE "+ESessionGroupAttributes.GroupId + "=" + groupId;
        String query = "DELETE FROM "+ETables.SessionsGroups + whereClause;
        
        database.DeleteQuery(query);
    }

    @Override
    public void UpdateSessionGroup(int groupId, Collection<AttributeWrapper> attributes)
            throws SQLException {
                String whereClause = " WHERE "+ESessionGroupAttributes.GroupId + "=" + groupId;
                String query = "UPDATE "+ETables.SessionsGroups+ UpdateWrapperToQuery(attributes)+ whereClause;
                database.UpdateQuery(query);
        
    }

    @Override
    public void RegsiterSessionWorker( Collection<AttributeWrapper> attributes)
            throws SQLException {
                String query = "INSERT INTO "+ETables.SessionWorkers+ InsertWrapperToQuery(attributes);
                database.UpdateQuery(query);
    }

    @Override
    public void UnregisterGroupWorker(int workerId) throws SQLException {
        String whereClause = " WHERE "+ESessionWorkerAttributes.WorkerId + "=" + workerId;
        String query = "DELETE FROM "+ETables.SessionWorkers + whereClause;
        
        database.DeleteQuery(query);
    }

    @Override
    public void UpdateSessionWorker(int sessionWorkerId, Collection<AttributeWrapper> attributes) throws SQLException {
        String whereClause = " WHERE "+ESessionWorkerAttributes.WorkerId + "=" + sessionWorkerId;
                String query = "UPDATE "+ETables.SessionWorkers+ UpdateWrapperToQuery(attributes)+ whereClause;
                database.UpdateQuery(query);
        
    }

    @Override
    public void RegisterSessionRecord(Collection<AttributeWrapper> attributes) throws SQLException {
        String query = "INSERT INTO "+ETables.SessionsRecords+ InsertWrapperToQuery(attributes);
        database.InsertQuery(query);
        
    }

    @Override
    public void CreateSessionTable() throws SQLException {
        String query = "CREATE TABLE IF NOT EXISTS "+ ETables.Sessions +" (\n"
            + ESessionAttributes.SessionId+" INTEGER PRIMARY KEY AUTOINCREMENT,\n"
            + ESessionAttributes.StartDate+" DATE,\n"
            + ESessionAttributes.EndDate+" DATE,\n"
            + ESessionAttributes.PriceShiftValue+" REAL,\n"
            + ESessionAttributes.QuantityShiftValue+" REAL)\n";
           
           
        database.CreateQuery(query);
        
    }

    @Override
    public void CreateSessionGroupTable() throws SQLException {
        String query = "CREATE TABLE IF NOT EXISTS "+ ETables.SessionsGroups +" (\n"
            + ESessionGroupAttributes.GroupId+" INTEGER PRIMARY KEY AUTOINCREMENT,\n"
            + ESessionGroupAttributes.SessionId+" INTEGER,\n"
            + ESessionGroupAttributes.GroupName+" TEXT,\n"
           
            + "FOREIGN KEY ("+ ESessionRecordAttributes.SessionId +")\n"
            + "REFERENCES "+ ETables.Sessions +"(" +ESessionAttributes.SessionId +")\n"  
            + "ON DELETE CASCADE ON UPDATE NO ACTION) \n";
           
        database.CreateQuery(query);
        
    }

    @Override
    public void CreateSessionRecordTable() throws SQLException {
        String query = "CREATE TABLE IF NOT EXISTS "+ ETables.SessionsRecords +" (\n"
            + ESessionRecordAttributes.RecordId + " INTEGER PRIMARY KEY AUTOINCREMENT,\n"
            + ESessionRecordAttributes.SessionId + " INTEGER,\n"
            + ESessionRecordAttributes.WorkerId + " INTEGER,\n"
            + ESessionRecordAttributes.GroupId + " INTEGER,\n"
            + ESessionRecordAttributes.InventoryId + " INTEGER,\n"
            + ESessionRecordAttributes.RecordDate + " DATE,\n"
            + ESessionRecordAttributes.PriceShift + " REAL,\n"
            + ESessionRecordAttributes.QuantityShift + " INTEGER,\n"
            + ESessionRecordAttributes.RecordQuantity + " INTEGER,\n"
            + ESessionRecordAttributes.StockQuantity + " INTEGER,\n"
            + ESessionRecordAttributes.StockPrice + " REAL,\n"

            + "FOREIGN KEY ("+ ESessionRecordAttributes.SessionId +")\n"
            + "REFERENCES "+ ETables.Sessions +"(" +ESessionAttributes.SessionId +")\n"  
            + "ON DELETE CASCADE ON UPDATE NO ACTION, \n"

            + "FOREIGN KEY ("+ ESessionRecordAttributes.WorkerId +")\n"
            + "REFERENCES "+ ETables.Workers +"(" +EWorkerAttributes.WorkerId +")\n"  
            + "ON DELETE CASCADE ON UPDATE NO ACTION, \n"

            + "FOREIGN KEY ("+ ESessionRecordAttributes.GroupId +")\n"
            + "REFERENCES "+ ETables.SessionsGroups +"(" +ESessionGroupAttributes.GroupId +")\n"  
            + "ON DELETE CASCADE ON UPDATE NO ACTION, \n"
            
            + "FOREIGN KEY ("+ ESessionRecordAttributes.InventoryId +")\n"
            + "REFERENCES "+ ETables.Inventory +"(" +EInventoryAttributes.ArticleId +")\n"  
            + "ON DELETE CASCADE ON UPDATE NO ACTION) \n";
           
        database.CreateQuery(query);
    }

    @Override
    public void CreateSessionWorkersTable() throws SQLException {
        String query = "CREATE TABLE IF NOT EXISTS "+ ETables.SessionWorkers +" (\n"
            + ESessionWorkerAttributes.WorkerId + " INTEGER PRIMARY KEY ,\n"
            + ESessionWorkerAttributes.GroupId + " INTEGER,\n"
            + ESessionWorkerAttributes.Password+" TEXT,\n"
        
            + "FOREIGN KEY ("+ ESessionRecordAttributes.WorkerId +")\n"
            + "REFERENCES "+ ETables.Workers +"(" +EWorkerAttributes.WorkerId +")\n"  
            + "ON DELETE CASCADE ON UPDATE NO ACTION, \n"

            + "FOREIGN KEY ("+ ESessionRecordAttributes.GroupId +")\n"
            + "REFERENCES "+ ETables.SessionsGroups +"(" +ESessionGroupAttributes.GroupId +")\n"  
            + "ON DELETE CASCADE ON UPDATE NO ACTION) \n";
            
           
        database.CreateQuery(query);
        
    }


    @Override
    public ResultSet SearchSessionGroup(SearchWrapper parametrers) throws SQLException {
        String whereClause = " WHERE "+ SearchWrapperToWhereClause(parametrers);
        String query = "SELECT * FROM "+ETables.SessionsGroups+ whereClause;

        ResultSet result = database.SelectQuery(query);
        return result;
    }


    @Override
    public ResultSet SearchSessionWorker(SearchWrapper parametrers) throws SQLException {
        String whereClause = " WHERE "+ SearchWrapperToWhereClause(parametrers);

        String joinClause = " INNER JOIN " +ETables.Workers +" ON "
            +ETables.SessionsRecords + "."+ ESessionRecordAttributes.WorkerId
            +"=" + ETables.Workers + "." + EWorkerAttributes.WorkerId

            +" INNER JOIN " +ETables.SessionsGroups +" ON "
            +ETables.SessionsRecords + "."+ ESessionRecordAttributes.GroupId
            +"=" + ETables.SessionsGroups + "." + ESessionGroupAttributes.GroupId ;

        String query = "SELECT "
            + ETables.Workers + "." + EWorkerAttributes.WorkerId+" ,"
            + ETables.Workers + "." + EWorkerAttributes.WorkerName+" ,"
            + ETables.Workers + "." + EWorkerAttributes.WorkerPhone+" ,"
            + ETables.SessionWorkers + "." + ESessionWorkerAttributes.Password+" ,"
            + ETables.SessionsGroups + "." + ESessionGroupAttributes.GroupId +" ,"
            + ETables.SessionsGroups + "." + ESessionGroupAttributes.GroupName +" ,"
            +" FROM "+ETables.SessionsRecords +joinClause+ whereClause;

        ResultSet result = database.SelectQuery(query);
        return result;
    }


    @Override
    public ResultSet SearchSessionRecord(SearchWrapper parametrers) throws SQLException {
        String whereClause = " WHERE "+ SearchWrapperToWhereClause(parametrers);

        String joinClause = " INNER JOIN " +ETables.Workers +" ON "
            +ETables.SessionsRecords + "."+ ESessionRecordAttributes.WorkerId
            +"=" + ETables.Workers + "." + EWorkerAttributes.WorkerId

            +" INNER JOIN " +ETables.Stock +" ON "
            +ETables.SessionsRecords + "."+ ESessionRecordAttributes.InventoryId
            +"=" + ETables.Stock + "." + EStockAttributes.ArticleId ;

        String query = "SELECT "
            + ETables.SessionsRecords + "." + ESessionRecordAttributes.RecordId+" ,"
            + ETables.SessionsRecords + "." + ESessionRecordAttributes.RecordDate +" ,"
            + ETables.SessionsRecords + "." + ESessionRecordAttributes.PriceShift +" ,"
            + ETables.SessionsRecords + "." + ESessionRecordAttributes.QuantityShift +" ,"
            + ETables.SessionsRecords + "." + ESessionRecordAttributes.RecordQuantity +" ,"
            + ETables.SessionsRecords + "." + ESessionRecordAttributes.StockQuantity +" ,"
            + ETables.SessionsRecords + "." + ESessionRecordAttributes.StockPrice +" ,"
            + ETables.SessionsRecords + "." + ESessionRecordAttributes.SessionId +" ,"
            + ETables.Workers + "." + EWorkerAttributes.WorkerName +" ,"
            + ETables.Stock + "." + EStockAttributes.ArticleName +" ,"
            +" FROM "+ETables.SessionsRecords +joinClause+ whereClause;

        ResultSet result = database.SelectQuery(query);
        return result;
    }


    @Override
    public ResultSet LoadSessionRecord(LoadWrapper parametrers) throws SQLException {
        String extraClause = " LIMIT "+ parametrers.getLimit() + " OFFSET " + parametrers.getOffset();
        String joinClause = " INNER JOIN " +ETables.Workers +" ON "
        +ETables.SessionsRecords + "."+ ESessionRecordAttributes.WorkerId
        +"=" + ETables.Workers + "." + EWorkerAttributes.WorkerId

        +" INNER JOIN " +ETables.Stock +" ON "
        +ETables.SessionsRecords + "."+ ESessionRecordAttributes.InventoryId
        +"=" + ETables.Stock + "." + EStockAttributes.ArticleId ;

        String query = "SELECT "
        + ETables.SessionsRecords + "." + ESessionRecordAttributes.RecordId+" ,"
        + ETables.SessionsRecords + "." + ESessionRecordAttributes.RecordDate +" ,"
        + ETables.SessionsRecords + "." + ESessionRecordAttributes.PriceShift +" ,"
        + ETables.SessionsRecords + "." + ESessionRecordAttributes.QuantityShift +" ,"
        + ETables.SessionsRecords + "." + ESessionRecordAttributes.RecordQuantity +" ,"
        + ETables.SessionsRecords + "." + ESessionRecordAttributes.StockQuantity +" ,"
        + ETables.SessionsRecords + "." + ESessionRecordAttributes.StockPrice +" ,"
        + ETables.SessionsRecords + "." + ESessionRecordAttributes.SessionId +" ,"
        + ETables.Workers + "." + EWorkerAttributes.WorkerName +" ,"
        + ETables.Stock + "." + EStockAttributes.ArticleName +" ,"
        +" FROM "+ETables.SessionsRecords +joinClause+ extraClause;
        System.out.println(query);
    ResultSet result = database.SelectQuery(query);
    return result;
    }


    @Override
    public ResultSet LoadSessionGroup(LoadWrapper parametrers) throws SQLException {
        String extraClause = " LIMIT "+ parametrers.getLimit() + " OFFSET " + parametrers.getOffset();
        String query = "SELECT * FROM "+ETables.SessionsGroups+ extraClause;
        ResultSet result = database.SelectQuery(query);
        return result;
    }


    @Override
    public ResultSet LoadSessionWorkers(LoadWrapper parametrers) throws SQLException {
        String extraClause = " LIMIT "+ parametrers.getLimit() + " OFFSET " + parametrers.getOffset();

        String joinClause = " INNER JOIN " +ETables.Workers +" ON "
            +ETables.SessionsRecords + "."+ ESessionRecordAttributes.WorkerId
            +"=" + ETables.Workers + "." + EWorkerAttributes.WorkerId

            +" INNER JOIN " +ETables.SessionsGroups +" ON "
            +ETables.SessionsRecords + "."+ ESessionRecordAttributes.GroupId
            +"=" + ETables.SessionsGroups + "." + ESessionGroupAttributes.GroupId ;

        String query = "SELECT "
            + ETables.Workers + "." + EWorkerAttributes.WorkerId+" ,"
            + ETables.Workers + "." + EWorkerAttributes.WorkerName+" ,"
            + ETables.Workers + "." + EWorkerAttributes.WorkerPhone+" ,"
            + ETables.SessionWorkers + "." + ESessionWorkerAttributes.Password+" ,"
            + ETables.SessionsGroups + "." + ESessionGroupAttributes.GroupId +" ,"
            + ETables.SessionsGroups + "." + ESessionGroupAttributes.GroupName +" ,"
            +" FROM "+ETables.SessionsRecords +joinClause+ extraClause;

        ResultSet result = database.SelectQuery(query);
        return result;
    }

    @Override
    public void CreateIndexes() throws SQLException {
       
    }
}
