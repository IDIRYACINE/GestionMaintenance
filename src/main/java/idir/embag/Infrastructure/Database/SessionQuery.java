package idir.embag.Infrastructure.Database;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;

import idir.embag.Types.Infrastructure.Database.Generics.MDatabase.SessionsGroupsAttributes;
import idir.embag.Types.Infrastructure.Database.Generics.MDatabase.SessionsRecordsAttributes;
import idir.embag.Types.Infrastructure.Database.Generics.MDatabase.StockAttributes;
import idir.embag.Types.Infrastructure.Database.Generics.MDatabase.WorkersAttributes;
import idir.embag.Types.Infrastructure.Database.IDatabase;
import idir.embag.Types.Infrastructure.Database.ISessionQuery;
import idir.embag.Types.Infrastructure.Database.Generics.AttributeWrapper;
import idir.embag.Types.Infrastructure.Database.Generics.LoadWrapper;
import idir.embag.Types.Infrastructure.Database.Generics.MDatabase;
import idir.embag.Types.Infrastructure.Database.Generics.SearchWrapper;

public class SessionQuery extends ISessionQuery{

    private IDatabase database;
    
    private static final String SESSION_TABLE_NAME = MDatabase.Tables.Sessions;
    private static final String RECORDS_TABLE_NAME = MDatabase.Tables.SessionsRecords;
    private static final String SESSION_WORKERS_TABLE_NAME = MDatabase.Tables.SessionWorkers;
    private static final String GROUPS_TABLE_NAME = MDatabase.Tables.SessionsGroups;
    private static final String WORKERS_TABLE_NAME = MDatabase.Tables.Workers;

    public SessionQuery(IDatabase database) {
        this.database = database;
    }

    
    @Override
    public void RegisterSession(Collection<AttributeWrapper> attributes) throws SQLException {
        String query = "INSERT INTO "+SESSION_TABLE_NAME+ InsertWrapperToQuery(attributes);
        database.InsertQuery(query);
        
    }

    @Override
    public void UpdateSession(int sessionId, Collection<AttributeWrapper> attributes)
            throws SQLException {
                String whereClause = " WHERE "+MDatabase.StockAttributes.ArticleId + "=" + sessionId;
                String query = "UPDATE "+SESSION_TABLE_NAME+ UpdateWrapperToQuery(attributes)+ whereClause;
                database.UpdateQuery(query);
        
    }

    @Override
    public void RegisterSessionGroup(Collection<AttributeWrapper> attributes) throws SQLException {
        String query = "INSERT INTO "+GROUPS_TABLE_NAME+ InsertWrapperToQuery(attributes);
        database.InsertQuery(query);
        
    }

    @Override
    public void UnregisterSessionGroup(int groupId) throws SQLException {}

    @Override
    public void UpdateSessionGroup(int groupId, Collection<AttributeWrapper> attributes)
            throws SQLException {
                String whereClause = " WHERE "+MDatabase.SessionsGroupsAttributes.Id + "=" + groupId;
                String query = "UPDATE "+GROUPS_TABLE_NAME+ UpdateWrapperToQuery(attributes)+ whereClause;
                database.UpdateQuery(query);
        
    }

    @Override
    public void RegsiterSessionWorker(int workerId, Collection<AttributeWrapper> attributes)
            throws SQLException {
                String whereClause = " WHERE "+MDatabase.SessionWorkersAttributes.WorkerId + "=" + workerId;
                String query = "UPDATE "+SESSION_WORKERS_TABLE_NAME+ UpdateWrapperToQuery(attributes)+ whereClause;
                database.UpdateQuery(query);
        
    }

    @Override
    public void UnregisterGroupWorker(int workerId) throws SQLException {}

    @Override
    public void RegisterSessionRecord(Collection<AttributeWrapper> attributes) throws SQLException {
        String query = "INSERT INTO "+RECORDS_TABLE_NAME+ InsertWrapperToQuery(attributes);
        database.InsertQuery(query);
        
    }

