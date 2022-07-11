package idir.embag.Utility.Database;

import java.sql.SQLException;

import idir.embag.Utility.Database.Generics.IQuery;
import idir.embag.Utility.Database.Generics.MDatabase;

public abstract class IProductQuery extends IQuery{
    
    public abstract void RegisterStockProduct(AttributeWrapper<MDatabase.StockAttributes>[] attributes) throws SQLException;
    public abstract void UnregisterStockProduct() throws SQLException;
    public abstract void UpdateStockProduct(int articleId , AttributeWrapper<MDatabase.StockAttributes>[] attributes) throws SQLException;

    public abstract void RegisterInventoryProduct(AttributeWrapper<MDatabase.InventoryAttributes>[] attributes) throws SQLException;
    public abstract void UnregisterInventoryProduct() throws SQLException;
    public abstract void UpdateInventoryProduct(int articleId,AttributeWrapper<MDatabase.InventoryAttributes>[] attributes) throws SQLException;

    public abstract void RegisterFamilyCode(AttributeWrapper<MDatabase.FamilliesCodeAttributes>[] attributes) throws SQLException;
    public abstract void UpdateFamilyCode(int familyId,AttributeWrapper<MDatabase.FamilliesCodeAttributes>[] attributes ) throws SQLException;
    public abstract void UnregisterFamilyCode() throws SQLException;


    public abstract void CreateFamiLCodeTable() throws SQLException;
    public abstract void CreateInventoryTable() throws SQLException;
    public abstract void CreateStockTable() throws SQLException;

    
    
}
