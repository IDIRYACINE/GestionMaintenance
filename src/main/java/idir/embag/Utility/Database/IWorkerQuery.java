package idir.embag.Utility.Database;

import java.sql.SQLException;

import idir.embag.Utility.Database.Generics.IQuery;
import idir.embag.Utility.Database.Generics.MDatabase.WorkersAttributes;

public abstract class IWorkerQuery extends IQuery{

    public abstract void RegisterWorker(AttributeWrapper<WorkersAttributes>[] attributes) throws SQLException;
    public abstract void UpdateWorker(int workerId,AttributeWrapper<WorkersAttributes>[] attributes) throws SQLException;
    public abstract void UnregisterWorker() throws SQLException;


    public abstract void CreateWorkerTable() throws SQLException;
}