package idir.embag.EventStore.Models.Users.RequestsData;

import java.util.Collection;

import idir.embag.Types.Infrastructure.Database.Generics.AttributeWrapper;

public class UpdateUser {
    private Collection<AttributeWrapper> fields;
    private Collection<AttributeWrapper> grantedPermissions;
    private Collection<AttributeWrapper> unGrantedPermissions;

    public UpdateUser(Collection<AttributeWrapper> fields, Collection<AttributeWrapper> grantedPermissions,
            Collection<AttributeWrapper> unGrantedPermissions) {
        this.fields = fields;
        this.grantedPermissions = grantedPermissions;
        this.unGrantedPermissions = unGrantedPermissions;
    }

    public Collection<AttributeWrapper> getFields() {
        return fields;
    }

    public Collection<AttributeWrapper> getGrantedPermissions() {
        return grantedPermissions;
    }

    public Collection<AttributeWrapper> getUnGrantedPermissions() {
        return unGrantedPermissions;
    }
    
}
