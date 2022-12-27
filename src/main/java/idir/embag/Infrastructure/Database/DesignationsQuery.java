package idir.embag.Infrastructure.Database;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;

import idir.embag.Types.Infrastructure.Database.IDatabase;
import idir.embag.Types.Infrastructure.Database.IDesignationsQuery;
import idir.embag.Types.Infrastructure.Database.Generics.AttributeWrapper;
import idir.embag.Types.Infrastructure.Database.Generics.LoadWrapper;
import idir.embag.Types.Infrastructure.Database.Generics.SearchWrapper;
import idir.embag.Types.Infrastructure.Database.Metadata.EDesignationAttributes;
import idir.embag.Types.Infrastructure.Database.Metadata.ETables;
import idir.embag.Types.Infrastructure.Database.Metadata.EUsersAttributes;

public class DesignationsQuery extends IDesignationsQuery {
    private IDatabase database;

    public DesignationsQuery(IDatabase database) {
        this.database = database;
    }

    @Override
    public void RegisterDesignation(Collection<AttributeWrapper> attributes) throws SQLException {
        String query = "INSERT INTO " + ETables.Designations + InsertWrapperToQuery(attributes);
        database.InsertQuery(query);

    }

    @Override
    public void UpdateDesignation(int designationId, Collection<AttributeWrapper> attributes) throws SQLException {
        String whereClause = " WHERE " + EDesignationAttributes.DesignationId + "=" + designationId;
        String query = "UPDATE " + ETables.Designations + UpdateWrapperToQuery(attributes) + whereClause;
        database.UpdateQuery(query);

    }

    @Override
    public void DeleteDesignation(int designationId) throws SQLException {
        String whereClause = " WHERE " + EDesignationAttributes.DesignationId + "=" + designationId;
        String query = "DELETE FROM " + ETables.Designations + whereClause;

        database.DeleteQuery(query);

    }

    @Override
    public void CreateDesignationsTable() throws SQLException {
        String query = "CREATE TABLE IF NOT EXISTS " + ETables.Designations + " (\n"
                + EDesignationAttributes.DesignationId + " INTEGER PRIMARY  KEY ,\n"
                + EDesignationAttributes.DesignationName + " TEXT\n";

        database.CreateQuery(query);
    }

    @Override
    public void CreatePermissionsTable() throws SQLException {
        String query = "CREATE TABLE IF NOT EXISTS " + ETables.DesignationsPermissions + " (\n"
                + EUsersAttributes.UserId + " INTEGER  ,\n"
                + EDesignationAttributes.DesignationId + " INTEGER \n"
                + " PRIMARY KEY (" + EUsersAttributes.UserId + ","
                + EDesignationAttributes.DesignationId + "),\n";

        database.CreateQuery(query);
    }

    @Override
    public void CreateIndexes() throws SQLException {

    }

    @Override
    public ResultSet SearchDesignations(SearchWrapper parametrers) throws SQLException {
        String whereClause = " WHERE " + SearchWrapperToWhereClause(parametrers);
        String query = "SELECT * FROM " + ETables.Designations + whereClause;

        ResultSet result = database.SelectQuery(query);
        return result;
    }

    @Override
    public ResultSet LoadDesignations(LoadWrapper parametrers) throws SQLException {
        String extraClause = " LIMIT " + parametrers.getLimit() + " OFFSET " + parametrers.getOffset();
        String query = "SELECT * FROM " + ETables.Designations + extraClause;
        ResultSet result = database.SelectQuery(query);
        return result;
    }

}
