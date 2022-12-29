package idir.embag.Types.Infrastructure.Database;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;

import idir.embag.DataModels.Users.DesignationPermission;
import idir.embag.Types.Infrastructure.Database.Generics.AttributeWrapper;
import idir.embag.Types.Infrastructure.Database.Generics.IQuery;
import idir.embag.Types.Infrastructure.Database.Generics.LoadWrapper;
import idir.embag.Types.Infrastructure.Database.Generics.SearchWrapper;

public abstract class IUsersQuery extends IQuery{

    public abstract void RegisterUser(Collection<AttributeWrapper> attributes) throws SQLException;
    public abstract void UnregisterUser(int userId) throws SQLException;
    public abstract void UpdateUser(int userId,Collection<AttributeWrapper> attributes) throws SQLException;
   
    public abstract ResultSet LoadUsers(LoadWrapper parametrers) throws SQLException;
    public abstract ResultSet SearchUsers(SearchWrapper parameters) throws SQLException;

    public abstract void GrantDesignationSupervisior(Collection<AttributeWrapper> attributes) throws SQLException;
    public abstract void RevokeDesignationSupervisior(Collection<DesignationPermission> atributes) throws SQLException;

    public abstract ResultSet LoadUserPermissions(int userId) throws SQLException;
    public abstract ResultSet LoadUserUngrantedPermissions(Collection<Integer> grantedPermissions) throws SQLException;

    public abstract ResultSet Login(String userName, String password) throws SQLException;

    public abstract void CreateUsersTable() throws SQLException;

}
