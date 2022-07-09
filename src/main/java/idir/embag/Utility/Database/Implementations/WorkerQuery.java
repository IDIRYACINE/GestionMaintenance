package idir.embag.Utility.Database.Implementations;

import java.sql.SQLException;

public interface WorkerQuery{

    public void RegisterWorker() throws SQLException;
    public void UpdateWorker() throws SQLException;
    public void UnregisterWorker() throws SQLException;


    public void CreateWorkerTable() throws SQLException;
}