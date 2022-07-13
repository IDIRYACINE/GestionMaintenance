package idir.embag.EventStore.Models.Stock;

import java.sql.SQLException;
import java.util.List;

import idir.embag.EventStore.Stores.Generics.IDataDelegate;
import idir.embag.Infrastructure.Database.AttributeWrapper;
import idir.embag.Infrastructure.Database.IProductQuery;
import idir.embag.Infrastructure.Database.Generics.MDatabase;

@SuppressWarnings("unchecked")
public class FamilyModel implements IDataDelegate{
    private IProductQuery productQuery;

    @Override
    public void add(Object data) {
        try {
            productQuery.RegisterFamilyCode((AttributeWrapper<MDatabase.FamilliesCodeAttributes>[])data);
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
            productQuery.UpdateFamilyCode(id, (AttributeWrapper<MDatabase.FamilliesCodeAttributes>[])data);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
    }

    @Override
    public List<Object> search(Object data) {
        List<Object> result = null;
        try {
            result = productQuery.SearchFamilyCode((AttributeWrapper<MDatabase.FamilliesCodeAttributes>[])data);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return result;
    }
    
}
