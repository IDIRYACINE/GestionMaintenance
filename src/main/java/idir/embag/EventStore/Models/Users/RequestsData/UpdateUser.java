package idir.embag.EventStore.Models.Users.RequestsData;

import java.util.Collection;

import idir.embag.DataModels.Users.AffectationPermission;
import idir.embag.Types.Infrastructure.Database.Generics.AttributeWrapper;

public class UpdateUser {
    private Collection<AttributeWrapper> fields;
    private Collection<AffectationPermission> grantedPermissions;
    private Collection<AffectationPermission> unGrantedPermissions;

    public UpdateUser(Collection<AttributeWrapper> fields, Collection<AffectationPermission> grantedPermissions,
            Collection<AffectationPermission> unGrantedPermissions) {
        this.fields = fields;
        this.grantedPermissions = grantedPermissions;
        this.unGrantedPermissions = unGrantedPermissions;
    }

    public Collection<AttributeWrapper> getFields() {
        return fields;
    }

    public Collection<AffectationPermission> getGrantedPermissions() {
        return grantedPermissions;
    }

    public Collection<AffectationPermission> getUnGrantedPermissions() {
        return unGrantedPermissions;
    }

    public Object getUserId() {
        return null;
    }
    
}
