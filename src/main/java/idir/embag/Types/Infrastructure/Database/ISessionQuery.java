package idir.embag.Types.Infrastructure.Database;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;

import idir.embag.Types.Infrastructure.Database.Generics.AttributeWrapper;
import idir.embag.Types.Infrastructure.Database.Generics.IQuery;
import idir.embag.Types.Infrastructure.Database.Generics.SearchWrapper;

public abstract class ISessionQuery extends IQuery{
    
    public abstract void RegisterSession(Collection<AttributeWrapper> attributes) throws SQLException;
    public abstract void UpdateSession(int sessionId,Collection<AttributeWrapper> attributes) throws SQLException;

    public abstract void RegisterSessionGroup(Collection<AttributeWrapper> attributes) throws SQLException;
    public abstract void UnregisterSessionGroup(int groupId) throws SQLException;
    public abstract void UpdateSessionGroup(int groupId , Collection<AttributeWrapper> attributes) throws SQLException;

    public abstract void RegsiterSessionWorker(int workerId,Collection<AttributeWrapper> attributes) throws SQLException;
    public abstract void UnregisterGroupWorker(int workerId) throws SQLException;

    public abstract void RegisterSessionRecord(Collection<AttributeWrapper> attributes) throws SQLException;

    public abstract ResultSet SearchSessionRecord(SearchWrapper parametrers) throws SQLException;
    public abstract ResultSet SearchSessionWorker(SearchWrapper parametrers) throws SQLException;
    public abstract ResultSet SearchSessionGroup(SearchWrapper parametrers) throws SQLException;

    public abstract void CreateSessionTable() throws SQLException;
    public abstract void CreateSessionGroupTable() throws SQLException;
    public abstract void CreateSessionRecordTable()  throws SQLException;
    public abstract void CreateSessionWorkersTabel()  throws SQLException;
}