    @Override
    public void CreateSessionTable() throws SQLException {
        String query = "CREATE TABLE IF NOT EXISTS "+ SESSION_TABLE_NAME +" (\n"
            + MDatabase.SessionsAttributes.SessionId+" INTEGER PRIMARY KEY AUTOINCREMENT,\n"
            + MDatabase.SessionsAttributes.StartDate+" DATE,\n"
            + MDatabase.SessionsAttributes.EndDate+" DATE,\n"
            + MDatabase.SessionsAttributes.PriceShiftValue+" REAL,\n"
            + MDatabase.SessionsAttributes.QuantityShiftValue+" REAL)\n";
           
           
        database.CreateQuery(query);
        
    }

    @Override
    public void CreateSessionGroupTable() throws SQLException {
        String query = "CREATE TABLE IF NOT EXISTS "+ GROUPS_TABLE_NAME +" (\n"
            + MDatabase.SessionsGroupsAttributes.Id+" INTEGER PRIMARY KEY AUTOINCREMENT,\n"
            + MDatabase.SessionsGroupsAttributes.SessionId+" INTEGER,\n"
            + MDatabase.SessionsGroupsAttributes.Name+" TEXT,\n"
           
            + "FOREIGN KEY ("+ MDatabase.SessionsRecordsAttributes.SessionId +")\n"
            + "REFERENCES "+ SESSION_TABLE_NAME +"(" +MDatabase.SessionsAttributes.SessionId +")\n"  
            + "ON DELETE CASCADE ON UPDATE NO ACTION) \n";
           
        database.CreateQuery(query);
        
    }

    @Override
    public void CreateSessionRecordTable() throws SQLException {
        String query = "CREATE TABLE IF NOT EXISTS "+ RECORDS_TABLE_NAME +" (\n"
            + MDatabase.SessionsRecordsAttributes.RecordId + " INTEGER PRIMARY KEY AUTOINCREMENT,\n"
            + MDatabase.SessionsRecordsAttributes.SessionId + " INTEGER,\n"
            + MDatabase.SessionsRecordsAttributes.WorkerId + " INTEGER,\n"
            + MDatabase.SessionsRecordsAttributes.GroupId + " INTEGER,\n"
            + MDatabase.SessionsRecordsAttributes.InventoryId + " INTEGER,\n"
            + MDatabase.SessionsRecordsAttributes.RecordDate + " DATE,\n"
            + MDatabase.SessionsRecordsAttributes.PriceShift + " REAL,\n"
            + MDatabase.SessionsRecordsAttributes.QuantityShift + " INTEGER,\n"
            + MDatabase.SessionsRecordsAttributes.RecordQuantity + " INTEGER,\n"
            + MDatabase.SessionsRecordsAttributes.StockQuantity + " INTEGER,\n"
            + MDatabase.SessionsRecordsAttributes.StockPrice + " REAL,\n"

            + "FOREIGN KEY ("+ MDatabase.SessionsRecordsAttributes.SessionId +")\n"
            + "REFERENCES "+ SESSION_TABLE_NAME +"(" +MDatabase.SessionsAttributes.SessionId +")\n"  
            + "ON DELETE CASCADE ON UPDATE NO ACTION, \n"

            + "FOREIGN KEY ("+ MDatabase.SessionsRecordsAttributes.WorkerId +")\n"
            + "REFERENCES "+ MDatabase.Tables.Workers +"(" +MDatabase.WorkersAttributes.WorkerId +")\n"  
            + "ON DELETE CASCADE ON UPDATE NO ACTION, \n"

            + "FOREIGN KEY ("+ MDatabase.SessionsRecordsAttributes.GroupId +")\n"
            + "REFERENCES "+ GROUPS_TABLE_NAME +"(" +MDatabase.SessionsGroupsAttributes.Id +")\n"  
            + "ON DELETE CASCADE ON UPDATE NO ACTION, \n"
            
            + "FOREIGN KEY ("+ MDatabase.SessionsRecordsAttributes.InventoryId +")\n"
            + "REFERENCES "+ MDatabase.Tables.Inventory +"(" +MDatabase.InventoryAttributes.ArticleId +")\n"  
            + "ON DELETE CASCADE ON UPDATE NO ACTION) \n";
           
        database.CreateQuery(query);
    }

