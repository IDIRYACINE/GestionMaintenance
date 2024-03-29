package idir.embag.Infrastructure.Database;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;

import idir.embag.Types.Infrastructure.Database.Metadata.EInventoryAttributes;
import idir.embag.Types.Infrastructure.Database.Metadata.ESessionAttributes;
import idir.embag.Types.Infrastructure.Database.Metadata.ESessionGroupAttributes;
import idir.embag.Types.Infrastructure.Database.Metadata.ESessionRecordAttributes;
import idir.embag.Types.Infrastructure.Database.Metadata.ESessionWorkerAttributes;
import idir.embag.Types.Infrastructure.Database.Metadata.ETables;
import idir.embag.Types.Infrastructure.Database.Metadata.EWorkerAttributes;
import idir.embag.Types.Infrastructure.Database.IDatabase;
import idir.embag.Types.Infrastructure.Database.ISessionQuery;
import idir.embag.Types.Infrastructure.Database.Generics.AttributeWrapper;
import idir.embag.Types.Infrastructure.Database.Generics.LoadWrapper;
import idir.embag.Types.Infrastructure.Database.Generics.SearchWrapper;

public class SessionQuery extends ISessionQuery {

    private IDatabase database;

    public SessionQuery(IDatabase database) {
        this.database = database;
    }

    @Override
    public void RegisterSession(Collection<AttributeWrapper> attributes) throws SQLException {
        String query = "INSERT INTO " + ETables.Sessions + InsertWrapperToQuery(attributes);
        database.InsertQuery(query);

    }

    @Override
    public void UpdateSession(Timestamp sessionId, Collection<AttributeWrapper> attributes)
            throws SQLException {
        String whereClause = " WHERE " + ESessionAttributes.SessionId + "=" + sessionId;
        String query = "UPDATE " + ETables.Sessions + UpdateWrapperToQuery(attributes) + whereClause;
        database.UpdateQuery(query);

    }

    @Override
    public void RegisterSessionGroup(Collection<AttributeWrapper> attributes) throws SQLException {
        String query = "INSERT INTO " + ETables.SessionsGroups + InsertWrapperToQuery(attributes);
        database.InsertQuery(query);

    }

    @Override
    public void UnregisterSessionGroup(int groupId) throws SQLException {
        String whereClause = " WHERE " + ESessionGroupAttributes.GroupId + "=" + groupId;
        String query = "DELETE FROM " + ETables.SessionsGroups + whereClause;

        database.DeleteQuery(query);
    }

    @Override
    public void UpdateSessionGroup(int groupId, Collection<AttributeWrapper> attributes)
            throws SQLException {
        String whereClause = " WHERE " + ESessionGroupAttributes.GroupId + "=" + groupId;
        String query = "UPDATE " + ETables.SessionsGroups + UpdateWrapperToQuery(attributes) + whereClause;
        database.UpdateQuery(query);
    }

    @Override
    public void RegsiterSessionWorker(Collection<AttributeWrapper> attributes)
            throws SQLException {
        String query = "INSERT INTO " + ETables.SessionWorkers + InsertWrapperToQuery(attributes);
        database.UpdateQuery(query);
    }

    @Override
    public void UnregisterGroupWorker(int workerId) throws SQLException {
        String whereClause = " WHERE " + ESessionWorkerAttributes.WorkerId + "=" + workerId;
        String query = "DELETE FROM " + ETables.SessionWorkers + whereClause;

        database.DeleteQuery(query);
    }

    @Override
    public void UpdateSessionWorker(int sessionWorkerId, Collection<AttributeWrapper> attributes) throws SQLException {
        String whereClause = " WHERE " + ESessionWorkerAttributes.WorkerId + "=" + sessionWorkerId;
        String query = "UPDATE " + ETables.SessionWorkers + UpdateWrapperToQuery(attributes) + whereClause;
        database.UpdateQuery(query);

    }

    @Override
    public void RegisterSessionRecord(Collection<AttributeWrapper> attributes) throws SQLException {
        String query = "INSERT INTO " + ETables.SessionsRecords + InsertWrapperToQuery(attributes);
        database.InsertQuery(query);

    }

    @Override
    public void CreateSessionTable() throws SQLException {
        String query = "CREATE TABLE IF NOT EXISTS " + ETables.Sessions + " (\n"
                + ESessionAttributes.SessionId + " TIMESTAMP PRIMARY KEY ,\n"
                + ESessionAttributes.Active + " BOOLEAN,\n"
                + ESessionAttributes.StartDate + " DATE,\n"
                + ESessionAttributes.EndDate + " DATE,\n"
                + ESessionAttributes.PriceShiftValue + " REAL,\n"
                + ESessionAttributes.QuantityShiftValue + " REAL)\n";

        database.CreateQuery(query);

    }

