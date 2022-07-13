package idir.embag.Infrastructure.Database.Implementations;

import java.sql.SQLException;
import java.util.List;

import idir.embag.Infrastructure.Database.IDatabase;
import idir.embag.Infrastructure.Database.ISessionQuery;
import idir.embag.Infrastructure.Database.Generics.AttributeWrapper;
import idir.embag.Infrastructure.Database.Generics.MDatabase;
import idir.embag.Infrastructure.Database.Generics.MDatabase.SessionWorkersAttributes;
import idir.embag.Infrastructure.Database.Generics.MDatabase.SessionsGroupsAttributes;
import idir.embag.Infrastructure.Database.Generics.MDatabase.SessionsRecordsAttributes;
import idir.embag.Infrastructure.Database.Generics.SearchWrapper;

public class SessionQuery extends ISessionQuery{

    private IDatabase database;
    
    private static final MDatabase.Tables SESSION_TABLE_NAME = MDatabase.Tables.Sessions;
    private static final MDatabase.Tables RECORDS_TABLE_NAME = MDatabase.Tables.SessionsRecords;
    private static final MDatabase.Tables SESSION_WORKERS_TABLE_NAME = MDatabase.Tables.SessionWorkers;
    private static final MDatabase.Tables GROUPS_TABLE_NAME = MDatabase.Tables.SessionsGroups;


    public SessionQuery(IDatabase database) {
        this.database = database;
    }

    
    @Override
    public void RegisterSession(AttributeWrapper<SessionsRecordsAttributes>[] attributes) throws SQLException {
        String query = "INSERT INTO "+SESSION_TABLE_NAME+ InsertWrapperToQuery(attributes);
        database.InsertQuery(query);
        
    }

    @Override
    public void UpdateSession(int sessionId, AttributeWrapper<SessionsRecordsAttributes>[] attributes)
            throws SQLException {
                String whereClause = " WHERE "+MDatabase.StockAttributes.ArticleId + "=" + sessionId;
                String query = "UPDATE "+SESSION_TABLE_NAME+ UpdateWrapperToQuery(attributes)+ whereClause;
                database.UpdateQuery(query);
        
    }

    @Override
    public void RegisterSessionGroup(AttributeWrapper<SessionsGroupsAttributes>[] attributes) throws SQLException {
        String query = "INSERT INTO "+GROUPS_TABLE_NAME+ InsertWrapperToQuery(attributes);
        database.InsertQuery(query);
        
    }

    @Override
    public void UnregisterSessionGroup(int groupId) throws SQLException {}

    @Override
    public void UpdateSessionGroup(int groupId, AttributeWrapper<SessionsGroupsAttributes>[] attributes)
            throws SQLException {
                String whereClause = " WHERE "+MDatabase.SessionsGroupsAttributes.Id + "=" + groupId;
                String query = "UPDATE "+GROUPS_TABLE_NAME+ UpdateWrapperToQuery(attributes)+ whereClause;
                database.UpdateQuery(query);
        
    }

    @Override
    public void RegsiterSessionWorker(int workerId, AttributeWrapper<SessionWorkersAttributes>[] attributes)
            throws SQLException {
                String whereClause = " WHERE "+MDatabase.SessionWorkersAttributes.WorkerId + "=" + workerId;
                String query = "UPDATE "+SESSION_WORKERS_TABLE_NAME+ UpdateWrapperToQuery(attributes)+ whereClause;
                database.UpdateQuery(query);
        
    }

    @Override
    public void UnregisterGroupWorker(int workerId) throws SQLException {}

    @Override
    public void RegisterSessionRecord(AttributeWrapper<SessionsRecordsAttributes>[] attributes) throws SQLException {
        String query = "INSERT INTO "+RECORDS_TABLE_NAME+ InsertWrapperToQuery(attributes);
        database.InsertQuery(query);
        
    }

    @Override
    public void CreateSessionTable() throws SQLException {
        String query = "CREATE TABLE "+ SESSION_TABLE_NAME +" (\n"
            + MDatabase.SessionsAttributes.SessionId+" INTEGER PRIMARY KEY AUTOINCREMENT,\n"
            + MDatabase.SessionsAttributes.StartDate+" DATE,\n"
            + MDatabase.SessionsAttributes.EndDate+" DATE,\n"
            + MDatabase.SessionsAttributes.PriceShiftValue+" REAL,\n"
            + MDatabase.SessionsAttributes.QuantityShiftValue+" REAL)\n";
           
           
        database.CreateQuery(query);
        
    }

    @Override
    public void CreateSessionGroupTable() throws SQLException {
        String query = "CREATE TABLE "+ GROUPS_TABLE_NAME +" (\n"
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
        String query = "CREATE TABLE "+ RECORDS_TABLE_NAME +" (\n"
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
    public void CreateSessionWorkersTabel() throws SQLException {
        String query = "CREATE TABLE "+ SESSION_WORKERS_TABLE_NAME +" (\n"
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
    public List<Object> SearchSessionGroup(SearchWrapper parametrers) throws SQLException {
        // TODO Auto-generated method stub
        return null;
    }


    @Override
    public List<Object> SearchSessionWorker(SearchWrapper parametrers) throws SQLException {
        // TODO Auto-generated method stub
        return null;
    }


    @Override
    public List<Object> SearchSessionRecord(SearchWrapper parametrers) throws SQLException {
        // TODO Auto-generated method stub
        return null;
    }


}