    @Override
    public void CreateSessionWorkersTable() throws SQLException {
        String query = "CREATE TABLE IF NOT EXISTS "+ SESSION_WORKERS_TABLE_NAME +" (\n"
            + MDatabase.SessionWorkersAttributes.Id + " INTEGER PRIMARY KEY AUTOINCREMENT,\n"
            + MDatabase.SessionWorkersAttributes.GroupId + " INTEGER,\n"
            + MDatabase.SessionWorkersAttributes.WorkerId + " INTEGER,\n"
        
            + "FOREIGN KEY ("+ MDatabase.SessionsRecordsAttributes.WorkerId +")\n"
            + "REFERENCES "+ MDatabase.Tables.Workers +"(" +MDatabase.WorkersAttributes.WorkerId +")\n"  
            + "ON DELETE CASCADE ON UPDATE NO ACTION, \n"

            + "FOREIGN KEY ("+ MDatabase.SessionsRecordsAttributes.GroupId +")\n"
            + "REFERENCES "+ GROUPS_TABLE_NAME +"(" +MDatabase.SessionsGroupsAttributes.Id +")\n"  
            + "ON DELETE CASCADE ON UPDATE NO ACTION) \n";
            
           
        database.CreateQuery(query);
        
    }


    @Override
    public ResultSet SearchSessionGroup(SearchWrapper parametrers) throws SQLException {
        String whereClause = " WHERE "+ SearchWrapperToWhereClause(parametrers);
        String query = "SELECT * FROM "+GROUPS_TABLE_NAME+ whereClause;

        ResultSet result = database.SelectQuery(query);
        return result;
    }


    @Override
    public ResultSet SearchSessionWorker(SearchWrapper parametrers) throws SQLException {
        String whereClause = " WHERE "+ SearchWrapperToWhereClause(parametrers);

        String joinClause = " INNER JOIN " +WORKERS_TABLE_NAME +" ON "
            +RECORDS_TABLE_NAME + "."+ SessionsRecordsAttributes.WorkerId
            +"=" + WORKERS_TABLE_NAME + "." + WorkersAttributes.WorkerId

            +" INNER JOIN " +GROUPS_TABLE_NAME +" ON "
            +RECORDS_TABLE_NAME + "."+ SessionsRecordsAttributes.GroupId
            +"=" + GROUPS_TABLE_NAME + "." + SessionsGroupsAttributes.Id ;

        String query = "SELECT "
            + WORKERS_TABLE_NAME + "." + WorkersAttributes.WorkerId+" ,"
            + WORKERS_TABLE_NAME + "." + WorkersAttributes.Name+" ,"
            + WORKERS_TABLE_NAME + "." + WorkersAttributes.Phone+" ,"
            + GROUPS_TABLE_NAME + "." + SessionsGroupsAttributes.Id +" ,"
            + GROUPS_TABLE_NAME + "." + SessionsGroupsAttributes.Name +" ,"
            +" FROM "+RECORDS_TABLE_NAME +joinClause+ whereClause;

        ResultSet result = database.SelectQuery(query);
        return result;
    }


    @Override
    public ResultSet SearchSessionRecord(SearchWrapper parametrers) throws SQLException {
        String whereClause = " WHERE "+ SearchWrapperToWhereClause(parametrers);

        String joinClause = " INNER JOIN " +WORKERS_TABLE_NAME +" ON "
            +RECORDS_TABLE_NAME + "."+ SessionsRecordsAttributes.WorkerId
            +"=" + WORKERS_TABLE_NAME + "." + WorkersAttributes.WorkerId

            +" INNER JOIN " +MDatabase.Tables.Stock +" ON "
            +RECORDS_TABLE_NAME + "."+ SessionsRecordsAttributes.InventoryId
            +"=" + MDatabase.Tables.Stock + "." + StockAttributes.ArticleId ;

        String query = "SELECT "
            + RECORDS_TABLE_NAME + "." + SessionsRecordsAttributes.RecordId+" ,"
            + RECORDS_TABLE_NAME + "." + SessionsRecordsAttributes.RecordDate +" ,"
            + RECORDS_TABLE_NAME + "." + SessionsRecordsAttributes.PriceShift +" ,"
            + RECORDS_TABLE_NAME + "." + SessionsRecordsAttributes.QuantityShift +" ,"
            + RECORDS_TABLE_NAME + "." + SessionsRecordsAttributes.RecordQuantity +" ,"
            + RECORDS_TABLE_NAME + "." + SessionsRecordsAttributes.StockQuantity +" ,"
            + RECORDS_TABLE_NAME + "." + SessionsRecordsAttributes.StockPrice +" ,"
            + RECORDS_TABLE_NAME + "." + SessionsRecordsAttributes.SessionId +" ,"
            + WORKERS_TABLE_NAME + "." + WorkersAttributes.Name +" ,"
            + MDatabase.Tables.Stock + "." + StockAttributes.ArticleName +" ,"
            +" FROM "+RECORDS_TABLE_NAME +joinClause+ whereClause;

        ResultSet result = database.SelectQuery(query);
        return result;
    }