    @Override
    public void CreateSessionGroupTable() throws SQLException {
        String query = "CREATE TABLE IF NOT EXISTS " + ETables.SessionsGroups + " (\n"
                + ESessionGroupAttributes.GroupId + " INTEGER PRIMARY KEY AUTO_INCREMENT,\n"
                + ESessionGroupAttributes.SessionId + " TIMESTAMP,\n"
                + ESessionGroupAttributes.GroupName + " TEXT,\n"
                + ESessionGroupAttributes.GroupSupervisorId + " INTEGER,\n"

                + "FOREIGN KEY (" + ESessionRecordAttributes.SessionId + ")\n"
                + "REFERENCES " + ETables.Sessions + "(" + ESessionAttributes.SessionId + ")\n"
                + "ON DELETE CASCADE ON UPDATE NO ACTION) \n";

        database.CreateQuery(query);

    }

    @Override
    public void CreateSessionRecordTable() throws SQLException {
        String query = "CREATE TABLE IF NOT EXISTS " + ETables.SessionsRecords + " (\n"
                + ESessionRecordAttributes.RecordId + " TIMESTAMP PRIMARY KEY DEFAULT CURRENT_TIMESTAMP,\n"
                + ESessionRecordAttributes.SessionId + " TIMESTAMP,\n"
                + ESessionRecordAttributes.WorkerId + " INTEGER,\n"
                + ESessionRecordAttributes.GroupId + " INTEGER,\n"
                + ESessionRecordAttributes.WorkerName + " TEXT,\n"
                + ESessionRecordAttributes.ArticleName + " TEXT,\n"
                + ESessionRecordAttributes.InventoryId + " INTEGER,\n"
                + ESessionRecordAttributes.RecordDate + " DATE,\n"
                + ESessionRecordAttributes.PriceShift + " REAL,\n"
                + ESessionRecordAttributes.QuantityShift + " INTEGER,\n"
                + ESessionRecordAttributes.RecordQuantity + " INTEGER,\n"
                + ESessionRecordAttributes.StockQuantity + " INTEGER,\n"
                + ESessionRecordAttributes.StockPrice + " REAL,\n"

                + "FOREIGN KEY (" + ESessionRecordAttributes.SessionId + ")\n"
                + "REFERENCES " + ETables.Sessions + "(" + ESessionAttributes.SessionId + ")\n"
                + "ON DELETE CASCADE ON UPDATE NO ACTION, \n"

                + "FOREIGN KEY (" + ESessionRecordAttributes.WorkerId + ")\n"
                + "REFERENCES " + ETables.Workers + "(" + EWorkerAttributes.WorkerId + ")\n"
                + "ON DELETE CASCADE ON UPDATE NO ACTION, \n"

                + "FOREIGN KEY (" + ESessionRecordAttributes.GroupId + ")\n"
                + "REFERENCES " + ETables.SessionsGroups + "(" + ESessionGroupAttributes.GroupId + ")\n"
                + "ON DELETE CASCADE ON UPDATE NO ACTION, \n"

                + "FOREIGN KEY (" + ESessionRecordAttributes.InventoryId + ")\n"
                + "REFERENCES " + ETables.Inventory + "(" + EInventoryAttributes.ArticleId + ")\n"
                + "ON DELETE CASCADE ON UPDATE NO ACTION) \n";

        database.CreateQuery(query);
    }

    @Override
    public void CreateSessionWorkersTable() throws SQLException {
        String query = "CREATE TABLE IF NOT EXISTS " + ETables.SessionWorkers + " (\n"
                + ESessionWorkerAttributes.WorkerId + " INTEGER PRIMARY KEY ,\n"
                + ESessionWorkerAttributes.GroupId + " INTEGER,\n"
                + ESessionWorkerAttributes.Password + " TEXT,\n"
                + ESessionWorkerAttributes.Username + " TEXT,\n"
                + ESessionWorkerAttributes.SupervisorId + " INTEGER,\n"

                + "FOREIGN KEY (" + ESessionRecordAttributes.WorkerId + ")\n"
                + "REFERENCES " + ETables.Workers + "(" + EWorkerAttributes.WorkerId + ")\n"
                + "ON DELETE CASCADE ON UPDATE NO ACTION, \n"

                + "FOREIGN KEY (" + ESessionRecordAttributes.GroupId + ")\n"
                + "REFERENCES " + ETables.SessionsGroups + "(" + ESessionGroupAttributes.GroupId + ")\n"
                + "ON DELETE CASCADE ON UPDATE NO ACTION) \n";

        database.CreateQuery(query);

    }

    @Override
    public ResultSet SearchSessionGroup(SearchWrapper parametrers) throws SQLException {
        String whereClause = " WHERE " + SearchWrapperToWhereClause(parametrers);
        String query = "SELECT * FROM " + ETables.SessionsGroups + whereClause;

        ResultSet result = database.SelectQuery(query);
        return result;
    }

