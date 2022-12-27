package idir.embag.Types.Infrastructure.Database;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;

import idir.embag.Types.Infrastructure.Database.Generics.AttributeWrapper;
import idir.embag.Types.Infrastructure.Database.Generics.IQuery;
import idir.embag.Types.Infrastructure.Database.Generics.LoadWrapper;
import idir.embag.Types.Infrastructure.Database.Generics.SearchWrapper;

public abstract class IDesignationsQuery extends IQuery{

    public abstract void RegisterDesignation (Collection<AttributeWrapper> attributes) throws SQLException;
    public abstract void UpdateDesignation (int designationId, Collection<AttributeWrapper> attributes) throws SQLException;
    public abstract void DeleteDesignation (int designationId) throws SQLException;
    
    public abstract ResultSet SearchDesignations (SearchWrapper parametrers ) throws SQLException;
    public abstract ResultSet LoadDesignations (LoadWrapper parametrers ) throws SQLException;

    public abstract void CreateDesignationsTable() throws SQLException;
    public abstract void CreatePermissionsTable() throws SQLException;

}
