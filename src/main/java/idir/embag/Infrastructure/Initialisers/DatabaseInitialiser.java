package idir.embag.Infrastructure.Initialisers;

import idir.embag.Infrastructure.Database.DesignationsQuery;
import idir.embag.Infrastructure.Database.MysqlDatabase;
import idir.embag.Infrastructure.Database.ProductQuery;
import idir.embag.Infrastructure.Database.SessionQuery;
import idir.embag.Infrastructure.Database.UsersQuery;
import idir.embag.Infrastructure.Database.WorkerQuery;
import idir.embag.Types.Infrastructure.Database.IConnectionParameters;
import idir.embag.Types.Infrastructure.Database.IDatabase;
import idir.embag.Types.Infrastructure.Database.IDesignationsQuery;
import idir.embag.Types.Infrastructure.Database.IProductQuery;
import idir.embag.Types.Infrastructure.Database.ISessionQuery;
import idir.embag.Types.Infrastructure.Database.IUsersQuery;
import idir.embag.Types.Infrastructure.Database.IWorkerQuery;

public class DatabaseInitialiser {

    private IProductQuery productQuery;
    private ISessionQuery sessionQuery;
    private IWorkerQuery workerQuery;
    private IDatabase database;
    private IUsersQuery usersQuery;
    private IDesignationsQuery designationsQuery;

    public DatabaseInitialiser() {
        database = new MysqlDatabase();
        productQuery = new ProductQuery(database);
        sessionQuery = new SessionQuery(database);
        workerQuery = new WorkerQuery(database);
        usersQuery = new UsersQuery(database);
        designationsQuery = new DesignationsQuery(database);
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

    public IUsersQuery getUsersQuery() {
        return usersQuery;
    }

    public IDesignationsQuery getDesignationsQuery() {
        return designationsQuery;
    }

    public void createTables() {
        try {
            productQuery.CreateFamiLyCodesTable();
            productQuery.CreateStockTable();
            productQuery.CreateInventoryTable();

            workerQuery.CreateWorkerTable();

            sessionQuery.CreateSessionTable();
            sessionQuery.CreateSessionGroupTable();
            sessionQuery.CreateSessionWorkersTable();
            sessionQuery.CreateSessionRecordTable();

            designationsQuery.CreateDesignationsTable();
            designationsQuery.CreatePermissionsTable();

            usersQuery.CreateUsersTable();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void connect(IConnectionParameters connectionParameters) {
        database.Connect(connectionParameters);
    }
}