    @Override
    public ResultSet SearchSessionWorker(SearchWrapper parametrers) throws SQLException {
        String whereClause = " WHERE " + SearchWrapperToWhereClause(parametrers);

        String joinClause = " INNER JOIN " + ETables.Workers + " ON "
                + ETables.SessionWorkers + "." + ESessionRecordAttributes.WorkerId
                + "=" + ETables.Workers + "." + EWorkerAttributes.WorkerId

                + " INNER JOIN " + ETables.SessionsGroups + " ON "
                + ETables.SessionWorkers + "." + ESessionWorkerAttributes.GroupId
                + "=" + ETables.SessionsGroups + "." + ESessionGroupAttributes.GroupId;

        String query = "SELECT "
                + ETables.Workers + "." + EWorkerAttributes.WorkerId + " ,"
                + ETables.Workers + "." + EWorkerAttributes.WorkerName + " ,"
                + ETables.Workers + "." + EWorkerAttributes.WorkerPhone + " ,"
                + ETables.SessionWorkers + "." + ESessionWorkerAttributes.Password + " ,"
                + ETables.SessionWorkers + "." + ESessionWorkerAttributes.SupervisorId + " ,"
                + ETables.SessionWorkers + "." + ESessionWorkerAttributes.Username + " ,"
                + ETables.SessionsGroups + "." + ESessionGroupAttributes.GroupId + " ,"
                + ETables.SessionsGroups + "." + ESessionGroupAttributes.GroupName 
                + " FROM " + ETables.SessionWorkers + joinClause + whereClause;


        ResultSet result = database.SelectQuery(query);
        return result;
    }

    @Override
    public ResultSet SearchSessionRecord(SearchWrapper parametrers) throws SQLException {
        String whereClause = " WHERE " + SearchWrapperToWhereClause(parametrers);

        String query = "SELECT * " + " FROM " + ETables.SessionsRecords + whereClause;

        ResultSet result = database.SelectQuery(query);
        return result;
    }

    @Override
    public ResultSet LoadSessionRecord(LoadWrapper parametrers) throws SQLException {
        String extraClause = " LIMIT " + parametrers.getLimit() + " OFFSET " + parametrers.getOffset();

        String query = "SELECT * "+ " FROM " + ETables.SessionsRecords  + extraClause;
        ResultSet result = database.SelectQuery(query);
        return result;
    }

    @Override
    public ResultSet LoadSessionGroup(LoadWrapper parametrers) throws SQLException {
        String extraClause = " LIMIT " + parametrers.getLimit() + " OFFSET " + parametrers.getOffset();
        String query = "SELECT * FROM " + ETables.SessionsGroups + extraClause;
        ResultSet result = database.SelectQuery(query);
        return result;
    }

    @Override
    public ResultSet LoadSessionWorkers(LoadWrapper parametrers) throws SQLException {

        String joinClause = " INNER JOIN " + ETables.SessionsGroups + " ON "
                + ETables.SessionWorkers + "." + ESessionWorkerAttributes.GroupId
                + "=" + ETables.SessionsGroups + "." + ESessionGroupAttributes.GroupId

                + " INNER JOIN " + ETables.Workers + " ON "
                + ETables.SessionWorkers + "." + ESessionWorkerAttributes.WorkerId
                + "=" + ETables.Workers + "." + EWorkerAttributes.WorkerId;

        String query = "SELECT "
                + ETables.Workers + "." + EWorkerAttributes.WorkerId + " ,"
                + ETables.Workers + "." + EWorkerAttributes.WorkerName + " ,"
                + ETables.Workers + "." + EWorkerAttributes.WorkerPhone + " ,"
                + ETables.SessionWorkers + "." + ESessionWorkerAttributes.Password + " ,"
                + ETables.SessionWorkers + "." + ESessionWorkerAttributes.Username + " ,"
                + ETables.SessionsGroups + "." + ESessionGroupAttributes.GroupId + " ,"
                + ETables.SessionWorkers + "." + ESessionWorkerAttributes.SupervisorId + " ,"
                + ETables.SessionsGroups + "." + ESessionGroupAttributes.GroupName + " "
                + " FROM " + ETables.SessionWorkers + joinClause ;

        ResultSet result = database.SelectQuery(query);
        return result;
    }

    @Override
    public void CreateIndexes() throws SQLException {

    }

    @Override
    public void RegisterSessionRecordCollection(Collection<AttributeWrapper[]> collection) throws SQLException {
        String query = "INSERT INTO " + ETables.SessionsRecords + InsertCollectionToQuery(collection);
        database.InsertQuery(query);

    }

    @Override
    public void CloseActiveSession(Timestamp id) throws SQLException {
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        Date date = new Date();

        String endDate = formatter.format(date);
        String query = "Update " + ETables.Sessions + " SET "
                + ESessionAttributes.EndDate + " = '" + endDate + "'"
                + " WHERE " + ESessionAttributes.SessionId + " = " + id.toString();        

        database.UpdateQuery(query);

        query = "TRUNCATE " + ETables.SessionWorkers;
        database.UpdateQuery(query);


    }
}
