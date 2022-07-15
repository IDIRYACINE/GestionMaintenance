package idir.embag.EventStore.Models.Stock;

import java.sql.SQLException;
import java.util.List;

import idir.embag.EventStore.Stores.DataStore.IDataDelegate;
import idir.embag.Infrastructure.Database.IProductQuery;
import idir.embag.Infrastructure.Database.Generics.AttributeWrapper;
import idir.embag.Infrastructure.Database.Generics.SearchWrapper;

public class FamilyModel implements IDataDelegate{

    private IProductQuery productQuery;

    public FamilyModel(IProductQuery productQuery) {
        this.productQuery = productQuery;
    }

    @Override
    public void add(Object data) {
        try {
            productQuery.RegisterFamilyCode((AttributeWrapper[])data);
        } catch (SQLException e) {
           
            e.printStackTrace();
        }
        
    }

    @Override
    public void remove(int id) {
       try {
        productQuery.UnregisterFamilyCode(id);
    } catch (SQLException e) {
        e.printStackTrace();
    }
        
    }

    @Override
    public void update(int id, Object data) {
        try {
            productQuery.UpdateFamilyCode(id, (AttributeWrapper[])data);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
    }

    @Override
    public List<Object> search(Object data) {
        List<Object> result = null;
        try {
            result = productQuery.SearchFamilyCode((SearchWrapper) data);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return result;
    }
    
}
