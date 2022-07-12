package idir.embag.Infrastructure.Database;

import java.sql.SQLException;

import idir.embag.Infrastructure.Database.Generics.IQuery;
import idir.embag.Infrastructure.Database.Generics.MDatabase;

public abstract class ISessionQuery extends IQuery{

    public abstract void RegisterSession(AttributeWrapper<MDatabase.SessionsRecordsAttributes>[] attributes) throws SQLException;
    public abstract void UpdateSession(int sessionId,AttributeWrapper<MDatabase.SessionsRecordsAttributes>[] attributes) throws SQLException;

    public abstract void RegisterSessionGroup(AttributeWrapper<MDatabase.SessionsGroupsAttributes>[] attributes) throws SQLException;
    public abstract void UnregisterSessionGroup(int groupId) throws SQLException;
    public abstract void UpdateSessionGroup(int groupId , AttributeWrapper<MDatabase.SessionsGroupsAttributes>[] attributes) throws SQLException;

    public abstract void RegsiterSessionWorker(int workerId,AttributeWrapper<MDatabase.SessionWorkersAttributes>[] attributes) throws SQLException;
    public abstract void UnregisterGroupWorker(int workerId) throws SQLException;


    public abstract void RegisterSessionRecord(AttributeWrapper<MDatabase.SessionsRecordsAttributes>[] attributes) throws SQLException;
    

    public abstract void CreateSessionTable() throws SQLException;
    public abstract void CreateSessionGroupTable() throws SQLException;
    public abstract void CreateSessionRecordTable()  throws SQLException;
    public abstract void CreateSessionWorkersTabel()  throws SQLException;

}
