package idir.embag.EventStore.Models.Stock;

import java.sql.SQLException;
import java.util.List;

import idir.embag.EventStore.Stores.DataStore.IDataDelegate;
import idir.embag.Infrastructure.Database.IProductQuery;
import idir.embag.Infrastructure.Database.Generics.AttributeWrapper;
import idir.embag.Infrastructure.Database.Generics.SearchWrapper;
import idir.embag.Infrastructure.Database.Generics.MDatabase.StockAttributes;

@SuppressWarnings("unchecked")
public class StockModel implements IDataDelegate{

    IProductQuery productQuery;

    public StockModel(IProductQuery productQuery) {
        this.productQuery = productQuery;
    }

    @Override
    public void add(Object data) {
        try {
            productQuery.RegisterStockProduct((AttributeWrapper<StockAttributes>[]) data);
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
    public void update(int id, Object data) {
        try {
            productQuery.UpdateStockProduct(id, (AttributeWrapper<StockAttributes>[]) data);
        } catch (SQLException e) {
            
            e.printStackTrace();
        }
        
    }

    @Override
    public List<Object> search(Object data) {
        List<Object> result = null;
        try {
            result = productQuery.SearchStockProduct((SearchWrapper) data);
        } catch (SQLException e) {
           
            e.printStackTrace();
        }
        
        return result;
    }
}
