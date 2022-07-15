package idir.embag.Infrastructure.Database;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

import idir.embag.DataModels.Metadata.EEventDataKeys;
import idir.embag.Infrastructure.Database.Generics.IQuery;
import idir.embag.Infrastructure.Database.Generics.SearchWrapper;

public abstract class ISessionQuery extends IQuery{

    public abstract void RegisterSession(Map<EEventDataKeys,Object> attributes) throws SQLException;
    public abstract void UpdateSession(int sessionId,Map<EEventDataKeys,Object> attributes) throws SQLException;

    public abstract void RegisterSessionGroup(Map<EEventDataKeys,Object> attributes) throws SQLException;
    public abstract void UnregisterSessionGroup(int groupId) throws SQLException;
    public abstract void UpdateSessionGroup(int groupId , Map<EEventDataKeys,Object> attributes) throws SQLException;
    public abstract ResultSet SearchSessionGroup(SearchWrapper parametrers) throws SQLException;


    public abstract void RegsiterSessionWorker(int workerId,Map<EEventDataKeys,Object> attributes) throws SQLException;
    public abstract void UnregisterGroupWorker(int workerId) throws SQLException;
    public abstract ResultSet SearchSessionWorker(SearchWrapper parametrers) throws SQLException;



    public abstract void RegisterSessionRecord(Map<EEventDataKeys,Object> attributes) throws SQLException;
    public abstract ResultSet SearchSessionRecord(SearchWrapper parametrers) throws SQLException;


    public abstract void CreateSessionTable() throws SQLException;
    public abstract void CreateSessionGroupTable() throws SQLException;
    public abstract void CreateSessionRecordTable()  throws SQLException;
    public abstract void CreateSessionWorkersTabel()  throws SQLException;


}
