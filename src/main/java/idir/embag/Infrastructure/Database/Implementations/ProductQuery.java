package idir.embag.Infrastructure.Database.Implementations;

import java.sql.SQLException;
import java.util.List;

import idir.embag.Infrastructure.Database.AttributeWrapper;
import idir.embag.Infrastructure.Database.IDatabase;
import idir.embag.Infrastructure.Database.IProductQuery;
import idir.embag.Infrastructure.Database.Generics.MDatabase;
import idir.embag.Infrastructure.Database.Generics.MDatabase.FamilliesCodeAttributes;
import idir.embag.Infrastructure.Database.Generics.MDatabase.InventoryAttributes;
import idir.embag.Infrastructure.Database.Generics.MDatabase.StockAttributes;

public class ProductQuery extends IProductQuery{
    
    private IDatabase database;
    
    private static final MDatabase.Tables STOCK_TABLE_NAME = MDatabase.Tables.Stock;
    private static final MDatabase.Tables INVENTORY_TABLE_NAME = MDatabase.Tables.Inventory;
    private static final MDatabase.Tables FAMILIES_TABLE_NAME = MDatabase.Tables.FamilliesCode;

    public ProductQuery(IDatabase database) {
        this.database = database;
    }

    @Override
    public void RegisterStockProduct(AttributeWrapper<StockAttributes>[] attributes) throws SQLException {
        String query = "INSERT INTO "+STOCK_TABLE_NAME+ InsertWrapperToQuery(attributes);
        database.InsertQuery(query);
    }

    @Override
    public void UnregisterStockProduct(int articleId) throws SQLException {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void UpdateStockProduct(int articleId, AttributeWrapper<StockAttributes>[] attributes) throws SQLException {
        String whereClause = " WHERE "+MDatabase.StockAttributes.ArticleId + "=" + articleId;
        String query = "UPDATE "+STOCK_TABLE_NAME+ UpdateWrapperToQuery(attributes)+ whereClause;
        database.UpdateQuery(query);
        
    }

    @Override
    public void RegisterInventoryProduct(AttributeWrapper<InventoryAttributes>[] attributes) throws SQLException {
        String query = "INSERT INTO "+INVENTORY_TABLE_NAME+ InsertWrapperToQuery(attributes);
        database.InsertQuery(query);
        
    }

    @Override
    public void UnregisterInventoryProduct(int articleId) throws SQLException {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void UpdateInventoryProduct(int articleId, AttributeWrapper<InventoryAttributes>[] attributes)
            throws SQLException {

        String whereClause = " WHERE "+MDatabase.InventoryAttributes.ArticleId + "=" + articleId;
        String query = "UPDATE "+STOCK_TABLE_NAME+ UpdateWrapperToQuery(attributes) + whereClause;
        database.UpdateQuery(query);
        
    }

    @Override
    public void RegisterFamilyCode(AttributeWrapper<FamilliesCodeAttributes>[] attributes) throws SQLException {
        String query = "INSERT INTO "+FAMILIES_TABLE_NAME+ InsertWrapperToQuery(attributes);
        database.InsertQuery(query);
        
    }

    @Override
    public void UpdateFamilyCode(int familyId, AttributeWrapper<FamilliesCodeAttributes>[] attributes)
            throws SQLException {
        String whereClause = " WHERE "+MDatabase.FamilliesCodeAttributes.FamilyCode + "=" + familyId;
        String query = "UPDATE "+STOCK_TABLE_NAME+ UpdateWrapperToQuery(attributes) +whereClause;
        database.UpdateQuery(query);
        
    }

    @Override
    public void UnregisterFamilyCode(int familyId) throws SQLException {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void CreateFamiLCodeTable() throws SQLException {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void CreateInventoryTable() throws SQLException {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void CreateStockTable() throws SQLException {
        // TODO Auto-generated method stub
        
    }

    @Override
    public List<Object> SearchInventoryProduct(AttributeWrapper<InventoryAttributes>[] data) throws SQLException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<Object> SearchStockProduct(AttributeWrapper<StockAttributes>[] data) throws SQLException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<Object> SearchFamilyCode(AttributeWrapper<FamilliesCodeAttributes>[] data) {
        // TODO Auto-generated method stub
        return null;
    }


}
