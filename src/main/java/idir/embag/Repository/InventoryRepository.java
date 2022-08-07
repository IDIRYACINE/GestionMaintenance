package idir.embag.Repository;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Collection;
import idir.embag.DataModels.Products.InventoryProduct;
import idir.embag.Types.Infrastructure.Database.Metadata.EInventoryAttributes;
import idir.embag.Types.Infrastructure.Database.Metadata.EStockAttributes;

public class InventoryRepository {
    public Collection<InventoryProduct> resultSetToProduct(ResultSet source){
        Collection<InventoryProduct> result = new ArrayList<InventoryProduct>();
        
        try {
            while (source.next()) {
                result.add(new InventoryProduct(source.getInt(EInventoryAttributes.ArticleId.toString()),
                    source.getString(EStockAttributes.ArticleName.toString()),
                    source.getInt(EInventoryAttributes.ArticleCode.toString()),
                    1,
                    source.getDouble(EStockAttributes.Price.toString()),
                    source.getInt(EStockAttributes.FamilyCode.toString()),
                    source.getInt(EInventoryAttributes.StockId.toString())));
                  
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
}
