package idir.embag.Utility.Database.Implementations;

import java.sql.SQLException;

import idir.embag.Utility.Database.AttributeWrapper;
import idir.embag.Utility.Database.IDatabase;
import idir.embag.Utility.Database.IProductQuery;
import idir.embag.Utility.Database.Generics.MDatabase;
import idir.embag.Utility.Database.Generics.MDatabase.FamilliesCodeAttributes;
import idir.embag.Utility.Database.Generics.MDatabase.InventoryAttributes;
import idir.embag.Utility.Database.Generics.MDatabase.StockAttributes;

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
    public void UnregisterStockProduct() throws SQLException {
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
    public void UnregisterInventoryProduct() throws SQLException {
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
    public void UnregisterFamilyCode() throws SQLException {
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


}
