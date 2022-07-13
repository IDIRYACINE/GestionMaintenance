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
        database = new Database("dATABASE_URL");
        productQuery = new ProductQuery(database);
        sessionQuery = new SessionQuery(database);
        workerQuery = new WorkerQuery(database);
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

    

}
