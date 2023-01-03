package idir.embag.Types.Infrastructure.Database;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;

import idir.embag.DataModels.Users.DesignationPermission;

public interface IGroupPermissionsQuery {

    public abstract ResultSet LoadGroupPermissions(int groupId) throws SQLException;
    public abstract ResultSet LoadGroupUngrantedPermissions(Collection<Integer> grantedPermissions) throws SQLException;

    public abstract void GrantGroupPermission(Collection<DesignationPermission>  attributes) throws SQLException;
    public abstract void RevokeGroupPermission(Collection<DesignationPermission> attributes) throws SQLException;

    public abstract void CreateGroupPermissionsTable() throws SQLException;
}
