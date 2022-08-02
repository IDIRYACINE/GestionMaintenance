package idir.embag.Infrastructure.Database;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;

import idir.embag.Types.Infrastructure.Database.IDatabase;
import idir.embag.Types.Infrastructure.Database.IProductQuery;
import idir.embag.Types.Infrastructure.Database.Generics.AttributeWrapper;
import idir.embag.Types.Infrastructure.Database.Generics.LoadWrapper;
import idir.embag.Types.Infrastructure.Database.Generics.MDatabase;
import idir.embag.Types.Infrastructure.Database.Generics.SearchWrapper;
import idir.embag.Types.Infrastructure.Database.Generics.MDatabase.InventoryAttributes;
import idir.embag.Types.Infrastructure.Database.Generics.MDatabase.StockAttributes;

public class ProductQuery extends IProductQuery{
    
    private IDatabase database;
    
    private static final String STOCK_TABLE_NAME = MDatabase.Tables.Stock;
    private static final String INVENTORY_TABLE_NAME = MDatabase.Tables.Inventory;
    private static final String FAMILIES_TABLE_NAME = MDatabase.Tables.FamilyCodes;

    public ProductQuery(IDatabase database) {
        this.database = database;
    }

    @Override
    public void RegisterStockProduct(Collection<AttributeWrapper> attributes) throws SQLException {
        String query = "INSERT INTO "+STOCK_TABLE_NAME+ InsertWrapperToQuery(attributes);
        database.InsertQuery(query);
    }

    @Override
    public void UnregisterStockProduct(int articleId) throws SQLException {
        String whereClause = " WHERE "+MDatabase.StockAttributes.ArticleId + "=" + articleId;
        String query = "DELETE FROM "+STOCK_TABLE_NAME + whereClause;
        
        database.DeleteQuery(query);
    }

    @Override
    public void UpdateStockProduct(int articleId, Collection<AttributeWrapper> attributes) throws SQLException {
        String whereClause = " WHERE "+MDatabase.StockAttributes.ArticleId + "=" + articleId;
        String query = "UPDATE "+STOCK_TABLE_NAME+ UpdateWrapperToQuery(attributes)+ whereClause;

        database.UpdateQuery(query);
        
    }

    @Override
    public void RegisterInventoryProduct(Collection<AttributeWrapper> attributes) throws SQLException {
        String query = "INSERT INTO "+INVENTORY_TABLE_NAME+ InsertWrapperToQuery(attributes);
        database.InsertQuery(query);
        
    }

    @Override
    public void UnregisterInventoryProduct(int articleId,int stockId) throws SQLException {
        String whereClause = " WHERE "+MDatabase.InventoryAttributes.ArticleId + "=" + articleId;
        String query = "DELETE FROM "+INVENTORY_TABLE_NAME + whereClause;
        database.DeleteQuery(query);

        whereClause = " WHERE "+MDatabase.StockAttributes.ArticleId + "=" + stockId;
        query = "UPDATE "+STOCK_TABLE_NAME+ MDatabase.StockAttributes.Quantity +" -=1" + whereClause;

        database.UpdateQuery(query);

    }

    @Override
    public void UpdateInventoryProduct(int articleId, Collection<AttributeWrapper> attributes)
            throws SQLException {

        String whereClause = " WHERE "+MDatabase.InventoryAttributes.ArticleId + "=" + articleId;
        String query = "UPDATE "+INVENTORY_TABLE_NAME+ UpdateWrapperToQuery(attributes) + whereClause;
        
        database.UpdateQuery(query);
        
    }

    @Override
    public void RegisterFamilyCode(Collection<AttributeWrapper> attributes) throws SQLException {
        String query = "INSERT INTO "+FAMILIES_TABLE_NAME+ InsertWrapperToQuery(attributes);
        database.InsertQuery(query);
        
    }

    @Override
    public void UpdateFamilyCode(int familyId, Collection<AttributeWrapper> attributes)
            throws SQLException {
        String whereClause = " WHERE "+MDatabase.FamilliesCodeAttributes.FamilyCode + "=" + familyId;
        String query = "UPDATE "+FAMILIES_TABLE_NAME+ UpdateWrapperToQuery(attributes) +whereClause;
        database.UpdateQuery(query);
        
    }

    @Override
    public void UnregisterFamilyCode(int familyId) throws SQLException {
        String whereClause = " WHERE "+MDatabase.FamilliesCodeAttributes.FamilyCode + "=" + familyId;
        String query = "DELETE FROM "+FAMILIES_TABLE_NAME + whereClause;
        
        database.DeleteQuery(query);
    }

    @Override
    public void CreateFamiLyCodesTable() throws SQLException {
        String query = "CREATE TABLE IF NOT EXISTS "+FAMILIES_TABLE_NAME+"("+
                MDatabase.FamilliesCodeAttributes.FamilyCode+" INTEGER PRIMARY KEY,"+
                MDatabase.FamilliesCodeAttributes.FamilyName+" TEXT)";

        database.CreateQuery(query);        
    }

