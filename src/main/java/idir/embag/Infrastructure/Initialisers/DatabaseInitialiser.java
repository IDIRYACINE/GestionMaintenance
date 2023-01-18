package idir.embag.Infrastructure.Initialisers;

import java.sql.ResultSet;
import java.sql.SQLException;

import idir.embag.Infrastructure.Database.AffectationsQuery;
import idir.embag.Infrastructure.Database.GroupPermissionsQuery;
import idir.embag.Infrastructure.Database.MysqlDatabase;
import idir.embag.Infrastructure.Database.ProductQuery;
import idir.embag.Infrastructure.Database.SessionQuery;
import idir.embag.Infrastructure.Database.UsersQuery;
import idir.embag.Infrastructure.Database.WorkerQuery;
import idir.embag.Types.Infrastructure.Database.IConnectionParameters;
import idir.embag.Types.Infrastructure.Database.IDatabase;
import idir.embag.Types.Infrastructure.Database.IAffectationssQuery;
import idir.embag.Types.Infrastructure.Database.IGroupPermissionsQuery;
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
    private IAffectationssQuery affectationsQuery;
    private IGroupPermissionsQuery groupPermissionsQuery;

    public DatabaseInitialiser() {
        database = new MysqlDatabase();
        productQuery = new ProductQuery(database);
        sessionQuery = new SessionQuery(database);
        workerQuery = new WorkerQuery(database);
        usersQuery = new UsersQuery(database);
        affectationsQuery = new AffectationsQuery(database);
        groupPermissionsQuery = new GroupPermissionsQuery(database);
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

    public IAffectationssQuery getAffectationsQuery() {
        return affectationsQuery;
    }

    public IGroupPermissionsQuery getGroupPermissionsQuery() {
        return groupPermissionsQuery;
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

            affectationsQuery.CreateAffectationsTable();
            affectationsQuery.CreatePermissionsTable();

            usersQuery.CreateUsersTable();

            groupPermissionsQuery.CreateGroupPermissionsTable();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void connect(IConnectionParameters connectionParameters) {
        database.Connect(connectionParameters);
    }

    public ResultSet executeQuery(String query) throws SQLException {
        return database.SelectQuery(query);
    }
}
