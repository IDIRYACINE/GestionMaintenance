package idir.embag.Infrastructure.Database;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;

import idir.embag.Types.Infrastructure.Database.IDatabase;
import idir.embag.Types.Infrastructure.Database.IAffectationssQuery;
import idir.embag.Types.Infrastructure.Database.Generics.AttributeWrapper;
import idir.embag.Types.Infrastructure.Database.Generics.LoadWrapper;
import idir.embag.Types.Infrastructure.Database.Generics.SearchWrapper;
import idir.embag.Types.Infrastructure.Database.Metadata.EAffectationAttributes;
import idir.embag.Types.Infrastructure.Database.Metadata.ETables;
import idir.embag.Types.Infrastructure.Database.Metadata.EUsersAttributes;

public class AffectationsQuery extends IAffectationssQuery {
    private IDatabase database;

    public AffectationsQuery(IDatabase database) {
        this.database = database;
    }

    @Override
    public void RegisterAffectation(Collection<AttributeWrapper> attributes) throws SQLException {
        String query = "INSERT INTO " + ETables.Affectations + InsertWrapperToQuery(attributes);
        database.InsertQuery(query);

    }

    @Override
    public void UpdateAffectation(int affectationId, Collection<AttributeWrapper> attributes) throws SQLException {
        String whereClause = " WHERE " + EAffectationAttributes.AffectationId + "=" + affectationId;
        String query = "UPDATE " + ETables.Affectations + UpdateWrapperToQuery(attributes) + whereClause;
        database.UpdateQuery(query);

    }

    @Override
    public void DeleteAffectation(int affectationId) throws SQLException {
        String whereClause = " WHERE " + EAffectationAttributes.AffectationId + "=" + affectationId;
        String query = "DELETE FROM " + ETables.Affectations + whereClause;

        database.DeleteQuery(query);

    }

    @Override
    public void CreateAffectationsTable() throws SQLException {
        String query = "CREATE TABLE IF NOT EXISTS " + ETables.Affectations + " (\n"
                + EAffectationAttributes.AffectationId + " INTEGER PRIMARY  KEY ,\n"
                + EAffectationAttributes.AffectationName + " TEXT)\n";

        database.CreateQuery(query);
    }

    @Override
    public void CreatePermissionsTable() throws SQLException {
        String query = "CREATE TABLE IF NOT EXISTS " + ETables.AffecationsPermissions + " (\n"
                + EUsersAttributes.UserId + " INTEGER  ,\n"
                + EAffectationAttributes.AffectationId + " INTEGER ,\n"
                + " PRIMARY KEY (" + EUsersAttributes.UserId + ","
                + EAffectationAttributes.AffectationId + ")\n"
                + ")";

        database.CreateQuery(query);
    }

    @Override
    public void CreateIndexes() throws SQLException {

    }

    @Override
    public ResultSet SearchAffectations(SearchWrapper parametrers) throws SQLException {
        String whereClause = " WHERE " + SearchWrapperToWhereClause(parametrers);
        String query = "SELECT * FROM " + ETables.Affectations + whereClause;

        ResultSet result = database.SelectQuery(query);
        return result;
    }

    @Override
    public ResultSet LoadAffectations(LoadWrapper parametrers) throws SQLException {
        String extraClause = " LIMIT " + parametrers.getLimit() + " OFFSET " + parametrers.getOffset();
        String query = "SELECT * FROM " + ETables.Affectations + extraClause;
        ResultSet result = database.SelectQuery(query);
        return result;
    }

}
