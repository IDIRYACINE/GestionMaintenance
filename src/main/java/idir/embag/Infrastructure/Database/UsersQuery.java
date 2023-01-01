package idir.embag.Infrastructure.Database;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.Iterator;
import idir.embag.DataModels.Users.DesignationPermission;
import idir.embag.Types.Infrastructure.Database.IDatabase;
import idir.embag.Types.Infrastructure.Database.IUsersQuery;
import idir.embag.Types.Infrastructure.Database.Generics.AttributeWrapper;
import idir.embag.Types.Infrastructure.Database.Generics.LoadWrapper;
import idir.embag.Types.Infrastructure.Database.Generics.SearchWrapper;
import idir.embag.Types.Infrastructure.Database.Metadata.EDesignationAttributes;
import idir.embag.Types.Infrastructure.Database.Metadata.EDesignationsPermissions;
import idir.embag.Types.Infrastructure.Database.Metadata.ETables;
import idir.embag.Types.Infrastructure.Database.Metadata.EUsersAttributes;

public class UsersQuery extends IUsersQuery {
    private IDatabase database;

    public UsersQuery(IDatabase database) {
        this.database = database;
    }

    @Override
    public void RegisterUser(Collection<AttributeWrapper> attributes) {
        String query = "INSERT INTO " + ETables.Users + InsertWrapperToQuery(attributes);
        database.InsertQuery(query);
    }

    @Override
    public void UpdateUser(int userId, Collection<AttributeWrapper> attributes) {
        String whereClause = " WHERE " + EUsersAttributes.UserId + "=" + userId;
        String query = "UPDATE " + ETables.Users + UpdateWrapperToQuery(attributes) + whereClause;
        database.UpdateQuery(query);
    }

    @Override
    public void CreateIndexes() throws SQLException {

    }

    @Override
    public ResultSet LoadUsers(LoadWrapper parametrers) throws SQLException {
        String query = "SELECT * FROM " + ETables.Users;
        ResultSet result = database.SelectQuery(query);
        return result;
    }

    @Override
    public void UnregisterUser(int userId) throws SQLException {
        String whereClause = " WHERE " + EUsersAttributes.UserId + "=" + userId;
        String query = "DELETE FROM " + ETables.Users + whereClause;

        database.DeleteQuery(query);

        whereClause = " WHERE " + EUsersAttributes.UserId + "=" + userId;
        query = "DELETE FROM " + ETables.DesignationsPermissions + whereClause;

        database.DeleteQuery(query);


    }

    @Override
    public void GrantDesignationSupervisior(Collection<AttributeWrapper> attributes) throws SQLException {
        String query = "INSERT INTO " + ETables.Designations + InsertWrapperToQuery(attributes);
        database.InsertQuery(query);

    }

    @Override
    public void RevokeDesignationSupervisior(Collection<DesignationPermission> attributes) throws SQLException {
        Iterator<DesignationPermission> iterator = attributes.iterator();
        DesignationPermission permission = iterator.next();

        String whereClause = " WHERE " + EUsersAttributes.UserId + "=" + permission.getId()
                + " AND (" + EDesignationAttributes.DesignationId + "=" + permission.getDesignationId();

        while (iterator.hasNext()) {
            permission = iterator.next();
            whereClause += " OR " + EDesignationAttributes.DesignationId + "=" + permission.getDesignationId();
        }

        whereClause += ")";

        String query = "DELETE FROM " + ETables.DesignationsPermissions + whereClause;

        database.DeleteQuery(query);

    }

    @Override
    public ResultSet Login(String userName, String password) throws SQLException {
        String whereClause = " WHERE " + EUsersAttributes.UserName + "="
                + userName + " AND " + EUsersAttributes.Password + "=" + password;

        String query = "SELECT * FROM " + ETables.Users + whereClause;
        ResultSet result = database.SelectQuery(query);

        return result;
    }

    @Override
    public void CreateUsersTable() throws SQLException {
        String query = "CREATE TABLE IF NOT EXISTS " + ETables.Users + " (\n"
                + EUsersAttributes.UserId + " INTEGER PRIMARY KEY AUTO_INCREMENT ,\n"
                + EUsersAttributes.UserName + " TEXT,\n"
                + EUsersAttributes.Password + " TEXT,\n"
                + EUsersAttributes.Admin + " Boolean Default False )\n";

        database.CreateQuery(query);

    }

    // private void RegisterDefaultAdminUser(){
    // Collection<AttributeWrapper> attributes = new ArrayList<AttributeWrapper>();
    // attributes.add(new AttributeWrapper(EUsersAttributes.UserName, "admin"));
    // attributes.add(new AttributeWrapper(EUsersAttributes.Password, "admin"));
    // attributes.add(new AttributeWrapper(EUsersAttributes.Admin, 1));

    // RegisterUser(attributes);

    // }

    @Override
    public ResultSet SearchUsers(SearchWrapper parameters) throws SQLException {
        String whereClause = " WHERE " + SearchWrapperToWhereClause(parameters);
        String query = "SELECT * FROM " + ETables.Users + whereClause;
        ResultSet result = database.SelectQuery(query);
        return result;
    }

    @Override
    public ResultSet LoadUserUngrantedPermissions(Collection<Integer> grantedPermissions) throws SQLException {
        String whereClause = " WHERE " + EDesignationAttributes.DesignationId + " NOT IN (";

        Iterator<Integer> iterator = grantedPermissions.iterator();
        while (iterator.hasNext()) {
            whereClause += iterator.next();
            if (iterator.hasNext())
                whereClause += ",";
        }

        whereClause += ")";

        String query = "SELECT * FROM " + ETables.Designations;

        if (grantedPermissions.size() > 0)
            query += whereClause;

        ResultSet result = database.SelectQuery(query);

        return result;
    }

    @Override
    public ResultSet LoadUserPermissions(int userId) throws SQLException {

        String whereClause = " WHERE EXISTS ("
                + "SELECT * FROM " + ETables.DesignationsPermissions + " WHERE"
                + EDesignationsPermissions.UserId + "=" + userId + ")";

        String query = "SELECT * FROM " + ETables.Designations + whereClause;

        ResultSet result = database.SelectQuery(query);

        return result;
    }

}
