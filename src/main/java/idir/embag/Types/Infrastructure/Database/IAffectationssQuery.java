package idir.embag.Types.Infrastructure.Database;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;

import idir.embag.Types.Infrastructure.Database.Generics.AttributeWrapper;
import idir.embag.Types.Infrastructure.Database.Generics.IQuery;
import idir.embag.Types.Infrastructure.Database.Generics.LoadWrapper;
import idir.embag.Types.Infrastructure.Database.Generics.SearchWrapper;

public abstract class IAffectationssQuery extends IQuery{

    public abstract void RegisterAffectation (Collection<AttributeWrapper> attributes) throws SQLException;
    public abstract void UpdateAffectation (int AffectationId, Collection<AttributeWrapper> attributes) throws SQLException;
    public abstract void DeleteAffectation (int AffectationId) throws SQLException;
    
    public abstract ResultSet SearchAffectations (SearchWrapper parametrers ) throws SQLException;
    public abstract ResultSet LoadAffectations (LoadWrapper parametrers ) throws SQLException;

    public abstract void CreateAffectationsTable() throws SQLException;
    public abstract void CreatePermissionsTable() throws SQLException;

}
