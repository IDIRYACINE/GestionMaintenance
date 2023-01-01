package idir.embag.Infrastructure.Database;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.Iterator;

import idir.embag.DataModels.Users.DesignationPermission;
import idir.embag.Types.Infrastructure.Database.IDatabase;
import idir.embag.Types.Infrastructure.Database.IGroupPermissionsQuery;
import idir.embag.Types.Infrastructure.Database.Generics.AttributeWrapper;
import idir.embag.Types.Infrastructure.Database.Generics.IQuery;
import idir.embag.Types.Infrastructure.Database.Metadata.EDesignationAttributes;
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
        String query = "SELECT * FROM " + ETables.GroupsPermissions + " WHERE "
                + EGroupsPermissionsAttributes.GroupId + " = " + groupId;

        return database.SelectQuery(query);        
    }

    @Override
    public ResultSet LoadGroupUngrantedPermissions(Collection<Integer> grantedPermissions) throws SQLException {
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
    public void GrantGroupPermission(Collection<AttributeWrapper> attributes) throws SQLException {
        String query = "INSERT INTO " + ETables.GroupsPermissions + InsertWrapperToQuery(attributes);
        database.InsertQuery(query);

    }

    @Override
    public void RevokeGroupPermission(Collection<DesignationPermission> attributes) throws SQLException {
        Iterator<DesignationPermission> iterator = attributes.iterator();
        DesignationPermission permission = iterator.next();

        String whereClause = " WHERE " + EGroupsPermissionsAttributes.GroupId + "=" + permission.getId()
                + " AND (" + EGroupsPermissionsAttributes.PermissionId + "=" + permission.getDesignationId();

        while (iterator.hasNext()) {
            permission = iterator.next();
            whereClause += " OR " + EDesignationAttributes.DesignationId + "=" + permission.getDesignationId();
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
