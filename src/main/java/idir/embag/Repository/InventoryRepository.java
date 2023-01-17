package idir.embag.Repository;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Collection;
import idir.embag.DataModels.Products.InventoryProduct;
import idir.embag.Types.Infrastructure.Database.Metadata.EInventoryAttributes;

public class InventoryRepository {
    public Collection<InventoryProduct> resultSetToProduct(ResultSet source){
        Collection<InventoryProduct> result = new ArrayList<InventoryProduct>();
        
        try {
            while (source.next()) {
                result.add(new InventoryProduct(source.getInt(EInventoryAttributes.ArticleId.toString()),
                    source.getString(EInventoryAttributes.ArticleName.toString()),
                    source.getInt(EInventoryAttributes.ArticleCode.toString()),
                    1,
                    source.getDouble(EInventoryAttributes.ArticlePrice.toString()),
                    source.getInt(EInventoryAttributes.FamilyCode.toString()),
                    source.getInt(EInventoryAttributes.DesignationId.toString())));
                  
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
}
