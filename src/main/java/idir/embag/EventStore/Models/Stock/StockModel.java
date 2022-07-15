package idir.embag.EventStore.Models.Stock;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import idir.embag.DataModels.Metadata.EEventDataKeys;
import idir.embag.EventStore.Stores.DataStore.IDataDelegate;
import idir.embag.Infrastructure.Database.IProductQuery;
import idir.embag.Infrastructure.Database.Generics.AttributeWrapper;
import idir.embag.Infrastructure.Database.Generics.SearchWrapper;

public class StockModel implements IDataDelegate{

    IProductQuery productQuery;

    public StockModel(IProductQuery productQuery) {
        this.productQuery = productQuery;
    }

    @Override
    public void add(Map<EEventDataKeys,Object> data) {
        try {
            productQuery.RegisterStockProduct( data);
        } catch (SQLException e) {
        
            e.printStackTrace();
        }
        
    }

    @Override
    public void remove(int id) {
        try {
            productQuery.UnregisterStockProduct(id);
        } catch (SQLException e) {
           
            e.printStackTrace();
        }
        
    }

    @Override
    public void update(int id, Map<EEventDataKeys,Object> data) {
        try {
            productQuery.UpdateStockProduct(id,  data);
        } catch (SQLException e) {
            
            e.printStackTrace();
        }
        
    }

    @Override
    public List<Object> search(Map<EEventDataKeys,Object> data) {
        List<Object> result = null;
        /*  TODO : Implement
        try {
            result = productQuery.SearchStockProduct((SearchWrapper) data);
        } catch (SQLException e) {
           
            e.printStackTrace();
        }*/
        
        return result;
    }
}