    @Override
    public void CreateInventoryTable() throws SQLException {
        String query = "CREATE TABLE IF NOT EXISTS "+INVENTORY_TABLE_NAME+"("
                + MDatabase.InventoryAttributes.ArticleId +" INTEGER PRIMARY KEY,\n"
                + MDatabase.InventoryAttributes.StockId +" INTEGER,\n"
                + MDatabase.InventoryAttributes.ArticleCode +" INTEGER,\n"

                + "FOREIGN KEY ("+ MDatabase.InventoryAttributes.StockId +")\n"
                + "REFERENCES "+ STOCK_TABLE_NAME +"(" +MDatabase.StockAttributes.ArticleId +")\n"  
                + "ON DELETE CASCADE ON UPDATE NO ACTION)";
        database.CreateQuery(query);
    }

    @Override
    public void CreateStockTable() throws SQLException {

        String query = "CREATE TABLE IF NOT EXISTS "+ STOCK_TABLE_NAME +" (\n"
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
    public ResultSet SearchStockProduct(SearchWrapper parametrers) throws SQLException {
        String whereClause = " WHERE "+ SearchWrapperToWhereClause(parametrers);
        String query = "SELECT * FROM "+STOCK_TABLE_NAME+ whereClause;
        ResultSet result = database.SelectQuery(query);
        return result;
    }

    @Override
    public ResultSet SearchInventoryProduct(SearchWrapper parametrers) throws SQLException {
        String whereClause = " WHERE "+ SearchWrapperToWhereClause(parametrers);

        String joinClause = " INNER JOIN " +STOCK_TABLE_NAME +" ON "
         +INVENTORY_TABLE_NAME + "."+ InventoryAttributes.StockId
         +"=" + STOCK_TABLE_NAME + "." + StockAttributes.ArticleId ;

         String query = "SELECT "
         + INVENTORY_TABLE_NAME + "." + InventoryAttributes.ArticleId +" ,"
         + INVENTORY_TABLE_NAME + "." + InventoryAttributes.ArticleCode +" ,"
         + INVENTORY_TABLE_NAME + "." + InventoryAttributes.StockId +" ,"
         + STOCK_TABLE_NAME + "." + StockAttributes.Price +" ,"
         + STOCK_TABLE_NAME + "." + StockAttributes.FamilyCode +" ,"
         + STOCK_TABLE_NAME + "." + StockAttributes.ArticleName

         +" FROM "+INVENTORY_TABLE_NAME +joinClause+ whereClause;
         
        ResultSet result = database.SelectQuery(query);
        return result;
    }

    @Override
    public ResultSet SearchFamilyCode(SearchWrapper parametrers) throws SQLException {
        String whereClause = " WHERE "+ SearchWrapperToWhereClause(parametrers);
        String query = "SELECT * FROM "+FAMILIES_TABLE_NAME+ whereClause;
        ResultSet result = database.SelectQuery(query);
        return result;
    }

    @Override
    public ResultSet LoadFamilyCode(LoadWrapper parametrers) throws SQLException {
        String extraClause = " LIMIT "+ parametrers.getLimit() + " OFFSET " + parametrers.getOffset();
        String query = "SELECT * FROM "+FAMILIES_TABLE_NAME+ extraClause;
        ResultSet result = database.SelectQuery(query);
        return result;
    }

    @Override
    public ResultSet LoadInventoryProduct(LoadWrapper parametrers) throws SQLException {
         String extraClause = " LIMIT "+ parametrers.getLimit() + " OFFSET " + parametrers.getOffset();
         String joinClause = " INNER JOIN " +STOCK_TABLE_NAME +" ON "
         +INVENTORY_TABLE_NAME + "."+ InventoryAttributes.StockId
         +"=" + STOCK_TABLE_NAME + "." + StockAttributes.ArticleId ;

         String query = "SELECT "
         + INVENTORY_TABLE_NAME + "." + InventoryAttributes.ArticleId +" ,"
         + INVENTORY_TABLE_NAME + "." + InventoryAttributes.ArticleCode +" ,"
         + INVENTORY_TABLE_NAME + "." + InventoryAttributes.StockId +" ,"
         + STOCK_TABLE_NAME + "." + StockAttributes.Price +" ,"
         + STOCK_TABLE_NAME + "." + StockAttributes.FamilyCode +" ,"
         + STOCK_TABLE_NAME + "." + StockAttributes.ArticleName

         +" FROM "+INVENTORY_TABLE_NAME +joinClause+ extraClause;
         
         ResultSet result = database.SelectQuery(query);
         return result;
    }

    @Override
    public ResultSet LoadStockProduct(LoadWrapper parametrers) throws SQLException {
         String extraClause = " LIMIT "+ parametrers.getLimit() + " OFFSET " + parametrers.getOffset();
         String query = "SELECT * FROM "+STOCK_TABLE_NAME+ extraClause;
         ResultSet result = database.SelectQuery(query);
         return result;
    }

    @Override
    public void CreateIndexes() throws SQLException {
        String query = "CREATE INDEX " + MDatabase.InventoryIdexes.CodeBarIndex +" ON "
        + INVENTORY_TABLE_NAME 
        +"(" + MDatabase.InventoryAttributes.ArticleCode 
        +")";

        database.CreateQuery(query);
    }




}
