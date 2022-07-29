package idir.embag.Types.Infrastructure.Database;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;

import idir.embag.Types.Infrastructure.Database.Generics.AttributeWrapper;
import idir.embag.Types.Infrastructure.Database.Generics.IQuery;
import idir.embag.Types.Infrastructure.Database.Generics.LoadWrapper;
import idir.embag.Types.Infrastructure.Database.Generics.SearchWrapper;

public abstract class IProductQuery extends IQuery{
    
    public abstract void RegisterStockProduct(Collection<AttributeWrapper> attributes) throws SQLException;
    public abstract void UnregisterStockProduct(int articleId) throws SQLException;
    public abstract void UpdateStockProduct(int articleId , Collection<AttributeWrapper> attributes) throws SQLException;

    public abstract void RegisterInventoryProduct(Collection<AttributeWrapper> attributes) throws SQLException;
    public abstract void UnregisterInventoryProduct(int articleId) throws SQLException;
    public abstract void UpdateInventoryProduct(int articleId,Collection<AttributeWrapper> attributes) throws SQLException;

    public abstract void RegisterFamilyCode(Collection<AttributeWrapper> attributes) throws SQLException;
    public abstract void UpdateFamilyCode(int familyId,Collection<AttributeWrapper> attributes ) throws SQLException;
    public abstract void UnregisterFamilyCode(int familyId) throws SQLException;

    public abstract ResultSet SearchFamilyCode(SearchWrapper parametrers) throws SQLException;
    public abstract ResultSet SearchInventoryProduct(SearchWrapper parametrers) throws SQLException;
    public abstract ResultSet SearchStockProduct(SearchWrapper parametrers)  throws SQLException;

    public abstract ResultSet LoadFamilyCode(LoadWrapper parametrers) throws SQLException;
    public abstract ResultSet LoadInventoryProduct(LoadWrapper parametrers) throws SQLException;
    public abstract ResultSet LoadStockProduct(LoadWrapper parametrers)  throws SQLException;


    public abstract void CreateFamiLCodeTable() throws SQLException;
    public abstract void CreateInventoryTable() throws SQLException;
    public abstract void CreateStockTable() throws SQLException;
    
}
