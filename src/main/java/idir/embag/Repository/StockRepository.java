package idir.embag.Repository;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Collection;

import idir.embag.DataModels.Products.IProduct;
import idir.embag.DataModels.Products.StockProduct;
import idir.embag.Types.Infrastructure.Database.Metadata.EStockAttributes;

public class StockRepository {
    
    public Collection<IProduct> resultSetToProduct(ResultSet source){
        Collection<IProduct> result = new ArrayList<IProduct>();
        // resultSet to IProduct
        try {
            while (source.next()) {
                
                result.add(new StockProduct(source.getInt(EStockAttributes.ArticleId.toString()),
                    source.getString(EStockAttributes.ArticleName.toString()),
                    source.getInt(EStockAttributes.Quantity.toString()),
                    source.getDouble(EStockAttributes.Price.toString()),
                    source.getInt(EStockAttributes.FamilyCode.toString())));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
        
}
