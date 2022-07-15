package idir.embag.Infrastructure.Database;

import java.sql.SQLException;
import java.util.List;

import idir.embag.Infrastructure.Database.Generics.AttributeWrapper;
import idir.embag.Infrastructure.Database.Generics.IQuery;
import idir.embag.Infrastructure.Database.Generics.SearchWrapper;

public abstract class ISessionQuery extends IQuery{

    public abstract void RegisterSession(AttributeWrapper[] attributes) throws SQLException;
    public abstract void UpdateSession(int sessionId,AttributeWrapper[] attributes) throws SQLException;

    public abstract void RegisterSessionGroup(AttributeWrapper[] attributes) throws SQLException;
    public abstract void UnregisterSessionGroup(int groupId) throws SQLException;
    public abstract void UpdateSessionGroup(int groupId , AttributeWrapper[] attributes) throws SQLException;
    public abstract List<Object> SearchSessionGroup(SearchWrapper parametrers) throws SQLException;


    public abstract void RegsiterSessionWorker(int workerId,AttributeWrapper[] attributes) throws SQLException;
    public abstract void UnregisterGroupWorker(int workerId) throws SQLException;
    public abstract List<Object> SearchSessionWorker(SearchWrapper parametrers) throws SQLException;



    public abstract void RegisterSessionRecord(AttributeWrapper[] attributes) throws SQLException;
    public abstract List<Object> SearchSessionRecord(SearchWrapper parametrers) throws SQLException;


    public abstract void CreateSessionTable() throws SQLException;
    public abstract void CreateSessionGroupTable() throws SQLException;
    public abstract void CreateSessionRecordTable()  throws SQLException;
    public abstract void CreateSessionWorkersTabel()  throws SQLException;


}
