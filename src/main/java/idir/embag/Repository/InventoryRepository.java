package idir.embag.Repository;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Collection;

import idir.embag.DataModels.Products.IProduct;
import idir.embag.DataModels.Products.InventoryProduct;
import idir.embag.Types.Infrastructure.Database.Generics.MDatabase;

public class InventoryRepository {
    public Collection<IProduct> resultSetToProduct(ResultSet source){
        Collection<IProduct> result = new ArrayList<IProduct>();
        
        try {
            while (source.next()) {
                result.add(new InventoryProduct(source.getInt(MDatabase.InventoryAttributes.ArticleId),
                    source.getString(MDatabase.InventoryAttributes.ArticleName),
                    source.getInt(MDatabase.InventoryAttributes.ArticleCode),
                    1,
                    source.getDouble(MDatabase.InventoryAttributes.Price),
                    source.getInt(MDatabase.InventoryAttributes.FamilyCode),
                    source.getInt(MDatabase.InventoryAttributes.StockId)));
                  
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
}
