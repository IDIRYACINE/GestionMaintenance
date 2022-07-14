package idir.embag.EventStore.Models.Stock;

import java.sql.SQLException;
import java.util.List;

import idir.embag.EventStore.Stores.DataStore.IDataDelegate;
import idir.embag.Infrastructure.Database.IProductQuery;
import idir.embag.Infrastructure.Database.Generics.AttributeWrapper;
import idir.embag.Infrastructure.Database.Generics.SearchWrapper;
import idir.embag.Infrastructure.Database.Generics.MDatabase.InventoryAttributes;

@SuppressWarnings("unchecked")
public class InventoryModel  implements IDataDelegate {

    IProductQuery productQuery;

    
    
    public InventoryModel(IProductQuery productQuery) {
        this.productQuery = productQuery;
    }

    public void add(Object data) {
        try {
            productQuery.RegisterInventoryProduct((AttributeWrapper<InventoryAttributes>[]) data);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void remove(int id) {
        try {
            productQuery.UnregisterInventoryProduct(id);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void update(int id , Object data) {
        try {
            productQuery.UpdateInventoryProduct(id, (AttributeWrapper<InventoryAttributes>[]) data);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Object> search(Object data) {

       List<Object> result = null;
       try {
        result = productQuery.SearchInventoryProduct((SearchWrapper) data);
        } catch (SQLException e) {
        e.printStackTrace();
        }

        return result;
    }

   

}

    
