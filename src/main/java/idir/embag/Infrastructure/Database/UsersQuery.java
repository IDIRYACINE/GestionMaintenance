package idir.embag.Infrastructure.Database;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import idir.embag.DataModels.Users.DesignationPermission;
import idir.embag.Types.Infrastructure.Database.IDatabase;
import idir.embag.Types.Infrastructure.Database.IUsersQuery;
import idir.embag.Types.Infrastructure.Database.Generics.AttributeWrapper;
import idir.embag.Types.Infrastructure.Database.Generics.LoadWrapper;
import idir.embag.Types.Infrastructure.Database.Generics.SearchWrapper;
import idir.embag.Types.Infrastructure.Database.Metadata.EDesignationAttributes;
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

        String whereClause = " WHERE " + EUsersAttributes.UserId + "=" + permission.getUserId() 
        + " AND (" + EDesignationAttributes.DesignationId + "=" + permission.getDesignationId() ;
        
        while(iterator.hasNext()){
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
        String query = "CREATE TABLE IF NOT EXISTS " + ETables.SessionWorkers + " (\n"
                + EUsersAttributes.UserId + " INTEGER PRIMARY AUTO_INCREMENT KEY ,\n"
                + EUsersAttributes.UserName + " TEXT,\n"
                + EUsersAttributes.Password + " TEXT,\n"
                + EUsersAttributes.Admin + " Boolean Default False \n";

        database.CreateQuery(query);

        RegisterDefaultAdminUser();

    }


    private void RegisterDefaultAdminUser(){
        Collection<AttributeWrapper> attributes = new ArrayList<AttributeWrapper>();
        attributes.add(new AttributeWrapper(EUsersAttributes.UserName, "admin"));
        attributes.add(new AttributeWrapper(EUsersAttributes.Password, "admin"));
        attributes.add(new AttributeWrapper(EUsersAttributes.Admin, true));

        RegisterUser(attributes);

    }

    @Override
    public ResultSet SearchUsers(SearchWrapper parameters) throws SQLException {
        String whereClause = " WHERE " + SearchWrapperToWhereClause(parameters);
        String query = "SELECT * FROM " + ETables.Users + whereClause;

        ResultSet result = database.SelectQuery(query);
        return result;
    }

}
