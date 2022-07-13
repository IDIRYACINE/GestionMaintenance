package idir.embag.Infrastructure.Initialisers;

import idir.embag.Infrastructure.Database.IDatabase;
import idir.embag.Infrastructure.Database.IProductQuery;
import idir.embag.Infrastructure.Database.ISessionQuery;
import idir.embag.Infrastructure.Database.IWorkerQuery;
import idir.embag.Infrastructure.Database.Implementations.Database;
import idir.embag.Infrastructure.Database.Implementations.ProductQuery;
import idir.embag.Infrastructure.Database.Implementations.SessionQuery;
import idir.embag.Infrastructure.Database.Implementations.WorkerQuery;

public class DatabaseInitialiser {
    
    private IProductQuery productQuery;
    private ISessionQuery sessionQuery;
    private IWorkerQuery workerQuery;
    private IDatabase database;

    

    public DatabaseInitialiser() {
        database = new Database("Data/TestDatabase.db");
        productQuery = new ProductQuery(database);
        sessionQuery = new SessionQuery(database);
        workerQuery = new WorkerQuery(database);
        database.Connect();
    }

    public IProductQuery getProductQuery() {
        return productQuery;
    }
    public ISessionQuery getSessionQuery() {
        return sessionQuery;
    }
    public IWorkerQuery getWorkerQuery() {
        return workerQuery;
    }

    public void createTables(){
        try{
        productQuery.CreateFamiLCodeTable();
        productQuery.CreateStockTable();
        productQuery.CreateInventoryTable();

        workerQuery.CreateWorkerTable();

        sessionQuery.CreateSessionTable();
        sessionQuery.CreateSessionGroupTable();
        sessionQuery.CreateSessionWorkersTabel();
        sessionQuery.CreateSessionRecordTable();


        }
        catch(Exception e){
            e.printStackTrace();
        }

    }

    public void connect(){
        database.Connect();
    }

    

}
