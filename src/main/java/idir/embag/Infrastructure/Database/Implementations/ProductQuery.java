package idir.embag.Infrastructure.Database.Implementations;

import java.sql.SQLException;
import java.util.List;

import idir.embag.Infrastructure.Database.IDatabase;
import idir.embag.Infrastructure.Database.IProductQuery;
import idir.embag.Infrastructure.Database.Generics.AttributeWrapper;
import idir.embag.Infrastructure.Database.Generics.MDatabase;
import idir.embag.Infrastructure.Database.Generics.SearchWrapper;

public class ProductQuery extends IProductQuery{
    
    private IDatabase database;
    
    private static final String STOCK_TABLE_NAME = MDatabase.Tables.Stock;
    private static final String INVENTORY_TABLE_NAME = MDatabase.Tables.Inventory;
    private static final String FAMILIES_TABLE_NAME = MDatabase.Tables.FamilyCodes;

    public ProductQuery(IDatabase database) {
        this.database = database;
    }

    @Override
    public void RegisterStockProduct(AttributeWrapper[] attributes) throws SQLException {
        String query = "INSERT INTO "+STOCK_TABLE_NAME+ InsertWrapperToQuery(attributes);
        database.InsertQuery(query);
    }

    @Override
    public void UnregisterStockProduct(int articleId) throws SQLException {}

    @Override
    public void UpdateStockProduct(int articleId, AttributeWrapper[] attributes) throws SQLException {
        String whereClause = " WHERE "+MDatabase.StockAttributes.ArticleId + "=" + articleId;
        String query = "UPDATE "+STOCK_TABLE_NAME+ UpdateWrapperToQuery(attributes)+ whereClause;
        database.UpdateQuery(query);
        
    }

    @Override
    public void RegisterInventoryProduct(AttributeWrapper[] attributes) throws SQLException {
        String query = "INSERT INTO "+INVENTORY_TABLE_NAME+ InsertWrapperToQuery(attributes);
        database.InsertQuery(query);
        
    }

    @Override
    public void UnregisterInventoryProduct(int articleId) throws SQLException {}

    @Override
    public void UpdateInventoryProduct(int articleId, AttributeWrapper[] attributes)
            throws SQLException {

        String whereClause = " WHERE "+MDatabase.InventoryAttributes.ArticleId + "=" + articleId;
        String query = "UPDATE "+STOCK_TABLE_NAME+ UpdateWrapperToQuery(attributes) + whereClause;
        database.UpdateQuery(query);
        
    }

    @Override
    public void RegisterFamilyCode(AttributeWrapper[] attributes) throws SQLException {
        String query = "INSERT INTO "+FAMILIES_TABLE_NAME+ InsertWrapperToQuery(attributes);
        database.InsertQuery(query);
        
    }

    @Override
    public void UpdateFamilyCode(int familyId, AttributeWrapper[] attributes)
            throws SQLException {
        String whereClause = " WHERE "+MDatabase.FamilliesCodeAttributes.FamilyCode + "=" + familyId;
        String query = "UPDATE "+STOCK_TABLE_NAME+ UpdateWrapperToQuery(attributes) +whereClause;
        database.UpdateQuery(query);
        
    }

    @Override
    public void UnregisterFamilyCode(int familyId) throws SQLException {}

    @Override
    public void CreateFamiLCodeTable() throws SQLException {
        String query = "CREATE TABLE "+FAMILIES_TABLE_NAME+"("+
                MDatabase.FamilliesCodeAttributes.FamilyCode+" INTEGER PRIMARY KEY,"+
                MDatabase.FamilliesCodeAttributes.FamilyName+" TEXT)";

        database.CreateQuery(query);        
    }

    @Override
    public void CreateInventoryTable() throws SQLException {
        String query = "CREATE TABLE "+INVENTORY_TABLE_NAME+"("
                + MDatabase.InventoryAttributes.ArticleId +" INTEGER PRIMARY KEY,\n"
                + MDatabase.InventoryAttributes.ArticleName +" TEXT,\n"
                + MDatabase.InventoryAttributes.StockId +" INTEGER,\n"
                + MDatabase.InventoryAttributes.FamilyCode +" INTEGER,\n"
                + MDatabase.InventoryAttributes.Price +" REAL,\n"

                + "FOREIGN KEY ("+ MDatabase.InventoryAttributes.FamilyCode +")\n"
                + "REFERENCES "+ FAMILIES_TABLE_NAME +"(" +MDatabase.FamilliesCodeAttributes.FamilyCode +")\n"  
                + "ON DELETE CASCADE ON UPDATE NO ACTION,\n"

                + "FOREIGN KEY ("+ MDatabase.InventoryAttributes.StockId +")\n"
                + "REFERENCES "+ STOCK_TABLE_NAME +"(" +MDatabase.StockAttributes.ArticleId +")\n"  
                + "ON DELETE CASCADE ON UPDATE NO ACTION)";
                
        database.CreateQuery(query);
    }

    @Override
    public void CreateStockTable() throws SQLException {

        String query = "CREATE TABLE "+ STOCK_TABLE_NAME +" (\n"
           + MDatabase.StockAttributes.ArticleId + " INTEGER PRIMARY KEY,\n"
           + MDatabase.StockAttributes.ArticleName + " TEXT,\n"
           + MDatabase.StockAttributes.Price + " REAL,\n"
           + MDatabase.StockAttributes.Quantity + " INTEGER,\n"
           + MDatabase.StockAttributes.FamilyCode + " INTEGER,\n"
           + "FOREIGN KEY ("+ MDatabase.StockAttributes.FamilyCode +")\n"
           + "REFERENCES "+ FAMILIES_TABLE_NAME +"(" +MDatabase.FamilliesCodeAttributes.FamilyCode +")\n"  
           + "ON DELETE CASCADE ON UPDATE NO ACTION)";

        database.CreateQuery(query);
        
    }

    @Override
    public List<Object> SearchStockProduct(SearchWrapper parametrers) throws SQLException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<Object> SearchInventoryProduct(SearchWrapper parametrers) throws SQLException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<Object> SearchFamilyCode(SearchWrapper parametrers) throws SQLException {
        // TODO Auto-generated method stub
        return null;
    }



}
