package idir.embag.EventStore.Models.Stock;

import java.sql.SQLException;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import idir.embag.DataModels.Metadata.EEventDataKeys;
import idir.embag.DataModels.Products.IProduct;
import idir.embag.Types.Infrastructure.Database.IProductQuery;
import idir.embag.Types.Infrastructure.Database.Generics.AttributeWrapper;
import idir.embag.Types.Stores.DataStore.IDataDelegate;

@SuppressWarnings("unchecked")
public class InventoryModel implements IDataDelegate {

    IProductQuery productQuery;

    public InventoryModel(IProductQuery productQuery) {
        this.productQuery = productQuery;
    }

    public void add(Map<EEventDataKeys,Object> data) {
        try {
            productQuery.RegisterInventoryProduct((Collection<AttributeWrapper>)data.get(EEventDataKeys.AttributeWrappersList));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void remove(Map<EEventDataKeys,Object> data) {
        IProduct product = (IProduct)data.get(EEventDataKeys.Product);
        try {
            productQuery.UnregisterInventoryProduct(product.getArticleId());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void update(Map<EEventDataKeys,Object> data) {
        IProduct product = (IProduct)data.get(EEventDataKeys.Product);
        Collection<AttributeWrapper> wrappers = (Collection<AttributeWrapper>)data.get(EEventDataKeys.AttributeWrappersList);

        try {
            productQuery.UpdateInventoryProduct(product.getArticleId() ,wrappers);
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Object> search(Map<EEventDataKeys,Object> data) {

       List<Object> result = null;
       /* Todo : implement this
       try {
        result = productQuery.SearchInventoryProduct((SearchWrapper) data);
        } catch (SQLException e) {
        e.printStackTrace();
        }*/

        return result;
    }

   

}

    

