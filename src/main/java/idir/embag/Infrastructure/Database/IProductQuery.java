package idir.embag.Infrastructure.Database;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

import idir.embag.DataModels.Metadata.EEventDataKeys;
import idir.embag.Infrastructure.Database.Generics.IQuery;
import idir.embag.Infrastructure.Database.Generics.SearchWrapper;

public abstract class IProductQuery extends IQuery{
    
    public abstract void RegisterStockProduct(Map<EEventDataKeys,Object> attributes) throws SQLException;
    public abstract void UnregisterStockProduct(int articleId) throws SQLException;
    public abstract void UpdateStockProduct(int articleId , Map<EEventDataKeys,Object> attributes) throws SQLException;
    public abstract ResultSet SearchStockProduct(SearchWrapper parametrers)  throws SQLException;


    public abstract void RegisterInventoryProduct(Map<EEventDataKeys,Object> attributes) throws SQLException;
    public abstract void UnregisterInventoryProduct(int articleId) throws SQLException;
    public abstract void UpdateInventoryProduct(int articleId,Map<EEventDataKeys,Object> attributes) throws SQLException;
    public abstract ResultSet SearchInventoryProduct(SearchWrapper parametrers) throws SQLException;

    public abstract void RegisterFamilyCode(Map<EEventDataKeys,Object> attributes) throws SQLException;
    public abstract void UpdateFamilyCode(int familyId,Map<EEventDataKeys,Object> attributes ) throws SQLException;
    public abstract void UnregisterFamilyCode(int familyId) throws SQLException;
    public abstract ResultSet SearchFamilyCode(SearchWrapper parametrers) throws SQLException;


    public abstract void CreateFamiLCodeTable() throws SQLException;
    public abstract void CreateInventoryTable() throws SQLException;
    public abstract void CreateStockTable() throws SQLException;


    
    
}
