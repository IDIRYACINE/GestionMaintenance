package idir.embag.Infrastructure.Database;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.Iterator;

import idir.embag.DataModels.Users.AffectationPermission;
import idir.embag.Types.Infrastructure.Database.IDatabase;
import idir.embag.Types.Infrastructure.Database.IGroupPermissionsQuery;
import idir.embag.Types.Infrastructure.Database.Generics.IQuery;
import idir.embag.Types.Infrastructure.Database.Metadata.EAffectationAttributes;
import idir.embag.Types.Infrastructure.Database.Metadata.EGroupsPermissionsAttributes;
import idir.embag.Types.Infrastructure.Database.Metadata.ETables;

public class GroupPermissionsQuery extends IQuery implements IGroupPermissionsQuery {

    private IDatabase database;

    public GroupPermissionsQuery(IDatabase database) {
        this.database = database;
    }

    @Override
    public void CreateIndexes() throws SQLException {

    }

    @Override
    public ResultSet LoadGroupPermissions(int groupId) throws SQLException {
        String query = "SELECT * FROM " + ETables.Affectations + " WHERE "
                + EAffectationAttributes.AffectationId + " IN (" 
                + "SELECT " + EGroupsPermissionsAttributes.PermissionId + " FROM " + ETables.GroupsPermissions 
                + " WHERE " + EGroupsPermissionsAttributes.GroupId + "=" + groupId + ")" ;

        return database.SelectQuery(query);        
    }

    @Override
    public ResultSet LoadGroupUngrantedPermissions(Collection<Integer> grantedPermissions) throws SQLException {
        String whereClause = " WHERE " + EAffectationAttributes.AffectationId + " NOT IN (";

        Iterator<Integer> iterator = grantedPermissions.iterator();
        while (iterator.hasNext()) {
            whereClause += iterator.next();
            if (iterator.hasNext())
                whereClause += ",";
        }

        whereClause += ")";

        String query = "SELECT * FROM " + ETables.Affectations;

        if (grantedPermissions.size() > 0)
            query += whereClause;

        ResultSet result = database.SelectQuery(query);

        return result;
    }

    @Override
    public void GrantGroupPermission(Collection<AffectationPermission> attributes) throws SQLException {
        Iterator<AffectationPermission> iterator = attributes.iterator();
        AffectationPermission permission = iterator.next();

        String query = "INSERT INTO " + ETables.GroupsPermissions  + " (" + EGroupsPermissionsAttributes.GroupId + ","
                + EGroupsPermissionsAttributes.PermissionId + ") VALUES (" + permission.getId() + ","
                + permission.getAffectationId();

        while (iterator.hasNext()) {
            permission = iterator.next();
            query += ",(" + permission.getId() + "," + permission.getAffectationId() + ")";
        }
        
        query += ")";     
        
        database.InsertQuery(query);

    }

    @Override
    public void RevokeGroupPermission(Collection<AffectationPermission> attributes) throws SQLException {
        Iterator<AffectationPermission> iterator = attributes.iterator();
        AffectationPermission permission = iterator.next();

        String whereClause = " WHERE " + EGroupsPermissionsAttributes.GroupId + "=" + permission.getId()
                + " AND (" + EGroupsPermissionsAttributes.PermissionId + "=" + permission.getAffectationId();

        while (iterator.hasNext()) {
            permission = iterator.next();
            whereClause += " OR " + EAffectationAttributes.AffectationId + "=" + permission.getAffectationId();
        }

        whereClause += ")";

        String query = "DELETE FROM " + ETables.GroupsPermissions + whereClause;

        database.DeleteQuery(query);

    }

    @Override
    public void CreateGroupPermissionsTable() throws SQLException {
        String query = "CREATE TABLE IF NOT EXISTS " + ETables.GroupsPermissions + " ("
                + EGroupsPermissionsAttributes.GroupId + " INTEGER NOT NULL,"
                + EGroupsPermissionsAttributes.PermissionId + " INTEGER NOT NULL,"
                + "PRIMARY KEY (" + EGroupsPermissionsAttributes.GroupId + ","
                + EGroupsPermissionsAttributes.PermissionId + ")"
                + ")";
        database.CreateQuery(query);
    }

}
