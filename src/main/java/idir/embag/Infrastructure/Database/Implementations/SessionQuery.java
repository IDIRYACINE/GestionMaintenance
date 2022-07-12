package idir.embag.Infrastructure.Database.Implementations;

import java.sql.SQLException;

import idir.embag.Infrastructure.Database.AttributeWrapper;
import idir.embag.Infrastructure.Database.IDatabase;
import idir.embag.Infrastructure.Database.ISessionQuery;
import idir.embag.Infrastructure.Database.Generics.MDatabase;
import idir.embag.Infrastructure.Database.Generics.MDatabase.SessionWorkersAttributes;
import idir.embag.Infrastructure.Database.Generics.MDatabase.SessionsGroupsAttributes;
import idir.embag.Infrastructure.Database.Generics.MDatabase.SessionsRecordsAttributes;

public class SessionQuery extends ISessionQuery{

    private IDatabase database;
    
    private static final MDatabase.Tables SESSION_TABLE_NAME = MDatabase.Tables.Sessions;
    private static final MDatabase.Tables RECORDS_TABLE_NAME = MDatabase.Tables.SessionsRecords;
    private static final MDatabase.Tables SESSION_WORKERS_TABLE_NAME = MDatabase.Tables.SessionWorkers;
    private static final MDatabase.Tables GROUPS_TABLE_NAME = MDatabase.Tables.SessionWorkers;


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
    public void UnregisterSessionGroup(int groupId) throws SQLException {
        // TODO Auto-generated method stub
        
    }

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
    public void UnregisterGroupWorker(int workerId) throws SQLException {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void RegisterSessionRecord(AttributeWrapper<SessionsRecordsAttributes>[] attributes) throws SQLException {
        String query = "INSERT INTO "+RECORDS_TABLE_NAME+ InsertWrapperToQuery(attributes);
        database.InsertQuery(query);
        
    }

    @Override
    public void CreateSessionTable() throws SQLException {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void CreateSessionGroupTable() throws SQLException {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void CreateSessionRecordTable() throws SQLException {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void CreateSessionWorkersTabel() throws SQLException {
        // TODO Auto-generated method stub
        
    }


}
