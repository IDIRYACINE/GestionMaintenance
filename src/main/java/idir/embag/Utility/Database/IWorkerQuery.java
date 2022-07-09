package idir.embag.Utility.Database;

import java.sql.SQLException;

public interface IWorkerQuery{

    public void RegisterWorker() throws SQLException;
    public void UpdateWorker() throws SQLException;
    public void UnregisterWorker() throws SQLException;


    public void CreateWorkerTable() throws SQLException;
}