package idir.embag.Utility.Database.Implementations;

import java.sql.SQLException;

import idir.embag.Utility.Database.AttributeWrapper;
import idir.embag.Utility.Database.IDatabase;
import idir.embag.Utility.Database.IWorkerQuery;
import idir.embag.Utility.Database.Generics.MDatabase;
import idir.embag.Utility.Database.Generics.MDatabase.WorkersAttributes;

public class WorkerQuery extends IWorkerQuery{
    private IDatabase database;
    

    private static final MDatabase.Tables WORKERS_TABLE_NAME = MDatabase.Tables.Workers;
    

    
    public WorkerQuery(IDatabase database) {
        this.database = database;
    }



    @Override
    public void UnregisterWorker() throws SQLException {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void RegisterWorker(AttributeWrapper<WorkersAttributes>[] attributes) throws SQLException {
        String query = "INSERT INTO "+WORKERS_TABLE_NAME+ InsertWrapperToQuery(attributes);
        database.InsertQuery(query);
        
    }

    @Override
    public void UpdateWorker(int workerId, AttributeWrapper<WorkersAttributes>[] attributes) throws SQLException {
        String whereClause = " WHERE "+MDatabase.StockAttributes.ArticleId + "=" + workerId;
        String query = "UPDATE "+WORKERS_TABLE_NAME+ UpdateWrapperToQuery(attributes)+ whereClause;
        database.UpdateQuery(query);
    }


    @Override
    public void CreateWorkerTable() throws SQLException {
        // TODO Auto-generated method stub
        
    }

}