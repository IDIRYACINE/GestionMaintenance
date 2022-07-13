package idir.embag.Infrastructure.Database;

import java.sql.SQLException;
import java.util.List;

import idir.embag.Infrastructure.Database.Generics.AttributeWrapper;
import idir.embag.Infrastructure.Database.Generics.IQuery;
import idir.embag.Infrastructure.Database.Generics.MDatabase;
import idir.embag.Infrastructure.Database.Generics.SearchWrapper;

public abstract class IProductQuery extends IQuery{
    
    public abstract void RegisterStockProduct(AttributeWrapper<MDatabase.StockAttributes>[] attributes) throws SQLException;
    public abstract void UnregisterStockProduct(int articleId) throws SQLException;
    public abstract void UpdateStockProduct(int articleId , AttributeWrapper<MDatabase.StockAttributes>[] attributes) throws SQLException;
    public abstract List<Object> SearchStockProduct(SearchWrapper parametrers)  throws SQLException;


    public abstract void RegisterInventoryProduct(AttributeWrapper<MDatabase.InventoryAttributes>[] attributes) throws SQLException;
    public abstract void UnregisterInventoryProduct(int articleId) throws SQLException;
    public abstract void UpdateInventoryProduct(int articleId,AttributeWrapper<MDatabase.InventoryAttributes>[] attributes) throws SQLException;
    public abstract List<Object> SearchInventoryProduct(SearchWrapper parametrers) throws SQLException;

    public abstract void RegisterFamilyCode(AttributeWrapper<MDatabase.FamilliesCodeAttributes>[] attributes) throws SQLException;
    public abstract void UpdateFamilyCode(int familyId,AttributeWrapper<MDatabase.FamilliesCodeAttributes>[] attributes ) throws SQLException;
    public abstract void UnregisterFamilyCode(int familyId) throws SQLException;
    public abstract List<Object> SearchFamilyCode(SearchWrapper parametrers) throws SQLException;


    public abstract void CreateFamiLCodeTable() throws SQLException;
    public abstract void CreateInventoryTable() throws SQLException;
    public abstract void CreateStockTable() throws SQLException;


    
    
}
