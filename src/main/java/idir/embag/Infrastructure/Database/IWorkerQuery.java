package idir.embag.Infrastructure.Database;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

import idir.embag.DataModels.Metadata.EEventDataKeys;
import idir.embag.Infrastructure.Database.Generics.IQuery;
import idir.embag.Infrastructure.Database.Generics.SearchWrapper;

public abstract class IWorkerQuery extends IQuery{

    public abstract void RegisterWorker(Map<EEventDataKeys,Object> attributes) throws SQLException;
    public abstract void UpdateWorker(int workerId,Map<EEventDataKeys,Object> attributes) throws SQLException;
    public abstract void UnregisterWorker() throws SQLException;


    public abstract void CreateWorkerTable() throws SQLException;
    public abstract ResultSet SearchWorker(SearchWrapper parametrers) throws SQLException;
}