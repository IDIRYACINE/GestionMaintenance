package idir.embag.EventStore.Models.Users.RequestsData;

import java.util.Collection;

import idir.embag.DataModels.Users.DesignationPermission;
import idir.embag.Types.Infrastructure.Database.Generics.AttributeWrapper;

public class UpdateUser {
    private Collection<AttributeWrapper> fields;
    private Collection<DesignationPermission> grantedPermissions;
    private Collection<DesignationPermission> unGrantedPermissions;

    public UpdateUser(Collection<AttributeWrapper> fields, Collection<DesignationPermission> grantedPermissions,
            Collection<DesignationPermission> unGrantedPermissions) {
        this.fields = fields;
        this.grantedPermissions = grantedPermissions;
        this.unGrantedPermissions = unGrantedPermissions;
    }

    public Collection<AttributeWrapper> getFields() {
        return fields;
    }

    public Collection<DesignationPermission> getGrantedPermissions() {
        return grantedPermissions;
    }

    public Collection<DesignationPermission> getUnGrantedPermissions() {
        return unGrantedPermissions;
    }

    public Object getUserId() {
        return null;
    }
    
}
