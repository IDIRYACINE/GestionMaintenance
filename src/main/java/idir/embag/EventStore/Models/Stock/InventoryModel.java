package idir.embag.EventStore.Models.Stock;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import idir.embag.DataModels.Metadata.EEventDataKeys;
import idir.embag.EventStore.Stores.DataStore.IDataDelegate;
import idir.embag.Infrastructure.Database.IProductQuery;
import idir.embag.Infrastructure.Database.Generics.AttributeWrapper;

@SuppressWarnings("unchecked")
public class InventoryModel implements IDataDelegate {

    IProductQuery productQuery;

    public InventoryModel(IProductQuery productQuery) {
        this.productQuery = productQuery;
    }

    public void add(Object data) {
        Map<EEventDataKeys,AttributeWrapper> result = (Map<EEventDataKeys, AttributeWrapper>) data;
        try {
            productQuery.RegisterInventoryProduct(result.values());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void remove(Object data) {
        Map<EEventDataKeys,AttributeWrapper> result = (Map<EEventDataKeys, AttributeWrapper>) data;

        try {
            productQuery.UnregisterInventoryProduct((int) result.get(EEventDataKeys.Id).getValue());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void update(Object data) {
        Map<EEventDataKeys,AttributeWrapper> result = (Map<EEventDataKeys, AttributeWrapper>) data;
        try {
            productQuery.UpdateInventoryProduct((int) result.get(EEventDataKeys.Id).getValue() ,result.values());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Object> search(Object data) {

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

    

