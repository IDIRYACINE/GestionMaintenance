package idir.embag.Infrastructure.Database;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;

import idir.embag.Application.State.AppState;
import idir.embag.Types.Infrastructure.Database.IDatabase;
import idir.embag.Types.Infrastructure.Database.IProductQuery;
import idir.embag.Types.Infrastructure.Database.Generics.AttributeWrapper;
import idir.embag.Types.Infrastructure.Database.Generics.LoadWrapper;
import idir.embag.Types.Infrastructure.Database.Generics.SearchWrapper;
import idir.embag.Types.Infrastructure.Database.Metadata.EFamilyCodeAttributes;
import idir.embag.Types.Infrastructure.Database.Metadata.EIndex;
import idir.embag.Types.Infrastructure.Database.Metadata.EInventoryAttributes;
import idir.embag.Types.Infrastructure.Database.Metadata.EStockAttributes;
import idir.embag.Types.Infrastructure.Database.Metadata.ETables;

public class ProductQuery extends IProductQuery{
    
    private IDatabase database;

    public ProductQuery(IDatabase database) {
        this.database = database;
    }

    @Override
    public void RegisterStockProduct(Collection<AttributeWrapper> attributes) throws SQLException {
        String query = "INSERT INTO "+ETables.Stock+ InsertWrapperToQuery(attributes);
        database.InsertQuery(query);
    }

    @Override
    public void UnregisterStockProduct(int articleId) throws SQLException {
        String whereClause = " WHERE "+EStockAttributes.ArticleId + "=" + articleId;
        String query = "DELETE FROM "+ETables.Stock + whereClause;
        
        database.DeleteQuery(query);
    }

    @Override
    public void UpdateStockProduct(int articleId, Collection<AttributeWrapper> attributes) throws SQLException {
        String whereClause = " WHERE "+EStockAttributes.ArticleId + "=" + articleId;
        String query = "UPDATE "+ETables.Stock+ UpdateWrapperToQuery(attributes)+ whereClause;

        database.UpdateQuery(query);
        
    }

    @Override
    public void RegisterInventoryProduct(Collection<AttributeWrapper> attributes) throws SQLException {
        String query = "INSERT INTO "+ETables.Inventory+ InsertWrapperToQuery(attributes);
        database.InsertQuery(query);
        
    }

    @Override
    public void UnregisterInventoryProduct(int articleId,int stockId) throws SQLException {
        String whereClause = " WHERE "+EInventoryAttributes.ArticleId + "=" + articleId;
        String query = "DELETE FROM "+ETables.Inventory + whereClause;
        database.DeleteQuery(query);

        whereClause = " WHERE "+EStockAttributes.ArticleId + "=" + stockId;
        query = "UPDATE "+ETables.Stock+ EStockAttributes.Quantity +" -=1" + whereClause;

        database.UpdateQuery(query);

    }

    @Override
    public void UpdateInventoryProduct(int articleId, Collection<AttributeWrapper> attributes)
            throws SQLException {

        String whereClause = " WHERE "+EInventoryAttributes.ArticleId + "=" + articleId;
        String query = "UPDATE "+ETables.Inventory+ UpdateWrapperToQuery(attributes) + whereClause;
        
        database.UpdateQuery(query);
        
    }

    @Override
    public void RegisterFamilyCode(Collection<AttributeWrapper> attributes) throws SQLException {
        String query = "INSERT INTO "+ETables.FamilyCodes+ InsertWrapperToQuery(attributes);
        database.InsertQuery(query);
        
    }

    @Override
    public void UpdateFamilyCode(int familyId, Collection<AttributeWrapper> attributes)
            throws SQLException {
        String whereClause = " WHERE "+EFamilyCodeAttributes.FamilyCode + "=" + familyId;
        String query = "UPDATE "+ETables.FamilyCodes+ UpdateWrapperToQuery(attributes) +whereClause;
        database.UpdateQuery(query);
        
    }

    @Override
    public void UnregisterFamilyCode(int familyId) throws SQLException {
        String whereClause = " WHERE "+EFamilyCodeAttributes.FamilyCode + "=" + familyId;
        String query = "DELETE FROM "+ETables.FamilyCodes + whereClause;
        
        database.DeleteQuery(query);
    }

    @Override
    public void CreateFamiLyCodesTable() throws SQLException {
        String query = "CREATE TABLE IF NOT EXISTS "+ETables.FamilyCodes+"("+
                EFamilyCodeAttributes.FamilyCode+" INTEGER PRIMARY KEY,"+
                EFamilyCodeAttributes.FamilyName+" TEXT)";

        database.CreateQuery(query);        
    }

