package idir.embag.Repository;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Collection;

import idir.embag.DataModels.Products.IProduct;
import idir.embag.DataModels.Products.StockProduct;
import idir.embag.Types.Infrastructure.Database.Generics.MDatabase;

public class StockRepository {
    
    public Collection<IProduct> resultSetToProduct(ResultSet source){
        Collection<IProduct> result = new ArrayList<IProduct>();
        // resultSet to IProduct
        try {
            while (source.next()) {
                //StockProduct(int articleId, String articleName, int articleCodebar, int articleQuantity, double articlePrice,
                //int familyCode) 
                result.add(new StockProduct(source.getInt(MDatabase.StockAttributes.ArticleId),
                    source.getString(MDatabase.StockAttributes.ArticleName),
                    source.getInt(MDatabase.StockAttributes.Quantity),
                    source.getDouble(MDatabase.StockAttributes.Price),
                    source.getInt(MDatabase.StockAttributes.FamilyCode)));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
        
}
