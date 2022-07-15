package idir.embag.EventStore.Models.Stock;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import idir.embag.DataModels.Metadata.EEventDataKeys;
import idir.embag.EventStore.Stores.DataStore.IDataDelegate;
import idir.embag.Infrastructure.Database.IProductQuery;


public class FamilyModel implements IDataDelegate{

    private IProductQuery productQuery;

    public FamilyModel(IProductQuery productQuery) {
        this.productQuery = productQuery;
    }

    @Override
    public void add(Map<EEventDataKeys,Object> data) {
        try {
            productQuery.RegisterFamilyCode(data);
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
    public void update(int id, Map<EEventDataKeys,Object> data) {
        try {
            productQuery.UpdateFamilyCode(id, data);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
    }

    @Override
    public List<Object> search(Map<EEventDataKeys,Object> data) {
        List<Object> result = null;
        /*  TODO: implement this
        try {
            result = productQuery.SearchFamilyCode((SearchWrapper) data);
        } catch (SQLException e) {
            e.printStackTrace();
        }*/
        
        return result;
    }
    
}
