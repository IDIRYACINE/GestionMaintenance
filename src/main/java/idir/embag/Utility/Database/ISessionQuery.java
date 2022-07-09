package idir.embag.Utility.Database;

import java.sql.SQLException;

public interface ISessionQuery {

    public void RegisterSession() throws SQLException;
    public void UpdateSession() throws SQLException;


    public void RegisterSessionGroup() throws SQLException;
    public void UnregisterSessionGroup() throws SQLException;
    public void UpdateSessionGroup() throws SQLException;

    public void RegisterGroupWorker() throws SQLException;
    public void UnregisterGroupWorker() throws SQLException;


    public void RegisterSessionRecord() throws SQLException;
    

    public void CreateSessionTable() throws SQLException;
    public void CreateSessionGroupTable() throws SQLException;
    public void CreateSessionRecordTable()  throws SQLException;
    public void CreateSessionWorkersTabe()  throws SQLException;

}