    @Override
    public void CreateInventoryTable() throws SQLException {
        String query = "CREATE TABLE IF NOT EXISTS "+ETables.Inventory+"("
                + EInventoryAttributes.ArticleId +" INTEGER PRIMARY KEY,\n"
                + EInventoryAttributes.ArticleName +" TEXT,\n"
                + EInventoryAttributes.ArticleCode +" INTEGER,\n"
                + EInventoryAttributes.DesignationId +" INTEGER,\n"
                + EInventoryAttributes.FamilyCode +" INTEGER)\n";

        database.CreateQuery(query);
    }

    @Override
    public void CreateStockTable() throws SQLException {

        String query = "CREATE TABLE IF NOT EXISTS "+ ETables.Stock +" (\n"
           + EStockAttributes.ArticleId + " INTEGER PRIMARY KEY,\n"
           + EStockAttributes.ArticleName + " TEXT,\n"
           + EStockAttributes.Price + " REAL,\n"
           + EStockAttributes.Quantity + " INTEGER,\n"
           + EStockAttributes.FamilyCode + " INTEGER)\n";
           
        database.CreateQuery(query);
        
    }

    @Override
    public ResultSet SearchStockProduct(SearchWrapper parametrers) throws SQLException {
        String whereClause = " WHERE "+ SearchWrapperToWhereClause(parametrers);

        String query = "SELECT * FROM "+ETables.Stock+ whereClause;
        ResultSet result = database.SelectQuery(query);
        return result;
    }

    @Override
    public ResultSet SearchInventoryProduct(SearchWrapper parametrers) throws SQLException {
        String addDesignationRestrictions = addDesignationRestriction(AppState.getInstance().getCurrentUser());
        String whereClause = " WHERE "+  SearchWrapperToWhereClause(parametrers) ;

        if(!addDesignationRestrictions.equals("")){
            whereClause += " And " + addDesignationRestrictions;
        }

        String query = "SELECT * FROM "+ETables.Inventory  + whereClause;
         
        ResultSet result = database.SelectQuery(query);
        return result;
    }

    @Override
    public ResultSet SearchFamilyCode(SearchWrapper parametrers) throws SQLException {
        String whereClause = " WHERE "+ SearchWrapperToWhereClause(parametrers);
        String query = "SELECT * FROM "+ETables.FamilyCodes+ whereClause;
        ResultSet result = database.SelectQuery(query);
        return result;
    }

    @Override
    public ResultSet LoadFamilyCode(LoadWrapper parametrers) throws SQLException {
        String extraClause = " LIMIT "+ parametrers.getLimit() + " OFFSET " + parametrers.getOffset();
        String query = "SELECT * FROM "+ETables.FamilyCodes+ extraClause;
        ResultSet result = database.SelectQuery(query);
        return result;
    }

    @Override
    public ResultSet LoadInventoryProduct(LoadWrapper parametrers) throws SQLException {
        String addDesignationRestrictions = addDesignationRestriction(AppState.getInstance().getCurrentUser());
        String query = "SELECT * FROM "+ETables.Inventory ;
         
         if(!addDesignationRestrictions.equals("")){
            query += " WHERE " + addDesignationRestrictions ;
        }
        
        ResultSet result = database.SelectQuery(query);
        return result;
    }

    @Override
    public ResultSet LoadStockProduct(LoadWrapper parametrers) throws SQLException {
         String extraClause = " LIMIT "+ parametrers.getLimit() + " OFFSET " + parametrers.getOffset();
         String query = "SELECT * FROM "+ETables.Stock+ extraClause;
         ResultSet result = database.SelectQuery(query);
         return result;
    }

    @Override
    public void CreateIndexes() throws SQLException {
        String query = "CREATE INDEX " + EIndex.CodeBarIndex +" ON "
        + ETables.Inventory 
        +"(" + EInventoryAttributes.ArticleCode 
        +")";

        database.CreateQuery(query);
    }

    @Override
    public void RegisterStockCollection(Collection<AttributeWrapper[]> collection) throws SQLException {
        String query = "INSERT INTO "+ETables.Stock+ InsertCollectionToQuery(collection);
        database.InsertQuery(query);
        
    }

    @Override
    public void RegisterInventoryCollection(Collection<AttributeWrapper[]> collection) throws SQLException {
        String query = "INSERT IGNORE INTO "+ETables.Inventory+ InsertCollectionToQuery(collection);        
        database.InsertQuery(query);
    }

    @Override
    public void RegisterFamilyCodeCollection(Collection<AttributeWrapper[]> collection) throws SQLException {
        String query = "INSERT INTO "+ETables.FamilyCodes+ InsertCollectionToQuery(collection);
        database.InsertQuery(query);
        
    }

    @Override
    public ResultSet LoadNegativeProducts(String positiveBarcodes) throws SQLException {
        String query = "SELECT * FROM "+ETables.Inventory+" WHERE "+EInventoryAttributes.ArticleCode+" NOT IN ("+positiveBarcodes+")";
        ResultSet result = database.SelectQuery(query);
        return result;
    }




}
