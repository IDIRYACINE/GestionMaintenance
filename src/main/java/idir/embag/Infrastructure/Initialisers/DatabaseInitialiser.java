package idir.embag.Infrastructure.Initialisers;

import idir.embag.Infrastructure.Database.Database;
import idir.embag.Infrastructure.Database.ProductQuery;
import idir.embag.Infrastructure.Database.SessionQuery;
import idir.embag.Infrastructure.Database.WorkerQuery;
import idir.embag.Types.Infrastructure.Database.IDatabase;
import idir.embag.Types.Infrastructure.Database.IProductQuery;
import idir.embag.Types.Infrastructure.Database.ISessionQuery;
import idir.embag.Types.Infrastructure.Database.IWorkerQuery;

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
        productQuery.CreateFamiLyCodesTable();
        productQuery.CreateStockTable();
        productQuery.CreateInventoryTable();

        workerQuery.CreateWorkerTable();

        sessionQuery.CreateSessionTable();
        sessionQuery.CreateSessionGroupTable();
        sessionQuery.CreateSessionWorkersTable();
        sessionQuery.CreateSessionRecordTable();


        }
        catch(Exception e){
            
        }

    }

    public void connect(){
        database.Connect();
    }

    

}
