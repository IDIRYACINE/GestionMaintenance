package idir.embag.EventStore.Models.Stock;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import idir.embag.DataModels.Metadata.EEventDataKeys;
import idir.embag.EventStore.Stores.DataStore.IDataDelegate;
import idir.embag.Infrastructure.Database.IProductQuery;

public class InventoryModel  implements IDataDelegate {

    IProductQuery productQuery;

    
    
    public InventoryModel(IProductQuery productQuery) {
        this.productQuery = productQuery;
    }

    public void add(Map<EEventDataKeys,Object> data) {
        try {
            productQuery.RegisterInventoryProduct( data);
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

    public void update(int id , Map<EEventDataKeys,Object> data) {
        try {
            productQuery.UpdateInventoryProduct(id,  data);
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

    