    @Override
    public ResultSet LoadSessionRecord(LoadWrapper parametrers) throws SQLException {
        String extraClause = " LIMIT "+ parametrers.getLimit() + " OFFSET " + parametrers.getOffset();
        String joinClause = " INNER JOIN " +WORKERS_TABLE_NAME +" ON "
        +RECORDS_TABLE_NAME + "."+ SessionsRecordsAttributes.WorkerId
        +"=" + WORKERS_TABLE_NAME + "." + WorkersAttributes.WorkerId

        +" INNER JOIN " +MDatabase.Tables.Stock +" ON "
        +RECORDS_TABLE_NAME + "."+ SessionsRecordsAttributes.InventoryId
        +"=" + MDatabase.Tables.Stock + "." + StockAttributes.ArticleId ;

        String query = "SELECT "
        + RECORDS_TABLE_NAME + "." + SessionsRecordsAttributes.RecordId+" ,"
        + RECORDS_TABLE_NAME + "." + SessionsRecordsAttributes.RecordDate +" ,"
        + RECORDS_TABLE_NAME + "." + SessionsRecordsAttributes.PriceShift +" ,"
        + RECORDS_TABLE_NAME + "." + SessionsRecordsAttributes.QuantityShift +" ,"
        + RECORDS_TABLE_NAME + "." + SessionsRecordsAttributes.RecordQuantity +" ,"
        + RECORDS_TABLE_NAME + "." + SessionsRecordsAttributes.StockQuantity +" ,"
        + RECORDS_TABLE_NAME + "." + SessionsRecordsAttributes.StockPrice +" ,"
        + RECORDS_TABLE_NAME + "." + SessionsRecordsAttributes.SessionId +" ,"
        + WORKERS_TABLE_NAME + "." + WorkersAttributes.Name +" ,"
        + MDatabase.Tables.Stock + "." + StockAttributes.ArticleName +" ,"
        +" FROM "+RECORDS_TABLE_NAME +joinClause+ extraClause;

    ResultSet result = database.SelectQuery(query);
    return result;
    }


    @Override
    public ResultSet LoadSessionGroup(LoadWrapper parametrers) throws SQLException {
        String extraClause = " LIMIT "+ parametrers.getLimit() + " OFFSET " + parametrers.getOffset();
        String query = "SELECT * FROM "+GROUPS_TABLE_NAME+ extraClause;
        ResultSet result = database.SelectQuery(query);
        return result;
    }


    @Override
    public ResultSet LoadSessionWorkers(LoadWrapper parametrers) throws SQLException {
        String extraClause = " LIMIT "+ parametrers.getLimit() + " OFFSET " + parametrers.getOffset();

        String joinClause = " INNER JOIN " +WORKERS_TABLE_NAME +" ON "
            +RECORDS_TABLE_NAME + "."+ SessionsRecordsAttributes.WorkerId
            +"=" + WORKERS_TABLE_NAME + "." + WorkersAttributes.WorkerId

            +" INNER JOIN " +GROUPS_TABLE_NAME +" ON "
            +RECORDS_TABLE_NAME + "."+ SessionsRecordsAttributes.GroupId
            +"=" + GROUPS_TABLE_NAME + "." + SessionsGroupsAttributes.Id ;

        String query = "SELECT "
            + WORKERS_TABLE_NAME + "." + WorkersAttributes.WorkerId+" ,"
            + WORKERS_TABLE_NAME + "." + WorkersAttributes.Name+" ,"
            + WORKERS_TABLE_NAME + "." + WorkersAttributes.Phone+" ,"
            + GROUPS_TABLE_NAME + "." + SessionsGroupsAttributes.Id +" ,"
            + GROUPS_TABLE_NAME + "." + SessionsGroupsAttributes.Name +" ,"
            +" FROM "+RECORDS_TABLE_NAME +joinClause+ extraClause;

        ResultSet result = database.SelectQuery(query);
        return result;
    }

}
