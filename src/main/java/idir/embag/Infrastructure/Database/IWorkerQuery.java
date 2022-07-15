package idir.embag.Infrastructure.Database;

import java.sql.SQLException;
import java.util.List;

import idir.embag.Infrastructure.Database.Generics.AttributeWrapper;
import idir.embag.Infrastructure.Database.Generics.IQuery;
import idir.embag.Infrastructure.Database.Generics.SearchWrapper;

public abstract class IWorkerQuery extends IQuery{

    public abstract void RegisterWorker(AttributeWrapper[] attributes) throws SQLException;
    public abstract void UpdateWorker(int workerId,AttributeWrapper[] attributes) throws SQLException;
    public abstract void UnregisterWorker() throws SQLException;


    public abstract void CreateWorkerTable() throws SQLException;
    public abstract List<Object> SearchWorker(SearchWrapper parametrers) throws SQLException;
}