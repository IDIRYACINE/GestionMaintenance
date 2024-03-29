package idir.embag.Types.Infrastructure.Database;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Collection;

import idir.embag.Types.Infrastructure.Database.Generics.AttributeWrapper;
import idir.embag.Types.Infrastructure.Database.Generics.IQuery;
import idir.embag.Types.Infrastructure.Database.Generics.LoadWrapper;
import idir.embag.Types.Infrastructure.Database.Generics.SearchWrapper;

public abstract class ISessionQuery extends IQuery{
    
    public abstract void RegisterSession(Collection<AttributeWrapper> attributes) throws SQLException;
    public abstract void UpdateSession(Timestamp sessionId,Collection<AttributeWrapper> attributes) throws SQLException;

    public abstract void RegisterSessionGroup(Collection<AttributeWrapper> attributes) throws SQLException;
    public abstract void UnregisterSessionGroup(int groupId) throws SQLException;
    public abstract void UpdateSessionGroup(int groupId , Collection<AttributeWrapper> attributes) throws SQLException;

    public abstract void RegsiterSessionWorker(Collection<AttributeWrapper> attributes) throws SQLException;
    public abstract void UnregisterGroupWorker(int workerId) throws SQLException;
    public abstract void UpdateSessionWorker(int sessionWorkerId, Collection<AttributeWrapper> collection) throws SQLException;

    public abstract void RegisterSessionRecord(Collection<AttributeWrapper> attributes) throws SQLException;


    public abstract void CloseActiveSession(Timestamp id) throws SQLException;

    public abstract ResultSet SearchSessionRecord(SearchWrapper parametrers) throws SQLException;
    public abstract ResultSet SearchSessionWorker(SearchWrapper parametrers) throws SQLException;
    public abstract ResultSet SearchSessionGroup(SearchWrapper parametrers) throws SQLException;
    
    public abstract ResultSet LoadSessionRecord(LoadWrapper parametrers) throws SQLException;
    public abstract ResultSet LoadSessionGroup(LoadWrapper parametrers) throws SQLException;
    public abstract ResultSet LoadSessionWorkers(LoadWrapper parametrers)  throws SQLException;

    public abstract void CreateSessionTable() throws SQLException;
    public abstract void CreateSessionGroupTable() throws SQLException;
    public abstract void CreateSessionRecordTable()  throws SQLException;
    public abstract void CreateSessionWorkersTable()  throws SQLException;
    public abstract void RegisterSessionRecordCollection(Collection<AttributeWrapper[]> attributesCollection) throws SQLException;
   
}
